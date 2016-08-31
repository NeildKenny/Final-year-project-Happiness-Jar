package com.example.neild_000.happinessjar.ThirdPartyRequests;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.RESTCalls;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by neild_000 on 10/03/2015.
 */
public class ThirdPartyMediaFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(ThirdPartyMediaFragment.class.toString(), "In reddit fragmetn create view");
        View rootView = inflater.inflate(R.layout.card_edit_fragment, container, false);
        String provider = "reddit";

        new GetMedia().execute("aww", provider);
        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class GetMedia extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {

            JSONObject jsonObject = new JSONObject();

            try{
                jsonObject.put("subreddit", params[0]);
            }catch(JSONException e){
                e.printStackTrace();
            }

            String response = RESTCalls.postJSONToServer(ConfigURLs.URL_REDDIT, jsonObject);
            Log.i(ThirdPartyMediaFragment.class.toString(), response);

            return null;
        }
    }

}
