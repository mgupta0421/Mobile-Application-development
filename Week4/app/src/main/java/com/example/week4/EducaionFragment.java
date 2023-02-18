package com.example.week4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.week4.databinding.FragmentEducaionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EducaionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EducaionFragment extends Fragment {

    public EducaionFragment() {
        // Required empty public constructor
    }

    FragmentEducaionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentEducaionBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.submitEdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int checkedid= binding.maingroup.getCheckedRadioButtonId();
                if(checkedid == -1){
                    Toast.makeText(getActivity(), "Make a selection", Toast.LENGTH_SHORT).show();
                }else {
                    RadioButton button = binding.maingroup.findViewById(checkedid);
                    String dept = button.getText().toString();
                    mlistner.course(dept);
                }


            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mlistner.cancel();

            }
        });
    }

    EdusubmitListener mlistner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mlistner = (EdusubmitListener) context;
    }

    public interface EdusubmitListener{
        void cancel();
        void course(String Education);

    }
}