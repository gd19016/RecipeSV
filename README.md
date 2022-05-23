### RecipeSV
### Descripción y contexto
---
La aplicación móvil es una aplicación que nos permite guardar recetas de cocina, la cual cuenta con varias secciones de modo que esta pueda brindar una experiencia única y amigable al usuario.

Además de poder crear las recetas, esta permite:
- Segmentar los pasos por salto de línea.
- Establecer recordatorios.
- Notificar cuando el tiempo del recordatorio se ha finalizado.

### Prototipos
Los prototipos de la aplicación pueden consultarse en: [Figma](https://www.figma.com/files/recent?fuid=1079993254510316241)
 	
### Guía de instalación
---
La solución informática está publicada mediante un archivo empaquetado para Android (apk), configurado para funcionar con una versión de Android 5.0 (API 21 del SDK) o superior; sin embargo, es recomendable usar un dispositivo con un Android 7.0 o superior.

Para poder utilizar la aplicación se necesita:
- Descargar el APK.
- Pasarlo al dispositivo Android mediante una MicroSD, Bluetooth o el servicio de almacenamiento en la nube de su preferencia.
- Buscar el apk transferido al dispositivo y abrirlo.
- Seguir el asistente de instalación por defecto de android.*
- Iniciar la aplicación.

* Posiblemente se requiera agregar permiso de instalación de fuentes desconocidas.

Para poder compilar la aplicación:
- Se requiere clonar o descargar el proyecto a su equipo.
- Abrir Android Studio y abrir el proyecto.
- Compilar el proyecto validando que todas las librerías configuradas en el Gradle funcionen (como Room o las utilidades de notificaciones).

Para ejecutar el proyecto en el emulador de Android:
- Se recomienda crear un dispositivo emulado en Android Studio con una API 24 o superior.

#### Dependencias
Para que el proyecto pueda ejecutarse se usan las siguientes dependencias:

    implementation "androidx.appcompat:appcompat:1.4.1"
    implementation "androidx.activity:activity-ktx:1.4.0"

    // Room components
    implementation "androidx.room:room-ktx:2.4.2"
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    kapt "androidx.room:room-compiler:2.4.2"
    androidTestImplementation "androidx.room:room-testing:2.4.2"

    // Lifecycle components
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.4.1"
    implementation "androidx.lifecycle:lifecycle-common-java8:2.4.1"

    // Kotlin components
    implementation "androidx.core:core-ktx:1.7.0"
    implementation "org.jetbrains.kotlin:kotlin-stdlib:1.5.30"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.21"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"

    // UI
    implementation "androidx.constraintlayout:constraintlayout:2.1.3"
    implementation "com.google.android.material:material:1.5.0"

    // Navigation
    implementation "androidx.navigation:navigation-fragment-ktx:2.4.2"
    implementation "androidx.navigation:navigation-ui-ktx:2.4.2"

    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'


### Autor/es
---
- GD19016 - Gutiérrez Dubón, Roberto Antonio
- HM11019 - Hernández Mejía, Erick Serafín 
- HR18015 - Hernández Ramírez, Cicely Joceline
- NM19010 - Núñez Menéndez, Kevin Arturo

### Información adicional
---
Esta es la sección que permite agregar más información de contexto al proyecto como alguna web de relevancia, proyectos similares o que hayan usado la misma tecnología.

### Licencia 
---
Shield: [![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

This work is licensed under a
[Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License][cc-by-nc-sa].

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: http://creativecommons.org/licenses/by-nc-sa/4.0/
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg

## Limitación de responsabilidades

El equipo de desarrollo y la Universidad de El Salvador (UES) no se harán responsables, bajo circunstancia alguna, de daño ni indemnización, moral o patrimonial; directo o indirecto; accesorio o especial; o por vía de consecuencia, previsto o imprevisto, que pudiese surgir:

i. Bajo cualquier teoría de responsabilidad, ya sea por contrato, infracción de derechos de propiedad intelectual, negligencia o bajo cualquier otra teoría; y/o

ii. A raíz del uso de la Herramienta Digital, incluyendo, pero sin limitación de potenciales defectos en la Herramienta Digital, o la pérdida o inexactitud de los datos de cualquier tipo. Lo anterior incluye los gastos o daños asociados a fallas de comunicación y/o fallas de funcionamiento de computadoras, vinculados con la utilización de la Herramienta Digital.
