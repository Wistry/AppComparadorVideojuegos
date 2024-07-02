# AppComparadorVideojuegos <img src="src\main\resources\static\LOGO.jpeg" alt="Logo" width="80" align="right">

PricePlayPay es un proyecto colaborativo que desarrollamos, alojado en Google Cloud bajo este nombre. Se trata de un comparador de videojuegos que combina consultas a una API y webscraping para obtener información detallada de juegos populares.

## Descripción del Proyecto

El proyecto se basa en las siguientes funcionalidades principales:

- **API de Consulta**: Utiliza una API, cheapshark, para obtener información detallada de juegos a partir de consultas específicas.
  
- **Webscraping con Selenium**: Implementa webscraping utilizando Selenium desde entornos Ubuntu y Windows. Se obtiene información de los primeros 60 juegos de las siguientes páginas:
  - [Game.es - Juegos para PC](https://www.game.es/buscar/juego-pc)
  - [Instant Gaming - Juegos de Steam para PC](https://www.instant-gaming.com/es/pc/steam/)
  
  Los datos extraídos se convierten en archivos JSON para su procesamiento posterior.

- **Implementación en Java**: La lógica del proyecto se desarrolla en clases Java, utilizando una programación orientada a objetos para una estructura modular y mantenible.

- **Uso de Bibliotecas Java**: Se emplean diversas bibliotecas Java para el webscraping y controladores, optimizando el manejo de datos y la integración con la plataforma Google Cloud.

- **Configuración y Despliegue en Google Cloud**: Incluye un archivo de configuración diseñado para ser desplegado en Google Cloud, utilizando una IP dedicada. La gestión de dependencias y la lógica del proyecto se realizan con Maven.

## Tecnologías Utilizadas

- **Java**: Para la implementación de la lógica del proyecto y la manipulación de datos.
- **Selenium**: Para el webscraping desde entornos Ubuntu y Windows.
- **Cheapshark**: Api empleada para la busqueda de informacion.
- **Google Cloud**: Como plataforma de alojamiento y despliegue del proyecto.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.

## Contribuidores

Este proyecto fue desarrollado en colaboración por Wistry y mi compañero de ISI. Ambos contribuimos en el diseño, implementación y mejora continua del proyecto.

## Video Demostrativo

A continuación se muestra una miniatura del video demostrativo de la app. Haz clic en la imagen para ver el video en YouTube.

<p align="center">
  <a href="https://youtu.be/95vYd36eLGM">
    <img src="src\main\resources\static\demo-priceplaypay.png" alt="Video Demostrativo" width="100%">
  </a>
</p>

```plaintext
All Rights Reserved
Copyright (c) 2024 Willy

Unauthorized copying of this file, via any medium is strictly prohibited.
Proprietary and confidential.

Written by Wistry, 2024.
