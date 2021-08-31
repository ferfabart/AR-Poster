package com.example.appproject;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GalleryFragment extends Fragment {

    ViewPager2 viewPager2;
    ArrayList<Object> images = new ArrayList<Object>();
    ArrayList<Object> images2 = new ArrayList<Object>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<SliderItem> list = new ArrayList<>();
    List<SliderItem> list2 = new ArrayList<>();
    View view;
    SliderAdapter adapter;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        for (SliderItem item : list2) {
            this.list.add(item);
        }
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance("https://arapp-4ab44-default-rtdb.europe-west1.firebasedatabase.app");
        reference = firebaseDatabase.getReference();
        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        list=list2;
        adapter = new SliderAdapter(list, viewPager2);
        viewPager2.setPageTransformer(compositePageTransformer);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    collectImages(dataSnapshot);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        viewPager2.setAdapter(adapter);
        return view;
    }



    private void collectImages(DataSnapshot value) {
        for (DataSnapshot image : value.getChildren()) {
            this.list2.add( new SliderItem((String) image.getValue()));
            Log.d("IMAGE", String.valueOf(new SliderItem((String) image.getValue())));
        }

        adapter.notifyDataSetChanged();

    }
}