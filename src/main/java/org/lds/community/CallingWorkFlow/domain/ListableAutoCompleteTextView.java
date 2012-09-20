package org.lds.community.CallingWorkFlow.domain;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AutoCompleteTextView;

public class ListableAutoCompleteTextView extends AutoCompleteTextView{
    private Listable selected;
    public ListableAutoCompleteTextView(Context context,AttributeSet attrs){
        super(context, attrs);
    }
    @Override
    public int getListSelection(){
        Log.i("temp","***"+super.getListSelection());
        return super.getListSelection();
    }
    @Override
    public void setListSelection(int position){
        Log.i("temp","***setting selection:"+position);
        super.setListSelection(position);
    }
    @Override
    public CharSequence convertSelectionToString(Object selectedItem){
        CharSequence result = ((Listable)selectedItem).getDisplayString();
        Log.i("temp","***Selection:"+result+" ");
        return result;
    }
}
