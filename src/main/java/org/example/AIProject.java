package org.example;

public class AIProject extends Assessment{
    String assessmentName=AssessmentFeed.AI_PROJECT;

    public AIProject(){
        AIAllowed=true;
    }
    public double getProgramMark(String cid) {
        double func=AssessmentFeed.getResult(cid, assessmentName,AssessmentFeed.FUNCTIONALITY_MARK);
        double verif=AssessmentFeed.getResult(cid,assessmentName,AssessmentFeed.VERIFICATION_MARK);
        programMark=0.4*func+0.6*verif;
        return programMark;
    }
}
