package com.example.opp_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KarticaPodaci {

    @SerializedName("brKartica")
    @Expose
    private String brKartica;
    @SerializedName("oib")
    @Expose
    private String oib;
    @SerializedName("vrstaKartice")
    @Expose
    private VrstaKartice vrstaKartice;
    @SerializedName("stanje")
    @Expose
    private String stanje;
    @SerializedName("valjanost")
    @Expose
    private String valjanost;
    @SerializedName("limitKartice")
    @Expose
    private String limitKartice;
    @SerializedName("kamStopa")
    @Expose
    private String kamStopa;
    @SerializedName("datRate")
    @Expose
    private int datRate;
    @SerializedName("brRacun")
    @Expose
    private String brRacun;



    public String getBrKartica() {
        return brKartica;
    }

    public void setBrKartica(String brKartica) {
        this.brKartica = brKartica;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public VrstaKartice getVrstaKartice() {
        return vrstaKartice;
    }

    public void setVrstaKartice(VrstaKartice vrstaKartice) {
        this.vrstaKartice = vrstaKartice;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    public String getValjanost() {
        return valjanost;
    }

    public void setValjanost(String valjanost) {
        this.valjanost = valjanost;
    }

    public String getLimitKartice() {
        return limitKartice;
    }

    public void setLimitKartice(String limitKartice) {
        this.limitKartice = limitKartice;
    }

    public String getKamStopa() {
        return kamStopa;
    }

    public void setKamStopa(String kamStopa) {
        this.kamStopa = kamStopa;
    }

    public int getDatRate() {
        return datRate;
    }

    public void setDatRate(int datRate) {
        this.datRate = datRate;
    }

    public String getBrRacun() {
        return brRacun;
    }

    public void setBrRacun(String brRacun) {
        this.brRacun = brRacun;
    }

}
