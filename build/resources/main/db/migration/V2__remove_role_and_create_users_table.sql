-- Remove the "role" table
DROP TABLE IF EXISTS role;

-- Create the "users" table
CREATE TABLE users (
   user_id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   role VARCHAR(255) NOT NULL
);