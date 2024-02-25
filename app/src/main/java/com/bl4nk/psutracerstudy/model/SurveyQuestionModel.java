package com.bl4nk.psutracerstudy.model;

import java.util.List;

public class SurveyQuestionModel {
    private String questionText;
    private List<String> options;
    private int selectedOptionIndex; // Index of the selected option

    // Constructor
    public SurveyQuestionModel(String questionText, List<String> options) {
        this.questionText = questionText;
        this.options = options;
    }

    // Getters and setters
    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }
}
