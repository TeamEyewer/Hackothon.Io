package ranger.eyewer.com.rangerapp.Main.Fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import ranger.eyewer.com.rangerapp.R;

public class Fragment_InfoView extends Fragment {

    TextView title, desc, mylocation, telephone, fire_title, flood_title, police_title, hospital_title;
    LocationManager mLocationManager;
    RelativeLayout fire_call, flood_call, police_call, hospital_call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment__info_view, container, false);
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    return true;
                }
                return false;
            }
        });

        title = (TextView) rootView.findViewById(R.id.title);
        desc = (TextView) rootView.findViewById(R.id.desc);
        mylocation = (TextView) rootView.findViewById(R.id.mylocation);
        telephone = (TextView) rootView.findViewById(R.id.telephone);
        fire_title = (TextView) rootView.findViewById(R.id.fire_title);
        flood_title = (TextView) rootView.findViewById(R.id.flood_title);
        police_title = (TextView) rootView.findViewById(R.id.police_title);
        hospital_title = (TextView) rootView.findViewById(R.id.hospital_title);
        fire_call = (RelativeLayout) rootView.findViewById(R.id.fire_call);
        flood_call = (RelativeLayout) rootView.findViewById(R.id.flood_call);
        police_call = (RelativeLayout) rootView.findViewById(R.id.police_call);
        hospital_call = (RelativeLayout) rootView.findViewById(R.id.hospital_call);

        Typeface face_light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/robotolight.ttf");

        title.setTypeface(face_light);
        desc.setTypeface(face_light);
        mylocation.setTypeface(face_light);
        telephone.setTypeface(face_light);
        fire_title.setTypeface(face_light);
        flood_title.setTypeface(face_light);
        police_title.setTypeface(face_light);
        hospital_title.setTypeface(face_light);

        try {
            mLocationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        } catch (Exception ex) {
            Log.d("EYERROR", "At requestLocationUpdates: " + ex.toString());
        }

        fire_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + "011-2422222"));
                startActivity(intent);
            }
        });

        flood_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + "118"));
                startActivity(intent);
            }
        });

        police_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + "119"));
                startActivity(intent);
            }
        });

        hospital_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + "011-2691111"));
                startActivity(intent);
            }
        });

        return rootView;
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            try {
                Toast.makeText(getActivity(), "Changed", Toast.LENGTH_LONG).show();
                String address = GetAddressFromMap(location.getLatitude(), location.getLongitude());
                mylocation.setText(address);
            } catch (Exception ex) {
                Log.d("EYERROR", "At LocationListener: " + ex.toString());
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private String GetAddressFromMap(double latitude, double longitude) {

        String address = "";

        try {
            Geocoder geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {

            } else {
                if (addresses.size() > 0) {
                    address = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }

        return address;
    }

}


