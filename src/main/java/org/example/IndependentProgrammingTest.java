package org.example;

public class IndependentProgrammingTest extends Assessment{
    String assessmentName=AssessmentFeed.PROGRAMMING_TEST;
    public IndependentProgrammingTest(){
        AIAllowed=false;
    }
    public double getProgramMark(String cid) {
        double raw=AssessmentFeed.getResult(cid, assessmentName,AssessmentFeed.PROGRAM_MARK);
        double explain=AssessmentFeed.getResult(cid,assessmentName,AssessmentFeed.EXPLAINED_SUCCESSFULLY);
        if(explain==0.0 && raw>=40.0){
            programMark=40.0;
        }else{
            programMark=raw;
        }
        return programMark;
    }
}
