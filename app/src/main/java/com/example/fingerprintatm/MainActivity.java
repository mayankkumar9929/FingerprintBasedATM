package com.example.fingerprintatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fingerprintatm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Bundle bundle = getIntent().getBundleExtra("Message");
        String userId = bundle.getString("userId");
        String pin = bundle.getString("pin");


        binding.viewBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewBalanceActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);

            }
        });

        binding.transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        binding.pinChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PinChangeActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }
}