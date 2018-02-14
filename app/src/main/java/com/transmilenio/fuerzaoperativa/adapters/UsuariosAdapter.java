package com.transmilenio.fuerzaoperativa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.models.db.Aforador;

import java.util.List;


public class UsuariosAdapter extends BaseAdapter {

    private Context context;
    private List<Aforador> lista;
    private int layout;

    public UsuariosAdapter() {
    }

    public UsuariosAdapter(Context context, List<Aforador> lista, int layout) {
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
        UsuariosAdapter.ViewHolder vh;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new UsuariosAdapter.ViewHolder();
            vh.titulo = (TextView) convertView.findViewById(R.id.lv_user_textView);
            convertView.setTag(vh);
        }else{
            vh = (UsuariosAdapter.ViewHolder) convertView.getTag();
        }

        Aforador encuesta = lista.get(position);
        vh.titulo.setText(encuesta.getUsuario());

        return convertView;
    }

    public class ViewHolder{
        TextView titulo;
    }
}
