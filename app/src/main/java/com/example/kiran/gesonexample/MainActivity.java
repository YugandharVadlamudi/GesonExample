package com.example.kiran.gesonexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listViewGeson;
    ArrayList<Gallery> galleryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }

    private void initialization() {
        listViewGeson = (ListView) findViewById(R.id.lv_gson);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Utils.urlJSON, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("response_is", "" + response.getJSONObject("sitter").getJSONObject("response").getString("status"));
                    Log.e("true", "" + Boolean.valueOf(response.getJSONObject("sitter").getJSONObject("response").getString("status")));
                    if ((response.getJSONObject("sitter").getJSONObject("response").getString("status")).equals("success")) {
                        JSONArray array = response.getJSONObject("sitter").getJSONObject("response").getJSONArray("getGallery");
                        for (int i = 0; i < response.getJSONObject("sitter").getJSONObject("response").getJSONArray("getGallery").length(); i++) {
                            Gson gson = new Gson();
                            Log.e("gesonObjectConvertion", "" + gson.fromJson(array.get(i).toString(), Gallery.class));
                            Gallery gallery = gson.fromJson(array.get(i).toString(), Gallery.class);
                            Log.e("idofGeson", "" + gallery.images);
                            galleryArrayList.add(gallery);
                        }
                        ListViewAdapter listViewAdapter=new ListViewAdapter(getApplicationContext(),galleryArrayList);
                        Log.e("adapterCount",""+listViewAdapter.getCount());
                        listViewGeson.setAdapter(listViewAdapter);
                    } else {
                        Log.e("response_error", "" + response.getJSONObject("sitter").getJSONObject("response").getString("status"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error_meassage", "" + error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);
    }
}
