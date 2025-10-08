# Web Sevice

A web Service is a software system designed to support interoperable machine to machine interaction over a network.

# REST

REST -REpresentational State Transfer is an architectural style for developing web services to access a resource.

In REST, everything is a Resource (data/Functionality) that is uniquely identified By
Uniform Resource Idetifier (URI).

The Resources of REST can be represented in many wasy that include JSON, XML etc.

# Actuator

Spring Boot Actuator provides production-ready endpoints to monitor, manage, and gain insights into the health, metrics, and configuration of an application.

# Spring Boot Actuator provides:`

- Health checks – see if your app is running correctly.
- Metrics – information about memory usage, CPU, HTTP requests, etc.
- Application info – version, environment properties, etc.
- Trace/debug – track requests or logs.
- Environment details – view configuration properties.
- Custom endpoints – you can create your own management endpoints.

# Some of the built-in endpoints include:

Endpoint Description

- /actuator/health - Shows application health (UP/DOWN)
- /actuator/metrics - Provides metrics like memory, JVM, HTTP requests
- /actuator/info - Application info (version, description)
- /actuator/env - Environment properties
- /actuator/loggers - Logging levels

# CORS

- CORS stands for Cross-Origin Resource Sharing.
- It is a browser enforced security policy that control how web pages can make request to a different origin.

An origin is defined by

- Protocal- http,https,file
- Host - localhost
- Port -8080,9090

CORS Prevent Javascript running in browser from accessing resources on a different origin unless the server explicitly allows it via CORS headers.

- It’s a security feature built into browsers that controls how web pages from one origin (domain) can request resources from another origin.

# i18n (Internationalization)
It is the process of designing a website or application to support multiple languages and regional formats.
# l10n (Localization)
It is the process of adapting the website for a specific language or region, including translations, date/number formats, and cultural adjustments.
