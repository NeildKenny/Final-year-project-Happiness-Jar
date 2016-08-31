<?php


class EDController{

	public function __construct($model, $action=null, $param=null, $slimApp){

		$this->slimApp = $slimApp; 
		$this->model = $model; 
		
		if($action !=null){
		
			switch($action){
				case ACTION_GET_REDDIT_IMAGE : $this->getRedditImage($param);
				break; 
				case ACTION_GET_FLICKR_IMAGE : $this->getFlickrImage($param);
				break;
				default:
			}
		}
	}


	
	private function getRedditImage($param){
		$jsonInput = $param["json"]; 

		if( isset($jsonInput) ){
		
			$parameters = json_decode($jsonInput, true); 
			
			if( isset($parameters["subreddit"]) ){
			
				$subreddit = $parameters["subreddit"];
				if( isset($parameters["last_id"]) ){
					$last_id = $parameters["last_id"];
				}else{
					$last_id = null;
				}
				$url = URL_REDDIT . '/r/' . $subreddit; 
				print_r( $url );
				if( $this->model->checkUrl($url) ){
					$result = $this->model->getRedditImage( $url, $last_id );
					if($result["error"] == false){
						
						$response["error"] = false; 
						$response["error_message"] = "no error"; 
						
						$response["id"] = $result["id"];
						$response["image"]  = $result["image"]; 
						$response["title"] = $result["title"];
						$response["description"] = $result["description"]; 
						$response["permalink"] = $result["permalink"];
						$this->model->apiResponse = $response;
						$this->slimApp->response->setStatus(HTTPSTATUS_OK);
					}
				}else{
					//subreddit does not exist
					$response["error"] = true; 
					$response["error_message"] = "Subreddit does not exist"; 
					$this->model->apiResponse = $response;
					$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );
				}
				
			}else{
				//subreddit not set
				$response["error"] = false; 
				$response["error_message"] = "Subreddit not set"; 
				$this->model->apiResponse = $response;
				$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );
			}
		}else{
			$response["error"] = false; 
			$response["error_message"] = "Input not set"; 
			$this->model->apiResponse = $response;
			$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );
		}
	}
	
	private function getFlickrImage($param){
	
		$json_input = $param["json"];
		if( isset( $json_input ) ){
			$json_object = json_decode( $json_input, true );
			if( isset( $json_object["tag"] ) || !$json_object["tag"] == "" ){
				$result = $this->model->getFlickrImage( $json_object["tag"] );
				if($result["error"] == false){
		
					$this->slimApp->response->setStatus(HTTPSTATUS_OK);
					$this->model->apiResponse = $result;
				
					
				}else{
					$response["error"] = true;
					$response["error_message"] = "Tag not set correctly, status returned bad";
					$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );
					$this->model->apiResponse = $response;
				}
				
			}else{
				$result["error"] = true; 
				$result["error_message"] = "Tag not set";
				$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );
				$this->model->apiResponse = $result;
				
			}
		}else{
			$result["error"] = true; 
			$result["error_message"] = "Json data not set";
			$this->slimApp->response->setStatus( HTTPSTATUS_BADREQUEST );
			$this->model->apiResponse = $result;
		}
	
	}

}

?>
