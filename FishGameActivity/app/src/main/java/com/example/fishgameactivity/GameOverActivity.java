package com.example.fishgameactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fishgameactivity.adapter.HighScoreAdapter;
import com.example.fishgameactivity.callback.callback;
import com.example.fishgameactivity.databinding.ActivityGameOverBinding;
import com.example.fishgameactivity.model.UserHighScore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameOverActivity extends AppCompatActivity {
    private ActivityGameOverBinding binding;
    private String score;
    private String timeup;
    private String name;
    private int HighScore = 0;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ArrayList<UserHighScore> list = new ArrayList<>();
    private HighScoreAdapter highScoreAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameOverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
//        score = bundle.getString("score", "");
        timeup = bundle.getString("timeup", "");
//        name = bundle.getString("name", "");
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        binding.tvTitle.setText("ALL SCORE");

        highScoreAdapter = new HighScoreAdapter(GameOverActivity.this);
//        binding.tvName.setText(timeup);
//        binding.tvScore.setText("Score: " + score);

//        binding.imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        DatabaseReference friendsHighScore = ref;
        friendsHighScore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnap : snapshot.getChildren()) {
                    name = dataSnap.child("name").getValue().toString();
                    score = dataSnap.child("score").getValue().toString();
                    if (Integer.parseInt(score) > HighScore) {
                        HighScore = Integer.parseInt(score);
                        binding.tvNameHighScore.setText(name);
                        binding.tvScoreHighScore.setText(String.valueOf(" " + HighScore));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference friendsRef = ref;
        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnap : snapshot.getChildren()) {
                    name = dataSnap.child("name").getValue().toString();
                    score = dataSnap.child("score").getValue().toString();
                    list.add(new UserHighScore(name, score));
                }
                onSetRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (timeup.equals("no")) {
            binding.btnConfirm.setVisibility(View.GONE);
        }

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        highScoreAdapter.setOnClickListener(new callback.CallBackDelete() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void OnItemDirect(String name, int position) {
                ref.child(list.get(position).getName()).removeValue();
                list.clear();
                highScoreAdapter.notifyDataSetChanged();
            }
        });

    }

    private void onSetRecyclerView() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider_recyclerview)));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(itemDecoration);
        highScoreAdapter.setData((ArrayList<UserHighScore>) list);
        binding.recyclerView.setAdapter(highScoreAdapter);
    }
}