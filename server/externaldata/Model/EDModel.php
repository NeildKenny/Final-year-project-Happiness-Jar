<?php 

include_once "externaldata/config/config.inc.php";
include_once "externaldata/externalsource/ExternalData.php";


class EDModel{


	public $apiResponse;
	public $ExternalData ;
	
	public function __construct(){
		$this->ExternalData  = new ExternalData();
	}
	
	public function __destruct(){
	}
	
	public function checkUrl($url){
		$result = $this->ExternalData->checkUrl($url);
		return ($result);
	}
	
	public function getRedditImage($url, $last_id = null){
		$result = $this->ExternalData->getRedditImage($url, $last_id);
		return($result);
	}
	
	public function getFlickrImage($tag){
		$result = $this->ExternalData->getFlickrImage($tag);
		return($result);
	}


		


}

?>