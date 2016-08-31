package com.example.neild_000.happinessjar.RedundantCode.Card;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.RedundantCode.Drawer.NavigationDrawerFragment;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.RESTCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by neild_000 on 01/03/2015.
 */
public class CardSelectorActivity  extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

  //  private int[] card_ids = {45,46,47,48,49,50,51};
    private String user_id = "1" ;

    private List<String> card_ids_list = new ArrayList<String>();


    public static final int progress_bar_type = 0;


    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if( extras !=null ){
            user_id = extras.getString("user_id");
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        getCardIDs(null);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.card_grid_layout, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((CardSelectorActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void ProcessFinished(){

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new CardSelectorAdapter( this, card_ids_list.size() ));



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                intent.putExtra( "card_id",card_ids_list.get(position) );
                intent.putExtra( "user_id", user_id );
                Log.i(CardSelectorActivity.class.toString(), "card id: " + card_ids_list.get(position) + user_id);
                startActivity(intent);

            }
        });
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

    public void getCardIDs(View view){
      //  progressBar.setVisibility(View.VISIBLE);
      new GetCardIDs().execute();
    }

    private class GetCardIDs extends AsyncTask< Void, Void, Void >{



        @Override
        protected Void doInBackground(Void... params) {
            String response = RESTCalls.getJSONFromServer( ConfigURLs.URL_GET_CARD_IDS_FROM_SERVER + user_id );

            parseJSONArrayToString(response);
            Collections.shuffle(card_ids_list);

            Log.i(CardSelectorActivity.class.toString(), response);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ProcessFinished();
        }
    }

}
