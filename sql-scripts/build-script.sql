CREATE DATABASE ETF_DB;
USE ETF_DB;

CREATE TABLE basic_etf_data (
    `etf_symbol` varchar(4) NOT NULL,
    `description` varchar(400),
	PRIMARY KEY(`etf_symbol`)
);

CREATE TABLE top_ten_holdings (
	`etf_ref` varchar(4) NOT NULL,
	`company` varchar(50),
    `weight` decimal(5, 2),
    `shares` int
);

CREATE TABLE country_weights (
	`etf_ref` varchar(4) NOT NULL,
	`country_name` varchar(55),
	`country_weight` decimal(5, 2)
);

CREATE TABLE sector_weights (
	`etf_ref` varchar(4) NOT NULL,
	`sector_name` varchar(55),
	`sector_weight` decimal(5, 2)
);