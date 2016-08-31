include_once "card/config/config.inc.php";
	include_once "card/Model/CardModel.php";
	include_once "card/Controller/CardController.php";
	include_once "card/View/CardView.php"; 


	$app->get('/', 'Helloworlduser');
	function Helloworlduser(){
		echo "Helloworld/user";
	}

	$app->get('/card/:id', function($id) use($app){
		$parameters['id'] = $id;
		$MVC = new MVCComponent(ACTION_GET_CARD_BY_ID, $app, $parameters);
	});

	$app->get('/cardimage', function($id) use($app){
		
	}


	/* $app->post(/'addcardimage', function($id) use($app){
		$parameters["file_data"] = $_FILES; 
		$parameters["post_data"] = $_POST; 
		
		$MVC = new MVCComponent(ACTION_INSERT_CARD_IMAGE, $app, $parameters); 
	} );  */
	/* $app->post('/addcardphoto/' , function($id) use($app){
			$parameters["file_data"] = $_FILES; 
			$parameters["post_data"] = $_POST; 
			
			$MVC = new MVCComponent(ACTION_INSERT_CARD_IMAGE, $app, $parameters); 
		


	});  */


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