package com.udacity.bakingapp.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class BakingAppRemoteViewsService extends RemoteViewsService {
    private static final String TAG = "[Debug]" + BakingAppRemoteViewsService.class.getSimpleName();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: ");
        return new BakingAppRemoteViewFactory(this.getApplicationContext());
    }
}
