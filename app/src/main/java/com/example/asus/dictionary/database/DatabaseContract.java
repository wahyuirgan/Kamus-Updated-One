package com.example.asus.dictionary.database;

import android.provider.BaseColumns;


public class DatabaseContract {

    public static String TABLE_INA_KE_ENG  = "ina_ke_eng";
    public static String TABLE_ENG_TO_INA  = "eng_to_ina";

    public static final class KamusColumns implements BaseColumns {
        public static String FIELD_WORD     = "word";
        public static String FIELD_MEANING     = "meaning";
    }

}
