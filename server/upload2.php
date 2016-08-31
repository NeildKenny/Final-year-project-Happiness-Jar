<?php


print_r($_FILES); 
  
for($i=0; $i<count($_FILES['image']['name']); $i++) {
  //Get the temp file path
  $tmpFilePath = $_FILES['image']['tmp_name'][$i];
  //Make sure we have a filepath
  if ($tmpFilePath != ""){
    //Setup our new file path
    $newFilePath = "uploads/" . $_FILES['image']['name'][$i];

    //Upload the file into the temp dir
    if(move_uploaded_file($tmpFilePath, $newFilePath)) {
      //Handle other code here

    }
  }
}
?>