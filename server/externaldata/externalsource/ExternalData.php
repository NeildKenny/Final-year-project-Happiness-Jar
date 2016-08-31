<?php
include_once "externaldata/config/config.inc.php";

class ExternalData{

	public function checkUrl($url){
		$header = get_headers($url, 1);
		
		if($header[1] == "HTTP/1.1 200 OK"){
			return true;
		}else{
			return false;
		}
		
	}
	
	public function getRedditImage($subreddit, $last_id = null){
		$url = $subreddit . ".json";
				
		$context = stream_context_create(array('http' => array('header'=>'Connection: close\r\n')));

		$getJson = file_get_contents($url, false, $context); 
		$decode_json = json_decode($getJson, true); 
	
		if( isset($decode_json) ){
			
			$i = 0;
			$nsfw = true; 
			
			
			while($nsfw == true){
				$i = mt_rand(0, 9);
				if( strpos($decode_json['data']['children'][$i]['data']['url'], "gif") == false ){
					if($decode_json['data']['children'][$i]['data']['over_18'] == false){
						$nsfw = false;
					}
				}
			}
			
			$check_for_extension = '.';
			
			$image_url  = $decode_json['data']['children'][$i]['data']['url'];
			
			if( strpos($image_url, $check_for_extension) == false ){
				$image_url .= ".jpg";
			}

			
			$result["error"] = false; 
			$result["error_message"] = "no error";
			
			$result["title"] = $decode_json['data']['children'][$i]['data']['title'];
			$result["permalink"]  = URL_REDDIT . $decode_json['data']['children'][$i]['data']['permalink'];
			$result["id"] = $decode_json['data']['children'][$i]['data']['id'];
			$result["image"] = $image_url; 
			$result["description"] = $decode_json['data']['children'][$i]['data']['selftext'];
			
			return($result);
			
		}else{
			$result["error"] = true; 
			$result["error_message"]  = "decode_json; not set";
			return $result;
		}
		
		
	}

	public function getFlickrImage($tag){
		/**
	Size Suffixes

	The letter suffixes are as follows:
	s	small square 75x75
	q	large square 150x150
	t	thumbnail, 100 on longest side
	m	small, 240 on longest side
	n	small, 320 on longest side
	-	medium, 500 on longest side
	z	medium 640, 640 on longest side
	c	medium 800, 800 on longest side†
	b	large, 1024 on longest side*
	h	large 1600, 1600 on longest side†
	k	large 2048, 2048 on longest side†
	o	original image, either a jpg, gif or png, depending on source format
	*/

		$api_key =  "742246ea5a70ca48c973214f82b5aa86";

		$per_page = 10; 
		$rand_num = mt_rand(0, $per_page -1);

		$max_page = 75;
		$rand_page = mt_rand(0,$max_page);

		$tag = $string = str_replace(' ', '', $tag);

		
		$url = 'https://api.flickr.com/services/rest/?method=flickr.photos.search';
		$url.= '&api_key='.$api_key;
		$url.= '&tags='.$tag;
		$url.= '&per_page='.$per_page;
		$url.='&page=$'.$rand_page;
		$url.= '&format=json';
		$url.= '&nojsoncallback=1';
		

		
		$context = stream_context_create(array('http' => array('header'=>'Connection: close\r\n')));
		
		
		$response  = json_decode( file_get_contents( $url, true, $context  ), true ); 
		
	
		if( isset($response) ){
			if($response["stat"] == "ok"){
			
				$single_image = $response['photos']['photo'][$rand_num]; 
				
				
				$result["error"] = false;
				$result["error_message"] = "No error"; 
				$result['farm_id'] = $single_image['farm'];
				$result['server_id'] = $single_image['server'];
				$result['photo_id'] = $single_image['id']; 
				$result['secret_id'] = $single_image['secret'];
				$result['title'] = $single_image['title'];
				$result['size'] = 'z';
				
				$result['url'] = $photo_url = 'http://farm'.$result['farm_id'].'.staticflickr.com/'.$result['server_id'].'/'.$result['photo_id'].'_'.$result['secret_id'].'_'.$result['size'].'.'.'jpg';	
				
				return $result; 
			}else{
				$result["error"] = true; 
				$result["error_message"] = "Response not ok"; 
			}
		}else{
			$result["error"] = true; 
			$result["error_message"] = "Response not set"; 
		}
		
		return $result; 

	}
}


?>