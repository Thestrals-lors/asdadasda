package com.bl4nk.psutracerstudy.user_background;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.admin_adapter.NameAdapter;
import com.bl4nk.psutracerstudy.admin_model.EmailModel;
import com.bl4nk.psutracerstudy.admin_model.NameModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.List;

public class Name extends Fragment {
    FirebaseFirestore fStore;
    CollectionReference reference;
    RecyclerView recyclerView;

    List<NameModel> list;
    NameAdapter nameAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Name() {
        // Required empty public constructor
    }

    public static Name newInstance(String param1, String param2) {
        Name fragment = new Name();
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
        return inflater.inflate(R.layout.fragment_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();
        reference = fStore.collection("Users");

        init(view);

        loadAllName();
    }

    private void loadAllName() {
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    return;
                }

                if (value == null) {
                    return;
                }
                list.clear();

                for(QueryDocumentSnapshot snapshot: value){


                    if(snapshot.getString("userType").equals("Admin")){

                    }else{
                        list.add(new NameModel(
                                snapshot.getString("firstName"),
                                snapshot.getString("middleName"),
                                snapshot.getString("lastName")
                        ));

                    }
                }

                nameAdapter.notifyDataSetChanged();

            }
        });
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        nameAdapter = new NameAdapter(getContext(), list);
        recyclerView.setAdapter(nameAdapter);
    }
}