package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw05.databinding.FragmentWeatherBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private DataService.City mParam1;
    public WeatherFragment() {
        // Required empty public constructor
    }
    public static WeatherFragment newInstance(DataService.City city) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (DataService.City)getArguments().getSerializable(ARG_PARAM1);

        }
    }

        FragmentWeatherBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Forecast");
        binding.textViewCityName.setText(mParam1.toString());
        getWeather();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListerner.moreDetail(mParam1);
            }
        });
    }

    private final OkHttpClient client = new OkHttpClient();
    Weather weather;
    void getWeather(){
       /* RequestBody formBody = new FormBody.Builder()
                .add("appid", "put your key")
                .add("lon", String.valueOf(mParam1.getLon()))
                .add("lat", String.valueOf(mParam1.getLat()))
                .add("units", "imperial")
                .build();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather")
                .post(formBody)
                .build(); */

        HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder()
                .addQueryParameter("appid", "TYPE YOUR KEY")
              //  .addQueryParameter("units", "imperial")
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
                        weather = new Weather(value);
                        Log.d("DEMO",weather.getTemp().toString());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.textViewTemp.setText(weather.getTemp().toString());
                                binding.textViewTempMin.setText(weather.getTemp_min().toString());
                                binding.textViewTempMax.setText(weather.getTemp_max().toString());
                                binding.textViewDesc.setText(weather.getDescription());
                                binding.textViewHumidity.setText(weather.getHumidity().toString());
                                binding.textViewWindSpeed.setText(weather.getSpeed().toString());
                                binding.textViewWindDegree.setText(weather.getDeg().toString());
                                binding.textViewCloudiness.setText(weather.getCloudiness().toString());
                               // Picasso.get().load(weather.getIcon()).into(binding.imageViewWeatherIcon);

                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    WeatherListerner mListerner;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListerner = (WeatherListerner) context;
    }

    public interface WeatherListerner{
        void moreDetail(DataService.City city);
    }
}