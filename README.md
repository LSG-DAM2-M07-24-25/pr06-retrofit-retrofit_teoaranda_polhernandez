# Trivia Legends 🎮

## Descripció del Projecte

Trivia Legends és una aplicació de preguntes i respostes desenvolupada amb les últimes tecnologies d'Android. El joc desafia als usuaris amb preguntes de diferents categories idifficulties, permetent competir per les millors puntuacions.

## Característiques Principals 🌟

- **Preguntes Dinàmiques**: Integració amb l'API OpenTrivia per obtenir preguntes actualitzades
- **Sistema de Puntuació**: Puntuació basada en dificultat i temps de resposta
- **Estadístiques Detallades**: Seguiment de rendiment i millors puntuacions
- **Configuració Personalitzable**: Ajustaments de dificultat i temps per pregunta
- **Disseny Modern**: Interfície d'usuari intuïtiva amb Jetpack Compose

## Tecnologies Utilitzades 🛠️

- **Kotlin**: Llenguatge principal de desenvolupament
- **Jetpack Compose**: UI moderna i declarativa
- **MVVM**: Patró d'arquitectura per una clara separació de responsabilitats
- **Retrofit**: Consum d'API REST
- **Room**: Base de dades local SQLite
- **Dagger Hilt**: Injecció de dependències
- **Coroutines & Flow**: Programació asíncrona i reactiva
- **Navigation Component**: Navegació entre pantalles

## Arquitectura 📐

El projecte segueix el patró MVVM (Model-View-ViewModel) amb Clean Architecture:

```
app/
├── data/
│   ├── api/          # Serveis d'API
│   ├── database/     # Base de dades Room
│   └── repository/   # Repositoris
├── di/               # Injecció de dependències
└── ui/               # Interfícies d'usuari
    ├── game/         # Pantalla de joc
    ├── scores/       # Pantalla de puntuacions
    └── settings/     # Pantalla de configuració
```

## Captures de Pantalla 📱

[Aquí hauríes d'incloure captures de pantalla de la teva aplicació al simulador]

## Instal·lació i Configuració 🚀

1. Clona el repositori:
```bash
git clone https://github.com/yourusername/trivia-legends.git
```

2. Obre el projecte a Android Studio

3. Sincronitza el projecte amb Gradle

4. Executa l'aplicació en un emulador o dispositiu Android

## Autors ✍️

- Pol Hernández
- Teo Aranda

## Agraïments 🙏

- OpenTrivia DB per proporcionar l'API de preguntes
- La Salle pel suport en el desenvolupament del projecte 