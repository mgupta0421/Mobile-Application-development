package com.example.midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.midterm.databinding.FragmentReviewsBinding;
import com.example.midterm.databinding.RowItemProductBinding;
import com.example.midterm.databinding.RowItemReviewBinding;
import com.example.midterm.models.Product;
import com.example.midterm.models.Review;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private Product mParam1;


    public ReviewsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReviewsFragment newInstance(Product product) {
        ReviewsFragment fragment = new ReviewsFragment();
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

    FragmentReviewsBinding binding;
    ArrayList<Review> mReviews = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    ReviewAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Review");
        binding.textViewProductName.setText(mParam1.getName());
        binding.textViewProductPrice.setText(mParam1.getPrice());
        Picasso.get().load(mParam1.getImg_url()).into(binding.imageViewProductIcon);
        //binding.textView3.setText(mParam1.getReview_count());
        adapter = new ReviewsFragment.ReviewAdapter(getActivity(), R.layout.row_item_review,mReviews);
        binding.listView.setAdapter(adapter);
        getReviews();

        binding.buttonCreateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListerner.writeReview(mParam1);
            }
        });

    }

    private final OkHttpClient client = new OkHttpClient();

    void getReviews(){

        HttpUrl url = HttpUrl.parse("https://www.theappsdr.com/api/product/reviews/31b100f4-ec80-4ef7-8ba9-05575e54499f").newBuilder()
                //  .addQueryParameter("units", "imperial")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();

                    try {
                        JSONObject jsonvalue = new JSONObject(body);
                        JSONArray reviewDetails = jsonvalue.getJSONArray("reviews");
                        if(reviewDetails.length()>0){
                            for (int i = 0; i < reviewDetails.length(); i++) {
                                JSONObject detail = reviewDetails.getJSONObject(i);
                                Review review = new Review(detail);
                                mReviews.add(review);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    class ReviewAdapter extends ArrayAdapter<Review> {
        public ReviewAdapter(@NonNull Context context, int resource, @NonNull List<Review> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            RowItemReviewBinding mbinding;
            if(convertView == null){
                mbinding = RowItemReviewBinding.inflate(getLayoutInflater(), parent,false);
                convertView = mbinding.getRoot();
                convertView.setTag(mbinding);
            }else{
                mbinding = (RowItemReviewBinding) convertView.getTag();
            }

            Review review = getItem(position);

            mbinding.textViewReview.setText(review.getReview());
            mbinding.textViewReviewDate.setText(review.getCreated_at());
            //Picasso.get().load(review.getRating()).into(mbinding.imageViewReviewRating);

            ImageView imageview = convertView.findViewById(R.id.imageViewReviewRating);
            if(review.getRating().equals("1")) {
                imageview.setImageResource(R.drawable.stars_1);
            }else if(review.getRating().equals("2")) {
                imageview.setImageResource(R.drawable.stars_2);
            }else if(review.getRating().equals("3")) {
                imageview.setImageResource(R.drawable.stars_3);
            }else if(review.getRating().equals("4")) {
                imageview.setImageResource(R.drawable.stars_4);
            }else if (review.getRating().equals("5")) {
                imageview.setImageResource(R.drawable.stars_5);
            }

            return convertView;
        }
    }

    ReviewListerner mListerner;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListerner = (ReviewListerner) context;
    }

    public interface ReviewListerner{
        void writeReview(Product product);
    }
}