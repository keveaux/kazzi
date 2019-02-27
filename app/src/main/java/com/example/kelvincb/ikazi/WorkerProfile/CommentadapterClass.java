package com.example.kelvincb.ikazi.WorkerProfile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.mPicasso.PicassoClient;

import java.util.ArrayList;

public class CommentadapterClass extends RecyclerView.Adapter<MyViewHolder> {

    Context c;
    ArrayList <CommentgetterSetterClass> commentsList;
    private LayoutInflater inflater;


    public CommentadapterClass(Context c, ArrayList<CommentgetterSetterClass> commentsList) {
        inflater = LayoutInflater.from(c);
        this.c = c;
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.comments_row_model, viewGroup, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.username.setText(commentsList.get(position).getUsername());
        holder.comment.setText(commentsList.get(position).getComment());

        String rating=commentsList.get(position).getRating();

        float mRating= Float.parseFloat(rating);

        holder.ratingBar.setRating(mRating);

        PicassoClient.loadImage(commentsList.get(position).getImageurl(),holder.userimage);



//        if(commentsList.get(position).getComment().equals("null")){
//            Toast.makeText(c, "empty", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }


}

class MyViewHolder extends RecyclerView.ViewHolder
{

    TextView username;
    TextView comment;
    RatingBar ratingBar;
    ImageView userimage;


    public MyViewHolder(View itemView) {
        super(itemView);
        comment=itemView.findViewById(R.id.comment);
        username=itemView.findViewById(R.id.user_name);
        ratingBar=itemView.findViewById(R.id.commentratingbar);
        userimage=itemView.findViewById(R.id.user_image);


    }


}

