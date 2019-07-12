package com.example.asus.dictionary.loading;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.asus.dictionary.DictionaryActivity;
import com.example.asus.dictionary.model.DictionaryModel;
import com.example.asus.dictionary.preference.DictionaryPreference;
import com.example.asus.dictionary.R;
import com.example.asus.dictionary.helper.DictionaryHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class LoadingActivity extends AppCompatActivity {
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        progressBar =  findViewById(R.id.progress_bar);
        new LoadData().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadData extends AsyncTask<Void, Integer, Void> {

        final String TAG = LoadData.class.getSimpleName();

        DictionaryHelper dictionaryHelper;
        DictionaryPreference dictionaryPreference;

        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            dictionaryHelper  = new DictionaryHelper(LoadingActivity.this);
            dictionaryPreference   = new DictionaryPreference(LoadingActivity.this);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... params) {

            Boolean firstRun = dictionaryPreference.getFirstRun();
            if (firstRun) {
                ArrayList<DictionaryModel> kamusModelsEngToInd = preLoadRaw("Eng");
                ArrayList<DictionaryModel> kamusModelsIndKeEng = preLoadRaw("Ina");

                dictionaryHelper.open();
                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 100.0;
                int total_size = kamusModelsEngToInd.size() + kamusModelsIndKeEng.size();
                Double progressDiff = (progressMaxInsert - progress) / total_size;

                //Eng to Ina
                dictionaryHelper.beginTransaction();
                try {
                    for (DictionaryModel dictionaryModel : kamusModelsEngToInd) {
                        dictionaryHelper.insertTransaction(dictionaryModel, "Eng");
                    }
                    dictionaryHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                dictionaryHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                //Ina to Eng
                dictionaryHelper.beginTransaction();
                try {
                    for (DictionaryModel dictionaryModel : kamusModelsIndKeEng) {
                        dictionaryHelper.insertTransaction(dictionaryModel, "Ina");
                    }
                    dictionaryHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                dictionaryHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                //close untuk semua insert selesai
                dictionaryHelper.close();

                dictionaryPreference.setFirstRun(false);
                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception ignored) {
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }


        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(LoadingActivity.this, DictionaryActivity.class);
            startActivity(i);
            finish();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<DictionaryModel> preLoadRaw(String selection) {
        int raw_data;
        if(Objects.equals(selection, "Eng")){
            raw_data = R.raw.english_indonesia;
        }else{
            raw_data = R.raw.indonesia_english;
        }
        ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(raw_data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            //noinspection InfiniteLoopStatement
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                DictionaryModel dictionaryModel;

                dictionaryModel = new DictionaryModel(splitstr[0], splitstr[1]);
                dictionaryModels.add(dictionaryModel);
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaryModels;
    }
}
