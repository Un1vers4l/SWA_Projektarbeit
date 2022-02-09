/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-02 22:08:39
 * @modify date 2022-02-02 22:08:39
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

@Vetoed
@Entity
@NamedQuery(name = "Answer.find", query = "SELECT f from Answer f Where f.question = :question AND f.answerID = :answerID", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Answer implements Serializable {

    @Id
    @SequenceGenerator(name = "answerIdSequence", sequenceName = "Answers_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "answerIdSequence")
    private int answerID;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean isSolution;

    @Column(nullable = false)
    private String studentName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student owner;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Question question;


    public Answer() {
    }


    public Answer(String text, String studentName, Question question, Student owner) {
        this.text = text;
        this.isSolution = false;
        this.studentName = studentName;
        this.question = question;
        this.owner = owner;
    }

    public int getAnswerID() {
        return this.answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isIsSolution() {
        return this.isSolution;
    }

    public boolean getIsSolution() {
        return this.isSolution;
    }

    public void setIsSolution(boolean isSolution) {
        this.isSolution = isSolution;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
        if (!(o instanceof Answer)) {
            return false;
        }
        Answer answer = (Answer) o;
        return answerID == answer.answerID && Objects.equals(question, answer.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerID, text, isSolution, studentName, question);
    }


    @Override
    public String toString() {
        return "{" +
            " answerID='" + getAnswerID() + "'" +
            ", text='" + getText() + "'" +
            ", isSolution='" + isIsSolution() + "'" +
            ", studentName='" + getStudentName() + "'" +
            "}";
    }

    public void changeMyData(Answer other){
        if(this.text != null) this.text = other.text;
    }
    public void changeIsSolution(){
        this.isSolution = !this.isSolution;
    }

}
