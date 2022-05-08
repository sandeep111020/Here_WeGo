package com.example.herewego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mapscreen extends AppCompatActivity implements OnMapReadyCallback {

    RecyclerView recyclerView;
    JunctionsAdapter adapter;
    GpsTracker gpsTracker;
    double latitude,longitude;

    private GoogleMap mMap;

    String userid;
    private ArrayList<LatLng> locationArrayList;
    LatLng TamWorth = new LatLng(-31.083332, 150.916672);
    LatLng NewCastle = new LatLng(-32.916668, 151.750000);
    LatLng Brisbane = new LatLng(-27.470125, 153.021072);

    private String temp;
    private List<junctionmodel> exampleList=new ArrayList<>();
    private DatabaseReference databaseRef5;
    //private LottieAnimationView lottieAnimationView;
    private junctionmodel shops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);
        Fragment fragment=new MapFragment();

        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_layout,fragment)
                .commit();


        locationArrayList = new ArrayList<>();
        locationArrayList.add(TamWorth);
        locationArrayList.add(NewCastle);
        locationArrayList.add(Brisbane);
        //lottieAnimationView=findViewById(R.id.lav_actionBar);
        recyclerView = findViewById(R.id.idRVItemsmain);
        userid=getIntent().getStringExtra("id");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker = new GpsTracker(Mapscreen.this);
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();


        }else{
            gpsTracker.showSettingsAlert();
        }
        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Junctions");
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if (snapshot.getChildrenCount() == 0) {


                    //lottieAnimationView.setVisibility(View.VISIBLE);
                    //lottieAnimationView.playAnimation();

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
      /*  FirebaseRecyclerOptions<junctionmodel> options =
                new FirebaseRecyclerOptions.Builder<junctionmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Merchants").child("Profile"), junctionmodel.class)
                        .build();
        adapter = new ShopsAdapter(options,getApplicationContext());*/



        /*    recyclerView.setAdapter(adapter);*/
        fillExampleList();
    }
    private void fillExampleList() {

        List<Double> l = new ArrayList<>();
        List<Double> lcheck = new ArrayList<>();
        List<Double> testl = new ArrayList<>();
        List<Double> lfin = new ArrayList<>();
        databaseRef5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    double temp;
                    String lat=ds.child("lat").getValue().toString();
                    String lon=ds.child("lon").getValue().toString();
                    System.out.println(distance(latitude,longitude,Double.parseDouble(lat),Double.parseDouble(lon),"K"));
                    temp =distance(latitude,longitude,Double.parseDouble(lat),Double.parseDouble(lon),"K");
                    l.add(temp);
                    lcheck.add(temp);
                    testl.add(Double.parseDouble(lat));
                }
                System.out.println(l);
                Collections.sort(l);
                for (int i=0; i<l.size();i++){
                    int pos = lcheck.indexOf(l.get(i));
                    double tt = testl.get(pos);
                    lfin.add(tt);
                }
                System.out.println(l);
                System.out.println(testl);
                System.out.println(lfin+"lfffin");



                System.out.println(dataSnapshot);
                for ( int i =0;i<lfin.size();i++){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        String name=ds.child("name").getValue().toString();
                        String number=ds.child("number").getValue().toString();

                        String lat=ds.child("lat").getValue().toString();
                        String lon=ds.child("lon").getValue().toString();

                        System.out.println(distance(latitude,longitude,Double.parseDouble(lat),Double.parseDouble(lon),"K")+"ello" +name);
                        if(lat.equals(lfin.get(i).toString())){
                            junctionmodel item=new junctionmodel(lat,lon,name,number);
                            exampleList.add(item);
                            LatLng sydney = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
                            locationArrayList.add(sydney);
                            System.out.println(lat);
                            System.out.println("wyudfweyi");
                       }

                    }

                }

                /*for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String name=ds.child("shopname").getValue().toString();
                    String des=ds.child("shopdesc").getValue().toString();
                    String im=ds.child("shopimage").getValue().toString();
                    String lat=ds.child("lat").getValue().toString();
                    String lon=ds.child("lon").getValue().toString();
                    String upi=ds.child("upi").getValue().toString();
                    String phone=ds.child("phone").getValue().toString();
                    String userid=ds.child("userid").getValue().toString();
                    System.out.println(distance(latitude,longitude,Double.parseDouble(lat),Double.parseDouble(lon),"K"));
                    junctionmodel item=new junctionmodel(name,des,phone,im,userid,lat,lon,upi);
                    exampleList.add(item);
                }*/
                RecyclerView recyclerView = findViewById(R.id.idRVItemsmain);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Mapscreen.this);
                adapter = new JunctionsAdapter(exampleList,getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        exampleList.add(new ExampleItem(R.drawable.kddd, "Ali"));
//        exampleList.add(new ExampleItem(R.drawable.kid, "Uzma"));
//        exampleList.add(new ExampleItem(R.drawable.kiddd,  "Aslam"));
//        exampleList.add(new ExampleItem(R.drawable.kddd, "Sara"));
//        exampleList.add(new ExampleItem(R.drawable.kid,  "Ammara"));
//        exampleList.add(new ExampleItem(R.drawable.kidd,  "EasyCoding"));
//        exampleList.add(new ExampleItem(R.drawable.kiddd,  "Rasheed"));
//        exampleList.add(new ExampleItem(R.drawable.kddd,  "Areeb"));
//        exampleList.add(new ExampleItem(R.drawable.kiddd,  "Khan"));
    }
    private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            System.out.println(dist+"Kilometers");
            Toast.makeText(Mapscreen.this,dist+"Kilometers", Toast.LENGTH_SHORT).show();
            return (dist);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // inside on map ready method
        // we will be displaying all our markers.
        // for adding markers we are running for loop and
        // inside that we are drawing marker on our map.
        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title("Marker"));

            // below lin is use to zoom our camera on map.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_questions);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }


}