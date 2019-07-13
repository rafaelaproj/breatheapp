package rafaelacs.com.br.breatheapp.util;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Activity activity){
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setDate(long milliseconds){
        preferences.edit().putLong("seconds", milliseconds).apply();
    }

    public String getDate(){
        long milliDate = preferences.getLong("seconds", 0);
        String amOrPm;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliDate);
        int a = calendar.get(Calendar.AM_PM);

        if(a == Calendar.AM){
            amOrPm = "AM";
        } else {
           amOrPm = "PM";
        }

        String time = "Last " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                calendar.get(Calendar.MINUTE) + " " + amOrPm;

        return time;
    }

    public void setSessions(int session){
        preferences.edit().putInt("sessions", session).apply();
    }

    public int getSessions(){
        return preferences.getInt("sessions", 0);
    }

    public void setBreathes(int breathes){
        preferences.edit().putInt("breathes", breathes).apply();  //saving our breathes into system file
    }

    public int getBreathes(){
        return preferences.getInt("breathes", 0);
    }

}
