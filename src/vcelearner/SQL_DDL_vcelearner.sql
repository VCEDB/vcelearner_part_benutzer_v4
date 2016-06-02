-- Author:  Rainer
-- Created: 26.05.2016

-- Dummydatenbank incl. Datensätze wiederherstellen

--------------------------------------------------------------------------------

--
-- Datenbank `vcelearner` auswählen
--

use vcelearner;

--------------------------------------------------------------------------------

--
-- Alle Datensätze in allen Tabellen löschen
--

TRUNCATE lernkarte2themenbereich;
TRUNCATE lernsitzung2potentielleantwort;
TRUNCATE benutzer2lernkarte;
TRUNCATE lernsitzung2lernkarte;
DELETE FROM lernsitzung;
DELETE FROM benutzer;
DELETE FROM themenbereich;
DELETE FROM potentielleantwort;
DELETE FROM lernkarte;

--------------------------------------------------------------------------------

--
-- AUTO_INCREMENT aller Tabellen mit Primärschlüsseln (ID) auf 1 setzen
--

ALTER TABLE lernsitzung AUTO_INCREMENT=1;
ALTER TABLE benutzer AUTO_INCREMENT=1;
ALTER TABLE themenbereich AUTO_INCREMENT=1;
ALTER TABLE potentielleantwort AUTO_INCREMENT=1;
ALTER TABLE lernkarte AUTO_INCREMENT=1;

--------------------------------------------------------------------------------

--
-- Alle Datensätze der sieben Themenbereiche in die Datenbank einfügen
--

INSERT INTO themenbereich VALUES (NULL, "Java Basics");
INSERT INTO themenbereich VALUES (NULL, "Working with Java data types");
INSERT INTO themenbereich VALUES (NULL, "Methods and Encapsulation");
INSERT INTO themenbereich VALUES (NULL, "String, StringBuilder, Arrays and ArrayList");
INSERT INTO themenbereich VALUES (NULL, "Flow Control");
INSERT INTO themenbereich VALUES (NULL, "Working with inheritance");
INSERT INTO themenbereich VALUES (NULL, "Exception handling");

--------------------------------------------------------------------------------

--
-- Vier komplette Lernkarten in die Datenbank einfügen
--

-- Komplette Lernkarte mit Primärschlüssel 1 (ID)
INSERT INTO lernkarte VALUES (NULL, "Wie heißt dieses Projekt?", 0);
INSERT INTO lernkarte2themenbereich VALUES(NULL, 1, 1);
INSERT INTO lernkarte2themenbereich VALUES(NULL, 1, 2);
INSERT INTO potentielleantwort VALUES(NULL, "true", "vcelearner", 1);
INSERT INTO potentielleantwort VALUES(NULL, "false", "Taschenrechner", 1);
-- Komplette Lernkarte mit Primärschlüssel 2 (ID)
INSERT INTO lernkarte VALUES (NULL, "Ist das Java?", 3);
INSERT INTO lernkarte2themenbereich VALUES(NULL, 2, 3);
INSERT INTO lernkarte2themenbereich VALUES(NULL, 2, 5);
INSERT INTO potentielleantwort VALUES(NULL, "true", "Natürlich!", 2);
INSERT INTO potentielleantwort VALUES(NULL, "false", "Nö, Shakespeare", 2);
-- Komplette Lernkarte mit Primärschlüssel 3 (ID)
INSERT INTO lernkarte VALUES (NULL, "Bist du Fachinformatiker?", 6);
INSERT INTO lernkarte2themenbereich VALUES(NULL, 3, 2);
INSERT INTO lernkarte2themenbereich VALUES(NULL, 3, 4);
INSERT INTO potentielleantwort VALUES(NULL, "true", "Ich will es werden.", 3);
INSERT INTO potentielleantwort VALUES(NULL, "false", "Ja, schon seit 30 Jahren.", 3);
-- Komplette Lernkarte mit Primärschlüssel 4 (ID)
INSERT INTO lernkarte VALUES (NULL, "Hast du noch Zeit für eine Frage?", 9);
INSERT INTO lernkarte2themenbereich VALUES(NULL, 4, 6);
INSERT INTO lernkarte2themenbereich VALUES(NULL, 4, 7);
INSERT INTO potentielleantwort VALUES(NULL, "true", "Klar, eine geht noch!", 4);
INSERT INTO potentielleantwort VALUES(NULL, "false", "Nö, heute nicht mehr.", 4);

--------------------------------------------------------------------------------

--
-- Vier komplette Benutzer in die Datenbank einfügen
--

-- Kompletter Benutzer mit Primärschlüssel 1 (ID)
INSERT INTO benutzer VALUES (NULL, "chef", "abc", "Klaus", "Maier");
INSERT INTO benutzer2lernkarte VALUES(1, 4, "true");
-- Kompletter Benutzer mit Primärschlüssel 2 (ID)
INSERT INTO benutzer VALUES (NULL, "teilnehmer", "123", "Gabi", "Schmidt");
INSERT INTO benutzer2lernkarte VALUES(2, 3, "false");
-- Kompletter Benutzer mit Primärschlüssel 3 (ID)
INSERT INTO benutzer VALUES (NULL, "klassenclown", "blabla", "Steffen", "Lehmann");
INSERT INTO benutzer2lernkarte VALUES(3, 2, "true");
-- Kompletter Benutzer mit Primärschlüssel 4 (ID)
INSERT INTO benutzer VALUES (NULL, "fauli", "schnarch", "Steffi", "Schulz");
INSERT INTO benutzer2lernkarte VALUES(4, 1, "false");

--------------------------------------------------------------------------------

--
-- 4 komplette Lernsitzungen in die Datenbank einfügen
--

-- Komplette Lernsitzung mit Primärschlüssel 1 (ID)
INSERT INTO lernsitzung VALUES (NULL, "Test", "1900-01-01", 4);
INSERT INTO lernsitzung2lernkarte VALUES (1, 1, "false");
INSERT INTO lernsitzung2potentielleantwort VALUES (1, 1);
INSERT INTO lernsitzung2potentielleantwort VALUES (1, 2);
-- Komplette Lernsitzung mit Primärschlüssel 2 (ID)
INSERT INTO lernsitzung VALUES (NULL, "Lern", "1930-03-03", 3);
INSERT INTO lernsitzung2lernkarte VALUES (2, 2, "true");
INSERT INTO lernsitzung2potentielleantwort VALUES (2, 3);
INSERT INTO lernsitzung2potentielleantwort VALUES (2, 4);
-- Komplette Lernsitzung mit Primärschlüssel 3 (ID)
INSERT INTO lernsitzung VALUES (NULL, "LernR", "1960-06-06", 2);
INSERT INTO lernsitzung2lernkarte VALUES (3, 3, "false");
INSERT INTO lernsitzung2potentielleantwort VALUES (3, 5);
INSERT INTO lernsitzung2potentielleantwort VALUES (3, 6);
-- Komplette Lernsitzung mit Primärschlüssel 4 (ID)
INSERT INTO lernsitzung VALUES (NULL, "ungewertet", "1990-09-09", 1);
INSERT INTO lernsitzung2lernkarte VALUES (4, 4, "true");
INSERT INTO lernsitzung2potentielleantwort VALUES (4, 7);
INSERT INTO lernsitzung2potentielleantwort VALUES (4, 8);