package ca.ulaval.mp.mp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gabriel_2 on 25-Apr-16.
 */
public class PrevisionActivity  extends ListActivity {

    private String body;
    private Button mToday;
    private Button mForecast;
    private JsonNode jNode;
    private JSONObject jObj;


    private JSONObject query;
    private JSONObject results;
    private JSONObject channel;
    private JSONObject item;
    private JSONArray foreCast;

    private ArrayList<JSONObject> mFocastList;

    private ListView myListView;

    private WeatherAdapter mWeatherAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevision);
        new ForeCast().execute();
        mWeatherAdapter = new WeatherAdapter(this, new ArrayList<Weather>());
        getListView().setAdapter(mWeatherAdapter);



    }

    private int test;

    private class ForeCast extends AsyncTask<String,Integer, HttpResponse<JsonNode>> {

        protected HttpResponse<JsonNode> doInBackground(String... msg) {

            HttpResponse<JsonNode> request = null;
            try {
                request = Unirest.get("https://simple-weather.p.mashape.com/weatherdata?lat=46.8&lng=-71.3")
                        .header("X-Mashape-Key", "xn55jeUBg0mshwmZSIrB2dNbudabp1xomN8jsnXryHN4ARtYyo")
                        .header("Accept", "application/json")
                        .asJson();
            } catch (UnirestException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return request;
        }

        protected void onProgressUpdate(Integer... integers) {

        }

        protected void onPostExecute(HttpResponse<JsonNode> response) {
            jNode = response.getBody();
            jObj = jNode.getObject();
            try {
                query = jObj.getJSONObject("query");
                results = query.getJSONObject("results");
                channel = results.getJSONObject("channel");
                item = channel.getJSONObject("item");
                foreCast = item.getJSONArray("forecast");
                for (int i=0;i<foreCast.length();i++){
                    JSONObject obj = foreCast.getJSONObject(i);
                    Log.d("carretail", "OBJECT " + obj);
                    mWeatherAdapter.addWeather(obj);
                }
                mWeatherAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


    static public class WeatherAdapter extends ArrayAdapter<Weather> {

        private ArrayList<Weather> mWeatherList;
        private Activity mActivity;

        public WeatherAdapter(Activity inActivity, ArrayList<Weather> inModelList){
            super(inActivity, R.layout.list_iteme, inModelList);
            this.mActivity = inActivity;
            mWeatherList=inModelList;

        }

        public void addWeather(JSONObject inJson){
            Weather outItem = new Weather(inJson);
            mWeatherList.add(outItem);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mWeatherList.size();
        }

        @Override
        public Weather getItem(int arg0) {
            // TODO Auto-generated method stub
            return mWeatherList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        static class ViewHolder {
            public TextView sky;
            public TextView min;
            public TextView max;
            public TextView date;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {

            View childView = arg1;
            if(childView == null || childView.getTag() == null){

                childView = mActivity.getLayoutInflater().inflate(R.layout.list_iteme, null);

                ViewHolder viewHolder = new ViewHolder();

                viewHolder.sky = (TextView) childView.findViewById(R.id.tvSky);
                viewHolder.min = (TextView) childView.findViewById(R.id.tvMin);
                viewHolder.max = (TextView) childView.findViewById(R.id.tvMax);
                viewHolder.date = (TextView) childView.findViewById(R.id.tvDate);

                childView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) childView.getTag();
            holder.sky.setText(mWeatherList.get(arg0).getSky());
            holder.max.setText("max:"+mWeatherList.get(arg0).getMax().toString()+" °C");
            holder.min.setText("min:"+mWeatherList.get(arg0).getMin().toString()+" °C");
            holder.date.setText(mWeatherList.get(arg0).getSDate());
            return childView;
        }

    }
}