package com.example.asus.dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DictionaryModel implements Parcelable {
    private int id;
    private String word;
    private String description;
    private String category;

    public DictionaryModel(String word, String description){
        this.word       = word;
        this.description  = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.description);
        dest.writeString(this.category);
    }

    public DictionaryModel() {
    }

    private DictionaryModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.description = in.readString();
        this.category = in.readString();
    }

    public static final Creator<DictionaryModel> CREATOR = new Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel source) {
            return new DictionaryModel(source);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };
}
