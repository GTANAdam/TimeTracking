CREATE DATABASE IF NOT EXISTS `time_tracking_db`

USE `time_tracking_db`

CREATE TABLE IF NOT EXISTS `activities` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `status` enum('Awaiting Approval','Approved','Awaiting Deletion') NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` enum('user','admin') DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

INSERT INTO `users` (`id`, `name`, `email`, `password`, `role`) VALUES (1, 'admin', 'admin@localhost', 'admin', 'admin');

ALTER TABLE `activities` ADD PRIMARY KEY (`id`);
ALTER TABLE `activities` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `users` ADD PRIMARY KEY (`id`);
ALTER TABLE `users` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;