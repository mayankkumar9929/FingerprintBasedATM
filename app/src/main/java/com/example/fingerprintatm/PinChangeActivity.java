package com.example.fingerprintatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fingerprintatm.databinding.ActivityPinChangeBinding;

public class PinChangeActivity extends AppCompatActivity {

    ActivityPinChangeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPinChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHandler db = new DatabaseHandler(this);

        String userId = getIntent().getStringExtra("userId");

        User user = db.getUser(userId);

        binding.changePinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPin = binding.newPin.getText().toString();
                boolean change = db.updatePin(userId, newPin);
                if(change){
                    Toast.makeText(PinChangeActivity.this, "PIN Changed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PinChangeActivity.this, loginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}