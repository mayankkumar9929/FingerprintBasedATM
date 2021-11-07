package com.example.fingerprintatm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fingerprintatm.databinding.ActivityViewBalanceBinding;

public class ViewBalanceActivity extends AppCompatActivity {

    ActivityViewBalanceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBalanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHandler db = new DatabaseHandler(this);

        String userId = getIntent().getStringExtra("userId");

        User user = db.getUser(userId);


        binding.viewBal.setText(
                "Acc. Holder's name : "+ user.getName() +"\n"+"\n"+
                "Your remaining balance is : "+ user.getBalance()
        );
    }
}