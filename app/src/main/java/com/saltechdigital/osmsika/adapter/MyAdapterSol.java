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
import com.saltechdigital.osmsika.database.sol.TSol;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Jeanpaul Tossou on 27/11/2017.
 */

public class MyAdapterSol extends RecyclerView.Adapter<MyAdapterSol.MyViewHolder> {
    private Context context;
    private final List<TSol> mal;

    public MyAdapterSol(Context context, List<TSol> liste) {
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
        View view = inflater.inflate(R.layout.list_sol, parent, false);
        return new MyViewHolder(view);
    }

    private void setAnimation(View toAnimate) {
        Animation animation = AnimationUtils.loadAnimation(toAnimate.getContext(), android.R.anim.fade_in);
        animation.setDuration(1000);
        toAnimate.startAnimation(animation);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TSol actuel = mal.get(position);
        holder.linearLayout.setBackgroundResource(R.drawable.state_list);
        holder.iv_image.setBackgroundResource(R.drawable.state_button);
        holder.display(actuel);
        setAnimation(holder.linearLayout);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nomSol;
        private TSol current;
        private ImageView iv_image;
        private LinearLayout linearLayout;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        MyViewHolder(final View itemView) {
            super(itemView);
            nomSol = itemView.findViewById(R.id.tv_nomSol);
            linearLayout = itemView.findViewById(R.id.lin_cours);
            iv_image = itemView.findViewById(R.id.iv_cours);

            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.image_profil, null);
                    ImageView profil = view.findViewById(R.id.profil_icon);
                    Picasso.with(context).load("http:saltechdigital.com").placeholder(R.drawable.graduation_color).into(profil);
                    builder.setView(view);
                    builder.setTitle(current.getTypeSol());
                    builder.setCancelable(true);
                    builder.create().show();
                }
            });

        }

        void display(TSol news) {
            current = news;
            nomSol.setText(news.getTypeSol());
        }

    }
}
