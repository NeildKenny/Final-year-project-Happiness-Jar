<?php 

include_once 'user/config/config.inc.php';

include_once "user/db/DAOFactory.php";


// User model

class UserModel{

	public $DAOFactory; 
	public $validationFactory;
	public $UserDAO; 
	public $apiResponse;

	public function __construct(){ 

		$this ->DAOFactory = new DAOFactory();
		$this->DAOFactory->initDBResources();
		$this->UserDAO = $this->DAOFactory->getUserDAO();

	}

	public function __destruct(){
		$this->DAOFactory->clearDBResources();
	}
	
	public function getUserByID($id){
		$result = $this->UserDAO->getUserByID($id);
		return($result[0]);
	}

	public function doesUserExistByID($id){
		$result = $this->UserDAO->doesUserExistByID($id);
		return($result);
	}
	
	public function doesUserExist($name){
		$result = $this->UserDAO->doesUserExist($name);
		return($result);	
	}
	
	public function doesEmailExist($email){
		$result = $this->UserDAO->doesEmailExist($email);
		return($result); 
	}

	public function getUserByEmail($email){
		$result = $this->UserDAO->getUserByEmail($email);
		return($result[0]);
	}

	public function registerUser($name, $password, $email, $gender, $dob){
		$results = $this->UserDAO->registerUser( $name, $password, $email, $gender, $dob);
		return($results);
	}
	public function updateUser($id, $name, $password, $email,  $gender, $dob){
		$result = $this->UserDAO->updateUser($id, $name, $password, $email, $gender, $dob);
		return($result);
	}
	
	public function deleteUser($id){
		$result = $this->UserDAO->deleteUser($id);
		var_dump($result);
		return($result);
	}
	
	public function checkLogin($email, $password){
		$result = $this->UserDAO->checkLogin($email, $password);
		return($result);
	}
	
	public function getUserIDByLogin($email, $password){
		$result = $this->UserDAO->getUserIDByLogin($email, $password);
		return($result); 
	}
}
?>