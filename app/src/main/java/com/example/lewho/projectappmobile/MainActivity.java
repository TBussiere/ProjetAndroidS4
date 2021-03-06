package com.example.lewho.projectappmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//import android.support.v7.//SearchView;

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
        Intent i = getIntent();
        boolean err = i.getBooleanExtra("error",false);
        if(!err){
            items =  (List<Station>) i.getSerializableExtra("itemscharged");
        }
        else{
            items = getOfflinedatas();
        }

        JodaTimeAndroid.init(this);
        //btn = findViewById(R.id.refresh);
        //btnReload = findViewById(R.id.button);
        lv = findViewById(R.id.listView);
        sA = new StationAdapter(getApplicationContext(),items);
        lv.setAdapter(sA);
        //searchView = findViewById(R.id.action_search);
        //sb = new StringBuilder(50000);
        //new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",getApplicationContext(),sA,items,"");

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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on rÃ©cupÃ¨re la HashMap contenant les infos de notre item (titre, description, img)
                Station station = (Station) lv.getItemAtPosition(i);

                Intent myIntent = new Intent(MainActivity.this, MapActivity.class);
//                myIntent.putExtra("clef", "test ");
                System.out.println("************************** " + station.getPosition().getLat() + "   " + station.getPosition().getLng());
                myIntent.putExtra("lat", station.getPosition().getLat());
                myIntent.putExtra("lng", station.getPosition().getLng());
                myIntent.putExtra("nom", station.getName());

                startActivity(myIntent);
            }
        });


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
                items = getOfflinedatas();
                sA.getFilter().filter(query);
                new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",getApplicationContext(),sA,items,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",sA,items,newText);
                if(newText.equals("")){
                    sA.getFilter().filter("");
                    items = getOfflinedatas();
                    sA.notifyDataSetChanged();
                    new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",getApplicationContext(),sA,items,"");
                }
                else {
                    sA.getFilter().filter(newText);
                    sA.notifyDataSetChanged();
                }


                return true;
            }
        });

        MenuItem mi = menu.findItem(R.id.refreshmenu);

        mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                items = getOfflinedatas();
                sA.notifyDataSetChanged();
                new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",getApplicationContext(),sA,items,"");
                return true;
            }
        });

        return true;
    }

    @Override
    protected void onStop() {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("offline2.dat",MODE_PRIVATE);//new FileOutputStream("offline.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            System.out.println("Done Saving");
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }
    List<Station> getOfflinedatas(){
        try {
            FileInputStream fis = openFileInput("offline2.dat");//new FileInputStream("offline.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            //System.out.println(ois.readObject());
            return (List<Station>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ici");
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("ou ici");
            return new ArrayList<>();
        }
    }
}
