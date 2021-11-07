package com.example.fingerprintatm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fingerprintatm.databinding.ActivitySignupBinding;

public class signupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHandler db = new DatabaseHandler(this);

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addUser(
                        new User(
                                binding.userid.getText().toString(),
                                binding.pin.getText().toString(),
                                binding.name.getText().toString(),
                                Integer.parseInt(binding.balance.getText().toString())
                        )
                );
                Toast.makeText(signupActivity.this, "User Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}