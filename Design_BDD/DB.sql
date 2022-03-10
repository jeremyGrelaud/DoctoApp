CREATE TABLE Dosing_Time(
   idDate INT,
   Date_hour DATETIME,
   taken BOOLEAN,
   PRIMARY KEY(idDate)
);

CREATE TABLE Tutor(
   mail_tutor VARCHAR(50),
   Name VARCHAR(50),
   PRIMARY KEY(mail_tutor)
);

CREATE TABLE Users(
   id_user INT,
   mail VARCHAR(50) NOT NULL,
   password VARCHAR(50),
   mail_tutor VARCHAR(50) NOT NULL,
   PRIMARY KEY(id_user),
   FOREIGN KEY(mail_tutor) REFERENCES Tutor(mail_tutor)
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

CREATE TABLE Medical_Appointments(
   id_appointment INT,
   date_appointment DATETIME,
   Name_doctor VARCHAR(50),
   Adress VARCHAR(50),
   id_user INT NOT NULL,
   PRIMARY KEY(id_appointment),
   FOREIGN KEY(id_user) REFERENCES Users(id_user)
);

INSERT INTO Dosing_Time (idDate,Date_hour,taken) VALUES 
('1','2022-03-10 11:30:00','1'),
('2','2022-03-10 15:30:00','0');

INSERT INTO Tutor (mail_tutor, Name) VALUES 
('j.g@gmail.com','Jérémy GRELAUD');

INSERT INTO Users (id_user,mail,password,mail_tutor) VALUES 
('1','gui@gmail;com','1234','j.g@gmail.com');

INSERT INTO Treatment_list (idTreatment,Remaining_Days,Dosage,Name,id_user,idDate) VALUES 
('1','7','50 mL','paracétamol','1','1'),
('2','30','1 comprimé','aerius','1','2');

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
FROM Users INNER JOIN Tutor ON Tutor.mail_tutor = Users.mail_tutor
WHERE id_user='1';

commit;