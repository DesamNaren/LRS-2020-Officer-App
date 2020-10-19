package com.cgg.lrs2020officerapp.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class L2UploadCustomViewModel implements ViewModelProvider.Factory {
    private Context context;

    public L2UploadCustomViewModel(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new L2UploadViewModel( context);
    }
}
