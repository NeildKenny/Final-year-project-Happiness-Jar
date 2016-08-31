<?php

class UserController{

	private  $model;
	private $slimApp;
	
	public function __construct($model, $action = null, $param = null, $slimApp){
	
		$this->slimApp = $slimApp;
		$this->model = $model;
		
		if($action!=null){
		
			switch($action){
			
				case ACTION_INSERT_USER : $this->updateUser($param, false);
					break;
				case ACTION_UPDATE_USER : $this->updateUser($param, true);
					break;
					//for testing
				case ACTION_DELETE_USER : $this->deleteUser($param); 
					break;
					//for testing
				case ACTION_GET_USER_BY_ID : $this->getUserByID($param);
					break;
					//for testing
				case ACTION_DOES_USER_EXIST_BY_ID : $this->doesUserExistByID($param);
					break; 
					//for testing
				case ACTION_DOES_EMAIL_EXIST : $this->doesEmailExist($param);
					break;
				case ACTION_LOGIN_USER : $this->loginUser($param); 
					break;
					//for testing
				case ACTION_GET_USER_BY_EMAIL : $this->getUserByEmail($param); 
					break;
				default:
			
			}
		}
	}//end constructor 

	private function doesUserExistByID($param){
	$use_id = $param["user_id"];
	$response = array();
		//check for null
		//confirm we're dealing with a number
		if(  isset($use_id) ){
			//confirm user exists
			if($this->model->doesUserExistByID($use_id)){
				$response["error"] = false; 
				$response["error_message"] = "None, user exists"; 
				$this->model->apiResponse = $response; 
				$this->slimApp->response->setStatus(HTTPSTATUS_OK);
			
			} else{
				$response["error"] = true; 
				$response["error_message"] = "user not found"; 
				$this->model->apiResponse = $response; 
				$this->slimApp->response->setStatus(HTTPSTATUS_OK);
			}
		}else{
			$response["error"] = true; 
			$response["error_message"] = "user id not set"; 
			$this->model->apiResponse = $response; 
			$this->slimApp->repsonse->setStatus(HTTPSTATUS_BADREQUEST);
		}
	
	
	}
	

	private function getUserByID($param){
	
		$id = $param["id"];
		
			//confirm we're dealing with a number
			if( is_numeric($id) && isset($id) ){
				//confirm user exists
				if($this->model->doesUserExistByID($id)){
					//gets user 
					$this->model->apiResponse = $this->model->getUserByID($id);
					$this->slimApp->response->setStatus(HTTPSTATUS_OK);
					return;
				} else{
					$this->slimApp->response->setStatus(HTTPSTATUS_NOTFOUND);
				}
			}else{
				$this->slimApp->repsonse->setStatus(HTTPSTATUS_BADREQUEST);
			}
	}
	
	private function getUserByEmail($param){
		$email = $param["email"];
		$response = array();
		
		if( isset($email) ){
			if($this->model->doesEmailExist($email)){
			
				$user = $this->model->getUserByEmail($email);
				$response ["error"] = false; 
				$response['name'] = $user['name'];

				$this->model->apiResponse = $response;
				//$this->model->apiResponse = $this->model->getUserByEmail($email);
				$this->slimApp->response->setStatus(HTTPSTATUS_OK);
				return; 

			}else{
				$this->slimApp->response->setStatus(HTTPSTATUS_NOTFOUND);
				$response ["error"] = false; 
				$response['name'] = $email;
				$response['error_message'] = "Email does not exists" ; 
				$this->model->apiResponse = $response;

			}
		}else{
			$this->slimApp->repsonse->setStatus(HTTPSTATUS_BADREQUEST);
			$response ["error"] = true; 
			$response['error_message'] = "Email not set" ; 
			$this->model->apiResponse = $response;

		}

	}

	private function doesEmailExist($param){
	$email = $param["email"];
	
		if(isset($email)){
			if($this->model->doesEmailExist($email)){
				$response["error"]  = false; 
				$response["does_exist"] = true;
				$response["error_message"] = "Email found"; 
				$this->slimApp->response->setStatus(HTTPSTATUS_OK);
				$this->model->apiResponse = $response;
			} else {
				$response["error"] = false; 
				$response["does_exist"] = false;
				$response["error_message"] = "No email found";
				$this->model->apiResponse = $response;
				$this->slimApp->response->setStatus(HTTPSTATUS_OK);
			}
		}else{
			$response["error"] = true; 
			$response["error_message"] = "Email not set";
			$this->model->apiResponse = $response;

			$this->slimApp->response->setStatus(HTTPSTATUS_BADREQUEST);
		}
	}
	
