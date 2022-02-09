/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-02 22:08:34
 * @modify date 2022-02-02 22:08:34
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

@Vetoed
@Entity
@NamedQuery(name = "Question.findAll", query = "SELECT f from Question f", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@NamedQuery(name = "Question.find", query = "SELECT f from Question f Where f.questionId = :questionId AND f.modul = :modul", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Question {

    @Id
    @SequenceGenerator(name = "questionIdSequence", sequenceName = "Questions_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "questionIdSequence")
    private int questionId;
    @Column(nullable = false)
    private String topic;
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String studentName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Modul modul;

    @OneToMany(mappedBy="question", cascade = CascadeType.REMOVE)
    private Set<Answer> answers = new HashSet<>();

    /*@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="answerId")
    private Question answers;
 
    @OneToMany(mappedBy = "answers")
    private Set<Question> subordinates = new HashSet<>();*/


    public Question() {
    }

    public Question(String topic, String text, Modul modul, String studentName, Student owner) {
        this.topic = topic;
        this.text = text;
        this.modul = modul;
        this.studentName = studentName;
        this.owner = owner;
    }


    public int getquestionId() {
        return this.questionId;
    }

    public void setquestionId(int questionId) {
        this.questionId = questionId;
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

    public Modul getModul() {
        return this.modul;
    }

    public void setModul(Modul modul) {
        this.modul = modul;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Set<Answer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Student getOwner() {
        return this.owner;
    }

    public void setOwner(Student owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Question)) {
            return false;
        }
        Question question = (Question) o;
        return questionId == question.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }

    @Override
    public String toString() {
        return "{" +
            " questionId='" + getquestionId() + "'" +
            ", topic='" + getTopic() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }

    
}
