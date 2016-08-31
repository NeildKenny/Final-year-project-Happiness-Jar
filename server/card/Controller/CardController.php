<?php


class CardController{

	public function __construct($model, $action=null, $param=null, $slimApp){

		$this->slimApp = $slimApp; 
		$this->model = $model; 
		
		if($action !=null){
		
			switch($action){
				case ACTION_GET_CARD_BY_ID : $this->getCardByID($param);
				break; 
				case ACTION_DOES_CARD_EXIST : $this->doesCardExist($param);
				break;
				case ACTION_INSERT_CARD_TEXT : $this->updateCardText($param, false); 
				break;
				case ACTION_INSERT_CARD_IMAGE : $this->updateCardImage($param, false); 
				break; 
				case ACTION_GET_USER_CARD_IDS : $this->getUserCardIDs($param); 
				break; 
				default:
			}
		}
	}


	private function getUserCardIDs($param){
		
		
		if(isset($param)){
			$user_id = $param["user_id"]; 
		
			$url = URL_DOES_USER_EXIST; 
			$url .= $param["user_id"];
			
			$context = stream_context_create(array('http' => array('header'=>'Connection: close\r\n')));
			$does_user_exist_json = file_get_contents($url, false, $context);
			$does_user_exist = json_decode($does_user_exist_json);
			
			if($does_user_exist->{'error'} == false){
				$response = $this->model->getUserCardIDs($user_id);
				if( isset($response) ){
					$jsonResponse["error"] = false;
					$jsonResponse["error_message"] = "none"; 
					$jsonResponse["card_ids"] = $response;
					$this->model->apiResponse = $jsonResponse;
					$this->slimApp->response->setStatus(HTTPSTATUS_OK);
				}else{
					$jsonResponse["error"] = false;
					$jsonResponse["error_message"] = "no cards"; 
					$this->model->apiResponse = $jsonResponse;
					$this->slimApp->response->setStatus(HTTPSTATUS_OK);
				}

			}else{
				$jsonResponse["error"] = true;
				$jsonResponse["error_message"] = "User does not exist"; 
				$this->model->apiResponse = $jsonResponse;
				$this->slimApp->response->setStatus(HTTPSTATUS_BADREQUEST);
			
			}
		}else{
			$jsonResponse["error"] = true;
			$jsonResponse["error_message"] = "parameters not set "; 
			$this->model->apiResponse = $jsonResponse;
			$this->slimApp->response->setStatus(HTTPSTATUS_BADREQUEST);
		
		}
	}

	private function doesCardExist($param){
	
	}
	private function getCardByID($param){

		
		if( isset($param['card_id']) && isset($param['user_id']) ){
		
			$url = URL_DOES_USER_EXIST; 
			$url .= $param["user_id"];
			
			$context = stream_context_create(array('http' => array('header'=>'Connection: close\r\n')));
			$does_user_exist_json = file_get_contents($url, false, $context);
			$does_user_exist = json_decode($does_user_exist_json);
			
			if($does_user_exist->{'error'} == false){

				if( isset($param['card_id']) && isset($param['user_id']) ){
				
					if( is_numeric($param['card_id']) && is_numeric($param['user_id']) ){
					
						if($this->model->doesCardExist( $param['card_id'], $param['user_id'] )){
							$card_response = $this->model->getCardByID( $param['card_id'], $param['user_id']);
							$jsonResponse["error"] = false;
							$jsonResponse["error_message"] = "none"; 
							$jsonResponse["card"] = $card_response; 
							$this->model->apiResponse = $jsonResponse; 
							$this->slimApp->response->setStatus(HTTPSTATUS_OK);
						}else{
							$jsonResponse["error"] = true;
							$jsonResponse["error_message"] = "User does not exist"; 
							$this->model->apiResponse = $jsonResponse;
							$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );	
						}
					
					}else{
						$jsonResponse["error"] = true;
						$jsonResponse["error_message"] = "Parameters not correct format";  
						$this->model->apiResponse = $jsonResponse;
						$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST) ;
					}
				}else {
					$jsonResponse["error"] = true;
					$jsonResponse["error_message"] = "Parameters not set";  
					$this->model->apiResponse = $jsonResponse;
					$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST) ;
				
				}
			}else{
				$jsonResponse["error"] = true;
				$jsonResponse["error_message"] = "User does not exist"; 
				$this->model->apiResponse = $jsonResponse;
				$this->slimApp->response->setStatus(HTTPSTATUS_BADREQUEST);
			
			}
	
		}
	}
	
	
	
