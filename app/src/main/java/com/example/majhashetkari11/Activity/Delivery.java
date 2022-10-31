package com.example.majhashetkari11.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.majhashetkari11.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Delivery extends AppCompatActivity {

    FirebaseFirestore db;

    EditText edttt1, edttt2, edttt3;
    Button bttn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        db = FirebaseFirestore.getInstance();

        edttt1 = findViewById(R.id.edttt1);
        edttt2 = findViewById(R.id.edttt2);
        edttt3 = findViewById(R.id.edttt3);
        bttn1 = findViewById(R.id.bttn1);

        edttt1.addTextChangedListener(textWatcher);
        edttt2.addTextChangedListener(textWatcher);
        edttt3.addTextChangedListener(textWatcher);
        bttn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = edttt1.getText().toString();
                String Mobile = edttt2.getText().toString();
                String Address = edttt3.getText().toString();
                String Confirmation = "Order Placed (PaD)";
                if (Name.isEmpty() || Mobile.isEmpty() || Address.isEmpty()) {
                    Toast.makeText(Delivery.this, "Name,Mobile number and Address is necessary", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> h = new HashMap<>();
                    h.put("Name", Name);
                    h.put("Number", Mobile);
                    h.put("Address", Address);
                    h.put("Result", Confirmation);
                    db.collection("AddToUser")
                            //.document("User")
                            .add(h)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference DocumentReference) {
                                    Toast.makeText(Delivery.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Delivery.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Toast.makeText(Delivery.this, "Order Placed Sucessfully", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String name = edttt1.getText().toString();
            String number = edttt2.getText().toString();
            String address = edttt3.getText().toString();
            bttn1.setEnabled(!name.isEmpty() && !number.isEmpty() && !address.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}