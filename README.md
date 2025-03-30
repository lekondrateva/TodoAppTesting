# Запуск тестов

## ВАЖНО! Перед запуском тестов

Перед запуском тестов убедитесь, что приложение работает, запустить контейнер с приложением можно командой:
`docker run -d --name todo-app -p 8080:4242 todo-app`

## Запуск тестов из IDE (Maven)

1. Склонируйте репозиторий:
   `git clone https://github.com/lekondrateva/TodoAppTesting.git`
   `cd TodoAppTesting`

2. Установите зависимости:
   `mvn clean install`

3. Запустите тесты:
   `mvn clean test`

## Генерация отчета Allure

После завершения тестов можно сгенерировать и открыть отчет Allure:
`mvn io.qameta.allure:allure-maven:serve`

### Установка Allure (если не установлен)

Если Allure не установлен, выполните установку:
`npm install -g allure-commandline --save-dev`

Или следуйте официальной инструкции https://docs.qameta.io/allure/#_installing_a_commandline.