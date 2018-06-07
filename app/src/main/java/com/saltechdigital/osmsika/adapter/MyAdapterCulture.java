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
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Jeanpaul Tossou on 27/11/2017.
 */

public class MyAdapterCulture extends RecyclerView.Adapter<MyAdapterCulture.MyViewHolder> {
    private Context context;
    private final List<TCulture> mal;

    public MyAdapterCulture(Context context, List<TCulture> liste) {
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
        View view = inflater.inflate(R.layout.list_culture, parent, false);
        return new MyViewHolder(view);
    }

    private void setAnimation(View toAnimate) {
        Animation animation = AnimationUtils.loadAnimation(toAnimate.getContext(), android.R.anim.fade_in);
        animation.setDuration(1000);
        toAnimate.startAnimation(animation);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TCulture actuel = mal.get(position);
        holder.linearLayout.setBackgroundResource(R.drawable.state_list);
        holder.iv_image.setBackgroundResource(R.drawable.state_button);
        holder.display(actuel);
        setAnimation(holder.linearLayout);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nomCulture;
        private TCulture current;
        private ImageView iv_image;
        private LinearLayout linearLayout;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        MyViewHolder(final View itemView) {
            super(itemView);
            nomCulture = itemView.findViewById(R.id.tv_nomCulture);
            linearLayout = itemView.findViewById(R.id.lin_cours);
            iv_image = itemView.findViewById(R.id.iv_culture);

            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.image_profil, null);
                    ImageView profil = view.findViewById(R.id.profil_icon);
                    Picasso.with(context).load("http:saltechdigital.com").placeholder(R.drawable.graduation_color).into(profil);
                    builder.setView(view);
                    builder.setTitle(current.getNomCulture());
                    builder.setCancelable(true);
                    builder.create().show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("culture", current);
                    context.startActivity(intent);
                }
            });
        }

        void display(TCulture news) {
            current = news;
            nomCulture.setText(news.getNomCulture());
        }

    }
}
