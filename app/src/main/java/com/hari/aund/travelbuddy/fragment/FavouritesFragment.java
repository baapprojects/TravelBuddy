package com.hari.aund.travelbuddy.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.MainActivity;
import com.hari.aund.travelbuddy.adapter.PlacesListAdapter;
import com.hari.aund.travelbuddy.data.PlacesCategoryValues;
import com.hari.aund.travelbuddy.data.provider.PlaceColumns;
import com.hari.aund.travelbuddy.data.provider.Places;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouritesFragment extends Fragment
        implements View.OnClickListener, DefaultValues, TextWatcher,
        AdapterView.OnItemClickListener {

    private static final String LOG_TAG = FavouritesFragment.class.getSimpleName();

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final int FILTER_ALL_PLACES = 1;
    private static final int FILTER_CATEGORY = 2;
    private static final int FILTER_SUB_TYPE = 3;
    private static final String ALL_PLACES_TITLE = "All Places";

    private int mNavSectionId;
    private int mSelectedFilterId;
    private int mCategoryIndex;
    private String mNavSectionName;
    private String mSelectedCategoryName;
    private String mSelectedSubTypeName;
    private ActionBar mActionBar = null;
    private SharedPreferences mSharedPreferences;
    private TextView mNoDataTextView;
    private ProgressWheel mProgressWheel;
    private FloatingActionMenu fabMenu;
    private FloatingActionButton allPlacesFab, categoryFab, subTypeFab;
    private ArrayAdapter<String> mSubTypeArrayAdapter;
    private ArrayList<String> mSubTypeArrayList;
    private AlertDialog mSubTypeAlertDialog;
    private PlacesListAdapter mPlacesListAdapter;
    private RecyclerView mRecyclerView;
    private Cursor mPlacesListCursor;

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

//        mSubTypeArrayAdapter = PlacesCategoryValues.getSubTypeAsArrayAdapter(getContext());
        mSubTypeArrayList = PlacesCategoryValues.getSubTypeAsArrayList();
        mSubTypeArrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.select_dialog_singlechoice, mSubTypeArrayList);

        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);

        mProgressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel_favourites);
        mProgressWheel.spin();

        mNoDataTextView = (TextView) rootView.findViewById(R.id.no_data_to_display_fav);
        mNoDataTextView.setVisibility(View.INVISIBLE);

        fabMenu = (FloatingActionMenu) rootView.findViewById(R.id.fab_favourites_menu);
        allPlacesFab = (FloatingActionButton) rootView.findViewById(R.id.fab_favourites_all_places);
        categoryFab = (FloatingActionButton) rootView.findViewById(R.id.fab_favourites_category);
        subTypeFab = (FloatingActionButton) rootView.findViewById(R.id.fab_favourites_sub_type);

        fabMenu.setOnClickListener(this);
        fabMenu.close(true);
        allPlacesFab.setOnClickListener(this);
        categoryFab.setOnClickListener(this);
        subTypeFab.setOnClickListener(this);

        StaggeredGridLayoutManager sGridLayoutManager = new StaggeredGridLayoutManager(
                DEFAULT_COLUMN_COUNT_1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(sGridLayoutManager);

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
        mPreferenceEditor.putInt(Utility.KEY_FILTER_ID,
                getSelectedFilterId());
        mPreferenceEditor.putString(Utility.KEY_FILTER_NAME,
                getFilterName());
        mPreferenceEditor.putInt(Utility.KEY_CATEGORY_ID,
                getCategoryIndex());
        mPreferenceEditor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        int filterId = FILTER_ALL_PLACES;
        String filterName = ALL_PLACES_TITLE;

        if (getNavSectionId() == 0 || getSelectedFilterId() == 0 ||
                getCategoryIndex() == 0) {

            if (getNavSectionId() == 0) {
                setNavSectionId(mSharedPreferences.getInt(
                        Utility.KEY_NAVIGATION_SECTION_ID, Utility.NAV_SECTION_EXPLORE_PLACES));
                setNavSectionName(Utility.getNavSectionName(getContext(), getNavSectionId()));
            }

            filterId = mSharedPreferences.getInt(Utility.KEY_FILTER_ID,
                    FILTER_ALL_PLACES);
            filterName = mSharedPreferences.getString(Utility.KEY_FILTER_NAME,
                    ALL_PLACES_TITLE);
            setCategoryIndex(mSharedPreferences.getInt(Utility.KEY_CATEGORY_ID,
                    DEFAULT_CATEGORY_ID));

            mProgressWheel.stopSpinning();
        }

        setFragmentTitle(filterId, filterName);
        setFabImageResource();
        createAndAddAdapterToView();
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
                        setCategoryIndex(which);
                        dialog.dismiss();
                    }
                }
        );
        builder.show();
        return true;
    }

    private boolean displaySubTypeSelectorList() {
        //TODO: fetch data from Database & display it.

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        final EditText editTextSearch = new EditText(getContext());
        editTextSearch.addTextChangedListener(this);

        ListView listView = new ListView(getContext());
        listView.setAdapter(mSubTypeArrayAdapter);
        listView.setOnItemClickListener(this);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(editTextSearch);
        linearLayout.addView(listView);

        /*
        String defaultSubTypeStr = (mSelectedSubTypeName == null ?
                PlacesCategoryValues.bankingSubTypes[0] : mSelectedSubTypeName);
        listView.setItemChecked(mSubTypeArrayAdapter.getPosition(defaultSubTypeStr), true);
        */

        alertDialogBuilder.setTitle("Select a Sub Type as Filter");
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSubTypeArrayAdapter.getFilter().filter("");
                        dialog.dismiss();
                        fabMenu.close(true);
                    }
                }
        );
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String searchText = editTextSearch.getText().toString();
                        Log.d(LOG_TAG, "Search Text in EditText - " + searchText);
                        if (mSubTypeArrayList.contains(searchText)) {
                            setLocalValuesAndUpdateUI(FILTER_SUB_TYPE,
                                    searchText);
                        } else {
                            Log.d(LOG_TAG, "Search Text [" + searchText + "] is not matching");
                            fabMenu.close(true);

                            if (!searchText.isEmpty())
                                Toast.makeText(getContext(), "No such subType filter", Toast.LENGTH_SHORT)
                                        .show();
                        }
                        mSubTypeArrayAdapter.getFilter().filter("");
                    }
                }
        );

        mSubTypeAlertDialog = alertDialogBuilder.show();
        return true;
    }

    private void setLocalValuesAndUpdateUI(int filterId, String titleStr) {
        setFragmentTitle(filterId, titleStr);
        setFabImageResource();

        fabMenu.close(true);
        mProgressWheel.stopSpinning();

        createAndAddAdapterToView();
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

    private void createAndAddAdapterToView() {
        fetchCursorFromDatabase();
    }

    private void fetchCursorFromDatabase() {
        try {
            mPlacesListCursor = getCursorForFilter();
            //DatabaseUtils.dumpCursor(mPlacesListCursor);
            if (mPlacesListCursor != null && mPlacesListCursor.moveToFirst()) {
                mNoDataTextView.setVisibility(View.INVISIBLE);
                Log.d(LOG_TAG, "fetchCursorFromDatabase : Entries[" +
                        mPlacesListCursor.getCount() + "] present in DB!");
            } else {
                mNoDataTextView.setVisibility(View.VISIBLE);
                Log.d(LOG_TAG, "fetchCursorFromDatabase : No Entry present in DB!");
            }
            mProgressWheel.stopSpinning();
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "addToFavourites : NullPointerException@try for Cursor!");
            e.printStackTrace();
        } finally {
            try {
                if (mPlacesListCursor != null)
                    mPlacesListCursor.close();
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "addToFavourites : NullPointerException@finally for Cursor!");
                e.printStackTrace();
            }
        }
    }

    private Cursor getCursorForFilter() {
        switch (getSelectedFilterId()) {
            case FILTER_ALL_PLACES:
                return getActivity().getContentResolver().query(
                        Places.CONTENT_URI_PLACES,
                        null,
                        null,
                        null,
                        null);
            case FILTER_CATEGORY:
                return getActivity().getContentResolver().query(
                        Places.CONTENT_URI_CATEGORY,
                        null,
                        PlaceColumns.CATEGORY_ID + " = ?",
                        new String[]{getCategoryIndexStr()},
                        /*Alternate: PlacesCategoryValues.getPlacesCategoryPosition(
                                getSelectedCategoryName());*/
                        null);
            case FILTER_SUB_TYPE:
                return getActivity().getContentResolver().query(
                        Places.CONTENT_URI_SUB_TYPE,
                        null,
                        PlaceColumns.SUB_TYPE_NAME + " = ?",
                        new String[]{mSelectedSubTypeName},
                        null);
        }

        return null;
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

    public int getSelectedFilterId() {
        return mSelectedFilterId;
    }

    public void setSelectedFilterId(int selectedFilterId) {
        mSelectedFilterId = selectedFilterId;
    }

    public String getCategoryIndexStr() {
        return mCategoryIndex + "";
    }

    public int getCategoryIndex() {
        return mCategoryIndex;
    }

    public void setCategoryIndex(int categoryIndex) {
        mCategoryIndex = categoryIndex;
    }

    public String getSelectedCategoryName() {
        return mSelectedCategoryName;
    }

    public void setSelectedCategoryName(String selectedCategoryName) {
        mSelectedCategoryName = selectedCategoryName;
    }

    public String getSelectedSubTypeName() {
        return mSelectedSubTypeName;
    }

    public void setSelectedSubTypeName(String selectedSubTypeName) {
        mSelectedSubTypeName = selectedSubTypeName;
    }

    private String getFilterName() {
        switch (mSelectedFilterId) {
            case FILTER_ALL_PLACES:
                return ALL_PLACES_TITLE;
            case FILTER_CATEGORY:
                return getSelectedCategoryName();
            case FILTER_SUB_TYPE:
                return getSelectedSubTypeName();
        }
        return ALL_PLACES_TITLE;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        mSubTypeArrayAdapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        setLocalValuesAndUpdateUI(FILTER_SUB_TYPE,
                mSubTypeArrayAdapter.getItem(position));
        mSubTypeArrayAdapter.getFilter().filter("");
        mSubTypeAlertDialog.dismiss();
    }
}
