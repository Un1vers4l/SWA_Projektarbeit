INSERT INTO Account (userId, username, password)
VALUES (1,'student','264c8c381bf16c982a4e59b0dd4c6f7808c51a05f64c35db42cc78a2a72875bb'),
    (2,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918'),
    (3,'sekt','63870b1f068e4a136ad446e762d45f752de8ff74524bf7e458b495598f01c414');

INSERT INTO User_roles (userId, role)
VALUES (1, 0),
    (2, 1),
    (3, 2),
    (1, 3),
    (2, 3),
    (3, 3);

