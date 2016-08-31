<?php
include_once "UserDBManager.php";

class DAOFactory{

	private $dbManager;
	
	function getDBManager(){
		if($this->dbManager == null)
			throw new Exception("No database link");
		return $this->dbManager;
	}
	
	function initDBResources(){
		$this->dbManager = new userDBManager();
		$this->dbManager->openConnection(); 
		
	}
	
	function clearDBResources(){
		if($this->dbManager != null)
			$this->dbManager->closeConnection();
	
	}
	
	function getUserDAO(){
		require_once("UserDAO.php");
		return new UserDAO($this->getDBManager());
		
	}
	

}

?>