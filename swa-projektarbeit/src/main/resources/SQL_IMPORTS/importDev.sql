/**
 * @author Joana Wegener, Marcel Sauer(886022)
 * @email joana.wegener@hs-osnabrueck.de, marcel.sauer@hs-osnabrueck.de
 * @create date 2022-01-22 19:22:56
 * @modify date 2022-01-31 09:26:34
 * @desc [description]
 */

/* Testen der authentifizierung*/
INSERT INTO Account (userId, username, password)
VALUES (1,'studentTest','264c8c381bf16c982a4e59b0dd4c6f7808c51a05f64c35db42cc78a2a72875bb'),
    (2,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918'),/*Password: "admin"*/
    (3,'sekt','63870b1f068e4a136ad446e762d45f752de8ff74524bf7e458b495598f01c414');/*Password: "sekt"*/

INSERT INTO User_roles (userId, role)
VALUES (1, 0),
    (2, 1),
    (3, 2),
    (1, 3),
    (2, 3),
    (3, 3);

/*Password: "student"*/
INSERT INTO Account (userId, username, password)
VALUES (4,'student','264c8c381bf16c982a4e59b0dd4c6f7808c51a05f64c35db42cc78a2a72875bb'),
    (5,'Nina Chuba','264c8c381bf16c982a4e59b0dd4c6f7808c51a05f64c35db42cc78a2a72875bb'),
    (6,'Beth Harmon','264c8c381bf16c982a4e59b0dd4c6f7808c51a05f64c35db42cc78a2a72875bb'),
    (7,'Test Name','264c8c381bf16c982a4e59b0dd4c6f7808c51a05f64c35db42cc78a2a72875bb');

INSERT INTO User_roles (userId, role)
VALUES (4, 0),
    (4, 3),
    (5, 0),
    (5, 3),
    (6, 0),
    (6, 3),
    (7, 0),
    (7, 3);

INSERT INTO adress(nr, street, town, zipCode, id) 
VALUES (12, 'Eisenbahnstrasse', 'Osnabrueck', 49074, 1),
    (6, 'Jahnplatz', 'Osnabrueck', 49080, 2),
    (17, 'Suesterstrasse', 'Osnabrueck', 49074, 3),
    (18, 'Sanstrasse', 'Osnabrueck', 49080, 4);

INSERT INTO students(adress_id, email, vorname, nachname, matNr, userId) 
VALUES (1, 'daisy.jones@hs-osnabrueck.de', 'Daisy', 'Jones', 1000, 4),
    (2, 'nina.chuba@hs-osnabrueck.de', 'Nina', 'Chuba', 1001, 5),
    (3, 'beth.harmon@hs-osnabrueck.de', 'Beth', 'Harmon', 1002, 6),
    (4, 'test.name@hs-osnabrueck.de', 'Test', 'Name', 1003, 7);

INSERT INTO Module(name, modulID, isProject, description) 
VALUES ('Software Architektur - Konzepte und Anwendungen', 1, true, 'Die Software-Architektur legt die wesentlichen Softwarebausteine (Komponenten) zur Strukturierung eines
        Softwaresystems fest und definiert Vorgaben zum erwarteten dynamischen Verhalten einer Software,
        unter Einhaltung definierter nicht-funktionaler Anforderungen. Studierende lernen typische SoftwareArchitektur-Stile, -Muster und Prinzipien kennen, strukturieren die Software in Komponenten, definieren
        Interaktionsformen zwischen Komponenten und weisen die Tragfähigkeit der Entscheidungen nach.'),
    ('Webanwendungen', 2, true, 'Webanwendungen dienen dazu, dem Benutzer den Zugriff auf Services über Webbrowser oder Mobilgeräte
        ermöglichen. In diesem Modul werden die Grundlagen solcher Webanwendungen behandelt, sowie anhand von
        ausgewählten Technologien direkt angewandt'),
    ('Spieleprogrammierung und 3D-Animation', 3, true, 'Digitale Spiele sind aus dem Alltag vieler Menschen nicht mehr wegzudenken. Ob nun vor dem
        heimischen PC, auf der Couch zusammen mit Freunden oder während der Zugfahrt mit dem Smartphone,
        Spiele haben unseren Alltag durchdrungen und sind zu einer milliardenschweren Industrie avanciert.
        Obwohl sich digitale Spiele in ihrer Ausprägung stark unterscheiden können und darüber hinaus für
        unterschiedliche Plattformen entwickelt werden (PC, Konsole, Handheld, Smartphone, Browser, etc.),
        lassen sich dennoch viele Konzepte verallgemeinern und bilden so einen Grundstock, der für erfolgreiche
        Spieleentwicklungen von besonderem Wert ist.'),
    ('Verteilte Systeme', 4, false, 'Die Studierenden sollen dem schnell wachsenden Bedarf an Know-How im Bereich verteilter Systeme und
        Anwendungen (dazu zählen insbesondere web-orientierte Anwendungen) mit Kompetenz und technischer
        Tiefe begegnen können'),
    ('Theoretische Informatik', 5, false, 'Die theoretische Informatik bildet sowohl hinsichtlich der Begrifflichkeiten als auch der Betrachtungen und
    Schlußweisen eine sehr wichtige Grundlage des Informatikstudiums und ist als Kernfach anzusehen.');

INSERT INTO GROUPS (maxMembers, modul_modulID, name, owner_matNr, type, groupId)
VALUES (3, 1, 'SWA Lerngruppe', 1000, 1, 1000),
    (4, 3, 'Spiele programmieren', 1001, 1, 1001),
    (2, 1, 'SWA Projektarbeit', 1000, 0, 1002);

INSERT INTO student_groups (fk_student, fk_group) 
VALUES (1000, 1000),
    (1001, 1001),
    (1002, 1001),
    (1000, 1002),
    (1001, 1000),
    (1003, 1002);

INSERT INTO student_modules (fk_student, fk_modul) 
VALUES (1000, 1), 
    (1000,2), 
    (1000,3),
    (1001, 1),
    (1002, 2),
    (1001, 3), 
    (1001,4),
    (1001,5);

INSERT INTO Question (questionId, topic, text, studentName, owner_matNr, modul_modulID, hasSolution)
VALUES (0, 'Vorlesung online?', 'Weiß jemand, ob die Vorlesung am Freitag online ist?', 'Daisy Jones', 1000, 5, true),
    (1, 'Test Topic' , 'Hier steht die Frage', 'Nina Chuba', 1001, 1, false),
    (2, 'Frage zum Projekt', 'Wann müssen wir das Projekt abgeben?', 'Daisy Jones', 1000, 1, true),
    (3, 'Hibernate SQL Queries' , 'Hi Leute, weiß jemand, wie man die SQL Queries von Hibernate in der Console anzeigt?', 'Nina Chuba', 1001, 1, true);
INSERT INTO Answer (answerID, question_modul_modulID, question_questionId, owner_matNr, isSolution, studentName, text)
VALUES (0, 5, 0, 1002, true,'Beth Harmon', 'ja, die VL ist online:)'),
    (1, 1, 3, 1001, false,'Daisy Jones', 'Füg das einfach in deine application.properties ein: %dev.quarkus.hibernate-orm.log.sql=false'),
    (2, 1, 3, 1002, true,'Beth Harmon', 'Das ist nicht richtig, du musst das hier einfügen: %dev.quarkus.hibernate-orm.log.sql=true');


