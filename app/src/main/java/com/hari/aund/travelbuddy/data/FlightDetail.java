package com.hari.aund.travelbuddy.data;

import com.hari.aund.travelbuddy.R;

import java.util.HashMap;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public class FlightDetail {

    private final static String AIR_INDIA_FLIGHT_ID = "AI";
    private final static String AIR_CANADA_FLIGHT_ID = "AC";
    private final static String ETHIOPIA_FLIGHT_ID = "ET";
    private final static String GO_AIR_FLIGHT_ID = "G8";
    private final static String INDIGO_FLIGHT_ID = "6E";
    private final static String JET_AIRWAYS_FLIGHT_ID = "9W";
    private final static String JET_LITE_FLIGHT_ID = "S2";
    private final static String SPICE_JET_FLIGHT_ID = "SG";
    private final static String TURKISH_FLIGHT_ID = "TK";
    private final static String VISTARA_FLIGHT_ID = "UK";

    private final String LOCALE_CURRENCY_COUNTRY = "India";
    private final String CURRENCY_PREFIX = "Rs.";
    private final int CURRENCY_CONVERSION_RATE = 67;

    private final String TERMINAL_PREFIX = "Departure Terminal : ";

    private Double price;
    private Double duration;
    private String sTime;
    private String tTime;
    private String flight;
    private String airline;
    private String terminal;
    private HashMap<String, Integer> airlineLogo;


    public FlightDetail(){
        initHashMap();
    }

    private void initHashMap(){
        airlineLogo = new HashMap<>();
        airlineLogo.put(AIR_INDIA_FLIGHT_ID, R.drawable.airindia_logo);
        airlineLogo.put(AIR_CANADA_FLIGHT_ID, R.drawable.aircanada_logo);
        airlineLogo.put(ETHIOPIA_FLIGHT_ID, R.drawable.ethiopian_logo);
        airlineLogo.put(GO_AIR_FLIGHT_ID, R.drawable.goair_logo);
        airlineLogo.put(INDIGO_FLIGHT_ID, R.drawable.indigo_logo);
        airlineLogo.put(JET_AIRWAYS_FLIGHT_ID, R.drawable.jetairways_logo);
        airlineLogo.put(JET_LITE_FLIGHT_ID, R.drawable.jetlite_logo);
        airlineLogo.put(SPICE_JET_FLIGHT_ID, R.drawable.spicejet_logo);
        airlineLogo.put(TURKISH_FLIGHT_ID, R.drawable.turkish_logo);
        airlineLogo.put(VISTARA_FLIGHT_ID, R.drawable.vistara_logo);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String gettTime() {
        return tTime;
    }

    public void settTime(String tTime) {
        this.tTime = tTime;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public int getDefaultFlightIcon(){
        return R.drawable.airindia_logo;
    }

    public int getFlightIcon() {
        if (airlineLogo.containsKey(getAirline())) {
            return airlineLogo.get(getAirline());
        }
        return (getDefaultFlightIcon());
    }

    public String getPriceValue(){
        return CURRENCY_PREFIX +
                getPrice() * CURRENCY_CONVERSION_RATE;
    }

    public String getTerminalFullStr(){
        return TERMINAL_PREFIX + getTerminal();
    }
}
