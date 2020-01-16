package com.example.opp_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RacuniPodaci implements Serializable {


    @SerializedName("brRacun")
    @Expose
    private String brRacun;
    @SerializedName("oib")
    @Expose
    private String oib;
    @SerializedName("datOtvaranja")
    @Expose
    private String datOtvaranja;
    @SerializedName("stanje")
    @Expose
    private String stanje;
    @SerializedName("vrstaRacuna")
    @Expose
    private VrstaRacuna vrstaRacuna;
    @SerializedName("prekoracenje")
    @Expose
    private String prekoracenje;
    @SerializedName("kamStopa")
    @Expose
    private String kamStopa;



    public String getBrRacun() {
        return brRacun;
    }

    public void setBrRacun(String brRacun) {
        this.brRacun = brRacun;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getDatOtvaranja() {
        return datOtvaranja;
    }

    public void setDatOtvaranja(String datOtvaranja) {
        this.datOtvaranja = datOtvaranja;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    public VrstaRacuna getVrstaRacuna() {
        return vrstaRacuna;
    }

    public void setVrstaRacuna(VrstaRacuna vrstaRacuna) {
        this.vrstaRacuna = vrstaRacuna;
    }

    public String getPrekoracenje() {
        return prekoracenje;
    }

    public void setPrekoracenje(String prekoracenje) {
        this.prekoracenje = prekoracenje;
    }

    public String getKamStopa() {
        return kamStopa;
    }

    public void setKamStopa(String kamStopa) {
        this.kamStopa = kamStopa;
    }
}
