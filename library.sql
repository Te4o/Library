--
-- Database: `library`
--
DROP DATABASE IF EXISTS `library`;
CREATE DATABASE `library` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `library`;

-- --------------------------------------------------------

--
-- Table structure for table `authors`
--
CREATE TABLE `authors` (
`author_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` VARCHAR(50) NOT NULL,
`country` VARCHAR(50) NOT NULL
) ENGINE = InnoDB CHARSET=utf8;

--
-- Sample data for table `author`
--
INSERT INTO `authors` (`author_id`, `name`, `country`) VALUES
(1, 'Ivan Vazov', 'Bulgaria'),
(2, 'Hristo Botev', 'Bulgaria'),
(3, 'Mario Puzo', 'USA'),
(4, 'Svetlin Nakov', 'Bulgaria'),
(5, 'Napoleon Hill', 'USA');

-- --------------------------------------------------------

--
-- Table structure for table `author_books`
--
CREATE TABLE `author_books` (
`book_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`author_id` BIGINT UNSIGNED NOT NULL,
`title` VARCHAR(50) NOT NULL,
`status` VARCHAR(30) NOT NULL,
`image` LONGBLOB,
`pages` INT(6) NOT NULL,
`book_description` TEXT NOT NULL,
CONSTRAINT `author_books_author` FOREIGN KEY (`author_id`)
REFERENCES `authors`(`author_id`) ON DELETE CASCADE
) ENGINE = InnoDB CHARSET=utf8;

--
-- Sample data for table `author_books`
--
INSERT INTO `author_books` (`book_id`, `author_id`, `title`, `status`, `pages`, `book_description`) VALUES
(1, 1, 'Pod Igoto', 'Available', 528, 'There is no description for this book yet'),
(2, 3, 'The Godfather','Available', 448, 'The story of Don Vito Corleone, the head of a New York Mafia family, inspired some of the most successful movies ever. It is in The Godfather of Mario Puzo  that Corleone first appears. As Corleone`s desperate struggle to control the Mafia underworld unfolds, so does the story of his family. The novel is full of exquisitely detailed characters who, despite leading unconventional lifestyles within a notorious crime family, experience the triumphs and failures of the human condition. Filled with the requisite valor, love, and rancor of a great epic, The Godfather is the definitive gangster novel.' ),
(3, 4, 'Introduction to programming with Java', 'Available', 909, 'The book “Introduction to Programming with Java” focuses on the fundamentals of computer programming, logical, algorithmical thinking, problem solving, data structures and algorithms. It uses a contemporary object-oriented approach and language but is valuable for any non-Java programmer in the begginning of his career. ' ),
(5, 3, 'The Sicilian', 'Available', 416 ,'There is no description for this book yet');

--
-- Table structure for table `users`
--
create table `users` (
   `user_id` varbinary(36) NOT NULL PRIMARY KEY,
   `username` VARCHAR(30) NOT NULL,
   `password` VARCHAR(60) NOT NULL,
   `first_name` VARCHAR(30) NOT NULL,
   `last_name`  VARCHAR(30) NOT NULL,
   `email` VARCHAR(30) NOT NULL,
   `role` VARCHAR(30) NOT NULL,
   `status` VARCHAR(30) NOT NULL,
   UNIQUE (username)
) ENGINE = InnoDB CHARSET=utf8;

--
-- Populate one Admin User: user/pass - admin/admin
--
INSERT INTO `users`(`user_id`, `username`, `password`, `first_name`, `last_name`, `email`, `role`, `status`)
VALUES (uuid(), 'admin','$2a$10$oMBvbuqBRgsamQYLVvXgsempbOV8d879sc.HyKYquJTxeGY1qNpCS', 'Adminec','Adminov','admin@abv.com', 'ADMIN', 'ACTIVE');

--
-- Table structure for table `book_ratings`
--
CREATE TABLE `book_ratings` (
`rating_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`rating` BIGINT UNSIGNED NOT NULL,
`book_id` BIGINT UNSIGNED NOT NULL,
`user_id` varbinary(36) NOT NULL,
CONSTRAINT `book_ratings_author_books` FOREIGN KEY (`book_id`) REFERENCES `author_books`(`book_id`) ON DELETE CASCADE,
CONSTRAINT `book_ratings_users` FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE CASCADE
) ENGINE = InnoDB CHARSET=utf8;


--
-- Table structure for table `messages`
--
CREATE TABLE `messages` (
	`message_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`header` VARCHAR(60) NOT NULL,
	`body` TEXT NOT NULL,
	`date` DATETIME NOT NULL DEFAULT NOW(),
	`sender_id` varbinary(36) NOT NULL,
	`receiver_id` varbinary(36) NOT NULL,
	`is_new` TINYINT(1) NOT NULL,
	`in_reply_to` INT(6) NOT NULL,
	CONSTRAINT `fk_sender_message` FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
	CONSTRAINT `fk_receiver_message` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE = InnoDB CHARSET=utf8;

--
-- Table structure for table `comments`
--
CREATE TABLE `comments` (
	`comment_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`book_id` BIGINT UNSIGNED NOT NULL,
	`user_id` varbinary(36) NOT NULL,
	`comment` VARCHAR(200) NOT NULL,
	CONSTRAINT `fk_book_comments` FOREIGN KEY (`book_id`) REFERENCES `author_books` (`book_id`) ON DELETE CASCADE,
	CONSTRAINT `fk_comments_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET=utf8;

--
-- Table structure for table `books_history`
--
CREATE table `books_history`(
	`book_history_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`book_id` BIGINT UNSIGNED NOT NULL,
	`user_id` varbinary(36) NOT NULL,
	`get_date` DATETIME NOT NULL DEFAULT NOW(),
	`return_date` DATETIME NOT NULL,
	`is_returned` TINYINT(1) NOT NULL,
	CONSTRAINT `fk_book_id` FOREIGN KEY (`book_id`) REFERENCES `author_books`(`book_id`) ON DELETE CASCADE,
	CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET=utf8;
