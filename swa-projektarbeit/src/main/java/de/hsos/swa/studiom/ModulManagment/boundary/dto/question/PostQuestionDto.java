package de.hsos.swa.studiom.ModulManagment.boundary.dto.question;

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
    
}
