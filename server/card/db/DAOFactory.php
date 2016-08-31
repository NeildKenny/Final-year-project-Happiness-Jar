<?php

include_once "DBManager.php";

Class DAOFactory{

	private $dbManager;
	
	function getDBManager(){
		if($this->dbManager == null)
			throw new Exception("No database link");
		return $this->dbManager;
	}
	
	function initDBResources(){
		$this->dbManager = new DBManager();
		$this->dbManager->openConnection();
	}
	
	function clearDBResources(){
		if($this->dbManager != null)
			$this->dbManager->closeConnection();
			
	}
	
	function getCardDAO(){
		require_once("CardDAO.php");
		return new CardDAO($this->getDBManager());
	}

}
?>