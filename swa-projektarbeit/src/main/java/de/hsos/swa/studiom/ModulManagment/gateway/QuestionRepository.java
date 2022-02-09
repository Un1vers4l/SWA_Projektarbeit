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

    Logger log = Logger.getLogger(ModulRepository.class);

    @Override
    public Optional<Question> getQuestion(int modulId, int questionId) {
        TypedQuery<Question> query = em.createNamedQuery("Modul.find", Question.class);
        query.setParameter("questionId", questionId);
        query.setParameter("modul", modulId);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Question> getAllQuestion(int modulId) throws EntityNotFoundException {
        Modul modul = modulService.getModulWithExeption(modulId);
        return modul.getQuestions().stream().collect(Collectors.toList());
    }

    @Override
    public Question createdQuestion(int matNr, int modulId, String topic, String text) throws EntityNotFoundException {
        if(topic == null || text == null) throw new IllegalArgumentException();

        Student student = this.studentService.getStudent(matNr).get();
        Modul modul = this.modulService.getModulWithExeption(modulId);

        Question question = new Question(topic, text, modul, student.getFullName(), student);
        em.persist(question);
        return question;
    }

    @Override
    public Question changeQuestion(int questionId, Question question) throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteQuestion(int questionId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Question getWithExeption(int modulId) throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
