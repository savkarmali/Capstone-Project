insert into users(id, name, email, password, role) values
    (1, 'Admin User', 'admin@crms.com', '{noop}admin123', 'ADMIN');

insert into surveys(id, title, description, status, start_date, end_date, public_token, created_at) values
    (1, 'Fullstack-Java-Registration', 'Java Full Stack learning registration survey', 'PUBLISHED', current_date - 1, current_date + 30, 'java-fullstack-registration', current_timestamp);

insert into questions(id, survey_id, text, type, required, position) values
                                                                         (1, 1, 'Reason for interest in Java Fullstack program?', 'RADIO', true, 1),
                                                                         (2, 1, 'How many hours per week can you dedicate?', 'RADIO', true, 2),
                                                                         (3, 1, 'Today''s Date', 'DATE', true, 3);

insert into question_options(id, question_id, label_value) values
                                                               (1, 1, 'I want to get into Digital projects'),
                                                               (2, 1, 'Strengthen my Java technology skills'),
                                                               (3, 1, 'Build a career in Java space'),
                                                               (4, 2, '2 hours a week'),
                                                               (5, 2, '2 - 6 hours a week'),
                                                               (6, 2, 'Greater than 6 hours a week');