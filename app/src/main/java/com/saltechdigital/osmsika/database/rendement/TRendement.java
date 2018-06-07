package com.saltechdigital.osmsika.database.rendement;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TRendement implements Parcelable {
    private int idCulture;
    private int idPeriode;
    private double qtemetrecarre;

    public TRendement() {
    }

    public TRendement(int idCulture, int idPeriode, double qtemetrecarre) {
        this.idCulture = idCulture;
        this.idPeriode = idPeriode;
        this.qtemetrecarre = qtemetrecarre;
    }

    public TRendement(JSONObject jsonObject){
        this.idCulture = jsonObject.optInt("IDCULTURE");
        this.idPeriode = jsonObject.optInt("IDPERIODE");
        this.qtemetrecarre = jsonObject.optDouble("QTEMETRECARRE");
    }

    protected TRendement(Parcel in) {
        idCulture = in.readInt();
        idPeriode = in.readInt();
        qtemetrecarre = in.readDouble();
    }

    public static final Creator<TRendement> CREATOR = new Creator<TRendement>() {
        @Override
        public TRendement createFromParcel(Parcel in) {
            return new TRendement(in);
        }

        @Override
        public TRendement[] newArray(int size) {
            return new TRendement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idCulture);
        parcel.writeInt(idPeriode);
        parcel.writeDouble(qtemetrecarre);
    }

    public int getIdCulture() {
        return idCulture;
    }

    public void setIdCulture(int idCulture) {
        this.idCulture = idCulture;
    }

    public int getIdPeriode() {
        return idPeriode;
    }

    public void setIdPeriode(int idPeriode) {
        this.idPeriode = idPeriode;
    }

    public double getQtemetrecarre() {
        return qtemetrecarre;
    }

    public void setQtemetrecarre(double qtemetrecarre) {
        this.qtemetrecarre = qtemetrecarre;
    }
}
