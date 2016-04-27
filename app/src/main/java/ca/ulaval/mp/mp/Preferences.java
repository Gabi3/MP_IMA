package ca.ulaval.mp.mp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gabriel_2 on 25-Apr-16.
 */
public class Preferences implements Parcelable {
    private String m_temperatureFroid;
    private String m_temperatureChaud;

    public Preferences() {

    }

    public Preferences(Parcel in) {
        readFromParcel(in);
    }

    public void setTemperatureFroid(String temperatureFroid){
        m_temperatureFroid = temperatureFroid;
    }

    public String getTemperatureFroid(){
        return m_temperatureFroid;
    }

    public void setTemperatureChaud(String tempratureChaud){
        m_temperatureChaud = tempratureChaud;
    }

    public String getTemperatureChaud(){
        return m_temperatureChaud;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m_temperatureFroid);
        dest.writeString(m_temperatureChaud);
    }

    private void readFromParcel(Parcel in) {
        m_temperatureFroid = in.readString();
        m_temperatureChaud = in.readString();
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Preferences createFromParcel(Parcel in) {
                    return new Preferences(in);
                }

                public Preferences[] newArray(int size) {
                    return new Preferences[size];
                }
            };

}
