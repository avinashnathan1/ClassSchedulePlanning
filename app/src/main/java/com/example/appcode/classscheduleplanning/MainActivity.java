package com.example.aaronaftab.walkable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.places.ui.*;
import com.google.android.gms.common.*;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.*;
import android.util.Log;
import com.google.android.gms.common.api.*;
import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    //objects to use in the methods below
    EditText className1;
    EditText className2;
    TextView locationStorage;
    TextView locationStorage2;
    String cName1;
    String cName2;
    String loc1;
    String loc2;
    PlaceAutocompleteFragment autocompleteFragment;
    PlaceAutocompleteFragment autocompleteFragment2;
    public String time;
    String id1;
    String id2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GeoDataClient mGeoDataClient;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mGeoDataClient = Places.getGeoDataClient(this, null);

        final String TAG = "Main2Activity";
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment2 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);
        locationStorage = findViewById(R.id.locStorage1);
        locationStorage2 = findViewById(R.id.locStorage2);
        //set hints
        autocompleteFragment.setHint("Enter Location");
        autocompleteFragment2.setHint("Enter Location");
        //set place selection listener
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                locationStorage.setText(place.getAddress());
                id1 = place.getId();
                //Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                locationStorage2.setText(place.getAddress());
                id2 = place.getId();

                //Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        Button button = findViewById(R.id.submitButton);
        className1 = findViewById(R.id.className1);
        className2 = findViewById(R.id.className2);
        button.setOnClickListener(new RelativeLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent backToMain = new Intent(Main2Activity.this, MainActivity.class);
                String[] args = new String[5];
                cName1 = className1.getText().toString();
                cName2 = className2.getText().toString();
                loc1 = locationStorage.getText().toString();
                loc2 = locationStorage2.getText().toString();
                args[0] = cName1;
                args[1] = cName2;
                args[2] = loc1;
                args[3] = loc2;
                args[4] = time;
                //Log.d("got through", args[4]);
                passInfo(Main2Activity.this, args);
                startActivity(backToMain);
            }
        });

    }


    public static void passInfo(Context context, String[] passingArgs) {
        SharedPreferences prefs = context.getSharedPreferences("ClassLoc", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("class1", passingArgs[0]);
        editor.putString("class2", passingArgs[1]);
        editor.putString("location1", passingArgs[2]);
        editor.putString("location2", passingArgs[3]);
        //editor.putString("time", passingArgs[4]);
        editor.apply();
    }


    public void getTime() {
        RequestQueue queue = Volley.newRequestQueue(Main2Activity.this);
        final String TAG = "Main2Activity";
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=place_id:"
                + id1 + "&destinations=place_id:" + id2 + "&mode=walking" + "&key=" + getString(R.string.api_key);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        try {
                            Log.d(TAG, "Got into onResponse");
                            //JSONObject json = new JSONObject(htt);
                            JSONObject duration = response
                                    .getJSONArray("rows")
                                    .getJSONObject(0)
                                    .getJSONArray("elements")
                                    .getJSONObject(0)
                                    .getJSONObject("duration");
                            time = duration.get("text").toString();
                            Log.d(TAG, time);
                            Log.d(TAG, "Finished onResponse");
//                            JSONArray rows = response.getJSONArray("rows");
//                            JSONArray element = rows.getJSONArray(0);
//                            //JSONObject duration = element.getJSONObject(1);

                        } catch (JSONException e) {
                            Log.i("check", "JSON issue");
                            //System.out.println("JSON issue");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.w(TAG, error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }

}
