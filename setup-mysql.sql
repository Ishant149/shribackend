-- MySQL Database Setup for Tiffin Delivery App
-- Run this script in MySQL Workbench or command line

-- Create database
CREATE DATABASE IF NOT EXISTS tiffin_delivery;

-- Use the database
USE tiffin_delivery;

-- Create user (optional - you can use root)
-- CREATE USER 'tiffin_user'@'localhost' IDENTIFIED BY 'tiffin_password';
-- GRANT ALL PRIVILEGES ON tiffin_delivery.* TO 'tiffin_user'@'localhost';
-- FLUSH PRIVILEGES;

-- Database will be auto-created by Spring Boot with tables
-- No need to create tables manually as Hibernate will handle it