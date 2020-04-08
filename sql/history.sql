-- --------------------------------------------------------
-- Rps History Database
-- --------------------------------------------------------

CREATE DATABASE IF NOT EXISTS `history`;
USE `history`;

DROP TABLE IF EXISTS `json_entry`;
CREATE TABLE IF NOT EXISTS `json_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attr` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`attr`)),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO `json_entry` (`id`, `attr`) VALUES
	(1, '{"result":"You Win!","comp":"Scissors","round":"1","userScore":"1","compScore":"0","user":"Rock"} '),
	(2, '{"result":"You Win!","comp":"Scissors","round":"2","userScore":"1","compScore":"0","user":"Rock"} ');

