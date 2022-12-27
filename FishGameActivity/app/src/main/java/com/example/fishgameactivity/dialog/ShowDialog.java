package com.example.fishgameactivity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fishgameactivity.MainActivity;
import com.example.fishgameactivity.R;

public class ShowDialog {
    private Context context;

    public ShowDialog(Context context) {
        this.context = context;
    }

    public void showLevelUp(int gravity) {
//        Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_over);
//        Window window = dialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams windowAttributes = window.getAttributes();
//        windowAttributes.gravity = gravity;
//        if (Gravity.CENTER == gravity) {
//            dialog.setCancelable(true);
//        } else {
//            dialog.setCancelable(false);
//        }
//        dialog.setCancelable(false);
//        dialog.show();
    }

    public void openDialogName(int gravity) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_name);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        ConstraintLayout btnCancel = dialog.findViewById(R.id.btn_cancel);
        ConstraintLayout btnConfirm = dialog.findViewById(R.id.btn_confirm);
        EditText edtName = dialog.findViewById(R.id.edtNameDish);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().isEmpty()) {
                    edtName.setError("Name is required!");
                    edtName.requestFocus();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", edtName.getText().toString());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void openDialogDelete(int gravity, String nameDish, int position) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialog);
        Button btnConfirm = dialog.findViewById(R.id.btnConFirmDialog);
        TextView txtLogOut = dialog.findViewById(R.id.txtLogOut);
        txtLogOut.setText(nameDish);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                callBackDelete.OnItemDirect(position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
