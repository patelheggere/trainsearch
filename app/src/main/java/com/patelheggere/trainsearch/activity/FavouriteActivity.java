package com.patelheggere.trainsearch.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.patelheggere.trainsearch.R;
import com.patelheggere.trainsearch.adapter.FavouriteAdpter;
import com.patelheggere.trainsearch.data.DBHelper;
import com.patelheggere.trainsearch.models.FromTo;
import com.patelheggere.trainsearch.models.Location;
import com.patelheggere.trainsearch.models.Prognosis;
import com.patelheggere.trainsearch.models.Station;
import com.patelheggere.trainsearch.models.coordinate;
import com.patelheggere.trainsearch.models.from;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FavouriteAdpter mFavouriteAdapter;
    private List<FromTo> mFromToList;
    private Gson mGson;
    private DBHelper mDbHelper;
    private SQLiteDatabase dbWrite,dbRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        initialize();
    }
    private void initialize()
    {
        mFromToList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.rvfavourite);
        mFavouriteAdapter = new FavouriteAdpter(this,mFromToList);
        mRecyclerView.setAdapter(mFavouriteAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getFavouriteList();
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

            FromTo fromTo = new FromTo();

            fromTo.setFrom(fromObj);


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

            fromTo.setTo(toObj);
            mFromToList.add(fromTo);
            mFavouriteAdapter.notifyDataSetChanged();

            System.out.println("from:"+fromObj.getDepartureTimestamp()+"\n station:"+fromObj.getStation().getName()+"\n Coordinate:"+fromObj.getCoordinate1().getType()+"\n Prgnosis:"+fromObj.getPrognosis().getDeparture());
            System.out.println("To:"+toObj.getArrivalTimestamp()+"\n station:"+toObj.getStation().getName()+"\n Coordinate:"+toObj.getCoordinate1().getType()+"\n Prgnosis:"+toObj.getPrognosis().getDeparture());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
