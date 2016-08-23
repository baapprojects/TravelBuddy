package com.hari.aund.travelbuddy.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.MainActivity;
import com.hari.aund.travelbuddy.data.PlacesCategoryValues;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouritesFragment extends Fragment
        implements View.OnClickListener, DefaultValues {

    private static final String LOG_TAG = FavouritesFragment.class.getSimpleName();

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final int FILTER_ALL_PLACES = 1;
    private static final int FILTER_CATEGORY = 2;
    private static final int FILTER_SUB_TYPE = 3;
    private static final String ALL_PLACES_TITLE = "All Places";

    private int mNavSectionId;
    private int mSelectedFilterId = FILTER_ALL_PLACES;
    private String mNavSectionName;
    private String mSelectedCategoryName;
    private String mSelectedSubTypeName;
    private ActionBar mActionBar = null;
    private SharedPreferences mSharedPreferences;
    private ProgressWheel mProgressWheel;
    private FloatingActionMenu fabMenu;
    private FloatingActionButton allPlacesFab, categoryFab, subTypeFab;

    public FavouritesFragment() {
    }

    public static FavouritesFragment getNewInstance(int navSectionId) {
        FavouritesFragment FavouritesFragment = new FavouritesFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Utility.KEY_NAVIGATION_SECTION_ID, navSectionId);
        FavouritesFragment.setArguments(bundle);

        return FavouritesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActionBar = ((MainActivity) getActivity()).getSupportActionBar();

        mSharedPreferences = getActivity().getPreferences(PREFERENCE_MODE_PRIVATE);
        readValues();
        setFragmentTitle(FILTER_ALL_PLACES, ALL_PLACES_TITLE);

        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);

        mProgressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel_favourites);
        mProgressWheel.spin();

        fabMenu = (FloatingActionMenu) rootView.findViewById(R.id.fab_favourites_menu);
        allPlacesFab = (FloatingActionButton) rootView.findViewById(R.id.fab_favourites_all_places);
        categoryFab = (FloatingActionButton) rootView.findViewById(R.id.fab_favourites_category);
        subTypeFab = (FloatingActionButton) rootView.findViewById(R.id.fab_favourites_sub_type);

        fabMenu.setOnClickListener(this);
        fabMenu.close(true);
        allPlacesFab.setOnClickListener(this);
        categoryFab.setOnClickListener(this);
        subTypeFab.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor mPreferenceEditor = mSharedPreferences.edit();
        mPreferenceEditor.putInt(Utility.KEY_NAVIGATION_SECTION_ID,
                getNavSectionId());
        mPreferenceEditor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getNavSectionId() == 0) {
            setNavSectionId(mSharedPreferences.getInt(
                    Utility.KEY_NAVIGATION_SECTION_ID, Utility.NAV_SECTION_EXPLORE_PLACES));
            setNavSectionName(Utility.getNavSectionName(getContext(), getNavSectionId()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_favourites_all_places:
                setLocalValuesAndUpdateUI(FILTER_ALL_PLACES, ALL_PLACES_TITLE);
                break;
            case R.id.fab_favourites_category:
                if (!displayCategorySelectorList())
                    Toast.makeText(getContext(), "No Category Present", Toast.LENGTH_SHORT)
                            .show();
                break;
            case R.id.fab_favourites_sub_type:
                if (!displaySubTypeSelectorList())
                    Toast.makeText(getContext(), "No Sub Type Present", Toast.LENGTH_SHORT)
                            .show();
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void readValues() {
        if (getArguments() != null) {
            setNavSectionId(getArguments().getInt(
                    Utility.KEY_NAVIGATION_SECTION_ID));
            setNavSectionName(Utility.getNavSectionName(
                    getContext(), getNavSectionId()));
        }
    }

    private boolean displayCategorySelectorList() {
        //TODO: fetch data from Database & display it.
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.select_dialog_singlechoice);

        for (int index = 0; index < PlacesCategoryValues.MAX_PLACES_CATEGORIES; index++)
            arrayAdapter.add(PlacesCategoryValues.placesCategories[index]);

        String defaultCategoryStr = (mSelectedCategoryName == null ?
                PlacesCategoryValues.placesCategories[0] : mSelectedCategoryName);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select a Category as Filter");
        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        builder.setSingleChoiceItems(arrayAdapter,
                arrayAdapter.getPosition(defaultCategoryStr),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setLocalValuesAndUpdateUI(FILTER_CATEGORY,
                                arrayAdapter.getItem(which));
                        dialog.dismiss();
                    }
                }
        );
        builder.show();
        return true;
    }

    private boolean displaySubTypeSelectorList() {
        //TODO: fetch data from Database & display it.
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.select_dialog_singlechoice);

        arrayAdapter.add(PlacesCategoryValues.bankingSubTypes[0]);
        arrayAdapter.add(PlacesCategoryValues.commuteSubTypes[0]);
        arrayAdapter.add(PlacesCategoryValues.emergencySubTypes[0]);
        arrayAdapter.add(PlacesCategoryValues.hangoutSubTypes[0]);
        arrayAdapter.add(PlacesCategoryValues.healthCareSubTypes[0]);
        arrayAdapter.add(PlacesCategoryValues.personalCareSubTypes[0]);
        arrayAdapter.add(PlacesCategoryValues.restaurantSubTypes[0]);
        arrayAdapter.add(PlacesCategoryValues.shoppingSubTypes[0]);
        arrayAdapter.add(PlacesCategoryValues.templeSubTypes[0]);
        arrayAdapter.add(PlacesCategoryValues.touristsSpotSubTypes[0]);

        String defaultSubTypeStr = (mSelectedSubTypeName == null ?
                PlacesCategoryValues.bankingSubTypes[0] : mSelectedSubTypeName);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select a Sub Type as Filter");
        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        builder.setSingleChoiceItems(arrayAdapter,
                arrayAdapter.getPosition(defaultSubTypeStr),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setLocalValuesAndUpdateUI(FILTER_SUB_TYPE,
                                arrayAdapter.getItem(which));
                        dialog.dismiss();
                    }
                }
        );
        builder.show();
        return true;
    }

    private void setLocalValuesAndUpdateUI(int filterId, String titleStr){
        setFragmentTitle(filterId, titleStr);
        setFabImageResource();

        fabMenu.close(true);
        mProgressWheel.stopSpinning();
    }

    private void setFragmentTitle(int filterId, String titleStr) {
        String titleStrPrefix = "Favourites - ";
        mSelectedFilterId = filterId;
        if (mActionBar != null &&
                titleStr != null && !titleStr.isEmpty()) {
            switch (mSelectedFilterId) {
                case FILTER_ALL_PLACES:
                    break;
                case FILTER_CATEGORY:
                    mSelectedCategoryName = titleStr;
                    break;
                case FILTER_SUB_TYPE:
                    mSelectedSubTypeName = titleStr;
                    break;
            }
            titleStrPrefix += titleStr;
            mActionBar.setTitle(titleStrPrefix);
        }
    }

    private void setFabImageResource() {
        int enabledDrawableId = R.drawable.ic_filter_list_black_24dp;
        int disabledDrawableId = R.drawable.ic_filter_list_white_24dp;
        switch (mSelectedFilterId) {
            case FILTER_ALL_PLACES:
                allPlacesFab.setImageResource(enabledDrawableId);
                categoryFab.setImageResource(disabledDrawableId);
                subTypeFab.setImageResource(disabledDrawableId);
                break;
            case FILTER_CATEGORY:
                allPlacesFab.setImageResource(disabledDrawableId);
                categoryFab.setImageResource(enabledDrawableId);
                subTypeFab.setImageResource(disabledDrawableId);
                break;
            case FILTER_SUB_TYPE:
                allPlacesFab.setImageResource(disabledDrawableId);
                categoryFab.setImageResource(disabledDrawableId);
                subTypeFab.setImageResource(enabledDrawableId);
                break;
        }
    }

    private void setNavSectionId(int navSectionId) {
        this.mNavSectionId = navSectionId;
    }

    private int getNavSectionId() {
        return mNavSectionId;
    }

    private void setNavSectionName(String navSectionName) {
        this.mNavSectionName = navSectionName;
    }

    private String getNavSectionName() {
        return mNavSectionName;
    }
}
