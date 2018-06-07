package com.saltechdigital.osmsika;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.saltechdigital.osmsika.adapter.MyAdapterRendement;
import com.saltechdigital.osmsika.database.culture.TCulture;
import com.saltechdigital.osmsika.database.rendement.Other;
import com.saltechdigital.osmsika.database.rendement.TRendementDao;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TRendementDao rendementDao;
    private TCulture culture;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rendementDao = new TRendementDao(this);
        context = this;

        culture = getIntent().getParcelableExtra("culture");

        DataBind();

    }

    private void DataBind(){
        rendementDao = new TRendementDao(this);
        List<Other> otherList = rendementDao.selectionnerListByCultureAndPeriode(culture);

        RecyclerView rv = DetailActivity.this.findViewById(R.id.rv_rendement);
        assert rv != null;
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(new MyAdapterRendement(context, otherList));
        rv.setItemAnimator(new DefaultItemAnimator());
    }
}
