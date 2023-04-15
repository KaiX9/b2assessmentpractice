CREATE DATABASE IF NOT EXISTS bgg;

USE bgg;

CREATE TABLE IF NOT EXISTS user (
    user_id VARCHAR(64) NOT NULL PRIMARY KEY, 
    username VARCHAR(255) NOT NULL,
    UNIQUE (username),
    name VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS task (
    task_id INT AUTO_INCREMENT NOT NULL,
    description VARCHAR(255) NOT NULL,
    priority INT,
    CHECK (priority >= 1 AND priority <= 3),
    due_date DATE NOT NULL,
    user_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (task_id),
    CONSTRAINT fk_user_id
    FOREIGN KEY(user_id) REFERENCES user(user_id)
    );