package com.example.fishgameactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.fishgameactivity.databinding.ActivityGameOverBinding;
import com.example.fishgameactivity.databinding.ActivityStartBinding;
import com.example.fishgameactivity.dialog.ShowDialog;

public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding binding;
    private ShowDialog showDialog = new ShowDialog(StartActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog.openDialogName(Gravity.CENTER);
            }
        });

        binding.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, GameOverActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("timeup", "no");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}