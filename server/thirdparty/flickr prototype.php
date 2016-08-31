<?php 
	$api_key =  "742246ea5a70ca48c973214f82b5aa86";
	$tag = 'animal, god, nature'; 
	$tag = $string = str_replace(' ', '', $tag);
	$per_page = 10; 
	$max_page = 50;
	$page = 1;
	$url = 'https://api.flickr.com/services/rest/?method=flickr.photos.search';
	$url.= '&api_key='.$api_key;
	$url.= '&tags='.$tag;
	$url.= '&per_page='.$per_page;
	$url.='&page=$'.$page;
	$url.= '&format=json';
	$url.= '&nojsoncallback=1';
	
	$result = array();
	$response  = json_decode( file_get_contents( $url, true ), true ); 
	
	$rand_num = mt_rand(0, $per_page -1);

	
	if($response["stat"] == "ok"){
		$single_image = $response['photos']['photo'][$rand_num]; 
		$photo_url = 'http://farm'.$result['farm_id'].'.staticflickr.com/'.$result['server_id'].'/'.$result['photo_id'].'_'.$result['secret_id'].'_'.$result['size'].'.'.'jpg';
		
		print_r($photo_url); 
	}else{
		print_r("error");
		}
?>