	private function updateUser( $param, $isUpdate = false ){
	
		$inputJson = $param["json"];
		$jsonResponse  = array(); 
		if(isset($inputJson)){
		
			$jsonObject = json_decode($inputJson,true);
			//$name, $password, $email, $gender, $dob
			if(isset($jsonObject["name"]) && isset($jsonObject["password"]) && isset($jsonObject["email"]) &&  isset($jsonObject["gender"]) &&  isset($jsonObject["dob"]) ){
				if( $isUpdate ){
				
					if(isset($param["id"])){
					
						$id = $param["id"];
						if( $this->model->doesUserExistByID($id)){
							
							$effected_rows = $this->model->updateUser($id, $jsonObject["name"],$jsonObject["password"], $jsonObject["email"],  $jsonObject["gender"], $jsonObject["dob"]);
							
							$jsonReponse["user_id"] = "$id"; 
							$jsonResponse["error"] = false; 
							$jsonReponse["error_message"] = "User updated"; 
							$this->model->apiResponse = $jsonReponse; 
							$this->slimApp->response->setStatus(HTTPSTATUS_OK);
							
							return;
						}else{
							$jsonResponse["error_message"]  = "User does not exist";
							$jsonResponse["error"] = true; 

							$this->model->apiResponse = $jsonReponse; 
							$this->slimApp->response-->setStatus( HTTPSTATUS_NOTFOUND );
						}
					}else{
						$jsonResponse["error_message"]  = "ID not set"; 
						$jsonResponse["error"] = true; 
						$this->model->apiResponse = $jsonReponse; 
						$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );
					}
					
				}else{
					$user_id = $this->model->registerUser($jsonObject["name"],$jsonObject["password"], $jsonObject["email"], $jsonObject["gender"], $jsonObject["dob"]);
					
					
					if($user_id){
						$jsonResponse["user_id"] = "$user_id"; 
						$jsonResponse["error_message"] = "user registered"; 
						$jsonResponse["error"] = false; 
						$this->model->apiResponse = $jsonResponse; 
						$this->slimApp->response->setStatus(HTTPSTATUS_CREATED);
						return;
					}
				}		
			}
		}else{
			$jsonResponse["error"] = true; 
			$jsonResponse["error_message"]  = "Parameters not set"; 
			
			$this->model->apiResponse = $jsonReponse; 
		
			$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );
		}
	}
	
	private function loginUser($param){
	
		$inputJSON = $param["json"];
		
		//$response = array();
		
		if( isset($inputJSON) ){
		
			$jsonObject = json_decode($inputJSON, true);
			
			if( isset($jsonObject["email"]) && isset($jsonObject["password"]) ){
				
				$result = array();
				
				if( $this->model->checkLogin($jsonObject["email"], $jsonObject["password"]) ){
					
					$user_id = $this->model->getUserIDByLogin($jsonObject["email"], $jsonObject["password"]); 
					$result["error"] = false;
					$result["user_id"] = $user_id["user_id"]; 	
					$this->slimApp->response->setStatus(HTTPSTATUS_OK);
					$this->model->apiResponse = $result;

					//$responser["error"] = false; 
					//$response['email']
				}else{
					//bad login
					
					$result["error"] = true;
					$result["email"] = $jsonObject["email"]; 
					//$result["error"] = "Email or password not set correctly";
					$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );

					$this->model->apiResponse = $result;

				}
			}else{
				$result["error"] = true;
				$result["email"] = $jsonObject["email"]; 
				$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );

			// not set bad request
			}
		}else{
		//bad request
			$result["error"] = true;
			$result["email"] = $jsonObject["email"]; 
			$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );

		}
	
	}
	
	private function deleteUser($param){
		$id = $param["id"];
		if(isset($id) && is_numeric($id)){
			if($this->model->doesUserExistByID($id)){
				$result = $this->model->deleteUser($id);
				$this->slimApp->response->setStatus(HTTPSTATUS_OK);
				return($result);
			}else{
				$this->slimApp->response->setStatus(HTTPSTATUS_NOTFOUND);
			}
		}else{
			$this->slimApp->response->setStatus(HTTPSTATUS_BADREQUEST);
		}
	}
}


?>
