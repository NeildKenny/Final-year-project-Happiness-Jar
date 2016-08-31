<?php

require_once("user/db/DAO.php");

class UserDAO extends DAO{

	public function UserDAO($dbMng){
		parent::DAO($dbMng); 
	}
	
	public function doesUserExistByID($id){
		$sqlQuery = "SELECT count(*) as doesExist FROM users ";
		$sqlQuery .="WHERE user_id = '$id'";
		$result = $this->getDBManager()->queryDB($sqlQuery);
		if($result[0]["doesExist"] ==1) return(true); 
	}
	
	public function getUserByID($id){
		$sqlQuery = "SELECT * FROM users ";
		$sqlQuery .= "WHERE user_id = '$id'";
		$result = $this->getDBManager()->queryDB($sqlQuery);
		return($result);
		
	}
	
	public function getUserByEmail($email){
		$sqlQuery = "SELECT * FROM users ";
		$sqlQuery .= " WHERE email = '$email' ";
		$result = $this->getDBManager()->queryDB($sqlQuery);
		return ($result);
	
	}

	public function doesUserExist($username){
		$sqlQuery = "SELECT count(*) as doesExist FROM users ";
		$sqlQuery .="WHERE username = '$username'";
		$result = $this->getDBManager()->queryDB($sqlQuery);
		if($result[0]["doesExist"] ==1) return(true); 
	}
	
	public function doesEmailExist($email){
		$sqlQuery = "SELECT count(*) as doesExist  from users ";
		$sqlQuery .=" WHERE email  = '$email'";
		$result = $this->getDBManager()->queryDB($sqlQuery);
		if($result[0]["doesExist"] == 1) return(true);
	}
	
	
	public function getUserByName($username){
		$sqlQuery = "SELECT * FROM users" ; 
		$sqlQuey .= " WHERE username = '$username'";
		$result = $this->getDBManager()->queryDB($sqlQuery);
		return($result);
	}
	
	public function registerUser($name, $password, $email, $gender, $dob){
	
		$sqlQuery = "INSERT INTO users ( name, password, email, gender, dob ) ";
		$sqlQuery.= " VALUES('$name', '$password', '$email', '$gender', '$dob') ";
		$result = $this->getDBManager()->insertData($sqlQuery); 

		return($result);
	}
	
	public function updateUser( $id, $name, $password, $email, $gender, $dob){
		$sqlQuery = "UPDATE users SET name = '$name', password = '$password', email = '$email', dob = '$dob' ";
		$sqlQuery .=" WHERE user_id = $id" ;
		$result = $this->getDBManager()->updateData($sqlQuery);
		return($result); 
	}
	
	public function deleteUser($id){
		$sqlQuery = "DELETE FROM users";
		$sqlQuery.= " WHERE user_id = $id ";
		$result = $this->getDBManager()->deleteData($sqlQuery);
		
		return($result);
	}
	
	public function checkLogin($email, $password){
		$sqlQuery  = "SELECT count(*) as doesExist FROM users" ;
		$sqlQuery .= " WHERE email = '$email' and password = '$password' ";
		$result = $this->getDBManager()->queryDB($sqlQuery);
		
		if($result[0]["doesExist"] == 1) return(true);
		else return false; 
		
	}
	
	public function getUserIDByLogin($email, $password){
		$sqlQuery = "SELECT user_id FROM users ";
		$sqlQuery .= "WHERE email = '$email' and password = '$password' "; 
		
		$result = $this->getDBManager()->queryDB($sqlQuery); 
		return ($result[0]);
	}
	
}

?>