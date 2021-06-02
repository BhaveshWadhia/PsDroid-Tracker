package com.example.psdroid.ui.notifications;

public class NotificationHelperClass {
    String user,uname,timestamp,hisUid,lat,lon,not;

    public NotificationHelperClass(String user, String uname, String timestamp, String hisUid, String lat, String lon, String not) {
        this.user = user;
        this.uname = uname;
        this.timestamp = timestamp;
        this.hisUid = hisUid;
        this.lat = lat;
        this.lon = lon;
        this.not = not;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHisUid() {
        return hisUid;
    }

    public void setHisUid(String hisUid) {
        this.hisUid = hisUid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }
}
