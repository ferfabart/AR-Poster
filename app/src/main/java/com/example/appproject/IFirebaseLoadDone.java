package com.example.appproject;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(List<SliderItem> itemList);
    void  onFirebaseLoadFailed(String message);

}
