# ☕ Coffee & Choc

Application web de commande de café en ligne, développée dans le cadre de l'UE **Services Web** du programme **M1 MIAGE** à l'Université de Nouvelle-Calédonie.

Coffee & Choc est une boutique en ligne de cafés et chocolats chauds proposant une vitrine produits, un système de panier client, un processus de commande, ainsi qu'un espace d'administration protégé. L'API REST est documentée et testable via Swagger UI.

---

## 👥 Groupe

| Membre | Rôle |
|---|---|
| **Luca BEROULE** | Backend & Architecture |
| **Damien JAMET** | Frontend & Design |

---

## 🛠️ Technologies utilisées

| Technologie | Version | Rôle |
|---|---|---|
| Java | 21 (LTS) | Langage de développement |
| Spring Boot | 4.0.6 | Framework backend principal |
| Spring Web MVC | 4.0.6 | Contrôleurs REST et MVC |
| Spring Data JPA / Hibernate | 4.0.6 / 7.x | Accès base de données ORM |
| Thymeleaf | 3.x | Moteur de templates HTML |
| PostgreSQL | 17 | Base de données relationnelle |
| Lombok | 1.18.x | Génération de code boilerplate |
| SpringDoc OpenAPI | 3.0.2 | Documentation Swagger UI |
| Bootstrap | 5.3.3 | Framework CSS frontend |

---

## 🚀 Mise en place en local

### 1. Base de données

1. Installer **pgAdmin 4**
2. Créer une base de données nommée `coffeechoc`
3. Faire un clic droit dessus → **Restore...**
4. Dans le champ *Filename*, localiser le fichier de backup `coffeechoc` présent dans le dossier `database/` à la racine du projet
5. Cliquer sur **Restore**

### 2. Récupération et lancement du projet

1. Dans **IntelliJ IDEA**, cloner le projet :
```
   https://github.com/Sh0ck3rNC/coffee-choc.git
```
2. Ouvrir le fichier `src/main/resources/application.properties` et adapter les paramètres PostgreSQL à votre configuration locale (url, port, username, password)
3. Lancer le projet : clic droit sur `CoffeeChocApplication.java`
```
   src\main\java\com\miage2026\coffeechoc\CoffeeChocApplication.java
```
puis **Run**
4. Accéder au site : [http://localhost:8083](http://localhost:8083)
   *(ou le port configuré dans `application.properties`)*

> 🔐 **Connexion admin** : le mot de passe est `admin1234` (configurable dans `application.properties`)

---

## 📖 Documentation API

Swagger UI disponible sur : [http://localhost:8083/swagger-ui](http://localhost:8083/swagger-ui)
*(accès protégé par le même mot de passe admin)*