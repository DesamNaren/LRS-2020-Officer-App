package com.cgg.lrs2020officerapp.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.lrs2020officerapp.databinding.ActivityListBinding;


public class AddScrutinyCustomViewModel implements ViewModelProvider.Factory {
    private Context context;

    public AddScrutinyCustomViewModel(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddScrutinyViewModel( context);
    }
}