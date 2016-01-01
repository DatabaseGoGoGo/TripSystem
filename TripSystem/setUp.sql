/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/12/13 Sophia PM 03:12:30                */
/*==============================================================*/

SET FOREIGN_KEY_CHECKS = 0;

drop table if exists admin;

drop table if exists application;

drop table if exists assign;

drop table if exists develop;

drop table if exists developer;

drop table if exists manager;

drop table if exists project;

drop table if exists rejectionLog;

drop table if exists salesman;

drop table if exists trip;

drop table if exists tripRecord;

drop table if exists user;

SET FOREIGN_KEY_CHECKS = 1;

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   userID               char(20) not null,
   primary key (userID)
);

/*==============================================================*/
/* Table: application                                           */
/*==============================================================*/
create table application
(
   applicationID        decimal not null,
   projectID            decimal not null,
   userID               char(20) not null,
   state                decimal not null,
   primary key (applicationID)
);

/*==============================================================*/
/* Table: assign                                                */
/*==============================================================*/
create table assign
(
   applicationID        decimal not null,
   userID               char(20) not null,
   state                char(10),
   primary key (applicationID, userID)
);

/*==============================================================*/
/* Table: develop                                               */
/*==============================================================*/
create table develop
(
   projectID            decimal not null,
   userID               char(20) not null,
   primary key (projectID, userID)
);

/*==============================================================*/
/* Table: developer                                             */
/*==============================================================*/
create table developer
(
   userID               char(20) not null,
   primary key (userID)
);

/*==============================================================*/
/* Table: manager                                               */
/*==============================================================*/
create table manager
(
   userID               char(20) not null,
   primary key (userID)
);

/*==============================================================*/
/* Table: project                                               */
/*==============================================================*/
create table project
(
   projectID            decimal not null,
   userID               char(20) not null,
   projectName          char(20) not null,
   projectDescription   char(200) not null,
   primary key (projectID)
);

/*==============================================================*/
/* Table: rejectionLog                                          */
/*==============================================================*/
create table rejectionLog
(
   rejectionID          decimal not null,
   applicationID        decimal not null,
   rejectReason         char(200),
   rejectTime           datetime,
   primary key (rejectionID)
);

/*==============================================================*/
/* Table: salesman                                              */
/*==============================================================*/
create table salesman
(
   userID               char(20) not null,
   primary key (userID)
);

/*==============================================================*/
/* Table: trip                                                  */
/*==============================================================*/
create table trip
(
   tripID               decimal not null,
   applicationID        decimal not null,
   departTime           date not null,
   groupNumber          decimal not null,
   days                 decimal not null,
   description          char(200) not null,
   primary key (tripID)
);

/*==============================================================*/
/* Table: tripRecord                                            */
/*==============================================================*/
create table tripRecord
(
   tripID               decimal not null,
   userID               char(20) not null,
   actualDepartTime     date,
   actualTripDays       decimal,
   tripContent          char(200),
   primary key (tripID, userID)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   userID               char(20) not null,
   userName             char(10) not null,
   role					decimal,
   password             char(15) not null,
   primary key (userID)
);

alter table admin add constraint FK_Inheritance_4 foreign key (userID)
      references user (userID) on delete restrict on update restrict;

alter table application add constraint FK_apply foreign key (userID)
      references salesman (userID) on delete restrict on update restrict;

alter table application add constraint FK_project_application foreign key (projectID)
      references project (projectID) on delete restrict on update restrict;

alter table assign add constraint FK_assign foreign key (applicationID)
      references application (applicationID) on delete restrict on update restrict;

alter table assign add constraint FK_assign2 foreign key (userID)
      references developer (userID) on delete restrict on update restrict;

alter table develop add constraint FK_develop foreign key (projectID)
      references project (projectID) on delete restrict on update restrict;

alter table develop add constraint FK_develop2 foreign key (userID)
      references developer (userID) on delete restrict on update restrict;

alter table developer add constraint FK_Inheritance_2 foreign key (userID)
      references user (userID) on delete restrict on update restrict;

alter table manager add constraint FK_Inheritance_1 foreign key (userID)
      references user (userID) on delete restrict on update restrict;

alter table project add constraint FK_manage foreign key (userID)
      references manager (userID) on delete restrict on update restrict;

alter table rejectionLog add constraint FK_reject foreign key (applicationID)
      references application (applicationID) on delete restrict on update restrict;

alter table salesman add constraint FK_Inheritance_3 foreign key (userID)
      references user (userID) on delete restrict on update restrict;

alter table trip add constraint FK_generate1 foreign key (applicationID)
      references application (applicationID) on delete restrict on update restrict;

alter table tripRecord add constraint FK_tripRecord foreign key (tripID)
      references trip (tripID) on delete restrict on update restrict;

alter table tripRecord add constraint FK_tripRecord2 foreign key (userID)
      references developer (userID) on delete restrict on update restrict;

