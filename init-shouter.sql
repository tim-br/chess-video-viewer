CREATE TABLE users(
  id SERIAL PRIMARY KEY,
  is_admin boolean DEFAULT FALSE,
  full_name text NOT NULL,
  email text NOT NULL,
  enc_password text NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
);
