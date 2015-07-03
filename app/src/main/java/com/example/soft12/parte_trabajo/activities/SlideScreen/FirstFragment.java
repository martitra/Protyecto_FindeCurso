package com.example.soft12.parte_trabajo.activities.SlideScreen;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.dao.ClienteDAO;
import com.example.soft12.parte_trabajo.model.Diario;

import java.util.Calendar;

/**
 * Created by soft12 on 30/06/2015.
 */
public class FirstFragment extends Fragment {

    public EditText mTxtHoraIni, mTxtHoraFin, mTxtCAU;
    //public TextView itemcod;
    Diario diario;
    Calendar myCalendar = Calendar.getInstance();
    ClienteDAO clienteDAO;
    private AutoCompleteTextView autoCliente;

    // TODO COMPROBAR FEHCA INI MENOR QUE FECHA FIN
    // TODO QUE LOS CAMPOS NO ESTEAN VACÍOS
    // TODO CREAR AUTOCOMPLETAR EN CLIENTES

    /* TIME */
    TimePickerDialog.OnTimeSetListener timeIni = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            mTxtHoraIni.setText(String.format("%02d:%02d", hourOfDay, minute));
        }
    };
    /* TIME */
    TimePickerDialog.OnTimeSetListener timeFin = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay+1);
            myCalendar.set(Calendar.MINUTE,minute);
            mTxtHoraFin.setText(String.format("%02d:%02d", hourOfDay, minute));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.first_frag, container, false);

        clienteDAO = new ClienteDAO(getActivity());
        clienteDAO.open();

        diario = new Diario();
        initViews(v);

        ItemAutoTextAdapter adapter = this.new ItemAutoTextAdapter(clienteDAO);
        autoCliente.setAdapter(adapter);
        autoCliente.setOnItemClickListener(adapter);

        return v;
    }
    class ItemAutoTextAdapter extends CursorAdapter
            implements android.widget.AdapterView.OnItemClickListener {

        private ClienteDAO mDbHelper;

        /**
         * Constructor. Note that no cursor is needed when we create the
         * adapter. Instead, cursors are created on demand when completions are
         * needed for the field. (see
         * {@link ItemAutoTextAdapter#runQueryOnBackgroundThread(CharSequence)}.)
         *
         * @param dbHelper
         *            The AutoCompleteDbAdapter in use by the outer class
         *            object.
         */
        public ItemAutoTextAdapter(ClienteDAO dbHelper) {
            // Call the CursorAdapter constructor with a null Cursor.
            super(getActivity(), null);
            mDbHelper = dbHelper;
        }

        /**
         * Invoked by the AutoCompleteTextView field to get completions for the
         * current input.
         *
         * NOTE: If this method either throws an exception or returns null, the
         * Filter class that invokes it will log an error with the traceback,
         * but otherwise ignore the problem. No choice list will be displayed.
         * Watch those error logs!
         *
         * @param constraint
         *            The input entered thus far. The resulting query will
         *            search for Items whose description begins with this string.
         * @return A Cursor that is positioned to the first row (if one exists)
         *         and managed by the activity.
         */
        @Override
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            if (getFilterQueryProvider() != null) {
                return getFilterQueryProvider().runQuery(constraint);
            }

            return mDbHelper.fetchItemsByDesc(
                    (constraint != null ? constraint.toString() : "@@@@"));
        }

        /**
         * Called by the AutoCompleteTextView field to get the text that will be
         * entered in the field after a choice has been made.
         *
         * @param cursor
         *            The cursor, positioned to a particular row in the list.
         * @return A String representing the row's text value. (Note that this
         *         specializes the base class return value for this method,
         *         which is {@link CharSequence}.)
         */
        @Override
        public String convertToString(Cursor cursor) {
            final int columnIndex = cursor.getColumnIndexOrThrow("nombre");
            return cursor.getString(columnIndex);
        }

        /**
         * Called by the ListView for the AutoCompleteTextView field to display
         * the text for a particular choice in the list.
         *
         * @param view
         *            The TextView used by the ListView to display a particular
         *            choice.
         * @param context
         *            The context (Activity) to which this form belongs;
         * @param cursor
         *            The cursor for the list of choices, positioned to a
         *            particular row.
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            //final String text = convertToString(cursor);
            //((TextView) view).setText(text);

            final int itemColumnIndex = cursor.getColumnIndexOrThrow("codigo");
            final int descColumnIndex = cursor.getColumnIndexOrThrow("nombre");
            TextView text1 = (TextView) view.findViewById(R.id.txt_cliente_nombre);
            text1.setText(cursor.getString(itemColumnIndex));
            TextView text2 = (TextView) view.findViewById(R.id.txt_cliente_codigo);
            text2.setText(cursor.getString(descColumnIndex));
        }

        /**
         * Called by the AutoCompleteTextView field to display the text for a
         * particular choice in the list.
         *
         * @param context
         *            The context (Activity) to which this form belongs;
         * @param cursor
         *            The cursor for the list of choices, positioned to a
         *            particular row.
         * @param parent
         *            The ListView that contains the list of choices.
         *
         * @return A new View (really, a TextView) to hold a particular choice.
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            return inflater.inflate(R.layout.list_item_cliente,parent, false);
        }

        /**
         * Called by the AutoCompleteTextView field when a choice has been made
         * by the user.
         *
         * @param listView
         *            The ListView containing the choices that were displayed to
         *            the user.
         * @param view
         *            The field representing the selected choice
         * @param position
         *            The position of the choice within the list (0-based)
         * @param id
         *            The id of the row that was chosen (as provided by the _id
         *            column in the cursor.)
         */
        @Override
        public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
            // Get the cursor, positioned to the corresponding row in the result set
            Cursor cursor = (Cursor) listView.getItemAtPosition(position);

            // Get the Item Number from this row in the database.
            String itemNumber = cursor.getString(cursor.getColumnIndexOrThrow("codigo"));

            // Update the parent class's TextView
            autoCliente.setText(autoCliente.getText() + " - " + itemNumber);
            //itemcod.setText(autoCliente.getText());
            //Log.w("Quantity:", String.valueOf(itemcod.getText().length()));
            //autoCliente.setText("");
        }

    }

    private void establecerValores() {
       // mTxtCAU.setText(diario.getCau().getcNombre());
       // mTxtCliente.setText(diario.getCliente().getnNombre());
        //mTxtHoraIni.setText(diario.getHoraIni());
        //mTxtHoraFin.setText(diario.getHoraFin());

        Editable cau = mTxtCAU.getText();
        Editable hora_ini = mTxtHoraIni.getText();
        Editable hora_fin = mTxtHoraFin.getText();
        Editable cliente = autoCliente.getText();
        String cli = cliente.toString();

        String[] clien = cli.split(" -");

        SharedPreferences.Editor editor = this.getActivity().
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE).edit();
        editor.putString("cau", cau.toString());
        editor.putString("hora_ini", hora_ini.toString());
        editor.putString("hora_fin", hora_fin.toString());
        editor.putString("cliente", clien[0]);
        editor.apply();
    }

    private void initViews(View v) {

        this.mTxtHoraIni = (EditText) v.findViewById(R.id.txt_hora_ini);
        this.mTxtHoraFin = (EditText) v.findViewById(R.id.txt_hora_fin);
        //this.mTxtCliente = (EditText) v.findViewById(R.id.editText_cliente);
        this.autoCliente = (AutoCompleteTextView) v.findViewById(R.id.autocomplete_cliente);
        //this.itemcod = (TextView) v.findViewById(R.id.itemDesc);
        //itemView = (EditText) v.findViewById(R.id.item);
        this.mTxtCAU = (EditText) v.findViewById(R.id.editText_cau);

        /* HORA INICIO */
        this.mTxtHoraIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), timeIni, myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });

        mTxtHoraIni.setText(String.format("%02d:%02d", myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE)));

        /* HORA FIN */
        this.mTxtHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), timeFin, myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });
        mTxtHoraFin.setText(String.format("%02d:%02d", myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE)));
        mTxtHoraFin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    establecerValores();
                }
            }
        });
        mTxtHoraIni.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    establecerValores();
                }
            }
        });
    }

    public static FirstFragment newInstance(int pos) {

        FirstFragment f = new FirstFragment();
        Bundle b = new Bundle();
        b.putInt("msg", pos);

        f.setArguments(b);

        return f;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
