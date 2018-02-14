package com.transmilenio.fuerzaoperativa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.models.db.Encuesta;

import java.util.List;


public class SurveyAdapter extends BaseAdapter {

    private Context context;
    private List<Encuesta> lista;
    private int layout;

    public SurveyAdapter() {
    }

    public SurveyAdapter(Context context, List<Encuesta> lista, int layout) {
        this.context = context;
        this.lista = lista;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Encuesta getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SurveyAdapter.ViewHolder vh;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new ViewHolder();
            vh.titulo = (TextView) convertView.findViewById(R.id.textView_tituloEncuesta);
            convertView.setTag(vh);
        }else{
            vh = (SurveyAdapter.ViewHolder) convertView.getTag();
        }

        Encuesta encuesta = lista.get(position);
        vh.titulo.setText(encuesta.getNombre());

        return convertView;
    }

    public class ViewHolder{
        TextView titulo;
    }
}
