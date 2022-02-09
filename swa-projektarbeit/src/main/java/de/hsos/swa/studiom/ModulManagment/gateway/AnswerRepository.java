/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-09 14:39:31
 * @modify date 2022-02-09 14:39:31
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hsos.swa.studiom.ModulManagment.control.AnswerService;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.ModulManagment.entity.Answer;
import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
public class AnswerRepository implements AnswerService {
    @Inject
    QuestionService questionService;

    @Inject 
    StudentService studentService;

    @Inject
    EntityManager em;

    Logger log = Logger.getLogger(AnswerRepository.class);

    
    /** 
     * @param modulId
     * @param questionId
     * @param answerID
     * @return Optional<Answer>
     */
    @Override
    public Optional<Answer> getAnswer(int modulId, int questionId, int answerID) {
        Optional<Question> qOptional = this.questionService.getQuestion(modulId, questionId);
        if(!qOptional.isPresent()) return Optional.ofNullable(null);

        TypedQuery<Answer> query = em.createNamedQuery("Answer.find", Answer.class);
        query.setParameter("answerID", answerID);
        query.setParameter("question", qOptional.get());
        return query.getResultStream().findFirst();
    }

    
    /** 
     * @param modulId
     * @param questionId
     * @return List<Answer>
     * @throws EntityNotFoundException
     */
    @Override
    public List<Answer> getAllAnswer(int modulId, int questionId) throws EntityNotFoundException {
        Question question = this.questionService.getWithExeption(modulId, questionId);
        return question.getAnswers().stream().collect(Collectors.toList());
    }

    
    /** 
     * @param matNr
     * @param modulID
     * @param questionId
     * @param text
     * @return Answer
     * @throws EntityNotFoundException
     */
    @Override
    public Answer createdAnswer(int matNr ,int modulID, int questionId, String text)
            throws EntityNotFoundException {
        if(text == null) throw new IllegalArgumentException();
        
        Student student = this.studentService.getStudent(matNr).get();
        Question question = this.questionService.getWithExeption(modulID, questionId);

        Answer answer = new Answer(text, student.getFullName(), question, student);
        em.persist(answer);
        log.info("Answer wurde erzeugt");
        return answer;
    }

    
    /** 
     * @param modulId
     * @param questionId
     * @param answerID
     * @param changAnswer
     * @return Answer
     * @throws EntityNotFoundException
     */
    @Override
    public Answer changeAnswer(int modulId, int questionId, int answerID, Answer changAnswer)
            throws EntityNotFoundException {
        Answer answer = this.getWithExeption(modulId, questionId, answerID);
        answer.changeMyData(changAnswer);
        log.info("Change Answer");
        return answer;
    }

    
    /** 
     * @param modulId
     * @param questionId
     * @param answerID
     * @return boolean
     */
    @Override
    public boolean deleteAnswer(int modulId, int questionId, int answerID) {
        Optional<Answer> aOptional = this.getAnswer(modulId, questionId, answerID);
        if(!aOptional.isPresent()) return false;

        em.remove(aOptional.get());
        log.info("Remove Answer");
        return true;
    }

    
    /** 
     * @param modulId
     * @param questionId
     * @param answerID
     * @return Answer
     * @throws EntityNotFoundException
     */
    @Override
    public Answer getWithExeption(int modulId, int questionId, int answerID) throws EntityNotFoundException {
        Optional<Answer> aOptional = this.getAnswer(modulId, questionId, answerID);
        if(!aOptional.isPresent()) throw new EntityNotFoundException(Answer.class, answerID);
        return aOptional.get();
    }

    
    /** 
     * @param modulId
     * @param questionId
     * @param answerID
     * @return boolean
     * @throws EntityNotFoundException
     */
    @Override
    public boolean changeIsSolution(int modulId, int questionId, int answerID) throws EntityNotFoundException {
        Answer answer = this.getWithExeption(modulId, questionId, answerID);
        Question question = answer.getQuestion();


        if(question.hasSolution() && !answer.getIsSolution()) return false;
        answer.changeIsSolution();
        question.changeHasSolution();
        log.info("Change Solution");
        return true;
    }

    
    /** 
     * @param matNr
     * @param modulId
     * @param questionId
     * @param answerID
     * @return boolean
     * @throws EntityNotFoundException
     */
    @Override
    public boolean isAnswerOwner(int matNr, int modulId, int questionId, int answerID) throws EntityNotFoundException {
        Answer answer = this.getWithExeption(modulId, questionId, answerID);

        if(answer.getOwner() == null) return false;
        return answer.getOwner().getMatNr() == matNr;
    }
    
}
