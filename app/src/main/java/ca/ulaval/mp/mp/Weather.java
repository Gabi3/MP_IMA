package ca.ulaval.mp.mp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pacman92 on 2016-04-29.
 */
public class Weather implements Parcelable{

    private String mSky;
    private Integer mMin;
    private Integer mMax;
    private String sDate;


    public Weather() {
    }



    public String getSky(){
        return mSky;
    }



    public Integer getMin() {
        return mMin;
    }

    public Integer getMax() {
        return mMax;
    }

    public String getSDate(){
        return sDate;
    }






    public Weather(JSONObject inObject){
        if(inObject!=null){
            try {
                this.mSky=inObject.getString("text");
                this.mMax=inObject.getInt("high");
                this.mMin=inObject.getInt("low");
                this.sDate=inObject.getString("date");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    public final static
    Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };



    protected Weather(Parcel in) {
        this.mSky = in.readString();
        this.mMin = in.readInt();
        this.mMax = in.readInt();
        this.sDate = in.readString();
    }


    @Override
    public int
    describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSky);
        dest.writeString(this.mMin.toString());
        dest.writeString(this.mMax.toString());;
        dest.writeString(this.sDate);
    }

}
