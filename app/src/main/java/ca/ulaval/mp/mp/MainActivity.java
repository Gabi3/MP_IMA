package ca.ulaval.mp.mp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String lat;
    private String lon;
    private String apiURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat =getLat();
        lon = getLon();//faire les get pour le deux surment devoir créé une class possition/géo
        apiURL = "https://simple-weather.p.mashape.com/weather?lat="+lat+"&lng="+lon

        HttpResponse<String> response = Unirest.get(apiURL) //faudrait implanté la library Unirest, voir http://unirest.io/java
                .header("X-Mashape-Key", "xn55jeUBg0mshwmZSIrB2dNbudabp1xomN8jsnXryHN4ARtYyo")
                .header("Accept", "text/plain")
                .asString();
    }


}
