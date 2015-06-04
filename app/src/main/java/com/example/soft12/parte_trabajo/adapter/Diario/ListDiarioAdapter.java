package com.example.soft12.parte_trabajo.adapter.Diario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.model.Diario;

import java.util.List;

/**
 * Created by soft12 on 04/06/2015.
 */
public class ListDiarioAdapter extends BaseAdapter {

    public static final String TAG = "ListDiarioAdapter";

    private List<Diario> mItems;
    private LayoutInflater mInflater;

    public ListDiarioAdapter(Context context, List<Diario> listDiario) {
        this.setItems(listDiario);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Diario getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.list_item_repostaje, parent, false);
            holder = new ViewHolder();
            holder.txtFecha = (TextView) v.findViewById(R.id.txt_fecha);
            holder.txtCau = (TextView) v.findViewById(R.id.txt_cau_nombre);
            holder.txtCliente = (TextView) v.findViewById(R.id.txt_cliente_nombre);
            holder.txtSolucion = (TextView) v.findViewById(R.id.txt_solucion_nombre);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Diario currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtFecha.setText(currentItem.getFecha());
            holder.txtCau.setText(String.valueOf(currentItem.getCau().getcNombre()));
            holder.txtCliente.setText(currentItem.getCliente().getnNombre());
            holder.txtSolucion.setText(currentItem.getSolucion().getnNombre());
        }

        return v;
    }

    public List<Diario> getItems() {
        return mItems;
    }

    public void setItems(List<Diario> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtFecha;
        TextView txtCau;
        TextView txtCliente;
        TextView txtSolucion;
    }

}
