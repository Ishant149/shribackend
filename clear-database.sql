-- Clear all data from MySQL database
USE tiffin_delivery;

SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

-- Delete in correct order (child tables first)
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM weekly_menu;
DELETE FROM meal_plans;
DELETE FROM users;

-- Reset auto increment
ALTER TABLE order_items AUTO_INCREMENT = 1;
ALTER TABLE orders AUTO_INCREMENT = 1;
ALTER TABLE weekly_menu AUTO_INCREMENT = 1;
ALTER TABLE meal_plans AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;