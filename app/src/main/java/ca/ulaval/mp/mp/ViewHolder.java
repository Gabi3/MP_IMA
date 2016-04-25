package ca.ulaval.mp.mp;

import android.widget.TextView;

/**
 * Created by Pacman92 on 2016-04-24.
 */
public class ViewHolder {

    public TextView sky; // firstName
    public TextView temperature; // lastName
    public TextView date;



    public void setData(Weather weather){


        sky.setText(weather.getSky());
        temperature.setText(weather.getTemperature());
        date.setText(weather.getDate().toString());

    }
}
