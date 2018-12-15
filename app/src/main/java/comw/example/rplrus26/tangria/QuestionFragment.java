package comw.example.rplrus26.tangria;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    private int id = 0;
    private int layout = 0;
    private String question = "";
    private RadioGroup radioGroup;
    private Preview_Adapter adapter;
    private ArrayList<String> answer = new ArrayList<>();
    TextView TVtreatment, txtViewQuestion;
    RadioButton[] CheckYes;
    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(String question, int id, ArrayList<String> answer,int layout) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString("question", question);
        args.putInt("id", id);
        args.putStringArrayList("answer", answer);
        args.putInt("layout",layout);
        questionFragment.setArguments(args);

        return questionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (getArguments() != null) {
            question = getArguments().getString("question");
            id = getArguments().getInt("id");
            answer = getArguments().getStringArrayList("answer");
            layout = getArguments().getInt("layout");
        }
        View view = inflater.inflate(layout, container, false);
        if (layout == R.layout.row_item) {
            txtViewQuestion = view.findViewById(R.id.txtViewQuestion);
            txtViewQuestion.setText(question);
            radioGroup = view.findViewById(R.id.radio_group);
            CheckYes = new RadioButton[answer.size()];
            for (int i = 0; i < answer.size(); i++) {
                CheckYes[i] = new RadioButton(getActivity());
                CheckYes[i].setText(answer.get(i));
                CheckYes[i].setId(i);
                radioGroup.addView(CheckYes[i]);
            }
        }
        return view;
    }

    public String getAnswer() {
        switch (layout){
            case R.layout.row_item:
                String answer = "";
                for (RadioButton radioButton : CheckYes) {
                    if(radioButton.getId() == radioGroup.getCheckedRadioButtonId()){
                        answer = radioButton.getText().toString();
                    }
                }
                return answer;
            default:
                return "";
        }
    }

}
