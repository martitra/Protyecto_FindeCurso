package com.example.soft12.parte_trabajo.activities.Solucion;

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
import com.example.soft12.parte_trabajo.adapter.Solucion.ListSolucionesAdapter;
import com.example.soft12.parte_trabajo.dao.SolucionDAO;
import com.example.soft12.parte_trabajo.model.Solucion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 04/06/2015.
 */
public class ListSolucionesActivity extends Activity implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    public static final String TAG = "ListSolucionesActivity";

    public static final int REQUEST_CODE_ADD_SOLUCION = 40;
    public static final String EXTRA_ADDED_SOLUCION = "extra_key_added_solucion";

    private ListView mListviewSoluciones;
    private TextView mTxtEmptyListSoluciones;

    private ListSolucionesAdapter mAdapter;
    private List<Solucion> mListSoluciones;
    private SolucionDAO mSolucionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_soluciones);

        // initialize views
        initViews();

        // fill the listView
        mSolucionDao = new SolucionDAO(this);
        mListSoluciones = mSolucionDao.getAllSoluciones();
        if(mListSoluciones != null && !mListSoluciones.isEmpty()) {
            mAdapter = new ListSolucionesAdapter(this, mListSoluciones);
            mListviewSoluciones.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListSoluciones.setVisibility(View.VISIBLE);
            mListviewSoluciones.setVisibility(View.GONE);
        }
        registerForContextMenu(mListviewSoluciones);
    }

    private void initViews() {
        this.mListviewSoluciones = (ListView) findViewById(R.id.list_soluciones);
        this.mTxtEmptyListSoluciones = (TextView) findViewById(R.id.txt_empty_list_soluciones);
        ImageButton mBtnAddCoche = (ImageButton) findViewById(R.id.btn_add_solucion);

        this.mListviewSoluciones.setOnItemClickListener(this);
        mBtnAddCoche.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle extras = new Bundle();
        switch (v.getId()) {
            case R.id.btn_add_solucion:
                extras.putBoolean("add", true);
                lanzarEditSolucion(extras);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_SOLUCION) {
            if(resultCode == RESULT_OK) {
                // add the added coche to the listCoches and refresh the listView
                if(data != null) {
                    Solucion createdSolucion = (Solucion) data.getSerializableExtra(EXTRA_ADDED_SOLUCION);
                    if(createdSolucion != null) {
                        if(mListSoluciones == null)
                            mListSoluciones = new ArrayList<>();
                        mListSoluciones.add(createdSolucion);

                        if(mListviewSoluciones.getVisibility() != View.VISIBLE) {
                            mListviewSoluciones.setVisibility(View.VISIBLE);
                            mTxtEmptyListSoluciones.setVisibility(View.GONE);
                        }

                        if(mAdapter == null) {
                            mAdapter = new ListSolucionesAdapter(this, mListSoluciones);
                            mListviewSoluciones.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListSoluciones);
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
        mSolucionDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Solucion clickedSolucion = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedSolucion.getnNombre());
        Bundle extras = clickedSolucion.getBundle();
        extras.putBoolean("add", false);
        lanzarEditSolucion(extras);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Solucion clickedSolucion = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : " + clickedSolucion.getnNombre());
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_solucion_contextual, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        Solucion solucion = mAdapter.getItem(info.position);

        switch (item.getItemId()) {

            case R.id.edit_solucion:
                Bundle extras = solucion.getBundle();
                extras.putBoolean("add", false);
                lanzarEditSolucion(extras);
                return true;

            case R.id.delete_solucion:
                eliminarSolucion(solucion);
                // poner confirmación, para que no se borre a la primera
                Toast.makeText(getBaseContext(),
                        "Eliminar: " + solucion.getnNombre(), Toast.LENGTH_SHORT)
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void eliminarSolucion(Solucion solucion) {
        if(mSolucionDao != null) {
            mSolucionDao.deleteSolucion(solucion);
            mListSoluciones.remove(solucion);

            //refresh the listView
            if(mListSoluciones.isEmpty()) {
                mListSoluciones = null;
                mListviewSoluciones.setVisibility(View.GONE);
                mTxtEmptyListSoluciones.setVisibility(View.VISIBLE);
            }
            mAdapter.setItems(mListSoluciones);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(getBaseContext(),
                    "Algo estás haciendo mal!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void lanzarEditSolucion(Bundle extras) {
        Intent i = new Intent(this, AddSolucionActivity.class);
        i.putExtras(extras);
        startActivityForResult(i, 40);
    }


}
