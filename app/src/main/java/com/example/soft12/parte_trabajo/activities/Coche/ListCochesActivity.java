package com.example.soft12.parte_trabajo.activities.Coche;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.adapter.Coche.ListCochesAdapter;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
import com.example.soft12.parte_trabajo.model.Coche;

import java.util.ArrayList;
import java.util.List;

public class ListCochesActivity extends Activity implements OnItemLongClickListener,
		OnItemClickListener, OnClickListener {
	
	public static final String TAG = "ListCochesActivity";
	
	public static final int REQUEST_CODE_ADD_COCHE = 40;
	public static final String EXTRA_ADDED_COCHE = "extra_key_added_coche";
	
	private ListView mListviewCoches;
	private TextView mTxtEmptyListCoches;

	private ListCochesAdapter mAdapter;
	private List<Coche> mListCoches;
	private CocheDAO mCocheDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_coches);
		
		// initialize views
		initViews();
		
		// fill the listView
		mCocheDao = new CocheDAO(this);
		mListCoches = mCocheDao.getAllCoches();
		if(mListCoches != null && !mListCoches.isEmpty()) {
			mAdapter = new ListCochesAdapter(this, mListCoches);
			mListviewCoches.setAdapter(mAdapter);
		}
		else {
			mTxtEmptyListCoches.setVisibility(View.VISIBLE);
			mListviewCoches.setVisibility(View.GONE);
		}
		registerForContextMenu(mListviewCoches);
	}
	
	private void initViews() {
		this.mListviewCoches = (ListView) findViewById(R.id.list_coches);
		this.mTxtEmptyListCoches = (TextView) findViewById(R.id.txt_empty_list_coches);
		ImageButton mBtnAddCoche = (ImageButton) findViewById(R.id.btn_add_coche);

		this.mListviewCoches.setOnItemClickListener(this);
		mBtnAddCoche.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Bundle extras = new Bundle();
		switch (v.getId()) {
			case R.id.btn_add_coche:
				extras.putBoolean("add", true);
				lanzarEditCoche(extras);
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_ADD_COCHE) {
			if(resultCode == RESULT_OK) {
				// add the added coche to the listCoches and refresh the listView
				if(data != null) {
					Coche createdCoche = (Coche) data.getSerializableExtra(EXTRA_ADDED_COCHE);
					if(createdCoche != null) {
						if(mListCoches == null)
							mListCoches = new ArrayList<>();
						mListCoches.add(createdCoche);
						
						if(mListviewCoches.getVisibility() != View.VISIBLE) {
							mListviewCoches.setVisibility(View.VISIBLE);
							mTxtEmptyListCoches.setVisibility(View.GONE);
						}
						
						if(mAdapter == null) {
							mAdapter = new ListCochesAdapter(this, mListCoches);
							mListviewCoches.setAdapter(mAdapter);
						}
						else {
							mAdapter.setItems(mListCoches);
							mAdapter.notifyDataSetChanged();
						}
					}
				}
			}
		}
		else 
			super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCocheDao.close();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Coche clickedCoche = mAdapter.getItem(position);
		Log.d(TAG, "clickedItem : " + clickedCoche.getMatricula());
		Bundle extras = new Bundle();
		extras.putBoolean("add", false);
		lanzarEditCoche(extras);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Coche clickedCoche = mAdapter.getItem(position);
		Log.d(TAG, "longClickedItem : " + clickedCoche.getMatricula());
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenu.ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_coche_contextual, menu);
	}


	@Override
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Coche coche = mAdapter.getItem(info.position);

		switch (item.getItemId()) {

			case R.id.edit_coche:
				Bundle extras = coche.getBundle();
				extras.putBoolean("add", false);
				lanzarEditCoche(extras);
				return true;

			case R.id.delete_coche:
				eliminarCoche(coche);
				// poner confirmación, para que no se borre a la primera
				Toast.makeText(getBaseContext(),
						"Eliminar: " + coche.getMatricula(), Toast.LENGTH_SHORT)
						.show();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void eliminarCoche(Coche coche) {
		if(mCocheDao != null) {
			mCocheDao.deleteCoche(coche);
			mListCoches.remove(coche);

			//refresh the listView
			if(mListCoches.isEmpty()) {
				mListCoches = null;
				mListviewCoches.setVisibility(View.GONE);
				mTxtEmptyListCoches.setVisibility(View.VISIBLE);
			}
			mAdapter.setItems(mListCoches);
			mAdapter.notifyDataSetChanged();
		}
		else
		{
			Toast.makeText(getBaseContext(),
					"Algo estás haciendo mal!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void lanzarEditCoche(Bundle extras) {
		Intent i = new Intent(this, AddCocheActivity.class);
		i.putExtras(extras);
		startActivityForResult(i, 40);
	}

}
