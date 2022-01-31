/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 08:47:13
 * @modify date 2022-01-31 08:47:13
 * @desc [description]
 */
 
INSERT INTO GROUPS (maxMembers, module_id, name, owner_matNr, type, groupId) VALUES (3, 1, 'SWA Lerngruppe', 1000, 0, 1000);
INSERT INTO student_groups (fk_student, fk_group) VALUES (1000, 1000);

INSERT INTO GROUPS (maxMembers, module_id, name, owner_matNr, type, groupId) VALUES (4, 3, 'Spiele programmieren', 1001, 0, 1001);
INSERT INTO student_groups (fk_student, fk_group) VALUES (1001, 1001);
