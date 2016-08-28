package com.hari.aund.travelbuddy.data;

/**
 * Created by Hari Nivas Kumar R P on 8/28/2016.
 */
public class ShareInfo {

    private static final String SHARE_INTENT_TITLE = "Share using";
    private static final String SHARE_APP_TITLE = "Travel Buddy - App : ";
    private static final String SHARE_APP_CONTENT =
            "Download Travel Buddy to help find places with ease.";
    private static final String SHARE_CONTENT_TYPE = "text/plain";

    private String mIntentTitle;
    private String mTitle;
    private String mContent;
    private String mType;

    public ShareInfo(){
        setIntentTitle(SHARE_INTENT_TITLE);
        setTitle(SHARE_APP_TITLE);
        setContent(SHARE_APP_CONTENT);
        setType(SHARE_CONTENT_TYPE);
    }

    public ShareInfo(String title, String content){
        title = SHARE_APP_TITLE + title;
        content = content + "\n\n" + SHARE_APP_CONTENT;

        setIntentTitle(SHARE_INTENT_TITLE);
        setTitle(title);
        setContent(content);
        setType(SHARE_CONTENT_TYPE);
    }

    public String getIntentTitle() {
        return mIntentTitle;
    }

    public void setIntentTitle(String intentTitle) {
        mIntentTitle = intentTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    private void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    private void setContent(String content) {
        mContent = content;
    }

    public String getType() {
        return mType;
    }

    private void setType(String type) {
        mType = type;
    }
}
