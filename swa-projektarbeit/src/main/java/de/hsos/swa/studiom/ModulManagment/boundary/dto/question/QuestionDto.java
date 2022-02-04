package de.hsos.swa.studiom.ModulManagment.boundary.dto.question;

import de.hsos.swa.studiom.ModulManagment.entity.Question;

public class QuestionDto {
    
    private int questionId;

    private String topic;

    private String text;

    private String studentName;


    public QuestionDto() {
    }


    public QuestionDto(int questionId, String topic, String text, String studentName) {
        this.questionId = questionId;
        this.topic = topic;
        this.text = text;
        this.studentName = studentName;
    }


    public int getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public static class Converter {

        public static QuestionDto SimpleDto(Question question){
            QuestionDto uDto = new QuestionDto();
            uDto.setQuestionId(question.getquestionId());
            uDto.setStudentName(question.getStudentName());
            uDto.setTopic(question.getTopic());
            return uDto;
        }
        public static QuestionDto QuestionToDto(Question question){
            QuestionDto uDto = new QuestionDto();
            uDto.setQuestionId(question.getquestionId());
            uDto.setStudentName(question.getStudentName());
            uDto.setTopic(question.getTopic());
            uDto.setText(question.getText());
            return uDto;
        }
    }

}
