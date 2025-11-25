package com.example.group14app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private List<Users> users =
            List.of(
                    new Users("jon","password","Jon Doe",1),
                    new Users("jane","password","Jane Doe",2),
                    new Users("jim","password","Jimmy Jim",3)
            );



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void onLoginClicked(View view) {
        EditText usernameEditText = findViewById(R.id.usrName);
        EditText passwordEditText = findViewById(R.id.etPassword);

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        for (Users user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                Intent intent = new Intent(this,HomeDashboardActivity.class);
                startActivity(intent);
                return;
            }
        }

        usernameEditText.setError("Invalid username or password");
        passwordEditText.setError("Invalid username or password");
        }


}