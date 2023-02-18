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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.class4.databinding.FragmentDeprtmentBinding;
import com.example.class4.databinding.FragmentRegistrationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeprtmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeprtmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeprtmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeprtmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeprtmentFragment newInstance(String param1, String param2) {
        DeprtmentFragment fragment = new DeprtmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
      FragmentDeprtmentBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentDeprtmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int id=  binding.deptgroup.getCheckedRadioButtonId();
               if(id == -1){
                   Toast.makeText(getActivity(), "selct the dept", Toast.LENGTH_SHORT).show();
               }else{

                   RadioButton button= binding.deptgroup.findViewById(id);
                   String deptvalue = button.getText().toString();
                  // Log.d("DEPARTMENT", "onClick: " + deptvalue);
                   mlistner.selectDept(deptvalue);
               }
            }
        });
    }


    DeparmentListner mlistner;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistner = (DeparmentListner) context;

    }

    public interface DeparmentListner{
        void selectDept(String value);
    }
}