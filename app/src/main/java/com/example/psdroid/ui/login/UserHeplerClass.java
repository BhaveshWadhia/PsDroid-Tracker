package com.example.psdroid.ui.login;
//User Helper Class
public class UserHeplerClass {
    String email,user,mobile,pass,conpass,name;

  //Constructor
    public UserHeplerClass( String user,String name,String email,String mobile,String pass,String conpass ) {
        this.email = email;
        this.name = name;
        this.user = user;
        this.mobile = mobile;
        this.pass = pass;
        this.conpass = conpass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Getter & Setter Function
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


