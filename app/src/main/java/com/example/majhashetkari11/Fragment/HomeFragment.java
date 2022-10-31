package com.example.majhashetkari11.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majhashetkari11.Activity.ShowAllActivity;
import com.example.majhashetkari11.Adapter.CategoryAdapter;
import com.example.majhashetkari11.Adapter.ProductsAdapter;
import com.example.majhashetkari11.Model.CategoryModel;
import com.example.majhashetkari11.Model.ProductsModel;
import com.example.majhashetkari11.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView productsShowAll;

    RecyclerView catRecyclerView, productRecyclerView;

    //Category Recycler View
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> categoryModelList;

    //Product Recycler View
    ProductsAdapter productsAdapter;
    ArrayList<ProductsModel> productsModelList;

    //FireStore
    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        //To ShowAllActivity class
        productsShowAll = root.findViewById(R.id.products_see_all);
        productsShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        //Category
        catRecyclerView = root.findViewById(R.id.rec_category);
        catRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();

                            }
                        } else {

                            Toast.makeText(getActivity(), "Exception" + task.getException(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

        //Products
        productRecyclerView = root.findViewById(R.id.product_rec);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        productsModelList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(getContext(), productsModelList);
        productRecyclerView.setAdapter(productsAdapter);

        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ProductsModel productsModel = document.toObject(ProductsModel.class);
                                productsModelList.add(productsModel);
                                productsAdapter.notifyDataSetChanged();

                            }
                        } else {

                            Toast.makeText(getActivity(), "Exception" + task.getException(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

        return root;
    }
}