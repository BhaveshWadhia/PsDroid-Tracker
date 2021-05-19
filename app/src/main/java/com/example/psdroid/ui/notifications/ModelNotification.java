package com.example.psdroid.ui.notifications;

//model class for recyclerview of notification
public class ModelNotification {
String pId, timestamp,pUid,notification,sUid,sUsername,sEmail;
//for firebase 
    public ModelNotification() {

    }

    public ModelNotification( String timestamp, String notification, String sUsername, String sEmail,String sUid) {
        //this.pId = pId;
        this.timestamp = timestamp;
        //this.pUid = pUid;
        this.notification = notification;
        this.sUid = sUid;
        this.sUsername = sUsername;
        this.sEmail = sEmail;
    }

   // public String getpId() {
   //     return pId;
   // }

//    public void setpId(String pId) {
  //      this.pId = pId;
   // }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

  /*  public String getpUid() {
        return pUid;
    }

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }
*/
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

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }
}
