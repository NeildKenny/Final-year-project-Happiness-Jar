<?php

class DAO
{
	 var $dbManager = null; 
	
	function DAO($dbMng){
		$this->dbManager = $dbMng;
	}
	
	function getDBManager(){
		return $this->dbManager;
	}
	
	
}

?>