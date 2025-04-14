-- Add a unique constraint and index to the "email" column in the "users" table
ALTER TABLE users
    ADD CONSTRAINT unique_email UNIQUE (email);

-- Create an index on the "email" column
CREATE INDEX idx_email ON users (email);