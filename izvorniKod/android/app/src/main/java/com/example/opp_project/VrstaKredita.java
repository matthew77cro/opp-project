package com.example.opp_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VrstaKredita {


    @SerializedName("sifVrsteKredita")
    @Expose
    private int sifVrsteKredita;
    @SerializedName("nazVrsteKredita")
    @Expose
    private String nazVrsteKredita;
    @SerializedName("kamStopa")
    @Expose
    private String kamStopa;



    public int getSifVrsteKredita() {
        return sifVrsteKredita;
    }

    public void setSifVrsteKredita(int sifVrsteKredita) {
        this.sifVrsteKredita = sifVrsteKredita;
    }

    public String getNazVrsteKredita() {
        return nazVrsteKredita;
    }

    public void setNazVrsteKredita(String nazVrsteKredita) {
        this.nazVrsteKredita = nazVrsteKredita;
    }

    public String getKamStopa() {
        return kamStopa;
    }

    public void setKamStopa(String kamStopa) {
        this.kamStopa = kamStopa;
    }
}
