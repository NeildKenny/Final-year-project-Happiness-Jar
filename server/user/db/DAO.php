<?php

class DAO{

	var $dbManager = null;
	
	function DAO($dbManager){
		$this->dbManager = $dbManager;
	}

	function getDBManager(){
		return $this->dbManager; 
	}
}

?>