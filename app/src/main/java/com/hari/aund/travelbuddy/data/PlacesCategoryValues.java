package com.hari.aund.travelbuddy.data;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.PlacesCategoryActivity;

/**
 * Created by Hari Nivas Kumar R P on 8/15/2016.
 */
public class PlacesCategoryValues {

    public static final int MAX_PLACES_CATEGORIES = 10;

    public static final int PLACES_CATEGORY_BANKING_ID = 0;
    public static final int PLACES_CATEGORY_COMMUTE_ID = 1;
    public static final int PLACES_CATEGORY_EMERGENCY_ID = 2;
    public static final int PLACES_CATEGORY_HANGOUT_ID = 3;
    public static final int PLACES_CATEGORY_HEALTH_CARE_ID = 4;
    public static final int PLACES_CATEGORY_PERSONAL_CARE_ID = 5;
    public static final int PLACES_CATEGORY_RESTAURANT_ID = 6;
    public static final int PLACES_CATEGORY_SHOPPING_ID = 7;
    public static final int PLACES_CATEGORY_TEMPLE_ID = 8;
    public static final int PLACES_CATEGORY_TOURISTS_SPOT_ID = 9;

    public static final String placesCategories[] = new String[]{
            "Banking", "Commute", "Emergency", "Hangout", "Health Care",
            "Personal Care", "Restaurant", "Shopping", "Temple", "Tourists Spot"
    };

    public static final String bankingSubTypes[] = new String[]{
            "accounting",
            "atm",
            "bank"
    };

    public static final String commuteSubTypes[] = new String[]{
            "subway_station",
            "taxi_stand",
            "airport",
            "bus_station",
            "car_rental",
            "gas_station",
            "parking",
            "train_station"
    };

    public static final String emergencySubTypes[] = new String[]{
            "fire_station",
            "lawyer",
            "police",
            "post_office"
    };

    public static final String hangoutSubTypes[] = new String[]{
            "bowling_alley",
            "casino",
            "movie_theater",
            "night_club",
            "park"
    };

    public static final String healthCareSubTypes[] = new String[]{
            "dentist",
            "doctor",
            "hospital",
            "pharmacy",
            "physiotherapist",
            "veterinary_care"
    };

    public static final String personalCareSubTypes[] = new String[]{
            "beauty_salon",
            "gym",
            "hair_care",
            "spa"
    };

    public static final String restaurantSubTypes[] = new String[]{
            "bakery",
            "bar",
            "cafe",
            "liquor_store",
            "restaurant"
    };

    public static final String shoppingSubTypes[] = new String[]{
            "book_store",
            "city_hall",
            "clothing_store",
            "department_store",
            "electronics_store",
            "jewelry_store",
            "shoe_store",
            "shopping_mall"
    };

    public static final String templeSubTypes[] = new String[]{
            "church",
            "hindu_temple",
            "mosque"
    };

    public static final String touristsSpotSubTypes[] = new String[]{
            "amusement_park",
            "aquarium",
            "art_gallery",
            "museum",
            "zoo"
    };

    public static final int categoriesSubTypeCountDef[] = new int[]{
            3, 8, 4, 5, 6, 4, 5, 8, 3, 5
    };

    public static final int categoriesSubTypeCount[] = new int[]{
            bankingSubTypes.length,
            commuteSubTypes.length,
            emergencySubTypes.length,
            hangoutSubTypes.length,
            healthCareSubTypes.length,
            personalCareSubTypes.length,
            restaurantSubTypes.length,
            shoppingSubTypes.length,
            templeSubTypes.length,
            touristsSpotSubTypes.length
    };

    public static String getSubType(int categoryId, int position){
        if (position >= categoriesSubTypeCount[categoryId]){
            return null;
        }
        switch (categoryId){
            case PLACES_CATEGORY_BANKING_ID:
                return bankingSubTypes[position];
            case PLACES_CATEGORY_COMMUTE_ID:
                return commuteSubTypes[position];
            case PLACES_CATEGORY_EMERGENCY_ID:
                return emergencySubTypes[position];
            case PLACES_CATEGORY_HANGOUT_ID:
                return hangoutSubTypes[position];
            case PLACES_CATEGORY_HEALTH_CARE_ID:
                return healthCareSubTypes[position];
            case PLACES_CATEGORY_PERSONAL_CARE_ID:
                return personalCareSubTypes[position];
            case PLACES_CATEGORY_RESTAURANT_ID:
                return restaurantSubTypes[position];
            case PLACES_CATEGORY_SHOPPING_ID:
                return shoppingSubTypes[position];
            case PLACES_CATEGORY_TEMPLE_ID:
                return templeSubTypes[position];
            case PLACES_CATEGORY_TOURISTS_SPOT_ID:
                return touristsSpotSubTypes[position];
        }
        return null;
    }

    public static final int PLACES_CATEGORY_A_ACTIVITY = 1;
    public static final int PLACES_CATEGORY_B_ACTIVITY = 2;
    public static final int PLACES_CATEGORY_C_ACTIVITY = 3;
    public static final int PLACES_CATEGORY_INVALID = 4;

    public static int getCategoryActivityId(int categorySubTypeCount){
        if (categorySubTypeCount > 2 && categorySubTypeCount < 5){
            return PLACES_CATEGORY_A_ACTIVITY;
        } else if (categorySubTypeCount < 7){
            return PLACES_CATEGORY_B_ACTIVITY;
        } else if (categorySubTypeCount < 9){
            return PLACES_CATEGORY_C_ACTIVITY;
        }
        return PLACES_CATEGORY_INVALID;
    }

    public static Class getCategoryActivityClass(int categoryActivityId){
        return PlacesCategoryActivity.class;
    }

    public static int getCategoryImageResourceId(int categoryId){
        switch (categoryId){
            case PLACES_CATEGORY_BANKING_ID:
                return R.drawable.ic_restaurant;
            case PLACES_CATEGORY_COMMUTE_ID:
                return R.drawable.ic_movie;
            case PLACES_CATEGORY_EMERGENCY_ID:
                return R.drawable.ic_food_court;
            case PLACES_CATEGORY_HANGOUT_ID:
                return R.drawable.ic_movie;
            case PLACES_CATEGORY_HEALTH_CARE_ID:
                return R.drawable.ic_hotel;
            case PLACES_CATEGORY_PERSONAL_CARE_ID:
                return R.drawable.ic_hotel;
            case PLACES_CATEGORY_RESTAURANT_ID:
                return R.drawable.ic_restaurant;
            case PLACES_CATEGORY_SHOPPING_ID:
                return R.drawable.ic_shopping;
            case PLACES_CATEGORY_TEMPLE_ID:
                return R.drawable.ic_tourists;
            case PLACES_CATEGORY_TOURISTS_SPOT_ID:
                return R.drawable.ic_tourists;
        }
        return R.drawable.ic_tourists;
    }
}
