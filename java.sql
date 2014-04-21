-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 07, 2014 at 08:28 PM
-- Server version: 5.5.35
-- PHP Version: 5.4.6-1ubuntu1.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `java`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE IF NOT EXISTS `book` (
  `Barcode` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `Title` varchar(50) COLLATE latin1_general_ci NOT NULL,
  `Discipline` varchar(25) COLLATE latin1_general_ci NOT NULL,
  `Author1` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `Author2` varchar(30) COLLATE latin1_general_ci DEFAULT NULL,
  `Author3` varchar(30) COLLATE latin1_general_ci DEFAULT NULL,
  `Author4` varchar(30) COLLATE latin1_general_ci DEFAULT NULL,
  `Publisher` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `YearOfPublication` int(4) NOT NULL,
  `ISBN` varchar(15) COLLATE latin1_general_ci DEFAULT NULL,
  `BookCondition` enum('Good','Damaged') COLLATE latin1_general_ci NOT NULL DEFAULT 'Good',
  `SuggestedPrice` varchar(6) COLLATE latin1_general_ci NOT NULL,
  `Notes` varchar(300) COLLATE latin1_general_ci DEFAULT NULL,
  `BookStatus` enum('Active','Lost','Inactive') COLLATE latin1_general_ci NOT NULL DEFAULT 'Active',
  `DateOfLastUpdate` varchar(12) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`Barcode`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`Barcode`, `Title`, `Discipline`, `Author1`, `Author2`, `Author3`, `Author4`, `Publisher`, `YearOfPublication`, `ISBN`, `BookCondition`, `SuggestedPrice`, `Notes`, `BookStatus`, `DateOfLastUpdate`) VALUES
