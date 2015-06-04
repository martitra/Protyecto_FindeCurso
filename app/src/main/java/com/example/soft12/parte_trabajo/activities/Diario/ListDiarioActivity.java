package com.example.soft12.parte_trabajo.activities.Diario;

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
import com.example.soft12.parte_trabajo.adapter.Diario.ListDiarioAdapter;
import com.example.soft12.parte_trabajo.dao.DiarioDAO;
import com.example.soft12.parte_trabajo.model.Diario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 04/06/2015.
 */
public class ListDiarioActivity extends Activity implements View.OnClickListener,
        AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    public static final String TAG = "ListDiarioActivity";

    public static final int REQUEST_CODE_ADD_DIARIO = 40;

    private ListView mListviewDiario;
    private TextView mTxtEmptyListDiario;

    private ListDiarioAdapter mAdapter;
    private List<Diario> mListDiario;
    private DiarioDAO mDiarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_diario);

        // initialize views
        initViews();

        mDiarioDAO = new DiarioDAO(this);
        //Intent intent  = getIntent();
        //if(intent != null) {
        //}

        //if(mCocheId != -1) {
        mListDiario = mDiarioDAO.getAllDiario();
        // fill the listView
        if(mListDiario != null && !mListDiario.isEmpty()) {
            mAdapter = new ListDiarioAdapter(this, mListDiario);
            mListviewDiario.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListDiario.setVisibility(View.VISIBLE);
            mListviewDiario.setVisibility(View.GONE);
        }
        //}
        registerForContextMenu(mListviewDiario);
    }

    private void initViews() {
        this.mListviewDiario = (ListView) findViewById(R.id.list_diario);
        this.mTxtEmptyListDiario = (TextView) findViewById(R.id.txt_empty_list_diario);
        ImageButton mBtnAddRepostaje = (ImageButton) findViewById(R.id.btn_add_diario);
        this.mListviewDiario.setOnItemClickListener(this);
        mBtnAddRepostaje.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_diario:
                Bundle extras = new Bundle();
                extras.putBoolean("add", true);
                lanzarEditDiario(extras);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_DIARIO) {
            if(resultCode == RESULT_OK) {
                //refresh the listView
                if(mListDiario == null || !mListDiario.isEmpty()) {
                    mListDiario = new ArrayList<>();
                }

                if(mDiarioDAO == null)
                    mDiarioDAO = new DiarioDAO(this);
                mListDiario = mDiarioDAO.getAllDiario();

                if(mListDiario != null && !mListDiario.isEmpty() &&
                        mListviewDiario.getVisibility() != View.VISIBLE) {
                    mTxtEmptyListDiario.setVisibility(View.GONE);
                    mListviewDiario.setVisibility(View.VISIBLE);
                }

                if(mAdapter == null) {
                    mAdapter = new ListDiarioAdapter(this, mListDiario);
                    mListviewDiario.setAdapter(mAdapter);
                }
                else {
                    mAdapter.setItems(mListDiario);
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
        mDiarioDAO.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Diario clickedDiario = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedDiario.getFecha());
        Bundle extras = clickedDiario.getBundle();
        extras.putBoolean("add", false);
        lanzarEditDiario(extras);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Diario clickedDiario = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : " + clickedDiario.getFecha());
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_diario_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        Diario diario = mAdapter.getItem(info.position);

        switch (item.getItemId()) {

            case R.id.edit_diario:
                Bundle extras = diario.getBundle();
                extras.putBoolean("add", false);
                lanzarEditDiario(extras);
                return true;

            case R.id.delete_diario:
                eliminarDiario(diario);
                // poner confirmación, para que no se borre a la primera
                Toast.makeText(getBaseContext(),
                        "Eliminar: " + diario.getFecha(), Toast.LENGTH_SHORT)
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void eliminarDiario(Diario diario) {
        if(mDiarioDAO != null) {
            mDiarioDAO.deleteDiario(diario);
            mListDiario.remove(diario);

            //refresh the listView
            if(mListDiario.isEmpty()) {
                mListDiario = null;
                mListviewDiario.setVisibility(View.GONE);
                mTxtEmptyListDiario.setVisibility(View.VISIBLE);
            }
            mAdapter.setItems(mListDiario);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(getBaseContext(),
                    "Algo estás haciendo mal!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void lanzarEditDiario(Bundle extras) {
        Intent i = new Intent(this, AddDiarioActivity.class);
        i.putExtras(extras);
        startActivityForResult(i, 40);
    }

}
