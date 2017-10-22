package com.shishir.sieveoferatosthenes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shishir.sieveoferatosthenes.R;
import com.shishir.sieveoferatosthenes.data.SieveNum;

import java.util.List;

/**
 * Created by Shishir on 10/21/2017.
 */

public class SieveOfEratosthenesAdapter extends RecyclerView.Adapter<SieveOfEratosthenesAdapter.ViewHolder>{

    private final List<SieveNum> mValues;

    public SieveOfEratosthenesAdapter(List<SieveNum> mValues) {
        this.mValues = mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_number_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);

        holder.txt_num.setText(mValues.get(position).getNum()+"");

        if(mValues.get(position).isFlag())
            holder.ll_cross_not_prime.setVisibility(View.GONE);
        else {

            holder.ll_cross_not_prime.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeInUp)
                    .duration(700)
                    .repeat(0)
                    .playOn(holder.ll_cross_not_prime);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txt_num;
        public final LinearLayout ll_cross_not_prime;
        public SieveNum mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txt_num = (TextView) view.findViewById(R.id.txt_num);
            ll_cross_not_prime = (LinearLayout) view.findViewById(R.id.ll_cross_not_prime);
        }

    }
}
