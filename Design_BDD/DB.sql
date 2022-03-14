CREATE TABLE Users(
   id_user INT,
   mail VARCHAR(50) NOT NULL,
   password VARCHAR(50),
   PRIMARY KEY(id_user)
);

CREATE TABLE Dosing_Time(
   idDate INT,
   Date_hour DATETIME,
   taken BOOLEAN,
   PRIMARY KEY(idDate)
);

CREATE TABLE Tutor(
   mail_tutor VARCHAR(50),
   Name VARCHAR(50),
   id_user INT NOT NULL,
   PRIMARY KEY(mail_tutor),
   FOREIGN KEY(id_user) REFERENCES Users(id_user)
);

CREATE TABLE Medical_Appointments(
   id_appointment INT,
   date_appointment DATETIME,
   Name_doctor VARCHAR(50),
   Adress VARCHAR(50),
   id_user INT NOT NULL,
   PRIMARY KEY(id_appointment),
   FOREIGN KEY(id_user) REFERENCES Users(id_user)
);

CREATE TABLE Treatment_list(
   idTreatment INT,
   Remaining_Days INT,
   Dosage VARCHAR(50),
   Name VARCHAR(50),
   id_user INT NOT NULL,
   idDate INT NOT NULL,
   PRIMARY KEY(idTreatment),
   FOREIGN KEY(id_user) REFERENCES Users(id_user),
   FOREIGN KEY(idDate) REFERENCES Dosing_Time(idDate)
);

INSERT INTO Users (id_user,mail,password) VALUES 
('1','gui@gmail.com','1234');

INSERT INTO Dosing_Time (idDate,Date_hour,taken) VALUES 
('1','2022-03-11 11:30:00','1'),
('2','2022-03-11 15:30:00','0'),
('3','2022-03-15 15:30:00','0');

INSERT INTO Tutor (mail_tutor, Name, id_user) VALUES 
('j.g@gmail.com','Jérémy GRELAUD','1');


INSERT INTO Treatment_list (idTreatment,Remaining_Days,Dosage,Name,id_user,idDate) VALUES 
('1','7','50 mL','paracétamol','1','1'),
('2','30','1 comprimé','aerius','1','2'),
('3','2','2 comprimés','spasfond','1','3');

INSERT INTO Medical_Appointments (id_appointment,date_appointment,Name_doctor,Adress,id_user) VALUES
('1','2022-04-15 16:50:00','Perrin','5 rue de la Bergerie Poitier','1'),
('2','2022-03-18 17:00:00','Perrin','5 rue de la Bergerie Poitier','1');

/*To get the id of the user who just connected with his mail and his password*/
SELECT id_user 
FROM Users
WHERE mail='example' AND password='example';


/*To get the list of treatments of the user*/
SELECT  Treatment_list.Name,Treatment_list.Remaining_Days , Treatment_list.Dosage, Dosing_Time.Date_hour, Dosing_Time.taken
FROM Treatment_list INNER JOIN Dosing_Time ON Treatment_list.idDate = Dosing_Time.idDate
WHERE id_user='1';

/*each time the id_user is the id of the user who opened the session of the application*/

/*To get the list of appointments*/
SELECT date_appointment, Name_doctor, Adress
FROM Medical_Appointments
WHERE id_user='1';

/*get the next appointment*/
SELECT date_appointment,  Name_doctor
FROM Medical_Appointments
WHERE id_user='1' AND date_appointment >= CURRENT_TIMESTAMP()
ORDER BY date_appointment ASC LIMIT 1;

/*To get tutor's info*/
SELECT Tutor.mail_tutor, Tutor.Name
FROM Users INNER JOIN Tutor ON Tutor.id_user = Users.id_user
WHERE Users.id_user='1';



/*get all the hours of todays treatments*/
SELECT Remaining_Days, Dosage, Name,  Date_hour
FROM Treatment_list INNER JOIN Dosing_Time ON Treatment_list.idDate = Dosing_Time.idDate
WHERE id_user='1' AND Date_hour >= Date(now()) AND Date_hour <= DATE_ADD(CURDATE(),INTERVAL 1 DAY)
ORDER BY Date_hour ASC;


/*SELECT DATE_ADD(CURDATE(),INTERVAL 1 DAY) AS tomorrow;*/


SELECT id_user, password
FROM users
WHERE mail='gui@gmail.com';

SELECT max(id_user)
FROM users;


/*Query to modify tutor datas from the database*/
UPDATE Tutor 
SET mail_tutor = 'j.g@gmail.com', Name = 'Jérémy GRELAUD'
WHERE id_user = '1';
    
commit;