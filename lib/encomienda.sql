-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-05-2019 a las 00:53:43
-- Versión del servidor: 10.1.36-MariaDB
-- Versión de PHP: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `encomienda`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `identificador` varchar(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `usuario` varchar(30) NOT NULL,
  `contraseña` varchar(10) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `nivel` int(11) NOT NULL,
  `fecharegistro` date NOT NULL,
  `estado` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `identificador`, `nombre`, `email`, `usuario`, `contraseña`, `telefono`, `nivel`, `fecharegistro`, `estado`) VALUES
(1, '73011001', 'alonso', '1510648@utp.edu.pe', 'alonso', '123', '978527701', 2, '2019-04-03', 1),
(22, '73011004', 'alejandro', 'larcroco3@gmail.com', 'alejandro', '123', '978527704', 2, '2019-04-07', 1),
(23, '73011011', 'luis', '1510647@utp.edu.pe', 'luisalo', '123', '978527705', 1, '2019-04-08', 1),
(25, '73011006', 'yamil', 'c14095@utp.edu.pe', 'yamil', '123', '978527707', 2, '2019-04-11', 1),
(27, '73095001', 'luisdelegado', 'larcroco2@gmail.com', 'luishugo', '123', '979527707', 2, '2019-04-23', 1),
(28, '12345678', 'luis', 'larcroco@gmail.com', 'pancho', '123', '123456789', 2, '2019-04-23', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `disponibilidad`
--

CREATE TABLE `disponibilidad` (
  `id` int(11) NOT NULL,
  `idVehiculo` int(11) NOT NULL,
  `idTipoEncomienda` int(11) NOT NULL,
  `actualvolumen` decimal(6,2) NOT NULL,
  `actualcapacidad` decimal(6,2) NOT NULL,
  `situacion` tinyint(1) NOT NULL DEFAULT '1',
  `fecharegistro` datetime NOT NULL,
  `estado` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `disponibilidad`
--

INSERT INTO `disponibilidad` (`id`, `idVehiculo`, `idTipoEncomienda`, `actualvolumen`, `actualcapacidad`, `situacion`, `fecharegistro`, `estado`) VALUES
(1, 1, 141, '8.30', '1200.00', 1, '2019-05-04 14:57:33', 1),
(61, 2, 183, '0.04', '280.00', 1, '2019-05-05 01:22:47', 1),
(62, 1, 184, '8.31', '1204.00', 1, '2019-05-05 03:43:06', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `enco`
--

CREATE TABLE `enco` (
  `id` int(11) NOT NULL,
  `origen` varchar(30) NOT NULL,
  `destino` varchar(30) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `fr` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `estado` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `encomiendas`
--

CREATE TABLE `encomiendas` (
  `id` int(11) NOT NULL,
  `origen` varchar(50) NOT NULL,
  `destino` varchar(50) NOT NULL,
  `fecharegistro` date NOT NULL,
  `idCliente` int(11) NOT NULL,
  `estado` int(1) NOT NULL DEFAULT '1',
  `fechatime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `encomiendas`
--

INSERT INTO `encomiendas` (`id`, `origen`, `destino`, `fecharegistro`, `idCliente`, `estado`, `fechatime`) VALUES
(1, 'lima', 'ancash', '2019-04-08', 1, 1, '2019-04-08 11:08:04'),
(2, 'lima', 'ancash', '2019-04-08', 1, 1, '2019-04-08 11:21:06'),
(3, 'lima', 'ancash', '2019-04-08', 1, 1, '2019-04-08 11:32:10'),
(4, 'lima', 'ancash', '2019-04-08', 1, 1, '2019-04-08 11:33:34'),
(9, 'amazonas', 'lima', '2019-04-08', 22, 1, '2019-04-08 11:35:17'),
(10, 'apurimac', 'amazonas', '2019-04-08', 22, 1, '2019-04-08 11:37:21'),
(11, 'apurimac', 'ancash', '2019-04-08', 22, 1, '2019-04-08 11:44:59'),
(12, 'amazonas', 'ancash', '2019-04-08', 23, 1, '2019-04-08 11:55:26'),
(81, 'arequipa', 'ancash', '2019-04-23', 23, 1, '2019-04-23 09:42:18'),
(82, 'ancash', 'arequipa', '2019-04-23', 1, 1, '2019-04-23 10:17:05'),
(85, 'arequipa', 'ancash', '2019-04-23', 27, 1, '2019-04-23 08:13:05'),
(93, 'ancash', 'apurimac', '2019-04-23', 27, 1, '2019-04-23 08:48:13'),
(94, 'ancash', 'apurimac', '2019-04-23', 27, 1, '2019-04-23 10:24:09'),
(96, 'apurimac', 'lima', '2019-04-23', 25, 1, '2019-04-23 10:04:00'),
(97, 'lima', 'apurimac', '2019-04-23', 25, 1, '2019-04-24 10:10:04'),
(123, 'arequipa', 'callao', '2019-05-05', 28, 1, '2019-05-05 01:22:47'),
(124, 'amazonas', 'ancash', '2019-05-05', 28, 1, '2019-05-05 03:43:06');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `locales`
--

CREATE TABLE `locales` (
  `id` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `cx` varchar(30) NOT NULL,
  `cy` varchar(30) NOT NULL,
  `idLugar` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `locales`
--

INSERT INTO `locales` (`id`, `titulo`, `cx`, `cy`, `idLugar`, `estado`) VALUES
(1, 'local1', '-12.062731694001265', '77.02901473480313', 8, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugares`
--

CREATE TABLE `lugares` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estado` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `lugares`
--

INSERT INTO `lugares` (`id`, `nombre`, `estado`) VALUES
(1, 'amazonas', 1),
(2, 'ancash', 1),
(3, 'apurimac', 1),
(4, 'arequipa', 1),
(5, 'ayacucho', 1),
(6, 'cajamarca', 1),
(7, 'callao', 1),
(8, 'lima', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiposencomiendas`
--

CREATE TABLE `tiposencomiendas` (
  `id` int(11) NOT NULL,
  `tipo` varchar(30) NOT NULL,
  `altura` double NOT NULL,
  `anchura` double NOT NULL,
  `largo` double NOT NULL,
  `cantidad` int(11) NOT NULL,
  `peso` double NOT NULL,
  `precio` double NOT NULL,
  `delicado` tinyint(1) NOT NULL DEFAULT '0',
  `fecharegistro` date NOT NULL,
  `idEncomienda` int(11) NOT NULL,
  `estado` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tiposencomiendas`
--

INSERT INTO `tiposencomiendas` (`id`, `tipo`, `altura`, `anchura`, `largo`, `cantidad`, `peso`, `precio`, `delicado`, `fecharegistro`, `idEncomienda`, `estado`) VALUES
(141, 'paquete', 10, 12, 14, 2, 175, 70, 0, '2019-04-23', 81, 1),
(142, 'paquete', 10, 15, 15, 4, 225, 180, 0, '2019-04-23', 82, 1),
(145, 'sobre', 0, 0, 0, 1, 0.01, 10, 0, '2019-04-23', 85, 1),
(153, 'sobre', 0, 0, 0, 1, 0.25, 10, 0, '2019-04-23', 93, 1),
(154, 'sobre', 0, 0, 0, 10, 1.13, 100, 0, '2019-04-23', 94, 1),
(156, 'sobre', 0, 0, 0, 20, 1.12, 200, 0, '2019-04-23', 96, 1),
(157, 'sobre', 0, 0, 0, 2, 0.1, 20, 0, '2019-04-23', 97, 1),
(183, 'paquete', 15, 5, 14, 5, 80, 50, 1, '2019-05-05', 123, 1),
(184, 'paquete', 15, 5, 14, 5, 4, 50, 1, '2019-05-05', 124, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculos`
--

CREATE TABLE `vehiculos` (
  `id` int(11) NOT NULL,
  `placa` varchar(10) NOT NULL,
  `volumen` decimal(6,2) NOT NULL,
  `capacidad` decimal(6,2) NOT NULL,
  `actualvolumen` decimal(6,2) NOT NULL,
  `actualcapacidad` decimal(6,2) NOT NULL,
  `ocupado` tinyint(1) NOT NULL DEFAULT '1',
  `fechatime` datetime NOT NULL,
  `estado` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `vehiculos`
--

INSERT INTO `vehiculos` (`id`, `placa`, `volumen`, `capacidad`, `actualvolumen`, `actualcapacidad`, `ocupado`, `fechatime`, `estado`) VALUES
(1, 'PE1326', '9.30', '1400.00', '8.00', '1300.00', 1, '2019-05-01 04:13:23', 1),
(2, 'PE1327', '8.60', '1200.00', '0.06', '202.18', 1, '2019-05-01 04:14:17', 1),
(3, 'PE1328', '6.00', '1390.00', '2.00', '300.00', 1, '2019-05-01 05:22:00', 1),
(4, 'PE1329', '9.30', '1400.00', '6.06', '1393.75', 1, '2019-05-01 05:22:12', 1),
(5, 'PE1330', '3.10', '1310.00', '4.00', '16.00', 1, '2019-05-01 05:24:00', 1),
(6, 'PE1331', '1.20', '1100.00', '1.00', '800.00', 1, '2019-05-01 05:24:13', 1),
(10, 'a22', '11.12', '12.12', '0.00', '0.00', 1, '2019-05-01 22:06:50', 1),
(11, 'a22', '11.12', '12.10', '0.00', '0.00', 1, '2019-05-01 22:07:21', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `disponibilidad`
--
ALTER TABLE `disponibilidad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idVehiculo` (`idVehiculo`),
  ADD KEY `idTipoEncomienda` (`idTipoEncomienda`);

--
-- Indices de la tabla `enco`
--
ALTER TABLE `enco`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `encomiendas`
--
ALTER TABLE `encomiendas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idCliente` (`idCliente`);

--
-- Indices de la tabla `locales`
--
ALTER TABLE `locales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idLugar` (`idLugar`);

--
-- Indices de la tabla `lugares`
--
ALTER TABLE `lugares`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tiposencomiendas`
--
ALTER TABLE `tiposencomiendas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idEncomienda` (`idEncomienda`);

--
-- Indices de la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT de la tabla `disponibilidad`
--
ALTER TABLE `disponibilidad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT de la tabla `encomiendas`
--
ALTER TABLE `encomiendas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;

--
-- AUTO_INCREMENT de la tabla `locales`
--
ALTER TABLE `locales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `lugares`
--
ALTER TABLE `lugares`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `tiposencomiendas`
--
ALTER TABLE `tiposencomiendas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=185;

--
-- AUTO_INCREMENT de la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `disponibilidad`
--
ALTER TABLE `disponibilidad`
  ADD CONSTRAINT `disponibilidad_ibfk_1` FOREIGN KEY (`idVehiculo`) REFERENCES `vehiculos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `disponibilidad_ibfk_2` FOREIGN KEY (`idTipoEncomienda`) REFERENCES `tiposencomiendas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `encomiendas`
--
ALTER TABLE `encomiendas`
  ADD CONSTRAINT `encomiendas_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `locales`
--
ALTER TABLE `locales`
  ADD CONSTRAINT `locales_ibfk_1` FOREIGN KEY (`idLugar`) REFERENCES `lugares` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `tiposencomiendas`
--
ALTER TABLE `tiposencomiendas`
  ADD CONSTRAINT `tiposencomiendas_ibfk_1` FOREIGN KEY (`idEncomienda`) REFERENCES `encomiendas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
