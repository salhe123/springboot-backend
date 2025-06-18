CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    join_date DATE NOT NULL,
    department VARCHAR(50) NOT NULL
);