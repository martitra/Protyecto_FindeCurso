package com.example.soft12.parte_trabajo.adapter.CAU;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.model.CAU;

import java.util.List;

/**
 * Created by soft12 on 02/06/2015.
 */
public class SpinnerCAUAdapter extends BaseAdapter {

    public static final String TAG = "SpinnerCAUAdapter";

    private List<CAU> mItems;
    private LayoutInflater mInflater;

    public SpinnerCAUAdapter(Context context, List<CAU> listCAU) {
        this.setItems(listCAU);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public CAU getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getCauId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.spinner_item_cau, parent, false);
            holder = new ViewHolder();
            holder.txtNombreCAU = (TextView) v.findViewById(R.id.txt_cau_nombre);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        CAU currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtNombreCAU.setText(currentItem.getcNombre());
        }

        return v;
    }

    public List<CAU> getItems() {
        return mItems;
    }

    public void setItems(List<CAU> mItems) {
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
        TextView txtNombreCAU;
    }

}
