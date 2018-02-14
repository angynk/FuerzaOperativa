package com.transmilenio.fuerzaoperativa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.models.db.RegistroConteo;

import java.util.List;


public class RegistroConteoDeAdapter extends BaseAdapter {

    private Context context;
    private List<RegistroConteo> lista;
    private int layout;

    public RegistroConteoDeAdapter(Context context, List<RegistroConteo> lista, int layout) {
        this.context = context;
        this.lista = lista;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RegistroConteoDeAdapter.ViewHolder vh;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new RegistroConteoDeAdapter.ViewHolder();
            vh.titulo = (TextView) convertView.findViewById(R.id.textView_tituloRegistro);
            convertView.setTag(vh);
        }else{
            vh = (RegistroConteoDeAdapter.ViewHolder) convertView.getTag();
        }

        RegistroConteo registro = lista.get(position);
        vh.titulo.setText("Servicio: "+registro.getServicio()+" - "+registro.getHoradespacho());

        return convertView;
    }

    public class ViewHolder{
        TextView titulo;
    }
}
