package org.lds.community.CallingWorkFlow.domain;

public class Calling extends CallingBaseRecord {

    public Calling() {
    }

    public Calling(long individualId, long positionId, String statusName, long assignedTo, long dueDate, boolean synced) {
        setIndividualId(individualId);
        setPositionId(positionId);
        setStatusName(statusName);
        setAssignedTo(assignedTo);
        setIsSynced(synced);
    }
}