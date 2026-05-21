# ECF HelpDesk Pro

Application de gestion de tickets de support interne pour Microsoft Solutions.
Développée avec Spring Boot, Thymeleaf et PostgreSQL.

---

## Prérequis

- Java 25+
- Maven 3.8+
- Docker Desktop (pour PostgreSQL)
- IntelliJ (recommandé)

---

## Installation

### 1. Cloner le projet

```bash
git clone https://github.com/MaximeBjrd/Maxime_BEAUJARD_CDA25_HelpDesk_Pro
cd ECF_HelpDesk_Pro/demo
```

### 2. Lancer PostgreSQL

```bash
docker run --name postgres-ecf \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=helpdeskpro \
  -p 5432:5432 \
  -d postgres
```

### 3. Configurer la base de données

Le fichier `src/main/resources/application.properties` est déjà configuré tel que:

```properties
spring.datasource.url=jdbc:postgresql://locahost:5432/helpdeskpro
spring.datasource.username=postgres
spring.datasource.password=postgres
```
    
### 4. Lancer l'application

Depuis IntelliJ: Ouvrir `DemoApplication.java` et cliquer sur le bouton Run à gauche de la classe.

### 5. Accéder à l'application

Ouvrir http://localhost:8080/login et saisir l'un identifiants suivants afin de se connecter en fonction du rôle souhaité:

| Rôle | Email | Mot de passe |
|------|-------|--------------|
| Admin | admin1@helpdesk.fr | password123 |
| Admin | admin2@helpdesk.fr | password123 |
| Technicien | tech1@helpdesk.fr | password123 |
| Technicien | tech2@helpdesk.fr | password123 |
| Technicien | tech3@helpdesk.fr | password123 |

---

## Planning prévisionnel

| # | Tâche | Durée estimée | Statut |
|---|-------|---------------|--------|
| 1 | Init projet Git + Spring Initializr | 15 min        | ✅ Terminé |
| 2 | Configuration PostgreSQL | 30 min        | ✅ Terminé |
| 3 | Modélisation : entités JPA + enums | 30 min        | ✅ Terminé |
| 4 | Repositories | 15 min        | ✅ Terminé |
| 5 | Services + règles métier | 1h            | ✅ Terminé |
| 6 | Sécurité Spring Security + BCrypt | 45 min        | ✅ Terminé |
| 7 | Controllers | 45 min        | ✅ Terminé |
| 8 | Templates Thymeleaf | 1h            | ✅ Terminé |
| 9 | Données de test | 10 min        | ✅ Terminé |
| 10 | Gestion des erreurs + messages flash + README | 30 min        | ✅ Terminé |
| 11 | Tests unitaires | 45 min        | 🔄 En cours |
| 12 | Polish + corrections bugs | 30 min        | ✅ Terminé |

---

## Tests unitaires

Depuis IntelliJ: Ouvrir `DemoApplicationTests.java` et cliquer sur le bouton Run à gauche de la classe.
