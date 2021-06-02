package com.example.psdroid.ui.home;

public class WRYHelperClass {
    String user,uname,timestamp,hisUid,mob,not;

    public WRYHelperClass(String user, String uname, String timestamp, String hisUid, String mob, String not) {
        this.user = user;
        this.uname = uname;
        this.timestamp = timestamp;
        this.hisUid = hisUid;
        this.mob = mob;
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

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }
}