//insert/update card
	private function updateCardText($param, $isUpdate=false){
		$inputJson = $param["json"];
		
		if(isset($inputJson)){
			$jo = json_decode($inputJson, true);
			if(isset($jo["title"]) && isset($jo["description"]) && isset($jo["tags"])){
				if($isUpdate){
					if(isset($jo["id"])){
						$id = $jo["id"];
						if($this->model->doesCardExist($id)){
							$id = $this->model->updateCard($id, $jo["title"], $jo["description"], $jo["tags"]);
							$this->slimApp->response->setStatus(HTTPSTATUS_OK);
							return;
						}
					}else{
						$this->slimApp->response->setStatus( HTTPSTATUS_NOTFOUND );
					}
				}else{
					$id = $this->model->insertCard($jo["title"], $jo["description"], $jo["tags"]);
					if($id){
						$jsonResponse["Location"] = "Card/$id";
						$this->model->apiResponse = $jsonResponse;
						$this->slimApp->response->setStatus(HTTPSTATUS_CREATED);
						return;
					}
				}
			}else{
				$this->slimApp->response->setStatus(HTTPSTATUS_BADREQUEST);
			}
		}
	}
	
	private function updateCard($param, $isUpdate = false){
	if( $isUpdate = false ){
		if(isset($param)){
			$image_file = $param;
			//print_r ($image_file);	
			for($i=0; $i<count($image_file['image']['name']); $i++) {
			//Get the temp file path
				$tmpFilePath = $image_file['image']['tmp_name'][$i];
			//Make sure we have a filepath
				if ($tmpFilePath != ""){
			//Setup our new file path
					$newFilePath = "uploads/" . $image_file['image']['name'][$i];

			//Upload the file into the temp dir
					if(move_uploaded_file($tmpFilePath, $newFilePath)) {
			//Handle other code here
						
						$jsonResponse["error"] = "none, object created ". implode($image_file["image"]["name"]);
						$this->model->apiResponse = $jsonResponse;
						$this->slimApp->response->setStatus(HTTPSTATUS_CREATED);
						
						/* if($i == count[$image_file]){
							
							$this->model->insertCard($image_file["
					
						}
 */
					}else{
						$jsonResponse["error"] = "object not created".implode($image_file["image"]["name"]);
						$this->model->apiResponse = $jsonResponse;
						$this->slimApp->response->setStatus(HTTPSTATUS_INTSERVER);

					}
				}else{
					$jsonResponse["error"] = "temp path not set";
					$this->model->apiResponse = $jsonResponse;
					$this->slimApp->response->setStatus(HTTPSTATUS_BADREQUEST);				
				}
			}
		}else{
		
			$jsonResponse["error"] = "param not set";
			$this->model->apiResponse = $jsonResponse;
			$this->slimApp->response->setStatus(HTTPSTATUS_BADREQUEST);
		
		}
	}
	}
	private function updateCardImage( $param, $isUpdate = false ){
		if($isUpdate == false){
			if( isset($param) ){
				
				if( !isset($param["text_data"]["title"]) || $param["text_data"]["title"] == "null" ){
					$param["text_data"]["title"] = null; 
				}
				
				if( !isset($param["text_data"]["description"]) || $param["text_data"]["description"] == "null" ){
					$param["text_data"]["description"] = null; 
				}
				if( !isset($param["text_data"]["tag"]) || $param["text_data"]["tag"] == "null" ){
					$param["text_data"]["tag"] = null; 
				}
				$card_id = $this->model->insertCardText( $param["text_data"]["user_id"], $param["text_data"]["title"], $param["text_data"]["description"], $param["text_data"]["tag"]); 
				
				if( isset($card_id) ){
					$image_file = $param;
					//print_r($param);
					for($i=0; $i<count($image_file['image']['name']); $i++) {
					//Get the temp file path
						$tmpFilePath = $image_file['image']['tmp_name'][$i];
					//Make sure we have a filepath
						if ($tmpFilePath != ""){
					//Setup new file path
							$newFilePath = IMAGE_FOLDER_PATH . $image_file['image']['name'][$i];

					//Upload the file into the temp dir
							if( move_uploaded_file($tmpFilePath, $newFilePath) ){
					//Handle other code here
								$imagePath["image"][$i]   = $image_file['image']['name'][$i]; 
								
	/* 							$jsonResponse["error"] = "none, object created ". implode($image_file["image"]["name"]);
								$this->model->apiResponse = $jsonResponse;
								$this->slimApp->response->setStatus(HTTPSTATUS_CREATED); */

							}else{
								$jsonResponse["error"] = "object not created".implode($image_file["image"]["name"]);
								$this->model->apiResponse = $jsonResponse;
								$this->slimApp->response->setStatus(HTTPSTATUS_INTSERVER);

							}
						}else{
							$jsonResponse["error"] = "path not set";
							$this->model->apiResponse = $jsonResponse;
							$this->slimApp->response->setStatus(HTTPSTATUS_BADREQUEST);				
						}
					}
					
					if( isset($imagePath) ){
						//print_r($imagePath); 
						//negates array offset error 
						for($i=0; $i<MAX_IMAGE_ARRAY_SIZE; $i++){
							if( !isset($imagePath["image"][$i]) ){
								$imagePath["image"][$i] = null; 
							}
							
						}
						$result = $this->model->updateImage( $param["text_data"]["user_id"], $card_id , $imagePath["image"][0],$imagePath["image"][1], $imagePath["image"][2], $imagePath["image"][3] );
						
						if($result == 1){
							$jsonResponse["error"] = "none, object created ". implode( $image_file["image"]["name"] );
							$this->model->apiResponse = $jsonResponse;
							$this->slimApp->response->setStatus(HTTPSTATUS_CREATED);
						}else{
							$jsonResponse["error"] = "Error inserting images no result returned";
							$this->model->apiResponse = $jsonResponse;
							$this->slimApp->response->setStatus(HTTPSTATUS_INTSERVER);		
						}
				
					}else{
							$jsonResponse["error"] = "Image path not set  ";
							$this->model->apiResponse = $jsonResponse;
							$this->slimApp->response->setStatus(HTTPSTATUS_INTSERVER);				
						}
					
				}else{
				
					$jsonResponse["error"] = "ID not set, means insert not inserted";
					$this->model->apiResponse = $jsonResponse;
					$this->slimApp->response->setStatus(HTTPSTATUS_PARTIAL_CONTENT);
				
				}
			}else{
				
				$jsonResponse["error"] = "param not set";
				$this->model->apiResponse = $jsonResponse;
				$this->slimApp->response->setStatus(HTTPSTATUS_NOCONTENT);
				
			}
		}
	}
	
	private function checkIsSet($param){
		if(isset($param)){
			return $param;
		}else{
			return null; 
		}

	}

	private function deleteCard($param){
		$id = $param["id"];
		
		if($this->model->doesCardExist($id)){
			$this->model->deleteMovie($id);
			$this->slimApp->response->setStatus(HTTPSTATUS_OK);
			
		}else{
			$this->slimApp->response->setStatus(HTTPSTATUS_NOTFOUND);
		}
	
	}


}

?>
