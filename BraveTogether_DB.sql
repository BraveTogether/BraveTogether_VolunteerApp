CREATE TABLE `user_types` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `description` varchar(255)
);

CREATE TABLE `weekdays` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `day` varchar(255)
);

CREATE TABLE `time_windows` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `start_hour` timestamp,
  `end_hour` timestamp
);

CREATE TABLE `volunteer_location_pref` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255)
);

CREATE TABLE `users` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255),
  `first_name` varchar(255),
  `last_name` varchar(255),
  `created_at` timestamp
);

CREATE TABLE `users_profile` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `uid` varchar(255),
  `phone_number` varchar(255),
  `home_address` varchar(255),
  `about` varchar(255),
  `user_type` int,
  `profile_picture` varchar(255),
  `coins` bigint DEFAULT 0
);

CREATE TABLE `users_notifications` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `uid` bigint,
  `notification_address` varchar(255),
  `notify_on_date` boolean,
  `notification_location_pref_id` bigint
);

CREATE TABLE `users_notifications_dates` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `users_notifications_id` bigint,
  `day` int,
  `time_window_id` int
);

CREATE TABLE `volunteer_events` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `manager` bigint,
  `picture` varchar(255),
  `address` varchar(255),
  `online` boolean,
  `duration` int,
  `about_place` varchar(255),
  `about_volunteering` varchar(255),
  `min_volunteer` int DEFAULT 0,
  `max_volunteers` int DEFAULT 0,
  `value_in_coins` int,
  `confirmed` Boolean
);

CREATE TABLE `volunteer_events_dates` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `vid` bigint,
  `date` datetime
);

CREATE TABLE `users_volunteerings_events` (
  `uid` bigint,
  `vid` bigint,
  `Confirmed` Boolean
);

CREATE TABLE `reviews` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `uid` bigint,
  `vid` bigint,
  `review_text` varchar(255),
  `picture` varchar(255)
);

ALTER TABLE `users_profile` ADD FOREIGN KEY (`uid`) REFERENCES `users` (`id`);

ALTER TABLE `users_profile` ADD FOREIGN KEY (`user_type`) REFERENCES `user_types` (`id`);

ALTER TABLE `users_notifications` ADD FOREIGN KEY (`uid`) REFERENCES `users` (`id`);

ALTER TABLE `users_notifications` ADD FOREIGN KEY (`notification_location_pref_id`) REFERENCES `volunteer_location_pref` (`id`);

ALTER TABLE `users_notifications_dates` ADD FOREIGN KEY (`users_notifications_id`) REFERENCES `users_notifications` (`id`);

ALTER TABLE `users_notifications_dates` ADD FOREIGN KEY (`day`) REFERENCES `weekdays` (`id`);

ALTER TABLE `users_notifications_dates` ADD FOREIGN KEY (`time_window_id`) REFERENCES `time_windows` (`id`);

ALTER TABLE `volunteer_events` ADD FOREIGN KEY (`manager`) REFERENCES `users` (`id`);

ALTER TABLE `volunteer_events_dates` ADD FOREIGN KEY (`vid`) REFERENCES `volunteer_events` (`id`);

ALTER TABLE `users_volunteerings_events` ADD FOREIGN KEY (`uid`) REFERENCES `users` (`id`);

ALTER TABLE `users_volunteerings_events` ADD FOREIGN KEY (`vid`) REFERENCES `volunteer_events` (`id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`uid`) REFERENCES `users` (`id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`vid`) REFERENCES `volunteer_events` (`id`);
