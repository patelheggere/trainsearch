package com.patelheggere.trainsearch.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.patelheggere.trainsearch.AppContoller;
import com.patelheggere.trainsearch.R;
import com.patelheggere.trainsearch.data.DBHelper;
import com.patelheggere.trainsearch.models.Location;
import com.patelheggere.trainsearch.models.Prognosis;
import com.patelheggere.trainsearch.models.Station;
import com.patelheggere.trainsearch.models.coordinate;
import com.patelheggere.trainsearch.models.from;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String url = "http://transport.opendata.ch/v1/connections?";
    private EditText mEtFrom, mEtTo;
    private Button mSubmit, mFavourite, mGetFav;
    private TextView mTvDepart, mTvArr;
    private Gson mGson;
    private DBHelper mDbHelper;
    private SQLiteDatabase dbWrite,dbRead;
    private String ConnectionResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize()
    {
        mEtFrom = findViewById(R.id.etFrom);
        mEtTo = findViewById(R.id.etTo);
        mSubmit = findViewById(R.id.submit);
        mTvArr = findViewById(R.id.tvArrival);
        mTvDepart = findViewById(R.id.tvDepart);
        mFavourite = findViewById(R.id.Favorite);
        mGetFav = findViewById(R.id.getfav);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSearch();
            }
        });
        mFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavouriteList();
            }
        });

        mGetFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), FavouriteActivity.class);
                startActivity(intent);
            }
        });
    }
    private void callSearch()
    {
        if((mEtFrom.getText().toString()!=null && mEtTo.getText().toString()!=null) || (mEtFrom.getText().toString()!="" && mEtTo.getText().toString()!="") )
        {
            callNetWorkOperation();
        }
        else {
          Toast.makeText(this, "From or To address cann't be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void callNetWorkOperation()
    {
        mGson =  new Gson();

        //JsonObjectRequest jsonObjectRequest = new

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url + "from=" + mEtFrom.getText().toString() + "&to=" + mEtTo.getText().toString(), null,
                new Response.Listener<JSONObject>()
                {
            @Override
            public void onResponse(JSONObject response) {
                ConnectionResult = response.toString();
                System.out.println("ConnectionRes:"+ConnectionResult);
                from fromObj = new from();
                from toObj = new from();
                coordinate coordinate1 = new coordinate();
                Prognosis prognosis = new Prognosis();
                Station station = new Station();
                Location location = new Location();
                try {
                    fromObj = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").toString(), from.class);
                    //obj.setDeparture(response.getJSONArray("connections").getJSONObject(0).getString("departure"));
                    prognosis = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").getJSONObject("prognosis").toString(), Prognosis.class);
                    coordinate1 = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").getJSONObject("station").getJSONObject("coordinate").toString(), coordinate.class);
                    station = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").getJSONObject("station").toString(), Station.class);
                    location = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").getJSONObject("location").toString(), Location.class);
                    location.setLocCoordinate(coordinate1);
                    fromObj.setCoordinate1(coordinate1);
                    fromObj.setStation(station);
                    fromObj.setPrognosis(prognosis);
                    fromObj.setLocation(location);


                    toObj = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").toString(), from.class);
                    //obj.setDeparture(response.getJSONArray("connections").getJSONObject(0).getString("departure"));
                    prognosis = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").getJSONObject("prognosis").toString(), Prognosis.class);
                    coordinate1 = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").getJSONObject("station").getJSONObject("coordinate").toString(), coordinate.class);
                    station = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").getJSONObject("station").toString(), Station.class);
                    location = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").getJSONObject("location").toString(), Location.class);
                    location.setLocCoordinate(coordinate1);
                    toObj.setCoordinate1(coordinate1);
                    toObj.setStation(station);
                    toObj.setPrognosis(prognosis);
                    toObj.setLocation(location);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mTvDepart.setText("Platform:"+fromObj.getPlatform()+"\n Station Name:"+fromObj.getStation().getName()+"\n Departure Time:"+TimeStampToDate(fromObj.getDepartureTimestamp()));
                mTvArr.setText("Platform:"+toObj.getPlatform()+"\n Station Name:"+toObj.getStation().getName()+"\n Arrival Time:"+TimeStampToDate(toObj.getArrivalTimestamp()));
               // System.out.println("from:"+fromObj.getDepartureTimestamp()+"\n station:"+fromObj.getStation().getName()+"\n Coordinate:"+fromObj.getCoordinate1().getType()+"\n Prgnosis:"+fromObj.getPrognosis().getDeparture());
                //System.out.println("To:"+toObj.getArrivalTimestamp()+"\n station:"+toObj.getStation().getName()+"\n Coordinate:"+toObj.getCoordinate1().getType()+"\n Prgnosis:"+toObj.getPrognosis().getDeparture());

            }
        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
      //RequestQueue requestQueue = Volley.newRequestQueue(this);
      //requestQueue.add(jsonArrayRequest);

        AppContoller.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    private String TimeStampToDate(Long ts)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(ts);
    }


    private void addToFavouriteList()
    {
        mDbHelper = new DBHelper(this);
        dbWrite = mDbHelper.getWritableDatabase();
        if(ConnectionResult!=null)
        DBHelper.writeToSQLDB(dbWrite, ConnectionResult);
        dbWrite.close();
    }

    private String getFavouriteList()
    {
        List<String> connectionList= new ArrayList<>();
        mDbHelper = new DBHelper(this);
        dbRead = mDbHelper.getReadableDatabase();
        connectionList = DBHelper.readFromSQLDB(dbRead);
        dbRead.close();
        for (int i = 0; i<connectionList.size(); i++)
        {
            try {
                parseJsonObject(new JSONObject(connectionList.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private void parseJsonObject(JSONObject response)
    {
        mGson = new Gson();
        from fromObj = new from();
        from toObj = new from();
        coordinate coordinate1 = new coordinate();
        Prognosis prognosis = new Prognosis();
        Station station = new Station();
        Location location = new Location();
        try {
            fromObj = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").toString(), from.class);
            //obj.setDeparture(response.getJSONArray("connections").getJSONObject(0).getString("departure"));
            prognosis = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").getJSONObject("prognosis").toString(), Prognosis.class);
            coordinate1 = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").getJSONObject("station").getJSONObject("coordinate").toString(), coordinate.class);
            station = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").getJSONObject("station").toString(), Station.class);
            location = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("from").getJSONObject("location").toString(), Location.class);
            location.setLocCoordinate(coordinate1);

            fromObj.setCoordinate1(coordinate1);
            fromObj.setStation(station);
            fromObj.setPrognosis(prognosis);


            toObj = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").toString(), from.class);
            //obj.setDeparture(response.getJSONArray("connections").getJSONObject(0).getString("departure"));
            prognosis = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").getJSONObject("prognosis").toString(), Prognosis.class);
            coordinate1 = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").getJSONObject("station").getJSONObject("coordinate").toString(), coordinate.class);
            station = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").getJSONObject("station").toString(), Station.class);
            location = mGson.fromJson(response.getJSONArray("connections").getJSONObject(0).getJSONObject("to").getJSONObject("location").toString(), Location.class);
            location.setLocCoordinate(coordinate1);
            toObj.setCoordinate1(coordinate1);
            toObj.setStation(station);
            toObj.setPrognosis(prognosis);


            System.out.println("from:"+fromObj.getDepartureTimestamp()+"\n station:"+fromObj.getStation().getName()+"\n Coordinate:"+fromObj.getCoordinate1().getType()+"\n Prgnosis:"+fromObj.getPrognosis().getDeparture());
            System.out.println("To:"+toObj.getArrivalTimestamp()+"\n station:"+toObj.getStation().getName()+"\n Coordinate:"+toObj.getCoordinate1().getType()+"\n Prgnosis:"+toObj.getPrognosis().getDeparture());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
