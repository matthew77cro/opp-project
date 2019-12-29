package com.example.opp_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VrstaKartice {

    @SerializedName("sifVrsteKartice")
    @Expose
    private int sifVrsteKartice;
    @SerializedName("nazVrsteKartice")
    @Expose
    private String nazVrsteKartice;



    public int getSifVrsteKartice() {
        return sifVrsteKartice;
    }

    public void setSifVrsteKartice(int sifVrsteKartice) {
        this.sifVrsteKartice = sifVrsteKartice;
    }

    public String getNazVrsteKartice() {
        return nazVrsteKartice;
    }

    public void setNazVrsteKartice(String nazVrsteKartice) {
        this.nazVrsteKartice = nazVrsteKartice;
    }
}
