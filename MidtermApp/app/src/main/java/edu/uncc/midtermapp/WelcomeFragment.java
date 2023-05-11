package edu.uncc.midtermapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.midtermapp.databinding.FragmentWelcomeBinding;
import edu.uncc.midtermapp.models.Question;
import edu.uncc.midtermapp.models.TriviaResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeFragment extends Fragment {
    private ArrayList<Question> mTriviaQuestions = new ArrayList<Question>();
    TriviaResponse triviaResponse;
    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestions();
            }
        });
        getActivity().setTitle("Welcome");
    }

    private final OkHttpClient client = new OkHttpClient();

    void getQuestions(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/trivia")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Error loading questions !!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){
                    String body = response.body().string();
                    Gson gson = new Gson();
                    triviaResponse = gson.fromJson(body, TriviaResponse.class);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mListener.sendQuestionsToTriva(triviaResponse.getQuestions());
                        }
                    });

/*
                    try {
                        JSONObject root = new JSONObject(body);
                        JSONArray questionsJSONArray = root.getJSONArray("questions");

                        mTriviaQuestions.clear();
                        for (int i = 0; i < questionsJSONArray.length(); i++) {
                            JSONObject questionJSON = questionsJSONArray.getJSONObject(i);
                            Question question = new Question(questionJSON);
                            mTriviaQuestions.add(question);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mListener.sendQuestionsToTriva(mTriviaQuestions);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

 */
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Error loading questions !!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    WelcomeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (WelcomeListener) context;
    }

    interface WelcomeListener{
        void sendQuestionsToTriva(ArrayList<Question> questions);
    }

}