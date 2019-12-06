package com.best.mwallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register );
        Button login = findViewById(R.id.login);
        final AutoCompleteTextView username = findViewById(R.id.username);
        final AutoCompleteTextView password = findViewById(R.id.password);
        final AutoCompleteTextView phone = findViewById(R.id.phone);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(username.getText())&&!TextUtils.isEmpty(password.getText())&&!TextUtils.isEmpty(phone.getText())){
                    progressBar.setVisibility(View.VISIBLE);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                    String  usernameText = sharedPreferences.getString(username.getText().toString(),null);
                    //String  passwordText = sharedPreferences.getString(username.getText().toString(),null);
                    if(usernameText!=null){
                        Toast.makeText(RegisterActivity.this,"Username already Exist",Toast.LENGTH_SHORT).show();
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(username.getText().toString(),username.getText().toString());
                    editor.putString(password.getText().toString(),password.getText().toString());
                    editor.putString("123"+username.getText().toString(),phone.getText().toString());
                    editor.apply();
                    progressBar.setVisibility(View.GONE);
                    AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this)
                            .setMessage("Registration Successful. Please Login with your details.")
                            .setTitle("Done")
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setCancelable(false).create();
                    dialog.show();


                }else {
                    Toast.makeText(RegisterActivity.this,"Please Fill All Fields",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
