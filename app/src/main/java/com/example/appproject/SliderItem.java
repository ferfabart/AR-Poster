package com.example.appproject;

import androidx.annotation.NonNull;

public class SliderItem {
    public SliderItem(){}
    private String image;

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    SliderItem (String image){
        this.image=image;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl() {
        this.image=image;
    }
}
