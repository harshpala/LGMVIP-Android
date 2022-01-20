package com.example.lgmvip_covidtracker;

public class Model {

    private String StateName;
    private String district;

    private int active;
    private int confirmed;
    private int deceased;
    private int recovered;

    public Model(String StateName, String district, int active, int confirmed, int deceased, int recovered) {
        this.StateName = StateName;
        this.district = district;
        //this.TotalActive = TotalActive;
        this.active = active;
        this.confirmed = confirmed;
        this.deceased = deceased;
        this.recovered = recovered;
    }


    public String getStateName() {return StateName;}
    public void setStateName(String stateName) {this.StateName = stateName;}

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

//    public  int getTotalActive(){return TotalActive ;}
//    public  void setTotalActive(int TotalActive){this.TotalActive = TotalActive;}

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }


    public int getDeceased() {
        return deceased;
    }

    public void setDeceased(int deceased) {
        this.deceased = deceased;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }
}
