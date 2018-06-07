package com.saltechdigital.osmsika.database.region;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saltechdigital.osmsika.database.DAOBase;

import java.util.ArrayList;
import java.util.List;

public class TRegionDao extends DAOBase {

    private static final String TABLE_NAME = "REGION";
    private static final String KEY = "IDREGION";
    private static final String NOMREGION = "NOMREGION";
    private static final String SURFACECULTIVABLE = "SURFACECULTIVABLE";

    private Context context;
    private SQLiteDatabase database = getDb();

    public TRegionDao(Context pContext) {
        super(pContext);
    }

    public void ajouter(TRegion mRegion) {
        if (mRegion.getNomRegion() != null) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(NOMREGION, mRegion.getNomRegion());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }

        if (mRegion.getSurfaceCultivable() != 0) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(SURFACECULTIVABLE, mRegion.getSurfaceCultivable());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
    }

    public int getKey(String nomCulture) {
        int ret = 0;
        database = open();
        Cursor cursor = database.rawQuery("SELECT " + KEY + " FROM " + TABLE_NAME + " WHERE " + SURFACECULTIVABLE + " = ?", new String[]{nomCulture});
        if (cursor.moveToFirst()) {
            ret = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return ret;
    }

    public void ajouterListe(List<TRegion> regionList) {
        for (TRegion region : regionList) {
            ajouter(region);
        }
    }

    public void supprimer(long id) {
        database = open();
        database.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public String getSurfacecultivable(int idCulture) {
        database = open();
        String retourne = "";
        Cursor cursor = database.rawQuery("SELECT " + SURFACECULTIVABLE + " FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(idCulture)});
        if (cursor.moveToFirst()) {
            retourne = cursor.getString(0);
        }
        cursor.close();
        database.close();
        return retourne;
    }

    public TRegion selectionner(long id) {
        database = open();
        TRegion region = new TRegion();

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            region.setIdRegion(c.getInt(0));
            region.setNomRegion(c.getString(1));
            region.setSurfaceCultivable(c.getDouble(2));
            return region;
        }
        c.close();
        database.close();
        return region;
    }

    public int taille() {
        database = open();
        int retourne = 0;
        Cursor cursor = database.rawQuery("SELECT COUNT(" + KEY + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            retourne = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return retourne;
    }

    public List<TRegion> selectionnerList() {

        database = open();

        List<TRegion> liste = new ArrayList<>();

        String requete = "SELECT * FROM " + TABLE_NAME/* + " WHERE " + KEY + " <> ?"*/;

        Cursor cursor = database.rawQuery(requete, /*new String[]{String.valueOf(1)}*/null);

        if (cursor.moveToFirst()) {
            do {
                TRegion region = new TRegion();
                region.setIdRegion(cursor.getInt(0));
                region.setNomRegion(cursor.getString(1));
                region.setSurfaceCultivable(cursor.getDouble(1));
                liste.add(region);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return liste;
    }
}
