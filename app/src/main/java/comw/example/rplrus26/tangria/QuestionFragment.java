package comw.example.rplrus26.tangria;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    private String pertanyaan = "";
    private int id = 0;

    TextView TVtreatment, txtViewQuestion;

    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(String pertanyaan, int id ) {
        QuestionFragment questionFragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putString("pertanyaan", pertanyaan);
        args.putInt("id", id);

        questionFragment.setArguments(args);

        return questionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (getArguments() != null) {
            pertanyaan = getArguments().getString("pertanyaan");
            id = getArguments().getInt("id");
        }
        View view = inflater.inflate(R.layout.row_item, container, false);

        TVtreatment = view.findViewById(R.id.TVtreatment);

        txtViewQuestion = view.findViewById(R.id.txtViewQuestion);
        txtViewQuestion.setText(pertanyaan);
        return view;
    }

}
