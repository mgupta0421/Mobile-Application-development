package com.example.midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.midterm.databinding.FragmentProductsBinding;
import com.example.midterm.databinding.RowItemProductBinding;
import com.example.midterm.models.Product;
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
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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

    FragmentProductsBinding binding;
    ArrayList<Product> mProducts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    ProductAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Products");
        adapter = new  ProductAdapter(getActivity(), R.layout.row_item_product,mProducts);
        binding.listView.setAdapter(adapter);
        getProducts();
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListerner.productname(adapter.getItem(i));
            }
        });

    }

    private final OkHttpClient client = new OkHttpClient();

    void  getProducts(){

        HttpUrl url = HttpUrl.parse("https://www.theappsdr.com/api/products").newBuilder()
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
                        JSONArray productdetaiil = jsonvalue.getJSONArray("products");
                        if(productdetaiil.length()>0){
                            for (int i = 0; i < productdetaiil.length(); i++) {
                                JSONObject detail = productdetaiil.getJSONObject(i);
                                Product product = new Product(detail);
                                mProducts.add(product);
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

    class ProductAdapter extends ArrayAdapter<Product> {
        public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            RowItemProductBinding mbinding;
            if(convertView == null){
                mbinding = RowItemProductBinding.inflate(getLayoutInflater(), parent,false);
                convertView = mbinding.getRoot();
                convertView.setTag(mbinding);
            }else{
                mbinding = (RowItemProductBinding) convertView.getTag();
            }

            Product product = getItem(position);

            mbinding.textViewProductName.setText(product.getName());
            mbinding.textViewProductPrice.setText(product.getPrice());
            mbinding.textViewProductReviews.setText(product.getReview_count());
            mbinding.textViewProductDesc.setText(product.getDescription());
            Picasso.get().load(product.getImg_url()).into(mbinding.imageViewProductIcon);

            return convertView;
        }
    }


    ProductListerner mListerner;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListerner = (ProductListerner) context;
    }

    public interface ProductListerner{
        void productname(Product product);
    }
}