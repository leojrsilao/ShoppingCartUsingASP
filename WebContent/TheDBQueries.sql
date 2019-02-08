drop table users cascade constraints;
drop table USER_TYPE cascade constraints;
drop table ANNON_USER cascade constraints;
drop table ORDERS cascade constraints;
drop table ORDER_DETAIL cascade constraints;
drop table ADDRESS cascade constraints;
drop table ITEM cascade constraints;
drop table PAYMENT_METHOD cascade constraints;
drop table CART cascade constraints;
--drop table REGISTER cascade constraints;
drop sequence ID_SEQ;

CREATE SEQUENCE ID_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE users 
(
   user_id NUMBER(9) , 
   username VARCHAR2(20) NOT NULL UNIQUE, password VARCHAR2(20) NOT NULL, 
   user_type_id NUMBER(1) NOT NULL, 
   CONSTRAINT user_pk PRIMARY KEY(user_id)
);

CREATE TABLE user_type (user_type_id NUMBER(1), role VARCHAR2(20), CONSTRAINT user_pk_type PRIMARY KEY(user_type_id));

CREATE TABLE item (item_id NUMBER(10), name VARCHAR2(20),price NUMBER(7,2), item_description VARCHAR2(100), quantity NUMBER(5), CONSTRAINT item_id_pk PRIMARY KEY(item_id));

CREATE TABLE annon_user (annon_id NUMBER(9), annon_code VARCHAR2(20), dateLogged DATE, CONSTRAINT anon_id_pk PRIMARY KEY (annon_id));

CREATE TABLE orders (order_id NUMBER(10), user_id NUMBER(9), address NUMBER(9), order_date DATE, payment_id NUMBER(9) , CONSTRAINT order_id_pk PRIMARY KEY (order_id));



CREATE TABLE order_detail(order_detail_id NUMBER(9), item_id NUMBER(10), order_id NUMBER(10), quantity NUMBER(10), sales_price NUMBER(7,2) , CONSTRAINT order_detail_id_pk PRIMARY KEY (order_detail_id));

CREATE TABLE address (address NUMBER(9), first_name VARCHAR2(50), last_name VARCHAR2(50), city VARCHAR2(15), postal_code VARCHAR(7), province VARCHAR2(15), street VARCHAR2(50), country VARCHAR2(20), user_id NUMBER(9), CONSTRAINT address_pk PRIMARY KEY(address));

CREATE TABLE payment_method (payment_id NUMBER(9), user_id NUMBER(9), credit_type VARCHAR2(20), card_number NUMBER(12), security_code NUMBER(3), expiration_date DATE, address NUMBER(9), CONSTRAINT payment_id_pk PRIMARY KEY (payment_id));

CREATE TABLE cart (cart_id NUMBER(9), user_id NUMBER(9), item_id NUMBER(10),quantity NUMBER(5), annon_id VARCHAR2(90), CONSTRAINT car_id_pk PRIMARY KEY(cart_id));

ALTER TABLE users
ADD CONSTRAINT users_user_type_id_fk
  FOREIGN KEY (user_type_id)
  REFERENCES user_type(user_type_id);
  

ALTER TABLE payment_method
ADD CONSTRAINT payment_address_fk
  FOREIGN KEY (address)
  REFERENCES address(address);

ALTER TABLE orders
ADD CONSTRAINT order_user_id_fk
  FOREIGN KEY (user_id)
  REFERENCES users(user_id);
  
ALTER TABLE orders
ADD CONSTRAINT order_address_fk
  FOREIGN KEY (address)
  REFERENCES address(address);  
  
ALTER TABLE orders
ADD CONSTRAINT order_payment_id_fk
  FOREIGN KEY (payment_id)
  REFERENCES payment_method(payment_id);  
  
ALTER TABLE cart
ADD CONSTRAINT cart_user_id_fk
  FOREIGN KEY (user_id)
  REFERENCES users(user_id);  
  
ALTER TABLE cart
ADD CONSTRAINT cart_item_id_fk
  FOREIGN KEY (item_id)
  REFERENCES item(item_id);  
  
ALTER TABLE cart
ADD CONSTRAINT cart_annon_id_fk
  FOREIGN KEY (annon_id)
  REFERENCES annon_user(annon_id);  
  
ALTER TABLE order_detail
ADD CONSTRAINT order_detail_item_id_fk
  FOREIGN KEY (item_id)
  REFERENCES item(item_id);   
  
ALTER TABLE order_detail
ADD CONSTRAINT order_detail_order_id_fk
  FOREIGN KEY (order_id)
  REFERENCES orders(order_id);

INSERT INTO user_type (user_type_id, role) VALUES (1, 'admin');
INSERT INTO user_type (user_type_id, role) VALUES (2, 'role');
INSERT INTO users (user_id, username, password, user_type_id) VALUES (ID_SEQ.NEXTVAL, 'admin', 'admin', 1);

CREATE TABLE address (address NUMBER(9), first_name VARCHAR2(50), last_name VARCHAR2(50), city VARCHAR2(15), postal_code VARCHAR(7), province VARCHAR2(15), street VARCHAR2(50), country VARCHAR2(20), user_id NUMBER(9), CONSTRAINT address_pk PRIMARY KEY(address));


INSERT INTO item(item_id,name,price,item_description,quantity) VALUES (ID_SEQ.NEXTVAL, 'NIKE HAT', 12, 'nike hat small',50);
INSERT INTO item(item_id,name,price,item_description,quantity) VALUES (ID_SEQ.NEXTVAL, 'Tommy Hilfiger Shirt', 12, 'medium size',50);
INSERT INTO item(item_id,name,price,item_description,quantity) VALUES (ID_SEQ.NEXTVAL, 'Louis Vuitton', 12, 'large and medium',100);
INSERT INTO item(item_id,name,price,item_description,quantity) VALUES (ID_SEQ.NEXTVAL, 'Jeans', 12, 'nike hat medium',12);
INSERT INTO item(item_id,name,price,item_description,quantity) VALUES (ID_SEQ.NEXTVAL, 'Shits', 12, 'nike hat medium',52);
INSERT INTO item(item_id,name,price,item_description,quantity) VALUES (ID_SEQ.NEXTVAL, 'Hand-Bags', 12, 'nike hat medium',45);
INSERT INTO item(item_id,name,price,item_description,quantity) VALUES (ID_SEQ.NEXTVAL, 'Jacket', 12, 'nike hat medium',9);



SELECT * FROM users;
SELECT * FROM user_type;

