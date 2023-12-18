DROP TABLE IF EXISTS faculties;

CREATE TABLE faculties (
    id UUID NOT NULL,
    name VARCHAR(50) NOT NULL,
    teacher VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    actual_visitors INTEGER NOT NULL,
    max_visitors INTEGER NOT NULL,
    price_per_day NUMERIC(10, 2) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO faculties(id, name, teacher, email, actual_visitors, max_visitors, price_per_day)
VALUES
    ('773dcbc0-d2fa-45b4-acf8-485b682adedd', 'Geography', 'Ivanov Petr Sidorovich', 'geography@gmail.com', 7, 20, 6.72),
    ('8d8cfc84-e77c-4722-b4d6-8e9fdc17c721', 'Mathematics', 'Kobrina Daria Nikolaevna', 'mathematics@somewhere.by', 19, 25, 10.15),
    ('a8014a1e-c14c-410a-abd0-a2bc3014c3b3', 'Physics', 'Gauss Doreman Kolistos', 'physics@somewhere.by', 5, 25, 15.15),
    ('da1a2959-363b-477e-ab23-66ef983a7568', 'Astronomy', 'Copernic Antip Petrovich', 'astronomy@somewhere.by', 16, 30, 6.99),
    ('459f0e45-1e90-4f87-9570-162ec69a9890', 'Russian', 'Ivanova Irina Ivanovna', 'russian@somewhere.by', 10, 20, 10.0),
    ('f308f9d9-65e1-4ad4-bb6d-66e57fc751ed', 'English', 'Borg Any Samuilovna', 'english@somewhere.by', 6, 10, 15.0),
    ('96d9273d-c77a-49d1-83e8-a908b869f49a', 'German', 'Kruger Aziza Dalaivovna', 'german@somewhere.by', 2, 10, 10.0),
    ('f69e13fb-cde9-4550-80c0-7bad338f5e9f', 'Philosophy', 'Freid Antip Ignatovich', 'philosophy@somewhere.by', 3, 10, 6.50),
    ('68a2cbbd-1a93-4f55-8434-1f0eb6b75d4d', 'Philology', 'Samsonova Kira Danilovna', 'philology@somewhere.by', 5, 11, 7.20),
    ('4448f984-5391-4000-a428-402bdfafce5d', 'Chemistry', 'Mendeleeva Zhanna Ivanovna', 'chemistry@somewhere.by', 11, 20, 11.00),
    ('31d7ff89-4d5f-4421-a166-12a1d0418879', 'Alchemy', 'Darkside Mikhail Ramuilovich', 'alchemy@somewhere.by', 6, 25, 9.99),
    ('084dbf1c-4220-49c0-b07b-88af35690b20', 'Magic', 'Abrakadabra Sabina Ippolitovna', 'magic@somewhere.by', 7, 20, 9.99),
    ('4dcb56c3-8c69-48c9-993a-6331a43a9a01', 'History', 'Saburova Inna Andreevna', 'history@somewhere.by', 11, 15, 15.0),
    ('556466dc-bfa0-48fb-9c2d-992ecc2aad6b', 'Culture', 'Kupala Ignat Sergeevich', 'culture@somewhere.by', 10, 11, 11.0),
    ('1dada6ba-4331-48cc-ae3f-43e7ba444ef6', 'Biology', 'Krevetkina Olga Danilovna', 'biology@somewhere.by', 18, 20, 15.69),
    ('16a88e87-779d-4094-bac3-e0d81c30ebc1', 'Physiology', 'Perelom Mihail Kuzmich', 'physiology@somewhere.by', 5, 10, 10.26),
    ('67c65e19-8ffa-4906-9966-1dd456e572eb', 'Cartography', 'Serchev Oleg Nikolaevich', 'cartography@somewhere.by', 5, 8, 10.33),
    ('ebe9dbe8-6d14-4672-a697-d91cbda55b5e', 'Astrology', 'Nesmeyanov Kuzma Olegovich', 'astrology@somewhere.by', 10, 15, 9.99),
    ('f8386d1d-6dd6-474c-b7ad-75d0bb4e6446', 'Modeling', 'Tiapov Andrey Ivanovich', 'modeling@somewhere.by', 20, 20, 19.99),
    ('61010750-d6c7-4410-9728-330a5b8da467', 'Information', 'Refilova Zhanna Andreevna', 'information@somewhere.by', 20, 25, 19.99),
    ('d6f69ef6-60de-4da3-a799-bbbb44790d4b', 'Topology', 'Berengov Andrei Olegovich', 'topology@somewhere.by', 1, 8, 6.25),
    ('32bb8392-37f3-40a5-a9df-25f8f86f3a53', 'Research', 'Kosmatov Ivan Ivanovich', 'research@somewhere.by', 10, 20, 9.35),
    ('166c7f50-f9f8-44c3-b781-36326276c8eb', 'Medicine', 'Aibolit Anton Antonovich', 'medicine@somewhere.by', 20, 25, 16.69),
    ('35bee038-ad79-4b73-81fa-a1dba8c880b9', 'Pharmacy', 'Koldunova Olga Evgenievna', 'pharmacy@somewhere.by', 21, 25, 17.22),
    ('198c7c2d-7a5c-4db0-9846-8f86b700ba8e', 'Archeology', 'Kopatel Anton Antonovich', 'archeology@somewhere.by', 6, 20, 6.21),
    ('953eeab2-302c-4ab6-aa11-f2f8ba186687', 'Geology', 'Glubokov Evgeniy Samuilovich', 'geology@somewhere.by', 7, 15, 7.33),
    ('cdd062c1-dca8-4ff5-8ee4-6258203a349b', 'Paleontology', 'Starikova Anna Ivanovna', 'paleontology@somewhere.by', 8, 15, 6.56)