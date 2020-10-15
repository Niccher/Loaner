package com.niccher.loaner.mod;

/**
 * Created by niccher on 04/06/19.
 */

public class Mod_UserConfig {
    String gUid, gFname, gLname, gPhone, gEmail, gDateBirth, gNationalid, gDate, gPwd;

    public Mod_UserConfig() {
    }

    public Mod_UserConfig(String gUid, String gFname, String gLname, String gPhone, String gEmail, String gDateBirth, String gNationalid, String gDate, String gPwd) {
        this.gUid = gUid;
        this.gFname = gFname;
        this.gLname = gLname;
        this.gPhone = gPhone;
        this.gEmail = gEmail;
        this.gDateBirth = gDateBirth;
        this.gNationalid = gNationalid;
        this.gDate = gDate;
        this.gPwd = gPwd;
    }

    public String getgUid() {
        return gUid;
    }

    public void setgUid(String gUid) {
        this.gUid = gUid;
    }

    public String getgFname() {
        return gFname;
    }

    public void setgFname(String gFname) {
        this.gFname = gFname;
    }

    public String getgLname() {
        return gLname;
    }

    public void setgLname(String gLname) {
        this.gLname = gLname;
    }

    public String getgPhone() {
        return gPhone;
    }

    public void setgPhone(String gPhone) {
        this.gPhone = gPhone;
    }

    public String getgEmail() {
        return gEmail;
    }

    public void setgEmail(String gEmail) {
        this.gEmail = gEmail;
    }

    public String getgDateBirth() {
        return gDateBirth;
    }

    public void setgDateBirth(String gDateBirth) {
        this.gDateBirth = gDateBirth;
    }

    public String getgNationalid() {
        return gNationalid;
    }

    public void setgNationalid(String gNationalid) {
        this.gNationalid = gNationalid;
    }

    public String getgDate() {
        return gDate;
    }

    public void setgDate(String gDate) {
        this.gDate = gDate;
    }

    public String getgPwd() {
        return gPwd;
    }

    public void setgPwd(String gPwd) {
        this.gPwd = gPwd;
    }
}