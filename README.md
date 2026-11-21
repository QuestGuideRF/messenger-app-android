# Mesibo Messenger for Android

Mesibo Messenger for Android — исходники Android-клиента для обмена сообщениями, голосовых и видеозвонков на базе Mesibo SDK. Проект содержит приложение, ресурсы интерфейса, настройки push и служебные классы для профиля, уведомлений и запуска.

## ✨ Особенности

- Личные и групповые чаты.
- Голосовые и видеозвонки.
- Поддержка push-уведомлений через Firebase.
- Интеграция карт и геолокации через Google Play Services.
- Экран настроек приложения и редактирование профиля.
- Встроенные классы для передачи файлов и фоновых сервисов.

## 🚀 Быстрый старт

В корне этого среза есть модуль приложения `app/`, но отсутствуют верхнеуровневые Gradle-скрипты проекта. Поэтому рабочий сценарий для этой копии выглядит так:

1. Откройте каталог как Android-проект в Android Studio.
2. Проверьте `app/build.gradle` и подключение Mesibo/Firebase зависимостей.
3. Используйте модуль `app` как основную точку сборки.

Ключевые параметры из `app/build.gradle`:

- `applicationId`: `org.mesibo.messenger`
- `minSdkVersion`, `targetSdkVersion`, `compileSdkVersion` берутся из Mesibo Gradle-конфигурации
- версия приложения задаётся через `PUBLISH_VERSION` и `PUBLISH_VERSION_CODE`

## 📁 Структура проекта

- `app/src/main/java/org/mesibo/messenger/` — основной Android-код приложения.
- `app/src/main/java/org/mesibo/messenger/fcm/` — push- и registration-сервисы Firebase.
- `app/src/main/java/org/mesibo/messenger/AppSettings/` — экраны настроек приложения.
- `app/src/main/res/` — layout, drawable, menu, значения и иконки.
- `app/src/main/assets/` — bundled assets, включая шрифты.
- `gradle/wrapper/` — Gradle wrapper-библиотеки, присутствующие в этом срезе.

## 🛠 Использование

После сборки приложение запускается как полноценный Android messenger с экранами входа, профиля, настроек и уведомлений. В коде присутствуют `MainApplication`, `StartUpActivity`, `EditProfileActivity`, `UIManager`, `MesiboListeners` и Firebase-сервисы, что указывает на готовый клиентский поток для чатов, звонков и пушей.

## 🧪 Технологии

- Java
- Android SDK
- Mesibo SDK
- Firebase Cloud Messaging
- Google Maps / Places
- AndroidX

Подпись относится к текущей версии проекта.

---

Автор: legenda  
Telegram: [@LEGENDA_SD](https://t.me/LEGENDA_SD)
