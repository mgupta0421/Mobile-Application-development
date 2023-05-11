package edu.uncc.midtermapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.midtermapp.databinding.FragmentStatsBinding;


public class StatsFragment extends Fragment {
    private static final String ARG_PARAM_STAT = "ARG_PARAM_STAT";
    private static final String ARG_PARAM_SIZE = "ARG_PARAM_SIZE";
    private int mStat, mSize;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance(int stat, int size) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_STAT, stat);
        args.putInt(ARG_PARAM_SIZE, size);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStat = getArguments().getInt(ARG_PARAM_STAT);
            mSize = getArguments().getInt(ARG_PARAM_SIZE);
        }
    }

    FragmentStatsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Stats");
        binding.textViewTriviaStatus.setText(mStat + " out of " + mSize + " questions were answered correctly from the first attempt!");

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.startNewGame();
            }
        });
    }

    StatsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (StatsListener) context;
    }

    interface StatsListener{
        void startNewGame();
    }
}