# 🚚 Transport Management System

## 📌 Описание
Transport Management System е Java конзолно приложение за управление на транспортна компания.  
Проектът е разработен с **Hibernate ORM** и **MySQL**, като покрива всички основни изисквания за CRUD операции, релации между обекти, справки, сортиране, филтриране и работа с файлове.

---

## 🛠️ Използвани технологии
- **Java 21**
- **Hibernate ORM**
- **MySQL**
- **Gradle**
- **Lombok**
- **IntelliJ IDEA**

---

## 🧱 Архитектура
Проектът е структуриран по слоеве:

- **org.example**
  - **entity** – JPA Entity класове
  - **dao** – Data Access Objects (CRUD операции и заявки)
  - **service** – Бизнес логика и работа с файлове
  - **configurate** – Hibernate конфигурация


---

## 🗂️ Основни обекти (Entities)
- **Company** – транспортна компания  
- **Employee** – служители (many-to-many с Company)  
- **Client** – клиенти на компанията  
- **Vehicle** – превозни средства  
- **Shipment** – превози  
- **PaymentStatus** – статус на плащане (PAID / UNPAID)

---

## 🔗 Релации
- Company ↔ Employee — Many-to-Many
- Company ↔ Client — One-to-Many
- Company ↔ Vehicle — One-to-Many
- Company ↔ Shipment — One-to-Many
- Vehicle ↔ Employee (driver) — Many-to-One
- Client ↔ Shipment — One-to-Many

---

## ⚙️ Функционалности

### ✅ CRUD операции
- Създаване, редактиране и изтриване на:
  - компании
  - служители
  - клиенти
  - превозни средства
  - превози

### ✅ Управление на служители
- Добавяне на нов служител
- Назначаване на служител към фирма (many-to-many)
- Един служител може да работи в повече от една компания

### ✅ Превози
- Записване на данни за превоз:
  - дестинация
  - товар
  - цена
  - дата
  - статус на плащане

### ✅ Плащания
- Проследяване дали превозът е платен (`PAID / UNPAID`)

### ✅ Сортиране и филтриране
- Служители по заплата
- Превози по дестинация

### ✅ Справки
- Общ брой превози
- Общ приход
- Брой превози по шофьор
- Приход по шофьор
- Приход на компания за определен период

### ✅ Работа с файлове
- Записване на превозите във файл
- Четене и извеждане на данните от файл

---

## ▶️ Стартиране на проекта

В `hibernate.properties`:

```properties
hibernate.connection.url=jdbc:mysql://localhost:3306/TransportManager
hibernate.connection.username=root
hibernate.connection.password=YOUR_PASSWORD

hibernate.hbm2ddl.auto=create-drop
hibernate.show_sql=true
