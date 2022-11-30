package com.example.parcial3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parcial3.model.User;
import com.example.parcial3.repository.UserRepository;

public class RegisterViewModel extends ViewModel {

    public MutableLiveData<User> user = new MutableLiveData<User>();;

    public LiveData<User> doRegister(String name, String lastName, String email, String phone, String password) {
        if (user == null) {
            user = new MutableLiveData<User>();
        }
        User ur = new User(0, name, lastName, email, phone, password);
        saveUser(ur);
        return user;
    }

    private void saveUser(User usr) {
        new Thread(() -> user.postValue(new UserRepository().saveUser(usr))).start();
    }
}
