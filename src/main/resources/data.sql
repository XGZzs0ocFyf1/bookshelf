insert into GENRES (name) values
('humor'),
('sci-fi'),
('horror'),
('fantasy');

insert into AUTHORS (name) values
('Mateo Askaripour'),
('Isaac Asimov'),
('Bram Stoker'),
('Terry Prachett');

insert into BOOKS (genre_id, author_id, title, isbn10, isbn13) values
( '1','1', 'Black Buck', '0358449332', '9780358449331'),
( '2','2', 'Foundation', '0358449332', '9780358449331'),
( '3','3', 'drakula', '0358449332', '9780358449331'),
( '4','4', 'Flat worlds', '0358449332', '9780358449331'),
( '3','3', 'drakula2', '0358449333', '9780358449333'),
( '3','3', 'drakula3', '0358449334', '9780358449334');


