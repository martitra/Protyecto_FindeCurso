package com.example.soft12.parte_trabajo.adapter.Coche;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.model.Coche;

import java.util.List;

public class SpinnerCochesAdapter extends BaseAdapter {
	
	public static final String TAG = "SpinnerCochesAdapter";
	
	private List<Coche> mItems;
	private LayoutInflater mInflater;
	
	public SpinnerCochesAdapter(Context context, List<Coche> listCoches) {
		this.setItems(listCoches);
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
	}

	@Override
	public Coche getItem(int position) {
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
			v = mInflater.inflate(R.layout.spinner_item_coche, parent, false);
			holder = new ViewHolder();
			holder.txtCocheMatricula = (TextView) v.findViewById(R.id.txt_coche_matricula);
			v.setTag(holder);
		}
		else {
			holder = (ViewHolder) v.getTag();
		}
		
		// fill row data
		Coche currentItem = getItem(position);
		if(currentItem != null) {
			holder.txtCocheMatricula.setText(currentItem.getMatricula());
		}

		return v;
	}
	
	public List<Coche> getItems() {
		return mItems;
	}

	public void setItems(List<Coche> mItems) {
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
		TextView txtCocheMatricula;
	}

}
