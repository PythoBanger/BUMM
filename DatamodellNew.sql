DROP TABLE BummUser CASCADE CONSTRAINTS;
DROP TABLE ImageCallery CASCADE CONSTRAINTS;
DROP TABLE Article CASCADE CONSTRAINTS;
DROP TABLE Category CASCADE CONSTRAINTS;
DROP TABLE Rating CASCADE CONSTRAINTS;
DROP TABLE RatingReport CASCADE CONSTRAINTS;
DROP TABLE BummOrder CASCADE CONSTRAINTS;
DROP TABLE ShoppingList CASCADE CONSTRAINTS;

DROP SEQUENCE seqArticle;
DROP SEQUENCE seqOrder;

CREATE TABLE BummUser (
  username VARCHAR2(50) PRIMARY KEY,
  password VARCHAR2(100),
  firstname VARCHAR2(50),
  lastname VARCHAR2(50),
  email VARCHAR(50) UNIQUE,
  birthdate DATE,
  location VARCHAR2(50),
  zipcode INTEGER,
  address VARCHAR2(50),
  role VARCHAR2(50),
  profilePic BLOB,
  status VARCHAR2(50),
  CONSTRAINT check_role1 CHECK (role is not null and (role = 'admin' or role = 'customer')),
  CONSTRAINT check_status1 CHECK (status is not null and (status = 'active' or status='deactive') )
);

CREATE TABLE Category(
  curCategory VARCHAR2(50) PRIMARY KEY,
  parentCategory VARCHAR2(50),
  CONSTRAINT check_cat CHECK (curCategory is not null),
  CONSTRAINT fk_category1 FOREIGN KEY(parentCategory) REFERENCES Category(curCategory)
);

CREATE SEQUENCE seqArticle START WITH 1 INCREMENT BY 1;

CREATE TABLE Article(
  artNr INTEGER,
  name VARCHAR2(50), /*muss nicht eindeutig sein. es können ja mehrere unterschiedliche z.b Iphone4 verkauft werden. */
  description VARCHAR2(50),
  price FLOAT, 
  onStock INTEGER,
  artCategory VARCHAR2(50),
  CONSTRAINT pk_article PRIMARY KEY(artNr),
  CONSTRAINT check_onStock CHECK (onStock >-1 ), /*can be smaller than 0 article*/
  CONSTRAINT fk_artCategory FOREIGN KEY(artCategory) REFERENCES Category(curCategory) /*article darf nur in der untersten category sein*/
);


CREATE TABLE ImageCallery(
  artNr INTEGER,
  image BLOB,
  CONSTRAINT fk_gallery FOREIGN KEY(artNr) REFERENCES Article(artNr)
);

CREATE TABLE Rating(
  artNr INTEGER,
  username VARCHAR2(50),
  ratingDate DATE,
  ratingComment VARCHAR2(50),
  ratingValue INTEGER,
  CONSTRAINT pk_Rating PRIMARY KEY (artNr,username), /*user kann ein artikel nur einmal bewerten*/
  CONSTRAINT fk_BU FOREIGN KEY(username) REFERENCES BummUser(username),
  CONSTRAINT fk_Art FOREIGN KEY(artNr) REFERENCES Article(artNr),
  CONSTRAINT check_rating CHECK (ratingValue > 0 AND ratingValue < 6 )
);
CREATE TABLE RatingReport(
  artNr INTEGER,
  reportedUser VARCHAR2(50),
  userWhoReported VARCHAR(50),
  reportDate DATE,
  CONSTRAINT pk_RatingV8 PRIMARY KEY (artNr,reportedUser,userWhoReported), /*user kann ein rating(kommentar) nur einmal melden*/
  CONSTRAINT fk_ArV7t FOREIGN KEY(artNr,reportedUser) REFERENCES Rating(artNr,username)
);

CREATE SEQUENCE seqOrder START WITH 1 INCREMENT BY 1;
CREATE TABLE BummOrder( 
  orderId INTEGER,
  username VARCHAR2(50),
  artNr INTEGER,
  amount INTEGER,
  CONSTRAINT pk_Ord PRIMARY KEY(orderId,username,artNr),
  CONSTRAINT fk_BU4 FOREIGN KEY(username) REFERENCES BummUser(username),
  CONSTRAINT fk_Art4 FOREIGN KEY(artNr) REFERENCES Article(artNr)
);

CREATE TABLE ShoppingList(
  username VARCHAR2(50),
  artNr INTEGER,
  CONSTRAINT pk_slist PRIMARY KEY(username,artNr),
  CONSTRAINT fk_BU5 FOREIGN KEY(username) REFERENCES BummUser(username),
  CONSTRAINT fk_Art5 FOREIGN KEY(artNr) REFERENCES Article(artNr)
);

INSERT INTO BummUser VALUES ('admin','nimda',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'admin',NULL,'active');
INSERT INTO BummUser VALUES ('pesso','nim',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'customer',NULL,'active');
INSERT INTO ShoppingList VALUES('pesso',3);
INSERT INTO Category VALUES('Alle Artikel',NULL); 

INSERT INTO Category VALUES('Technische Geräte','Alle Artikel'); 
INSERT INTO Category VALUES('Kleidung','Alle Artikel');

INSERT INTO Category VALUES('Handy', 'Technische Geräte');
INSERT INTO Category VALUES('Laptop', 'Technische Geräte');
INSERT INTO Category VALUES('Hosen', 'Kleidung');
INSERT INTO Category VALUES('T-Shirt', 'Kleidung');

INSERT INTO Article VALUES(seqArticle.NEXTVAL,'iPhone 4','Ein iPhone 4', 1234.45,34,'Handy');
INSERT INTO Article VALUES(seqArticle.NEXTVAL,'Toshiba','Ein Toshiba Biva', 500,78,'Laptop');
INSERT INTO Article VALUES(seqArticle.NEXTVAL,'overratedes Polo','Polo Shirt', 23.99,7,'T-Shirt');
INSERT INTO Article VALUES(seqArticle.NEXTVAL,'Calvon Klein Hose','Ein Calvin Klein Hose', 500,78,'Hosen');

COMMIT;


create or replace view allCatWithArt as (select c.parentcategory parent, c.curcategory child, a.ARTNR as artNr, a.NAME as artName, a.DESCRIPTION as artDesc, a.PRICE as artPrice, a.ONSTOCK as artOnStock 
       from category c  left join article a on a.ARTCATEGORY=c.CURCATEGORY
      
      );
      
     select * from allCatWithArt; 
      
    select *
    from allCatWithArt where artName like '%overratedes Polo%'
   start with child = 'Kleidung'
   connect by prior child = parent
   order siblings by child;
      
      select * from Category 
      start with curCategory='Alle Artikel' 
   connect by prior curCategory = parentCategory
   order siblings by parentCategory;
