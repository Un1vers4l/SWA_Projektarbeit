package de.hsos.swa.studiom.ModulManagment.boundary.dto.answer;

import de.hsos.swa.studiom.ModulManagment.entity.Answer;

public class AnswerDto {

    private int answerID;

    private String text;

    private boolean isSolution;

    private String studentName;


    public AnswerDto() {
    }

    public AnswerDto(int answerID, String text, boolean isSolution, String studentName) {
        this.answerID = answerID;
        this.text = text;
        this.isSolution = isSolution;
        this.studentName = studentName;
    }

    public int getAnswerID() {
        return this.answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isIsSolution() {
        return this.isSolution;
    }

    public boolean getIsSolution() {
        return this.isSolution;
    }

    public void setIsSolution(boolean isSolution) {
        this.isSolution = isSolution;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public static class Converter {

        public static AnswerDto SimpleDto(Answer answer){
            AnswerDto aDto = new AnswerDto();
            aDto.setAnswerID(answer.getAnswerID());
            aDto.setIsSolution(answer.getIsSolution());
            return aDto;
        }
        public static AnswerDto AnswerToDto(Answer answer){
            AnswerDto aDto = new AnswerDto();
            aDto.setAnswerID(answer.getAnswerID());
            aDto.setIsSolution(answer.getIsSolution());
            aDto.setText(answer.getText());
            aDto.setStudentName(answer.getStudentName());
            return aDto;
        }
    }
}