package com.example.lewho.projectappmobile;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.//SearchView;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //ImageButton btn;
    Button btnReload;
    ListView lv;
    StringBuilder sb;
    StationAdapter sA;
    List<Station> items;
    //SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btn = findViewById(R.id.refresh);
        //btnReload = findViewById(R.id.button);
        lv = findViewById(R.id.listView);
        items = new ArrayList<>();
        sA = new StationAdapter(getApplicationContext(),items);
        lv.setAdapter(sA);
        //searchView = findViewById(R.id.action_search);
        //sb = new StringBuilder(50000);
        new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",sA,items,"");

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",sA,items,"");
            }
        });*/

        /*btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.deferNotifyDataSetChanged();
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        //SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //SearchView searchView = (SearchView) findViewById(R.id.action_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //lv.setFilterText(query);
                sA.getFilter().filter(query);
                new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",sA,items,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",sA,items,newText);
                sA.getFilter().filter(newText);
                sA.notifyDataSetChanged();


                return true;
            }
        });

        MenuItem mi = menu.findItem(R.id.refreshmenu);

        mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",sA,items,"");
                return true;
            }
        });

        return true;
    }


}
