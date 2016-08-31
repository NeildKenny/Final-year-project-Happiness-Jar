<?php 
$url = "http://reddit.com/r/aw";

$test=null;
if(!isset($test)){
	print_r("I must be tired"); 
}
$poop = get_headers($url, 1); 
if($poop[1] == "HTTP/1.1 200 OK"){
	//print_r("no error");
}else{
	//print_r("error"); 
}
?>