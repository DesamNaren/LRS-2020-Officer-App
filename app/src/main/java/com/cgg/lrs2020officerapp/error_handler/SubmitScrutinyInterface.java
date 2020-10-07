package com.cgg.lrs2020officerapp.error_handler;

import android.content.Context;

import com.cgg.lrs2020officerapp.model.submit.SubmitScrutinyResponse;

import java.util.List;

public interface SubmitScrutinyInterface {
    void submitResponse(List<SubmitScrutinyResponse> responses);
}
