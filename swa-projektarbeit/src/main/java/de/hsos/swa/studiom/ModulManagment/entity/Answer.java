/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-02 22:08:39
 * @modify date 2022-02-02 22:08:39
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.entity;

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
@NamedQuery(name = "Answer.findAll", query = "SELECT f from Answer f", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Answer {

    @Id
    @SequenceGenerator(name = "answerIdSequence", sequenceName = "Answers_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "answerIdSequence")
    private int answerID;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean isSolution;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Student owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Question question;

}
