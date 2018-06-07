package com.saltechdigital.osmsika.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saltechdigital.osmsika.DetailActivity;
import com.saltechdigital.osmsika.R;
import com.saltechdigital.osmsika.database.culture.TCulture;
import com.saltechdigital.osmsika.database.rendement.Other;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;


/**
 * Created by Jeanpaul Tossou on 27/11/2017.
 */

public class MyAdapterRendement extends RecyclerView.Adapter<MyAdapterRendement.MyViewHolder> {
    private Context context;
    private final List<Other> mal;

    public MyAdapterRendement(Context context, List<Other> liste) {
        this.context = context;
        this.mal = liste;
    }

    @Override
    public int getItemCount() {
        return mal.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_rendement_par_culture, parent, false);
        return new MyViewHolder(view);
    }

    private void setAnimation(View toAnimate) {
        Animation animation = AnimationUtils.loadAnimation(toAnimate.getContext(), android.R.anim.fade_in);
        animation.setDuration(1000);
        toAnimate.startAnimation(animation);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Other actuel = mal.get(position);
        holder.linearLayout.setBackgroundResource(R.drawable.state_list);
        holder.display(actuel);
        setAnimation(holder.linearLayout);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView date,rendement;
        private Other current;
        private LinearLayout linearLayout;

        MyViewHolder(final View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_date);
            rendement = itemView.findViewById(R.id.tv_rendement);
            linearLayout = itemView.findViewById(R.id.card_viewRelative);
        }

        void display(Other news) {
            current = news;
            date.setText(MessageFormat.format("Du {0} Au {1}", current.getDateDebut(), current.getDateFin()));
            rendement.setText(String.valueOf(current.getQteMetreCarre()));
        }

    }
}
