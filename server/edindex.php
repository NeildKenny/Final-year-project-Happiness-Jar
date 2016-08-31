<?php

	require_once "/Slim/Slim.php";

	Slim\Slim::registerAutoloader();

	$app = new Slim\Slim( array('debug'=>true));

	$app = new \Slim\Slim();

	include_once "externaldata/config/config.inc.php";
	include_once "externaldata/Model/EDModel.php";
	include_once "externaldata/Controller/EDController.php";
	include_once "externaldata/View/EDView.php"; 


	$app->get('/', 'Helloworlduser');
	function Helloworlduser(){
		echo "Helloworld/user";
	}
	
	$app->post('/getredditimage/', function() use($app ){
		$param["json"] = $app->request->getBody();
		$MVC = new MVCComponent(ACTION_GET_REDDIT_IMAGE, $app, $param);
	});	
	
	$app->post('/getflickrimage/', function() use($app ){
		$param["json"] = $app->request->getBody();
		$MVC = new MVCComponent(ACTION_GET_FLICKR_IMAGE, $app, $param);
	});	

	
	$app->response()->header("Content-Type", "application/json; charset=utf-8");
	$app->run();

	class MVCComponent{
		public function __construct( $action, $app, $param=null ){
			$model = new EDModel();
			$controller = new EDController($model, $action, $param, $app);
			$view = new EDView($controller, $model, $app);
			$view->output();
		}
	}


?>