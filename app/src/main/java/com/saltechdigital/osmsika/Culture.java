package com.saltechdigital.osmsika;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saltechdigital.osmsika.adapter.MyAdapterCulture;
import com.saltechdigital.osmsika.database.culture.TCulture;
import com.saltechdigital.osmsika.database.culture.TCultureDao;

import java.util.List;

public class Culture extends Fragment {

    private static TCultureDao cultureDao;
    public static int idRegion = 1;
    private static Context staticContext;
    private static Activity staticActivity;

    public static int getIdRegion() {
        return idRegion;
    }

    public static void setIdRegion(int idRegion) {
        Culture.idRegion = idRegion;
        DataBind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.culture, container, false);
        return v;
    }

    @Override
    public void onResume() {
        DataBind();
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cultureDao = new TCultureDao(getContext());
        staticActivity = getActivity();
        staticContext = getContext();
        //DataBind();
    }

    @Override
    public void onDestroy() {
        cultureDao.close();
        super.onDestroy();
    }

    private static void DataBind() {
        cultureDao = new TCultureDao(staticContext);

        List<TCulture> cultures;
        cultures = cultureDao.selectionnerListByRegion(idRegion);

        RecyclerView rv = staticActivity.findViewById(R.id.rv_culture);
        assert rv != null;
        rv.setLayoutManager(new LinearLayoutManager(staticActivity));
        rv.setAdapter(new MyAdapterCulture(staticContext, cultures));
        rv.setItemAnimator(new DefaultItemAnimator());
    }
}
