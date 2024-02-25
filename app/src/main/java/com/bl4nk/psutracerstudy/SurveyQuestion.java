package com.bl4nk.psutracerstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bl4nk.psutracerstudy.model.SurveyQuestionModel;

import java.util.Arrays;
import java.util.List;

public class SurveyQuestion extends AppCompatActivity {

    private List<SurveyQuestionModel> surveyQuestionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_question);

        initializeSurveyQuestions();

        LinearLayout surveyLayout = findViewById(R.id.surveyLayout);

        // Loop through each question and add it to the layout
        for (int i = 0; i < surveyQuestionModel.size(); i++) {
            SurveyQuestionModel question = surveyQuestionModel.get(i);

            // Inflate the layout for each question
            @SuppressLint("InflateParams") LinearLayout questionLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_question, null);

            // Set the question text with number
            TextView questionTextView = questionLayout.findViewById(R.id.questionTextView);
            questionTextView.setText((i + 1) + ". " + question.getQuestionText());

            // Get the radio group for options
            RadioGroup radioGroup = questionLayout.findViewById(R.id.radioGroup);

            // Add radio buttons for each option
            for (int j = 0; j < question.getOptions().size(); j++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(question.getOptions().get(j));
                radioButton.setId(j); // Set unique ID for each radio button
                radioGroup.addView(radioButton); // Add radio button to the group
            }

            // Add the question layout to the main layout
            surveyLayout.addView(questionLayout);
        }
    }

    private void initializeSurveyQuestions() {
        // Hardcoded survey questions and options
        surveyQuestionModel = Arrays.asList(
                new SurveyQuestionModel("Are you presently employed?", Arrays.asList("Yes", "Never Employed", "Not yet Employed")),
                new SurveyQuestionModel("Please check the reason(s) why you are not yet employed. You may check more than one answer", Arrays.asList("No connections", "No job opportunity", "Family concern", "Engaged in further study", "Lack of professional eligibility requirements", "Have plans to seek job out of the country", "Health-related reasons", "Lack of work experience", "Starting pay is too low", "Other")),
                new SurveyQuestionModel("Employment status", Arrays.asList("Regular/Permanent", "Temporary/Contractual", "Self-employed", "Other")),
                new SurveyQuestionModel("Present Occupation", Arrays.asList("Teacher", "Welcome Alumni !", "Project Manager", "Self employed", "Unemployed", "Other")),
                new SurveyQuestionModel("What is the nature of the business/operation your present company/organization is engaged in?", Arrays.asList("Manufacturing", "Academe", "Hotel", "Service", "Public Office", "Restaurant", "Retailing", "Auditing", "Financial", "Agriculture", "Other")),
                new SurveyQuestionModel("What is your present position?", Arrays.asList("Managerial level", "Supervisory level", "Rank and File", "Other")),
                new SurveyQuestionModel("Is this your first job after college?", Arrays.asList("Yes", "No")),
                new SurveyQuestionModel("What are your reason(s) for staying on the job? You may check more than one answer", Arrays.asList("Salaries and benefits", "Career challenge", "Hotel", "Related to course or study", "Good human relations with employer/fellow employees", "Proximity to residence", "Peer influence", "Family influence", "Other")),
                new SurveyQuestionModel("Is your first job related to the course you took up in college?", Arrays.asList("Yes", "No")),
                new SurveyQuestionModel("If NO, what were your reasons for accepting the job? You may check more than one answer.", Arrays.asList("Salaries & benefits", "Career challenge", "Related to special skills", "Proximity to residence", "Other")),
                new SurveyQuestionModel("What was your reason(s) for changing job? You may check more than one answer.", Arrays.asList("Salaries & benefits", "Career challenge", "Related to special skills", "Proximity to residence", "Relations with people in the organization", "Other")),
                new SurveyQuestionModel("How long did you stay in your first job?", Arrays.asList("Less than a month", "1-6 months", "7-11 months", "1 year to less than 2 years", "2 years to less than 3 years", "3 years to less than 4 years", "Other")),
                new SurveyQuestionModel("How did you find your first job?", Arrays.asList("Arranged by school's job placement officer", "Response to an advertisement", "As walk-in applicant", "Recommended by someone", "Information from friends", "Job Fair", "Other")),
                new SurveyQuestionModel("How long did it take you to land your first job after graduating from college?", Arrays.asList("Less than a month", "1-6 months", "7-11 months", "1 year to less than 2 years", "2 years to less than 3 years", "3 years to less than 4 years", "Other")),
                new SurveyQuestionModel("Was the curriculum you had in college relevant to your first job?", Arrays.asList("Yes", "No")),
                new SurveyQuestionModel("If YES. What competencies learned in college did you find most useful in your first job?", Arrays.asList("Problem-solving skills", "Communication skills", "Critical Thinking skills", "Human Relations skills", "Information Technology skills", "Entrepreneurial skills", "Skills related to my course such as", "Other"))
                // Add more questions here as needed
        );
    }
}