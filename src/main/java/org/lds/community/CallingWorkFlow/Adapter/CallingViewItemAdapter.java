package org.lds.community.CallingWorkFlow.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.activity.CallingListFragment;
import org.lds.community.CallingWorkFlow.domain.CallingViewItem;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;

public class CallingViewItemAdapter extends ArrayAdapter<CallingViewItem> {

	@Inject
	CallingListFragment callingListFragment;

    private List<CallingViewItem> items;

    public CallingViewItemAdapter(Context context, int textViewResourceId, List<CallingViewItem> items) {
        super(context, textViewResourceId, items);
        this.items = items;
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
			        initializeMenu(position, isChecked, convertView);
		        }
	        });
        }
        return v;
    }

	public View initializeMenu(int position, boolean checked, View v) {
		Menu menu = (Menu) v.findViewById(R.id.bottom_menu_list);
		if(menu == null) {
			MenuInflater inflater = new MenuInflater(getContext());
			//menu = inflater.inflate(R.menu.remove_calling_status_update_menu, null);
			menu = (Menu) v.findViewById(R.id.bottom_menu_list);
		}
		if(menu != null) {
			menu.setGroupVisible(R.id.bottom_menu_list_group, (callingListFragment.removalItems.size() > 0));
		}
		if(checked) {
			removeItem(callingListFragment.removalItems, position);
		} else {
			safeAddCalling(callingListFragment.removalItems, position);
		}
		return v;
	}

	private void safeAddCalling(List<Integer> indexes, Integer position) {
		removeItem(indexes, position);
		indexes.add(position);
	}

	public void removeItem(List<Integer> indexes, Integer position) {
		Iterator iterator = indexes.iterator();
		while(iterator.hasNext()) {
			if(iterator.next().equals(position)) {
				iterator.remove();
				break;
			}
		}
	}
}