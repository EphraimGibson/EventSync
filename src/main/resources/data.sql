INSERT INTO Events (title, description, date, location) VALUES
                                        ('Primaviera Festival', 'Spring festival with live concert','2025-01-26', 'Porto'),
                                        ('Gravity Vilnius', 'Lakeside Event',current_date, 'Vilnius'),
                                        ('International Developers MeetUp', 'Meeting of Like Minded Tinkers','2025-06-02', 'Washington'),
                                        ('Vilnius Street Food Market', 'Old Town Square', '2025-06-06', 'Vilnius');



INSERT INTO Feedback (event_id, name, email, content, AI_Analysis, created_at, negative_score, positive_score, neutral_score) VALUES
                                                                                                                                                             (1, 'Lush Doe', 'lush@example.com', 'Absolutely amazing event!', 'POSITIVE', '2025-06-14T16:52:38.368159', 0.01, 0.95, 0.04),
                                                                                                                                                             (1, 'Sam Lee', 'sam.lee@example.com', 'Terrible experience, very disappointed.', 'NEGATIVE', '2025-06-14T17:20:00.000000', 0.92, 0.03, 0.05),
                                                                                                                                                             (1, 'Alex Kim', 'alex.kim@example.com', 'Worst event ever.', 'NEGATIVE', '2025-06-14T17:30:00.000000', 0.97, 0.01, 0.02);


INSERT INTO Feedback (event_id, name, email, content, AI_Analysis, created_at, negative_score, positive_score, neutral_score) VALUES
                                                                                                                                                             (2, 'Nina Park', 'nina.park@example.com', 'Loved every moment, fantastic!', 'POSITIVE', '2025-06-15T10:00:00.000000', 0.02, 0.96, 0.02),
                                                                                                                                                             (2, 'Omar Cruz', 'omar.cruz@example.com', 'Superb event, will come again.', 'POSITIVE', '2025-06-15T10:10:00.000000',  0.03, 0.93, 0.04),
                                                                                                                                                             (2, 'Liam Fox', 'liam.fox@example.com', 'It was fine, nothing stood out.', 'NEUTRAL', '2025-06-15T10:20:00.000000',  0.12, 0.12, 0.76);
