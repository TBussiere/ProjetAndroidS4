package com.example.lewho.projectappmobile;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Lewho on 16/03/2018.
 */

public class MyAsyncTask extends AsyncTask<Object, Void, String> {

    TextView tv;
    StringBuilder sb;
    ListView lv;
    StationAdapter sa;
    Context context;

    boolean isListView;

    @Override
    protected String doInBackground(Object... param) {
        String test = (String)param[0];
        isListView = true;

        if (param[1] instanceof TextView){
            tv = (TextView) param[1];
            isListView = false;
        }else{
            lv = (ListView) param[1];
            //sa = (StationAdapteur) param[2];
            context = (Context) param[2];
        }

        BufferedReader in;

        URL url = null;
        try {
            url = new URL(test);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //URL url = new URL()
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();

        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream() ) );
            //Log.d ("Async" , in.readLine()); // ou boucle tant qu’il y a des lignes
            String res = "";
            if (isListView) {
                String next;
                int i = 1;
                while ((next = in.readLine()) != null) {
                        sb.append(next);
                }
                in.close();
                List<Station> result = parceJSON(sb);
                sa = new StationAdapter(context,result);
                lv.setAdapter(sa);
                return "Oui";
            }
            else {
                //String res = in.readLine();
                in.close(); // et on ferme le flux
                return res;
            }
        }
        urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private List<Station> parceJSON(StringBuilder sb) {
        JSONObject jsonResponse;
        List<Station> res = new ArrayList<>();

        try {
            jsonResponse = new JSONObject(sb.toString());
            /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
            /*******  Returns null otherwise.  *******/
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");

            /*********** Process each JSON Node ************/

            int lengthJsonArr = jsonMainNode.length();

            for(int i=0; i < lengthJsonArr; i++)
            {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                Station temp = new Station(Integer.parseInt(jsonChildNode.getString("number").toString()),
                        jsonChildNode.getString("name"),
                        jsonChildNode.getString("address"),
                        new Position(Float.parseFloat(jsonChildNode.getString("lat")),Float.parseFloat(jsonChildNode.getString("lng"))),
                        Boolean.parseBoolean(jsonChildNode.getString("banking")),
                        Boolean.parseBoolean(jsonChildNode.getString("bonus")),
                        jsonChildNode.getString("status"),
                        jsonChildNode.getString("contract_name"),
                        Integer.parseInt(jsonChildNode.getString("bike_stands")),
                        Integer.parseInt(jsonChildNode.getString("available_bike_stands")),
                        Integer.parseInt(jsonChildNode.getString("available_bikes")));

                res.add(temp);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public void onPostExecute(String result){
        if (isListView){
            //wv.loadData(result, "text/html; charset=utf-8", "UTF-8");
        }else {
            tv.setText(result);
        }
    }
}
