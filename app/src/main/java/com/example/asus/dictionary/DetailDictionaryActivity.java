package com.example.asus.dictionary;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

public class DetailDictionaryActivity extends AppCompatActivity {

    public static final String ITEM_VOCABULARY    = "loading_picture";
    public static final String ITEM_MEANING        = "meaning";
    public static final String ITEM_CATEGORY    = "category";

    TextView tvDetailVocabulary, tvDetailMeaning, tvCategory;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dictionary);

        if (getSupportActionBar() != null)

            getSupportActionBar().setTitle(getIntent().getStringExtra(ITEM_VOCABULARY));
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(getIntent().getStringExtra(ITEM_CATEGORY));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDetailVocabulary  = findViewById(R.id.tvDetailVocabulary);
        tvDetailMeaning      = findViewById(R.id.tvDetailMeaning);
        tvCategory  = findViewById(R.id.tvCategory);

        tvDetailVocabulary.setText(getIntent().getStringExtra(ITEM_VOCABULARY));
        tvDetailMeaning.setText(getIntent().getStringExtra(ITEM_MEANING));
        tvCategory.setText(getIntent().getStringExtra(ITEM_CATEGORY));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
