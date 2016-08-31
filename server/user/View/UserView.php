<?php 

class UserView{

	private $model;
	private $controller;
	private $slimApp; 
	
	public function __construct($controller, $model, $slimApp){
		$this->controller = $controller;
		$this->model = $model;
		$this->slimApp = $slimApp;
	}

	public function output(){
		$jsonResponse = json_encode($this->model->apiResponse);
		$this->slimApp->response->write($jsonResponse);
	}
}


?>

