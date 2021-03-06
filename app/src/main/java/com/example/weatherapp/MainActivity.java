package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue  mQueue;
    EditText editText;
    TextView weatherDescription;
    TextView  weatherTemperature;
    ImageView imageView;
    TextView extraInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  mQueue=Volley.newRequestQueue(getApplicationContext());

    }

    public void onClick(View view) {
           editText=findViewById(R.id.etCtyName);
          String city= editText.getText().toString();
        String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=2bf3c3adbfef9b7eca6a9c3cc532e23c";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject  object= response.getJSONObject("main");
                    String x=object.getString("temp");
                    imageView=findViewById(R.id.imgWeatherIcon);
                    Log.d("check",x);

                    JSONArray arr = response.getJSONArray("weather");
                    JSONObject obj = (JSONObject) arr.get(0);
                    setIcon(Integer.parseInt(obj.getString("id")), imageView);
                    weatherTemperature=findViewById(R.id.txtWeatherTemperature);
                    weatherTemperature.setText("Temperature: " +
                            response.getJSONObject("main").getString("temp"));
                    weatherDescription=findViewById(R.id.txtWeatherDescription);
                    weatherDescription.setText(obj.getString("description"));
                    extraInfo =findViewById(R.id.extraInfo);
                  JSONObject info=  response.getJSONObject("main");

                   extraInfo.setText("Min. Temp. : "+info.getString("temp_min") +"\n Max. Temp. : "+info.getString("temp_max")+ "\nHumidity : "+info.getString("humidity"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }





    public void setIcon(int weatherId, ImageView icon) {
        if (weatherId >= 200 && weatherId <= 232) {
            icon.setImageResource(R.drawable.thunderstorm);
        } else if (weatherId >= 300 && weatherId <= 321) {
            icon.setImageResource(R.drawable.raindrops);
        } else if (weatherId >= 500 && weatherId <= 531) {
            icon.setImageResource(R.drawable.raindrops);
        } else if (weatherId >= 600 && weatherId <= 622) {
            icon.setImageResource(R.drawable.snow);
        } else if (weatherId >= 700 && weatherId <= 781) {
            icon.setImageResource(R.drawable.refresh);
        } else if (weatherId == 800) {
            icon.setImageResource(R.drawable.nightclear);


        } else if (weatherId >= 801 && weatherId <= 804) {
            icon.setImageResource(R.drawable.cloud);
        }
    }
}