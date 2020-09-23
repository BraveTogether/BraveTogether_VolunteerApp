package com.example.bravetogether_volunteerapp.ui.settings;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bravetogether_volunteerapp.R;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ImageView> mpicture;

    public SettingsViewModel() {
        mpicture = new MutableLiveData<ImageView>();
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}