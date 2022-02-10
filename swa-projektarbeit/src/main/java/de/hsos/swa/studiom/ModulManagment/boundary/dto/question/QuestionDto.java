/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-09 14:40:03
 * @modify date 2022-02-09 14:40:03
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.dto.question;

import java.util.List;
import java.util.stream.Collectors;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.answer.AnswerDto;
import de.hsos.swa.studiom.ModulManagment.entity.Question;

public class QuestionDto {

    private int questionId;

    private String topic;

    private String text;

    private String studentName;

    private int studentMatNr;

    private boolean hasSolution;

    private List<AnswerDto> answers;

    public QuestionDto() {
    }

    public QuestionDto(int questionId, String topic, String text, String studentName, boolean hasSolution,
            List<AnswerDto> answers) {
        this.questionId = questionId;
        this.topic = topic;
        this.text = text;
        this.studentName = studentName;
        this.hasSolution = hasSolution;
        this.answers = answers;
    }

    public QuestionDto(int questionId, String topic, String text, String studentName, int studentMatNr,
            boolean hasSolution, List<AnswerDto> answers) {
        this.questionId = questionId;
        this.topic = topic;
        this.text = text;
        this.studentName = studentName;
        this.studentMatNr = studentMatNr;
        this.hasSolution = hasSolution;
        this.answers = answers;
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

    public boolean isHasSolution() {
        return this.hasSolution;
    }

    public boolean getHasSolution() {
        return this.hasSolution;
    }

    public void setHasSolution(boolean hasSolution) {
        this.hasSolution = hasSolution;
    }

    public List<AnswerDto> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public int getStudentMatNr() {
        return studentMatNr;
    }

    public void setStudentMatNr(int studentMatNr) {
        this.studentMatNr = studentMatNr;
    }

    public static class Converter {

        public static QuestionDto SimpleDto(Question question) {
            QuestionDto uDto = new QuestionDto();
            uDto.setQuestionId(question.getquestionId());
            uDto.setStudentName(question.getStudentName());
            uDto.setTopic(question.getTopic());
            uDto.setHasSolution(question.getHasSolution());
            return uDto;
        }

        public static QuestionDto QuestionToDto(Question question) {
            QuestionDto uDto = new QuestionDto();
            uDto.setQuestionId(question.getquestionId());
            uDto.setStudentName(question.getStudentName());
            uDto.setTopic(question.getTopic());
            uDto.setText(question.getText());
            uDto.setHasSolution(question.getHasSolution());
            uDto.setAnswers(
                    question.getAnswers().stream().map(AnswerDto.Converter::SimpleDto).collect(Collectors.toList()));
            return uDto;
        }

        public static QuestionDto QuestionToDtoAnswers(Question question) {
            QuestionDto uDto = new QuestionDto();
            uDto.setQuestionId(question.getquestionId());
            uDto.setStudentName(question.getStudentName());
            uDto.setTopic(question.getTopic());
            uDto.setText(question.getText());
            uDto.setHasSolution(question.getHasSolution());
            uDto.setAnswers(
                    question.getAnswers().stream().map(AnswerDto.Converter::AnswerToDto).collect(Collectors.toList()));
            return uDto;
        }
    }

}
