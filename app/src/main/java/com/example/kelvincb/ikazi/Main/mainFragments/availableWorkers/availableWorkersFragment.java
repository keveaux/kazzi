package com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers.mLocation.LocationTrack;
import com.example.kelvincb.ikazi.NoInternetPackage.NoInternet;
import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.Main.mainFragments.availableWorkers.mLocation.autoCompleteLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class availableWorkersFragment extends Fragment {

    View view;
    private static String SITE_URL = "";
    TextView locationtxt;
    double lon;
    double lat;

    public int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    String occupationtxt;
    FloatingActionButton fab;

    String latitude;
    String longitude;

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
     NoInternet noInternet ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        noInternet= new NoInternet();

        occupationtxt = getArguments().getString("occupation");


        SITE_URL = "http://104.248.124.210/android/iKazi/phpFiles/workerdetails.php?occupation=" + occupationtxt;


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_available_workers, container, false);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), autoCompleteLocation.class);
                intent.putExtra("occupation", occupationtxt);
                startActivity(intent);
            }
        });

        locationtxt = view.findViewById(R.id.location);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Bold.ttf");
        locationtxt.setTypeface(font);

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(getContext(), "please turn on location permission", Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }
        }


        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }



        switch (getArguments().getString("EXTRA")) {
            case "first":

                NoInternet noInternet=new NoInternet();
                if(noInternet.isConnected(getActivity())){

                    getmyLocation();
                    }else {
                    noInternet.builddialog(getActivity());
                }
                break;
            case "second":

                Bundle bundle = getArguments();
                if (bundle != null) {
                    latitude = bundle.getString("lat");
                    longitude = bundle.getString("lon");

                    lat = Double.parseDouble(latitude);
                    lon = Double.parseDouble(longitude);
                    String streetName = streetName(lat, lon);

                    locationtxt.setText(streetName);
                }
                break;
        }

                fetchWorkers();


        return view;
    }

    @Override
    public void onResume() {

        switch (getArguments().getString("EXTRA")) {

            case "second":

                Bundle bundle = getArguments();
                if (bundle != null) {
                    latitude = bundle.getString("lat");
                    longitude = bundle.getString("lon");

                    lat = Double.parseDouble(latitude);
                    lon = Double.parseDouble(longitude);
                    String streetName = streetName(lat, lon);

                    locationtxt.setText(streetName);
                }
                break;
                default:
                    NoInternet noInternet=new NoInternet();
                    if(noInternet.isConnected(getActivity())){

                        getmyLocation();
                    }else {
                        noInternet.builddialog(getActivity());
                    }
                    break;
        }

        fetchWorkers();

        super.onResume();
    }

    public void fetchWorkers() {
        if (getActivity() != null) {
// Code goes here.


            final ProgressBar myProgressBar = view.findViewById(R.id.myprogressbar);
            myProgressBar.setVisibility(View.VISIBLE);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myProgressBar.setVisibility(View.GONE);

                    //check for internet connection

                    if(getActivity()!=null){

                    if (!isConnected(getActivity())) {


                        ImageView noworker = view.findViewById(R.id.noworker);
                        noworker.setVisibility(View.VISIBLE);

                        TextView noworkertxt = view.findViewById(R.id.noworkertxt);
                        noworkertxt.setVisibility(View.VISIBLE);
                        noworkertxt.setText("TURN ON YOUR INTERNET CONNECTION");

                        noInternet.builddialog(getActivity()).show();

                    } else {
                        final ListView lv = view.findViewById(R.id.myListView);


                        final ImageView noworker = view.findViewById(R.id.noworker);

                        final TextView noworkertxt = view.findViewById(R.id.noworkertxt);


                        Double mlatitude = Double.valueOf(lat);
                        Double mlongitude = Double.valueOf(lon);

                        if (mlatitude == 0.0) {
                            locationtxt.setText("cannot find location \n press button below to set location");
                        } else {
                            new JsonDownloader(getActivity()).retrieveWorkerInfo(SITE_URL, lv, myProgressBar, noworker, noworkertxt, mlongitude, mlatitude);
                        }

                    }}

                }
            }, 2000);


        }
    }


    public String streetName(double lat, double lon) {

        String curStreet = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                curStreet = addressList.get(0).getAddressLine(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curStreet;
    }



    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }




    public void getmyLocation(){


                locationTrack = new LocationTrack(getActivity());


                if (locationTrack.canGetLocation()) {


                    lon = locationTrack.getLongitude();
                    lat = locationTrack.getLatitude();

                    String streetName = streetName(locationTrack.getLatitude(), locationTrack.getLongitude());

                    locationtxt.setText(streetName);
                } else {

                    locationTrack.showSettingsAlert();
                }


    }

    public boolean isConnected(Context context){


        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();

        if(networkInfo!=null&&networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile!=null && mobile.isConnectedOrConnecting()) || (wifi!=null && wifi.isConnectedOrConnecting())){
                return true;
            }else return false;



        }else
            return false;

    }
}
