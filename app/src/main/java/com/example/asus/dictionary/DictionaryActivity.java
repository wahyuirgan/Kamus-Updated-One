package com.example.asus.dictionary;

import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.asus.dictionary.adapter.SearchAdapter;
import com.example.asus.dictionary.helper.DictionaryHelper;
import com.example.asus.dictionary.model.DictionaryModel;

import java.util.ArrayList;
import java.util.Objects;

public class DictionaryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private DictionaryHelper dictionaryHelper;
    private SearchAdapter searchAdapter;

    private ArrayList<DictionaryModel> list = new ArrayList<>();

    RecyclerView recyclerView;
    SearchView searchView;
    String lang_selection;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_eng:
                    lang_selection = "Eng";
                    getData(lang_selection, "");
                    return true;
                case R.id.navigation_ina:
                    lang_selection = "Ina";
                    getData(lang_selection, "");
                    return true;
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        recyclerView = findViewById(R.id.recycle_view);

        searchView = findViewById(R.id.search_bar);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);

        dictionaryHelper = new DictionaryHelper(this);
        searchAdapter = new SearchAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);

        //get Default English - Indonesia
        lang_selection = "Eng";
        getData(lang_selection, "");

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getData(String selection, String search) {
        try {
            dictionaryHelper.open();
            if (search.isEmpty()) {
                list = dictionaryHelper.getAllData(selection);
            } else {
                list = dictionaryHelper.getDataByName(search, selection);
            }

            String title;
            String hint;
            if (Objects.equals(selection, "Eng")) {
                title   = getResources().getString(R.string.engtoina);
                hint    = getResources().getString(R.string.search);
            } else {
                title = getResources().getString(R.string.inakeeng);
                hint    = getResources().getString(R.string.cari);
            }

            if (getSupportActionBar() != null)

                getSupportActionBar().setSubtitle(title);
            searchView.setQueryHint(hint);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dictionaryHelper.close();
        }
        searchAdapter.replaceAll(list);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onQueryTextSubmit(String keyword) {
        getData(lang_selection, keyword);
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onQueryTextChange(String keyword) {
        getData(lang_selection, keyword);
        return false;
    }
}
