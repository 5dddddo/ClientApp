package com.example.clientapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    HomeActivity activity;
    private TextView IdTv;
    private EditText nameEt;
    private TextView nameTv;
    private ToggleButton cnameBtn;
    private EditText telEt;
    private TextView telTv;
    private Spinner carSp;
    private EditText carIdEt;

    private ToggleButton ctelBtn;
    private Button cancelBtn;

    private String name;
    private String client_id;
    private String tel;
    private OnFragmentInteractionListener mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);

        IdTv = rootView.findViewById(R.id.IdTv);
        nameEt = rootView.findViewById(R.id.nameEt);
        nameTv = rootView.findViewById(R.id.nameTv);
        cnameBtn = rootView.findViewById(R.id.cnameBtn);
        telEt = rootView.findViewById(R.id.telEt);
        telTv = rootView.findViewById(R.id.telTv);
        carIdEt = rootView.findViewById(R.id.carIdEt);
        ctelBtn = rootView.findViewById(R.id.ctelBtn);
        cancelBtn = rootView.findViewById(R.id.cancelBtn);
        carSp = rootView.findViewById(R.id.carSp);

        cnameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cnameBtn.isChecked()) {
                    nameTv.setVisibility(View.GONE);
                    nameEt.setVisibility(View.VISIBLE);
                } else {
                    if (nameEt.getText().length() != 0)
                        name = nameEt.getText().toString();
                    nameTv.setText(name);
                    nameTv.setVisibility(View.VISIBLE);
                    nameEt.setVisibility(View.GONE);

                }
            }
        });
        ctelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ctelBtn.isChecked()) {
                    telTv.setVisibility(View.GONE);
                    telEt.setVisibility(View.VISIBLE);
                } else {
                    if (telEt.getText().length() != 0)
                        tel = telEt.getText().toString();
                    telTv.setText(tel);
                    telTv.setVisibility(View.VISIBLE);
                    telEt.setVisibility(View.GONE);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).replaceFragment(SettingFragment.newInstance());
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (HomeActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
