<?php

require_once "/Slim/Slim.php";

Slim\Slim::registerAutoloader();

$app = new Slim\Slim( array('debug'=>true));

$app = new \Slim\Slim();

include_once "card/config/config.inc.php";
include_once "card/Model/CardModel.php";
include_once "card/Controller/CardController.php";
include_once "card/View/CardView.php"; 


$app->get('/', 'Helloworlduser');
function Helloworlduser(){
	echo "Helloworld/user";
}

$app->get('/getcard/:card_id/:user_id', function($card_id, $user_id) use($app){
	$parameters['user_id'] = $user_id;
	$parameters['card_id'] = $card_id; 

	$MVC = new MVCComponent(ACTION_GET_CARD_BY_ID, $app, $parameters);
});

$app->get('/getusercardids/:user_id', function($user_id) use($app){
	$parameters['user_id'] = $user_id;
	
	$MVC = new MVCComponent(ACTION_GET_USER_CARD_IDS, $app, $parameters);
});

$app->post('/addcardtext', function() use($app){
	print_r($_POST); 
	
	//$parameters["json"] = $app->request->getBody();
	//$MVC = new MVCComponent(ACTION_INSERT_CARD_TEXT, $app, $parameters); 
	
});

$app->post('/addcardimage' , function() use($app){

//add  if files set if post set

	//print_r( $_FILES);
	$parameters = $_FILES; 
	$parameters["text_data"] = $_POST; 
	//print_r($_POST); 
	$MVC = new MVCComponent(ACTION_INSERT_CARD_IMAGE, $app, $parameters); 

}); 


$app->response()->header("Content-Type", "application/json; charset=utf-8");
$app->run();

class MVCComponent{
	public function __construct( $action, $app, $parameters=null ){
		$model = new CardModel();
		$controller = new CardController($model, $action, $parameters, $app);
		$view = new CardView($controller, $model, $app);
		$view->output();
	}
}


?>