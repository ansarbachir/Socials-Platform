INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (username,email, password) VALUES ('ansarbachir','ansar.bachir.94@gmail.com', 'password1');
INSERT INTO users (username,email, password) VALUES ('ansaranes','anes.23@gmail.com' 'password2');


INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
 
