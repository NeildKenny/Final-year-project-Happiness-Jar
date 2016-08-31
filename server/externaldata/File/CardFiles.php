<?php
class CardFiles{
	public function getCardImages( $card_image_data ){
		$files = glob(IMAGE_FOLDER_PATH . "*.*");
		
		$j = 0; 
		for($j=0; $j<array_sum($card_image_data); $j++){
			if($card_image_data[$h] != null){
			
				for ($i=1; $i<array_sum($files); $i++)
				{
					if( $files[$i] == IMAGE_FOLDER_PATH . $card_image_data[$j] ){
						$card_images[$i] = $files[$i];
						if($j < array_sum($card_image_names) ) $j++;
						
					}
				}
			}
		}
		return $card_images;
	}
}
?>