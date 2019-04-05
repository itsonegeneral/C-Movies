package com.rstudio.cmovies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class HorizontalMovieAdaptor extends RecyclerView.Adapter<HorizontalMovieAdaptor.MyViewHolder> {

    private Context context;
    private List<Movie> moviesList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView movieName, year, genre;
        public ImageView movieIcon;
        public ProgressBar pgLoading;
        public MyViewHolder(View view,OnItemClickListener listener) {
            super(view);
            movieName = view.findViewById(R.id.tv_movieNameAdaptor);
            movieIcon = view.findViewById(R.id.img_movieHorizontal);
            pgLoading = view.findViewById(R.id.pgBar_Adaptor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    public HorizontalMovieAdaptor(List<Movie> moviesList, Context context) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_view, parent, false);

        return new MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.pgLoading.setVisibility(View.VISIBLE);
        Glide.with(context).load(Uri.parse(movie.getImageLink()).toString())
                .apply(RequestOptions.centerCropTransform()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.pgLoading.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.pgLoading.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.movieIcon);
        holder.movieName.setText(movie.getMovieName());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}

