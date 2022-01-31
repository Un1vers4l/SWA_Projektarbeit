/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 19:22:56
 * @modify date 2022-01-31 09:26:34
 * @desc [description]
 */


INSERT INTO adress(nr, street, town, zipCode, id) VALUES (12, 'Eisenbahnstrasse', 'Osnabrueck', 49074, 1);
INSERT INTO students(adress_id, email, name, matNr) VALUES (1, 'daisy.jones@hs-osnabrueck.de', 'Daisy Jones', 1000);
INSERT INTO adress(nr, street, town, zipCode, id) VALUES (6, 'Jahnplatz', 'Osnabrueck', 49080, 2);
INSERT INTO students(adress_id, email, name, matNr) VALUES (1, 'nina.chuba@hs-osnabrueck.de', 'Nina Chuba', 1001);
INSERT INTO adress(nr, street, town, zipCode, id) VALUES (17, 'Suesterstrasse', 'Osnabrueck', 49074, 3);
INSERT INTO students(adress_id, email, name, matNr) VALUES (3, 'beth.harmon@hs-osnabrueck.de', 'Beth Harmon', 1002);
INSERT INTO adress(nr, street, town, zipCode, id) VALUES (18, 'Sanstrasse', 'Osnabrueck', 49080, 4);
INSERT INTO students(adress_id, email, name, matNr) VALUES (4, 'test.name@hs-osnabrueck.de', 'Test Name', 1003);
INSERT INTO MockModule(name, id, isProject) VALUES ('SWA', 1, true);
INSERT INTO MockModule(name, id, isProject) VALUES ('Webanwendungen', 2, true);
INSERT INTO MockModule(name, id, isProject) VALUES ('Spieleprogrammierung', 3, true);
INSERT INTO MockModule(name, id, isProject) VALUES ('Verteilte Systeme', 4, false);
INSERT INTO MockModule(name, id, isProject) VALUES ('Theoretische Informatik', 5, false);
INSERT INTO GROUPS (maxMembers, module_id, name, owner_matNr, type, groupId) VALUES (3, 1, 'SWA Lerngruppe', 1000, 1, 1000);
INSERT INTO student_groups (fk_student, fk_group) VALUES (1000, 1000);
INSERT INTO GROUPS (maxMembers, module_id, name, owner_matNr, type, groupId) VALUES (4, 3, 'Spiele programmieren', 1001, 1, 1001);
INSERT INTO student_groups (fk_student, fk_group) VALUES (1001, 1001);
INSERT INTO student_groups (fk_student, fk_group) VALUES (1002, 1001);
INSERT INTO GROUPS (maxMembers, module_id, name, owner_matNr, type, groupId) VALUES (2, 1, 'SWA Projektarbeit', 1000, 0, 1002);
INSERT INTO student_groups (fk_student, fk_group) VALUES (1000, 1002);
