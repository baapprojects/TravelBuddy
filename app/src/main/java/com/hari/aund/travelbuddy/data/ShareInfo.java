package com.hari.aund.travelbuddy.data;

/**
 * Created by Hari Nivas Kumar R P on 8/28/2016.
 */
public class ShareInfo {

    private static final String SHARE_INTENT_TITLE = "Share using";
    private static final String EMAIL_INTENT_TITLE = "Send Mail";

    private static final String APP_TITLE = "Travel Buddy - App : ";
    private static final String SHARE_TITLE = APP_TITLE;
    private static final String EMAIL_SUBJECT = APP_TITLE + "Queries";

    private static final String SHARE_CONTENT =
            "Download Travel Buddy to help find places with ease.";
    private static final String EMAIL_CONTENT = "Hi, ";

    private static final String SHARE_CONTENT_TYPE = "text/plain";
    private static final String EMAIL_CONTENT_TYPE = "plain/text";

    private String mIntentTitle;
    private String mEmail;
    private String mTitle;
    private String mContent;
    private String mType;

    public ShareInfo(){
        setIntentTitle(SHARE_INTENT_TITLE);
        setTitle(SHARE_TITLE);
        setContent(SHARE_CONTENT);
        setType(SHARE_CONTENT_TYPE);
    }

    public ShareInfo(String title, String content){
        title = SHARE_TITLE + title;
        content = content + "\n\n" + SHARE_CONTENT;

        setIntentTitle(SHARE_INTENT_TITLE);
        setTitle(title);
        setContent(content);
        setType(SHARE_CONTENT_TYPE);
    }

    public ShareInfo(String emailId){
        setIntentTitle(EMAIL_INTENT_TITLE);
        setEmail(emailId);
        setTitle(EMAIL_SUBJECT);
        setContent(EMAIL_CONTENT);
        setType(EMAIL_CONTENT_TYPE);
    }

    public String getIntentTitle() {
        return mIntentTitle;
    }

    public void setIntentTitle(String intentTitle) {
        mIntentTitle = intentTitle;
    }

    public String getEmail() {
        return mEmail;
    }

    private void setEmail(String email) {
        mEmail = email;
    }

    public boolean hasEmail(){
        return (getEmail() != null && !getEmail().isEmpty());
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
