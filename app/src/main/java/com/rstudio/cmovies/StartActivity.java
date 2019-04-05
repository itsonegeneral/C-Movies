package com.rstudio.cmovies;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";
    RecyclerView viewMal, viewHindi;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbMain = db.getReference("flamelink")
            .child("environments").child("production")
            .child("content");
    DatabaseReference dbMalayalam = dbMain.child("malayalamMovies")
            .child("en-US");
    DatabaseReference dbHindi = dbMain.child("hindiMovies")
            .child("en-US");
    ListView newList;
    ArrayList<String> newListArray = new ArrayList<>();
    private boolean doubleBackToExitPressedOnce = false;
    private RelativeLayout mNetworkLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setUpToolbar();
        mNetworkLayout = findViewById(R.id.vw_networkStart);
        mNetworkLayout.setVisibility(View.GONE);
        if (isNetworkConnected()) {
            showActiveNetwork();
        } else {
            showNoNetwork();
        }
        setHindi();
        setMal();
        setNewMovieList();
        //TODO setTamil();
        //setEng();
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void showActiveNetwork() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_slide_up);
        mNetworkLayout.setBackgroundColor(Color.parseColor("#515151"));
        TextView textView = findViewById(R.id.tv_networkStatus);
        textView.setText("Your Connection is back ..");
        findViewById(R.id.tv_retryNetworkStatus).setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mNetworkLayout.startAnimation(anim);
                mNetworkLayout.setVisibility(View.GONE);
            }
        },1000);
    }

    public void showNoNetwork() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_slide_bottom);
        TextView tvMain = findViewById(R.id.tv_networkStatus);
        TextView tvRetry = findViewById(R.id.tv_retryNetworkStatus);

        mNetworkLayout.setBackgroundColor(Color.parseColor("#D12121"));
        mNetworkLayout.setVisibility(View.VISIBLE);
        mNetworkLayout.startAnimation(anim);

        tvMain.setText("Oops ! No Connection ..");
        tvRetry.setVisibility(View.VISIBLE);
        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    showActiveNetwork();
                } else {
                    showNoNetwork();
                }
            }
        });

    }

    public void setHindi() {

        viewHindi = findViewById(R.id.rView_hindi_Main);
        ArrayList<Movie> movies = new ArrayList<>();

        dbHindi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    movies.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Movie temp = snapshot.getValue(Movie.class);
                        movies.add(temp);
                        Log.d(TAG, "onDataChange: Got Movie " + temp.getMovieName());
                    }
                    HorizontalMovieAdaptor adaptor = new HorizontalMovieAdaptor(movies, StartActivity.this);
                    viewHindi.setHasFixedSize(true);
                    viewHindi.setLayoutManager(new LinearLayoutManager(StartActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    viewHindi.setAdapter(adaptor);
                    adaptor.setOnItemClickListener(new HorizontalMovieAdaptor.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            //Open Video View Activity
                            openWatchActivity(movies.get(position));

                        }
                    });
                } else {
                    Log.d(TAG, "onDataChange: Database not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });

    }

    public void setMal() {

        Log.d(TAG, "setMal: ");
        viewMal = findViewById(R.id.rView_mal_Main);

        ArrayList<Movie> movies = new ArrayList<>();

        dbMalayalam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    movies.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Movie temp = snapshot.getValue(Movie.class);
                        movies.add(temp);
                        Log.d(TAG, "onDataChange: Got Movie " + temp.getMovieName());
                    }
                    HorizontalMovieAdaptor adaptor = new HorizontalMovieAdaptor(movies, StartActivity.this);
                    viewMal.setHasFixedSize(true);
                    viewMal.setLayoutManager(new LinearLayoutManager(StartActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    viewMal.setAdapter(adaptor);
                    adaptor.setOnItemClickListener(new HorizontalMovieAdaptor.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            //Open Video View Activity
                            openWatchActivity(movies.get(position));

                        }
                    });
                } else {
                    Log.d(TAG, "onDataChange: Database not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });

    }


    // slide the view from below itself to the current position
    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void openWatchActivity(Movie movie) {
        Intent i = new Intent(StartActivity.this, WatchMovieActivity.class);
        Log.d(TAG, "openWatchActivity: Opening" + movie);
        i.putExtra("movieClass", movie);
        startActivity(i);
    }

    public void setNewMovieList() {
        newList = findViewById(R.id.listview_newMovies);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(StartActivity.this, R.layout.simple_listview, newListArray);
        newList.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("New List");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "DataSnaoshot Loading");
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: Got Value " + s.getValue(String.class));
                    newListArray.add(s.getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_start);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_startactivity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    protected void onStop() {
        super.onStop();
    }
}