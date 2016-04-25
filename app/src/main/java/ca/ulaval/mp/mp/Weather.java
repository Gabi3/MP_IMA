package ca.ulaval.mp.mp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Pacman92 on 2016-04-24.
 */
public class Weather implements Parcelable {

    private String mSky;
    private Integer mTemperature;
    private Date mDate; // do we need it?


    public Weather() {
    }



    public String getSky(){
        return mSky;
    }

    public Integer getTemperature(){
        return mTemperature;
    }

    public Date getDate(){
        return mDate;
    }

    public void setSky(String inSky) {
        this.mSky = inSky;
    }

    public void setTemperature(Integer inTemperature) {
        this.mTemperature = inTemperature;
    }

    public void setDate(Date inDate) {
        this.mDate = inDate;
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
        this.mTemperature = in.readInt();
        this.mDate = new Date(in.readLong());
    }


    @Override
    public int
    describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSky);
        dest.writeString(this.mTemperature.toString());
        dest.writeLong(this.mDate.getTime());
    }

}
