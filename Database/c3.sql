-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 16, 2022 alle 19:38
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
(1, b'1', 'Tore', 'amministratore@ccc.com', 'Amministra', '$2a$10$ib5nAq6oZHgXr3TPnxk.zuqQbLaZZZ6Yx03FzoIFR91kdzyExkLU.', 'AMMINISTRATORE');

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
(9, 8, NULL, b'1', '5d161942ea72acc0b78948f639037da157c903d467755c42917391e394d4ce80'),
(10, 8, NULL, b'1', 'c4bcbac228a4ba98554c6c6159aa94f17153234f2d6fba4cd8360a8b62719d09'),
(11, 8, NULL, b'1', '478ca66f6cd11ad13bcb7fd19777abcc0d74f9ed4c5dd20bb53d480eb2c9f4c8'),
(12, 8, NULL, b'1', '009d5391b8556bac20165d580dec5d6207e4a9b9091ec268081a035607857c76'),
(13, 8, NULL, b'1', '9df9a78cea87992667510444d3119e39e1e45f46b247d0a45848ce5be465ab4b'),
(14, 8, NULL, b'1', '1c0a7f4f91fef4fecbaafe630b2aacad0f68c479dce2b00de2e5496cf5a7ceee'),
(15, 8, NULL, b'1', '08bae41b9a7e0819963af4242e969d83b3aee0387cd6c47dacd3c7be19171e8d'),
(16, 8, NULL, b'1', 'dc447e11698b8bfcabbdefbbaa65db69fc90f873c684fb4e457a2a6944bc60a2'),
(17, 8, NULL, b'1', '998b909e37eae46fce4411cd80f6d3b0ab47d2317587e94459000174ccf0f63d'),
(18, 8, NULL, b'1', '76f1f8d0e27f8e6513935f26f00a6503f3f56085691e36ec7b80f55723facdf8'),
(19, 8, NULL, b'1', '89551092131da289c66336163af333dcca7cd7fb5500bdd7555ac00822e3be03'),
(20, 8, NULL, b'1', 'd8a18da1da6f572f284bf01e818bc6e7402c27c349f7135d7b9016b801bc5f25'),
(21, 8, NULL, b'1', '0f58c78b36876d8e11b907e1e5c4df3413da84cbcb820bcc32d3419fbd2b4ea8'),
(22, 8, NULL, b'1', '390a9b8bb2c23cd88bcea5c9624c2a4dc2a2488ce55b8e887987cc82eb018f96'),
(23, 8, NULL, b'1', '912a272b1eee51d6abc3b2fefb13d726e76c4deb15605ada40e83e417f06f917'),
(24, 8, NULL, b'1', '520d05c8bc18bbf5cfa3e291b63b30b30e3e230f466a0d011c24552b4848999c'),
(25, 8, NULL, b'1', '08dc07791d3798cd71658356fad0a11a5a3fb288bfe16ed3c0f4083a205152e9'),
(26, 8, NULL, b'1', 'ae599445bf54a03cf6c5819def3fb7de893a751ceb92a9f28f90318ff1670ef4'),
(27, 8, NULL, b'1', 'b3687ece1e0063b28472851065aff93d11fff64f05d1654bd61ba39e27f32812'),
(28, 8, NULL, b'1', 'c114e39e5369d5953fe7a356032bfed26ae56cbdbec8d1c787b184a95d7faf74'),
(29, 8, NULL, b'1', 'aa4cefdf035763d64b6e153b574885cba98656f9d47bd593c52d075f6430a9c1'),
(30, 8, NULL, b'1', '2b7a9f15148ffdf52772aa12a9eb1a261655a2478ef74c897389d9c8655a1127'),
(31, 8, NULL, b'1', '0698a2982a2edd3c4503c470f769eee8d5bf040a4218dbfbb9f45ca690ecd7ec'),
(32, 8, NULL, b'1', '7fd13b41f23aae70f85f1e512b8b493afd241226094f77cf70791ad65c4c0a35'),
(33, 8, NULL, b'1', '9e48b38d233bae7272bbeca9af139d109e8d5d74b354b8961d0115aadaf40706'),
(34, 8, NULL, b'1', '3d9ea6f920ff76d37d1abd19faaf3958034c5a54e2e4f48bdad914e12fdd0d93'),
(35, 8, NULL, b'1', '1e7fa8f2f6fe2191dba1f1d41c4408c1d27df5b13b50088d2dc308150c7f95d0'),
(36, 8, NULL, b'1', '90999f9871772cc4f72b2ef74e6d414a7054919383d7a49c22dec85a83ec553b'),
(37, 8, NULL, b'1', '46c44bcb545fb3b1f8705e89d4d302c931192385d435061757e56eb2e3a51e7c'),
(38, 8, NULL, b'1', '0cee07564d697a3005576efb0ea7b9475fbeb2eae271ea3ee64eb6f481b3a818');

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
(2, b'1', 'Cognome', 'cliente@gmail.com', 'Cliente', '$2a$10$ubCn6M8PQP6M8BwA1StzPOB2M3.dFAyDunmvGEdxP8XPYMg66ZQwK', 'CLIENTE');

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
(3, b'1', 'Commerciante', 'commerciante@ccc.com', 'Commerciante', '$2a$10$4iZv7G.ni9jTIZX9baN9hONvyv15iA0OD/MI1.TRp8Sg9h.WgVSam', 'COMMERCIANTE', 4, NULL);

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
(5, b'1', 'Corriere', 'corriere@ccc.com', 'Corriere', '$2a$10$ZypgBLFGb7Q57gw.2JiB0.Y2FWRk8dv6c220i2zP8oYiKTv6q/KIi', 'CORRIERE', b'0');

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
(39, b'1', 'Andromeda', 'interflockadromeda@ccc.com', 'Locker', '$2a$10$B6A8dzJw3sMxDHaxMDFkPu605GPRcT/kx3418.ylXNRzPOLtPmR52', 'INTLOCKER', 8);

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
(6, b'1', 'Magazziniere', 'magazziniere@ccc.com', 'Magazziniere', '$2a$10$6NbgqWry.F54Vub6hRO/dOrLrWxHlqXOcAgCy6S0PYydcZLHfaF7K', 'MAGAZZINIERE');

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