('30101', 'The Blacker The Berry', 'African/American Studies', 'Wallace Thurman', '', '', '', 'Bob Saget Publishing', 1980, '068481580x', 'Good', '100.00', '', 'Active', '2014-04-07'),
('30102', 'The Blacker The Berry', 'African/American Studies', 'Wallace Thurman', '', '', '', 'Unknown', 0, '068481580x', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30103', 'The Blacker The Berry', 'African/American Studies', 'Wallace Thurman', '', '', '', 'Unknown', 0, '068481580x', 'Good', '100.00', '', 'Active', '2014-04-06'),
('30104', 'The Blacker The Berry', 'African/American Studies', 'Wallace Thurman', '', '', '', 'Unknown', 0, '068481580x', 'Good', '100.00', '', 'Active', '2014-04-02'),
('30105', 'The Blacker The Berry', 'African/American Studies', 'Wallace Thurman', '', '', '', 'Unknown', 0, '068481580x', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30106', 'The Blacker The Berry', 'African/American Studies', 'Wallace Thurman', '', '', '', 'Unknown', 0, '068481580x', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30107', 'The Blacker The Berry', 'African/American Studies', 'Wallace Thurman', '', '', '', 'Unknown', 0, '068481580x', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30108', 'Up From Slavery', 'African/American Studies', 'Booker T. Washington', '', '', '', 'Unknown', 0, '140390510', 'Good', '100.00', '', 'Active', '2014-04-07'),
('30109', 'Up From Slavery', 'African/American Studies', 'Booker T. Washington', '', '', '', 'Unknown', 0, '140390510', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30110', 'Up From Slavery', 'African/American Studies', 'Booker T. Washington', '', '', '', 'Unknown', 0, '140390510', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30111', 'Up From Slavery', 'African/American Studies', 'Booker T. Washington', '', '', '', 'Unknown', 0, '140390510', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30112', 'Native Son', 'African/American Studies', 'Richard Wright', '', '', '', 'Unknown', 0, '0060809779', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30113', 'Native Son', 'African/American Studies', 'Richard Wright', '', '', '', 'Unknown', 0, '0060809779', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30114', 'Native Son', 'African/American Studies', 'Richard Wright', '', '', '', 'Unknown', 0, '0060809779', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30115', 'Go Tell It On The Mountain', 'African/American Studies', 'James Baldwin', '', '', '', 'Unknown', 0, '0440330076', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30116', 'Go Tell It On The Mountain', 'African/American Studies', 'James Baldwin', '', '', '', 'Unknown', 0, '0440330076', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30117', 'Narrative of the Life of Frederick Douglass', 'African/American Studies', 'Frederick Douglass', '', '', '', 'Unknown', 0, '0486284999', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30118', 'Narrative of the Life of Frederick Douglass', 'African/American Studies', 'Frederick Douglass', '', '', '', 'Unknown', 0, '0486284999', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30119', 'Narrative of the Life of Frederick Douglass', 'African/American Studies', 'Frederick Douglass', '', '', '', 'Unknown', 0, '0486284999', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30120', 'Narrative of the Life of Frederick Douglass', 'African/American Studies', 'Frederick Douglass', '', '', '', 'Unknown', 0, '0486284999', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30121', 'The Autobiography of an Ex-Colored Man', 'African/American Studies', 'James Weldon Johnson', '', '', '', 'Unknown', 0, '0140184023', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30122', 'The Autobiography of an Ex-Colored Man', 'African/American Studies', 'James Weldon Johnson', '', '', '', 'Unknown', 0, '0140184023', 'Good', '100.00', '', 'Active', '2014-04-03'),
('30123', 'The Autobiography of an Ex-Colored Man', 'African/American Studies', 'James Weldon Johnson', '', '', '', 'Unknown', 0, '0140184023', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30124', 'Their Eyes Were Watching Good', 'African/American Studies', 'Zora Neale Hurston', '', '', '', 'Unknown', 0, '0060931418', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30125', 'Their Eyes Were Watching Good', 'African/American Studies', 'Zora Neale Hurston', '', '', '', 'Unknown', 0, '0060931418', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30126', 'Their Eyes Were Watching Good', 'African/American Studies', 'Zora Neale Hurston', '', '', '', 'Unknown', 0, '0060931418', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30127', 'Losing My Cool', 'African/American Studies', 'Thomas Chatterton Williams', '', '', '', 'Unknown', 0, '0143119621', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30128', 'Losing My Cool', 'African/American Studies', 'Thomas Chatterton Williams', '', '', '', 'Unknown', 0, '0143119621', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30129', 'Losing My Cool', 'African/American Studies', 'Thomas Chatterton Williams', '', '', '', 'Unknown', 0, '0143119621', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30130', 'Labor Of Love, Labor of Sorrow', 'African/American Studies', 'Jacqueline Jones', '', '', '', 'Unknown', 0, '0394745361', 'Good', '100.00', '', 'Active', '2014-02-04'),
('30131', 'Africa Unchained', 'African/American Studies', 'George B. N. Ayittey', '', '', '', 'Unknown', 0, '1403963598', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60101', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60102', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60103', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60104', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60105', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60106', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60107', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60108', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60109', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60110', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Active', '2014-02-04'),
('60111', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Inactive', '2014-04-01'),
('60112', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Inactive', '2014-04-01'),
('60113', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Inactive', '2014-04-07'),
('60114', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Inactive', '2014-04-06'),
('60115', 'Working', 'GEP', 'Studs Terkel', '', '', '', 'Unknown', 0, '1565843428', 'Good', '100.00', '', 'Inactive', '2014-04-06'),
('101232', 'Al Gore Rhythms', 'Mathematics', 'Al Gore', NULL, NULL, NULL, 'Al Gore', 2008, '12312412', 'Good', '112', '', 'Active', '2014-03-31'),
('101444', 'Math', 'Mathematics', 'Mr. Math', NULL, NULL, NULL, 'MathRUs', 2000, '1231238284', 'Good', '500', '', 'Inactive', '2014-04-01'),
('111123', 'BookBook', 'Communications', 'AuthorMan', NULL, NULL, NULL, 'Pub', 2009, '123124849', 'Good', '8', '', 'Inactive', '2014-04-01'),
('333030', 'Harry Potter 2', 'Physical Education', 'Rowling', NULL, NULL, NULL, 'Someone', 2010, '12312412', 'Good', '20', '', 'Inactive', '2014-04-07'),
('333234', 'ABook', 'Physical Education', 'AnAuthor', NULL, NULL, NULL, 'Pub', 2009, '124234993', 'Good', '10', '', 'Inactive', '2014-04-01'),
('555123', 'Koob', 'Education', 'Bob', NULL, NULL, NULL, 'Bob', 1998, '124382348', 'Good', '9', '', 'Inactive', '2014-04-01'),
('444321', 'Bookoob', 'Arts', 'Charly', NULL, NULL, NULL, 'Kob', 1999, '7437493498', 'Good', '5', '', 'Inactive', '2014-04-01'),
('4443212', 'Bookoob', 'Arts', 'Charly', NULL, NULL, NULL, 'Kob', 1999, '7437493498', 'Good', '5', '', 'Inactive', '2014-04-01'),
('222123455', 'Al Gore Rhythms', 'Criminal Justice', 'Jimeie', '', NULL, NULL, 'Matt Andre', 2012, '53543-432423-3', 'Good', '12', 'fdsfsdkjfklsdjfkldsjkl', 'Active', '2014-04-02'),
('222999', 'A Real Book', 'Criminal Justice', 'That Guy', '', NULL, NULL, '', 1998, '1234123789', 'Good', '15', '', 'Inactive', '2014-04-01'),
('111333', 'New Book!', 'Communications', 'Riley', '', NULL, NULL, 'Someone Else', 2002, '124884393', 'Good', '34', '', 'Inactive', '2014-04-02'),
('222222', 'Bookoob Returns', 'Criminal Justice', 'Author?', 'Oh, Okay', NULL, NULL, 'Nope', 1293, '213439439', 'Good', '684', '', 'Inactive', '2014-04-02'),
('111124', 'fdsda', 'Communications', 'adsads', 'asdva', NULL, NULL, 'dfs', 1232, '', 'Good', '', '', 'Inactive', '2014-04-02'),
('111111', 'dsfs', 'Communications', 'sfsdf', 'sdfsd', NULL, NULL, 'dsf', 1232, '', 'Good', '', '', 'Inactive', '2014-04-02'),
('222444', 'sfs', 'Criminal Justice', 'sdfs', 'sdfs', NULL, NULL, 'dfdsf', 2324, '1231242', 'Good', '', '', 'Inactive', '2014-04-02');

-- --------------------------------------------------------

--
-- Table structure for table `bookbarcodeprefix`
--

CREATE TABLE IF NOT EXISTS `bookbarcodeprefix` (
  `PrefixValue` varchar(3) COLLATE latin1_general_ci NOT NULL,
  `Discipline` varchar(25) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`PrefixValue`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `bookbarcodeprefix`
--

INSERT INTO `bookbarcodeprefix` (`PrefixValue`, `Discipline`) VALUES
('101', 'Mathematics'),
('201', 'English'),
('301', 'African/American Studies'),
('401', 'History'),
('501', 'Nursing'),
('601', 'GEP'),
('701', 'Sciences'),
('801', 'Business'),
('901', 'Computer Sciences'),
('111', 'Communications'),
('222', 'Criminal Justice'),
('333', 'Physical Education'),
('444', 'Arts'),
('555', 'Education'),
('666', 'Foreign Languages'),
('777', 'Health Science'),
('888', 'Political Science'),
('999', 'Psychology'),
('102', 'Public Administration'),
('292', 'Recreation & Leisure'),
('303', 'Sociology'),
('404', 'Social Work'),
('505', 'Women Studies');

-- --------------------------------------------------------

--
-- Table structure for table `maxduedate`
--

CREATE TABLE IF NOT EXISTS `maxduedate` (
  `CurrentMaxDueDate` varchar(12) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`CurrentMaxDueDate`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `maxduedate`
--

INSERT INTO `maxduedate` (`CurrentMaxDueDate`) VALUES
('2014-05-16');

-- --------------------------------------------------------

--
-- Table structure for table `rental`
--

CREATE TABLE IF NOT EXISTS `rental` (
  `ID` int(5) NOT NULL AUTO_INCREMENT,
  `BorrowerID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `BookID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `CheckoutDate` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `CheckoutWorkerID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `DueDate` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `CheckinDate` varchar(10) COLLATE latin1_general_ci DEFAULT NULL,
  `CheckinWorkerId` varchar(10) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `studentborrower`
--

CREATE TABLE IF NOT EXISTS `studentborrower` (
  `BannerID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `FirstName` varchar(25) COLLATE latin1_general_ci NOT NULL,
  `LastName` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `ContactPhone` varchar(15) COLLATE latin1_general_ci NOT NULL,
  `Email` varchar(50) COLLATE latin1_general_ci NOT NULL,
  `BorrowerStatus` enum('Delinquent','Good Standing') COLLATE latin1_general_ci NOT NULL DEFAULT 'Good Standing',
  `DateOfLatestBorrowerStatus` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `DateOfFirstRegistration` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `MonetaryPenalty` varchar(6) COLLATE latin1_general_ci DEFAULT NULL,
  `Notes` varchar(300) COLLATE latin1_general_ci DEFAULT NULL,
  `ActiveStatus` enum('Active','Inactive') COLLATE latin1_general_ci NOT NULL DEFAULT 'Active',
  `DateOfLastUpdate` varchar(12) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`BannerID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `studentborrower`
--

INSERT INTO `studentborrower` (`BannerID`, `FirstName`, `LastName`, `ContactPhone`, `Email`, `BorrowerStatus`, `DateOfLatestBorrowerStatus`, `DateOfFirstRegistration`, `MonetaryPenalty`, `Notes`, `ActiveStatus`, `DateOfLastUpdate`) VALUES
('800594444', 'Kimberly', 'Abdullah', '555-555-5555', 'kabdu2@brockport.edu', 'Good Standing', '2014-04-07', '2014-01-30', '0.00', '', 'Active', '2014-04-07'),
('800456358', 'Michael', 'Adams', '', 'madam5@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800551246', 'Maame Esi', 'Aggrey', '', 'maggr1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800580722', 'Tibian', 'Ahmed', '', 'tahme1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800582390', 'Priscilla', 'Alba', '', 'palba1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800498853', 'Casey', 'Albano', '', 'calba2@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800593769', 'Marcia', 'Albarracin', '', 'malba2@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800597704', 'Stephanie', 'Alcantara', '', 'salca1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800594345', 'Dominique', 'Alcutt', '', 'dalcu1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800505366', 'Yokastheline', 'Alejo', '', 'yalej1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800565173', 'Charles', 'Alford', '', 'calfo1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800587982', 'Jonathan', 'Alicea', '', 'jalic2@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800598215', 'Elijah', 'Allard', '', 'ealla2@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800447128', 'La''Kesa', 'Allen', '', 'lalle1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800555593', 'Trevor', 'Allston', '', 'talst1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800515421', 'Devon', 'Amado', '', 'damad1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800577488', 'O''Neil', 'Amegbo', '', 'oameg1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800418736', 'Stephannie', 'Ames', '', 'sames1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800603438', 'Dan', 'Andrews', '', 'dandr3@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800390706', 'Chelsie', 'Angelo', '', 'cange2@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800519369', 'Alexa', 'Arena', '', 'aaren2@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800549877', 'Genesis', 'Arias', '', 'garia1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800540679', 'Nancy', 'Armfield', '', 'narmf1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800464845', 'Hugo', 'Avalos', '', 'haval1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800453926', 'Andy', 'Ayala', '', 'aayal1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800449448', 'David', 'Babb', '', 'dbabb1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800514284', 'Jennifer', 'Banker', '', 'jbank2@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800455000', 'Hakeem', 'Banks', '', 'hbank1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800553597', 'Joel', 'Baque Zavala', '', 'jbaqu1@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800558994', 'Abigail', 'Barima', '', 'abari2@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800477875', 'Katherine', 'Barone', '', 'kbaro2@brockport.edu', 'Good Standing', '2014-01-30', '2014-01-30', '0.00', '', 'Active', '2014-01-30'),
('800513745', 'Trishanna', 'Bennett', '', 'tbenn2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800545845', 'Kristin', 'Bickford', '', 'kbick3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800500466', 'Abu', 'Bility', '', 'abili1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800500467', 'Lossine', 'Bility', '', 'lbili1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800485235', 'Nigel', 'Blair', '', 'nblai1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800486046', 'Eemane', 'Boadu', '', 'eboad1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800552467', 'Latresha', 'Booker', '', 'lbook1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800509625', 'Peter', 'Borges', '', 'pborg1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800214678', 'Acey', 'Bowman', '', 'abow0103@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800590136', 'Anya', 'Boyd', '', 'aboyd1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800453229', 'Daniel', 'Bradley', '', 'dbrad3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800428880', 'Elizabeth', 'Bradtke', '', 'ebrad1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800543203', 'Star', 'Brito', '', 'sbrit1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800449490', 'Yaw', 'Brobbey', '', 'ybrob1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800509073', 'Arthur', 'Brooks', '', 'abroo5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800450255', 'Shaton', 'Brown', '', 'sbrow8@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800590473', 'Jasmine', 'Brunson', '', 'jbrun2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800507432', 'Daniela', 'Bulos', '', 'dbulo1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800591899', 'Brianna', 'Bunch', '', 'bbunc1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800436955', 'Auriel', 'Burns', '', 'aburn4@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800532224', 'Lindsay', 'Buscemi', '', 'lbusc1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800594352', 'Robyn', 'Camacho', '', 'rcama1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800461800', 'James', 'Carlson', '', 'jcarl3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800467643', 'Eboni', 'Carman', '', 'ecarm1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800606459', 'Jonathan', 'Carnegie', '', 'jcarn3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800546986', 'Christopher', 'Carpio', '', 'ccarp5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800550182', 'Solana', 'Cartagena', '', 'scart3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800546197', 'Julio', 'Cedeno', '', 'jcede1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800461988', 'Jarid', 'Cheatham', '', 'jcheal1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800540774', 'Yan', 'Cheng', '', 'ychen1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800328327', 'Taib', 'Chito', '', 'tchi0101@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800596726', 'Tenzin', 'Chotsok', '', 'tchot1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800550627', 'Tranace', 'Chung-Barksdale', '', 'tchun1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800423630', 'Keturah', 'Clark', '', 'kclar2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800432519', 'Lakeisha', 'Clark', '', 'lclar8@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800479188', 'Mary', 'Clark', '', 'mclar5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800596028', 'Levi', 'Clarke', '', 'lclar9@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800519894', 'Shireem', 'Cobb', '', 'scobb1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800430391', 'Jenae', 'Cochran', '', 'jcoch1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800522228', 'Marissa', 'Coelho', '', 'mcoel1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800583051', 'Ashley', 'Coley', '', 'acole4@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800480984', 'Kerlyne', 'Colin', '', 'kcoli1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800447914', 'Davanique', 'Collier', '', 'dcoll3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800589344', 'Chaynna', 'Colon', '', 'ccolo2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800506095', 'Jalyn', 'Comfort', '', 'jcomf1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800446081', 'Jordan', 'Connors', '', 'jconn3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800489235', 'Justin', 'Cook', '', 'jcook3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800358527', 'John', 'Coppeta', '', 'jcopp2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800428287', 'Patricia', 'Cotton', '', 'pcott1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800619410', 'Randee', 'Crenshaw', '', 'rcren1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800403088', 'Latoni', 'Cromes', '', 'lcrom1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800474638', 'Carissa', 'Cromwell', '', 'ccrom1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800575515', 'Michael', 'Danz', '', 'mdanz1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-30', '0.00', '', 'Active', '2014-01-31'),
('800617424', 'Margaret', 'Davis', '', 'mdavi10@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800494081', 'Tyshon', 'Davis', '', 'tdavi6@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800591771', 'Jasmine', 'Davis-Higdon', '', 'jdavi9@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800436884', 'Arnold', 'De La Cruz', '', 'adela1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800475746', 'Tamira', 'DeJesus', '', 'tdeje1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800546421', 'Felicia', 'Diaz', '', 'fdiaz1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800547805', 'Nandy', 'Diego', '', 'ndieg1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800547327', 'Will', 'Dillard-Jackson', '', 'wdill1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800598232', 'Kiyana', 'Dunlock', '', 'kdunl2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800485877', 'Phil', 'Edwards', '', 'pedwa1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800533399', 'Xia', 'Edwards', '', 'xedwa1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800516543', 'David', 'Ekukpe', '', 'dekuk1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800515312', 'Helem', 'Fabre', '', 'hfabr1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800591763', 'Rosibel', 'Fajardo', '', 'rfaja1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800425682', 'Erica', 'Fardette', '', 'efard1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800516968', 'Darius', 'Favors', '', 'dfavo1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800604410', 'Paul', 'Feliciano', '', 'pfeli1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800467269', 'Rayleen', 'Fergen', '', 'rferg1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800594925', 'Keeley', 'Ferguson', '', 'kferg3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800512155', 'Gudrinis', 'Fernandez', '', 'gfern1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800530652', 'Dana', 'Fink', '', 'dfink1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800561247', 'Faith', 'Fletcher', '', 'fflet1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800517811', 'Darrel', 'Flores', '', 'dflor2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800457268', 'Jeiri', 'Flores', '', 'jflor1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800511716', 'Khadija', 'Fong', '', 'kfong1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800510490', 'Shoshana', 'Fox', '', 'sfox1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800463674', 'Charlena', 'Fraser', '', 'cfras1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800175432', 'Cynthia', 'Gardner', '', 'cgard3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800597615', 'Olubunmi', 'Gbajumo', '', 'ogbaj1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800449594', 'Reymy', 'Gerez', '', 'rgere1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800454384', 'Jessica', 'Giglio', '', 'jgigl1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800558742', 'Rokia', 'Gittens', '', 'rgitt1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800537669', 'Fatima', 'Goba', '', 'fgoba1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800462589', 'Eliah', 'Golding', '', 'egold1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800562530', 'Angelica', 'Gomez', '', 'agome1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800457243', 'Rafael', 'Gomez', '', 'rgome1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800467004', 'Albert', 'Gonzalez', '', 'agonz2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800408987', 'Matthew', 'Goodfellow', '', 'mgood2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800543577', 'Danielle', 'Gordon', '', 'dgord5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800547071', 'Sharmaine', 'Graham', '', 'sgrah2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800596523', 'Anrisa', 'Green', '', 'agree8@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800336361', 'Dayna', 'Greer', '', 'dgre0413@brockport,edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800488447', 'Dyanna', 'Gregory', '', 'dgreg1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800450483', 'Tameka', 'Groucher', '', 'tgrou1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800536357', 'Kimberly', 'Guevara-Garabito', '', 'kguev1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800582570', 'Ashley', 'Gutierrez', '', 'aguti2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800600304', 'Elizabeth', 'Guzman', '', 'eguzm1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800594233', 'Dorothy', 'Gyampoh', '', 'dgyam1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800479333', 'Bana', 'Hagos', '', 'bhago1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800598820', 'Cortland', 'Haines', '', 'chain1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800318503', 'Donald', 'Hall', '', 'dhal0613@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800423688', 'Maxx', 'Hamblin', '', 'mhamb1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800580883', 'Shawnike', 'Harden', '', 'shard2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800509041', 'Teyanee', 'Harewood', '', 'thare1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800457235', 'Nicole', 'Harris', '', 'nharr2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800453769', 'Michele', 'Harris-Semple', '', 'msemp1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800583242', 'Naphtalia', 'Hawthorne', '', 'nhawt1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800505879', 'Mark', 'Henry', '', 'mhenr2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800533159', 'Jorge', 'Hernandez', '', 'jhern3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800484006', 'Stephanie', 'Hernandez', '', 'shern1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800545886', 'Kevon', 'Hill', '', 'klond2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800473313', 'Nicole', 'Hird', '', 'nhird1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800546285', 'Joseph', 'Hirsch', '', 'jhirs1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800541835', 'Anissah', 'Holland', '', 'aholl5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800445797', 'Erik', 'Homan', '', 'ehoma1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800556668', 'Amber', 'Hooks', '', 'ahook1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800588237', 'Jason', 'Howard', '', 'jhowa6@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800523728', 'Eoin', 'Hughes', '', 'ehugh3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800495703', 'Johnathan', 'Ivy', '', 'jivy1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800611391', 'Deja', 'Jackson', '', 'djack3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800353043', 'Leon', 'Jackson', '', 'ljack7@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800477760', 'Nayeri', 'Jacobo', '', 'njaco2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800453083', 'Nayithe', 'Jalowayski', '', 'njalo1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800529410', 'Melissa', 'James', '', 'mjame1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800550742', 'Hannie', 'Jean-Gilles', '', 'hjean1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800598328', 'Tremec', 'Jeffries', '', 'tjeff1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800191416', 'Chester', 'Johnson', '', 'cjohn19@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800588858', 'Cynisha', 'Johnson', '', 'cjohn18@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800595659', 'Gary', 'Johnson', '', 'gjohn2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800526727', 'Marlin', 'Johnson', '', 'mjohn11@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800561045', 'Raymond', 'Johnson', '', 'rjohn9@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800355514', 'Tianna', 'Johnson', '', 'tjoh0707@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800470579', 'Jaquawn', 'Johnson-Tisdale', '', 'jjohn27@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800610580', 'Briana', 'Jones', '', 'bjone9@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800505432', 'Fanta', 'Kaba', '', 'fkaba1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800487717', 'Antoine', 'Keels', '', 'akeel1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800593942', 'Tajanae', 'Keith', '', 'tkeit1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800596652', 'Janiqua', 'Kelley', '', 'jtwit1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800596618', 'Ochena', 'Kelley', '', 'okell2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800100289', 'Starton', 'Kendall', '', 'sken0327@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800588020', 'Dameer', 'King', '', 'dking7@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800454608', 'Jamie Sue', 'King', '', 'jking5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800532154', 'Keara', 'Knight', '', 'kknig1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800477206', 'Aaron', 'Koutsoukos', '', 'akout1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800476399', 'Nicole', 'Kurdziel', '', 'nkurd1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800590970', 'Ajibola', 'Kutti', '', 'akutt2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800476912', 'Stacey', 'Lam', '', 'slam1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800553659', 'Jeanelle', 'Lambre', '', 'jlamb3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800477278', 'Shawn', 'Lantz', '', 'slant1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800420783', 'Elizabeth', 'Lara', '', 'elara1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800481204', 'Corey', 'Lauth', '', 'claut1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800453231', 'Brandon', 'Leisten', '', 'bleis1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800500554', 'Jason', 'Leroy', '', 'jlero2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800562058', 'Coty', 'Lessler', '', 'cless1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800100652', 'Adramein', 'Lopez', '', 'alop1020@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800546958', 'Raquel', 'Lopez', '', 'rlope3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800485375', 'Crystal', 'Lovell', '', 'clove1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800582599', 'Clifton', 'Lyerly', '', 'clyer1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800506903', 'Tasha-Denise', 'Maldonado', '', 'tmald1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800543995', 'Natalia', 'Manhertz', '', 'nmanh1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800453741', 'Curtis', 'Manson', '', 'cmans1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800514380', 'Davina', 'Manson', '', 'dmans2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800423919', 'Annette', 'Manu', '', 'amanu1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800550651', 'Zachery', 'Marcellus', '', 'zmarc1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800543509', 'Julie', 'Marte', '', 'jmart10@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800441582', 'Alexis', 'Martin', '', 'amart5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800587828', 'Chandra', 'Martinez', '', 'cmart12@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800507581', 'Kassandra', 'Martinez', '', 'kmart7@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800510136', 'Nicson', 'Martinez', '', 'nmart2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800486729', 'Nicolas', 'Marulanda-Castro', '', 'nmaru1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800487818', 'Angela', 'Matthews', '', 'amatt1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800485556', 'Nikisha', 'Matthews', '', 'amatt1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', 'E-MAIL ADDRESS IS INCORRECT', 'Active', '2014-01-31'),
('800458961', 'Erin', 'Matuszak', '', 'elong2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800599926', 'Kyle', 'McCarthy', '', 'kmcca5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800506081', 'Ebony', 'McCallum', '', 'emcca3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800369485', 'Ellen', 'McCarthy', '', 'emcca1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800463851', 'Elizabeth', 'Mcculler', '', 'emccu1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800473825', 'Alyshia', 'McElroy', '', 'amcel1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800549732', 'Brianna', 'McEnroe', '', 'bmcen1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800596532', 'Tiffany', 'Medaivilla', '', 'tmeda1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800443185', 'Casey', 'Medina', '', 'cmedi1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800522627', 'Seanmichael', 'Mendetta', '', 'smend1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800512677', 'Matthew', 'Merola', '', 'mmero1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800452741', 'Shantel', 'Merritt', '', 'smerr5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800478966', 'Nathan', 'Metott', '', 'nmeto1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800188288', 'Cynthia', 'Middlebrook', '', 'cmidd2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800609767', 'Shaunte', 'Middleton', '', 'smidd1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800588835', 'Jared', 'Miernik', '', 'jmier1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800510591', 'Kenney', 'Millington', '', 'kmill11@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800560974', 'Shanick', 'Milord', '', 'smilo1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800463982', 'Taryn', 'Mogavero', '', 'tmoga1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800595796', 'Emily', 'Montesino', '', 'emont6@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800396725', 'Andrea', 'Moore', '', 'amoor3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800480252', 'Tonielle', 'Moore', '', 'tmoor2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800454073', 'Wilfredo', 'Morales', '', 'wmora1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800332048', 'Christina', 'Moraza', '', 'cwed0811@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800539560', 'Destiny', 'Morrison', '', 'dmorr2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800545889', 'Tyler', 'Mosely', '', 'tmose1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800438226', 'Daisy', 'Moy', '', 'dmoy1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800594474', 'Mohammad', 'Mughal', '', 'mmugh1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800548375', 'Fernando', 'Muller', '', 'fmull1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800455154', 'Shawn', 'Muller', '', 'smull1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800548338', 'Pierre Henri', 'Namba', '', 'pnamb1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800437036', 'Samantha', 'Neil', '', 'sneil1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800180616', 'Sonia', 'Nelson', '', 'snels2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800577235', 'William', 'Nelson', '', 'wnels1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800362730', 'Jazmine', 'Noble', '', 'jnobl2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800548995', 'Nicole', 'Nyachae', '', 'nnyac1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800506062', 'Trezmalay', 'Oluwilliams', '', 'toluw1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800598017', 'Adwoa', 'Opoku-Nsiah', '', 'aopok1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800505775', 'Romina Elma', 'Owolewa', '', 'rowol1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800546111', 'Jaliscia', 'Parson', '', 'jpars1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800509135', 'Maria', 'Passy', '', 'mmpas1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800584004', 'Queen Nefertiti', 'Paul-Page', '', 'qpaul1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800510800', 'Edward', 'Pearce', '', 'epear1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800529341', 'Oscar', 'Pecci Perez', '', 'opecc1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800452443', 'Leandro', 'Pena', '', 'lpena1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800488427', 'Amisail', 'Perez', '', 'apere5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800551295', 'Arlene', 'Perez', '', 'apere6@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800529401', 'Connie', 'Perez', '', 'cpere2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800557774', 'Diana', 'Perla', '', 'dperl1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800534635', 'Rachel', 'Phelps', '', 'rphel2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800516588', 'Deanna', 'Poczciwnski', '', 'dplat2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800104410', 'Valerie', 'Pruner', '', 'vwri0420@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800583071', 'Mary', 'Pulluaim', '', 'mpull1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800597856', 'Tammy', 'Pym', '', 'tpym1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800468556', 'Laquasia', 'Richardson', '', 'lrich2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800467507', 'Sharlyn', 'Rios', '', 'srios1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800447417', 'Katherine', 'Rivera', '', 'krive1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800518213', 'Kevin', 'Rivera', '', 'krive2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800485452', 'Noraly', 'Rivera', '', 'nrive3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800521643', 'Shaquille', 'Rivera', '', 'srive1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800510867', 'Deanna', 'Robinson', '', 'drobi3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800593723', 'Jalissa', 'Rodriguez', '', 'jrodr8@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800589962', 'Lizbeth', 'Rodriguez', '', 'lrodr5@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800559656', 'Sandra', 'Rodriguez', '', 'srodr3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800554783', 'Samantha', 'Rogers', '', 'sroge1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800478666', 'Travis', 'Rogers', '', 'troge3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800605236', 'Melissa', 'Rohr', '', 'mrohr1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800553373', 'Marlo', 'Romero', '', 'mrome1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800573730', 'Ryan', 'Rounds', '', 'rroun1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800452831', 'Laniqua', 'Ruffin', '', 'lruff1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800480922', 'Jonathan', 'Samedy', '', 'jsame2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800547280', 'Yassielle', 'Sanchez', '', 'ysanc1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800560913', 'Andre', 'Sanders', '', 'asand4@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800545459', 'Jasmine', 'Sanders', '', 'jsand2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800553408', 'Jonathan', 'Santana', '', 'jsant11@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800552570', 'Jacob', 'Santiago', '', 'jsant10@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800582287', 'Henry', 'Sarpong', '', 'hsarp1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800553672', 'Keana', 'Saunders', '', 'ksaun1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800523014', 'Nicholas', 'Schroeder', '', 'nschr2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800514725', 'Junia', 'Scott', '', 'jscot3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800540733', 'Sachel', 'Scott', '', 'sscot4@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800445070', 'Colton', 'Seeberg', '', 'cseeb1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800213152', 'Ricardo', 'Serrano', '', 'rserr1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800596644', 'Paola', 'Severino', '', 'pseve1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800544131', 'Brian', 'Sevilla', '', 'bsevi1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800599981', 'Andre', 'Shaw', '', 'ashaw1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800461115', 'Daniel', 'Shaw', '', 'dshaw2@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800505295', 'Michelle', 'Singh', '', 'msing3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800429976', 'Brittany', 'Smiley-Jones', '', 'bsmil1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800488511', 'Dennis', 'Smith', '', 'dsmit8@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800520159', 'Andrea', 'Speller', '', 'aspel1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800477725', 'Craig', 'Spencer', '', 'cspen3@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800443987', 'Taniqua', 'Spencer', '', 'tspen1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800502683', 'Taevan', 'Steele', '', 'tstee1@brockport.edu', 'Good Standing', '2014-01-31', '2014-01-31', '0.00', '', 'Active', '2014-01-31'),
('800123456', 'My', 'Name', '716-555-4321', 'myemail@brockport.edu', 'Good Standing', '2014-04-07', '2014-04-05', '0.0', 'notes are good', 'Inactive', '2014-04-07'),
('800438390', 'James', 'Howe', '315-546-4677', 'jhowe2@borkcport.edu', 'Good Standing', '2014-04-05', '2014-04-05', '0.00', 'I see you', 'Active', '2014-04-05'),
('800123123', 'Testy', 'McTesterson', '123-456-7890', 'emseetest@website.com', 'Good Standing', '2014-04-07', '2014-04-06', '0', 'new notes!', 'Inactive', '2014-04-07'),
('800888888', '888', '888', '888-888-8888', '8888888@website.com', 'Good Standing', '2014-04-07', '2014-04-07', '0.00', NULL, 'Inactive', '2014-04-07'),
('800555555', 'Jim', 'Johnson', '555-555-5555', 'jjohnson@h.com', 'Good Standing', '2014-04-07', '2014-04-07', '0.00', NULL, 'Active', '2014-04-07');

-- --------------------------------------------------------

--
-- Table structure for table `worker`
--

CREATE TABLE IF NOT EXISTS `worker` (
  `BannerID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `Password` varchar(25) COLLATE latin1_general_ci NOT NULL,
  `FirstName` varchar(25) COLLATE latin1_general_ci NOT NULL,
  `LastName` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `ContactPhone` varchar(15) COLLATE latin1_general_ci NOT NULL,
  `Email` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `Credentials` enum('Administrator','Ordinary') COLLATE latin1_general_ci NOT NULL DEFAULT 'Ordinary',
  `DateOfLatestCredentialsStatus` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `DateOfHire` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `ActiveStatus` enum('Active','Inactive') COLLATE latin1_general_ci NOT NULL DEFAULT 'Active',
  `DateOfLastUpdate` varchar(12) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`BannerID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `worker`
--

INSERT INTO `worker` (`BannerID`, `Password`, `FirstName`, `LastName`, `ContactPhone`, `Email`, `Credentials`, `DateOfLatestCredentialsStatus`, `DateOfHire`, `ActiveStatus`, `DateOfLastUpdate`) VALUES
('800008948', '128849e34675901c364b', 'Gary', 'Owens', '585-395-2547', 'gowens@brockport.edu', 'Administrator', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800183765', '8bd95f0c03d3614d9fc0', 'Terri', 'Stymus', '585-395-2547', 'tstymus@brockport.edu', 'Administrator', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800029416', '003bff70111118f55f98', 'Dan', 'Askey', '585-395-5438', 'daskey@brockport.edu', 'Ordinary', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800081460', 'cfcd147f5f5dc8b3628d', 'Latasha', 'Craig', '585-395-5437', 'lcraig@brockport.edu', 'Ordinary', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800371086', '7ff7502cd1a474496a18', 'Terrence', 'Harris', '585-395-5434', 'tharris@brockport.edu', 'Ordinary', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800489338', '3f45ca4336e5e53888df', 'Nichole', 'Hall', '585-764-9607', 'nhall2@brockport.edu', 'Ordinary', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800522814', 'd6ea8ee61e1e5da715ae', 'Jackie', 'Valentin', '585-395-4768', 'jvale2@brockport.edu', 'Ordinary', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800541243', '639496345bd6bc2eb939', 'Shaniqua', 'Jafferalli', '585-395-4565', 'sjaff1@brockport.edu', 'Ordinary', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800508177', '0614f69a7322486f04dc', 'Yasmine', 'Obahiagbon', '347-596-1673', 'yobah1@brockport.edu', 'Ordinary', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800532212', 'a07223f7fb3cf395a128', 'Jeleda', 'Stewart', '585-395-4886', 'jstew2@brockport.edu', 'Ordinary', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('root', '96f7a06418e73323a2bc', 'Bob', 'Saget', '555-555-5555', 'mandre112@gmail.com', 'Administrator', '2014-01-30', '2014-01-30', 'Active', '2014-01-30'),
('800321321', '1e10b8977942f0eedfc9', 'Riley', 'Bauer', '321-213-3321', 'rbaue1@brockport.edu', 'Ordinary', '2014-04-07', '2014-04-07', 'Active', '2014-04-07'),
('800123123', '2209f187eab078b0145b', 'Testy', 'McTesterson', '123-456-7890', 'emseetest@website.com', 'Ordinary', '2014-04-06', '2014-04-06', 'Active', '2014-04-06'),
('800000000', '5755d11c82da94e15592', 'tets', 'test', '', 'asdf', 'Administrator', '2014-04-03', '2014-31-03', 'Active', '2014-04-03'),
('800564983', 'e2f42f4f86a5ededaf0d', 'Things', 'Stuff', '123-456-7891', 'stuff@things.net', 'Ordinary', '2014-04-02', '2014-31-02', 'Active', '2014-04-02');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
