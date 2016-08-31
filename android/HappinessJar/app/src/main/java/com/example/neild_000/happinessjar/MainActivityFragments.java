package com.example.neild_000.happinessjar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.example.neild_000.happinessjar.CardFragments.CardEditFragment;
import com.example.neild_000.happinessjar.CardFragments.CardNewFragment;
import com.example.neild_000.happinessjar.CardFragments.CardSelectorFragment;
import com.example.neild_000.happinessjar.CardFragments.CardViewFragment;
import com.example.neild_000.happinessjar.Drawer.FragmentNavigationDrawer;
import com.example.neild_000.happinessjar.RedundantCode.MainActivity;
import com.example.neild_000.happinessjar.ThirdPartyRequests.Flickr.FlickrFragment;
import com.example.neild_000.happinessjar.ThirdPartyRequests.Reddit.RedditFragment;

public class MainActivityFragments extends ActionBarActivity implements CardSelectorFragment.onCardSelectedListener, CardViewFragment.onEditButtonClickedListener {
    private FragmentNavigationDrawer dlDrawer;

    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fix);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
         user_id = extras.getString("user_id");
        }
        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);

        // Setup drawer view
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), toolbar,
                R.layout.drawer_nav_item, R.id.flContent);
        // Add nav items
        dlDrawer.addNavItem("New Card", "New card", CardNewFragment.class);
        dlDrawer.addNavItem("Edit Card", "Edit Card", CardEditFragment.class);
        dlDrawer.addNavItem("View Card", "View Card", CardViewFragment.class);
        dlDrawer.addNavItem("Select Card", "Select Card", CardSelectorFragment.class);
        dlDrawer.addNavItem("Reddit", "Reddit", RedditFragment.class);
        dlDrawer.addNavItem("Flickr", "Flickr", FlickrFragment.class);



        // Select default
        if (savedInstanceState == null) {
            dlDrawer.selectDrawerItem(0);
        }
    }

    public void setActionBarTitle(String title){
        dlDrawer.setTitle(title);
    }

    @Override
    public void onCardSelectd(String card_id) {
        Log.i(MainActivityFragments.class.toString(), "in onCardSelected" + card_id);

        CardViewFragment cardViewFragment = new CardViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("card_id", card_id);
        cardViewFragment.setArguments(bundle);
        Log.i(MainActivityFragments.class.toString(), "in card selected" + bundle.getString("card_id"));

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.flContent, cardViewFragment).addToBackStack(null).commit();

    }

    @Override
    public void onEditButtonClick(String card_id) {
        Log.i(MainActivity.class.toString(), "in Edit Button clicked");

        CardEditFragment cardEditFragment = new CardEditFragment();
        Bundle bundle = new Bundle();
        bundle.putString("card_id", card_id);
        cardEditFragment.setArguments(bundle);
        Log.i(MainActivityFragments.class.toString(),"in edit button clicked" + bundle.getString("card_id"));

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.flContent, cardEditFragment).addToBackStack(null).commit();

    }

/*    @Override
    public void onCardSubmited(String card_id) {

    }*/

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dlDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }




}