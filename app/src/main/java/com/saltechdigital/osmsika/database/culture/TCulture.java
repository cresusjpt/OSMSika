package com.saltechdigital.osmsika.database.culture;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TCulture implements Parcelable {

    private int idCulture;
    private String nomCulture;

    public TCulture(){}

    public TCulture(int idCulture, String nomCulture) {
        this.idCulture = idCulture;
        this.nomCulture = nomCulture;
    }

    protected TCulture(Parcel in){
        idCulture = in.readInt();
        nomCulture = in.readString();
    }

    public TCulture(JSONObject jsonObject){
        this.idCulture = jsonObject.optInt("IDCULTURE");
        this.nomCulture = jsonObject.optString("NOMCULTURE");
    }

    public static final Parcelable.Creator<TCulture> CREATOR = new Parcelable.Creator<TCulture>() {
        @Override
        public TCulture createFromParcel(Parcel in) {
            return new TCulture(in);
        }

        @Override
        public TCulture[] newArray(int size) {
            return new TCulture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idCulture);
        parcel.writeString(nomCulture);
    }

    public int getIdCulture() {
        return idCulture;
    }

    public void setIdCulture(int idCulture) {
        this.idCulture = idCulture;
    }

    public String getNomCulture() {
        return nomCulture;
    }

    public void setNomCulture(String nomCulture) {
        this.nomCulture = nomCulture;
    }
}
