package com.example.soft12.parte_trabajo.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.soft12.parte_trabajo.adapter.ListRepostajeAdapter;
import com.example.soft12.parte_trabajo.dao.RepostajeDAO;
import com.example.soft12.parte_trabajo.model.Repostaje;

import java.util.ArrayList;
import java.util.List;

public class ListRepostajeActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {

	public static final String TAG = "ListRepostajeActivity";

	public static final int REQUEST_CODE_ADD_REPOSTAJE = 40;
	//public static final String EXTRA_ADDED_REPOSTAJE = "extra_key_added_repostaje";
	//public static final String EXTRA_SELECTED_COCHE_ID = "extra_key_selected_coche_id";

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

		// get the company id from extras
		mRepostajeDao = new RepostajeDAO(this);
		//Intent intent  = getIntent();
		//if(intent != null) {
		//}

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
	}

	private void initViews() {
		this.mListviewRepostaje = (ListView) findViewById(R.id.list_repostaje);
		this.mTxtEmptyListRepostaje = (TextView) findViewById(R.id.txt_empty_list_repostaje);
		ImageButton mBtnAddRepostaje = (ImageButton) findViewById(R.id.btn_add_repostaje);
		this.mListviewRepostaje.setOnItemClickListener(this);
		this.mListviewRepostaje.setOnItemLongClickListener(this);
		mBtnAddRepostaje.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_repostaje:
			Intent intent = new Intent(this, AddRepostajeActivity.class);
			startActivityForResult(intent, REQUEST_CODE_ADD_REPOSTAJE);
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

		Intent intent = new Intent(this, AddRepostajeActivity.class);
		intent.putExtra(AddRepostajeActivity.EXTRA_SELECTED_REPOSTAJE_ID, clickedRepostaje.getId());
		startActivity(intent);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Repostaje clickedRepostaje = mAdapter.getItem(position);
		Log.d(TAG, "longClickedItem : " + clickedRepostaje.getFecha() + " " + clickedRepostaje.getEuros());
		
		showDeleteDialogConfirmation(clickedRepostaje);
		return true;
	}
	
	private void showDeleteDialogConfirmation(final Repostaje repostaje) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		 
        alertDialogBuilder.setTitle("Delete");
		alertDialogBuilder
				.setMessage("Are you sure you want to delete the repostaje \""
						+ repostaje.getFecha() + " "
						+ repostaje.getEuros() + "\"");
 
        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// delete the employee and refresh the list
				if(mRepostajeDao != null) {
					mRepostajeDao.deleteRepostaje(repostaje);
					
					//refresh the listView
					mListRepostaje.remove(repostaje);
					if(mListRepostaje.isEmpty()) {
						mListviewRepostaje.setVisibility(View.GONE);
						mTxtEmptyListRepostaje.setVisibility(View.VISIBLE);
					}

					mAdapter.setItems(mListRepostaje);
					mAdapter.notifyDataSetChanged();
				}
				
				dialog.dismiss();
				Toast.makeText(ListRepostajeActivity.this, R.string.repostaje_deleted_successfully, Toast.LENGTH_SHORT).show();

			}
		});
        
        // set neutral button OK
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Dismiss the dialog
                dialog.dismiss();
			}
		});
        
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
	}
}
