package org.lds.community.CallingWorkFlow.domain;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ObjectListAdapter extends ArrayAdapter<Listable> {
    Context context;
    int textViewId;
    Object[] items;
    List<Listable> displayedItems;
    LayoutInflater inflater;

    public ObjectListAdapter(Context context,int textViewResourceId,List<? extends Listable> list){
        super(context,textViewResourceId);
        super.addAll(list);
        this.context = context;
        this.textViewId = textViewResourceId;
        this.items = list.toArray();
        displayedItems = new ArrayList<Listable>(list);
        this.inflater = ((Activity)context).getLayoutInflater();
    }

    public ObjectListAdapter(Context context,int textViewResourceId,Listable[] list){
        super(context,textViewResourceId,list);
        this.context = context;
        this.textViewId = textViewResourceId;
        this.items = list;
        this.displayedItems = new ArrayList<Listable>();
        this.inflater = ((Activity)context).getLayoutInflater();
    }

    @Override
    public int getCount(){
        return displayedItems.size();
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        if(convertView == null){
            convertView = inflater.inflate(textViewId, parent, false);
        }
        ((TextView)convertView).setText(displayedItems.get(position).getDisplayString());
        return convertView;
    }

    @Override
    public View getDropDownView(int position,View convertView,ViewGroup parent){
        if(convertView == null){
            convertView = inflater.inflate(textViewId, parent, false);
        }
        ((TextView)convertView).setText(displayedItems.get(position).getDisplayString());
        return convertView;
    }

    @Override
    public Listable getItem(int position){
        return displayedItems.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter nameFilter = new Filter(){
            @Override
            public String convertResultToString(Object resultValue) {
                return ((Listable)resultValue).getDisplayString();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if(constraint != null)
                {
                    Object[] tmpAllItems = items;
                    displayedItems.clear();
                    for(int i = 0; i < tmpAllItems.length; i++)
                    {
                        if(((Listable)tmpAllItems[i]).getDisplayString().toLowerCase().startsWith(constraint.toString().toLowerCase()))
                        {
                            displayedItems.add((Listable)tmpAllItems[i]);
                        }
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = displayedItems;
                    filterResults.count = displayedItems.size();
                    return filterResults;
                }
                else
                {
                return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                notifyDataSetChanged();
            }
        };
        return nameFilter;
    }
}
