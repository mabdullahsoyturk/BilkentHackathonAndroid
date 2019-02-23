package com.example.bilkent.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private String questionText;
    private String[] choices;

    public Question(String questionText, String[] choices) {
        this.setChoices(choices);
        this.setQuestionText(questionText);
    }


    protected Question(Parcel in) {
        questionText = in.readString();
        choices = in.createStringArray();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionText);
        dest.writeStringArray(choices);
    }
}
