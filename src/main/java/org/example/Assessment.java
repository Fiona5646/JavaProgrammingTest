package org.example;

public abstract class Assessment {
    protected double programMark;
    protected boolean AIAllowed;
    public Assessment(){}

    public abstract double getProgramMark(String cid);

    public boolean getAIAllowed(){return AIAllowed;}
}
