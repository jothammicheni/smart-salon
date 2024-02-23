package com.example.witxsalon.ui.sell;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Boolean> isAdminLoginVisible = new MutableLiveData<>();

    private final MutableLiveData<String> mText;

    public SellViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Admin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    // Method to observe the visibility of admin login
    LiveData<Boolean> getIsAdminLoginVisible() {
        return isAdminLoginVisible;
    }
    // Method to update the visibility of admin login
    void setAdminLoginVisible(boolean isVisible) {
        isAdminLoginVisible.setValue(isVisible);
    }

}