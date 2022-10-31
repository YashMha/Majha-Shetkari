package com.example.majhashetkari11.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.majhashetkari11.Model.ProductsModel;
import com.example.majhashetkari11.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class upiOrCashActivity extends AppCompatActivity {

    FirebaseFirestore db;
    EditText edttt1, edttt2, edttt3;
    Button cashPay, upiPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_or_cash);

        db = FirebaseFirestore.getInstance();

        edttt1 = findViewById(R.id.p_d_name);
        edttt2 = findViewById(R.id.p_d_number);
        edttt3 = findViewById(R.id.p_d_address);
        cashPay = findViewById(R.id.cashPay);
        upiPay = findViewById(R.id.upiPay);

        edttt1.addTextChangedListener(textWatcher);
        edttt2.addTextChangedListener(textWatcher);
        edttt3.addTextChangedListener(textWatcher);
        cashPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = edttt1.getText().toString();
                String Mobile = edttt2.getText().toString();
                String Address = edttt3.getText().toString();
                String Confirmation = "Order Placed (PaD)";
                if (Name.isEmpty() || Mobile.isEmpty() || Address.isEmpty()) {
                    Toast.makeText(upiOrCashActivity.this, "Name, Mobile number and Address is necessary", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> h = new HashMap<>();
                    h.put("Name", Name);
                    h.put("Number", Mobile);
                    h.put("Address", Address);
                    h.put("Result", Confirmation);
                    db.collection("Cash on Delivery")
                            //.document("User")
                            .add(h)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference DocumentReference) {
                                    Toast.makeText(upiOrCashActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(upiOrCashActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Toast.makeText(Delivery.this, "Order Placed Sucessfully", Toast.LENGTH_SHORT).show();
                }
            }

        });

        upiPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = edttt1.getText().toString();
                String Mobile = edttt2.getText().toString();
                String Address = edttt3.getText().toString();
                if (Name.isEmpty() || Mobile.isEmpty() || Address.isEmpty()) {
                    Toast.makeText(upiOrCashActivity.this, "Name, Mobile number and Address is necessary", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> h = new HashMap<>();
                    h.put("Name", Name);
                    h.put("Number", Mobile);
                    h.put("Address", Address);
                    db.collection("Upi Payment")
                            //.document("User")
                            .add(h)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference DocumentReference) {
                                    Toast.makeText(upiOrCashActivity.this, "Please enter further details", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(upiOrCashActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    /*int amount = 0;
                    if (obj instanceof ProductsModel) {
                        ProductsModel productsModel = (ProductsModel) obj;
                        amount = productsModel.getPrice();
                    }*/
                    Intent intent = new Intent(upiOrCashActivity.this,MainClass.class);
                    //intent.putExtra("amount", amount);
                    startActivity(intent);

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
            upiPay.setEnabled(!name.isEmpty() && !number.isEmpty() && !address.isEmpty());
            cashPay.setEnabled(!name.isEmpty() && !number.isEmpty() && !address.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}