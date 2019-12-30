package com.example.opp_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KreditPodaci {

    @SerializedName("brKredit")
    @Expose
    private int brKredit;
    @SerializedName("oib")
    @Expose
    private String oib;
    @SerializedName("iznos")
    @Expose
    private String iznos;
    @SerializedName("vrstaKredita")
    @Expose
    private VrstaKredita vrstaKredita;
    @SerializedName("datUgovaranja")
    @Expose
    private String datUgovaranja;
    @SerializedName("periodOtplate")
    @Expose
    private int periodOtplate;
    @SerializedName("datRate")
    @Expose
    private int datRate;
    @SerializedName("preostaloDugovanje")
    @Expose
    private String preostaloDugovanje;



    public int getBrKredit() {
        return brKredit;
    }

    public void setBrKredit(int brKredit) {
        this.brKredit = brKredit;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getIznos() {
        return iznos;
    }

    public void setIznos(String iznos) {
        this.iznos = iznos;
    }

    public VrstaKredita getVrstaKredita() {
        return vrstaKredita;
    }

    public void setVrstaKredita(VrstaKredita vrstaKredita) {
        this.vrstaKredita = vrstaKredita;
    }

    public String getDatUgovaranja() {
        return datUgovaranja;
    }

    public void setDatUgovaranja(String datUgovaranja) {
        this.datUgovaranja = datUgovaranja;
    }

    public int getPeriodOtplate() {
        return periodOtplate;
    }

    public void setPeriodOtplate(int periodOtplate) {
        this.periodOtplate = periodOtplate;
    }

    public int getDatRate() {
        return datRate;
    }

    public void setDatRate(int datRate) {
        this.datRate = datRate;
    }

    public String getPreostaloDugovanje() {
        return preostaloDugovanje;
    }

    public void setPreostaloDugovanje(String preostaloDugovanje) {
        this.preostaloDugovanje = preostaloDugovanje;
    }
}
