CREATE TABLE users_role (
   role_id BIGINT NOT NULL,
   users_id BIGINT NOT NULL,
   FOREIGN KEY (role_id) REFERENCES roles (id),
   FOREIGN KEY (users_id) REFERENCES users (id)
);