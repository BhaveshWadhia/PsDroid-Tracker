package com.example.psdroid.ui.notifications;
//model class for recyclerview of notification
public class ModelNotification {
    String uname, user, timestamp, pUid, mobile, notification, sUid;

    public ModelNotification(){
        // Constructor for FireBase (required)//
    }

    // Constructor
    public ModelNotification(String uname, String user, String timestamp, String pUid, String mobile, String notification, String sUid)
    {
        this.uname = uname;
        this.user = user;
        this.timestamp = timestamp;
        this.pUid = pUid;
        this.mobile = mobile;
        this.notification = notification;
        this.sUid = sUid;
    }
    // Getter & Setter Function
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getpUid() {
        return pUid;
    }
    public void setpUid(String pUid) {
        this.pUid = pUid;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getNotification() {
        return notification;
    }
    public void setNotification(String notification) {
        this.notification = notification;
    }
    public String getsUid() {
        return sUid;
    }
    public void setsUid(String sUid) {
        this.sUid = sUid;
    }
}

