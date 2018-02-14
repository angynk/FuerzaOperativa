package com.transmilenio.fuerzaoperativa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.models.json.EncuestaTM;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class SurveySendAdapter  extends BaseAdapter {

    private Context context;
    private RealmResults<EncuestaTM> lista;
    private ArrayList<EncuestaTM> selectedItems;
    private int layout;
    ArrayList<Boolean> positionArray;

    private Realm realm;

    public SurveySendAdapter() {
    }

    public SurveySendAdapter(Context context, RealmResults<EncuestaTM> lista, int layout) {
        this.context = context;
        this.lista = lista;
        this.layout = layout;
        this.selectedItems = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        positionArray = new ArrayList<Boolean>(lista.size());
        for(int i =0;i<lista.size();i++){
            positionArray.add(false);
        }
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public EncuestaTM getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final SurveySendAdapter.ViewHolder vh;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new SurveySendAdapter.ViewHolder();
            vh.titulo = (TextView) convertView.findViewById(R.id.textView_send_tituloEncuesta);
            vh.fecha = (TextView) convertView.findViewById(R.id.textView_send_fechaEncuesta);
            vh.selection= (CheckBox) convertView.findViewById(R.id.checkBox_send_encuesta);
            convertView.setTag(vh);
        }else{
            vh = (SurveySendAdapter.ViewHolder) convertView.getTag();
        }

        EncuestaTM encuesta = lista.get(position);
        vh.titulo.setText(encuesta.getNombre_encuesta());
        vh.fecha.setText(encuesta.getIdentificador());
        vh.selection.setTag( position);
        vh.selection.setOnCheckedChangeListener(null);
        vh.selection.setFocusable(false);
        vh.selection.setChecked(positionArray.get(position));
        vh.selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer)  vh.selection.getTag();
                EncuestaTM cuadro = lista.get(pos);
                if(selectedItems.contains(cuadro)){
                    selectedItems.remove(cuadro);
                }else{
                    selectedItems.add(cuadro);
                }

            }
        });

        vh.selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked ){
                    System.out.println(position+"--- :)");
                    positionArray.set(position, true);

                }else{
                    positionArray.set(position, false);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder{
        TextView titulo,fecha;
        CheckBox selection;
    }

    public ArrayList<EncuestaTM> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(ArrayList<EncuestaTM> selectedItems) {
        this.selectedItems = selectedItems;
    }
}
