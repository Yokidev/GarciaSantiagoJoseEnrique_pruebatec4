-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-02-2024 a las 00:42:06
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agency`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `client`
--

CREATE TABLE `client` (
  `id` bigint(20) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `identification` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `client`
--

INSERT INTO `client` (`id`, `birthdate`, `identification`, `name`, `surname`) VALUES
(1, '2000-02-03', '2510896', 'Pedro', 'Peco'),
(2, '1989-02-03', '513669', 'Pepa', 'Zeta'),
(52, '2000-02-03', '0010896', 'Alberto', 'Perez'),
(53, '1989-02-03', '56969', 'Paco', 'Zeta'),
(102, '1999-02-03', '5554474', 'Lucia', 'Zeta'),
(152, '2000-02-03', '360896', 'Oliver', 'Perez'),
(202, '2000-02-03', '3578876', 'Marta', 'Perez'),
(203, '2000-02-03', '35000006', 'Luis', 'Oliva'),
(252, '2000-02-03', '33333876', 'Fran', 'Perez'),
(253, '2000-02-03', '3506', 'Maria', 'Oliva'),
(302, '2000-02-03', 'hkhjku', 'Fran', 'Perez'),
(303, '2000-02-03', 'kjhk', 'Maria', 'Oliva'),
(352, '2000-02-03', '555555', 'Pablo', 'Oliva'),
(402, '2000-02-03', '76', 'Ana', 'Perez'),
(403, '2000-02-03', '0006', 'Olga', 'Oliva'),
(452, '2000-02-03', '77477', 'Paco', 'Perez'),
(453, '2000-02-03', '33658', 'Maria', 'Oliva'),
(502, '1984-12-30', '325666', 'Lebron', 'James');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `client_seq`
--

CREATE TABLE `client_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `client_seq`
--

INSERT INTO `client_seq` (`next_val`) VALUES
(601);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight`
--

CREATE TABLE `flight` (
  `id` bigint(20) NOT NULL,
  `booked_seat` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `total_seat` int(11) DEFAULT NULL,
  `type_seat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `flight`
--

INSERT INTO `flight` (`id`, `booked_seat`, `code`, `date`, `destination`, `origin`, `price`, `total_seat`, `type_seat`) VALUES
(4, 2, 'TOKSEU-365', '2024-06-22', 'Seul', 'Tokio', 1800, 6, 'Luxury'),
(5, 0, 'MADLOS-225', '2024-05-22', 'Los Angeles', 'Madrid', 2800, 7, 'Luxury'),
(52, 0, 'TOKSEU-672', '2024-08-22', 'Seul', 'Tokio', 800, 26, 'Bussiness'),
(102, 0, 'SEVOVI-440', '2024-08-22', 'Oviedo', 'Sevilla', 800, 26, 'Bussiness');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_booking`
--

CREATE TABLE `flight_booking` (
  `id` bigint(20) NOT NULL,
  `name_client` varchar(255) DEFAULT NULL,
  `number_tickets` int(11) DEFAULT NULL,
  `flight_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `flight_booking`
--

INSERT INTO `flight_booking` (`id`, `name_client`, `number_tickets`, `flight_id`) VALUES
(202, 'Marta', 2, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_booking_client`
--

CREATE TABLE `flight_booking_client` (
  `flightbooking_id` bigint(20) NOT NULL,
  `client_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `flight_booking_client`
--

INSERT INTO `flight_booking_client` (`flightbooking_id`, `client_id`) VALUES
(202, 202),
(202, 203);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_booking_seq`
--

CREATE TABLE `flight_booking_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `flight_booking_seq`
--

INSERT INTO `flight_booking_seq` (`next_val`) VALUES
(451);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_seq`
--

CREATE TABLE `flight_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `flight_seq`
--

INSERT INTO `flight_seq` (`next_val`) VALUES
(251);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`id`, `city`, `name`) VALUES
(2, 'Madrid', 'NH'),
(52, 'Valencia', 'Lebreros'),
(102, 'Barcelona', 'Imperio'),
(152, 'Sevilla', 'Vertice'),
(153, 'Oviedo', 'Palacios'),
(202, 'Sevilla', 'NH');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel_seq`
--

CREATE TABLE `hotel_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `hotel_seq`
--

INSERT INTO `hotel_seq` (`next_val`) VALUES
(301);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login_user`
--

CREATE TABLE `login_user` (
  `id` bigint(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `login_user`
--

INSERT INTO `login_user` (`id`, `password`, `user_name`, `user`) VALUES
(1, '{bcrypt}$2a$04$J.OgvVpXZCkr0UunWPlaQ.Pu2Eei2yV0PKc8oxTXRcZ0YpNARipi2', 'usuario', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login_user_seq`
--

