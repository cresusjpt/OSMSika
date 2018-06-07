package com.saltechdigital.osmsika.database.region;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TRegion implements Parcelable {

    private int idRegion;
    private String nomRegion;
    private double surfaceCultivable;

    public TRegion() {
    }

    public TRegion(int idRegion, String nomRegion, double surfaceCultivable) {
        this.idRegion = idRegion;
        this.nomRegion = nomRegion;
        this.surfaceCultivable = surfaceCultivable;
    }

    public TRegion(JSONObject jsonObject){
        this.idRegion = jsonObject.optInt("IDREGION");
        this.nomRegion = jsonObject.optString("NOMREGION");
        this.surfaceCultivable = jsonObject.optDouble("SURFACECULTIVABLE");
    }

    protected TRegion(Parcel in) {
        idRegion = in.readInt();
        nomRegion = in.readString();
        surfaceCultivable = in.readDouble();
    }

    public static final Creator<TRegion> CREATOR = new Creator<TRegion>() {
        @Override
        public TRegion createFromParcel(Parcel in) {
            return new TRegion(in);
        }

        @Override
        public TRegion[] newArray(int size) {
            return new TRegion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idRegion);
        parcel.writeString(nomRegion);
        parcel.writeDouble(surfaceCultivable);
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getNomRegion() {
        return nomRegion;
    }

    public void setNomRegion(String nomRegion) {
        this.nomRegion = nomRegion;
    }

    public double getSurfaceCultivable() {
        return surfaceCultivable;
    }

    public void setSurfaceCultivable(double surfaceCultivable) {
        this.surfaceCultivable = surfaceCultivable;
    }
}
