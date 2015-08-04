package com.example.soft12.parte_trabajo.activities.slidescreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.soft12.parte_trabajo.R;

/**
 * Created by soft12 on 30/06/2015.
 */
public class SecondFragment extends Fragment {

    EditText mTxtSolucion;

    public static SecondFragment newInstance(int text) {

        SecondFragment f = new SecondFragment();
        Bundle b = new Bundle();
        b.putInt("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_frag, container, false);

        initViews(v);

        return v;
    }

    private void initViews(View v) {

        this.mTxtSolucion = (EditText) v.findViewById(R.id.editTextSolucion);
        mTxtSolucion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Editable solucion = mTxtSolucion.getText();

                    SharedPreferences.Editor editor = getActivity().
                            getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE).edit();
                    editor.putString("solucion", solucion.toString());
                    editor.apply();
                }
            }
        });
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