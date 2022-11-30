package com.example.parcial3.ui.login;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.parcial3.R;
import com.example.parcial3.repository.cache.SharedPreferencesManager;
import com.example.parcial3.ui.base.BaseFragment;
import com.example.parcial3.ui.HomeActivity;
import com.example.parcial3.viewmodel.RegisterViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterFragment extends BaseFragment {

    private TextInputEditText userName;
    private TextInputEditText userLastName;
    private TextInputEditText userEmail;
    private TextInputEditText userPhone;
    private TextInputEditText userPassword;
    private AppCompatButton saveButton;
    private AppCompatButton backButton;

    private RegisterViewModel viewModel;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        setUpViews(root);
        setUpListeners();

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        viewModel.user.observe(getViewLifecycleOwner(), user -> {
            progressDialog.dismiss();
            if (user != null) {
                new SharedPreferencesManager(getActivity()).saveSession(user);
                goHomeActivity();
            } else {
                Toast.makeText(getActivity(), "Error al registrar al usuario", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    protected void setUpViews(View root) {
        super.setUpViews(root);
        progressDialog = new ProgressDialog(getActivity());
        userName = root.findViewById(R.id.user_name_text_input_edit_text);
        userLastName = root.findViewById(R.id.user_lastname_text_input_edit_text);
        userEmail = root.findViewById(R.id.user_email_text_input_edit_text);
        userPhone = root.findViewById(R.id.user_phone_text_input_edit_text);
        userPassword = root.findViewById(R.id.password_text_input_edit_text);
        saveButton = root.findViewById(R.id.save_button);
        backButton = root.findViewById(R.id.back_button);
    }

    @Override
    protected void setUpListeners() {
        super.setUpListeners();
        if (saveButton != null) {
            saveButton.setOnClickListener(view -> {
                String name = userName.getText().toString();
                String lastName = userLastName.getText().toString();
                String email = userEmail.getText().toString();
                String phone = userPhone.getText().toString();
                String pass = userPassword.getText().toString();

                if (!name.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !pass.isEmpty()) {
                    progressDialog.setMessage("Espere un momento");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    viewModel.doRegister(name, lastName, email, phone, pass);
                } else {
                    Toast.makeText(getActivity(), "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (backButton != null) {
            backButton.setOnClickListener(view -> {
                getActivity().onBackPressed();
            });
        }
    }

    private void goHomeActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }
}
