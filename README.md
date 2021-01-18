# Simulación Cajero Electrónico :moneybag:

Este proyecto corresponde al backend de la simulación de un cajero electrónico, desarrollado con [Spring Boot](https://spring.io/) y BD [PostgreSQL](https://www.postgresql.org/).

## Instalación / local-environment

Requerimientos:
- JDK 8 or later
- Maven 3.2+
- PostgreSQL

### Despliegue ambiente local: 
```shell
git clone https://github.com/AlaskaRising/CajeroElectronico.git
cd CajeroElectronico
./mvnw spring-boot:run
```

## Considerations
 El proyecto se despliega por defecto con las siguientes propiedades:
* Puerto: 8085
* Base de datos: postgres
* Usuario: postgres
* Clave: alaska
> Para su correcto funcionamiento editar los valores de las propiedades, que se encuentran en el archivo [application.properties](https://github.com/AlaskaRising/CajeroElectronico/blob/630605db729b7fc5e6553833c06318595bc3866a/src/main/resources/application.properties#L4)

* En la base de datos postgres o la que se elija en su defecto debe encontrarse el schema de datos utilizado:

![Schema banco](https://github.com/AlaskaRising/CajeroElectronico/blob/master/database/ModeloDB.PNG)

* El [Script del schema](https://github.com/AlaskaRising/CajeroElectronico/blob/master/database/cajero.sql) contiene la creación de las tablas e inserción de datos paramétricos básicos para uso del API.

## Funcionalidades 

### Administrador
* [[GET]  /cajero/](https://github.com/AlaskaRising/CajeroElectronico/blob/630605db729b7fc5e6553833c06318595bc3866a/src/main/java/com/acsendo/CajeroElectronico/controllers/CajeroController.java#L24)
> Lista en formato JSON todas las denominaciones de billetes con su respectiva cantidad disponible.

![Admin 1](https://github.com/AlaskaRising/CajeroElectronico/blob/master/gif_readme/Admin_get.gif)

* [[POST]  /cajero/](https://github.com/AlaskaRising/CajeroElectronico/blob/630605db729b7fc5e6553833c06318595bc3866a/src/main/java/com/acsendo/CajeroElectronico/controllers/CajeroController.java#L29)

> Permite agregar una cantidad de billetes a una de las denominaciones disponibles del banco.
![Admin 2](https://github.com/AlaskaRising/CajeroElectronico/blob/master/gif_readme/Admin_post.gif)

> :sob: Bad Request Denominaciones permitidas.
![Admin 3](https://github.com/AlaskaRising/CajeroElectronico/blob/master/gif_readme/Amin_post_error1.gif)

> :frowning: Bad Request Cantidades permitidas.
![Admin 4](https://github.com/AlaskaRising/CajeroElectronico/blob/master/gif_readme/Amin_post_error2.gif)

### Cliente
* [[POST]  /retiro/](https://github.com/AlaskaRising/CajeroElectronico/blob/630605db729b7fc5e6553833c06318595bc3866a/src/main/java/com/acsendo/CajeroElectronico/controllers/RetiroController.java#L25)
> Recibe el valor solicitado por el cliente para comprobar si es una cantidad valida y disponible para ser entregada por el cajero.  En el momento de la entrega se entrega la menor cantidad de billetes posible para el valor solicitado. 
![Cliente 1](https://github.com/AlaskaRising/CajeroElectronico/blob/master/gif_readme/Cliente_post.gif)

> :worried: Bad Request Cantidades de retiro multiplo de 1000.
![Cliente 2](https://github.com/AlaskaRising/CajeroElectronico/blob/master/gif_readme/Cliente_error1.gif)

> :anguished: Bad Request Valores minimos de retiro.
![Cliente 3](https://github.com/AlaskaRising/CajeroElectronico/blob/master/gif_readme/Cliente_error2.gif)

### Postman
> Enlace para importar la coleccion de las peticiones: [Colección Postman](https://www.getpostman.com/collections/db5ecedb46b0ae78d727).



### Gracias :tiger:
