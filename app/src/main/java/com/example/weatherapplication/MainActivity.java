package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText EditText_City;
    TextView TextView_Temp;
    TextView TextView_Humid;
    TextView TextView_WindSpeed;
    TextView TextView_Country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText_City = findViewById(R.id.city_name);
        TextView_Temp = findViewById(R.id.temp_textview);
        TextView_Humid = findViewById(R.id.humid_textview);
        TextView_WindSpeed = findViewById(R.id.windspeed_textview);
        TextView_Country = findViewById(R.id.country_textview);

    }
    public void get(View v){
        String apikey="9c9bd4ce12a307cf4799bbdba015746f";
        String city=EditText_City.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //temperature
                    JSONObject object = response.getJSONObject("main");
                    String temperature = object.getString("temp");
                    Double temp=Double.parseDouble(temperature)-273.15;
                    TextView_Temp.setText("Temperature: "+temp.toString().substring(0,5)+" C");
                    //Humidity
                    String humidity = object.getString("humidity");
                    TextView_Humid.setText("Humidity: "+humidity);
                    //windspeed
                    JSONObject object1 = response.getJSONObject("wind");
                    String windspeed = object1.getString("speed");
                    TextView_WindSpeed.setText("Wind Speed: "+windspeed);
                    //country
                    JSONObject object2 = response.getJSONObject("sys");
                    String country = object2.getString("country");
                    TextView_Country.setText("Country: "+country);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Please Try Again", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
}