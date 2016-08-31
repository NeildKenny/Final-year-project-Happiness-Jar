<?php

	define( "DB_HOST", "localhost" ); 
	define( "DB_USER", "HappinessJar" );
	define( "DB_PASSWORD", "password" );
	define( "DB_PORT", 3306 );
	define( "DB_NAME", "HappinessJar" ); 
	
	

	define("ACTION_INSERT_USER", 1); 
	define("ACTION_UPDATE_USER", 2);
	define("ACTION_DELETE_USER", 3);
	define("ACTION_GET_USER_BY_ID", 4);
	define("ACTION_DOES_USER_EXIST_BY_ID", 5);
	define("ACTION_DOES_EMAIL_EXIST", 6);
	define("ACTION_LOGIN_USER", 7);
	define("ACTION_GET_USER_BY_EMAIL", 8); 
	
	define( "HTTPSTATUS_OK", 200);
	define( "HTTPSTATUS_CREATED", 201);
	define( "HTTPSTATUS_NOCONTENT", 204);
	define( "HTTPSTATUS_BADREQUEST", 400);
	define( "HTTPSTATUS_NOTFOUND", 404 ); 
	define( "HTTPSTATUS_INTSERVER", 500);
	define( "HTTPSTATUS_FOUND", 302); 
	

?>