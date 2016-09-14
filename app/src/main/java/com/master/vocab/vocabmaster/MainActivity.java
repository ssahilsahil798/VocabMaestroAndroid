package com.master.vocab.vocabmaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.master.vocab.vocabmaster.Activity.WordActivity;
import com.master.vocab.vocabmaster.Adapter.CardAdapter;
import com.master.vocab.vocabmaster.Api.ApiClient;
import com.master.vocab.vocabmaster.Models.Cards;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Cards> listCards;
    private CardAdapter listAdapter;
    private ListView listView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        Menu m = navView.getMenu();
        SubMenu topChannelMenu = m.addSubMenu("Top Channels");
        topChannelMenu.add("Foo");
        topChannelMenu.add("Bar");
        topChannelMenu.add("Baz");
    }

    private void fetchListCards() {

        ApiClient.getFeedApiInterface().getUserCardStatus(new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d("check", s);
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray objs = object.getJSONArray("objects");
                    for(int i=0;i<objs.length();i++){
                        Cards card = new Cards();
                        JSONObject jsonCard = objs.optJSONObject(i);
                        card.CardCategory = jsonCard.optJSONObject("category").optString("category");
                        card.CardCategoryId = jsonCard.optJSONObject("category").optInt("id");
                        card.total_words = jsonCard.optInt("total_words");
                        card.words_completed = jsonCard.optInt("words_completed");
                        listCards.add(card);
                        listAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.hide();
            }

            @Override
            public void failure(RetrofitError error) {
                if(null != getApplicationContext()){
                    Toast.makeText(getApplicationContext(), error.toString() + "", Toast.LENGTH_LONG).show();
                }
                progressDialog.hide();


            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        listCards = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Cards");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        fetchListCards();
        listView = (ListView)findViewById(R.id.cards_recycler_view);
        listAdapter = new CardAdapter(this, listCards);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, WordActivity.class);
                intent.putExtra("card", listCards.get(i).CardCategoryId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuItem item = menu.add("first");
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
