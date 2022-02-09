package de.hsos.swa.studiom.ModulManagment.boundary.dto.question;

import de.hsos.swa.studiom.ModulManagment.entity.Question;

public class PostQuestionDto {

    private String topic;

    private String text;

    public PostQuestionDto() {
    }

    public PostQuestionDto(String topic, String text) {
        this.topic = topic;
        this.text = text;
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
    public static class Converter {


        public static Question dtoToQuestion(PostQuestionDto qDto){
            Question q = new Question();
            q.setTopic(qDto.getTopic());
            q.setText(qDto.getText());
            return q;
        }
    }
}
