DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_idx`	int unsigned AUTO_INCREMENT PRIMARY KEY	NOT NULL,
                        `oauth_id`	TEXT	NOT NULL,
                        `nickname`	varchar(50)	NOT NULL,
                        `photo`	TEXT	NULL,
                        `isMale`	bool	NULL,
                        `birth`	date	NULL,
                        `active`	bool	NULL	DEFAULT true,
                        `created_at`	timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                        `updated_at`	timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `ott`;

CREATE TABLE `ott` (
                       `ottIdx`	int unsigned AUTO_INCREMENT PRIMARY KEY	NOT NULL,
                       `name`	varchar(50)	NOT NULL,
                       `photo`	TEXT	NULL
);

DROP TABLE IF EXISTS `genre`;

CREATE TABLE `genre` (
                         `genreIdx`	int unsigned AUTO_INCREMENT PRIMARY KEY	NOT NULL,
                         `genreName`	varchar(50)	NOT NULL
);

DROP TABLE IF EXISTS `movie`;

CREATE TABLE `movie` (
                         `movie_Idx`	int unsigned AUTO_INCREMENT PRIMARY KEY	NOT NULL,
                         `movie_title`	varchar(50)	NULL,
                         `release_year`	int	NULL,
                         `synopsis`	TEXT	NULL,
                         `director`	varchar(50)	NULL
);

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
                          `review_idx`	int unsigned AUTO_INCREMENT PRIMARY KEY	NOT NULL,
                          `movie_Idx`	int  unsigned 	NOT NULL,
                          `user_idx`	int  unsigned 	NOT NULL,
                          `contents`	TEXT	NULL,
                          `likeCnt`	int	NULL	DEFAULT 0,
                          `active`	bool	NULL	DEFAULT true,
                          `created_at`	timestamp	NULL	DEFAULT CURRENT_TIMESTAMP,
                          `updated_at`	timestamp	NULL	DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `keyword`;

CREATE TABLE `keyword` (
                           `keyword_idx`	int unsigned AUTO_INCREMENT PRIMARY KEY	NOT NULL,
                           `keyword`	varchar(30)	NULL
);

DROP TABLE IF EXISTS `keyword-movie`;

CREATE TABLE `keyword-movie` (
                                 `movie_Idx`	int  unsigned	NOT NULL,
                                 `keyword_idx`	int  unsigned 	NOT NULL
);

DROP TABLE IF EXISTS `movie-genre`;

CREATE TABLE `movie-genre` (
                               `movie_Idx`	int  unsigned 	NOT NULL,
                               `genreIdx`	int  unsigned 	NOT NULL
);

DROP TABLE IF EXISTS `movie-ott`;

CREATE TABLE `movie-ott` (
                             `ottIdx`	int  unsigned 	NOT NULL,
                             `movie_Idx`	int  unsigned 	NOT NULL
);

DROP TABLE IF EXISTS `user-ott`;

CREATE TABLE `user-ott` (
                            `ottIdx`	int  unsigned 	NOT NULL,
                            `user_idx`	int  unsigned 	NOT NULL
);

DROP TABLE IF EXISTS `user-genre`;

CREATE TABLE `user-genre` (
                              `user_idx`	int  unsigned 	NOT NULL,
                              `genreIdx`	int  unsigned 	NOT NULL
);

DROP TABLE IF EXISTS `user-movie`;

CREATE TABLE `user-movie` (
                              `user_idx`	int  unsigned 	NOT NULL,
                              `movie_Idx`	int  unsigned 	NOT NULL
);

DROP TABLE IF EXISTS `user-review`;

CREATE TABLE `user-review` (
                               `user_idx`	int  unsigned 	NOT NULL,
                               `review_idx`	int  unsigned 	NOT NULL
);

DROP TABLE IF EXISTS `movie_photo`;

CREATE TABLE `movie_photo` (
                               `movie_Idx`	int  unsigned 	NOT NULL,
                               `photo`	TEXT	NULL
);

DROP TABLE IF EXISTS `actor`;

CREATE TABLE `actor` (
                         `movie_Idx`	int  unsigned 	NOT NULL,
                         `actor_name`	varchar(50)	NULL
);

DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
                          `report_idx`	int unsigned AUTO_INCREMENT PRIMARY KEY	NOT NULL,
                          `review_idx`	int  unsigned 	NOT NULL,
                          `user_idx`	int  unsigned 	NOT NULL,
                          `contents`	TEXT	NULL,
                          `created_at`	timestamp	NULL	DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `review` ADD CONSTRAINT `FK_movie_TO_review_1` FOREIGN KEY (
                                                                        `movie_Idx`
    )
    REFERENCES `movie` (
                        `movie_Idx`
        );

ALTER TABLE `review` ADD CONSTRAINT `FK_user_TO_review_1` FOREIGN KEY (
                                                                       `user_idx`
    )
    REFERENCES `user` (
                       `user_idx`
        );

ALTER TABLE `keyword-movie` ADD CONSTRAINT `FK_movie_TO_keyword-movie_1` FOREIGN KEY (
                                                                                      `movie_Idx`
    )
    REFERENCES `movie` (
                        `movie_Idx`
        );

ALTER TABLE `keyword-movie` ADD CONSTRAINT `FK_keyword_TO_keyword-movie_1` FOREIGN KEY (
                                                                                        `keyword_idx`
    )
    REFERENCES `keyword` (
                          `keyword_idx`
        );

ALTER TABLE `movie-genre` ADD CONSTRAINT `FK_movie_TO_movie-genre_1` FOREIGN KEY (
                                                                                  `movie_Idx`
    )
    REFERENCES `movie` (
                        `movie_Idx`
        );

ALTER TABLE `movie-genre` ADD CONSTRAINT `FK_genre_TO_movie-genre_1` FOREIGN KEY (
                                                                                  `genreIdx`
    )
    REFERENCES `genre` (
                        `genreIdx`
        );

ALTER TABLE `movie-ott` ADD CONSTRAINT `FK_ott_TO_movie-ott_1` FOREIGN KEY (
                                                                            `ottIdx`
    )
    REFERENCES `ott` (
                      `ottIdx`
        );

ALTER TABLE `movie-ott` ADD CONSTRAINT `FK_movie_TO_movie-ott_1` FOREIGN KEY (
                                                                              `movie_Idx`
    )
    REFERENCES `movie` (
                        `movie_Idx`
        );

ALTER TABLE `user-ott` ADD CONSTRAINT `FK_ott_TO_user-ott_1` FOREIGN KEY (
                                                                          `ottIdx`
    )
    REFERENCES `ott` (
                      `ottIdx`
        );

ALTER TABLE `user-ott` ADD CONSTRAINT `FK_user_TO_user-ott_1` FOREIGN KEY (
                                                                           `user_idx`
    )
    REFERENCES `user` (
                       `user_idx`
        );

ALTER TABLE `user-genre` ADD CONSTRAINT `FK_user_TO_user-genre_1` FOREIGN KEY (
                                                                               `user_idx`
    )
    REFERENCES `user` (
                       `user_idx`
        );

ALTER TABLE `user-genre` ADD CONSTRAINT `FK_genre_TO_user-genre_1` FOREIGN KEY (
                                                                                `genreIdx`
    )
    REFERENCES `genre` (
                        `genreIdx`
        );

ALTER TABLE `user-movie` ADD CONSTRAINT `FK_user_TO_user-movie_1` FOREIGN KEY (
                                                                               `user_idx`
    )
    REFERENCES `user` (
                       `user_idx`
        );

ALTER TABLE `user-movie` ADD CONSTRAINT `FK_movie_TO_user-movie_1` FOREIGN KEY (
                                                                                `movie_Idx`
    )
    REFERENCES `movie` (
                        `movie_Idx`
        );

ALTER TABLE `user-review` ADD CONSTRAINT `FK_user_TO_user-review_1` FOREIGN KEY (
                                                                                 `user_idx`
    )
    REFERENCES `user` (
                       `user_idx`
        );

ALTER TABLE `user-review` ADD CONSTRAINT `FK_review_TO_user-review_1` FOREIGN KEY (
                                                                                   `review_idx`
    )
    REFERENCES `review` (
                         `review_idx`
        );

ALTER TABLE `movie_photo` ADD CONSTRAINT `FK_movie_TO_movie_photo_1` FOREIGN KEY (
                                                                                  `movie_Idx`
    )
    REFERENCES `movie` (
                        `movie_Idx`
        );

ALTER TABLE `actor` ADD CONSTRAINT `FK_movie_TO_actor_1` FOREIGN KEY (
                                                                      `movie_Idx`
    )
    REFERENCES `movie` (
                        `movie_Idx`
        );

ALTER TABLE `report` ADD CONSTRAINT `FK_review_TO_report_1` FOREIGN KEY (
                                                                         `review_idx`
    )
    REFERENCES `review` (
                         `review_idx`
        );

ALTER TABLE `report` ADD CONSTRAINT `FK_user_TO_report_1` FOREIGN KEY (
                                                                       `user_idx`
    )
    REFERENCES `user` (
                       `user_idx`
        );

