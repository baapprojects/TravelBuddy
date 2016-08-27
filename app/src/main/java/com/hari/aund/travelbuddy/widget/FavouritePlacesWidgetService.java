package com.hari.aund.travelbuddy.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Hari Nivas Kumar R P on 8/27/2016.
 */
public class FavouritePlacesWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavouritePlacesWidgetDataProvider(this, intent);
    }
}
