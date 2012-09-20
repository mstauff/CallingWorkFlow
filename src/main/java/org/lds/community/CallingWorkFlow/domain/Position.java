package org.lds.community.CallingWorkFlow.domain;

public class Position extends PositionBaseRecord implements Listable{
    public String getDisplayString(){
        return getPositionName();
    }
}
