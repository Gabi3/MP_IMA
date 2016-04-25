package ca.ulaval.mp.mp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pacman92 on 2016-04-24.
 */


public class Adapter extends ArrayAdapter<Weather> {


    List<Weather> lstWeather;
    LayoutInflater inflater;


    public Adapter(Context context, int resource, List<Weather> objects) {
        super(context, resource, objects);

        lstWeather = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewholder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_iteme, null);
            viewholder = new ViewHolder();

            viewholder.sky = (TextView) view.findViewById(R.id.tvSky);
            viewholder.temperature = (TextView) view.findViewById(R.id.tvTemperature);
            viewholder.date = (TextView) view.findViewById(R.id.tvDate);


            view.setTag(viewholder);
        }
        else{
            viewholder = (ViewHolder )view.getTag();
        }

        Weather weather = lstWeather.get(position);
        viewholder.setData(weather);

        return view;

    }
}