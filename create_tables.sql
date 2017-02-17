#SET FOREIGN_KEY_CHECKS=0;

-- Tables removal
DROP TABLE IF EXISTS VCRs;
DROP TABLE IF EXISTS Students;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS GEK;
DROP TABLE IF EXISTS Date;


-- Tables creation

CREATE TABLE Courses
(
  course_code VARCHAR(30) PRIMARY KEY NOT NULL,
  course_name VARCHAR(60) NOT NULL,
  course_specialization VARCHAR(60) NOT NULL
) CHARACTER SET utf8;

CREATE TABLE Students
(
  student_id INT PRIMARY KEY NOT NULL,
  first_name VARCHAR(60) NOT NULL,
  last_name VARCHAR(60) NOT NULL,
  middle_name VARCHAR(60) NOT NULL,
  qualification VARCHAR(60) NOT NULL,
  course_code VARCHAR(30),
  FOREIGN KEY(course_code) REFERENCES Courses(course_code) ON DELETE CASCADE
) CHARACTER SET utf8;

CREATE TABLE VCRs
(
  student_id INT NOT NULL,
  vcr_name VARCHAR(100) NOT NULL PRIMARY KEY,
  vcr_head VARCHAR(60) NOT NULL,
  vcr_reviewer VARCHAR(60) NOT NULL,
  FOREIGN KEY(student_id) REFERENCES Students(student_id) ON DELETE CASCADE
) CHARACTER SET utf8;

CREATE TABLE GEK
(
  gek_head VARCHAR(60) NOT NULL PRIMARY KEY,
  gek_subhead VARCHAR(60) NOT NULL,
  gek_secretary VARCHAR(60) NOT NULL,
  gek_member1 VARCHAR(60) NOT NULL,
  gek_member2 VARCHAR(60) NOT NULL,
  gek_member3 VARCHAR(60) NOT NULL,
  gek_member4 VARCHAR(60) NOT NULL,
  gek_member5 VARCHAR(60) NOT NULL,
  gek_member6 VARCHAR(60) NOT NULL
) CHARACTER SET utf8;

CREATE TABLE Date
(
  day INT NOT NULL,
  month VARCHAR(20) NOT NULL,
  year INT NOT NULL PRIMARY KEY
) CHARACTER SET utf8;


-- Insertions in tables

INSERT INTO Courses VALUES('11.11.11.11', 'Б-И', 'Б-И в сфере ИТ');
INSERT INTO Students VALUES(1, 'Иван', 'Иванов', 'Иванович', 'бакалавр', '11.11.11.11');
INSERT INTO VCRs VALUES(1, 'выпускная квалификационная работа', 'Руководитель Р.Р.', 'Ревьювер Р.Р');
INSERT INTO GEK VALUES('Главный Г.Г.', 'Заместитель З.З.', 'Секретарь С.С.',
                       'Первый', 'Второй', 'Третий', 'Четвертый', 'Пятый', 'Шестой');
INSERT INTO Date VALUES(12, 'апреля', 15);



















