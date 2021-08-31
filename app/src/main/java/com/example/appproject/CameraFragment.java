package com.example.appproject;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CameraFragment extends Fragment {
public Session session;
FloatingActionButton camera;
View view;

private boolean shouldConfigureSession  =false ;

    public CameraFragment() {

    }

    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_camera, container, false);
        camera=view.findViewById(R.id.button2);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CameraActivity.class);
                startActivity(i);
            }
        });
            return view;
    }






}
