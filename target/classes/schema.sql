CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    staff_id VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    specifications TEXT,
    condition_status VARCHAR(50) NOT NULL,
    availability_status VARCHAR(50) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    issue_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    notes TEXT,
    FOREIGN KEY (asset_id) REFERENCES assets(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(100) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    performed_by VARCHAR(100)
);

INSERT IGNORE INTO users (username, password, role) VALUES 
('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.7u41W3u', 'ADMIN'),
('user', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.7u41W3u', 'USER');

INSERT IGNORE INTO employees (staff_id, full_name, department, email) VALUES 
('EMP101', 'Samuel', 'IT Department', 'samuel@airtel.com'),
('EMP102', 'Ishimwe', 'Operations', 'ishimwe@airtel.com'),
('EMP103', 'Sindayiheba', 'Finance', 'sindayiheba@airtel.com'),
('EMP104', 'Damier', 'Management', 'damier@airtel.com');

INSERT IGNORE INTO assets (serial_number, name, type, condition_status, availability_status) VALUES 
('SN-001', 'Dell Latitude', 'Laptop', 'New', 'Available'),
('SN-002', 'HP EliteBook', 'Laptop', 'Good', 'Available'),
('SN-003', 'iPhone 14', 'Mobile', 'New', 'Available'),
('SN-004', 'Lenovo Desktop', 'Desktop', 'Fair', 'Available');
