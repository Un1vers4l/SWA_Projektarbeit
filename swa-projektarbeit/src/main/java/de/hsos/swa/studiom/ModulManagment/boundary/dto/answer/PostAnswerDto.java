package de.hsos.swa.studiom.ModulManagment.boundary.dto.answer;

import javax.validation.constraints.NotBlank;

import de.hsos.swa.studiom.ModulManagment.entity.Answer;

public class PostAnswerDto {

    @NotBlank(message="Bitte geben Sie ein Antwort an")
    private String text;

    public PostAnswerDto() {
    }


    public PostAnswerDto(String text) {
        this.text = text;
    }


    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public static class Converter {

        public static Answer DtoToAnswer(PostAnswerDto dto){
            Answer answer = new Answer();
            answer.setText(dto.getText());
            return answer;
        }
    }
    
}
