CREATE TABLE Dosing_Time(
   idDate INT,
   Date_hour DATETIME,
   taken BOOLEAN,
   PRIMARY KEY(idDate)
);

CREATE TABLE Tutor(
   mail_tutor VARCHAR(50),
   PRIMARY KEY(mail_tutor)
);

CREATE TABLE Medical_Appointments(
   id_appointment INT,
   date_appointment DATETIME,
   Name_doctor VARCHAR(50),
   Adress VARCHAR(50),
   PRIMARY KEY(id_appointment)
);

CREATE TABLE Treatment_list(
   idTreatment INT,
   Remaining_Days INT,
   Dosage VARCHAR(50),
   Name VARCHAR(50),
   idDate INT NOT NULL,
   PRIMARY KEY(idTreatment),
   FOREIGN KEY(idDate) REFERENCES Dosing_Time(idDate)
);

CREATE TABLE Users(
   id_user INT,
   mail VARCHAR(50) NOT NULL,
   password VARCHAR(50),
   id_appointment INT NOT NULL,
   mail_tutor VARCHAR(50) NOT NULL,
   idTreatment INT NOT NULL,
   PRIMARY KEY(id_user),
   FOREIGN KEY(id_appointment) REFERENCES Medical_Appointments(id_appointment),
   FOREIGN KEY(mail_tutor) REFERENCES Tutor(mail_tutor),
   FOREIGN KEY(idTreatment) REFERENCES Treatment_list(idTreatment)
);

INSERT INTO Dosing_Time (idDate,Date_hour,taken) VALUES 
('1','2022-03-10 11:30:00','1'),
('2','2022-03-10 15:30:00','0');

INSERT INTO Treatment_list (idTreatment,Remaining_Days,Dosage,Name,idDate) VALUES 
('1','7','50 mL','paracétamol','1'),
('2','30','1 comprimé','aerius','2');


/*To get the list of treatments of the user*/
/*
SELECT  Treatment_list.Name,Treatment_list.Remaining_Days , Treatment_list.Dosage, Dosing_Time.Date_hour, Dosing_Time.taken
FROM Treatment_list INNER JOIN Dosing_Time ON Treatment_list.idDate = Dosing_Time.idDate ;*/

/*To get the list of appointments*/



/*To get tutor's info*/

commit;