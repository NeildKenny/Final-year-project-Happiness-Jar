<?php 

class EdView{
	
	private $model;
	private $controller;
	private $slimApp;
	
	public function __construct($controller, $model, $slimApp){
		$this->model = $model;
		$this->controller = $controller;
		$this->slimApp = $slimApp;
		
	}
	
	public function output(){
		$jsonResponse = json_encode($this->model->apiResponse);
		$this->slimApp->response->write($jsonResponse);
	}

}


?>