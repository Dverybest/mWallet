package com.best.mwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String username;
    TextView name;
    TextView phone;
    TextView bal;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         name = findViewById(R.id.name);
         phone= findViewById(R.id.phone);
            bal= findViewById(R.id.bal);

        btn = findViewById(R.id.add);


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = getIntent().getStringExtra("key");
        String phoneN = sharedPreferences.getString("123" + username, null);
        final String balance = sharedPreferences.getString("bal" + username, null);
        name.setText(username);
        phone.setText(phoneN);
        if (balance == null) {
            bal.setText("N0.00");
        } else {
            bal.setText(balance);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PayActivity.class);
                intent.putExtra("key",username);
                intent.putExtra("bal",balance);
                startActivity(intent);
            }
        });
    }
}
