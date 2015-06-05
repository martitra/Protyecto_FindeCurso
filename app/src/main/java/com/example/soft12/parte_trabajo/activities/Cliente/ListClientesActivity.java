package com.example.soft12.parte_trabajo.activities.Cliente;

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
import com.example.soft12.parte_trabajo.adapter.Cliente.ListClientesAdapter;
import com.example.soft12.parte_trabajo.dao.ClienteDAO;
import com.example.soft12.parte_trabajo.model.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 04/06/2015.
 */
public class ListClientesActivity extends Activity implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{

    public static final String TAG = "ListClientesActivity";

    public static final int REQUEST_CODE_ADD_CLIENTE = 40;
    public static final String EXTRA_ADDED_CLIENTE = "extra_key_added_cliente";

    private ListView mListviewClientes;
    private TextView mTxtEmptyListClientes;

    private ListClientesAdapter mAdapter;
    private List<Cliente> mListClientes;
    private ClienteDAO mClienteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clientes);

        // initialize views
        initViews();

        // fill the listView
        mClienteDao = new ClienteDAO(this);
        mListClientes = mClienteDao.getAllClientes();
        if(mListClientes != null && !mListClientes.isEmpty()) {
            mAdapter = new ListClientesAdapter(this, mListClientes);
            mListviewClientes.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListClientes.setVisibility(View.VISIBLE);
            mListviewClientes.setVisibility(View.GONE);
        }
        registerForContextMenu(mListviewClientes);
    }

    private void initViews() {
        this.mListviewClientes = (ListView) findViewById(R.id.list_clientes);
        this.mTxtEmptyListClientes = (TextView) findViewById(R.id.txt_empty_list_clientes);
        ImageButton mBtnAddCoche = (ImageButton) findViewById(R.id.btn_add_cliente);

        this.mListviewClientes.setOnItemClickListener(this);
        mBtnAddCoche.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle extras = new Bundle();
        switch (v.getId()) {
            case R.id.btn_add_cliente:
                extras.putBoolean("add", true);
                lanzarEditCliente(extras);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_CLIENTE) {
            if(resultCode == RESULT_OK) {
                // add the added coche to the listCoches and refresh the listView
                if(data != null) {
                    Cliente createdCliente = (Cliente) data.getSerializableExtra(EXTRA_ADDED_CLIENTE);
                    if(createdCliente != null) {
                        if(mListClientes == null)
                            mListClientes = new ArrayList<>();
                        mListClientes.add(createdCliente);

                        if(mListviewClientes.getVisibility() != View.VISIBLE) {
                            mListviewClientes.setVisibility(View.VISIBLE);
                            mTxtEmptyListClientes.setVisibility(View.GONE);
                        }

                        if(mAdapter == null) {
                            mAdapter = new ListClientesAdapter(this, mListClientes);
                            mListviewClientes.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListClientes);
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
        mClienteDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cliente clickedCliente = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedCliente.getnNombre());
        Bundle extras = clickedCliente.getBundle();
        extras.putBoolean("add", false);
        lanzarEditCliente(extras);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Cliente clickedCliente = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : " + clickedCliente.getnNombre());
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_cliente_contextual, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        Cliente cliente = mAdapter.getItem(info.position);

        switch (item.getItemId()) {

            case R.id.edit_cliente:
                Bundle extras = cliente.getBundle();
                extras.putBoolean("add", false);
                lanzarEditCliente(extras);
                return true;

            case R.id.delete_cliente:
                eliminarCliente(cliente);
                // poner confirmación, para que no se borre a la primera
                Toast.makeText(getBaseContext(),
                        "Eliminar: " + cliente.getnNombre(), Toast.LENGTH_SHORT)
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void eliminarCliente(Cliente cliente) {
        if(mClienteDao != null) {
            mClienteDao.deleteCliente(cliente);
            mListClientes.remove(cliente);

            //refresh the listView
            if(mListClientes.isEmpty()) {
                mListClientes = null;
                mListviewClientes.setVisibility(View.GONE);
                mTxtEmptyListClientes.setVisibility(View.VISIBLE);
            }
            mAdapter.setItems(mListClientes);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(getBaseContext(),
                    "Algo estás haciendo mal!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void lanzarEditCliente(Bundle extras) {
        Intent i = new Intent(this, AddClienteActivity.class);
        i.putExtras(extras);
        startActivityForResult(i, 40);
    }

}
