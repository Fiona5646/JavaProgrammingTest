package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        IndependentProgrammingTest i1 = new IndependentProgrammingTest();
        AIProject a1=new AIProject();
        DesignViva d1=new DesignViva();
        ArrayList<Assessment> assessments=new ArrayList<>();
        assessments.add(i1);
        assessments.add(a1);
        assessments.add(d1);
        List<String> cid=AssessmentFeed.getCIDs();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Assessment Results");
            JButton printReport = new JButton("Print Report");
            printReport.addActionListener(e -> report(assessments));

            frame.setSize(1000, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            String[] columnNames={"Student Name","CID","Student Type","Programming Test","AI Project", "Design Viva","Overall Mark"};
            Object[][] data=new Object[cid.size()][7];
            for(int i=0;i<cid.size();i++){
                data[i][0]=AssessmentFeed.getStudentName(cid.get(i));
                data[i][1]=cid.get(i);
                data[i][2]=AssessmentFeed.getStudentType(cid.get(i));
                data[i][3]=assessments.get(0).getProgramMark(cid.get(i));
                data[i][4]=assessments.get(1).getProgramMark(cid.get(i));
                if (AssessmentFeed.getStudentType(cid.get(i)).equals("MSc")) {
                    data[i][5] = assessments.get(2).getProgramMark(cid.get(i));
                }else{
                    data[i][5] = "N/A";
                }
                data[i][6]=overallMark(cid.get(i),assessments);
            }
            JTable table=new JTable(data,columnNames);
            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
            {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
                {
                    final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    c.setBackground(checkAI(cid.get(row),assessments) ? Color.RED : Color.WHITE);
                    return c;
                }
            });

            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.add(printReport,BorderLayout.SOUTH);

        });
    }

    public static void report(ArrayList<Assessment> assessments) {
        System.out.println("Assessment Results");
        List<String> cid=AssessmentFeed.getCIDs();
        for(int i=0;i<cid.size();i++){
            System.out.println(AssessmentFeed.getStudentName(cid.get(i))+"  "+cid.get(i)+"  "+AssessmentFeed.getStudentType(cid.get(i)));
            System.out.println("Independent Programming Test: "+assessments.get(0).getProgramMark(cid.get(i)));
            System.out.println("Program mark: "+AssessmentFeed.getResult(cid.get(i),AssessmentFeed.PROGRAMMING_TEST,AssessmentFeed.PROGRAM_MARK));
            System.out.println("Explanation: "+ ((AssessmentFeed.getResult(cid.get(i),AssessmentFeed.PROGRAMMING_TEST,AssessmentFeed.EXPLAINED_SUCCESSFULLY)==0.0) ? "Fail" : "Pass"));
            System.out.println("AI Project: "+assessments.get(1).getProgramMark(cid.get(i)));
            System.out.println("Functionality: "+AssessmentFeed.getResult(cid.get(i),AssessmentFeed.AI_PROJECT,AssessmentFeed.FUNCTIONALITY_MARK));
            System.out.println("Verification: "+AssessmentFeed.getResult(cid.get(i),AssessmentFeed.AI_PROJECT,AssessmentFeed.VERIFICATION_MARK));
            if (AssessmentFeed.getStudentType(cid.get(i)).equals("MSc")) {
                System.out.println("Design Viva: "+assessments.get(2).getProgramMark(cid.get(i)));
                System.out.println("Design understanding: "+AssessmentFeed.getResult(cid.get(i),AssessmentFeed.DESIGN_VIVA,AssessmentFeed.DESIGN_MARK));
                System.out.println("Explanation: "+AssessmentFeed.getResult(cid.get(i),AssessmentFeed.DESIGN_VIVA,AssessmentFeed.EXPLANATION_MARK));
            }
            System.out.println("Overall Module Mark: "+overallMark(cid.get(i),assessments));
            System.out.println("AI Discrepancy: "+checkAI(cid.get(i),assessments));
            System.out.println("----------");
        }
    }

    public static double overallMark(String cid, ArrayList<Assessment> assessments){
        double overall=0.0;
        if(AssessmentFeed.getStudentType(cid).equals("MSc")){
            overall=assessments.get(0).getProgramMark(cid)*0.25+assessments.get(1).getProgramMark(cid)*0.45+assessments.get(2).getProgramMark(cid)*0.3;
        }else{
            overall=assessments.get(0).getProgramMark(cid)*0.3+assessments.get(1).getProgramMark(cid)*0.7;
            }
        return overall;
    }
    public static boolean checkAI(String cid, ArrayList<Assessment> assessments){
        double sumNoAI=0;
        double sumAI=0;
        double countNoAI=0;
        double countAI=0;
        for(Assessment assessment:assessments){
            if(assessment.getAIAllowed()){
                sumAI+=assessment.getProgramMark(cid);
                countAI+=1;
            }else{
                sumNoAI+=assessment.getProgramMark(cid);
                countNoAI+=1;
            }
        }
        double avgNoAI=sumNoAI/countNoAI;
        double avgAI=sumAI/countAI;
        if(avgAI-avgNoAI>20){
            return true;
        }else{
            return false;
        }
    }

}