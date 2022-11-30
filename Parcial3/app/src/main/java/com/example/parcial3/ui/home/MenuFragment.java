package com.example.parcial3.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.parcial3.R;
import com.example.parcial3.repository.cache.SharedPreferencesManager;
import com.example.parcial3.ui.HomeActivity;
import com.example.parcial3.ui.LoginActivity;

public class MenuFragment extends Fragment {

    private AppCompatButton registerEventButton;
    private AppCompatButton seeEventsButton;
    private AppCompatButton closeSessionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        setUpViews(root);
        setUptListeners();
        return root;
    }

    private void setUpViews(View root) {
        registerEventButton = root.findViewById(R.id.btn_new_event);
        seeEventsButton = root.findViewById(R.id.btn_see_events);
        closeSessionButton = root.findViewById(R.id.btn_close_session);
    }

    private void setUptListeners() {
        if (registerEventButton != null) {
            registerEventButton.setOnClickListener(view -> {
                ((HomeActivity) getActivity()).showRegisterEventFragment();
            });
        }

        if (seeEventsButton != null) {
            seeEventsButton.setOnClickListener(view -> {
                ((HomeActivity) getActivity()).showEventsFragment();
            });
        }

        if (closeSessionButton != null) {
            closeSessionButton.setOnClickListener(view -> {
                new SharedPreferencesManager(getActivity()).removeUserFromCache();
                goLoginActivity();
            });
        }
    }

    private void goLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().finish();
        startActivity(intent);
    }
}
