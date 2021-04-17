package com.example.psdroid.ui.login;

public class UserHeplerClass {

    String email,user,mobile,pass,conpass;

    public UserHeplerClass() {
    }

    public UserHeplerClass( String user,String email,String mobile,String pass,String conpass ) {
        this.email = email;
        this.user = user;
        this.mobile = mobile;
        this.pass = pass;
        this.conpass = conpass;


    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getConpass() {
        return conpass;
    }

    public void setConpass(String conpass) {
        this.conpass = conpass;
    }
}


