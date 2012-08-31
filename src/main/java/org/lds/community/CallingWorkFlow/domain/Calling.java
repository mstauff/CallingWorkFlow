package org.lds.community.CallingWorkFlow.domain;

public class Calling extends CallingBaseRecord {


    public Calling() {
    }

    public Calling(long individualId, long positionId, String statusName, boolean completed, long assignedTo, long dueDate, boolean synced) {
        setIndividualId(individualId);
        setPositionId(positionId);
        setStatusName(statusName);
        setCompleted( completed ? 1 : 0 );
        setAssignedTo( assignedTo );
        setIsSynced( synced ? 1 : 0 );
    }

}
