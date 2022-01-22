/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 19:22:56
 * @modify date 2022-01-22 19:22:56
 * @desc [description]
 */


INSERT INTO adress(nr, street, town, zipCode, id) VALUES (12, 'Eisenbahnstrasse', 'Osnabrueck', 49074, 1);
INSERT INTO adress(nr, street, town, zipCode, id) VALUES (46, 'Zur Werseaue', 'Sendenhorst', 48324, 2);
INSERT INTO students(adress_id, email, name, matNr) VALUES (1, 'joana.wegener@hs-osnabrueck.de', 'Joana Wegener', 855518);
INSERT INTO students(adress_id, email, name, matNr) VALUES (1, 'nina.chuba@hs-osnabrueck.de', 'Nina Chuba', 9999);
INSERT INTO MockModule(name, id) VALUES ('SWA', 1);
INSERT INTO MockModule(name, id) VALUES ('Webanwendungen', 2);
INSERT INTO MockModule(name, id) VALUES ('Spieleprogrammierung', 3);