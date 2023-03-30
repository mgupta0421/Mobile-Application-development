package com.example.midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.midterm.databinding.FragmentCreateReviewBinding;
import com.example.midterm.models.Product;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private Product mParam1;


    public CreateReviewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateReviewFragment newInstance(Product product) {
        CreateReviewFragment fragment = new CreateReviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Product) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    FragmentCreateReviewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateReviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Review");
        binding.textViewProductName.setText(mParam1.getName());
        binding.textViewProductPrice.setText(mParam1.getPrice());
        Picasso.get().load(mParam1.getImg_url()).into(binding.imageViewProductIcon);
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = binding.radioGroup.getCheckedRadioButtonId();
                String dept = getString(R.string.satisfied_level5_label);
                String rating = "5";
                if(selectedId == R.id.radioButton_level_1){
                    dept = getString(R.string.satisfied_level1_label);
                    rating="1";
                } else if(selectedId == R.id.radioButton_level_2){
                    dept = getString(R.string.satisfied_level2_label);
                    rating ="2";
                } else if(selectedId == R.id.radioButton_level_3){
                    dept = getString(R.string.satisfied_level3_label);
                    rating = "3";
                } else if(selectedId == R.id.radioButton_level_4){
                    dept = getString(R.string.satisfied_level4_label);
                    rating = "4";
                }
                String title = binding.editTextReview.getText().toString();
                if(title.isEmpty()) {
                    Toast.makeText(getContext(), "Review is required", Toast.LENGTH_SHORT).show();
                }else {
                    RequestBody formBody = new FormBody.Builder()
                            .add("pid", mParam1.getPid())
                            .add("review", title)
                            .add("rating", rating)
                            .build();
                    Request request = new Request.Builder()
                            .url("https://www.theappsdr.com/api/product/review")
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mListerner.completeForum();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }


    CreateListerner mListerner;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListerner = (CreateListerner) context;
    }

    public interface CreateListerner{
        void completeForum();
    }
}