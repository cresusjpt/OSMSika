package com.saltechdigital.osmsika.database.periodeculture;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TPeriodeCulture implements Parcelable {
    private int idPeriode;
    private String dateDebut;
    private String dateFin;

    public TPeriodeCulture() {
    }

    public TPeriodeCulture(int idPeriode, String dateDebut, String dateFin) {
        this.idPeriode = idPeriode;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public TPeriodeCulture(JSONObject jsonObject){
        this.idPeriode = jsonObject.optInt("IDPERIODE");
        this.dateDebut = jsonObject.optString("DATEDEBUT");
        this.dateFin = jsonObject.optString("DATEFIN");
    }


    protected TPeriodeCulture(Parcel in) {
        idPeriode = in.readInt();
        dateDebut = in.readString();
        dateFin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPeriode);
        dest.writeString(dateDebut);
        dest.writeString(dateFin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TPeriodeCulture> CREATOR = new Creator<TPeriodeCulture>() {
        @Override
        public TPeriodeCulture createFromParcel(Parcel in) {
            return new TPeriodeCulture(in);
        }

        @Override
        public TPeriodeCulture[] newArray(int size) {
            return new TPeriodeCulture[size];
        }
    };

    public int getIdPeriode() {
        return idPeriode;
    }

    public void setIdPeriode(int idPeriode) {
        this.idPeriode = idPeriode;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}
