package com.rstudio.cmovies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firestore.v1.TransactionOptions;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";
    HorizontalMovieAdaptor adaptorM,adaptorH,adaptorT,adaptorE;
    RecyclerView viewMal, viewHindi;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference refMal = db.collection("Mal_Movies");
    CollectionReference refTamil = db.collection("Mal_Movies");
    CollectionReference refEng = db.collection("Mal_Movies");
    CollectionReference refHindi = db.collection("Hindi_Movies");
    ListView newList ;
    ArrayList<String> newListArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setHindi();
        setMal();
        setNewMovieList();
        //TODO setTamil();
        //setEng();
    }

    public void setHindi() {
        Query queryH = refHindi.orderBy("movieName", Query.Direction.ASCENDING);
        viewHindi = findViewById(R.id.rView_hindi_Main);

        FirestoreRecyclerOptions<Movie> options = new FirestoreRecyclerOptions.Builder<Movie>()
                .setQuery(queryH,Movie.class)
                .build();
        adaptorH = new HorizontalMovieAdaptor(options);
        viewHindi.setHasFixedSize(true);
        viewHindi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewHindi.setAdapter(adaptorH);
        adaptorH.setOnItemClickListner(new HorizontalMovieAdaptor.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                try {
                    Movie movie =  documentSnapshot.toObject(Movie.class);
                    Toast.makeText(getApplicationContext(), movie.getMovieName(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(StartActivity.this,WatchMovieActivity.class);
                    i.putExtra("url",movie.getVideoLocation());
                    i.putExtra("movieName",movie.getMovieName());
                    startActivity(i);
                }catch (NullPointerException e ){
                    e.printStackTrace();
                }
            }
        });
    }

    public void setMal() {

        viewMal = findViewById(R.id.rView_mal_Main);

        Query queryM = refMal.orderBy("movieName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Movie> optionsM = new FirestoreRecyclerOptions.Builder<Movie>()
                .setQuery(queryM, Movie.class)
                .build();
        adaptorM = new HorizontalMovieAdaptor(optionsM);

        viewMal.setHasFixedSize(true);
        viewMal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewMal.setAdapter(adaptorM);
    }

    public void setNewMovieList() {
        newList = findViewById(R.id.listview_newMovies);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(StartActivity.this,R.layout.simple_listview,newListArray);
        newList.setAdapter(adapter);
        Log.d(TAG, "setNewMovieList: ");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("New List");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "DataSnaoshot Loading");
                for (DataSnapshot s : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: Got Value "+ s.getValue(String.class));
                     newListArray.add(s.getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        adaptorM.startListening();
        adaptorH.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptorM.stopListening();
        adaptorH.startListening();
    }
}