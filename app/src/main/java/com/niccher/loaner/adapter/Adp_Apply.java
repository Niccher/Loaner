package com.niccher.loaner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.niccher.loaner.R;
import com.niccher.loaner.mod.Mod_Apply;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Adp_Apply extends RecyclerView.Adapter<Adp_Apply.ViewHolder> {

    private Context mContext;
    List<Mod_Apply> mLinks;

    public Adp_Apply(Context mContext, List<Mod_Apply> mLinks) {
        this.mContext = mContext;
        this.mLinks = mLinks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Long tistamp = Long.valueOf(mLinks.get(position).getgTime());
        Date date = new Date(tistamp);
        SimpleDateFormat jdf = new SimpleDateFormat("MMM dd HH:mm a");//yyyy MMM dd HH:mm a
        String java_date = jdf.format(date);

        holder.desc.setText(mLinks.get(position).getgReason());
        holder.amt.setText(mLinks.get(position).getgAmount()+" KShs");
        holder.time.setText(java_date);
        holder.dur.setText("Grace Period "+mLinks.get(position).getgDuration());

        holder.accpt.setText(mLinks.get(position).getgAccepted());
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public void SetNotes(List<Mod_Apply> mod_products){
        this.mLinks = mod_products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView desc,amt,time, accpt,dur;

        RelativeLayout rel;

        public ViewHolder(View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.txt_body);
            amt = itemView.findViewById(R.id.txt_title);
            time = itemView.findViewById(R.id.txt_dat);
            accpt = itemView.findViewById(R.id.txt_priority);
            dur = itemView.findViewById(R.id.txt_time);
        }
    }

}