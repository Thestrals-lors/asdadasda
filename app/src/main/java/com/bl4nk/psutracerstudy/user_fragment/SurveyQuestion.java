package com.bl4nk.psutracerstudy.user_fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bl4nk.psutracerstudy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SurveyQuestion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyQuestion extends Fragment {

    private FirebaseFirestore fireStore;
    private FirebaseUser user;

    private TextView Question1Tv, Question3, Question4, Question5, Question6, Question12, Question13, Question14, Question7, Question9, Question15;

    private RadioGroup employment_status_radio_group;
    private RadioButton selected_employment_status;

    // Initialize variables for question 7
    RadioGroup firstJobRadioGroup;
    RadioButton selectedFirstJobRadioButton;

    // Initialize variables for question 9
    RadioGroup firstJobRelatedRadioGroup;
    RadioButton selectedFirstJobRelatedRadioButton;

    // Initialize variables for question 15
    RadioGroup curriculumRelevanceRadioGroup;
    RadioButton selectedCurriculumRelevanceRadioButton;

    String employmentStatus, firstJob, jobRelated, curriculum, occupationStatus, occupation, business, position, jobDuration, jobFinding, jobTime;

    String suggestion171, suggestion172, suggestion173, suggestion174, suggestion175;


    RadioGroup employmentStatusRadioGroup;
    RadioButton employmentStatusRadioButton;
    EditText other_occupation_edittext_q3;

    RadioGroup occupationRadioGroup;
    RadioButton occupationRadioButton;
    EditText other_occupation_edittext;

    RadioGroup business_type_radio_group;
    RadioButton businessTypeRadioButton;
    EditText other_business_edittext;

    RadioGroup present_position_radio_group_q6;
    RadioButton presentPositionRadioButton;
    EditText other_position_edittext_q6;

    RadioGroup first_job_duration_radio_group;
    RadioButton firstJobDurationRadioButton;
    EditText other_duration_edit_text;

    RadioGroup first_job_finding_radio_group;
    RadioButton firstJobFindingRadioButton;
    EditText other_finding_edit_text;

    RadioGroup time_to_land_first_job_radio_group;
    RadioButton timeToLandFirstJobRadioButton;
    EditText other_time_edit_text;


    CheckBox noConnectionsCheckbox;
    CheckBox noJobOpportunityCheckbox;
    CheckBox familyConcernCheckbox;
    CheckBox noInterestCheckbox;
    CheckBox healthRelatedCheckbox;
    CheckBox lackEligibilityCheckbox;
    CheckBox lackExperienceCheckbox;
    CheckBox lowStartingPayCheckbox;
    CheckBox furtherStudyCheckbox;
    CheckBox seekJobAbroadCheckbox;
    CheckBox otherOccupationCheckbox;

    // Get reference to the other occupation EditText
    EditText otherOccupationEditText;

    // Initialize a StringBuilder to store the selected reasons
    List<String> selectedReasons;

    CheckBox salariesBenefitsCheckbox;
    CheckBox careerChallengeCheckbox;
    CheckBox courseRelatedCheckbox;
    CheckBox goodHumanRelationsCheckbox;
    CheckBox proximityResidenceCheckbox;
    CheckBox peerInfluenceCheckbox;
    CheckBox familyInfluenceCheckbox;
    CheckBox otherReasonsCheckbox;

    // Declare EditText for other reasons
    EditText otherReasonsEditText;

    List<String> selectedReasonsList;


    CheckBox salariesBenefitsCheckboxQ10;
    CheckBox careerChallengeCheckboxQ10;
    CheckBox relatedSpecialSkillsCheckbox;
    CheckBox proximityResidenceCheckboxQ10;
    CheckBox otherReasonCheckboxQ10;

    // Initialize EditText for other reasons in question 10
    EditText otherReasonEditTextQ10;
    List<String> selectedReasonsListQ10;

    CheckBox salariesBenefitsCheckboxQ11;
    CheckBox careerChallengeCheckboxQ11;
    CheckBox relatedSpecialSkillsCheckboxQ11;
    CheckBox proximityResidenceCheckboxQ11;
    CheckBox relationsWithPeopleCheckboxQ11;
    CheckBox otherReasonCheckboxQ11;

    // EditText variable declaration
    EditText otherReasonEditTextQ11;

    List<String> selectedReasonsListQ11;

    CheckBox problemSolvingCheckbox;
    CheckBox communicationSkillsCheckbox;
    CheckBox criticalThinkingCheckbox;
    CheckBox humanRelationsCheckbox;
    CheckBox informationTechnologyCheckbox;
    CheckBox entrepreneurialSkillsCheckbox;
    CheckBox courseRelatedSkillsCheckbox;
    CheckBox otherSkillsCheckbox;

    // Initialize EditText for other skills
    EditText otherSkillsEditText;

    // Initialize list to store selected skills
    List<String> selectedSkillsList;

    EditText companyNameEditText;
    EditText companyAddressEditText;

    RadioGroup suggestion1RadioGroup;
    RadioGroup suggestion2RadioGroup;
    RadioGroup suggestion3RadioGroup;
    RadioGroup suggestion4RadioGroup;
    RadioGroup suggestion5RadioGroup;

    RadioButton selectedSuggestion1RadioButton;
    RadioButton selectedSuggestion2RadioButton;
    RadioButton selectedSuggestion3RadioButton;
    RadioButton selectedSuggestion4RadioButton;
    RadioButton selectedSuggestion5RadioButton;


    private Button submitBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SurveyQuestion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SurveyQuestion.
     */
    // TODO: Rename and change types and number of parameters
    public static SurveyQuestion newInstance(String param1, String param2) {
        SurveyQuestion fragment = new SurveyQuestion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);


        question3();
        question4();
        question5();
        question6();
        question12();
        question13();
        question14();

        clickListener();
    }

    private void question3() {
        other_occupation_edittext_q3.setEnabled(false);
        employmentStatusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                employmentStatusRadioButton = getView().findViewById(checkedId);

                if (checkedId == R.id.other_occupation_radio_button_q3) {
                    Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    other_occupation_edittext_q3.setEnabled(true);
                    // Request focus and show the keyboard
                    other_occupation_edittext_q3.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(other_occupation_edittext_q3, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    other_occupation_edittext_q3.setText(null);
                    other_occupation_edittext_q3.setEnabled(false);
                    // Hide the keyboard if it's open
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(other_occupation_edittext_q3.getWindowToken(), 0);
                }
            }
        });
    }

    private void question4() {
        other_occupation_edittext.setEnabled(false);
        occupationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                occupationRadioButton = getView().findViewById(checkedId);

                if (checkedId == R.id.other_occupation_radio_button) {
                    Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    other_occupation_edittext.setEnabled(true);
                    // Request focus and show the keyboard
                    other_occupation_edittext.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(other_occupation_edittext, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    other_occupation_edittext.setText(null);
                    other_occupation_edittext.setEnabled(false);
                    // Hide the keyboard if it's open
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(other_occupation_edittext.getWindowToken(), 0);
                }
            }
        });
    }

    private void question5() {
        other_business_edittext.setEnabled(false);
        business_type_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                businessTypeRadioButton = getView().findViewById(checkedId);

                if (checkedId == R.id.other_business_radio_button) {
                    Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    other_business_edittext.setEnabled(true);
                    // Request focus and show the keyboard
                    other_business_edittext.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(other_business_edittext, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    other_business_edittext.setText(null);
                    other_business_edittext.setEnabled(false);
                    // Hide the keyboard if it's open
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(other_business_edittext.getWindowToken(), 0);
                }
            }
        });
    }

    private void question6() {
        other_position_edittext_q6.setEnabled(false);
        present_position_radio_group_q6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                presentPositionRadioButton = getView().findViewById(checkedId);

                if (checkedId == R.id.other_position_radio_button_q6) {
                    Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    other_position_edittext_q6.setEnabled(true);
                    // Request focus and show the keyboard
                    other_position_edittext_q6.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(other_position_edittext_q6, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    other_position_edittext_q6.setText(null);
                    other_position_edittext_q6.setEnabled(false);
                    // Hide the keyboard if it's open
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(other_position_edittext_q6.getWindowToken(), 0);
                }
            }
        });
    }

    private void question12() {
        other_duration_edit_text.setEnabled(false);
        first_job_duration_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                firstJobDurationRadioButton = getView().findViewById(checkedId);
                if (checkedId == R.id.other_duration_radio_button) {
                    Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    other_duration_edit_text.setEnabled(true);
                    // Request focus and show the keyboard
                    other_duration_edit_text.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(other_duration_edit_text, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    other_duration_edit_text.setText(null);
                    other_duration_edit_text.setEnabled(false);
                    // Hide the keyboard if it's open
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(other_duration_edit_text.getWindowToken(), 0);
                }
            }
        });
    }

    private void question13() {
        other_finding_edit_text.setEnabled(false);
        first_job_finding_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                firstJobFindingRadioButton = getView().findViewById(checkedId);
                if (checkedId == R.id.other_finding_radio_button) {
                    Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    other_finding_edit_text.setEnabled(true);
                    // Request focus and show the keyboard
                    other_finding_edit_text.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(other_finding_edit_text, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    other_finding_edit_text.setText(null);
                    other_finding_edit_text.setEnabled(false);
                    // Hide the keyboard if it's open
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(other_finding_edit_text.getWindowToken(), 0);
                }
            }
        });
    }

    private void question14() {
        other_time_edit_text.setEnabled(false);
        time_to_land_first_job_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                timeToLandFirstJobRadioButton = getView().findViewById(checkedId);
                if (checkedId == R.id.other_time_radio_button) {
                    Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    other_time_edit_text.setEnabled(true);
                    // Request focus and show the keyboard
                    other_time_edit_text.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(other_time_edit_text, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    other_time_edit_text.setText(null);
                    other_time_edit_text.setEnabled(false);
                    // Hide the keyboard if it's open
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(other_time_edit_text.getWindowToken(), 0);
                }
            }
        });
    }


    private void init(View view) {

        fireStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Question1Tv = view.findViewById(R.id.Question1Tv);
        Question3 = view.findViewById(R.id.Question3);
        Question4 = view.findViewById(R.id.Question4);
        Question5 = view.findViewById(R.id.Question5);
        Question6 = view.findViewById(R.id.Question6);
        Question12 = view.findViewById(R.id.Question12);
        Question13 = view.findViewById(R.id.Question13);
        Question14 = view.findViewById(R.id.Question14);

        Question7 = view.findViewById(R.id.Question7);
        Question9 = view.findViewById(R.id.Question9);
        Question15 = view.findViewById(R.id.Question15);

        submitBtn = view.findViewById(R.id.submitBtn);

        employment_status_radio_group = view.findViewById(R.id.employment_status_radio_group);
        employment_status_radio_group.clearCheck();

        employmentStatusRadioGroup = view.findViewById(R.id.employment_status_radio_group_q3);
        other_occupation_edittext_q3 = view.findViewById(R.id.other_occupation_edittext_q3);

        occupationRadioGroup = view.findViewById(R.id.occupation_radio_group);
        other_occupation_edittext = view.findViewById(R.id.other_occupation_edittext);

        business_type_radio_group = view.findViewById(R.id.business_type_radio_group);
        other_business_edittext = view.findViewById(R.id.other_business_edittext);

        present_position_radio_group_q6 = view.findViewById(R.id.present_position_radio_group_q6);
        other_position_edittext_q6 = view.findViewById(R.id.other_position_edittext_q6);

        first_job_duration_radio_group = view.findViewById(R.id.first_job_duration_radio_group);
        other_duration_edit_text = view.findViewById(R.id.other_duration_edit_text);

        first_job_finding_radio_group = view.findViewById(R.id.first_job_finding_radio_group);
        other_finding_edit_text = view.findViewById(R.id.other_finding_edit_text);

        time_to_land_first_job_radio_group = view.findViewById(R.id.time_to_land_first_job_radio_group);
        other_time_edit_text = view.findViewById(R.id.other_time_edit_text);

        firstJobRadioGroup = view.findViewById(R.id.first_job_radio_group);

        // Question 9
        firstJobRelatedRadioGroup = view.findViewById(R.id.first_job_related_radio_group);

        // Question 15
        curriculumRelevanceRadioGroup = view.findViewById(R.id.curriculum_relevance_radio_group);

        noConnectionsCheckbox = view.findViewById(R.id.no_connections_checkbox);
        noJobOpportunityCheckbox = view.findViewById(R.id.no_job_opportunity_checkbox);
        familyConcernCheckbox = view.findViewById(R.id.family_concern_checkbox);
        noInterestCheckbox = view.findViewById(R.id.no_interest_checkbox);
        healthRelatedCheckbox = view.findViewById(R.id.health_related_checkbox);
        lackEligibilityCheckbox = view.findViewById(R.id.lack_eligibility_checkbox);
        lackExperienceCheckbox = view.findViewById(R.id.lack_experience_checkbox);
        lowStartingPayCheckbox = view.findViewById(R.id.low_starting_pay_checkbox);
        furtherStudyCheckbox = view.findViewById(R.id.further_study_checkbox);
        seekJobAbroadCheckbox = view.findViewById(R.id.seek_job_abroad_checkbox);
        otherOccupationCheckbox = view.findViewById(R.id.other_occupation_checkbox_q2);

        otherOccupationEditText = view.findViewById(R.id.other_occupation_edittext_q2);

        selectedReasons = new ArrayList<>();

        // Initialize CheckBoxes
        salariesBenefitsCheckbox = view.findViewById(R.id.salaries_benefits_checkbox_q8);
        careerChallengeCheckbox = view.findViewById(R.id.career_challenge_checkbox_q8);
        courseRelatedCheckbox = view.findViewById(R.id.course_related_checkbox);
        goodHumanRelationsCheckbox = view.findViewById(R.id.good_human_relations_checkbox);
        proximityResidenceCheckbox = view.findViewById(R.id.proximity_residence_checkbox_q8);
        peerInfluenceCheckbox = view.findViewById(R.id.peer_influence_checkbox);
        familyInfluenceCheckbox = view.findViewById(R.id.family_influence_checkbox);
        otherReasonsCheckbox = view.findViewById(R.id.other_reasons_checkbox_q8);

// Initialize EditText for other reasons
        otherReasonsEditText = view.findViewById(R.id.other_reasons_edittext_q8);


        selectedReasonsList = new ArrayList<>();

        // Initialize CheckBoxes for question 10
        salariesBenefitsCheckboxQ10 = view.findViewById(R.id.salaries_benefits_checkbox_q10);
        careerChallengeCheckboxQ10 = view.findViewById(R.id.career_challenge_checkbox_q10);
        relatedSpecialSkillsCheckbox = view.findViewById(R.id.related_special_skills_checkbox);
        proximityResidenceCheckboxQ10 = view.findViewById(R.id.proximity_residence_checkbox_q10);
        otherReasonCheckboxQ10 = view.findViewById(R.id.other_reason_checkbox_q10);

// Initialize EditText for other reasons in question 10
        otherReasonEditTextQ10 = view.findViewById(R.id.other_reason_edittext_q10);
        selectedReasonsListQ10 = new ArrayList<>();


        salariesBenefitsCheckboxQ11 = view.findViewById(R.id.salaries_benefits_checkbox_q11);
        careerChallengeCheckboxQ11 = view.findViewById(R.id.career_challenge_checkbox_q11);
        relatedSpecialSkillsCheckboxQ11 = view.findViewById(R.id.related_special_skills_checkbox_q11);
        proximityResidenceCheckboxQ11 = view.findViewById(R.id.proximity_residence_checkbox_q11);
        relationsWithPeopleCheckboxQ11 = view.findViewById(R.id.relations_with_people_checkbox_q11);
        otherReasonCheckboxQ11 = view.findViewById(R.id.other_reason_checkbox_q11);

// EditText variable declaration
        otherReasonEditTextQ11 = view.findViewById(R.id.other_reason_edittext_q11);

// Initialize list to store selected reasons for question 11
        selectedReasonsListQ11 = new ArrayList<>();

        problemSolvingCheckbox = view.findViewById(R.id.problem_solving_checkbox);
        communicationSkillsCheckbox = view.findViewById(R.id.communication_skills_checkbox);
        criticalThinkingCheckbox = view.findViewById(R.id.critical_thinking_checkbox);
        humanRelationsCheckbox = view.findViewById(R.id.human_relations_checkbox);
        informationTechnologyCheckbox = view.findViewById(R.id.information_technology_checkbox);
        entrepreneurialSkillsCheckbox = view.findViewById(R.id.entrepreneurial_skills_checkbox);
        courseRelatedSkillsCheckbox = view.findViewById(R.id.course_related_skills_checkbox);
        otherSkillsCheckbox = view.findViewById(R.id.other_skills_checkbox);

// Initialize EditText for other skills
        otherSkillsEditText = view.findViewById(R.id.other_skills_edittext);

// Initialize list to store selected skills
        selectedSkillsList = new ArrayList<>();

        companyNameEditText = view.findViewById(R.id.company_name_edittext);

// Initialize EditText for company address
        companyAddressEditText = view.findViewById(R.id.company_address_edittext);

        suggestion1RadioGroup = view.findViewById(R.id.suggestion_1_radio_group);
        suggestion2RadioGroup = view.findViewById(R.id.suggestion_2_radio_group);
        suggestion3RadioGroup = view.findViewById(R.id.suggestion_3_radio_group);
        suggestion4RadioGroup = view.findViewById(R.id.suggestion_4_radio_group);
        suggestion5RadioGroup = view.findViewById(R.id.suggestion_5_radio_group);

        // For suggestion 1
        selectedSuggestion1RadioButton = view.findViewById(suggestion1RadioGroup.getCheckedRadioButtonId());

// For suggestion 2
        selectedSuggestion2RadioButton = view.findViewById(suggestion2RadioGroup.getCheckedRadioButtonId());

// For suggestion 3
        selectedSuggestion3RadioButton = view.findViewById(suggestion3RadioGroup.getCheckedRadioButtonId());

// For suggestion 4
        selectedSuggestion4RadioButton = view.findViewById(suggestion4RadioGroup.getCheckedRadioButtonId());

// For suggestion 5
        selectedSuggestion5RadioButton = view.findViewById(suggestion5RadioGroup.getCheckedRadioButtonId());


    }

    private void clickListener() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedEmploymentId = employment_status_radio_group.getCheckedRadioButtonId();
                int selectedOccupationStatus = employmentStatusRadioGroup.getCheckedRadioButtonId();
                int selectedOccupation = occupationRadioGroup.getCheckedRadioButtonId();
                int selectBusiness = business_type_radio_group.getCheckedRadioButtonId();
                int selectPosition = present_position_radio_group_q6.getCheckedRadioButtonId();
                int selectedJobDuration = first_job_duration_radio_group.getCheckedRadioButtonId();
                int selectedFindingHob = first_job_finding_radio_group.getCheckedRadioButtonId();
                int selectedTimeJob = time_to_land_first_job_radio_group.getCheckedRadioButtonId();
                int selectedFirstJob = firstJobRadioGroup.getCheckedRadioButtonId();
                int selectedJobRelated = firstJobRelatedRadioGroup.getCheckedRadioButtonId();
                int selectedCurriculum = curriculumRelevanceRadioGroup.getCheckedRadioButtonId();

                int suggestion1 = suggestion1RadioGroup.getCheckedRadioButtonId();
                int suggestion2 = suggestion2RadioGroup.getCheckedRadioButtonId();
                int suggestion3 = suggestion3RadioGroup.getCheckedRadioButtonId();
                int suggestion4 = suggestion4RadioGroup.getCheckedRadioButtonId();
                int suggestion5 = suggestion5RadioGroup.getCheckedRadioButtonId();

                String companyName = companyNameEditText.getText().toString().trim();
                String companyAddress = companyAddressEditText.getText().toString().trim();


                if (selectedEmploymentId == -1) {
                    // No radio button selected
                    Question1Tv.setError("Please select an option");
                    Question1Tv.requestFocus();
                    Toast.makeText(getContext(), "Answer Q1.", Toast.LENGTH_SHORT).show();
                }
                else if(suggestion1 == -1){
                    Toast.makeText(getContext(), "Answer Q17.1.", Toast.LENGTH_SHORT).show();
                }
                else if(suggestion2 == -1){
                    Toast.makeText(getContext(), "Answer Q17.2.", Toast.LENGTH_SHORT).show();
                }
                else if(suggestion3 == -1){
                    Toast.makeText(getContext(), "Answer Q17.3.", Toast.LENGTH_SHORT).show();
                }
                else if(suggestion4 == -1){
                    Toast.makeText(getContext(), "Answer Q17.4.", Toast.LENGTH_SHORT).show();
                }
                else if(suggestion5 == -1){
                    Toast.makeText(getContext(), "Answer Q17.5.", Toast.LENGTH_SHORT).show();
                }
                else if (selectedFirstJob == -1) {
                    Question7.setError("Please select an option");
                    Question7.requestFocus();
                    Toast.makeText(getContext(), "Answer Q7.", Toast.LENGTH_SHORT).show();
                } else if (selectedJobRelated == -1) {
                    Question9.setError("Please select an option");
                    Question9.requestFocus();
                    Toast.makeText(getContext(), "Answer Q9.", Toast.LENGTH_SHORT).show();
                } else if (selectedCurriculum == -1) {
                    Question15.setError("Please select an option");
                    Question15.requestFocus();
                    Toast.makeText(getContext(), "Answer Q15.", Toast.LENGTH_SHORT).show();
                } else if (selectedOccupationStatus == -1) {
                    Question3.setError("Please select an option");
                    Question3.requestFocus();
                    Toast.makeText(getContext(), "Answer Q3.", Toast.LENGTH_SHORT).show();
                } else if (selectedOccupation == -1) {
                    Question4.setError("Please select an option");
                    Question4.requestFocus();
                    Toast.makeText(getContext(), "Answer Q4.", Toast.LENGTH_SHORT).show();
                } else if (selectBusiness == -1) {
                    Question5.setError("Please select an option");
                    Question5.requestFocus();
                    Toast.makeText(getContext(), "Answer Q5.", Toast.LENGTH_SHORT).show();
                } else if (selectPosition == -1) {
                    Question6.setError("Please select an option");
                    Question6.requestFocus();
                    Toast.makeText(getContext(), "Answer Q6.", Toast.LENGTH_SHORT).show();
                } else if (selectedJobDuration == -1) {
                    Question12.setError("Please select an option");
                    Question12.requestFocus();
                    Toast.makeText(getContext(), "Answer Q12.", Toast.LENGTH_SHORT).show();
                } else if (selectedFindingHob == -1) {
                    Question13.setError("Please select an option");
                    Question13.requestFocus();
                    Toast.makeText(getContext(), "Answer Q13.", Toast.LENGTH_SHORT).show();
                } else if (selectedTimeJob == -1) {
                    Question14.setError("Please select an option");
                    Question14.requestFocus();
                    Toast.makeText(getContext(), "Answer Q14.", Toast.LENGTH_SHORT).show();
                } else if (!noConnectionsCheckbox.isChecked() &&
                        !noJobOpportunityCheckbox.isChecked() &&
                        !familyConcernCheckbox.isChecked() &&
                        !noInterestCheckbox.isChecked() &&
                        !healthRelatedCheckbox.isChecked() &&
                        !lackEligibilityCheckbox.isChecked() &&
                        !lackExperienceCheckbox.isChecked() &&
                        !lowStartingPayCheckbox.isChecked() &&
                        !furtherStudyCheckbox.isChecked() &&
                        !seekJobAbroadCheckbox.isChecked() &&
                        !otherOccupationCheckbox.isChecked()) {

                    Toast.makeText(getContext(), "Please select option Q2", Toast.LENGTH_SHORT).show();
                } else if (!salariesBenefitsCheckbox.isChecked() &&
                        !careerChallengeCheckbox.isChecked() &&
                        !courseRelatedCheckbox.isChecked() &&
                        !goodHumanRelationsCheckbox.isChecked() &&
                        !proximityResidenceCheckbox.isChecked() &&
                        !peerInfluenceCheckbox.isChecked() &&
                        !familyInfluenceCheckbox.isChecked() &&
                        !otherReasonsCheckbox.isChecked()) {

                    Toast.makeText(getContext(), "Please select option Q8", Toast.LENGTH_SHORT).show();
                } else if (!salariesBenefitsCheckboxQ10.isChecked() &&
                        !careerChallengeCheckboxQ10.isChecked() &&
                        !relatedSpecialSkillsCheckbox.isChecked() &&
                        !proximityResidenceCheckboxQ10.isChecked() &&
                        !otherReasonCheckboxQ10.isChecked()) {

                    Toast.makeText(getContext(), "Please select option Q10", Toast.LENGTH_SHORT).show();
                } else if (!salariesBenefitsCheckboxQ11.isChecked() &&
                        !careerChallengeCheckboxQ11.isChecked() &&
                        !relatedSpecialSkillsCheckboxQ11.isChecked() &&
                        !proximityResidenceCheckboxQ11.isChecked() &&
                        !relationsWithPeopleCheckboxQ11.isChecked() &&
                        !otherReasonCheckboxQ11.isChecked()) {

                    Toast.makeText(getContext(), "Please select option Q11", Toast.LENGTH_SHORT).show();
                } else if (!problemSolvingCheckbox.isChecked() &&
                        !communicationSkillsCheckbox.isChecked() &&
                        !criticalThinkingCheckbox.isChecked() &&
                        !humanRelationsCheckbox.isChecked() &&
                        !informationTechnologyCheckbox.isChecked() &&
                        !entrepreneurialSkillsCheckbox.isChecked() &&
                        !courseRelatedSkillsCheckbox.isChecked() &&
                        !otherSkillsCheckbox.isChecked()) {

                    Toast.makeText(getContext(), "Please select option Q16", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(companyName)) {
                    companyNameEditText.setError("Enter Company name.");
                    companyNameEditText.requestFocus();
                } else if (TextUtils.isEmpty(companyAddress)) {
                    companyAddressEditText.setError("Enter Company address.");
                    companyAddressEditText.requestFocus();
                } else {
                    // At least one radio button is selected
                    selected_employment_status = getView().findViewById(selectedEmploymentId);
                    employmentStatus = selected_employment_status.getText().toString();

                    selectedFirstJobRadioButton = getView().findViewById(selectedFirstJob);
                    firstJob = selectedFirstJobRadioButton.getText().toString();

                    selectedFirstJobRelatedRadioButton = getView().findViewById(selectedJobRelated);
                    jobRelated = selectedFirstJobRelatedRadioButton.getText().toString();

                    selectedCurriculumRelevanceRadioButton = getView().findViewById(selectedCurriculum);
                    curriculum = selectedCurriculumRelevanceRadioButton.getText().toString();

                    selectedSuggestion1RadioButton = getView().findViewById(suggestion1);
                    suggestion171 = selectedSuggestion1RadioButton.getText().toString();


                    selectedSuggestion2RadioButton = getView().findViewById(suggestion2);
                    suggestion172 = selectedSuggestion2RadioButton.getText().toString();


                    selectedSuggestion3RadioButton = getView().findViewById(suggestion3);
                    suggestion173 = selectedSuggestion3RadioButton.getText().toString();


                    selectedSuggestion4RadioButton = getView().findViewById(suggestion4);
                    suggestion174 = selectedSuggestion4RadioButton.getText().toString();


                    selectedSuggestion5RadioButton = getView().findViewById(suggestion5);
                    suggestion175 = selectedSuggestion5RadioButton.getText().toString();


                    if (selectedOccupationStatus == R.id.other_occupation_radio_button_q3) {
                        occupationStatus = "Other: " + other_occupation_edittext_q3.getText().toString();
                        if (TextUtils.isEmpty(occupationStatus)) {
                            other_occupation_edittext_q3.setError("Please Specify your answer.");
                            other_occupation_edittext_q3.requestFocus();
                            Toast.makeText(getContext(), "Please Specify your answer in Q3.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        employmentStatusRadioButton = getView().findViewById(selectedOccupationStatus);
                        occupationStatus = employmentStatusRadioButton.getText().toString();
                    }

                    if (selectedOccupation == R.id.other_occupation_radio_button) {
                        occupation = "Other: " + other_occupation_edittext.getText().toString();
                        if (TextUtils.isEmpty(occupation)) {
                            other_occupation_edittext.setError("Please Specify your answer.");
                            other_occupation_edittext.requestFocus();
                            Toast.makeText(getContext(), "Please Specify your answer in Q4.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        occupationRadioButton = getView().findViewById(selectedOccupation);
                        occupation = occupationRadioButton.getText().toString();
                    }

                    if (selectBusiness == R.id.other_business_radio_button) {
                        business = "Other: " + other_business_edittext.getText().toString();
                        if (TextUtils.isEmpty(business)) {
                            other_business_edittext.setError("Please Specify your answer.");
                            other_occupation_edittext.requestFocus();
                            Toast.makeText(getContext(), "Please Specify your answer in Q5.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        businessTypeRadioButton = getView().findViewById(selectBusiness);
                        business = businessTypeRadioButton.getText().toString();
                    }

                    if (selectPosition == R.id.other_position_radio_button_q6) {
                        position = "Other: " + other_position_edittext_q6.getText().toString();
                        if (TextUtils.isEmpty(position)) {
                            other_position_edittext_q6.setError("Please Specify your answer.");
                            other_occupation_edittext.requestFocus();
                            Toast.makeText(getContext(), "Please Specify your answer in Q6.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        presentPositionRadioButton = getView().findViewById(selectPosition);
                        position = presentPositionRadioButton.getText().toString();
                    }

                    if (selectedJobDuration == R.id.other_duration_radio_button) {
                        jobDuration = "Other: " + other_duration_edit_text.getText().toString();
                        if (TextUtils.isEmpty(jobDuration)) {
                            other_duration_edit_text.setError("Please Specify your answer.");
                            other_occupation_edittext.requestFocus();
                            Toast.makeText(getContext(), "Please Specify your answer in Q12.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        firstJobDurationRadioButton = getView().findViewById(selectedJobDuration);
                        jobDuration = firstJobDurationRadioButton.getText().toString();
                    }

                    if (selectedFindingHob == R.id.other_finding_radio_button) {
                        jobFinding = "Other: " + other_finding_edit_text.getText().toString();
                        if (TextUtils.isEmpty(jobFinding)) {
                            other_finding_edit_text.setError("Please Specify your answer.");
                            other_occupation_edittext.requestFocus();
                            Toast.makeText(getContext(), "Please Specify your answer in Q13.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        firstJobFindingRadioButton = getView().findViewById(selectedFindingHob);
                        jobFinding = firstJobFindingRadioButton.getText().toString();
                    }

                    if (selectedTimeJob == R.id.other_time_radio_button) {
                        jobTime = "Other: " + other_time_edit_text.getText().toString();
                        if (TextUtils.isEmpty(jobTime)) {
                            other_time_edit_text.setError("Please Specify your answer.");
                            other_occupation_edittext.requestFocus();
                            Toast.makeText(getContext(), "Please Specify your answer in Q14.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        timeToLandFirstJobRadioButton = getView().findViewById(selectedTimeJob);
                        jobTime = timeToLandFirstJobRadioButton.getText().toString();
                    }

                    question2();

                    question8();

                    question10();

                    question11();

                    question16();

//                    Toast.makeText(getContext(), suggestion171 + suggestion172 + suggestion173 + suggestion174 + suggestion175, Toast.LENGTH_SHORT).show();

                    saveAnswerInDatabase(employmentStatus, selectedReasons, occupationStatus, occupation, companyName, companyAddress, business, position, firstJob, selectedReasonsList,
                            jobRelated, selectedReasonsListQ10, selectedReasonsListQ11, jobDuration, jobFinding, jobTime, curriculum, selectedSkillsList,
                            suggestion171, suggestion172, suggestion173, suggestion174, suggestion175);

//                    Toast.makeText(getContext(), employmentStatus+occupationStatus+occupation+ "\n" + business+position+jobDuration+"\n"+ jobFinding, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), firstJob + jobRelated + curriculum, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), "Last" + companyName + companyAddress, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void question2() {

        selectedReasons.clear();

        if (noConnectionsCheckbox.isChecked()) {
            selectedReasons.add("No connections");
        }
        if (noJobOpportunityCheckbox.isChecked()) {
            selectedReasons.add("No job opportunity");
        }
        if (familyConcernCheckbox.isChecked()) {
            selectedReasons.add("Family concern");
        }
        if (noInterestCheckbox.isChecked()) {
            selectedReasons.add("No interest in getting a job");
        }
        if (healthRelatedCheckbox.isChecked()) {
            selectedReasons.add("Health-related reasons");
        }
        if (lackEligibilityCheckbox.isChecked()) {
            selectedReasons.add("Lack of professional eligibility requirements");
        }
        if (lackExperienceCheckbox.isChecked()) {
            selectedReasons.add("Lack of work experience");
        }
        if (lowStartingPayCheckbox.isChecked()) {
            selectedReasons.add("Starting pay is too low");
        }
        if (furtherStudyCheckbox.isChecked()) {
            selectedReasons.add("Engaged in further study");
        }
        if (seekJobAbroadCheckbox.isChecked()) {
            selectedReasons.add("Have plans to seek a job out of the country");
        }
        if (otherOccupationCheckbox.isChecked()) {
            // Append the text from the other occupation EditText if the checkbox is checked
            String otherOccupation = otherOccupationEditText.getText().toString().trim();
            if (!otherOccupation.isEmpty()) {
                selectedReasons.add("Other: " + otherOccupation);
            }
        }

//        Toast.makeText(getContext(), selectedReasons.toString(), Toast.LENGTH_SHORT).show();
    }

    private void question8() {
        selectedReasonsList.clear();

        // Check which CheckBoxes are checked and add their corresponding reasons to the list
        if (salariesBenefitsCheckbox.isChecked()) {
            selectedReasonsList.add("Salaries and benefits");
        }
        if (careerChallengeCheckbox.isChecked()) {
            selectedReasonsList.add("Career challenge");
        }
        if (courseRelatedCheckbox.isChecked()) {
            selectedReasonsList.add("Related to course or study");
        }
        if (goodHumanRelationsCheckbox.isChecked()) {
            selectedReasonsList.add("Good human relations with employer/fellow employees");
        }
        if (proximityResidenceCheckbox.isChecked()) {
            selectedReasonsList.add("Proximity to residence");
        }
        if (peerInfluenceCheckbox.isChecked()) {
            selectedReasonsList.add("Peer influence");
        }
        if (familyInfluenceCheckbox.isChecked()) {
            selectedReasonsList.add("Family influence");
        }
        if (otherReasonsCheckbox.isChecked()) {
            // Add the text from the other reasons EditText if the checkbox is checked
            String otherReasons = otherReasonsEditText.getText().toString().trim();
            if (!otherReasons.isEmpty()) {
                selectedReasonsList.add("Other: " + otherReasons);
            }
        }


//        Toast.makeText(getContext(), selectedReasonsList.toString(), Toast.LENGTH_SHORT).show();
    }

    private void question10() {

        selectedReasonsListQ10.clear();
        if (salariesBenefitsCheckboxQ10.isChecked()) {
            selectedReasonsListQ10.add("Salaries & benefits");
        }
        if (careerChallengeCheckboxQ10.isChecked()) {
            selectedReasonsListQ10.add("Career challenge");
        }
        if (relatedSpecialSkillsCheckbox.isChecked()) {
            selectedReasonsListQ10.add("Related to special skills");
        }
        if (proximityResidenceCheckboxQ10.isChecked()) {
            selectedReasonsListQ10.add("Proximity to residence");
        }
        if (otherReasonCheckboxQ10.isChecked()) {
            // Add the text from the other reasons EditText if the checkbox is checked
            String otherReasonsQ10 = otherReasonEditTextQ10.getText().toString().trim();
            if (!otherReasonsQ10.isEmpty()) {
                selectedReasonsListQ10.add("Other: " + otherReasonsQ10);
            }
        }


//        Toast.makeText(getContext(), selectedReasonsListQ10.toString(), Toast.LENGTH_SHORT).show();
    }

    private void question11() {

        selectedReasonsListQ11.clear();

        if (salariesBenefitsCheckboxQ11.isChecked()) {
            selectedReasonsListQ11.add("Salaries & benefits");
        }
        if (careerChallengeCheckboxQ11.isChecked()) {
            selectedReasonsListQ11.add("Career challenge");
        }
        if (relatedSpecialSkillsCheckboxQ11.isChecked()) {
            selectedReasonsListQ11.add("Related to special skills");
        }
        if (proximityResidenceCheckboxQ11.isChecked()) {
            selectedReasonsListQ11.add("Proximity to residence");
        }
        if (relationsWithPeopleCheckboxQ11.isChecked()) {
            selectedReasonsListQ11.add("Relations with people in the organization");
        }
        if (otherReasonCheckboxQ11.isChecked()) {
            // Add the text from the other reasons EditText if the checkbox is checked
            String otherReasonsQ11 = otherReasonEditTextQ11.getText().toString().trim();
            if (!otherReasonsQ11.isEmpty()) {
                selectedReasonsListQ11.add("Other: " + otherReasonsQ11);
            }
        }


//        Toast.makeText(getContext(), selectedReasonsListQ11.toString(), Toast.LENGTH_SHORT).show();
    }


    private void question16() {

        selectedSkillsList.clear();

        if (problemSolvingCheckbox.isChecked()) {
            selectedSkillsList.add("Problem-solving skills");
        }
        if (communicationSkillsCheckbox.isChecked()) {
            selectedSkillsList.add("Communication skills");
        }
        if (criticalThinkingCheckbox.isChecked()) {
            selectedSkillsList.add("Critical Thinking skills");
        }
        if (humanRelationsCheckbox.isChecked()) {
            selectedSkillsList.add("Human Relations skills");
        }
        if (informationTechnologyCheckbox.isChecked()) {
            selectedSkillsList.add("Information Technology skills");
        }
        if (entrepreneurialSkillsCheckbox.isChecked()) {
            selectedSkillsList.add("Entrepreneurial skills");
        }
        if (courseRelatedSkillsCheckbox.isChecked()) {
            selectedSkillsList.add("Skills related to my course such as");
        }
        if (otherSkillsCheckbox.isChecked()) {
            // Add the text from the other skills EditText if the checkbox is checked
            String otherSkills = otherSkillsEditText.getText().toString().trim();
            if (!otherSkills.isEmpty()) {
                selectedSkillsList.add("Other: " + otherSkills);
            }
        }

//        Toast.makeText(getContext(), selectedSkillsList.toString(), Toast.LENGTH_SHORT).show();
    }

    private void saveAnswerInDatabase(String employmentStatus, List<String> selectedReasons, String occupationStatus,
                                      String occupation, String companyName, String companyAddress, String business,
                                      String position, String firstJob, List<String> selectedReasonsList,
                                      String jobRelated, List<String> selectedReasonsListQ10,
                                      List<String> selectedReasonsListQ11, String jobDuration,
                                      String jobFinding, String jobTime, String curriculum, List<String> selectedSkillsList,
                                      String suggestion171, String suggestion172, String suggestion173, String suggestion174, String suggestion175) {

        DocumentReference df = fireStore.collection("SurveyAnswer").document(user.getUid());
        Map<String, Object> answer = new HashMap<>();
        answer.put("userId", user.getUid());
        answer.put("userName", user.getDisplayName());

        answer.put("question1", employmentStatus);
        answer.put("question2", selectedReasons);
        answer.put("question3", occupationStatus);
        answer.put("question4", occupation);

        answer.put("companyName", companyName);
        answer.put("companyAddress", companyAddress);

        answer.put("question5", business);
        answer.put("question6", position);
        answer.put("question7", firstJob);
        answer.put("question8", selectedReasonsList);
        answer.put("question9", jobRelated);
        answer.put("question10", selectedReasonsListQ10);
        answer.put("question11", selectedReasonsListQ11);
        answer.put("question12", jobDuration);
        answer.put("question13", jobFinding);
        answer.put("question14", jobTime);
        answer.put("question15", curriculum);
        answer.put("question16", selectedSkillsList);


        answer.put("question171", suggestion171);
        answer.put("question172", suggestion172);
        answer.put("question173", suggestion173);
        answer.put("question174", suggestion174);
        answer.put("question175", suggestion175);

        df.set(answer, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Answer is save to database", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Saving answer fail!", Toast.LENGTH_SHORT).show();
                    }
                });





    }
}