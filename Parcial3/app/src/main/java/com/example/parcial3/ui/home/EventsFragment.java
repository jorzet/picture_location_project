package com.example.parcial3.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial3.R;
import com.example.parcial3.ui.base.BaseFragment;
import com.example.parcial3.viewmodel.EventViewModel;

public class EventsFragment extends BaseFragment {

    private RecyclerView eventList;
    private EventViewModel viewModel;
    private EventAdapter adapter;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event_list, container, false);
        setUpViews(root);
        setUpListeners();

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        viewModel.events.observe(getViewLifecycleOwner(), events -> {
            progressDialog.dismiss();
            if (events != null) {
                adapter = new EventAdapter(events, new EventAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener() {

                    }
                });

                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                eventList.setLayoutManager(manager);
                eventList.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "Error. No existen eventos aun", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.setMessage("Espere un momento");
        progressDialog.setCancelable(false);
        progressDialog.show();
        viewModel.getEvents();
        return root;
    }

    @Override
    protected void setUpViews(View root) {
        super.setUpViews(root);
        progressDialog = new ProgressDialog(getActivity());
        eventList = root.findViewById(R.id.rv_event_list);
    }

    @Override
    protected void setUpListeners() {
        super.setUpListeners();
        if (eventList != null) {

        }
    }
}
