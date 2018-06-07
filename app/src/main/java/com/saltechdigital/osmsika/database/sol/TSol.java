package com.saltechdigital.osmsika.database.sol;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TSol implements Parcelable {
    private int idSol;
    private String typeSol;

    public TSol() {
    }

    public TSol(int idSol, String typeSol) {
        this.idSol = idSol;
        this.typeSol = typeSol;
    }

    public TSol(JSONObject jsonObject){
        this.idSol = jsonObject.optInt("IDSOL");
        this.typeSol = jsonObject.optString("TYPESOL");
    }

    protected TSol(Parcel in) {
        idSol = in.readInt();
        typeSol = in.readString();
    }

    public static final Creator<TSol> CREATOR = new Creator<TSol>() {
        @Override
        public TSol createFromParcel(Parcel in) {
            return new TSol(in);
        }

        @Override
        public TSol[] newArray(int size) {
            return new TSol[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idSol);
        parcel.writeString(typeSol);
    }

    public int getIdSol() {
        return idSol;
    }

    public void setIdSol(int idSol) {
        this.idSol = idSol;
    }

    public String getTypeSol() {
        return typeSol;
    }

    public void setTypeSol(String typeSol) {
        this.typeSol = typeSol;
    }
}
