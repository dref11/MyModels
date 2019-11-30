package com.zybooks.mmimages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyModelsActivity extends AppCompatActivity {

    ListView modelListView;
    ViewModelsHelper mViewModelsHelper;

    ArrayList<DataObject> modelValuesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_models);

        //Get reference to the views
        modelListView = findViewById(R.id.listMyModels);
        mViewModelsHelper = new ViewModelsHelper(this);

        //Create an array list to populate
        modelValuesList = new ArrayList<DataObject>();
        getModelValues(modelValuesList);

        //Create the adapter
        MyCustomAdapter adapter = new MyCustomAdapter(this,modelValuesList);

        //Attach the adapter to the listview
        ListView modelListView = findViewById(R.id.listMyModels);
        modelListView.setAdapter(adapter);
    }

    //Populate the list via the adapter
    public void getModelValues(ArrayList<DataObject> modelValuesList){
        mViewModelsHelper.populateModelValues(modelValuesList);
    }
}
