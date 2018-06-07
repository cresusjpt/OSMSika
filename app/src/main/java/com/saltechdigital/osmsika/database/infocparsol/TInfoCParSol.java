package com.saltechdigital.osmsika.database.infocparsol;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TInfoCParSol implements Parcelable {

    private int idCulture;
    private int idSol;

    public TInfoCParSol() {
    }

    public TInfoCParSol(int idCulture, int idSol) {
        this.idCulture = idCulture;
        this.idSol = idSol;
    }

    public TInfoCParSol(JSONObject jsonObject){
        this.idCulture = jsonObject.optInt("IDCULTURE");
        this.idSol = jsonObject.optInt("IDSOL");
    }

    protected TInfoCParSol(Parcel in) {
        idCulture = in.readInt();
        idSol = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCulture);
        dest.writeInt(idSol);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TInfoCParSol> CREATOR = new Creator<TInfoCParSol>() {
        @Override
        public TInfoCParSol createFromParcel(Parcel in) {
            return new TInfoCParSol(in);
        }

        @Override
        public TInfoCParSol[] newArray(int size) {
            return new TInfoCParSol[size];
        }
    };

    public int getIdCulture() {
        return idCulture;
    }

    public void setIdCulture(int idCulture) {
        this.idCulture = idCulture;
    }

    public int getIdSol() {
        return idSol;
    }

    public void setIdSol(int idSol) {
        this.idSol = idSol;
    }
}
