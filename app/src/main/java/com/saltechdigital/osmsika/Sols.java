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

import com.saltechdigital.osmsika.adapter.MyAdapterSol;
import com.saltechdigital.osmsika.database.sol.TSol;
import com.saltechdigital.osmsika.database.sol.TSolDao;

import java.util.List;

public class Sols extends Fragment {

    private static TSolDao solDao;
    public static int idRegion = 1;
    private static Context staticContext;
    private static Activity staticActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sols, container, false);
        return v;
    }

    @Override
    public void onResume() {
        DataBind();
        super.onResume();
    }

    public static int getIdRegion() {
        return idRegion;
    }

    public static void setIdRegion(int idRegion) {
        Sols.idRegion = idRegion;
        DataBind();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        staticContext = getContext();
        staticActivity = getActivity();
        solDao = new TSolDao(getContext());
        //DataBind();
    }

    @Override
    public void onDestroy() {
        solDao.close();
        super.onDestroy();
    }

    private static void DataBind() {
        solDao = new TSolDao(staticContext);

        List<TSol> cultures;
        cultures = solDao.selectionnerListByRegion(idRegion);

        RecyclerView rv = staticActivity.findViewById(R.id.rv_sols);
        assert rv != null;
        rv.setLayoutManager(new LinearLayoutManager(staticContext));
        rv.setAdapter(new MyAdapterSol(staticContext, cultures));
        rv.setItemAnimator(new DefaultItemAnimator());
    }
}
