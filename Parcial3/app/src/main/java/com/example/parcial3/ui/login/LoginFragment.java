package com.example.parcial3.ui.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.parcial3.R;
import com.example.parcial3.repository.cache.SharedPreferencesManager;
import com.example.parcial3.ui.base.BaseFragment;
import com.example.parcial3.ui.LoginActivity;
import com.example.parcial3.ui.HomeActivity;
import com.example.parcial3.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends BaseFragment {

    /**
     * UI accessors
     */
    private AppCompatButton loginButton;
    private AppCompatButton registerButton;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;

    private LoginViewModel viewModel;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        setUpViews(root);
        setUpListeners();

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 200);


        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.user.observe(getViewLifecycleOwner(), user -> {
            progressDialog.dismiss();
            if (user != null) {
                new SharedPreferencesManager(getActivity()).saveSession(user);
                goHomeActivity();
            } else {
                Toast.makeText(getActivity(), "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    protected void setUpViews(View root) {
        super.setUpViews(root);
        progressDialog = new ProgressDialog(getActivity());
        emailEditText = root.findViewById(R.id.user_email_text_input_edit_text);
        passwordEditText = root.findViewById(R.id.password_text_input_edit_text);
        loginButton = root.findViewById(R.id.login_button);
        registerButton = root.findViewById(R.id.register_button);
    }

    @Override
    protected void setUpListeners() {
        if (loginButton != null) {
            loginButton.setOnClickListener(view -> {
                String email = emailEditText.getText().toString();
                String pass = passwordEditText.getText().toString();
                if (!email.isEmpty() && !pass.isEmpty()) {
                    progressDialog.setMessage("Espere un momento");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    viewModel.doLogin(email, pass);
                } else {
                    Toast.makeText(getActivity(), "Es necesario ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (registerButton != null) {
            registerButton.setOnClickListener(view ->  {
                ((LoginActivity) getActivity()).showRegisterFragment();
            });
        }
    }

    private void goHomeActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().finish();
        startActivity(intent);
    }
}
