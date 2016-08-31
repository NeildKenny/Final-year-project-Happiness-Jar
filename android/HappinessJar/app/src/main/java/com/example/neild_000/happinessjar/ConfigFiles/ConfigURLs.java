package com.example.neild_000.happinessjar.ConfigFiles;

/**
 * Created by neild_000 on 09/02/2015.
 */
public class ConfigURLs {

    private final static String IP_ADDRESS = "http://192.168.137.1/";
    private final static String FOLDER_PATH = "otherbits/nyx/server/";
    private final static String FULL_URL = IP_ADDRESS + FOLDER_PATH;

    public final static String URL_LOGIN = FULL_URL + "index.php/login/";
    public final static String URL_REGISTRATION = FULL_URL + "index.php/registeruser/";
    public final static String URL_CHECK_EMAIL = FULL_URL +"index.php/emailvalidation/";
    public final static String URL_PHOTO_UPLOAD = FULL_URL + "cardindex.php/addcardimage";
    //url is /getcard/card_id/user_id
    public final static String URL_GET_CARD_FROM_SERVER = FULL_URL +"cardindex.php/getcard/";
    public final static String URL_GET_CARD_IDS_FROM_SERVER = FULL_URL + "cardindex.php/getusercardids/";

    public final static String URL_IMAGE_UPLOAD_FOLDER = FULL_URL + "uploads/";

    public final static String URL_REDDIT = FULL_URL + "edIndex.php/getredditimage";

    public final static String URL_FLICKR = FULL_URL + "edIndex.php/getflickrimage";


   //public final static String URL_PHOTO_UPLOAD = FULL_URL + "upload.php";


}
