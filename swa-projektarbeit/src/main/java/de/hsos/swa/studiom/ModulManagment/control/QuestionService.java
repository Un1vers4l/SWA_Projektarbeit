/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-03 20:17:52
 * @modify date 2022-02-03 20:17:52
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

public interface QuestionService {
    public Optional<Question> getQuestion(int modulId, int questionId);
    public List<Question> getAllQuestion(int modulId) throws EntityNotFoundException;
    public Question createdQuestion(int matNr, int modulID, String topic, String text) throws EntityNotFoundException;
    public Question changeQuestion(int modulId, int questionId, Question question) throws EntityNotFoundException;
    public boolean deleteQuestion(int questionId, int modulId);
    public Question getWithExeption(int modulId, int questionId) throws EntityNotFoundException;
    public boolean isStudentOwner(int matNr, int modulId, int questionId) throws EntityNotFoundException;
}
