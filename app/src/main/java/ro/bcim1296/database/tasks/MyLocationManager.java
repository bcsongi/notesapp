package ro.bcim1296.database.tasks;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import ro.bcim1296.ActivityMain;
import ro.bcim1296.database.NoteDatabaseLoader;

public class MyLocationManager implements LocationListener {

    private LocationManager lm;
    private Activity activity;
    private IMyLocationManager listener;

    private Double longitude;
    private Double latitude;

    public MyLocationManager(Activity activity, NoteDatabaseLoader dl) {
        this.activity = activity;
        listener = (IMyLocationManager) dl;
        lm = (LocationManager) activity.getSystemService(ActivityMain.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    public void onLocationChanged(Location location) {
        latitude = location.getLongitude();
        longitude = location.getLatitude();
        lm.removeUpdates(this);
        listener.onChangeLocation(latitude, longitude);
    }

    public void onStatusChanged(String s, int i, Bundle bundle) {
        // TODO
    }

    public void onProviderEnabled(String s) {
        // TODO
    }

    public void onProviderDisabled(String s) {
        // TODO
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public interface IMyLocationManager {
        public void onChangeLocation(Double latitude, Double longitude);
    }

}
