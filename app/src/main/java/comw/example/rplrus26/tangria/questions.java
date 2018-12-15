package comw.example.rplrus26.tangria;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class questions implements Parcelable {
    private int id;
    private String pertanyaan;
    private ArrayList<questions>questionsArrayList;
    private ArrayList<String> answers = new ArrayList<>();

    public void addAnswer(String answer) {
        answers.clear();
        this.answers.add(answer);
    }


    public questions(int id, String pertanyaan,ArrayList<String>answers){
        this.setId(id);
        this.setPertanyaan(pertanyaan);
        this.answers = answers;
    }

    public questions(){

    }

    protected questions(Parcel in) {
        id = in.readInt();
        pertanyaan = in.readString();
        answers = in.createStringArrayList();
        setId(in.readInt());
        setPertanyaan(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(pertanyaan);
        dest.writeStringList(answers);
        dest.writeString(getPertanyaan());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<questions> CREATOR = new Creator<questions>() {
        @Override
        public questions createFromParcel(Parcel in) {
            return new questions(in);
        }

        @Override
        public questions[] newArray(int size) {
            return new questions[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
