package com.prajwal.restaurant.ui.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuGalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MenuGalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is menu fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
