package com.example.soft12.parte_trabajo.adapter.Repostaje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.model.Repostaje;

import java.util.List;

public class ListRepostajeAdapter extends BaseAdapter {
	
	public static final String TAG = "ListRepostajeAdapter";
	
	private List<Repostaje> mItems;
	private LayoutInflater mInflater;
	
	public ListRepostajeAdapter(Context context, List<Repostaje> listCompanies) {
		this.setItems(listCompanies);
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
	}

	@Override
	public Repostaje getItem(int position) {
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
			holder.txtEuros = (TextView) v.findViewById(R.id.txt_euros);
			holder.txtCoche = (TextView) v.findViewById(R.id.txt_coche_matricula);
			v.setTag(holder);
		}
		else {
			holder = (ViewHolder) v.getTag();
		}
		
		// fill row data
		Repostaje currentItem = getItem(position);
		if(currentItem != null) {
			holder.txtFecha.setText(currentItem.getFecha());
			holder.txtEuros.setText(String.valueOf(currentItem.getEuros()+" Euros"));
			holder.txtCoche.setText(currentItem.getCoche().getMatricula());
		}
		
		return v;
	}
	
	public List<Repostaje> getItems() {
		return mItems;
	}

	public void setItems(List<Repostaje> mItems) {
		this.mItems = mItems;
	}

	class ViewHolder {
		TextView txtFecha;
		TextView txtEuros;
		TextView txtCoche;
	}

}
