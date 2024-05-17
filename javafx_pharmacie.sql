-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 17 mai 2024 à 12:51
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `javafx_pharmacie`
--

-- --------------------------------------------------------

--
-- Structure de la table `medicament`
--

CREATE TABLE `medicament` (
  `code_med` int(11) NOT NULL,
  `nom_med` varchar(255) NOT NULL,
  `prix_med` double NOT NULL,
  `stock_med` int(11) NOT NULL,
  `type_med` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `medicament`
--

INSERT INTO `medicament` (`code_med`, `nom_med`, `prix_med`, `stock_med`, `type_med`) VALUES
(1, 'Aspirine', 5.99, 100, 'Normal'),
(2, 'Paracétamol', 3.49, 200, 'Normal'),
(3, 'Insuline', 25.75, 45, 'Special'),
(4, 'delip', 8, 8, 'Special'),
(6, 'test', 20, 25, 'Special');

-- --------------------------------------------------------

--
-- Structure de la table `patient`
--

CREATE TABLE `patient` (
  `codepatient` int(11) NOT NULL,
  `nompatient` varchar(255) NOT NULL,
  `telpatient` varchar(255) DEFAULT NULL,
  `emailpatient` varchar(255) DEFAULT NULL,
  `date_nais` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `patient`
--

INSERT INTO `patient` (`codepatient`, `nompatient`, `telpatient`, `emailpatient`, `date_nais`) VALUES
(1, 'yasine', '123', 'email', '2004-05-10'),
(2, 'yassine saa', '123', 'yassine@email.tn', '2007-05-11'),
(4, 'ahmed', '911', 'mohamedT@tt.tn', '1996-05-18'),
(7, 'amine', '50199271', 'amineissaoui1919@gmail.com', '2024-05-17'),
(8, 'aze', '111111111', 'zae', '2024-05-15');

-- --------------------------------------------------------

--
-- Structure de la table `patientmed`
--

CREATE TABLE `patientmed` (
  `id` int(11) NOT NULL,
  `codepatient` int(11) NOT NULL,
  `code_med` int(11) NOT NULL,
  `qte` int(11) NOT NULL,
  `date` date NOT NULL,
  `payer` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `patientmed`
--

INSERT INTO `patientmed` (`id`, `codepatient`, `code_med`, `qte`, `date`, `payer`) VALUES
(6, 2, 4, 2, '2024-05-17', 16);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nom_user` varchar(255) NOT NULL,
  `tel` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `nom_user`, `tel`) VALUES
(1, 'admin@gmail.com', '$2a$10$DDUIX5kofybLkM/18cj2Beq1xmFYvdk4IAhRLEZFfUHDHHg/b1HxW', 'admin', '911');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `medicament`
--
ALTER TABLE `medicament`
  ADD PRIMARY KEY (`code_med`);

--
-- Index pour la table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`codepatient`);

--
-- Index pour la table `patientmed`
--
ALTER TABLE `patientmed`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_achat_pat` (`codepatient`),
  ADD KEY `FK_achat_med` (`code_med`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Unique_Email` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `medicament`
--
ALTER TABLE `medicament`
  MODIFY `code_med` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `patient`
--
ALTER TABLE `patient`
  MODIFY `codepatient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `patientmed`
--
ALTER TABLE `patientmed`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `patientmed`
--
ALTER TABLE `patientmed`
  ADD CONSTRAINT `FK_achat_med` FOREIGN KEY (`code_med`) REFERENCES `medicament` (`code_med`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_achat_pat` FOREIGN KEY (`codepatient`) REFERENCES `patient` (`codepatient`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
