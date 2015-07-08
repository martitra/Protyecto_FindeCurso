package com.example.soft12.parte_trabajo.activities.repostaje;

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
import com.example.soft12.parte_trabajo.adapter.Repostaje.ListRepostajeAdapter;
import com.example.soft12.parte_trabajo.dao.RepostajeDAO;
import com.example.soft12.parte_trabajo.model.Repostaje;

import java.util.ArrayList;
import java.util.List;

public class ListRepostajeActivity extends Activity implements OnItemLongClickListener,
		OnItemClickListener, OnClickListener {

	public static final String TAG = "ListRepostajeActivity";

	public static final int REQUEST_CODE_ADD_REPOSTAJE = 40;

	private ListView mListviewRepostaje;
	private TextView mTxtEmptyListRepostaje;

	private ListRepostajeAdapter mAdapter;
	private List<Repostaje> mListRepostaje;
	private RepostajeDAO mRepostajeDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_repostaje);

		// initialize views
		initViews();
		mRepostajeDao = new RepostajeDAO(this);

		//if(mCocheId != -1) {
			mListRepostaje = mRepostajeDao.getAllRepostaje();
			// fill the listView
			if(mListRepostaje != null && !mListRepostaje.isEmpty()) {
				mAdapter = new ListRepostajeAdapter(this, mListRepostaje);
				mListviewRepostaje.setAdapter(mAdapter);
			}
			else {
				mTxtEmptyListRepostaje.setVisibility(View.VISIBLE);
				mListviewRepostaje.setVisibility(View.GONE);
			}
		//}
		registerForContextMenu(mListviewRepostaje);
	}

	private void initViews() {
		this.mListviewRepostaje = (ListView) findViewById(R.id.list_repostaje);
		this.mTxtEmptyListRepostaje = (TextView) findViewById(R.id.txt_empty_list_repostaje);
		ImageButton mBtnAddRepostaje = (ImageButton) findViewById(R.id.btn_add_repostaje);
        this.mListviewRepostaje.setOnItemClickListener(this);
		mBtnAddRepostaje.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_repostaje:
            Bundle extras = new Bundle();
            extras.putBoolean("add", true);
            lanzarEditRepostaje(extras);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_ADD_REPOSTAJE) {
			if(resultCode == RESULT_OK) {
				//refresh the listView
				if(mListRepostaje == null || !mListRepostaje.isEmpty()) {
					mListRepostaje = new ArrayList<>();
				}

				if(mRepostajeDao == null)
					mRepostajeDao = new RepostajeDAO(this);
				mListRepostaje = mRepostajeDao.getAllRepostaje();
				
				if(mListRepostaje != null && !mListRepostaje.isEmpty() &&
						mListviewRepostaje.getVisibility() != View.VISIBLE) {
					mTxtEmptyListRepostaje.setVisibility(View.GONE);
					mListviewRepostaje.setVisibility(View.VISIBLE);
				}
				
				if(mAdapter == null) {
					mAdapter = new ListRepostajeAdapter(this, mListRepostaje);
					mListviewRepostaje.setAdapter(mAdapter);
				}
				else {
					mAdapter.setItems(mListRepostaje);
					mAdapter.notifyDataSetChanged();
				}
			}
		}
		else 
			super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mRepostajeDao.close();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Repostaje clickedRepostaje = mAdapter.getItem(position);
		Log.d(TAG, "clickedItem : " + clickedRepostaje.getFecha());
		Bundle extras = clickedRepostaje.getBundle();
		extras.putBoolean("add", false);
		lanzarEditRepostaje(extras);
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Repostaje clickedRepostaje = mAdapter.getItem(position);
		Log.d(TAG, "longClickedItem : " + clickedRepostaje.getFecha());
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenu.ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_repostaje_contextual, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Repostaje repostaje = mAdapter.getItem(info.position);

		switch (item.getItemId()) {

			case R.id.edit_repostaje:
				Bundle extras = repostaje.getBundle();
				extras.putBoolean("add", false);
				lanzarEditRepostaje(extras);
				return true;

			case R.id.delete_repostaje:
				eliminarRepostaje(repostaje);
				// poner confirmación, para que no se borre a la primera
				Toast.makeText(getBaseContext(),
						"Eliminar: " + repostaje.getFecha(), Toast.LENGTH_SHORT)
						.show();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void eliminarRepostaje(Repostaje repostaje) {
		if(mRepostajeDao != null) {
			mRepostajeDao.deleteRepostaje(repostaje);
			mListRepostaje.remove(repostaje);

			//refresh the listView
			if(mListRepostaje.isEmpty()) {
				mListRepostaje = null;
				mListviewRepostaje.setVisibility(View.GONE);
				mTxtEmptyListRepostaje.setVisibility(View.VISIBLE);
			}
			mAdapter.setItems(mListRepostaje);
			mAdapter.notifyDataSetChanged();
		}
		else
		{
			Toast.makeText(getBaseContext(),
					"Algo estás haciendo mal!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void lanzarEditRepostaje(Bundle extras) {
		Intent i = new Intent(this, AddRepostajeActivity.class);
		i.putExtras(extras);
		startActivityForResult(i, 40);
	}

}
