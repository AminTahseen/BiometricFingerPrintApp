package com.example.biometricprompt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    Button login;
    TextView txtMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.login);
        txtMessage=findViewById(R.id.txtMessage);

        BiometricManager manager= BiometricManager.from(this);
        switch (manager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
                txtMessage.setText("You can use the fingerprint sensor.");
                break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    txtMessage.setText("The biometric sensor is currently unavailable.");
                    break;
                    case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                        txtMessage.setText("Device don't have fingerprint sensor.");
                        break;
                        case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                            txtMessage.setText("Device don't have any fingerprint saved.");
                            break;

        }
        Executor executer= ContextCompat.getMainExecutor(this);
       final BiometricPrompt bp=new BiometricPrompt(MainActivity.this, executer, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        final BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("Use your fingerprint to login")
                .setNegativeButtonText("Cancel")
                .build();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.authenticate(promptInfo);
            }
        });
    }
}