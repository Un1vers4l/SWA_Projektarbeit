/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-09 14:40:02
 * @modify date 2022-02-09 14:40:02
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.dto.question;

import javax.validation.constraints.NotBlank;

import de.hsos.swa.studiom.ModulManagment.entity.Question;

public class PostQuestionDto {

    @NotBlank(message="Bitte geben Sie den Topic an")
    private String topic;

    @NotBlank(message="Bitte geben Sie ein Text bzw. frage an")
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
