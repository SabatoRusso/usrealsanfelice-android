package app.srusso.usrealsanfelice.to;

import java.util.ArrayList;

/**
 * Created by italdata on 29/11/16.
 */

public class Partita {


    private String giornata;
    private  String ospiti;
    private  String locali;
    private  String data;
    private  String gol_osp;
    private  String gol_loc;
    private ArrayList<String> imgPartita = new ArrayList<>();


    public String getGiornata() {
        return giornata;
    }

    public void setGiornata(String giornata) {
        this.giornata = giornata;
    }

    public String getOspiti() {
        return ospiti;
    }

    public void setOspiti(String ospiti) {
        this.ospiti = ospiti;
    }

    public String getLocali() {
        return locali;
    }

    public void setLocali(String locali) {
        this.locali = locali;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getGol_osp() {
        return gol_osp;
    }

    public void setGol_osp(String gol_osp) {
        this.gol_osp = gol_osp;
    }

    public String getGol_loc() {
        return gol_loc;
    }

    public void setGol_loc(String gol_loc) {
        this.gol_loc = gol_loc;
    }

    public ArrayList<String> getImgPartita() {
        return imgPartita;
    }

    public void setImgPartita(ArrayList<String> imgPartita) {
        this.imgPartita = imgPartita;
    }
}
