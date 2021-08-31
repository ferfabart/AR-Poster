package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class HomeActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigation =findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_gallery));
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_camera));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_user));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment;
                switch (item.getId()){
                    case 1:
                        fragment = new CameraFragment();
                        break;
                    case 2:
                        fragment= new GalleryFragment();
                        break;
                    case 3:
                        fragment=new InfoFragment();
                        break;

                    default:
                        fragment=null;
                }
                loadFragment(fragment);
            }
        });


        bottomNavigation.show(1,true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }
}