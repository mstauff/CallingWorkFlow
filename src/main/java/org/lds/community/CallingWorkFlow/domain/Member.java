package org.lds.community.CallingWorkFlow.domain;

public class Member extends MemberBaseRecord {

    public Member( String lastName, String firstName, long individualId ) {
        this.setLastName( lastName );
        this.setFirstName(firstName);
        this.setIndividualId(individualId);
    }

    public Member() {

    }
}
