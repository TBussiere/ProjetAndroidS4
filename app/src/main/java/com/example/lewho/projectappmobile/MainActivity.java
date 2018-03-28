package com.example.lewho.projectappmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button btnReload;
    ListView lv;
    StringBuilder sb;
    StationAdapter sA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.tarace);
        btnReload = findViewById(R.id.button);
        lv = findViewById(R.id.listView);
        //sb = new StringBuilder(50000);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyAsyncTask().execute("https://api.jcdecaux.com/vls/v1/stations?contract=lyon&apiKey=84ff98c205f198f6656c735b00e097fc7a47a1a2",sA,getApplicationContext(),lv);
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.deferNotifyDataSetChanged();
            }
        });

    }


}
