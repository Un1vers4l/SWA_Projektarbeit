/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-09 14:39:26
 * @modify date 2022-02-09 14:39:26
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

import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
public class QuestionRepository implements QuestionService {

    @Inject
    StudentService studentService;

    @Inject
    ModulService modulService;

    @Inject
    EntityManager em;

    Logger log = Logger.getLogger(QuestionService.class);

    
    /** 
     * @param modulId
     * @param questionId
     * @return Optional<Question>
     */
    @Override
    public Optional<Question> getQuestion(int modulId, int questionId) {
        Optional<Modul> modul = modulService.getModul(modulId);
        if(!modul.isPresent()) return Optional.ofNullable(null);

        TypedQuery<Question> query = em.createNamedQuery("Question.find", Question.class);
        query.setParameter("questionId", questionId);
        query.setParameter("modul", modul.get());
        return query.getResultStream().findFirst();
    }

    
    /** 
     * @param modulId
     * @return List<Question>
     * @throws EntityNotFoundException
     */
    @Override
    public List<Question> getAllQuestion(int modulId) throws EntityNotFoundException {
        Modul modul = modulService.getModulWithExeption(modulId);
        return modul.getQuestions().stream().collect(Collectors.toList());
    }

    
    /** 
     * @param matNr
     * @param modulId
     * @param topic
     * @param text
     * @return Question
     * @throws EntityNotFoundException
     */
    @Override
    public Question createdQuestion(int matNr, int modulId, String topic, String text) throws EntityNotFoundException {
        if(topic == null || text == null) throw new IllegalArgumentException();

        Student student = this.studentService.getStudent(matNr).get();
        Modul modul = this.modulService.getModulWithExeption(modulId);

        Question question = new Question(topic, text, modul, student.getFullName(), student);
        em.persist(question);
        log.info("Question wurde erzeugt");
        return question;
    }

    
    /** 
     * @param modulId
     * @param questionId
     * @param changQuestion
     * @return Question
     * @throws EntityNotFoundException
     */
    @Override
    public Question changeQuestion(int modulId, int questionId, Question changQuestion) throws EntityNotFoundException {
        if(changQuestion == null) throw new IllegalArgumentException();
        Question question = this.getWithExeption(modulId, questionId);

        question.changeMyData(changQuestion);
        log.info("Change Question");
        return question;
    }

    
    /** 
     * @param questionId
     * @param modulId
     * @return boolean
     */
    @Override
    public boolean deleteQuestion(int questionId, int modulId) {
        Optional<Question> qOptional = this.getQuestion(modulId, questionId);
        if(!qOptional.isPresent()) return false;

        em.remove(qOptional.get());

        log.info("Remove Question");
        return true;
    }

    
    /** 
     * @param modulId
     * @param questionId
     * @return Question
     * @throws EntityNotFoundException
     */
    @Override
    public Question getWithExeption(int modulId, int questionId) throws EntityNotFoundException {
        Optional<Question> qOptional = this.getQuestion(modulId, questionId);

        if(!qOptional.isPresent()) throw new EntityNotFoundException(Question.class, questionId);
        return qOptional.get();
    }

    
    /** 
     * @param matNr
     * @param modulId
     * @param questionId
     * @return boolean
     * @throws EntityNotFoundException
     */
    @Override
    public boolean isQuestionOwner(int matNr, int modulId, int questionId) throws EntityNotFoundException {
        Question question = this.getWithExeption(modulId, questionId);

        if(question.getOwner() == null) return false;
        return question.getOwner().getMatNr() == matNr;
    }
    
}
