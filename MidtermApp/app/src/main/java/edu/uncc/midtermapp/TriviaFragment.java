package edu.uncc.midtermapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.midtermapp.databinding.FragmentTriviaBinding;
import edu.uncc.midtermapp.models.Answer;
import edu.uncc.midtermapp.models.Question;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TriviaFragment extends Fragment {
    private ArrayList<Question> mTriviaQuestions = new ArrayList<Question>();
    private static final String ARG_PARAM_QUESTIONS = "ARG_PARAM_QUESTIONS";
    ArrayList<Answer> answers = new ArrayList<>();
    ArrayAdapter<Answer> adapter;

    int stat = 0;
    boolean answeredIncorrectly = false;
    int currentQuestionIndex = 0;
    Question currentQuestion;

    public TriviaFragment() {
        // Required empty public constructor
    }

    public static TriviaFragment newInstance(ArrayList<Question> questions) {
        TriviaFragment fragment = new TriviaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_QUESTIONS, questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTriviaQuestions = (ArrayList<Question>) getArguments().getSerializable(ARG_PARAM_QUESTIONS);
        }
    }

    FragmentTriviaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTriviaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Trivia");

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, answers);
        binding.listViewAnswers.setAdapter(adapter);

        binding.listViewAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Answer selectedAnswer = adapter.getItem(position);

                RequestBody formBody = new FormBody.Builder()
                        .add("question_id", currentQuestion.getQuestion_id())
                        .add("answer_id", selectedAnswer.getAnswer_id())
                        .build();
                Request request = new Request.Builder()
                        .url("https://www.theappsdr.com/api/trivia/checkAnswer")
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Error calling check Answer!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            String body = response.body().string();
                            try {
                                JSONObject root = new JSONObject(body);
                                if(root.getBoolean("isCorrectAnswer")) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //need to go to the next question ..
                                            //of if the last question then go to the stats screen.
                                            if(!answeredIncorrectly){
                                                stat = stat + 1;
                                            }

                                            if(currentQuestionIndex == mTriviaQuestions.size() - 1){ //already on the last question.
                                                mListener.gotoStats(stat, mTriviaQuestions.size());
                                            } else {
                                                answeredIncorrectly = false;
                                                currentQuestionIndex = currentQuestionIndex + 1;
                                                currentQuestion = mTriviaQuestions.get(currentQuestionIndex);
                                                displayQuestion();
                                            }
                                        }
                                    });
                                } else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            answeredIncorrectly = true;
                                            Toast.makeText(getActivity(), "Incorrect Answer!!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "Error calling check Answer!!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Error calling check Answer!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

            }
        });

        if(mTriviaQuestions.size() > 0){
            currentQuestionIndex = 0;
            currentQuestion = mTriviaQuestions.get(0);
            displayQuestion();
        } else {
            Toast.makeText(getActivity(), "Trivia has no questions!!", Toast.LENGTH_SHORT).show();
        }

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.startNewGame();
            }
        });
    }

    void displayQuestion(){
        binding.textViewTriviaTopStatus.setText("Question " + (currentQuestionIndex + 1) + " of " + mTriviaQuestions.size());
        binding.textViewTriviaQuestion.setText(currentQuestion.getQuestion_text());
        answers.clear();
        answers.addAll(currentQuestion.getAnswers());
        adapter.notifyDataSetChanged();

        if(currentQuestion.getQuestion_url() != null){
            Picasso.get().load(currentQuestion.getQuestion_url()).into(binding.imageViewQuestion);
        } else {
            binding.imageViewQuestion.setImageResource(R.drawable.no_image_ic);
        }
    }

    TriviaListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TriviaListener) context;
    }

    interface TriviaListener{
        void gotoStats(int stat, int size);
        void startNewGame();
    }
}