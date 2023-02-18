package com.example.class4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.class4.databinding.FragmentMainBinding;
import com.example.class4.databinding.FragmentRegistrationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String result;

    private String dept;

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance(String value) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString("VALUE", value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
            result = getArguments().getString("VALUE");
        }
    }


    FragmentRegistrationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentRegistrationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(result != null){
            binding.deptvalue.setText(result.toString());
        }

       /* if(dept == null){
            binding.deptvalue.setText("Not Set");
        }else{
            binding.deptvalue.setText(dept);
        } */

        binding.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlisterner.setDept();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               EditText name = binding.namevalue.findViewById(R.id.namevalue);
               String namevalue = name.getText().toString();

                EditText email = binding.emailvalue.findViewById(R.id.emailvalue);
                String emailvalue = email.getText().toString();

                EditText id = binding.idvalue.findViewById(R.id.idvalue);
                String idvalue = id.getText().toString();

                Log.d("ALL VALUES", "onClick: " +namevalue);

                TextView dept = binding.deptvalue.findViewById(R.id.deptvalue);
                String deptvalue = dept.getText().toString();



                if (namevalue.isEmpty() ||emailvalue.isEmpty() || idvalue.isEmpty() || deptvalue.isEmpty()){
                    Toast.makeText(getActivity(), "Complete the form", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User(namevalue, emailvalue, idvalue, deptvalue);
                    mlisterner.sendUser(user);
                }

            }
        });
    }


    RegistrationListener mlisterner;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mlisterner = (RegistrationListener) context;
    }

    public interface RegistrationListener{
        void setDept();
        void sendUser(User user);
    }
}