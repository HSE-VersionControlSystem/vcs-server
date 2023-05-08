# Система контроля версий

## Описание

Данная программа является серверной частью проекта "Система контроля версий".

## Инструкция по запуску

1. Установка
    1. Скачайте docker образ напрямую
       с [docker hub](https://hub.docker.com/repository/docker/davidnatro/vcs-server/general)
       командой ```docker pull davidnatro/vcs-server```
    2. Скачайте данный проект любым удобным для вас способом (например, склонировав
       с [github](https://github.com/HSE-VersionControlSystem/vcs-server)).
       Перейдите
       с помощью консоли в корневую директорию проекта и
       пропишите ```docker build -t vcs-server .```.
2. Запуск
    1. В случае установки первым способом запустите контейнер
       командой ```docker run -d --name vcs-server -p 8080:8080 davidnatro/vcs-server```, иначе
       используйте ```docker run -d --name vcs-server -p 8080:8080 vcs-server```
       После чего можно отправлять запросы по конечным точкам (подробнее в следующем разделе)

> Для запуска проекта вне редактора кода требуется установленный docker.

## Конечные точки

- address - адрес сервера
- port - порт vcs-server'а в docker контейнере.

### GET

* http://address:port/actuator/heath - Возвращает DTO объект с единственным
  полем ```status```. ```status``` принимает 2 значения:
    1. UP
    2. DOWN

* http://address:port/files/all - Возвращает список всех репозиториев пользователя. Тип: массив
  строк.
* http://address:port/files/pull/{directoryName} - Возвращает запрошенную директорию списком всех
  файлов. Тип: массив DTO объектов.
  DTO объект имеет следующую структуру:
    - name (Тип: строка, включающая полный путь до директории)
    - file (Тип: массив байт)

### POST

* http://address:port/files/push - Сохраняет локально отправленную директорию. Принимает form/data с
  ключами directory_name и files, где directory_name - название директории, files - список файлов.

  > Возможные случаи возникновения ошибок обработаны. При их возникновении будут возвращаться
  соответствующие сообщения.

> На данном этапе все запросы проходят исключительно по протоколу http.