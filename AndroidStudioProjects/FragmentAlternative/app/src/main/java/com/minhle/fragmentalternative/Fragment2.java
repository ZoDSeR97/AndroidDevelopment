package com.minhle.fragmentalternative;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //---Button view---
        Button letters = (Button)getActivity().findViewById(R.id.letters);
        letters.setText("Hi There!");
        letters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText note = (EditText) getActivity().findViewById(R.id.Note);
                note.setText(note.getText()+ " " +"Hi There!");
            }
        });
        Button numbers = (Button)getActivity().findViewById(R.id.numbers);
        numbers.setText("1 2 3 4 5");
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText note = (EditText) getActivity().findViewById(R.id.Note);
                note.setText(note.getText()+ " " +"1 2 3 4 5");
            }
        });
        Button symbols = (Button)getActivity().findViewById(R.id.symbols);
        symbols.setText("! @ # $ %");
        symbols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText note = (EditText) getActivity().findViewById(R.id.Note);
                note.setText(note.getText()+ " " +"! @ # $ %");
            }
        });
    }

}