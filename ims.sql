-- IMS: Inventory Management System Database Schema
-- Seeded with SysAdmin Credentials

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Assets Table
CREATE TABLE IF NOT EXISTS assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100),
    condition_status VARCHAR(100),
    availability_status VARCHAR(100),
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Employees Table
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    department VARCHAR(255),
    email VARCHAR(255)
);

-- Audit Logs Table
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    performed_by VARCHAR(255)
);

-- Seed SysAdmin User
-- Username: 24RP00869
-- Password: 22RP03001 (BCrypt: $2a$10$8.UnVuG9HHgffUDAlk8q2OuVGkqRzhVymvn0yVz.QfV9zX4.VpXyK)
INSERT INTO users (username, password, role) 
SELECT '24RP00869', '$2a$10$8.UnVuG9HHgffUDAlk8q2OuVGkqRzhVymvn0yVz.QfV9zX4.VpXyK', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = '24RP00869');

-- Seed sample data
INSERT INTO employees (employee_id, full_name, department, email) VALUES ('EMP001', 'John Doe', 'IT', 'john@example.com');
INSERT INTO assets (serial_number, name, type, condition_status, availability_status) VALUES ('SN001', 'Laptop Dell', 'Laptop', 'New', 'Available');
