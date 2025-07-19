ALTER TABLE users ADD CONSTRAINT uq_user_email UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT uq_user_username UNIQUE (username);