package com.best.mwallet;

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

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.login);
        final AutoCompleteTextView username = findViewById(R.id.username);
        final AutoCompleteTextView password = findViewById(R.id.password);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView register = findViewById(R.id.reg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(username.getText())&&!TextUtils.isEmpty(password.getText())){
                    progressBar.setVisibility(View.VISIBLE);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    String  usernameText = sharedPreferences.getString(username.getText().toString(),null);
                    String  passwordText = sharedPreferences.getString(password.getText().toString(),null);
                   // String  passwordText = sharedPreferences.getString("123"+username.getText().toString()
                    if(usernameText!=null&&passwordText!=null){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("key",usernameText);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"Invalid login details",Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);

                }else {
                    Toast.makeText(LoginActivity.this,"Please Fill All Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });

            }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
