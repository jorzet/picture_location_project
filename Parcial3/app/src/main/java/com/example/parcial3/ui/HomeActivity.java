package com.example.parcial3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.parcial3.R;
import com.example.parcial3.ui.home.RegisterEventFragment;
import com.example.parcial3.ui.home.EventsFragment;
import com.example.parcial3.ui.home.MenuFragment;

public class HomeActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_view, new MenuFragment())
                .commit();
    }

    public void showEventsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_view, new EventsFragment())
                .addToBackStack("events_fragment")
                .commit();
    }

    public void showRegisterEventFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_view, new RegisterEventFragment())
                .addToBackStack("register_event_fragment")
                .commit();
    }
}