package com.jhon.appandroidfinal.Model;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhon.appandroidfinal.R;

import java.util.ArrayList;

/**
 * Created by DS on 07/01/2018.
 */

public class CocheAdapterL extends BaseAdapter{
    private Context context;
    private int layout;
    private ArrayList<Coche> CocheLista;

    public CocheAdapterL(Context pContext, int pLayout,
                               ArrayList<Coche> cocheLista)
    {
        this.context = pContext;
        this.layout =pLayout;
        CocheLista = cocheLista;
    }

    @Override
    public int getCount() {

        return CocheLista.size();
    }

    @Override
    public Object getItem(int position) {

        return CocheLista.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fila = convertView;
        Vista objVista = new Vista();
        if(fila == null)
        {
            LayoutInflater inflater;
            inflater = (LayoutInflater)context.
                    getSystemService(context.LAYOUT_INFLATER_SERVICE);
            fila = inflater.inflate(R.layout.layout, null);

            objVista.txtMarca = (TextView)fila.findViewById(R.id.tMarca);
            objVista.txtDueno = (TextView)fila.findViewById(R.id.txtDueno);
            objVista.txtPuertas = (TextView)fila.findViewById(R.id.txtPuertas);
            objVista.txtRuedas = (TextView)fila.findViewById(R.id.txtRuedas);

            fila.setTag(objVista);
        }
        else
        {            objVista = (Vista)fila.getTag();        }
        Coche objPersona = CocheLista.get(position);
        objVista.txtMarca.setText(objPersona.getMarca());
        objVista.txtDueno.setText(objPersona.getDueno());
        String puertas = String.valueOf(objPersona.getPuertas());
        objVista.txtPuertas.setText(puertas);
        String ruedas=String.valueOf(objPersona.getRuedas());
        objVista.txtRuedas.setText(ruedas);

        return fila;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Vista
    {

        TextView txtMarca, txtDueno, txtPuertas,txtRuedas;
    }
}
