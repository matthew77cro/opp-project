package com.example.opp_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VrstaRacuna {

    @SerializedName("sifVrsteRacuna")
    @Expose
    private String sifVrsteRacuna;
    @SerializedName("nazVrsteRacuna")
    @Expose
    private String nazVrsteRacuna;



    public String getSifVrsteRacuna() {
        return sifVrsteRacuna;
    }

    public void setSifVrsteRacuna(String sifVrsteRacuna) {
        this.sifVrsteRacuna = sifVrsteRacuna;
    }

    public String getNazVrsteRacuna() {
        return nazVrsteRacuna;
    }

    public void setNazVrsteRacuna(String nazVrsteRacuna) {
        this.nazVrsteRacuna = nazVrsteRacuna;
    }
}
