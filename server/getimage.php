<?php
	$files = glob("uploads/*.*");
	$test_array_stuff = array( "uploads/1424739565060.jpg", null ,  "uploads/1424739619910.jpg");

	$j = 0; 
	for($h=0; $h<sizeof($test_array_stuff); $h++){
		if($test_array_stuff[$h] != null){
			for ($i=0; $i<sizeof($files); $i++){
				if( $files[$i] == $test_array_stuff[$j]){
				
					echo $j; 
				}
			}
		}
	}

?>



