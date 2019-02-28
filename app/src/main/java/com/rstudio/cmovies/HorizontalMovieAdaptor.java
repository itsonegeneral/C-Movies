package com.rstudio.cmovies;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HorizontalMovieAdaptor extends FirestoreRecyclerAdapter<Movie, HorizontalMovieAdaptor.NoteHolder> {

    Context context;
    public HorizontalMovieAdaptor(@NonNull FirestoreRecyclerOptions<Movie> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Movie model) {
       Glide.with(context).load(Uri.parse(model.getImageUrl()).toString())
               .apply(RequestOptions.centerCropTransform())
               .into(holder.movieImage);
     //  Toast.makeText(context,model.getMovieName(),Toast.LENGTH_SHORT).show();
       holder.movieName.setText(model.getMovieName());
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_view,
                parent, false);

        this.context = parent.getContext();
        return new NoteHolder(v);

    }

    class NoteHolder extends RecyclerView.ViewHolder {
       ImageView movieImage;
        TextView movieName;

        public NoteHolder(View itemView) {
            super(itemView);
            movieName= itemView.findViewById(R.id.tv_movieNameAdaptor);
            movieImage = itemView.findViewById(R.id.image_movieHorizontal);
        }
    }
}