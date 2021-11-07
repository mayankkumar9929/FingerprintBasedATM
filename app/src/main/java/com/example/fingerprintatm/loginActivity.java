package com.example.fingerprintatm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fingerprintatm.databinding.ActivityLoginBinding;

import java.util.concurrent.Executor;

public class loginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHandler db = new DatabaseHandler(this);

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = binding.userid.getText().toString();
                String pin = binding.pinlogin.getText().toString();

                if(userId.equals("") || pin.equals(""))
                    Toast.makeText(loginActivity.this, "Please leave no field blank", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserid = db.checkuserId(userId);
                    if(checkuserid==false){
                        Toast.makeText(loginActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Boolean checkpin = db.checkuserIdpin(userId,pin);
                        if(checkpin==false){
                            Toast.makeText(loginActivity.this, "PIN is wrong", Toast.LENGTH_SHORT).show();
                        } else{
                          BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                                  .setTitle("Please Verify your Fingerprint")
                                  .setDescription("User Biometric Authentication is required to proceed")
                                  .setNegativeButtonText("Cancel")
                                  .build();
                          getPrompt().authenticate(promptInfo);
                        }
                    }
                }
            }
        });

        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, signupActivity.class);
                startActivity(intent);
            }
        });
    }
      private BiometricPrompt getPrompt(){
          Executor executor = ContextCompat.getMainExecutor(this);
          BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
              @Override
              public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                  super.onAuthenticationError(errorCode, errString);
                  notifyUser(errString.toString());
              }

              @Override
              public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                  super.onAuthenticationSucceeded(result);
                  notifyUser("Authentication Succeeded!");
                  String userId = binding.userid.getText().toString();
                  String pin = binding.pinlogin.getText().toString();
                  Intent intent = new Intent(loginActivity.this, MainActivity.class);
                  Bundle bundle = new Bundle();
                  bundle.putString("userId", userId);
                  bundle.putString("pin", pin);
                  intent.putExtra("Message", bundle);
                  startActivity(intent);
              }

              @Override
              public void onAuthenticationFailed() {
                  super.onAuthenticationFailed();
                  notifyUser("Authentication Failed!");
              }
          };

          BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, callback);
          return biometricPrompt;
      }

      private void notifyUser(String message){
          Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
      }
}