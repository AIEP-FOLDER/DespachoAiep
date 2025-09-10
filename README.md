# 📦 DespachoAiep — App Móvil para Cálculo de Despacho Automatizado

Aplicación Android desarrollada con Jetpack Compose y Firebase, orientada a automatizar el cálculo
de despacho para una empresa distribuidora de productos alimenticios. La app permite seleccionar
productos, calcular tarifas dinámicas según monto y distancia, validar condiciones de transporte
para productos congelados y gestionar la sesión del usuario mediante autenticación con Gmail.

---

## 🚀 Características principales

- 🔐 Autenticación con cuenta Gmail usando FirebaseAuth
- 🛒 Catálogo interactivo de productos con acumulación de montos
- 📍 Cálculo de distancia geográfica con fórmula de Haversine
- ❄️ Validación de temperatura para productos congelados
- 📦 Cálculo automático de tarifas de despacho según reglas de negocio
- 🧩 Arquitectura modular con componentes reutilizables
- 🌐 Instalación y compatibilidad con Android Ore

---

🧱 Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- Firebase Auth
- Firebase Crashlytics
- Navigation Compose
- Coil
- Play Services Location
- SplashScreen API
- Material 3

---

## 📂 Estructura del proyecto

~~~
com.lll.despachoaiep
│
├── presentation
│   ├── home
│   ├── components
│   ├── components.topBar
│   ├── components.bottomBar
│   ├── login.signup
│   └── initial
├── model
├── datos
├── ui.theme
├── utils
~~~

## 📸 Capturas de pantalla

- Catálogo de productos
![Grabación 2025-09-10 132654.gif](evidencia/Grabaci%C3%B3n%202025-09-10%20132654.gif)

- Cálculo de despacho con validación

![Grabación 2025-09-10 133546.gif](evidencia/Grabaci%C3%B3n%202025-09-10%20133546.gif)


- Imagen de perfil en barra inferior

---

## Instalación
- Clona el repositorio:
~~~
https://github.com/AIEP-FOLDER/DespachoAiep/tree/aiep
~~~

- Abre el proyecto en Android Studio (AGP 8.12.2)
- 
- Sincroniza Gradle y ejecuta en emulador Android Oreo

- Asegúrate de tener configurado Firebase con tu propio google-services.json

---

## 📄 Informe técnico

Este proyecto fue desarrollado como parte de una actividad evaluativa. El informe completo incluye:

- Introducción y contexto
- Desarrollo técnico y decisiones justificadas
- Evidencia de instalación
- Código fuente modularizado
- Conclusión reflexiva
- Bibliografía en formato APA

### Acceder al informe completo

https://correoaiep-my.sharepoint.com/:w:/g/personal/matias_perezn_correoaiep_cl/EaTVFe3ts4RHpMe-5E3TsXMBNbCxhcgMHEUrGLcfJc-Xzg?e=qCi0F2

---

### ✉️ Contacto

> Desarrollado por Matías Ignacio Pérez Nauto
>
> 📍 Puerto Varas, Chile
>
> 📧 contacto@mtsprz.org
> 
