setup ad hoc network 

netsh wlan set hostednetwork mode=allow ssid=food key=foobared

netsh wlan start hostednetwork 

curl -i -X POST -H 'Content-Type: application/json' -d '{"subreddit" : "aww"  }' http://localhost/otherbits/nyx/server/edindex.php/getreddit

curl -i -X POST -H 'Content-Type: application/json' -d '{"subreddit" : "aww", "last_id" : "2xsfu0" }'http://localhost/otherbits/nyx/server/edindex.php/getredditimage

curl -i -X POST -H 'Content-Type: application/json' -d '{"email" : "neildkenny@hotmail.com" , "password" : "rawr" }' http://192.168.173.1/otherbits/nyx/server/index.php/login

curl -i -X POST -H 'Content-Type: application/json' -d '{"name": "demods Kenny", "password" : "rawr", "email" : "neisdfxdsdzxzdkenny@gmail.com", "gender" : "m", "dob" : "1021990"}' http://192.168.173.1/otherbits/nyx/index.php/registeruser

curl -i -X POST -H 'Content-Type: application/json' -d '{"name": "demoddds Kenny", "password" : "rawr", "email" : "neisdfxdsdzxzddddddkenny@gmail.com", "gender" : "m", "dob" : "1021990"}' http://localhost/otherbits/nyx/server/index.php/registeruser

curl -i -X POST -H 'Content-Type: application/json' -d '{"email" : "davidbkenny@gmail.com", "password" : "Balbriggan"}' http://localhost/otherbits/nyx/index.php/login

curl -i -X PUT -H 'Content-Type: application/json' -d '{"name": "test for demo", "password" : "rawr", "email" : "neildkenny@hotmail.com", "gender" : "m", "dob" : "16021990"}' http://localhost/otherbits/nyx/server/index.php/updateuser/32
 
 curl -i -X DELETE http://localhost/otherbits/nyx/index.php/deleteuser/1
 
 curl -i -X GET http://localhost/otherbits/nyx/index.php/users/2
  
  curl -i -X GET http://localhost/otherbits/nyx/server/index.php/doesuserexistsbyid/29

 
  curl -i -X GET http://localhost/otherbits/nyx/index.php/users/32
  
  curl -i -X GET http://localhost/otherbits/nyx/index.php/useremail/davidbkenny@gmail.com
  
  curl -i -X GET 192.168.173.1/otherbits/nyx/index.php/useremail/qwert@gmail.com
  
  curl -i -X GET 192.168.173.1/otherbits/nyx/index.php/useremail/davidbkenny@gmail.com
  
curl -i -X GET 192.168.173.1/otherbits/nyx/index.php/getuserbyemail/davidbkenny@gmail.com
  
  curl -i -X GET localhost/otherbits/nyx/server/cardindex.php/getusercardids/1
    curl -i -X GET localhost/otherbits/nyx/server/cardindex.php/getcard/54/2