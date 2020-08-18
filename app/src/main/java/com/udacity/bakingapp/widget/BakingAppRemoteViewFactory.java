package com.udacity.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.database.AppDatabase;
import com.udacity.bakingapp.database.ShoppingListDao;
import com.udacity.bakingapp.database.ShoppingListEntry;

import java.util.List;

public class BakingAppRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    List<ShoppingListEntry> mShoppingListEntries;
    private Context mContext;
    private AppDatabase mDatabase;

    public BakingAppRemoteViewFactory(Context applicationContext) {
        mContext = applicationContext;
        mDatabase = AppDatabase.getInstance(applicationContext);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        ShoppingListDao shoppingListDao = mDatabase.shoppingListDao();
        mShoppingListEntries = shoppingListDao.loadAllIngredients();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return (mShoppingListEntries != null) ? mShoppingListEntries.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mShoppingListEntries == null || mShoppingListEntries.size() < position) {
            return null;
        }

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget_list_item);
        ShoppingListEntry shoppingListEntry = mShoppingListEntries.get(position);
        String quantity = shoppingListEntry.getQuantity();
        String measurement = shoppingListEntry.getMeasurement();
        String name = shoppingListEntry.getName();
        remoteViews.setTextViewText(R.id.widgetItemTaskNameLabel, quantity + " " + measurement + " of " + name);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(BakingAppWidgetProvider.EXTRA_RECIPE_ID, shoppingListEntry.getRecipeId());
        remoteViews.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
