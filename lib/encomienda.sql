-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-04-2019 a las 20:33:03
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
  `fecharegistro` date NOT NULL,
  `estado` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `identificador`, `nombre`, `email`, `usuario`, `contraseña`, `telefono`, `fecharegistro`, `estado`) VALUES
(1, '73011001', 'larc', 'larcroco1@gmail.com', 'larc', '123', '978527701', '2019-04-03', 1),
(2, '73011002', 'nicolas', 'polonorte@gmail.com', 'nicolas', '123', '978527702', '2019-04-03', 1),
(21, '73011003', 'larcc', 'larcroco2@gmail.com', 'larcc', '123', '978527703', '2019-04-07', 1),
(22, '73011004', 'larccc', 'larcroco3@gmail.com', 'larccc', '123', '978527704', '2019-04-07', 1),
(23, '73011012', 'luis', 'larcroco@gmail.com', 'luis', '123', '978527705', '2019-04-08', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `encomiendas`
--

CREATE TABLE `encomiendas` (
  `id` int(11) NOT NULL,
  `origen` varchar(50) NOT NULL,
  `destino` varchar(50) NOT NULL,
  `envio` date NOT NULL,
  `llegada` date NOT NULL,
  `fecharegistro` date NOT NULL,
  `idCliente` int(11) NOT NULL,
  `estado` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `encomiendas`
--

INSERT INTO `encomiendas` (`id`, `origen`, `destino`, `envio`, `llegada`, `fecharegistro`, `idCliente`, `estado`) VALUES
(1, 'lima', 'puno', '2019-04-09', '2019-04-10', '2019-04-08', 1, 1),
(2, 'lima', 'ica', '2019-04-09', '2019-04-10', '2019-04-08', 1, 1),
(3, 'lima', 'loreto', '2019-04-09', '2019-04-10', '2019-04-08', 1, 1),
(4, 'lima', 'arequipa', '2019-04-09', '2019-04-10', '2019-04-08', 1, 1),
(9, 'ab', 'aab', '2019-04-09', '2018-08-13', '2019-04-08', 22, 1),
(10, 'Lima', 'Madre de Dios', '2019-04-12', '2018-08-15', '2019-04-08', 22, 1),
(11, 'Lima', 'Cuzco', '2019-04-12', '2018-08-15', '2019-04-08', 22, 1),
(12, 'abril', 'abril', '2019-04-12', '2018-08-15', '2019-04-08', 23, 1),
(13, 'punoa', 'lima', '2019-04-12', '2018-08-17', '2019-04-08', 23, 1),
(14, 'a', 'a', '2019-04-12', '2018-08-18', '2019-04-08', 23, 1);

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
  `fecharegistro` date NOT NULL,
  `idEncomienda` int(11) NOT NULL,
  `estado` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tiposencomiendas`
--

INSERT INTO `tiposencomiendas` (`id`, `tipo`, `altura`, `anchura`, `largo`, `cantidad`, `peso`, `precio`, `fecharegistro`, `idEncomienda`, `estado`) VALUES
(1, 'sobre', 1.6, 60, 70, 1, 12, 30, '2019-04-08', 13, 1),
(2, 'paquete', 1.2, 1.2, 1.2, 1, 12, 1.2, '2019-04-08', 13, 1),
(3, 'paquete', 1, 1, 2, 3, 4, 2, '2019-04-08', 14, 1),
(4, 'sobre', 0, 0, 0, 2223, 3, 3, '2019-04-09', 14, 1),
(5, 'sobre', 0, 0, 0, 11111, 11111, 1111, '2019-04-09', 14, 1),
(6, 'sobre', 0, 0, 0, 555, 555, 555, '2019-04-09', 14, 1),
(7, 'sobre', 0, 0, 0, 555, 555, 555, '2019-04-09', 14, 1),
(8, 'sobre', 0, 0, 0, 555, 555, 555, '2019-04-09', 14, 1),
(9, 'sobre', 0, 0, 0, 555, 555, 555, '2019-04-09', 14, 1),
(10, 'sobre', 0, 0, 0, 7774, 777, 777, '2019-04-09', 14, 1),
(12, 'paquete', 2, 2, 3, 1, 2, 3, '2019-04-09', 14, 1),
(13, 'sobre', 0, 0, 0, 5, 321, 111, '2019-04-09', 14, 1),
(14, 'paquete', 123, 333, 333, 333, 333, 2222, '2019-04-09', 14, 1),
(15, 'paquete', 123, 333, 333, 333, 333, 2222, '2019-04-09', 14, 1),
(17, 'paquete', 123, 333, 333, 333, 333, 111, '2019-04-09', 14, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculos`
--

CREATE TABLE `vehiculos` (
  `id` int(11) NOT NULL,
  `placa` varchar(10) NOT NULL,
  `capacidad` double NOT NULL,
  `idEncomienda` int(11) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `vehiculos`
--

INSERT INTO `vehiculos` (`id`, `placa`, `capacidad`, `idEncomienda`, `estado`) VALUES
(1, 'PE1329', 70, 0, 1),
(2, 'PE1327', 70, 0, 1),
(3, 'PE1328', 140, 0, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `encomiendas`
--
ALTER TABLE `encomiendas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idCliente` (`idCliente`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `encomiendas`
--
ALTER TABLE `encomiendas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `tiposencomiendas`
--
ALTER TABLE `tiposencomiendas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `encomiendas`
--
ALTER TABLE `encomiendas`
  ADD CONSTRAINT `encomiendas_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `tiposencomiendas`
--
ALTER TABLE `tiposencomiendas`
  ADD CONSTRAINT `tiposencomiendas_ibfk_1` FOREIGN KEY (`idEncomienda`) REFERENCES `encomiendas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
