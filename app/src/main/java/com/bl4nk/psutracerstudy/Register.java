package com.bl4nk.psutracerstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    private EditText firstName;
    private EditText middleName;
    private EditText lastName;
    private EditText userAddress;
    private EditText userAge;
    private EditText userBirthday;
    private EditText userMobileNumber;
    private Spinner userSex;
    private Spinner userBatchYear;
    private Spinner userCivilStatus;
    private Spinner userHonor;
    private Spinner userExamPassed;
    private DatePickerDialog picker;
    private Button nextBtn;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        Intent intent = getIntent();
        if (intent != null) {
            firstName.setText(intent.getStringExtra("firstName"));
            middleName.setText(intent.getStringExtra("middleName"));
            lastName.setText(intent.getStringExtra("lastName"));
            userAddress.setText(intent.getStringExtra("userAddress"));
            userAge.setText(intent.getStringExtra("userAge"));
            userBirthday.setText(intent.getStringExtra("userBirthday"));
            userMobileNumber.setText(intent.getStringExtra("userMobileNumber"));

            // Select the appropriate spinner items based on received data
            selectSpinnerItemByValue(userSex, intent.getStringExtra("userSex"));
            selectSpinnerItemByValue(userCivilStatus, intent.getStringExtra("userCivilStatus"));
            selectSpinnerItemByValue(userBatchYear, intent.getStringExtra("userBatchYear"));
            selectSpinnerItemByValue(userHonor, intent.getStringExtra("userHonor"));
            selectSpinnerItemByValue(userExamPassed, intent.getStringExtra("userExamPassed"));
        }

        clickListener();

        setupSpinners();
    }

    private void init() {

        firstName = findViewById(R.id.firstName);
        middleName = findViewById(R.id.middleName);
        lastName = findViewById(R.id.lastName);
        userAddress = findViewById(R.id.userAddress);
        userAge = findViewById(R.id.userAge);
        userBirthday = findViewById(R.id.userBirthday);
        userMobileNumber = findViewById(R.id.userMobileNumber);
        userSex = findViewById(R.id.userSex);
        userBatchYear = findViewById(R.id.batchYear);
        userCivilStatus = findViewById(R.id.userCivilStatus);
        userHonor = findViewById(R.id.userHonor);
        userExamPassed = findViewById(R.id.userExamPassed);
        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.backBtn);

    }

    private void clickListener() {

        userBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(
                        new ContextThemeWrapper(Register.this, R.style.MyDatePickerDialogTheme),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Adjust the order to month, day, year
                                userBirthday.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        },
                        year,
                        month,
                        day
                );
                picker.show();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserInput();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupSpinners() {
        String[] sexOptions = {"Select...", "Male", "Female", "Other"};
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexOptions);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSex.setAdapter(sexAdapter);
        userSex.setSelection(0);

        String[] civilStatusOptions = {"Select...", "Single", "Married", "Divorced", "Widowed"};
        ArrayAdapter<String> civilStatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, civilStatusOptions);
        civilStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userCivilStatus.setAdapter(civilStatusAdapter);
        userCivilStatus.setSelection(0);

        String[] batchOptions = {"Select...", "2019", "2022", "2023"};
        ArrayAdapter<String> batchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, batchOptions);
        batchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userBatchYear.setAdapter(batchAdapter);
        userBatchYear.setSelection(0);

        String[] honorOptions = {"Select...", "Academic Distinction", "Summa Cum Laude", "Magna Cum Laude", "Cum Laude", "None", "Other"};
        ArrayAdapter<String> honorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, honorOptions);
        honorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userHonor.setAdapter(honorAdapter);
        userHonor.setSelection(0);

        String[] professionalExamOptions = {"Select...", "Civil Service Sub-Professional", "Civil Service Professional", "Civil Service CESO", "LET (Licensure Examination for Teachers)", "None", "Other"};
        ArrayAdapter<String> professionalExamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, professionalExamOptions);
        professionalExamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userExamPassed.setAdapter(professionalExamAdapter);
        userExamPassed.setSelection(0);
    }

    private void checkUserInput() {
        String firstNameText = firstName.getText().toString().trim();
        String middleNameText = middleName.getText().toString().trim();
        String lastNameText = lastName.getText().toString().trim();
        String userAddressText = userAddress.getText().toString().trim();
        String userAgeText = userAge.getText().toString().trim();
        String userBirthdayText = userBirthday.getText().toString().trim();
        String userMobileNumberText = userMobileNumber.getText().toString().trim();
        String userBatchYearText = userBatchYear.getSelectedItem().toString();
        String userSexText = userSex.getSelectedItem().toString();
        String userCivilStatusText = userCivilStatus.getSelectedItem().toString();
        String userHonorText = userHonor.getSelectedItem().toString();
        String userExamPassedText = userExamPassed.getSelectedItem().toString();

        if (TextUtils.isEmpty(firstNameText)) {
            firstName.setError("First name is required");
            firstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(middleNameText)) {
            middleName.setError("Middle name is required");
            middleName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastNameText)) {
            lastName.setError("Last name is required");
            lastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userAddressText)) {
            userAddress.setError("Address is required");
            userAddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userAgeText)) {
            userAge.setError("Age is required");
            userAge.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userBirthdayText)) {
            userBirthday.setError("Birthday is required");
            userBirthday.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userMobileNumberText)) {
            userMobileNumber.setError("Mobile number is required");
            userMobileNumber.requestFocus();
            return;
        }

        if (userSex.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userBatchYear.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select your batch year", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userCivilStatus.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select your civil status", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userHonor.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select your honor", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userExamPassed.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select your professional examination passed", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Register.this, RegisterCredentials.class);
        intent.putExtra("firstName", firstNameText);
        intent.putExtra("middleName", middleNameText);
        intent.putExtra("lastName", lastNameText);
        intent.putExtra("userAddress", userAddressText);
        intent.putExtra("userAge", userAgeText);
        intent.putExtra("userBirthday", userBirthdayText);
        intent.putExtra("userMobileNumber", userMobileNumberText);
        intent.putExtra("userBatchYear", userBatchYearText);
        intent.putExtra("userSex", userSexText);
        intent.putExtra("userCivilStatus", userCivilStatusText);
        intent.putExtra("userHonor", userHonorText);
        intent.putExtra("userExamPassed", userExamPassedText);
        startActivity(intent);
        finish();
    }

    private void selectSpinnerItemByValue(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            if (position >= 0) {
                spinner.setSelection(position);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}