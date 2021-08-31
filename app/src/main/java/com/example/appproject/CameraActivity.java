package com.example.appproject;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;

import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class CameraActivity extends AppCompatActivity implements Scene.OnUpdateListener {
    private ArSceneView arView;
    private Session session;
    private boolean shouldConfigureSession  =false ;
    ARNode node;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //View
        arView = findViewById(R.id.arView);

        //Request permision
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setupSession();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(CameraActivity.this,"Se necesitan permisos de camara",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
        initSceneView();
    }


    private void initSceneView() {
        arView.getScene().addOnUpdateListener((this::onUpdate));
    }

    private void setupSession() {
        if(session == null){
            try {
                session = new Session(this);
            } catch (UnavailableArcoreNotInstalledException e) {
                e.printStackTrace();
            } catch (UnavailableApkTooOldException e) {
                e.printStackTrace();
            } catch (UnavailableSdkTooOldException e) {
                e.printStackTrace();
            } catch (UnavailableDeviceNotCompatibleException e) {
                e.printStackTrace();
            }
            shouldConfigureSession = true;

        }
        if(shouldConfigureSession== true){
            configSession();
            shouldConfigureSession=false;
            arView.setupSession(session);
        }
        try {
            session.resume();
            arView.resume();
        } catch (CameraNotAvailableException e) {
            e.printStackTrace();
            session=null;
            return;
        }

    }

    private void configSession() {
        Config config = new Config(session);
        if(!buildDatabase(config)){
            Toast.makeText(this,"Error database",Toast.LENGTH_SHORT).show();
        }
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        config.setFocusMode(Config.FocusMode.AUTO);
        session.configure(config);
    }

    private boolean buildDatabase(Config config) {
        AugmentedImageDatabase augmentedImageDatabase;
        /*Bitmap bitmap = loadImage();
        if(bitmap== null) {
            return false;
        }*/

        try {
            InputStream inputStream = getAssets().open("edmtdev.imgdb");
            augmentedImageDatabase =   AugmentedImageDatabase.deserialize(session,inputStream);
            config.setAugmentedImageDatabase(augmentedImageDatabase);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }



    }
    @Override
    public void onUpdate(FrameTime frameTime) {
        if(node!=null) arView.getScene().removeChild(node);

        Frame frame = arView.getArFrame();
        Collection<AugmentedImage> updateAugmentedImg = frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage image : updateAugmentedImg){
            Log.d("IMAGE NAME", String.valueOf(image.getName()));
            if(image.getTrackingState() == TrackingState.TRACKING){
                if (image.getName().equals("hamburguesa.png")){
                    node  = new ARNode(this, R.raw.hamburguesa);
                    
                }else if (image.getName().equals("Poster1.jpg")){
                    node = new ARNode(this, R.raw.poster1);
                    
                }
                node.setImage(image);
                arView.getScene().addChild(node);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(session!= null){
            arView.pause();
            session.pause();



        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setupSession();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(CameraActivity.this,"Se necesitan permisos de camara",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

}
