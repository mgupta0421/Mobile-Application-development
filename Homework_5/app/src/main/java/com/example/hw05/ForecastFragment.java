package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.hw05.databinding.ForecastListItemBinding;
import com.example.hw05.databinding.FragmentForecastBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private DataService.City mParam1;

    public ForecastFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ForecastFragment newInstance(DataService.City city) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (DataService.City) getArguments().getSerializable(ARG_PARAM1);

        }
    }


    FragmentForecastBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Forecast> forecasts = new ArrayList<>();
    ForecastAdapter adapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weather forecast");
        adapter = new  ForecastAdapter(getActivity(), R.layout.forecast_list_item,forecasts);
        binding.listView.setAdapter(adapter);
        getDetails();
    }

    Forecast forecast;
    void getDetails() {


       /* RequestBody formBody = new FormBody.Builder()
                .add("appid", "put your key")
                .add("lon", String.valueOf(mParam1.getLon()))
                .add("lat", String.valueOf(mParam1.getLat()))
                .add("units", "imperial")
                .build();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/forecast")
                .post(formBody)
                .build(); */
        HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/forecast").newBuilder()
                .addQueryParameter("appid", "dc2912c5363d9ebec082bab802ea3108")
                .addQueryParameter("units", "imperial")
                .addQueryParameter("lon", String.valueOf(mParam1.getLon()))
                .addQueryParameter("lat", String.valueOf(mParam1.getLat()))
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
                            JSONObject value = new JSONObject(body);
                            JSONArray listvalue= value.getJSONArray("list");

                            forecasts.clear();
                            for (int i = 0; i < listvalue.length(); i++) {
                                JSONObject innervalue = listvalue.getJSONObject(0);
                                forecast = new Forecast(innervalue);
                                forecasts.add(forecast);

                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

    }


    class ForecastAdapter extends ArrayAdapter<Forecast>{
        public ForecastAdapter(@NonNull Context context, int resource, @NonNull List<Forecast> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ForecastListItemBinding mbinding;
            if(convertView == null){
                mbinding = ForecastListItemBinding.inflate(getLayoutInflater(), parent,false);
                convertView = mbinding.getRoot();
                convertView.setTag(mbinding);
            }else{
                mbinding = (ForecastListItemBinding) convertView.getTag();
            }

            Forecast forecast = getItem(position);

            mbinding.textViewDateTime.setText(forecast.getDt_txt());
            mbinding.textViewDesc.setText(forecast.getDescription());
            mbinding.textViewTemp.setText(forecast.getTemp().toString());
            mbinding.textViewTempMin.setText(forecast.getTemp_min().toString());
            mbinding.textViewTempMax.setText(forecast.getTemp_max().toString());
            mbinding.textViewHumidity.setText(forecast.getHumidity().toString());
            return convertView;
        }
    }
}