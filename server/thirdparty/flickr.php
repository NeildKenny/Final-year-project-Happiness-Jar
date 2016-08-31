<?php

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
	$tag = 'animal, god, poop'; 
	$tag = $string = str_replace(' ', '', $tag);
	$per_page = 10; 
	$max_page = 50;
	$url = 'https://api.flickr.com/services/rest/?method=flickr.photos.search';
	$url.= '&api_key='.$api_key;
	$url.= '&tags='.$tag;
	$url.= '&per_page='.$per_page;
	$rand_page = mt_rand(0,$max_page);
	$url.='&page=$'.$rand_page;
	$url.= '&format=json';
	$url.= '&nojsoncallback=1';
	

	$result = array();
	
	$context = stream_context_create(array('http' => array('header'=>'Connection: close\r\n')));
	
	
	$response  = json_decode( file_get_contents( $url, true ), true ); 
	
	$rand_num = mt_rand(0, $per_page -1);
	
	if($response["stat"] == "ok"){

		$single_image = $response['photos']['photo'][$rand_num]; 
		
		$result['farm_id'] = $single_image['farm'];
		$result['server_id'] = $single_image['server'];
		$result['photo_id'] = $single_image['id']; 
		$result['secret_id'] = $single_image['secret'];
		$result['title'] = $single_image['title'];
		$result['size'] = 'z';
		$result['url'] = $photo_url = 'http://farm'.$result['farm_id'].'.staticflickr.com/'.$result['server_id'].'/'.$result['photo_id'].'_'.$result['secret_id'].'_'.$result['size'].'.'.'jpg';	
		
		print_r($photo_url);
	}
	
?>