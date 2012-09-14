package org.lds.community.CallingWorkFlow.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.domain.Member;
import org.lds.community.CallingWorkFlow.domain.Position;
import org.lds.community.CallingWorkFlow.domain.WorkFlowDB;
import org.lds.community.CallingWorkFlow.domain.WorkFlowStatus;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CallingDetailFragment extends RoboSherlockFragment {
    @Inject
    WorkFlowDB db;
    @InjectView(value = R.id.saveCallingButton)
    Button saveCallingButton;
    @InjectView(value = R.id.cancelButton)
    Button cancelButton;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
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

        AutoCompleteTextView callingField = (AutoCompleteTextView) view.findViewById(R.id.callingPosition);
        List<Position> positions = db.getPositions();
        List<String> positionNames = new ArrayList<String>();
        for(Position v: positions) { positionNames.add(v.getPositionName()); }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.autocomplete_textview,positionNames);
        callingField.setAdapter(adapter);
        callingField.setThreshold(1);//Number of required characters for search

        AutoCompleteTextView memberField = (AutoCompleteTextView) view.findViewById(R.id.memberName);
        List<Member> members = db.getWardList();
        List<String> memberNames = new ArrayList<String>();
        for(Member m: members) { memberNames.add(m.getFirstName() + " " + m.getLastName()); }
        ArrayAdapter<String> memberAdapter = new ArrayAdapter<String>(getActivity(),R.layout.autocomplete_textview,memberNames);
        memberField.setAdapter(memberAdapter);
        memberField.setThreshold(1);//Number of required characters for search

        Spinner statusSpinner = (Spinner) view.findViewById(R.id.status_spinner);
        List<WorkFlowStatus> statusList = db.getWorkFlowStatuses();
        List<CharSequence> statusOptions = new ArrayList<CharSequence>();
        for(WorkFlowStatus s: statusList) { statusOptions.add(s.getStatusName()); }
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(getActivity(),android.R.layout.simple_spinner_item,statusOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(spinnerAdapter);

        return view;
    }

    public void saveCalling(View v){

    }

    public void cancelChanges(View v){
        getActivity().onBackPressed();
    }

}