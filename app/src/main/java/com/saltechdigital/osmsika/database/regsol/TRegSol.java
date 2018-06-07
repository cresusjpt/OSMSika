package com.saltechdigital.osmsika.database.regsol;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TRegSol implements Parcelable {

    private int idRegion;
    private int idSol;

    public TRegSol() {
    }

    public TRegSol(int idRegion, int idSol) {
        this.idRegion = idRegion;
        this.idSol = idSol;
    }

    public TRegSol(JSONObject jsonObject){
        this.idRegion = jsonObject.optInt("IDREGION");
        this.idSol = jsonObject.optInt("IDSOL");
    }

    protected TRegSol(Parcel in) {
        idRegion = in.readInt();
        idSol = in.readInt();
    }

    public static final Creator<TRegSol> CREATOR = new Creator<TRegSol>() {
        @Override
        public TRegSol createFromParcel(Parcel in) {
            return new TRegSol(in);
        }

        @Override
        public TRegSol[] newArray(int size) {
            return new TRegSol[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idRegion);
        parcel.writeInt(idSol);
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public int getIdSol() {
        return idSol;
    }

    public void setIdSol(int idSol) {
        this.idSol = idSol;
    }
}
