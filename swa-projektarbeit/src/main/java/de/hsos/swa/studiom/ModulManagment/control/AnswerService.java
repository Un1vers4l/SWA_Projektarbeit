/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-03 20:17:54
 * @modify date 2022-02-03 20:17:54
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.ModulManagment.entity.Answer;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

public interface AnswerService {
    public Optional<Answer> getAnswer(int modulId, int questionId, int answerID);
    public List<Answer> getAllAnswer(int modulId, int questionId) throws EntityNotFoundException;
    public Answer createdAnswer(int matNr, int modulID, int questionId, String text) throws EntityNotFoundException;
    public Answer changeAnswer(int modulId, int questionId, int answerID, Answer changAnswer) throws EntityNotFoundException;
    public boolean deleteAnswer(int modulId, int questionId, int answerID);
    public Answer getWithExeption(int modulId, int questionId, int answerID) throws EntityNotFoundException;
    public boolean changeIsSolution(int modulId, int questionId, int answerID) throws EntityNotFoundException;
    public boolean isAnswerOwner(int matNr, int modulId, int questionId, int answerID) throws EntityNotFoundException;
}
