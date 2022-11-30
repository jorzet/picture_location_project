package com.example.parcial3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.parcial3.R;
import com.example.parcial3.ui.login.LoginFragment;
import com.example.parcial3.ui.login.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_view, new LoginFragment())
                .commit();
    }

    public void showRegisterFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_view, new RegisterFragment())
                .addToBackStack("register_fragment")
                .commit();
    }
}