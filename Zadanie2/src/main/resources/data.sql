CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(255)        NOT NULL,
    last_name    VARCHAR(255)        NOT NULL,
    email        VARCHAR(255) UNIQUE NOT NULL,
    date_created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tasks
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(500) NOT NULL,
    status      VARCHAR(50),
    deadline    DATE
);

CREATE TABLE IF NOT EXISTS task_users
(
    task_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (task_id, user_id),
    FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


INSERT INTO users (first_name, last_name, email)
VALUES ('Patryk', 'Gorecki', 'patryk.gorecky@gmail.com'),
       ('Anna', 'Nowak', 'anna.nowak@example.com');

INSERT INTO tasks (title, description, status, deadline)
VALUES ('Stworzyć serwer', 'Stworzyć serwer obsługujący REST API na zajęcia z programowania rozproszonego', 'DONE', '2024-12-01'),
       ('Client React', 'Wykonać klienta API z wykorzystaniem Reacta na zajęcia', 'IN_PROGRESS', '2024-12-01'),
       ('Client numer 2', 'Stworzyć drugiego klienta z użyciem innej technologii', 'SUBMITTED', '2024-12-01'),
       ('Stworzyć dokumentację', 'Opisać utworzony projekt', 'REOPENED', '2024-12-01');

INSERT INTO task_users (task_id, user_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1);
