package com.zybooks.mmimages;

import android.content.Context;

import java.util.ArrayList;

public class ViewModelsHelper {

    Context ctx;
    ModelsDB myModelsDB;

    //Consdtructor
    public ViewModelsHelper(Context context){this.ctx = context;}

    //Populate the list view
    public void populateModelValues(ArrayList<DataObject> modelValuesList){
        myModelsDB = myModelsDB.getInstance(ctx);
        myModelsDB.getAll(modelValuesList);
    }
}

