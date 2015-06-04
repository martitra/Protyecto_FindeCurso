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
public class SpinnerSolucionesAdapter extends BaseAdapter {

    public static final String TAG = "SpinnerClientesAdapter";

    private List<Solucion> mItems;
    private LayoutInflater mInflater;

    public SpinnerSolucionesAdapter(Context context, List<Solucion> listSoluciones) {
        this.setItems(listSoluciones);
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
            v = mInflater.inflate(R.layout.spinner_item_solucion, parent, false);
            holder = new ViewHolder();
            holder.txtSolucionNombre = (TextView) v.findViewById(R.id.txt_solucion_nombre);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Solucion currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtSolucionNombre.setText(currentItem.getnNombre());
        }

        return v;
    }

    public List<Solucion> getItems() {
        return mItems;
    }

    public void setItems(List<Solucion> mItems) {
        this.mItems = mItems;
    }

    public int getPositionById(long id) {
        if (!this.isEmpty()) {
            for (int i = 0; i < this.getCount(); i++) {
                if (this.getItemId(i) == id) {
                    return i ;
                }
            }
        }

        return 0 ;
    }

    class ViewHolder {
        TextView txtSolucionNombre;
    }

}
