package com.prajwal.restaurant.ui.aboutUs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUs_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutUs_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}