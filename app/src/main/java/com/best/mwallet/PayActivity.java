package com.best.mwallet;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class PayActivity extends AppCompatActivity {

    AppCompatEditText edCardNumber;
    AppCompatEditText edCardExpiryMonth;
    AppCompatEditText edCardExpiryYear;
    AppCompatEditText edCardCVV;
    ProgressBar progressBar;
    AppCompatEditText tvAmount;
    SharedPreferences sharedPreferences;
    Button btnProceed;

    double mAmount ;
  //  String ref;
    String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        edCardNumber = findViewById(R.id.card_number);
        edCardExpiryMonth = findViewById(R.id.card_ex_month);
        edCardExpiryYear = findViewById(R.id.card_ex_year);
        edCardCVV = findViewById(R.id.card_cvv);
        tvAmount = findViewById(R.id.amount);
        btnProceed = findViewById(R.id.proceed);
        progressBar = findViewById(R.id.progressBar);
        //tvAmount.setText(amount);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final String balance = sharedPreferences.getString("bal" + getIntent().getStringExtra("key"), null);

        if (balance == null) {
            mAmount=0;
        } else {
            mAmount = Double.parseDouble(balance);
        }
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedWIthPayment();
            }
        });
    }
    void toast(String message){
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        toast.show();
    }

    boolean checkValidEntriesForErrors(){
        if(TextUtils.isEmpty(edCardNumber.getText())
            ||TextUtils.isEmpty(edCardExpiryMonth.getText())
                ||TextUtils.isEmpty(edCardExpiryYear.getText())
            ||TextUtils.isEmpty(edCardCVV.getText())
                ||TextUtils.isEmpty(tvAmount.getText())
        ){
            toast("All fields must be filled to proceed.");
            return true;
        }
        try {
            int exMonth = Integer.parseInt(edCardExpiryMonth.getText().toString());
            int exYear = Integer.parseInt(edCardExpiryYear.getText().toString());
        }catch (Exception e){
            toast("invalid month or year entered");
            return true;
        }
        int exMonth = Integer.parseInt(edCardExpiryMonth.getText().toString());
        if(exMonth<01||exMonth>12){
            toast("invalid month entered");
            return true;
        }
        int exYear = Integer.parseInt(edCardExpiryYear.getText().toString());
        if(exYear< Calendar.getInstance().get(Calendar.YEAR)){
            toast("invalid year entered");
            return true;
        }
        if(Integer.parseInt(tvAmount.getText().toString())<100){
            toast("N100 is the minimum fund amount.");
            return true;
        }
        return false;
    }
    void proceedWIthPayment(){
        boolean error = checkValidEntriesForErrors();
        if(error) return;
        String cardNum = edCardNumber.getText().toString();
        int exMonth = Integer.parseInt(edCardExpiryMonth.getText().toString());
        int year = Integer.parseInt(edCardExpiryYear.getText().toString());
        String cvv = edCardCVV.getText().toString();
        mAmount = mAmount + Double.parseDouble(tvAmount.getText().toString());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bal"+ getIntent().getStringExtra("key"),String.valueOf(mAmount));
        editor.apply();
        al();
    }



    void al(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Successful")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setMessage("Payment success" );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
