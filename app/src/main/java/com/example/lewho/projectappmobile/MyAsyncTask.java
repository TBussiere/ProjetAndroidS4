package com.example.lewho.projectappmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Lewho on 16/03/2018.
 */

public class MyAsyncTask extends AsyncTask<Object, Void, String> {

    TextView tv;
    StringBuilder sb;
    //ListView lv;
    List<Station> listItem;
    StationAdapter sa;
    Context context;
    String filter;
    boolean erreur;


    boolean isListView;
    boolean isInit;

    @Override
    protected String doInBackground(Object... param) {
        String test = (String)param[0];
        isListView = true;
        isInit = false;
        filter = "";
        erreur = false;

        if (param[1] instanceof TextView){
            tv = (TextView) param[1];
            isListView = false;
        }else if (param[1] instanceof Context){
            context = (Context) param[1];
            sa = (StationAdapter) param[2];
            //sa = (StationAdapteur) param[2];
            //context = (Context) param[2];
            //lv = (ListView) param[3];
            listItem = (List<Station>) param[3];
            if (param[3] != null) {
                filter = (String) param[4];
            }
        }else{
            isInit = true;
            isListView = false;
            listItem = new ArrayList<>();
            context = (Context)param[2];
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
            System.out.println(urlConnection.getResponseCode());
            System.out.println(HttpsURLConnection.HTTP_OK);
        if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){//HttpsURLConnection.HTTP_OK){
            in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream() ) );
            //Log.d ("Async" , in.readLine()); // ou boucle tant qu’il y a des lignes
            String res = "";
            if (isListView) {
                String next;
                int i = 1;
                sb = new StringBuilder();
                while ((next = in.readLine()) != null) {
                        sb.append(next);
                }
                in.close();
                listItem.clear();
                listItem = parceJSON(sb);
                System.out.println(listItem.size());
                System.out.println("DONE few more step ...");
                //sa = new StationAdapter(context,result);
                //lv.setAdapter(sa);

                return "Oui";
            }
            else {
                //String res = in.readLine();
                in.close(); // et on ferme le flux
                return res;
            }
        }
        urlConnection.disconnect();
        } catch (UnknownHostException ex){
            erreur = true;
            return "Erreur Pas de connection internet";
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private List<Station> parceJSON(StringBuilder sb) {
        JSONArray jsonResponse;
        List<Station> res = new ArrayList<>();
        //System.out.println(sb.toString());

        try {
            jsonResponse = new JSONArray(sb.toString());
            /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
            /*******  Returns null otherwise.  *******/
            //JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");

            /*********** Process each JSON Node ************/

            int lengthJsonArr = jsonResponse.length();//jsonMainNode.length();

            for(int i=0; i < lengthJsonArr; i++)
            {
                JSONObject jsonChildNode = jsonResponse.getJSONObject(i);
                //int temp = 0;
                JSONObject Pos = jsonChildNode.getJSONObject("position");
                Station temp = new Station(Integer.parseInt(jsonChildNode.getString("number").toString()),
                        jsonChildNode.getString("name"),
                        jsonChildNode.getString("address"),
                        new Position(Float.parseFloat(Pos.getString("lat")),Float.parseFloat(Pos.getString("lng"))),
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
        }catch (Exception e){
            e.fillInStackTrace();
        }

        return res;
    }

    @Override
    public void onPostExecute(String result){
        if (isListView){
            if (erreur){
                System.out.println(result);
                Toast t  = Toast.makeText(context, result, Toast.LENGTH_SHORT);
                t.show();
            }
            //wv.loadData(result, "text/html; charset=utf-8", "UTF-8");
            //lv.setAdapter(sa);
            else {
                    sa.clear();
                    sa.addAll(listItem);
                    sa.getFilter().filter(filter);
                    //sa.notifyDataSetChanged();
            }

        }else if(isInit) {
            launchApp(erreur);
            //finish();
        }
    }
    void launchApp(boolean error){
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("itemscharged", (Serializable) listItem);
        i.putExtra("error",error);
        context.startActivity(i);//startActivity(i);
    }
}
