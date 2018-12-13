package comw.example.rplrus26.tangria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnNext;
    EditText textNama;
    EditText textAlamat;
    inputNama Userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = findViewById(R.id.btn_next);
        textNama = findViewById(R.id.edtNama);
        textAlamat = findViewById(R.id.edtAlamat);

        Userlist = new inputNama();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Userlist.setNama(textNama.getText().toString());
                Userlist.setAlamat(textAlamat.getText().toString());
                if (Userlist.getNama().equals("") || Userlist.getAlamat().equals("")){
                    Toast.makeText(getApplicationContext(),"Notway Empty", Toast.LENGTH_SHORT).show();
                }else {
                    new ambildata().execute();
                }
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    public class ambildata extends AsyncTask<Void, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            //kasih loading
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObject;
            try {

                String url="http://192.168.6.88/kuesioner/android/input_name.php?nama=" + Userlist.getNama()+ "&alamat=" + Userlist.getAlamat()+ "";
                System.out.println("url ku " +url);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream, "iso-8859-1"
                ), 8);
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
//            //String url = "http://192.168.6.204/login/getData.php";
//            System.out.println("Result" +jsonObject.toString());
//            Log.d("Result json ", "onPostExecute: " + jsonObject.toString());
            if (jsonObject != null) {
                try {
                    JSONObject Result = jsonObject.getJSONObject("Result");
                    String sukses = Result.getString("Sukses");
                    Log.d("hasil sukses ", "onPostExecute: " + sukses);

                    if (sukses.equals("true")) {
                        Toast.makeText(getApplicationContext(), " berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Question.class);
                        startActivity(intent);
                        //to main menu
                    } else {
                        Toast.makeText(getApplicationContext(), " gagal", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ignored) {
                    Toast.makeText(MainActivity.this, ignored.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "json object is null", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
