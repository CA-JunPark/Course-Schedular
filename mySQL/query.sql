create table STUDENT (STUDENT_ID int primary key, F_NAME varchar(12), L_NAME varchar(12), DOB DATE, GENDER char(1));

insert into STUDENT values (617515, 'Jun', 'Park', str_to_date('November 19 1999', '%M %d %Y'), 'M');
insert into STUDENT values (615391, 'Isaac', 'Shoma', str_to_date('June 23 2001', '%M %d %Y'), 'M');
insert into STUDENT values (601024, 'Mark', 'Albin', str_to_date('May 11 1999', '%M %d %Y'), 'M');
insert into STUDENT values (642341, 'Robert', 'Einstein', str_to_date('March 14 1997', '%M %d %Y'), 'M');
insert into STUDENT values (341674, 'Vasa', 'Ramanujan', str_to_date('December 22 1996', '%M %d %Y'), 'M');
insert into STUDENT values (135678, 'Look', 'Nash', str_to_date('June 13 2000', '%M %d %Y'), 'M');


