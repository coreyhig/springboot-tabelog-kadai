 DROP TABLE IF EXISTS verification_tokens;
 DROP TABLE IF EXISTS reservations;
 DROP TABLE IF EXISTS store; 
 
 CREATE TABLE IF NOT EXISTS store (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(50) NOT NULL,  
     image_name VARCHAR(255),
     description VARCHAR(255) NOT NULL,
     price INT NOT NULL,
     capacity INT NOT NULL,
     postal_code VARCHAR(50) NOT NULL,
     address VARCHAR(255) NOT NULL,
     phone_number VARCHAR(50) NOT NULL,
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
 );

 CREATE TABLE IF NOT EXISTS roles (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(50) NOT NULL
 );
 
 CREATE TABLE IF NOT EXISTS users (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(50) NOT NULL,
     furigana VARCHAR(50) NOT NULL,
     postal_code VARCHAR(50) NOT NULL,
     address VARCHAR(255) NOT NULL,
     phone_number VARCHAR(50) NOT NULL,
     email VARCHAR(255) NOT NULL UNIQUE,
     password VARCHAR(255) NOT NULL,    
     role_id INT NOT NULL, 
     enabled BOOLEAN NOT NULL,
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,    
     FOREIGN KEY (role_id) REFERENCES roles (id)
 );
 
 CREATE TABLE IF NOT EXISTS verification_tokens (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     user_id INT NOT NULL UNIQUE,
     token VARCHAR(255) NOT NULL,        
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (user_id) REFERENCES users (id) 
 );
 
  CREATE TABLE IF NOT EXISTS reservations (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     store_id INT NOT NULL,
     user_id INT NOT NULL,
     checkin_date DATE NOT NULL,
     checkin_time TIME  NOT NULL,
     number_of_people INT NOT NULL,
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (store_id) REFERENCES store (id),
     FOREIGN KEY (user_id) REFERENCES users (id)
 );