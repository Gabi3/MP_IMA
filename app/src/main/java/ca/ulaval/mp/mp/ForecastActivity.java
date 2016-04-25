package ca.ulaval.mp.mp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ForecastActivity extends AppCompatActivity {


    private ListView myListView;
    private Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        final List<Weather> myList = WeatherAPI.getWeatherList(); // we get the Weather forecast frome the API

        myListView = (ListView) findViewById(R.id.mainListView);

        Adapter adapter = new Adapter(this, R.layout.list_iteme, myList);
        myListView.setAdapter(adapter);


        /**
         * pas obligatoir mais surtment facile a faire de metre une activity pareile a la Main qui
         * montre les vetementa mettre pour la journee
         */

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Weather foreCast = myList.get(position);
                Intent intentDescription = new Intent(ForecastActivity.this, theWatherClassIGesse.class);
                intentDescription.putExtra("weather", foreCast);
                startActivity(intentDescription);
            }
        });


    }





}
