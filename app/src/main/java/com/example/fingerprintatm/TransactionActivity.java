package com.example.fingerprintatm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fingerprintatm.databinding.ActivityTransactionBinding;

public class TransactionActivity extends AppCompatActivity {

    ActivityTransactionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHandler db = new DatabaseHandler(this);

        String userId = getIntent().getStringExtra("userId");

        User user = db.getUser(userId);

        binding.withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int withdrawAmt = Integer.parseInt(binding.withdrawAmt.getText().toString());
                if(withdrawAmt>user.getBalance()){
                    Toast.makeText(TransactionActivity.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                }
                 else{
                     boolean update = db.updateBalance(userId, user.getBalance()-withdrawAmt);
                     if(update){
                         Toast.makeText(TransactionActivity.this, "Transaction Successfull", Toast.LENGTH_SHORT).show();
                            finish();
                     }

                }
            }
        });
    }
}