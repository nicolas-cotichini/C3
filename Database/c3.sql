-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 02, 2022 alle 11:49
-- Versione del server: 10.4.18-MariaDB
-- Versione PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `c3`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `acquisto`
--

CREATE TABLE `acquisto` (
  `id` bigint(20) NOT NULL,
  `data` varchar(255) DEFAULT NULL,
  `dimensione` int(11) DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  `id_commerciante` bigint(20) DEFAULT NULL,
  `id_negozio` bigint(20) DEFAULT NULL,
  `nome_negozio` varchar(255) DEFAULT NULL,
  `ordinato` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `amministratore`
--

CREATE TABLE `amministratore` (
  `id` bigint(20) NOT NULL,
  `attivo` bit(1) NOT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ruolo` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `amministratore`
--

INSERT INTO `amministratore` (`id`, `attivo`, `cognome`, `email`, `nome`, `password`, `ruolo`) VALUES
(1, b'1', 'Tore', 'amministratore@ccc.com', 'Amministra', '$2a$10$BWYDfzolflBOavloFRb2LOTy3pZv3PUD5L1OOXCVUi2rn89opiLp2', 'AMMINISTRATORE');

-- --------------------------------------------------------

--
-- Struttura della tabella `cella`
--

CREATE TABLE `cella` (
  `id` bigint(20) NOT NULL,
  `id_locker` bigint(20) DEFAULT NULL,
  `id_ordine` bigint(20) DEFAULT NULL,
  `libero` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `cella`
--

INSERT INTO `cella` (`id`, `id_locker`, `id_ordine`, `libero`, `password`) VALUES
(9, 8, NULL, b'1', '82367096'),
(10, 8, NULL, b'1', '07402721'),
(11, 8, NULL, b'1', '42909020'),
(12, 8, NULL, b'1', '85324971'),
(13, 8, NULL, b'1', '06944701'),
(14, 8, NULL, b'1', '57874637'),
(15, 8, NULL, b'1', '54997173'),
(16, 8, NULL, b'1', '53121654'),
(17, 8, NULL, b'1', '04583776'),
(18, 8, NULL, b'1', '58213003'),
(19, 8, NULL, b'1', '15894139'),
(20, 8, NULL, b'1', '12964842'),
(21, 8, NULL, b'1', '30574400'),
(22, 8, NULL, b'1', '54216585'),
(23, 8, NULL, b'1', '16640698'),
(24, 8, NULL, b'1', '84728375'),
(25, 8, NULL, b'1', '50973430'),
(26, 8, NULL, b'1', '09160435'),
(27, 8, NULL, b'1', '20989524'),
(28, 8, NULL, b'1', '38146975'),
(29, 8, NULL, b'1', '88855295'),
(30, 8, NULL, b'1', '62022145'),
(31, 8, NULL, b'1', '79284073'),
(32, 8, NULL, b'1', '44504985'),
(33, 8, NULL, b'1', '72160426'),
(34, 8, NULL, b'1', '34653186'),
(35, 8, NULL, b'1', '94141954'),
(36, 8, NULL, b'1', '56939670'),
(37, 8, NULL, b'1', '12902249'),
(38, 8, NULL, b'1', '97429236');

-- --------------------------------------------------------

--
-- Struttura della tabella `cliente`
--

CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL,
  `attivo` bit(1) NOT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ruolo` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `cliente`
--

INSERT INTO `cliente` (`id`, `attivo`, `cognome`, `email`, `nome`, `password`, `ruolo`) VALUES
(2, b'1', 'Cognome', 'cliente@gmail.com', 'Cliente', '$2a$10$Qzb/1iwyMs7gTy7doyHCY.lhG4K9tY9A96jHBleraEaZhviSGhPAy', 'CLIENTE');

-- --------------------------------------------------------

--
-- Struttura della tabella `commerciante`
--

CREATE TABLE `commerciante` (
  `id` bigint(20) NOT NULL,
  `attivo` bit(1) NOT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ruolo` varchar(255) DEFAULT NULL,
  `id_negozio` bigint(20) DEFAULT NULL,
  `iva` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `commerciante`
--

INSERT INTO `commerciante` (`id`, `attivo`, `cognome`, `email`, `nome`, `password`, `ruolo`, `id_negozio`, `iva`) VALUES
(3, b'1', 'Commerciante', 'commerciante@ccc.com', 'Commerciante', '$2a$10$.OQw5FeEJtTXQWi8A8ueTevpo0dcQAT6pCaVFGv.w7XLKh9FCnU3W', 'COMMERCIANTE', 4, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `corriere`
--

CREATE TABLE `corriere` (
  `id` bigint(20) NOT NULL,
  `attivo` bit(1) NOT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ruolo` varchar(255) DEFAULT NULL,
  `operativo` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `corriere`
--

INSERT INTO `corriere` (`id`, `attivo`, `cognome`, `email`, `nome`, `password`, `ruolo`, `operativo`) VALUES
(5, b'1', 'Corriere', 'corriere@ccc.com', 'Corriere', '$2a$10$haSNXlkIUp1gDAn4NpxI7.h0ZFfA.AVQqZGXgCGhrsuqVISx8Fpli', 'CORRIERE', b'0');

-- --------------------------------------------------------

--
-- Struttura della tabella `corriere_lista_consegne`
--

CREATE TABLE `corriere_lista_consegne` (
  `corriere_id` bigint(20) NOT NULL,
  `lista_consegne_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(40);

-- --------------------------------------------------------

--
-- Struttura della tabella `interf_locker`
--

CREATE TABLE `interf_locker` (
  `id` bigint(20) NOT NULL,
  `attivo` bit(1) NOT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ruolo` varchar(255) DEFAULT NULL,
  `id_locker` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `interf_locker`
--

INSERT INTO `interf_locker` (`id`, `attivo`, `cognome`, `email`, `nome`, `password`, `ruolo`, `id_locker`) VALUES
(39, b'1', 'Andromeda', 'interfaccialockerandromeda@ccc.com', 'Locker', '$2a$10$RvQtA1/Nl/24RnBz/yps..h31Jj0ZDmIL28KGMWyCXt2kfbhIPR6S', 'INTLOCKER', 8);

-- --------------------------------------------------------

--
-- Struttura della tabella `locker`
--

CREATE TABLE `locker` (
  `id` bigint(20) NOT NULL,
  `indirizzo` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `interfaccia` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `locker`
--

INSERT INTO `locker` (`id`, `indirizzo`, `nome`, `note`, `tipo`, `interfaccia`) VALUES
(8, 'via dei Locker 12/b', 'Locker Andromeda', '', 'LOCKER', b'1');

-- --------------------------------------------------------

--
-- Struttura della tabella `magazziniere`
--

CREATE TABLE `magazziniere` (
  `id` bigint(20) NOT NULL,
  `attivo` bit(1) NOT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ruolo` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `magazziniere`
--

INSERT INTO `magazziniere` (`id`, `attivo`, `cognome`, `email`, `nome`, `password`, `ruolo`) VALUES
(6, b'1', 'Magazziniere', 'magazziniere@gmail.com', 'Magazziniere', '$2a$10$UvM1Jr9Em8Tua3Srrvhq..tArjIGgOjpK4E1WoxYzBXctShvwdBxW', 'MAGAZZINIERE');

-- --------------------------------------------------------

--
-- Struttura della tabella `magazzino`
--

CREATE TABLE `magazzino` (
  `id` bigint(20) NOT NULL,
  `indirizzo` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `orario_apertura` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `magazzino`
--

INSERT INTO `magazzino` (`id`, `indirizzo`, `nome`, `note`, `tipo`, `orario_apertura`) VALUES
(7, 'via dei Magazzini 12/b', 'Magazzino Fenice', '', 'MAGAZZINO', '');

-- --------------------------------------------------------

--
-- Struttura della tabella `merce`
--

CREATE TABLE `merce` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `merce_lista_negozi`
--

CREATE TABLE `merce_lista_negozi` (
  `merce_id` bigint(20) NOT NULL,
  `lista_negozi` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `negozio`
--

CREATE TABLE `negozio` (
  `id` bigint(20) NOT NULL,
  `indirizzo` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `id_commerciante` bigint(20) NOT NULL,
  `orario_apertura` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `negozio`
--

INSERT INTO `negozio` (`id`, `indirizzo`, `nome`, `note`, `tipo`, `id_commerciante`, `orario_apertura`) VALUES
(4, 'via dei Negozi 12/b', 'Negozio2', '', 'NEGOZIO', 3, '');

-- --------------------------------------------------------

--
-- Struttura della tabella `ordine`
--

CREATE TABLE `ordine` (
  `id` bigint(20) NOT NULL,
  `data` varchar(255) DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  `id_luogo_consegna` bigint(20) DEFAULT NULL,
  `stato` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `ordine_lista_acquisto`
--

CREATE TABLE `ordine_lista_acquisto` (
  `ordine_id` bigint(20) NOT NULL,
  `lista_acquisto_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `acquisto`
--
ALTER TABLE `acquisto`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `amministratore`
--
ALTER TABLE `amministratore`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_it2ikvhl4nyd2kc5sseg8dtp1` (`email`);

--
-- Indici per le tabelle `cella`
--
ALTER TABLE `cella`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_cmxo70m08n43599l3h0h07cc6` (`email`);

--
-- Indici per le tabelle `commerciante`
--
ALTER TABLE `commerciante`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6t0cwf2fpmpntc9p7wl71dbwm` (`email`),
  ADD UNIQUE KEY `UK_q36pori5651ylncb9yve8jh7r` (`iva`) USING HASH;

--
-- Indici per le tabelle `corriere`
--
ALTER TABLE `corriere`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ljg88f9ykuu1fu4b7xg20480g` (`email`);

--
-- Indici per le tabelle `corriere_lista_consegne`
--
ALTER TABLE `corriere_lista_consegne`
  ADD UNIQUE KEY `UK_j8lnp576rqviw9epxees8hr25` (`lista_consegne_id`),
  ADD KEY `FK1lq8v7cq1bgt3od9jaojs9bm8` (`corriere_id`);

--
-- Indici per le tabelle `interf_locker`
--
ALTER TABLE `interf_locker`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_1t0o8altdjfon7qsce5ewmghc` (`email`);

--
-- Indici per le tabelle `locker`
--
ALTER TABLE `locker`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `magazziniere`
--
ALTER TABLE `magazziniere`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_lymar7qox1s2r4p2i5hbb36e7` (`email`);

--
-- Indici per le tabelle `magazzino`
--
ALTER TABLE `magazzino`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `merce`
--
ALTER TABLE `merce`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_gl443o7gnulhb4bmpwatbtm23` (`nome`) USING HASH;

--
-- Indici per le tabelle `merce_lista_negozi`
--
ALTER TABLE `merce_lista_negozi`
  ADD KEY `FKdcuxl8dudb78f5uv6nxkf7erb` (`merce_id`);

--
-- Indici per le tabelle `negozio`
--
ALTER TABLE `negozio`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `ordine`
--
ALTER TABLE `ordine`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `ordine_lista_acquisto`
--
ALTER TABLE `ordine_lista_acquisto`
  ADD UNIQUE KEY `UK_5eo6kv8xuj0mvnbtmxbhot5i6` (`lista_acquisto_id`),
  ADD KEY `FKmpv5c8ar6cx1fua1dh4lf2mqu` (`ordine_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
