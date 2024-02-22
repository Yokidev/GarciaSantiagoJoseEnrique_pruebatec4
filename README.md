# Aplicación de Gestión de Reservas

<p>Tercer proyecto desarrollado durante el curso de Java en HaB. El objetivo de este desafío es aplicar los contenidos dados hasta el momento durante el BOOTCAMP (Git, Java, Spring Boot, Testing, JPA + Hibernate, Spring Security) en la implementación de una API REST.<p>
<p>Para ello se ha llevado a cabo el desarrollo de una aplicación back end con Spring Boot que permita recibir solicitudes de reservas para los diferentes tipos de paquetes que ofrece una agencia de turismo.<p>

## Pre-requisitos 📋

_Programas y herramientas necesarias para utilizar el programa_

```
Java 17 o superior
IDE compatible con Java 17 o superior
Un gestor de bases de datos compatible con MySQL
Un contenedor de servlet como Apache Tomcat para desplegar el proyecto web
```
_Supuestos:_ <br>

La tabla de datos de hoteles se ha dividido en Hoteles y habitaciones. <br>
Las reservas de estas se hacen a traves de las habitaciones. <br>
La disponibilidad de las mismas se rigen por las fechas en las que tengan o no una reserva.<br>
Es posible crear varios vuelos con las mismas caracteristicas pero se diferenciaran siempre por el codigo del mismo.<br>
Los clientes tienen una identificacion unica.<br>
Los administradores de la aplicacion tienen constancia tanto de la contraseña de la BBDD como de la contraseña para acceder a los endpoints protegidos<br>

Contraseña de la BBDD
```
Usuario: root
Contraseña: ""
```

Usuario y contraseña para usar los end-points protegidos
```
Usuario: usuario
Contraseña: 123
```
## Instalación 🔧

- Descargar el proyecto o realizar un git clone.
- Importar el archivo .sql alojado en la carpeta BBDD dentro de \src\main\resources del proyecto al SGBD compatible con MySql deseado.
- Abrir el proyecto en el IDE deseado.
- Es posible importar un archivo con los endpoints que se encuentra dentro de \src\main\resources para usarlos en postman.

## Estructura de la BBDD :cd:
![Captura de pantalla 2024-02-22 003325](https://github.com/Yokidev/GarciaSantiagoJoseEnrique_pruebatec4/assets/113154741/853adeec-85ae-486b-a1a4-e11c31aa2374)

## Funcionalidades del proyecto :hammer:

<p>El proyecto se compone de distintos controllers que ofrecen endpoints con distintas funcionalidades segun a cual se llame.</p>
<p>Algunas de ellas estan abiertas a uso publico para las demas funcionalidades hay que autenticarse.</p>

```
Usuario: usuario
Contraseña: 123
```


### Client Controller
![client-controller](https://github.com/Yokidev/GarciaSantiagoJoseEnrique_pruebatec4/assets/113154741/721ccb6c-a3d4-4844-97e3-f7d35c41f13c)

### Flight Booking Controller
![flight-booking-controller](https://github.com/Yokidev/GarciaSantiagoJoseEnrique_pruebatec4/assets/113154741/bdf4cb94-aa3b-4aff-aec8-799fbba4b7b9)

### Flight Controller 
![flight-controller](https://github.com/Yokidev/GarciaSantiagoJoseEnrique_pruebatec4/assets/113154741/889cb917-616d-45e3-b973-94a7a19b7aeb)

### Hotel Controller
![hotel-controller](https://github.com/Yokidev/GarciaSantiagoJoseEnrique_pruebatec4/assets/113154741/03bce5b6-0571-46ed-b0d5-f6986fc8e53e)

### Room Booking Controller
![room-booking-controller](https://github.com/Yokidev/GarciaSantiagoJoseEnrique_pruebatec4/assets/113154741/75d3e78d-9c3c-4674-bc0c-ad3159090830)

### Room Controller
![room-controller](https://github.com/Yokidev/GarciaSantiagoJoseEnrique_pruebatec4/assets/113154741/b86523fa-024f-4baa-a441-8cf0c61acdcb)


<p>De las distintas rutas presentadas anteriormente las siguientes se encuentran abiertas a uso publico:</p>

`/agency/hotels` <br>
`/agency/hotels/{id}` <br>
`/agency/hotelsFiltered` <br>
`/agency/room-booking/new` <br>
`/agency/flight-booking/new` <br>
`/agency/flights` <br>
`/agency/flights/{id}` <br>
`/agency/flightsFiltered` <br>


## Construido con 🛠️

* [Java](https://docs.oracle.com/en/java/javase/17/docs/api/index.html) - Lenguaje utilizado, especificamente el JDK17
* [Spring Boot](https://docs.oracle.com/cd/E17802_01/products/products/jsp/2.1/docs/jsp-2_1-pfd2/index.html) - Manejo de Back-End con Java
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Acceso a la base de datos
* [Spring Security](https://spring.io/projects/spring-security) - Manejo de la seguridad de la aplicación
* [Junit](https://junit.org/junit5/docs/current/user-guide/) - Creación de test
* [Maven](https://maven.apache.org/) - Manejador de dependencias

## Autor ✒️
* **Jose Enrique Garcia** [Yokidev](https://github.com/Yokidev)
