package com.example.soft12.parte_trabajo.activities.CAU;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.adapter.CAU.ListCAUAdapter;
import com.example.soft12.parte_trabajo.dao.CAUDAO;
import com.example.soft12.parte_trabajo.model.CAU;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 02/06/2015.
 */
public class ListCAUActivity  extends Activity implements AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener, View.OnClickListener
{
    public static final String TAG = "ListCAUActivity";

    public static final int REQUEST_CODE_ADD_CAU = 40;
    public static final String EXTRA_ADDED_CAU = "extra_key_added_cau";

    private ListView mListviewCAU;
    private TextView mTxtEmptyListCAU;

    private ListCAUAdapter mAdapter;
    private List<CAU> mListCAU;
    private CAUDAO mCAUDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cau);

        // initialize views
        initViews();

        // fill the listView
        mCAUDao = new CAUDAO(this);
        mListCAU = mCAUDao.getAllCAU();
        if(mListCAU != null && !mListCAU.isEmpty()) {
            mAdapter = new ListCAUAdapter(this, mListCAU);
            mListviewCAU.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListCAU.setVisibility(View.VISIBLE);
            mListviewCAU.setVisibility(View.GONE);
        }
        registerForContextMenu(mListviewCAU);
    }

    private void initViews() {
        this.mListviewCAU = (ListView) findViewById(R.id.list_cau);
        this.mTxtEmptyListCAU = (TextView) findViewById(R.id.txt_empty_list_cau);
        ImageButton mBtnAddCAU = (ImageButton) findViewById(R.id.btn_add_cau);

        this.mListviewCAU.setOnItemClickListener(this);
        mBtnAddCAU.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_cau:
                Bundle extras = new Bundle();
                extras.putBoolean("add", true);
                lanzarEditCAU(extras);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_CAU) {
            if(resultCode == RESULT_OK) {
                // add the added coche to the listCoches and refresh the listView
                if(data != null) {
                    CAU createdCAU = (CAU) data.getSerializableExtra(EXTRA_ADDED_CAU);
                    if(createdCAU != null) {
                        if(mListCAU == null)
                            mListCAU = new ArrayList<>();
                        mListCAU.add(createdCAU);

                        if(mListviewCAU.getVisibility() != View.VISIBLE) {
                            mListviewCAU.setVisibility(View.VISIBLE);
                            mTxtEmptyListCAU.setVisibility(View.GONE);
                        }

                        if(mAdapter == null) {
                            mAdapter = new ListCAUAdapter(this, mListCAU);
                            mListviewCAU.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListCAU);
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
        mCAUDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CAU clickedCAU = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedCAU.getcNombre());
        Bundle extras = clickedCAU.getBundle();
        extras.putBoolean("add", false);
        lanzarEditCAU(extras);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CAU clickedCAU = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : " + clickedCAU.getcNombre());
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_cau_contextual, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        CAU cau = mAdapter.getItem(info.position);

        switch (item.getItemId()) {

            case R.id.edit_cau:
                Bundle extras = cau.getBundle();
                extras.putBoolean("add", false);
                lanzarEditCAU(extras);
                return true;

            case R.id.delete_cau:
                eliminarCAU(cau);
                // poner confirmación, para que no se borre a la primera
                Toast.makeText(getBaseContext(),
                        "Eliminar: " + cau.getcNombre(), Toast.LENGTH_SHORT)
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void eliminarCAU(CAU cau) {
        if(mCAUDao != null) {
            mCAUDao.deleteCAU(cau);
            mListCAU.remove(cau);

            //refresh the listView
            if(mListCAU.isEmpty()) {
                mListCAU = null;
                mListviewCAU.setVisibility(View.GONE);
                mTxtEmptyListCAU.setVisibility(View.VISIBLE);
            }
            mAdapter.setItems(mListCAU);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(getBaseContext(),
                    "Algo estás haciendo mal!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void lanzarEditCAU(Bundle extras) {
        Intent i = new Intent(this, AddCAUActivity.class);
        i.putExtras(extras);
        startActivityForResult(i, 40);
    }
}
