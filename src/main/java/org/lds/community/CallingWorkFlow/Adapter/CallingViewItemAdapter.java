package org.lds.community.CallingWorkFlow.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.activity.CallingListFragment;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;

import java.util.List;

public class CallingViewItemAdapter extends ArrayAdapter<CallingViewItem> {

	CallingListFragment callingListFragment;

    private List<CallingViewItem> items;

    public CallingViewItemAdapter(Context context, int textViewResourceId, List<CallingViewItem> items, CallingListFragment callingListFragment) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.callingListFragment = callingListFragment;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.member_name_status, null);
        }
        CallingViewItem o = items.get(position);
        if (o != null) {
	        TextView tt = (TextView) v.findViewById(R.id.calling_name);
            TextView bt = (TextView) v.findViewById(R.id.member_name_and_status);
            if (tt != null) {
				tt.setText(o.getPositionName());
            }
            if(bt != null) {
				bt.setText(o.getFullName() + " ( " + o.getStatusName() + " ) ");
            }
	        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.calling_checkbox);
	        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			        callingListFragment.initializeMenu(position, isChecked, convertView);
		        }
	        });
        }
        return v;
    }
}