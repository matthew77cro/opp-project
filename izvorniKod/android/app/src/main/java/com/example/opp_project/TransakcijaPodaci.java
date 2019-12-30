package com.example.opp_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransakcijaPodaci {


    @SerializedName("brTransakcija")
    @Expose
    private int brTransakcija;
    @SerializedName("racTerecenja")
    @Expose
    private String racTerecenja;
    @SerializedName("racOdobrenja")
    @Expose
    private String racOdobrenja;
    @SerializedName("iznos")
    @Expose
    private String iznos;
    @SerializedName("datTransakcije")
    @Expose
    private String datTransakcije;



    public int getBrTransakcija() {
        return brTransakcija;
    }

    public void setBrTransakcija(int brTransakcija) {
        this.brTransakcija = brTransakcija;
    }

    public String getRacTerecenja() {
        return racTerecenja;
    }

    public void setRacTerecenja(String racTerecenja) {
        this.racTerecenja = racTerecenja;
    }

    public String getRacOdobrenja() {
        return racOdobrenja;
    }

    public void setRacOdobrenja(String racOdobrenja) {
        this.racOdobrenja = racOdobrenja;
    }

    public String getIznos() {
        return iznos;
    }

    public void setIznos(String iznos) {
        this.iznos = iznos;
    }

    public String getDatTransakcije() {
        return datTransakcije;
    }

    public void setDatTransakcije(String datTransakcije) {
        this.datTransakcije = datTransakcije;
    }
}
