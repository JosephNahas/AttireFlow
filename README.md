# AttireFlow - Warehouse Inventory Management

**Group Name:** The 404s

**Team Members:** Jenny Nguyen, Annie Nguyen, Anupa Ragoonanan, Joseph Nahas

---

## Project Overview

AttireFlow is an internal inventory management system for a fashion warehouse with multi‑location distribution. It enables staff to track products, stock levels, and deliveries, with role‑based access for Admins and Managers.

### Key Features

- **Product & Variant Management:** Products have variants (size/color) with unique SKUs and prices.
- **Inventory Tracking:** Stock levels per variant per warehouse, with reorder‑level alerts.
- **Delivery Management:** Create and track incoming/outgoing deliveries; filter, sort, and paginate delivery records.
- **Role‑Based Access:** Admins manage users, suppliers, and approvals; Managers handle stock and deliveries.
- **Audit Logs:** Transfer workflows (PENDING → APPROVED → COMPLETED) and logs for tracking changes.
- **Supplier Integration:** Products linked to suppliers for purchase tracking.

> **Note:** This is strictly an internal tool with no customer‑facing storefront. Inventory is managed at fixed warehouse locations; transfers between warehouses are tracked via delivery orders.

---

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Data JPA
- Thymeleaf
- H2 Database / MySQL
- Docker
- Maven
- Git

---

## How to Run the Application

### Option 1: Run with Docker (Recommended)

**Prerequisites:** Docker Desktop installed

1. Clone the repository:
git clone https://github.com/JosephNahas/AttireFlow.git
cd AttireFlow


2. Build and start the application:
docker-compose up --build

3. Open your browser to:
http://localhost:8080


### Option 2: Run locally with Maven

**Prerequisites:** Java 17 or later installed

1. Clone the repository:
git clone https://github.com/JosephNahas/AttireFlow.git
cd AttireFlow


2. Build and run using the Maven wrapper:
./mvnw spring-boot:run

3. Open your browser to:
http://localhost:8080

---

## Application Pages

| Page | URL |
|------|-----|
| Home | http://localhost:8080/ |
| Inventory | http://localhost:8080/inventory |
| Deliveries | http://localhost:8080/deliveries |

---

## Team Contributions

**Joseph Nahas:** Built the inventory page with full CRUD, search/filter/sort, and variant management.

**Jenny Nguyen:** Built the deliveries page with entity, repository, service, controller, and templates. Added validation, filtering, sorting, and pagination. Dockerized the application with MySQL, implemented add/remove stock functionality, and fixed delivery stock deduction. Updated the README.

**Anupa Ragoonanan:** Handled the overall styling of the application, including layout adjustments and modal designs.

**Annie Nguyen:** Built the dashboard page and home page with search functionality.