package com.bl4nk.psutracerstudy.user_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bl4nk.psutracerstudy.Login;
import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.ShowAnswer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    private CircleImageView profileImage;
    private String user_name, user_age, user_sex, user_birthday, user_civilStatus, user_mobilePhone, user_batchYear, user_honors, user_examPassed;
    private TextView userName;
    private TextView userAge;
    private TextView userSex;
    private TextView userBirthday;
    private TextView userCivilStatus;
    private TextView userMobileNumber;
    private TextView userBatchYear;
    private TextView userHonors;
    private TextView userExamPassed;
    private Button surveyAnswerBtn;
    private Button logoutBtn;
    private FirebaseFirestore fStore;
    private FirebaseUser user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        
        clickListener();
        
        getUserInfo();
    }

    private void init(View view){

        user = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        logoutBtn = view.findViewById(R.id.logoutBtn);

        profileImage = view.findViewById(R.id.profileImage);
        userName = view.findViewById(R.id.userName);
        userAge = view.findViewById(R.id.userAge);
        userSex = view.findViewById(R.id.userSex);
        userBirthday = view.findViewById(R.id.userBirthday);
        userCivilStatus = view.findViewById(R.id.userCivilStatus);
        userMobileNumber = view.findViewById(R.id.userMobileNumber);
        userBatchYear = view.findViewById(R.id.userBatchYear);
        userHonors = view.findViewById(R.id.userHonors);
        userExamPassed = view.findViewById(R.id.userExamPassed);
        surveyAnswerBtn = view.findViewById(R.id.surveyAnswerBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
    }

    private void clickListener() {

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        surveyAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowAnswer.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    private void getUserInfo() {
        DocumentReference df = fStore.collection("Users").document(user.getUid());

        df.get().addOnSuccessListener(doc -> {
            user_name = doc.getString("firstName") + " " + doc.getString("middleName") + " " + doc.getString("lastName");
            user_age = doc.getString("userAge");
            user_sex = doc.getString("userSex");
            user_birthday= doc.getString("userBirthday");
            user_civilStatus = doc.getString("userCivilStatus");
            user_mobilePhone = doc.getString("userMobileNumber");
            user_batchYear = doc.getString("userBatchYear");
            user_honors = doc.getString("userHonor");
            user_examPassed = doc.getString("userExamPassed");



            userName.setText(user_name);
            userAge.setText(user_age);
            userSex.setText(user_sex);
            userBirthday.setText(user_birthday);
            userCivilStatus.setText(user_civilStatus);
            userMobileNumber.setText(user_mobilePhone);
            userBatchYear.setText(user_batchYear);
            userHonors.setText(user_honors);
            userExamPassed.setText(user_examPassed);
        });
    }
}