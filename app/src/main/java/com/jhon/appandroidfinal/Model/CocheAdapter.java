package com.jhon.appandroidfinal.Model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhon.appandroidfinal.R;



import java.util.List;

/**
 * Created by DS on 07/01/2018.
 */

public class CocheAdapter extends RecyclerView.Adapter<CocheAdapter.CochesviewHolder>{

    List<Coche> lstCoches;

    public CocheAdapter(List<Coche> lstCoches) {
        this.lstCoches = lstCoches;
    }

    @Override
    public CochesviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_coche,parent,false);
        CochesviewHolder holder=new CochesviewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(CochesviewHolder holder, int position) {
        Coche coche=lstCoches.get(position);
        holder.textViewMarca.setText(coche.getMarca());
        holder.textViewDueno.setText(coche.getDueno());

    }

    @Override
    public int getItemCount() {
        return lstCoches.size();
    }

    public  static  class CochesviewHolder extends RecyclerView.ViewHolder{

        TextView textViewMarca,textViewDueno;
        public CochesviewHolder(View itemView) {
            super(itemView);
            textViewMarca=(TextView) itemView.findViewById(R.id.textView_marca);
            textViewDueno=(TextView) itemView.findViewById(R.id.textView_dueno);

        }

    }


}
