<?php

/* 	$url = "http://reddit.com/r/aww/.json"; 
	$url_other_visit = "http://reddit.com/r/aww/.json?limit=10&after=";
	//$last_id = "2xt0x7"; 
	if( isset($last_id) ) {
		$url = $url_other_visit .= $last_id;
	}
	
	$context = stream_context_create(array('http' => array('header'=>'Connection: close\r\n')));

	$getJson = file_get_contents($url, false, $context); 
	$decodeJson = json_decode($getJson, true); 
	//print_r($decodeJson);
	if( isset($decodeJson["error"]) ){
		print_r("error");
	}else{
	
		$i = mt_rand(0, 9);

		print_r($i);
		
		
		$image  = $decodeJson['data']['children'][$i]['data']['url'];
		$id = $decodeJson['data']['children'][$i]['data']['id'];

		$result['image'] = $image; 
		$result['id'] = $id; 

		print_r($result);
	}
	
	//print_r( $decodeJson->data->children->data->url );
	//print_r( $decodeJson->data->children); */
	
	//$url = "http://reddit.com/r/aww/.json"; 
	$url = "http://reddit.com" . "/r/" . "aww" . ".json";	
		
		print_r($url);
		
		//$last_id = "2xt0x7"; 
		
		
		$context = stream_context_create(array('http' => array('header'=>'Connection: close\r\n')));

		$getJson = file_get_contents($url, false, $context); 
		$decode_json = json_decode($getJson, true); 
		
		//print_r($decodeJson);
	
		if(isset($decode_json) ){
			
			//$decode_json['data']['children'][5]['data']['over_18'] = false;
				
	
			$i = 0;
			$nsfw = true; 
			while($nsfw == true){
				$i = mt_rand(0, 9);
				if($decode_json['data']['children'][$i]['data']['over_18'] == false){
					$nsfw = false;
				}
				
				print_r($i);

			}
			
			$check_for_extension = '.';
			print_r($i);
			
			
			$image_url  = $decode_json['data']['children'][$i]['data']['url'];
			
			if( strpos($image_url, $check_for_extension) == false ){
				$image_url .= ".jpg";
			}
			$title = $decode_json['data']['children'][$i]['data']['title'];
			$permalink = $decode_json['data']['children'][$i]['data']['permalink'];
			$id = $decode_json['data']['children'][$i]['data']['id'];
			
			$result["error"] = false; 
			$result["error_message"] = "no error";
			$result["title"] = $decode_json['data']['children'][$i]['data']['title'];
			$result["permalink"]  = $decode_json['data']['children'][$i]['data']['permalink'];
			$result["id"] = $decode_json['data']['children'][$i]['data']['id'];
			$result['image'] = $image_url; 
			$result['id'] = $id; 

			print_r( $result );
		}else{
			$result["error"] = true; 
			$result["error_message"]  = "decode_json; not set";
			return $result;
		}
		
	

?>
