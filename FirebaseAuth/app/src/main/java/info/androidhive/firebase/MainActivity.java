package info.androidhive.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.StationsGridAdapter;
import info.androidhive.model.Station;

import static info.androidhive.firebase.SignupActivity.SELECTED_COMPANY;
import static info.androidhive.firebase.SignupActivity.USER_NAME;

public class MainActivity extends AppCompatActivity {

    private Button signOut, manageAccount;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference mPostReference;
    private SharedPreferences sharedPref;
    private GridView gv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences("ElectricCarShare", Context.MODE_PRIVATE);

        gv = (GridView) findViewById(R.id.activity_main_chargers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        //get user from firebase - if not active then launch LoginActivity
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        // Must be logged in at Firebase so continue
        // Show all the chargers for the user's company
        mPostReference = FirebaseDatabase.getInstance().getReference("companies/"
                + sharedPref.getString(SELECTED_COMPANY, "not found"));
        // TODO if not found then show select company activity
        final Query chargersQuery = mPostReference.child("stations");
        // TEMP to reset the sharedPref to match the json company name if manually changed in FB
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("company", "mbna");
//        editor.commit();

        signOut = (Button) findViewById(R.id.sign_out);
        manageAccount = (Button) findViewById(R.id.manage_account_button);

        manageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
            }
        });

        // Listen to firebase changes in the stations node
        ValueEventListener stationsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // send the list of chargers to the grid layout
                // noOfStations.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                // Populate a List from Array elements
                final List<Station> stations = new ArrayList();
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Station station = d.getValue(Station.class);
                    station.setId(d.getRef().getKey());
                    stations.add(station);
                }

                // Create a new ArrayAdapter
                //final ArrayAdapter<Button> gridViewArrayAdapter = new ArrayAdapter
                  //      (MainActivity.this, android.R.layout.simple_list_item_1, stations);
                StationsGridAdapter gridViewArrayAdapter = new StationsGridAdapter(MainActivity.this, stations);

                // Data bind GridView with ArrayAdapter (String Array elements)
                gv.setAdapter(gridViewArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        // Add a listener for each Button in the grid
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
//                Toast.makeText(MainActivity.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
                Station selected = (Station)parent.getItemAtPosition(position);
                if (selected.getAvailable() != 0) {
                    selected.setAvailable(0);
                    selected.setUser(null);
                    selected.setWorking(true);
                } else
                {
                    long dummyTime = 123456789;
                    selected.setAvailable(dummyTime);
                    selected.setWorking(true);
                    selected.setUser(sharedPref.getString(USER_NAME, "not found"));
                }
                chargersQuery.getRef().child(selected.getId()).setValue(selected);
                Toast.makeText(MainActivity.this, "" + selected.getId(),
                        Toast.LENGTH_SHORT).show();


            }
        });

        chargersQuery.addValueEventListener(stationsListener);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}