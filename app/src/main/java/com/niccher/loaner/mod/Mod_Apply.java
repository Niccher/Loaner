package com.niccher.loaner.mod;

/**
 * Created by niccher on 04/06/19.
 */

public class Mod_Apply {
    String gUid, gTime, gAmount, gReason, gAccepted, gDuration;

    public Mod_Apply() {
    }

    public Mod_Apply(String gUid, String gTime, String gAmount, String gReason, String gAccepted, String gDuration) {
        this.gUid = gUid;
        this.gTime = gTime;
        this.gAmount = gAmount;
        this.gReason = gReason;
        this.gAccepted = gAccepted;
        this.gDuration = gDuration;
    }

    public String getgUid() {
        return gUid;
    }

    public void setgUid(String gUid) {
        this.gUid = gUid;
    }

    public String getgTime() {
        return gTime;
    }

    public void setgTime(String gTime) {
        this.gTime = gTime;
    }

    public String getgAmount() {
        return gAmount;
    }

    public void setgAmount(String gAmount) {
        this.gAmount = gAmount;
    }

    public String getgReason() {
        return gReason;
    }

    public void setgReason(String gReason) {
        this.gReason = gReason;
    }

    public String getgAccepted() {
        return gAccepted;
    }

    public void setgAccepted(String gAccepted) {
        this.gAccepted = gAccepted;
    }

    public String getgDuration() {
        return gDuration;
    }

    public void setgDuration(String gDuration) {
        this.gDuration = gDuration;
    }
}