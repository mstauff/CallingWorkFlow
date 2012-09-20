package org.lds.community.CallingWorkFlow.domain;

public class Member extends MemberBaseRecord implements Listable{

    public Member( String lastName, String firstName, long individualId ) {
        this.setLastName( lastName );
        this.setFirstName(firstName);
        this.setIndividualId(individualId);
    }

    public Member() {

    }

    public String getDisplayString(){
        return getFirstName()+" "+getLastName();
    }
}
