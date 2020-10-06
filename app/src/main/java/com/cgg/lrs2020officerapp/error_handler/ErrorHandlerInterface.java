package com.cgg.lrs2020officerapp.error_handler;

import android.content.Context;

public interface ErrorHandlerInterface {
    void handleError(Throwable e, Context context);
    void handleError(String e, Context context);
}
