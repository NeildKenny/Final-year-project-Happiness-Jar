<?php

	print_r($_FILES);
	print_r('uploads/' . $_FILES['image']['name']);
if (isset($_FILES['image'])) {
    // Example:
		$isUploaded = move_uploaded_file($_FILES['image']['tmp_name'], 'uploads/' . $_FILES['image']['name'] );
	if($isUploaded)
	echo 'successful';
	else{ 
		echo 'fail';
		
	}
}
else echo 'failed'
?>