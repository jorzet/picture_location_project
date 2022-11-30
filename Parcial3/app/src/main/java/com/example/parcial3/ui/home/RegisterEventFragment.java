package com.example.parcial3.ui.home;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.parcial3.R;
import com.example.parcial3.model.Event;
import com.example.parcial3.model.User;
import com.example.parcial3.repository.cache.SharedPreferencesManager;
import com.example.parcial3.ui.base.BaseFragment;
import com.example.parcial3.viewmodel.EventViewModel;
import com.example.parcial3.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterEventFragment extends BaseFragment {

    private TextInputEditText descriptionEditText;
    private ImageButton cameraButton;
    private ImageView mImageView;
    private AppCompatButton saveEventButton;
    private TextView latitudeTextView;
    private TextView longitudeTextView;

    private Uri cam_uri;

    private EventViewModel viewModel;

    private ProgressDialog progressDialog;

    ActivityResultLauncher<Intent> startCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    mImageView.setImageURI(cam_uri);
                    LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    latitudeTextView.setText(String.valueOf(latitude));
                    longitudeTextView.setText(String.valueOf(longitude));
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_event, container, false);
        setUpViews(root);
        setUpListeners();

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        viewModel.saveEventResponse.observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response != null) {
                getActivity().onBackPressed();
            } else {
                Toast.makeText(getActivity(), "Error al guardar el evento", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    protected void setUpViews(View root) {
        super.setUpViews(root);
        progressDialog = new ProgressDialog(getActivity());
        descriptionEditText = root.findViewById(R.id.description_text_input_edit_text);
        cameraButton = root.findViewById(R.id.imageButton);
        mImageView = root.findViewById(R.id.imageView);
        saveEventButton = root.findViewById(R.id.save_event);
        latitudeTextView = root.findViewById(R.id.tv_latitude);
        longitudeTextView = root.findViewById(R.id.tv_longitude);
    }

    @Override
    protected void setUpListeners() {
        super.setUpListeners();

        if (cameraButton != null) {
            cameraButton.setOnClickListener(view -> {
                    pickCamera();
            });
        }

        if (saveEventButton != null) {
            saveEventButton.setOnClickListener(view -> {
                String description = descriptionEditText.getText().toString();
                String lat = latitudeTextView.getText().toString();
                String lon = longitudeTextView.getText().toString();
                boolean islatLongSet = !lat.isEmpty() && !lat.equals("Latitud") && !lon.isEmpty() && !lon.equals("Longitud");

                if (cam_uri != null) {
                    if (!description.isEmpty()) {
                        progressDialog.setMessage("Espere un momento");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        InputStream iStream = null;
                        try {
                            iStream = getActivity().getContentResolver().openInputStream(cam_uri);
                            byte[] inputData = getBytes(iStream);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());

                            User user = new SharedPreferencesManager(getActivity()).getSession();

                            if (user != null) {
                                Event event = new Event(0, inputData, currentDateandTime, description, lat, lon, user.getUserId());
                                viewModel.saveEvent(event);
                            } else  {
                                Toast.makeText(getActivity(), "Error al obtener la sesion", Toast.LENGTH_SHORT).show();
                            }
                        } catch (FileNotFoundException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error al obtener la imagen", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Es necesario añadir una descripcion", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Es necesario tomar la foto", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        cam_uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);

        //startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE); // OLD WAY
        startCamera.launch(cameraIntent);                // VERY NEW WAY
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
