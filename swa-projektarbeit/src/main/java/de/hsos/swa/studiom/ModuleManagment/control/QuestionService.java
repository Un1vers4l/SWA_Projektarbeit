package de.hsos.swa.studiom.ModuleManagment.control;

import java.util.List;

import de.hsos.swa.studiom.ModuleManagment.entity.Question;

public interface QuestionService {
    public Module getQuestion(int modulId, int questionId);
    public List<Module> getAllQuestion(int modulId);
    public Question createdQuestion(int modulId, int matNr, String topic, String text);
    public Question changeQuestion(Question question);
    public boolean deleteQuestion(int questionId);
}
