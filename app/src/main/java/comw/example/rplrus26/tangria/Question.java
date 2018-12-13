package comw.example.rplrus26.tangria;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Question extends AppCompatActivity {

    ArrayList<questions> questionsArrayList = new ArrayList<questions>();
    ViewPager viewPager;
    JSONArray Hasiljson;
    QuestionAdapter adapter;
    Button previousBtn;
    Button nextBtn;
    questions questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        viewPager = findViewById(R.id.viewpager);
        previousBtn = findViewById(R.id.previous_btn);
        nextBtn = findViewById(R.id.next_btn);

        questions = new questions();
        new Adata().execute();


    }

    @SuppressLint("StaticFieldLeak")
    public class Adata extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            //kasih loading
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObject;
            try {
                String url = OnlyUrl.url + "question.php";
                System.out.println("urlnya : " + url);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                inputStream.close();
                String json = stringBuilder.toString();
                jsonObject = new JSONObject(json);
            } catch (Exception e) {
                jsonObject = null;
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                questionsArrayList = new ArrayList<>();
                Hasiljson = jsonObject.getJSONArray("Result");
                for (int i = 0; i < Hasiljson.length(); i++) {
                    questions a = new questions();
                    a.setId(Hasiljson.getJSONObject(i).getInt("id_question"));
                    a.setPertanyaan(Hasiljson.getJSONObject(i).getString("pertanyaan"));
                    Log.e("question", "onPostExecute: " + Hasiljson.getJSONObject(i).getInt("id_question") );
                    Log.e("question", "onPostExecute: " + Hasiljson.getJSONObject(i).getString("pertanyaan"));
                    questionsArrayList.add(a);
                }
                initFragment(questionsArrayList);
            } catch (Exception ignored) {
                System.out.println("erornya " + ignored);
            }
        }

    }

    void initFragment(ArrayList<questions> questionsArrayList) {
        adapter = new QuestionAdapter(getSupportFragmentManager());
        Log.e("SlideActivity", "initFragment: " + questionsArrayList.size());
        for (int i = 0; i <= questionsArrayList.size(); i++) {
            questions quest = questionsArrayList.get(i);
            adapter.initFragment(QuestionFragment.newInstance(quest.getPertanyaan(), quest.getId()));
            Log.e("Question", "initFragment: " + i );
            Log.e("Question", "initFragment: " + quest.getPertanyaan() );
        }
        viewPager.setAdapter(adapter);
    }
}