SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE SCHEMA IF NOT EXISTS `localizacion` DEFAULT CHARACTER SET utf8 ;
USE `localizacion` ;

CREATE TABLE IF NOT EXISTS `localizacion`.`User` (
 `ID_Cliente` int NOT NULL AUTO_INCREMENT,
 `Nombre` varchar(50) DEFAULT NULL,
 `apellidos` varchar(50) DEFAULT NULL,
 `Correo` varchar(50) DEFAULT NULL,
 `Telefono` varchar(50) DEFAULT NULL,
 `Pass` varchar(25) NOT NULL,
  PRIMARY KEY (`ID_Cliente`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `localizacion`.`Event` (
  `IDEventos` int NOT NULL AUTO_INCREMENT,
  `Imag` LONGBLOB NOT NULL,
  `Fechahora` varchar(100) NOT NULL,
  `Descripcion` varchar(100) NOT NULL,
  `Latitude` varchar(100) NOT NULL,
  `Longitude` varchar(100) NOT NULL,
  `IDUsuario` int NOT NULL,
  PRIMARY KEY (`IDEventos`),
  INDEX `fk_localizacion_Cliente_idx` (`IDUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_localizacion_Cliente`
    FOREIGN KEY (`IDUsuario`)
    REFERENCES `localizacion`.`User` (`ID_Cliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


DELIMITER $$
CREATE PROCEDURE getUser(IN email VARCHAR(255), IN pass VARCHAR(45), OUT response VARCHAR(100))
BEGIN
  IF EXISTS (SELECT u.ID_Cliente FROM `localizacion`.`User` as u where u.Correo = email and u.Pass = pass)
	THEN
		SELECT * FROM `localizacion`.`User` as u where u.Correo = email and u.Pass = pass;
		SET response = 'OK';
	ELSE
		SET response = 'No existe el usuraio ingresado, compruebe email y password';
	END IF;
END $$


DELIMITER $$
CREATE PROCEDURE insertUser(IN userName VARCHAR(255), IN userLastName VARCHAR(255), IN email VARCHAR(255), IN phone VARCHAR(255), IN pass VARCHAR(255),  OUT response VARCHAR(100))
BEGIN
 IF NOT EXISTS (SELECT u.ID_Cliente FROM `localizacion`.`User` as u where u.Correo = email and u.Pass = pass)
  THEN
		INSERT INTO `localizacion`.`User` (`ID_Cliente`,`Nombre`,`apellidos` , `Correo`,`Telefono` ,`Pass`)
		VALUES (Null, userName, userLastName, email, phone, pass);
		SET response='OK';
  ELSE
		SET response = 'Error, el usuario ya existe';
  END IF;
END $$


CALL insertUser("Jorge", "Zepeda", "jorzet.94", "5559810374", "1234", @result);
SELECT @result;

CALL getUser("jorzet.94", "1234", @result);
SELECT @result;



DELIMITER $$
CREATE PROCEDURE getEvents(OUT response VARCHAR(100))
BEGIN
  IF EXISTS (SELECT * FROM `localizacion`.`Event`)
	THEN
		SELECT * FROM `localizacion`.`Event`;
		SET response = 'OK';
	ELSE
		SET response = 'No existen eventos aun';
	END IF;
END $$


DELIMITER $$
CREATE PROCEDURE insertEvent(IN img LONGBLOB, IN eventDate VARCHAR(255), IN eventDescription VARCHAR(255), IN lat VARCHAR(255), IN lon VARCHAR(255), IN IDUsuario INT,  OUT response VARCHAR(100))
BEGIN
	INSERT INTO `localizacion`.`Event` (`IDEventos`,`Imag`,`Fechahora`,`Descripcion`,`Latitude` ,`Longitude`,`IDUsuario`)
	VALUES (Null, img, eventDate, eventDescription, lat, lon, IDUsuario);
	SET response='OK';
END $$


CALL insertEvent(0x024324, "24-23-23", "Hola", "132.12341243", "5345.4352345", 1, @result);
SELECT @result;

CALL getEvents(@result);
SELECT @result;