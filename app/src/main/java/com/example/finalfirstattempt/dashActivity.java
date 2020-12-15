package com.example.finalfirstattempt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dashActivity extends Activity {
    private Button logout;
    private TextView viewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        logout = findViewById(R.id.logout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(dashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
       final String API_KEY ="xJcJfLmyxETtypjNNHdbOJofUdg2fYIILyw2O4Hr7RIvOYPZOo_oLTSn7gM2RN6z4W3B8rpDagfFaknLCY4KPewmr6YmmyGHivHComZJMjIQBoCY_kFx-Or74FnRX3Yx";
        viewResult = findViewById(R.id.text_view_result);
        String URL = "https://api.yelp.com/v3/businesses/search?term=delis&latitude=37.786882&longitude=-122.399972";
        RequestQueue queue;
        queue = Volley.newRequestQueue(this);
        JSONObject parameters = new JSONObject();
        try{
            parameters.put("Bearer", API_KEY);
        } catch (JSONException e) {e.printStackTrace();}
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("onResponse", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("businesses");
                    for (int i = 0; i<jsonArray.length();i++){
                        JSONObject business = jsonArray.getJSONObject(i);
                        String name = business.getString("name");
                        int rating = business.getInt("rating");
                        viewResult.append(name+ ", " + String.valueOf(rating));
                    }
                }

             catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("error",error.toString());
        }
    }) {
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();

            headers.put("Authorization", "Bearer " + API_KEY);
            return headers;
        }
    };
                queue.add(request);
    }
}
