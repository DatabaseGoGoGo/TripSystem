insert sell(userID, projectID) values(
(select userID from salesman where userID = '2015110005'),
(select projectID from project where projectID = 2015120001));

insert sell(userID, projectID) values(
(select userID from salesman where userID = '2015110005'),
(select projectID from project where projectID = 2015120002));

insert sell(userID, projectID) values(
(select userID from salesman where userID = '2015110012'),
(select projectID from project where projectID = 2015120006));