# IMS - Inventory Management System

A professional Inventory Management System (IMS) designed for efficient asset tracking and employee assignments.

## Getting Started

### Prerequisites
- **Java 17** or higher
- **Maven 3.8+**
- **Eclipse IDE** (or any preferred Java IDE)

### Setup Instructions
1. **Import Project**: Open your IDE and import the folder as an **Existing Maven Project**.
2. **Build**: Run `mvn clean install` to download all dependencies.
3. **Run**: Execute the `InventoryApplication.java` file or run as a **Spring Boot App**.
4. **Access**: Open your browser and navigate to `http://localhost:8081`.

##  System Credentials

| Role | Username | Password |
| :--- | :--- | :--- |
| **SysAdmin** | `24RP00869` | `22RP03001` |
| **Standard User** | `user` | `password` |

##  Database Information
- The system uses an embedded H2 database for zero-configuration setup.
- For manual setup or external database reference, see the included `ims.sql` file.
- **H2 Console (Database UI)**: `http://localhost:8081/h2-console`
    - JDBC URL: `jdbc:h2:file:./data/ims_db`
    - User: `24rp00869`
    - Password: `22RP03001`
- Note: H2 Console credentials are configured in `application.properties`.

## 🛠 Features
- **Asset Registry**: Manage equipment details and condition.
- **Employee Management**: Track personnel across departments.
- **Assignment Logic**: Seamlessly issue and return assets.
- **Audit Logs**: Full history of system activities.
