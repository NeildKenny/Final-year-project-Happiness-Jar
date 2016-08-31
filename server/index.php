<?php 

	require_once "Slim/Slim.php";
	
	Slim\Slim::registerAutoloader();
	
	$app=new Slim\Slim( array('debug'=>true));
	
	$app=new \Slim\Slim();
	
	include_once "user/config/config.inc.php";
	include_once "user/Model/UserModel.php";
	include_once "user/View/UserView.php";
	include_once "user/Controller/UserController.php";
	
	//Get functions
	
	$app->get('/', 'Helloworlduser');
	function Helloworlduser(){
		echo "Helloworld/user";
	}
	
	
	//Get user by id
	$app->get('/user/:id', function($id) use($app){
		$parameters['id'] = $id;
		$MVC = new MVCComponents(ACTION_GET_USER_BY_ID, $app, $parameters);
	
	});
	
	//Get user by name
	$app->get('/doesuserexistsbyid/:user_id', function($user_id) use($app){
		$parameters["user_id"] = $user_id;
		$MVC = new MVCComponents(ACTION_DOES_USER_EXIST_BY_ID, $app, $parameters); 
	});
	
	$app->get('/emailvalidation/:email', function($email) use ($app){
		$parameters["email"] = $email; 
		$MVC = new MVCComponents(ACTION_DOES_EMAIL_EXIST, $app, $parameters);
	});
	
	$app->get('/getuserbyemail/:email', function($email) use ($app){
		$parameters["email"] = $email;
		$MVC = new MVCComponents(ACTION_GET_USER_BY_EMAIL, $app, $parameters);
	});
	
	//POST user login
	
	$app->post ('/login/', function() use ($app){
		$parameters["json"] = $app->request->getBody();
		$MVC = new MVCComponents(ACTION_LOGIN_USER, $app, $parameters);
	});	
	
	//POST register users
	
	$app->post('/registeruser/', function() use($app){
		$parameters["json"] = $app->request->getBody();
		$MVC = new MVCComponents(ACTION_INSERT_USER, $app, $parameters);

	});
	

	
	$app->put('/updateuser/:id', function($id) use($app){
		$parameters["id"] = $id;
		$parameters["json"] = $app->request->getbody();
		$MVC = new MVCComponents(ACTION_UPDATE_USER, $app, $parameters); 
		
	});
	
	$app->delete('/deleteuser/:id', function($id) use($app){
		$parameters["id"] = $id;
		$MVC = new MVCComponents(ACTION_DELETE_USER, $app, $parameters);
	});
	
	$app->response()->header("Content-Type", "application/json; charset=utf-8");
	
	$app->run();
	
	
	// component for calls
	class MVCComponents{
		public function __construct($action, $app, $parameters = null ){
			$model = new UserModel() ;
			$controller = new UserController($model, $action, $parameters, $app);
			$view  = new UserView($controller, $model, $app);
			$view->output();
		}
	
	}
	
?>