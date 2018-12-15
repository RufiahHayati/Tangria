package comw.example.rplrus26.tangria;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    ArrayList<questions> questionsArrayList;
    ViewPager viewPager;
    QuestionAdapter adapter;
    Button previousBtn,nextBtn;
    questions question;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        viewPager = findViewById(R.id.viewpager);
        new load_data().execute();
        previousBtn = findViewById(R.id.previous_btn);
        nextBtn = findViewById(R.id.next_btn);

        //        previousBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//            }
//        });
//
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (viewPager.getCurrentItem() == questionsArrayList.size()) {
//
//                }else {
//                    final int position = viewPager.getCurrentItem();
//                    QuestionFragment fragment = (QuestionFragment) adapter.getItem(position);
//                    final questions survey = questionsArrayList.get(position);
//                    final String answer = fragment.getAnswer();
//                    survey.addAnswer(answer);
//                    Log.e("answer", "onClick: " + answer);
//                    viewPager.setCurrentItem(position + 1);
//
//                }
//            }
//        });
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                if (i == questionsArrayList.size()) {
//                    nextBtn.setText("Finish");
//                } else {
//                    nextBtn.setText("Next");
//                }
//                if (i == 0) {
//                    previousBtn.setVisibility(View.GONE);
//                } else {
//                    previousBtn.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
    }
//
//    public void changeToDesiredQuestion(int position) {
//        viewPager.setCurrentItem(position);
//    }

    @SuppressLint("StaticFieldLeak")
    public class load_data extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObject;

            try {
                String url = OnlyUrl.url + "question.php";
                System.out.println("url " + url);
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
            Log.d("hasil json ", "onPostExecute: " + jsonObject.toString());
            try {
                if (jsonObject != null) {
                    JSONArray hasiljson = jsonObject.getJSONArray("Result");
                    questionsArrayList = new ArrayList<questions>();
                    for (int i = 0; i < hasiljson.length(); i++) {
                        ArrayList<String>answer = new ArrayList<String>();
                        answer.add("Yes");
                        answer.add("No");
                        questionsArrayList.add(new questions(hasiljson.getJSONObject(i).getInt("id_question"),hasiljson.getJSONObject(i).getString("pertanyaan"),answer));
                    }
                        initFragment(questionsArrayList);
                } else {
                    Log.d("TAG", "onPostExecute: " + "json object null");
                }
            } catch (Exception e) {
                Log.d("errorku ", "onPostExecute: " + e.toString());
            }
        }
    }
    void initFragment(ArrayList<questions> questionsArrayList) {
        adapter = new QuestionAdapter(getSupportFragmentManager());
        Log.e("SlideActivity", "initFragment: " + questionsArrayList.size());
        for (int i = 0; i <= questionsArrayList.size(); i++) {
            questions quest = questionsArrayList.get(i);
            adapter.initFragment(QuestionFragment.newInstance(quest.getPertanyaan(), quest.getId(), questionsArrayList.get(i).getAnswers(),R.layout.row_item));
            Log.e("Question", "initFragment: " + i);
            Log.e("Question", "initFragment: " + quest.getPertanyaan());
        }
        viewPager.setAdapter(adapter);
    }

}