CREATE TABLE `login_user_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `login_user_seq`
--

INSERT INTO `login_user_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room`
--

CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `available` bit(1) DEFAULT NULL,
  `max_capacity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `hotel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `room`
--

INSERT INTO `room` (`id`, `available`, `max_capacity`, `price`, `room_type`, `hotel_id`) VALUES
(103, b'1', 3, 300, 'Triple', 2),
(104, b'1', 3, 300, 'Triple', 2),
(152, b'1', 2, 200, 'Double', 52),
(153, b'1', 3, 300, 'Triple', 52),
(154, b'1', 3, 300, 'Triple', 52),
(202, b'1', 2, 200, 'Double', 102),
(203, b'1', 3, 300, 'Triple', 102),
(204, b'1', 3, 300, 'Triple', 102),
(252, b'1', 2, 320, 'Double', 152),
(253, b'1', 2, 120, 'Double', 152),
(254, b'1', 3, 300, 'Triple', 152),
(255, b'1', 3, 250, 'Triple', 152),
(256, b'1', 2, 320, 'Double', 153),
(257, b'1', 2, 120, 'Double', 153),
(258, b'1', 3, 180, 'Triple', 153),
(259, b'1', 4, 250, 'Multiple', 153),
(302, b'1', 2, 320, 'Double', 202),
(303, b'1', 2, 120, 'Double', 202),
(304, b'1', 3, 180, 'Triple', 202),
(305, b'1', 4, 250, 'Multiple', 202);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_booking`
--

CREATE TABLE `room_booking` (
  `id` bigint(20) NOT NULL,
  `check_in` date DEFAULT NULL,
  `check_out` date DEFAULT NULL,
  `client_name` varchar(255) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `room_booking`
--

INSERT INTO `room_booking` (`id`, `check_in`, `check_out`, `client_name`, `room_id`) VALUES
(152, '2024-11-27', '2024-11-29', 'Fran', 103),
(202, '2024-08-27', '2024-08-29', 'Paco', 154),
(252, '2024-08-27', '2024-08-29', 'Paco', 153);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_booking_client`
--

CREATE TABLE `room_booking_client` (
  `room_booking_id` bigint(20) NOT NULL,
  `client_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `room_booking_client`
--

INSERT INTO `room_booking_client` (`room_booking_id`, `client_id`) VALUES
(152, 302),
(152, 303),
(152, 352),
(202, 452),
(202, 453),
(252, 252),
(252, 453);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_booking_seq`
--

CREATE TABLE `room_booking_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `room_booking_seq`
--

INSERT INTO `room_booking_seq` (`next_val`) VALUES
(401);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_seq`
--

CREATE TABLE `room_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `room_seq`
--

INSERT INTO `room_seq` (`next_val`) VALUES
(401);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_powwvjq5dtrded35jufhbmcsd` (`identification`);

--
-- Indices de la tabla `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_mh8wxtmpqqb2nydefbnnivlmi` (`code`);

--
-- Indices de la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3uiklmjy1d7ba6rrjp6iq08kq` (`flight_id`);

--
-- Indices de la tabla `flight_booking_client`
--
ALTER TABLE `flight_booking_client`
  ADD KEY `FKyfrv6v8aoo3b0j3s7aps0viq` (`client_id`),
  ADD KEY `FK4f3nls0471ydl6j48qejnvui1` (`flightbooking_id`);

--
-- Indices de la tabla `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `login_user`
--
ALTER TABLE `login_user`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdosq3ww4h9m2osim6o0lugng8` (`hotel_id`);

--
-- Indices de la tabla `room_booking`
--
ALTER TABLE `room_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKiwt0ws97ta91ukd4xonewjbxl` (`room_id`);

--
-- Indices de la tabla `room_booking_client`
--
ALTER TABLE `room_booking_client`
  ADD KEY `FKjqstdfl7kfguaprlrydybkuds` (`client_id`),
  ADD KEY `FKjx8ivy7bl3ucuq6k8b894weeh` (`room_booking_id`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD CONSTRAINT `FK3uiklmjy1d7ba6rrjp6iq08kq` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`);

--
-- Filtros para la tabla `flight_booking_client`
--
ALTER TABLE `flight_booking_client`
  ADD CONSTRAINT `FK4f3nls0471ydl6j48qejnvui1` FOREIGN KEY (`flightbooking_id`) REFERENCES `flight_booking` (`id`),
  ADD CONSTRAINT `FKyfrv6v8aoo3b0j3s7aps0viq` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`);

--
-- Filtros para la tabla `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FKdosq3ww4h9m2osim6o0lugng8` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`);

--
-- Filtros para la tabla `room_booking`
--
ALTER TABLE `room_booking`
  ADD CONSTRAINT `FKiwt0ws97ta91ukd4xonewjbxl` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Filtros para la tabla `room_booking_client`
--
ALTER TABLE `room_booking_client`
  ADD CONSTRAINT `FKjqstdfl7kfguaprlrydybkuds` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `FKjx8ivy7bl3ucuq6k8b894weeh` FOREIGN KEY (`room_booking_id`) REFERENCES `room_booking` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
