package com.example.fishgameactivity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fishgameactivity.R;
import com.example.fishgameactivity.callback.callback;
import com.example.fishgameactivity.model.UserHighScore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {

    private ArrayList<UserHighScore> list = new ArrayList();
    //    private ArrayList<String> listStatus = new ArrayList();
    private Context context;
//    private Boolean status = false;

    public callback.CallBackDelete callBackDelete;

    public void setOnClickListener(callback.CallBackDelete callBackDelete) {
        this.callBackDelete = callBackDelete;
    }

    public HighScoreAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<UserHighScore> listNameTable) {
        this.list = listNameTable;
        Collections.sort(list, new Comparator<UserHighScore>() {

            @Override
            public int compare(UserHighScore o1, UserHighScore o2) {
                return o2.getScore().compareTo(o1.getScore());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_score_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView score;
        private final ImageButton delete;
        private final LinearLayout itemBookTable;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            score = (TextView) view.findViewById(R.id.score);
            delete = (ImageButton) view.findViewById(R.id.delete);
            itemBookTable = (LinearLayout) view.findViewById(R.id.itemBookTable);
        }
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText(list.get(position).getName().toString());

        holder.score.setText(list.get(position).getScore().toString());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackDelete.OnItemDirect(list.get(position).getName().toString(), position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
