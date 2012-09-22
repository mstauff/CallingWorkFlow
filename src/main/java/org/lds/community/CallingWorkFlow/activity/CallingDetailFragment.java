package org.lds.community.CallingWorkFlow.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.api.CallingManager;
import org.lds.community.CallingWorkFlow.domain.*;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CallingDetailFragment extends RoboSherlockFragment {
    @Inject
    WorkFlowDB db;
    @Inject
    private CallingManager callingManager;
    @InjectView(value = R.id.saveCallingButton)
    Button saveCallingButton;
    @InjectView(value = R.id.cancelButton)
    Button cancelButton;
    AutoCompleteTextView positionField;
    AutoCompleteTextView memberField;
    Spinner statusSpinner;
    CallingViewItem callingViewItem;
    Position selectedPosition;
    Member selectedMember;
    List<WorkFlowStatus> statusList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            callingViewItem = (CallingViewItem)bundle.get("callingViewItems");
            positionField.setText(callingViewItem.getPositionName());
            memberField.setText(callingViewItem.getFullName());
            for(WorkFlowStatus s: statusList){
                if(callingViewItem.getStatusName().equals(s.getStatusName())){
                    statusSpinner.setSelection(statusList.indexOf(s));
                    break;
                }
            }
        }
        saveCallingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveCalling(view);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cancelChanges(view);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = getLayoutInflater( savedInstanceState ).inflate( R.layout.callingdetail_fragment, container );

        positionField = (AutoCompleteTextView) view.findViewById(R.id.callingPosition);
        List<? extends Listable> positionList = db.getPositions();
        ObjectListAdapter positionAdapter = new ObjectListAdapter(getActivity(),R.layout.autocomplete_textview,positionList);
        positionField.setAdapter(positionAdapter);
        positionField.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedPosition = (Position) positionField.getAdapter().getItem(i);
                    }
                }
        );

        memberField = (AutoCompleteTextView) view.findViewById(R.id.memberName);
        List<? extends Listable> memberList = db.getWardList();
        ObjectListAdapter memberAdapter = new ObjectListAdapter(getActivity(),R.layout.autocomplete_textview,memberList);
        memberField.setAdapter(memberAdapter);
        memberField.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedMember = (Member) memberField.getAdapter().getItem(i);
                    }
                }
        );

        statusSpinner = (Spinner) view.findViewById(R.id.status_spinner);
        statusList = db.getWorkFlowStatuses();
        List<String> statusOptions = new ArrayList<String>();
        for(WorkFlowStatus s: statusList) { statusOptions.add(s.getStatusName()); }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,statusOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(spinnerAdapter);

        return view;
    }

    public void saveCalling(View v){
        if(selectedPosition != null && selectedMember != null){
            String statusName = (String) statusSpinner.getSelectedItem();
            Long positionId = selectedPosition.getPositionId();
            Long memberId = selectedMember.getIndividualId();
            Calling calling = new Calling();
            calling.setPositionId(memberId);
            calling.setIndividualId(positionId);
            calling.setStatusName(statusName);
            callingManager.saveCalling(calling,getActivity());
            Log.i("Detail Page","Calling Saved:"+selectedMember.getDisplayString()+
                    ", "+selectedPosition.getDisplayString()+", "+statusName);
        }

    }

    public void cancelChanges(View v){
        getActivity().onBackPressed();
    }

}