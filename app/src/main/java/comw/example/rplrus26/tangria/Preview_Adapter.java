package comw.example.rplrus26.tangria;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Preview_Adapter extends RecyclerView.Adapter<Preview_Adapter.MyViewHolder> {

    private ArrayList<questions> questionsArrayList;
    private Context context;
//    private onItemClickListener listener;

    public Preview_Adapter (ArrayList<questions>questionsArrayList,Context context){
        this.questionsArrayList =questionsArrayList;
        this.context =context;
    }

//    public void setListener(onItemClickListener listener) {
//        this.listener = listener;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Preview_Adapter.MyViewHolder holder, int position) {
        final questions questions = questionsArrayList.get(position);
        holder.txtViewQuestion.setText(questions.getPertanyaan());

    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView TVtreatment,txtViewQuestion,TVbagian;
        public MyViewHolder(View itemView) {
            super(itemView);
            TVtreatment = (TextView)itemView.findViewById(R.id.TVtreatment);
            txtViewQuestion = (TextView) itemView.findViewById(R.id.txtViewQuestion);
        }
    }
}
