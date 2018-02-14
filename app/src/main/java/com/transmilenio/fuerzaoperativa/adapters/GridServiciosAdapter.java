package com.transmilenio.fuerzaoperativa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.transmilenio.fuerzaoperativa.R;

import java.util.List;


public class GridServiciosAdapter  extends BaseAdapter {

    private Context context;
    private List<String> servicios;
    private int layout;

    public  GridServiciosAdapter(){

    }

    public GridServiciosAdapter(Context context, List<String> servicios, int layout) {
        this.context = context;
        this.servicios = servicios;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return servicios.size();
    }

    @Override
    public Object getItem(int position) {
        return servicios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final GridServiciosAdapter.ViewHolder vh;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new GridServiciosAdapter.ViewHolder();
            vh.servicio = (TextView) convertView.findViewById(R.id.servicio);
            convertView.setTag(vh);
        }else{
            vh = (GridServiciosAdapter.ViewHolder) convertView.getTag();
        }

        String servicio = servicios.get(position);
        vh.servicio.setText(servicio);
        return convertView;
    }

    public class ViewHolder{
        TextView servicio;
    }
}
