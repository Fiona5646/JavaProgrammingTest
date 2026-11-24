package org.example;

public class DesignViva extends Assessment{
    private String assessmentName=AssessmentFeed.DESIGN_VIVA;
    public DesignViva(){
        AIAllowed=false;
    }

    public double getProgramMark(String cid) {
        double design=AssessmentFeed.getResult(cid,assessmentName,AssessmentFeed.DESIGN_MARK);
        double explain=AssessmentFeed.getResult(cid,assessmentName,AssessmentFeed.EXPLANATION_MARK);
        programMark=design*0.5+explain*0.5;
        return programMark;
    }
}
