<?php 

	$url = "http://reddit.com" . "/r/" . "aww" . ".json";	
	print_r($url);


	$getJson = file_get_contents($url, false); 
	$decode_json = json_decode($getJson, true); 
	$i = mt_rand(0, 9);

	if(isset($decode_json) ){
		$image_url  = $decode_json['data']['children'][$i]['data']['url'];
		print_r($image_url);
	}else{
		print_r("error"); 
	}
?>