CREATE TABLE address (
	`id` INT NOT NULL AUTO_INCREMENT,
	`street_name` VARCHAR(100) NOT NULL,
	`number` VARCHAR(10) NOT NULL,
	`complement` VARCHAR(30),
	`neighbourhood` VARCHAR(100) NOT NULL,
	`city` VARCHAR(100) NOT NULL,
	`state` VARCHAR(100) NOT NULL,
	`country` VARCHAR(100) NOT NULL,
	`zip_code` VARCHAR(9) NOT NULL,
	`latitude` DECIMAL(10, 8),
	`longitude` DECIMAL(11, 8),
	PRIMARY KEY (id)
);