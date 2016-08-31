<?php 

include_once "card/config/config.inc.php";
include_once "card/db/DAOFactory.php"; 
include_once "card/File/CardFiles.php"; 

class CardModel{

	public $DAOFactory;
	public $validationFactory;
	public $cardDAO; 
	public $CardFiles; 
	public $apiResponse;
	
	
	public function __construct(){
		$this->DAOFactory = new DAOFactory();
		$this->DAOFactory->initDBResources();
		$this->cardDAO = $this->DAOFactory->getCardDAO();

	}
	
	public function __destruct(){
		$this->DAOFactory->clearDBResources();
	}
	
	
	public function doesCardExist( $card_id, $user_id ){
		$result = $this->cardDAO->doesCardExist( $card_id, $user_id );
		return($result);
	}
	
	public function getCardByID( $card_id, $user_id ){
		$result = $this->cardDAO->getCardByID( $card_id, $user_id );
		return($result[0]);
	}
	
	public function getCardImages( $card_image_data ){
		$result = $this->cardfiles->getCardImages( $card_image_data ); 
		return($result); 
	}
	
	public function getUserCardIDs($user_id){
		$result = $this->cardDAO->getUserCardIDs($user_id); 
		return($result); 
	}
	
	public function insertCardText( $card_id, $user_id, $title, $description= null, $tag = null){
		$result = $this->cardDAO->insertCardText( $card_id, $user_id, $title, $description, $tag );
		return($result);
	}
	
	public function updateImage( $user_id, $card_id, $image1 = null , $image2 = null, $image3 = null, $image4 = null ){
		$result = $this->cardDAO->updateImage( $user_id, $card_id, $image1 , $image2, $image3, $image4 ); 
		return ($result); 
	}
	public function updateCard($card_id, $user_id, $title, $descritpion, $tags){
		$result = $this->cardDAO->updateCard($card_id, $user_id, $title, $descritpion, $tags);
		return($result);
	}
	
	public function deleteCard($card_id, $user_id){
		$result = $this->cardDAO->deleteCard($card_id, $user_id);
		return($result); 
	}

		


}

?>