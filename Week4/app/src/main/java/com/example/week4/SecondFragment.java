package com.example.week4;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.week4.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    public static final String TAG = "demo";
    private String selectedEdu;

    public String getSelectedEdu() {
        return selectedEdu;
    }

    public void setSelectedEdu(String selectedEdu) {
        this.selectedEdu = selectedEdu;
    }

    private FragmentSecondBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        //return inflater.inflate(R.layout.fragment_second, container,false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home Fragment");
        if(selectedEdu == null){
            binding.eduvalue.setText("Not Set");
        }else{
            binding.eduvalue.setText(selectedEdu);
        }
       // binding.textView2.setText("Hello I ma the fragment");
        binding.submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = binding.editTextTextPersonName2.toString();
                Log.d("CHECKVALUE", "onClick: " + name);

                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter the valid name", Toast.LENGTH_SHORT).show();
                }
                try {
                    double age = Double.parseDouble(binding.editTextNumber2.getText().toString());
                    Profile profile = new Profile(name, age);
                    minterface.sendProfile(profile);
                }catch (NumberFormatException e){
                    Toast.makeText(getActivity(), "Enter the valid number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minterface.goToSettings();
            }
        });

        binding.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minterface.goToeducation();
            }
        });


    }

        MainInterface minterface;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: " + "MainInterface");

        minterface = (MainInterface) context;
    }

    // define the interface to talk to activity

    public interface MainInterface{
        void sendProfile(Profile profile);
        void sendUsername (String username);
        void goToSettings();

        void goToeducation();


    }
}