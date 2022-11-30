package com.example.parcial3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parcial3.model.Event;
import com.example.parcial3.repository.EventRepository;

import java.util.List;

public class EventViewModel extends ViewModel {

    public MutableLiveData<List<Event>> events = new MutableLiveData<List<Event>>();
    public MutableLiveData<String> saveEventResponse = new MutableLiveData<String>();

    public LiveData<List<Event>> getEvents() {
        if (events == null) {
            events = new MutableLiveData<List<Event>>();
        }
        loadEvents();
        return events;
    }

    public LiveData<String> saveEvent(Event event) {
        if (saveEventResponse == null) {
            saveEventResponse = new MutableLiveData<String>();
        }
        requestSaveEvent(event);
        return saveEventResponse;
    }

    private void loadEvents() {
        new Thread(() -> events.postValue(new EventRepository().getEvents())).start();
    }

    private void requestSaveEvent(Event event) {
        new Thread(() -> saveEventResponse.postValue(new EventRepository().saveEvent(event))).start();
    }
}
