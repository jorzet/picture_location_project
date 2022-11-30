package com.example.parcial3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parcial3.model.User;
import com.example.parcial3.repository.UserRepository;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<User> user = new MutableLiveData<User>();;

    public LiveData<User> doLogin(String email, String password) {
        if (user == null) {
            user = new MutableLiveData<User>();
        }
        loadUsers(email, password);
        return user;
    }

    private void loadUsers(String email, String password) {
        new Thread(() -> user.postValue(new UserRepository().getUser(email, password))).start();
    }
}
