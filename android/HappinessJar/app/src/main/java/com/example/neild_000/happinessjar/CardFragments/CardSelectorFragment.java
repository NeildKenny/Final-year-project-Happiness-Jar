package com.example.neild_000.happinessjar.CardFragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.RESTCalls;
import com.example.neild_000.happinessjar.RedundantCode.Card.CardSelectorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by neild_000 on 10/03/2015.
 */
public class CardSelectorFragment extends Fragment {


    private onCardSelectedListener listener;
    private String user_id = "1" ;

    private List<String> card_ids_list = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.card_grid_layout,container,  false);

        getCardIDs();

        return view;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof onCardSelectedListener ){
            listener = (onCardSelectedListener) activity;
        }else{
            throw new ClassCastException( activity.toString() + "must implement CardSelected listner");
        }
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
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface onCardSelectedListener{
        public void onCardSelectd(String card_id);
    }

    public void ProcessFinished(){

        GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);
        gridView.setAdapter(new CardSelectorAdapter( getActivity(), card_ids_list.size() ));



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i(CardSelectorFragment.class.toString(), "card id: " + card_ids_list.get(position) + user_id);


                cardCall(card_ids_list.get(position));
            }
        });
    }

    public void cardCall(String card_id){
        listener.onCardSelectd(card_id);
    }



    private void parseJSONArrayToString(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray card_ids_json = jsonObject.getJSONArray("card_ids");
            for(int i=0; i<card_ids_json.length(); i++){
                JSONObject card_ids = card_ids_json.getJSONObject(i);
                card_ids_list.add(i, card_ids.getString("card_id"));
            }



        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void getCardIDs(){
        //  progressBar.setVisibility(View.VISIBLE);
        if(RESTCalls.isNetworkAvailable(getActivity())) {
            new GetCardIDs().execute();
        }else{
            Toast toast = Toast.makeText(getActivity(), "upload complete", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private class GetCardIDs extends AsyncTask< Void, Void, Void > {



        @Override
        protected Void doInBackground(Void... params) {
            String response = RESTCalls.getJSONFromServer(ConfigURLs.URL_GET_CARD_IDS_FROM_SERVER + user_id);

            parseJSONArrayToString(response);
            Collections.shuffle(card_ids_list);

            Log.i(CardSelectorFragment.class.toString(), response);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ProcessFinished();
        }
    }
}
