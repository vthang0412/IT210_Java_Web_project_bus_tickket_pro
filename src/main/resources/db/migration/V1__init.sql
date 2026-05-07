-- Flyway migration: initial schema + seed (modified to include hold_expires_at)

CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role ENUM('PASSENGER','STAFF','ADMIN') NOT NULL DEFAULT 'PASSENGER',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_profiles (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               full_name VARCHAR(150),
                               phone VARCHAR(20) UNIQUE,
                               email VARCHAR(150) UNIQUE,
                               address VARCHAR(255),
                               user_id BIGINT UNIQUE,
                               CONSTRAINT fk_profile_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE
);

CREATE TABLE locations (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE routes (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        from_location_id BIGINT NOT NULL,
                        to_location_id BIGINT NOT NULL,
                        distance_km DOUBLE,
                        CONSTRAINT fk_route_from
                            FOREIGN KEY (from_location_id)
                                REFERENCES locations(id),
                        CONSTRAINT fk_route_to
                            FOREIGN KEY (to_location_id)
                                REFERENCES locations(id)
);

CREATE TABLE buses (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       license_plate VARCHAR(50) NOT NULL UNIQUE,
                       bus_type VARCHAR(100),
                       total_seats INT NOT NULL,
                       company_name VARCHAR(150),
                       driver_name VARCHAR(150)
);

CREATE TABLE trips (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       route_id BIGINT NOT NULL,
                       bus_id BIGINT NOT NULL,
                       departure_time DATETIME NOT NULL,
                       price DOUBLE NOT NULL,
                       CONSTRAINT fk_trip_route
                           FOREIGN KEY (route_id)
                               REFERENCES routes(id),
                       CONSTRAINT fk_trip_bus
                           FOREIGN KEY (bus_id)
                               REFERENCES buses(id)
);

CREATE TABLE seats (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       trip_id BIGINT NOT NULL,
                       seat_number VARCHAR(10) NOT NULL,
                       status ENUM('AVAILABLE','PENDING','BOOKED') DEFAULT 'AVAILABLE',
                       hold_expires_at TIMESTAMP NULL,
                       CONSTRAINT fk_seat_trip
                           FOREIGN KEY (trip_id)
                               REFERENCES trips(id)
);

CREATE TABLE tickets (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         ticket_code VARCHAR(50) UNIQUE,
                         customer_name VARCHAR(150),
                         customer_phone VARCHAR(20),
                         customer_email VARCHAR(150),
                         trip_id BIGINT NOT NULL,
                         seat_id BIGINT NOT NULL,
                         total_price DOUBLE,
                         status ENUM('PENDING','PAID','CANCELLED') DEFAULT 'PENDING',
                         booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_ticket_trip
                             FOREIGN KEY (trip_id)
                                 REFERENCES trips(id),
                         CONSTRAINT fk_ticket_seat
                             FOREIGN KEY (seat_id)
                                 REFERENCES seats(id)
);

-- seed minimal users (password 123456 bcrypt)
INSERT INTO users(username,password,role) VALUES
('admin','$2a$10$XQxJ8H7v7M0l4sYz1WQ5Eu4u3xg8L5w2K0QnK8L1fY9Q3bG7d2V7e','ADMIN'),
('staff','$2a$10$XQxJ8H7v7M0l4sYz1WQ5Eu4u3xg8L5w2K0QnK8L1fY9Q3bG7d2V7e','STAFF'),
('passenger','$2a$10$XQxJ8H7v7M0l4sYz1WQ5Eu4u3xg8L5w2K0QnK8L1fY9Q3bG7d2V7e','PASSENGER');
