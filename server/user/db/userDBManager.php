<?php

include_once "user/config/config.inc.php";

class UserDBManager{

	private $db_link;
	
	function OpenConnection(){
		$this->db_link = mysqli_connect( DB_HOST, DB_USER, DB_PASSWORD, DB_NAME) or die("unable to make database connection");
	}
	
	function queryDB($query){
		$result = mysqli_query($this->db_link, $query) or die("Error in SQL Statement: " . $query);
		
		while($row  = $result ->fetch_array(MYSQLI_ASSOC)){
			$rows[]=$row;
		}
		return($rows);
	}
			
	//inserts data into database
	function insertData($query){
		$result = mysqli_query($this->db_link,  $query) or die("Error in SQL statement: " . $query); 		
		if( isset($result) ){
			$result = mysqli_insert_id($this->db_link); 
		}else{
			$result = "error in sql statement, user not inserted";
		}
		return($result);
	}
	//update database
	function updateData($query){
		$result = mysqli_query($this->db_link,  $query) or die("Error in SQL statement: " . $query); 		
		return($result);
	}
	
	//deletes data from database
	function deleteData($query){
		$result = mysqli_query($this->db_link,  $query) or die("Error in SQL statement: " . $query); 		
		return($result);
	}
	//closes connection with the database 
	function closeConnection(){
		$this->db_link->close();
	}
	


}


?>