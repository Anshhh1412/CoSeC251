package com.covidselfcare.cosecv3;

import com.anychart.anychart.Pie;

public class State {
    private String state_name,ac,ti,rec,nd;
    public State(String state_name, String ac, String ti, String rec, String nd) {
        this.state_name = state_name;
        this.ac = ac;
        this.ti = ti;
        this.rec = rec;
        this.nd = nd;

    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }
}
