package com.irimedas.notifyme.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irimedas.notifyme.R;
import com.irimedas.notifyme.firebase.Auth;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private static View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        Button btnLogin  = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onClick(View v) {
        if(v!=null){
            int elementID = v.getId();
            switch (elementID){
                case R.id.btnLogin:
                    EditText etEmail = view.findViewById(R.id.etEmail);
                    EditText etPassword = view.findViewById(R.id.etPassword);
                    String email = etEmail.getText().toString();
                    String password = etPassword.getText().toString();
                    Auth loginAuth = new Auth(email,password,getActivity());
                    loginAuth.setFragment(this);
                    loginAuth.singIn();

                  // Toast.makeText(view.getContext(), "TEST:\nemail="+email+"\npassword="+password+"\nloaded="+loginAuth.isLoaded(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(view.getContext(),"ERROR",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
