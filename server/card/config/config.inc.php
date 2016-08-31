<?php

	define( "DB_HOST", "localhost" ); 
	define( "DB_USER", "HappinessJar" );
	define( "DB_PASSWORD", "password" );
	define( "DB_PORT", 3306 );
	define( "DB_NAME", "HappinessJar" ); 
	
	

	define("ACTION_INSERT_CARD_TEXT", 1); 
	define("ACTION_INSERT_CARD_IMAGE", 2); 
	define("ACTION_DOES_CARD_EXIST", 3); 
	define("ACTION_GET_CARD_BY_ID",4); 
	define("ACTION_GET_USER_CARD_IDS", 5); 
	
	
	define( "HTTPSTATUS_OK", 200);
	define( "HTTPSTATUS_CREATED", 201);
	define( "HTTPSTATUS_NOCONTENT", 204);
	define( "HTTPSTATUS_PARTIAL_CONTENT",  206 ); 
	define( "HTTPSTATUS_BADREQUEST", 400);
	define( "HTTPSTATUS_NOTFOUND", 404 ); 
	define( "HTTPSTATUS_INTSERVER", 500);
	define( "HTTPSTATUS_FOUND", 302);
	define( "HTTPSTATUS_NOTMODIFIED", 304); 	
	
	define( "URL_DOES_USER_EXIST", 'http://localhost/otherbits/nyx/server/index.php/doesuserexistsbyid/' );
	
	
	define( "IMAGE_FOLDER_PATH", "uploads/"); 
	
	define( "MAX_IMAGE_ARRAY_SIZE", 4); 

?>