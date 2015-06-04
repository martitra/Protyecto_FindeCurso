package com.example.soft12.parte_trabajo.adapter.Cliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.model.Cliente;

import java.util.List;

/**
 * Created by soft12 on 03/06/2015.
 */
public class SpinnerClientesAdapter extends BaseAdapter {

    public static final String TAG = "SpinnerClientesAdapter";

    private List<Cliente> mItems;
    private LayoutInflater mInflater;

    public SpinnerClientesAdapter(Context context, List<Cliente> listClientes) {
        this.setItems(listClientes);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Cliente getItem(int position) {
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
            v = mInflater.inflate(R.layout.spinner_item_cliente, parent, false);
            holder = new ViewHolder();
            holder.txtClienteNombre = (TextView) v.findViewById(R.id.txt_cliente_nombre);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Cliente currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtClienteNombre.setText(currentItem.getnNombre());
        }

        return v;
    }

    public List<Cliente> getItems() {
        return mItems;
    }

    public void setItems(List<Cliente> mItems) {
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
        TextView txtClienteNombre;
    }

}
