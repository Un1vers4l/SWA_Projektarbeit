/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 19:22:56
 * @modify date 2022-01-22 19:22:56
 * @desc [description]
 */


INSERT INTO adress(nr, street, town, zipCode, id) VALUES (12, 'Eisenbahnstrasse', 'Osnabrueck', 49074, 1);
INSERT INTO adress(nr, street, town, zipCode, id) VALUES (6, 'Jahnplatz', 'Osnabrueck', 49080, 2);
INSERT INTO students(adress_id, email, name, matNr) VALUES (1, 'daisy.jones@hs-osnabrueck.de', 'Daisy Jones', 1000);
INSERT INTO students(adress_id, email, name, matNr) VALUES (1, 'nina.chuba@hs-osnabrueck.de', 'Nina Chuba', 1001);
INSERT INTO MockModule(name, id) VALUES ('SWA', 1);
INSERT INTO MockModule(name, id) VALUES ('Webanwendungen', 2);
INSERT INTO MockModule(name, id) VALUES ('Spieleprogrammierung', 3);
INSERT INTO GROUPS (maxMembers, module_id, name, owner_matNr, type, groupId) VALUES (3, 1, 'SWA Lerngruppe', 1000, 0, 1000);
INSERT INTO student_groups (fk_student, fk_group) VALUES (1000, 1000);
INSERT INTO GROUPS (maxMembers, module_id, name, owner_matNr, type, groupId) VALUES (3, 3, 'Spieleprogrammierung lernen', 1001, 0, 1001);
INSERT INTO student_groups (fk_student, fk_group) VALUES (1001, 1001);

