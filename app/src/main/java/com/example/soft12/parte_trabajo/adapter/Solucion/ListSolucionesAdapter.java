package com.example.soft12.parte_trabajo.adapter.Solucion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.model.Solucion;

import java.util.List;

/**
 * Created by soft12 on 03/06/2015.
 */
public class ListSolucionesAdapter extends BaseAdapter {

    public static final String TAG = "ListSolucionesAdapter";

    private List<Solucion> mItems;
    private LayoutInflater mInflater;

    public ListSolucionesAdapter(Context context, List<Solucion> listCliente) {
        this.setItems(listCliente);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Solucion getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getcId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.list_item_solucion, parent, false);
            holder = new ViewHolder();
            holder.txtNombre = (TextView) v.findViewById(R.id.txt_solucion_nombre);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Solucion currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtNombre.setText(currentItem.getnNombre());
        }

        return v;
    }

    public List<Solucion> getItems() {
        return mItems;
    }

    public void setItems(List<Solucion> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtNombre;
    }


}
