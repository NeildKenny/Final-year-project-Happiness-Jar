<?php 

require_once("dao.php");

class CardDAO extends DAO{

	public function CardDAO($dbMng){
		parent::DAO($dbMng);
	}

	public function getUserCardIDs($user_id){
		$sqlQuery = "SELECT card_id FROM card ";
		$sqlQuery .=" WHERE user_id = $user_id;"; 
		
		$result  = $this->getDBManager()->queryDB($sqlQuery);
		return($result); 
	}
	public function doesCardExist($card_id, $user_id){
		$sqlQuery = "SELECT count(*) as doesExist FROM card" ;
		$sqlQuery .= " WHERE card_id = $card_id AND user_id = $user_id ";
		
		$results = $this->getDBManager()->queryDB($sqlQuery);
		if($results[0]["doesExist"] == 1) return (true);
		else return(false);
	}
	
	public function getCardByID( $card_id, $user_id ){
		$sqlQuery = "SELECT * FROM card";
		$sqlQuery .= " WHERE card_id = $card_id AND user_id = $user_id";
		
		$results = $this->getDBManager()->queryDB($sqlQuery);
		return($results);
	}
	
	public function getAllCards(){
		$sqlQuery = "SELECT * FROM card"; 
		
		$results = $this->getDBManager()->queryDB($sqlQuery);
		return($results); 
	}
	
	public function insertCardText( $user_id, $title, $description, $tag ){
		$sqlQuery = " INSERT INTO card (user_id, title, description, tag)"; 
		$sqlQuery .= " VALUES ( $user_id, '$title', '$description', '$tag' )";
		$result = $this->getDBManager()->insertData($sqlQuery);

		return ($result);
	}
	
	public function updateImage($user_id, $card_id, $image1 , $image2, $image3, $image4){
		$sqlQuery = "UPDATE card SET image_one = '$image1', image_two = '$image2', image_three  = '$image3', image_four = '$image4' ";
		$sqlQuery .= " WHERE card_id = $card_id AND user_id = $user_id; "; 
		
		$result = $this->getDBManager()->updateData($sqlQuery); 
		return ($result); 
	
	}
	public function updateCard($card_id, $user_id, $title, $descritpion, $tags){
		$sqlQuery = "UPDATE card SET title = '$title', description = '$description')";
		$sqlQuery .= " WHERE card_id = $card_id AND user_id = $user_id ";
		
		$results = $this->getDBManager()->updateData($sqlQuery);
		return($results);
	}
	
	public function deleteCard($card_id, $user_id){
		$sqlQuery = "DELETE FROM card" ;
		$sqlQuery = " WHERE card_id = $card_id AND user_id = $user_id";
		
		$results = $this->getDBManager()->deleteData();
		return($results);
	}
}

?>