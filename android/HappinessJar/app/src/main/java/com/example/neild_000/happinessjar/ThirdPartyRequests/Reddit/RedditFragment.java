package com.example.neild_000.happinessjar.ThirdPartyRequests.Reddit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.ImageHandler;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.RESTCalls;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

/**
 * Created by neild_000 on 10/03/2015.
 */
public class RedditFragment extends Fragment {

    private ImageView imageView;
    private TextView title;
    private TextView description;
    private TextView textview_url;

    private RedditModel redditModel;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(RedditFragment.class.toString(), "In reddit fragmetn create view");
        View rootView = inflater.inflate(R.layout.third_party_data_layout, container, false);
        initRedditFragmentView(rootView);

        new GetRedditMedia().execute("tifu");
        return rootView;

    }

    private void initRedditFragmentView(View view){
        imageView = (ImageView) view.findViewById(R.id.third_party_image);
        title = (TextView) view.findViewById(R.id.third_party_title);
        description = (TextView) view.findViewById(R.id.third_party_description_TextView);
        textview_url = (TextView) view.findViewById(R.id.third_party_url);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        redditModel = new RedditModel();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class GetRedditMedia extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {

            JSONObject jsonObject = new JSONObject();

            try{
                jsonObject.put("subreddit", params[0]);
            }catch(JSONException e){
                e.printStackTrace();
            }

            String response = RESTCalls.postJSONToServer(ConfigURLs.URL_REDDIT, jsonObject);
            Log.i(RedditFragment.class.toString(), "Reddit response" + response);
            redditModel.parseRedditData(response);
           /* if(!redditModel.getImage_link().isEmpty()){
                try {
                    redditModel.setImage_bitmap(ImageHandler.decodeUrl(new URL(redditModel.getImage_link())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            Log.i(RedditFragment.class.toString(), response);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!redditModel.getTitle().isEmpty()){
                title.setText(redditModel.getTitle());
                title.setVisibility(View.VISIBLE);
            }
            if(!redditModel.getDescription().isEmpty()){
                description.setText(redditModel.getDescription());
                description.setVisibility(View.VISIBLE);
            }
            if(!redditModel.getPermalink().isEmpty()){
                textview_url.setText(redditModel.getPermalink());
                textview_url.setVisibility(View.VISIBLE);
            }
            if(!redditModel.getImage_link().isEmpty()){
                imageView.setImageBitmap(redditModel.getImage_bitmap());
            }


        }
    }

}
