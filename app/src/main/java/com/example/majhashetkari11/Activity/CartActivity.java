package com.example.majhashetkari11.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.majhashetkari11.Adapter.CartAdapter;
import com.example.majhashetkari11.Model.CartModel;
import com.example.majhashetkari11.Model.ProductsModel;
import com.example.majhashetkari11.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    CartAdapter cartAdapter;
    ArrayList<CartModel> list;
    RecyclerView recyclerView;
    public TextView TotalAmount;
    Button proceedUpi;
    int GrandTotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        proceedUpi = findViewById(R.id.proceedUpi);
        TotalAmount = findViewById(R.id.total_amount);
        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        cartAdapter = new CartAdapter(this, list);
        recyclerView.setAdapter(cartAdapter);

        proceedUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, upiOrCashActivity.class);
                startActivity(intent);
            }
        });

        /* firestore.collection("AddToCart").document("random")
                 .collection("User")./* document(auth.getCurrentUser().getUid())
                .collection("User"). */ /* get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        CartModel cartModel = doc.toObject(CartModel.class);
                        list.add(cartModel);
                    }
                    cartAdapter.notifyDataSetChanged();
                }
            }
        }); */

        firestore.collection("AddToCart").document("random")
                .collection("User")./* document(auth.getCurrentUser().getUid())
                .collection("User"). */ get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:list1) {
                    CartModel obj = d.toObject(CartModel.class);
                    list.add(obj);
                }
                cartAdapter.notifyDataSetChanged();
            }
        });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("totalAmount", 0);
            TotalAmount.setText("Rs " + totalBill);

        }
    };
}