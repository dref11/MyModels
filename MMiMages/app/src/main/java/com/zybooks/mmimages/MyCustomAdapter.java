package com.zybooks.mmimages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DataObject> modelList;
    ViewModelsHelper mViewModelsHelper;

    //Constructor
    public MyCustomAdapter(Context context, ArrayList<DataObject> modelList){
        this.context = context;
        this.modelList = modelList;
        this.mViewModelsHelper = new ViewModelsHelper(context);
    }

    //Definition of adapter methods
    @Override public int getCount(){return modelList.size();}

    @Override
    public DataObject getItem(int position){return modelList.get(position);}

    @Override
    public long getItemId(int position){return position;}

    public void removeItem(int position){
        modelList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        //Inflate the layout for each list row
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.format_models, parent, false);
        }

        //Self reference for deletion
        final MyCustomAdapter selfAdapter = this;

        //Current data values to be displayed
        final DataObject currentValues = getItem(position);

        //Get each column to be displyaed
        //ImageView imageViewPic = (ImageView) convertView.findViewById(R.id.rowImageView);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.rowTitleView);

        //Set each data element to be displayed
        //imageViewPic.setImageBitmap(currentValues.getImage());
        textViewTitle.setText(Long.toString(currentValues.getId()));

        //Return the view
        return convertView;
    }
}
