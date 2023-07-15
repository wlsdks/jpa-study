INSERT INTO member(`id`, `name`, `email`, `created_at`, `updated_at`) VALUES (DEFAULT, 'wlsdks', 'wlsdks12@naver.com', NOW(), NOW());
INSERT INTO member(`id`, `name`, `email`, `created_at`, `updated_at`) VALUES (DEFAULT, 'jinan', 'jinan12@naver.com', NOW(), NOW());
INSERT INTO member(`id`, `name`, `email`, `created_at`, `updated_at`) VALUES (DEFAULT, 'dennis', 'dennis12@naver.com', NOW(), NOW());
INSERT INTO member(`id`, `name`, `email`, `created_at`, `updated_at`) VALUES (DEFAULT, 'james', 'james22@naver.com', NOW(), NOW());
INSERT INTO member(`id`, `name`, `email`, `created_at`, `updated_at`) VALUES (DEFAULT, 'wlsdks', 'wlsdks12@another.com', NOW(), NOW());

insert into publisher(`id`, `name`) values (1, '패스트캠퍼스');

insert into book(`id`, `name`, `publisher_id`, `deleted`, `status`) values (1, 'JPA 초격차 패키지', 1, false, 100);
insert into book(`id`, `name`, `publisher_id`, `deleted`, `status`) values (2, 'spring security 초격차 패키지', 1, false, 200);
insert into book(`id`, `name`, `publisher_id`, `deleted`, `status`) values (3, 'springBoot 올인원 패키지', 1, true, 100)
