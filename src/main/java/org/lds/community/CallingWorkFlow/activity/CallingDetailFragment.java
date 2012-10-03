package org.lds.community.CallingWorkFlow.activity;


import android.os.Bundle;
import android.view.*;
import android.widget.*;
import org.lds.community.CallingWorkFlow.R;
import org.lds.community.CallingWorkFlow.api.CallingManager;
import org.lds.community.CallingWorkFlow.domain.*;
import org.lds.community.CallingWorkFlow.wigdets.robosherlock.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CallingDetailFragment extends RoboSherlockFragment implements View.OnClickListener {

    public static final String CALLING_INDEX = "selectedCalling";

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
    private int callingIndex;
    long selectedPositionId;
    long selectedMemberId;
    List<WorkFlowStatus> statusList;
    List<CallingViewItem> callingList;

    private static final int SWIPE_MIN_DISTANCE = 80;
    private static final int SWIPE_MAX_OFF_PATH = 300;
    private static final int SWIPE_THRESHOLD_VELOCITY = 75;


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            callingList = callingManager.getCurrentViewCallingList();
            // make sure that we have a valid index
            callingIndex = getNextCallingIndex(bundle.getInt(CALLING_INDEX, 0) - 1, callingList);

            callingViewItem = callingList.get( callingIndex );
            initUIFromCalling(callingViewItem);
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

    /**
     * returns an index within the size of the list (wrapping back to the start of the list if we've reached the end)
     *
     * @param currentCallingIndex
     * @param callingList
     * @return
     */
    static int getNextCallingIndex(int currentCallingIndex, List<CallingViewItem> callingList) {
        if( ++currentCallingIndex >= callingList.size() ) {
            currentCallingIndex = 0;
        }
        return currentCallingIndex;
    }

    static int getPreviousCallingIndex(int currentCallingIndex, List<CallingViewItem> callingList) {
        if( --currentCallingIndex < 0 ) {
            currentCallingIndex = callingList.size() -1;
        }
        return currentCallingIndex;
    }

    private void initUIFromCalling(CallingViewItem calling) {
        positionField.setText(calling.getPositionName());
        memberField.setText(calling.getFullName());
        selectedPositionId = calling.getPositionId();
        selectedMemberId = calling.getIndividualId();
        statusSpinner.setSelection(( (ArrayAdapter) statusSpinner.getAdapter()).getPosition( calling.getStatusName() ));
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
                        selectedPositionId = ((Position)positionField.getAdapter().getItem(i)).getPositionId();
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
                        selectedMemberId = ((Member)memberField.getAdapter().getItem(i)).getIndividualId();
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

        final GestureDetector gestureDetector = new GestureDetector(new CallingSwipeDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        view.setOnClickListener( this );
        view.setOnTouchListener( gestureListener );


        return view;
    }

    public void saveCalling(View v){
        if(selectedPositionId != -1 && selectedMemberId != -1){
            if(callingViewItem == null){
                callingViewItem = new CallingViewItem();
            }
            callingViewItem.setPositionId(selectedPositionId);
            callingViewItem.setIndividualId(selectedMemberId);
            callingViewItem.setStatusName((String) statusSpinner.getSelectedItem());
            callingManager.saveCalling(callingViewItem,getActivity());
            getActivity().onBackPressed();
        }

    }

    public void cancelChanges(View v){
        getActivity().onBackPressed();
    }

    @Override
    public void onClick(View view) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    class CallingSwipeDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    Toast.makeText(getActivity(), "Left Swipe", Toast.LENGTH_SHORT).show();
                    callingIndex = getNextCallingIndex( callingIndex, callingList );
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    Toast.makeText(getActivity(), "Right Swipe", Toast.LENGTH_SHORT).show();
                    callingIndex = getPreviousCallingIndex( callingIndex, callingList );
                }
                callingViewItem = callingList.get( callingIndex );
                initUIFromCalling( callingViewItem );
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }

}