INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (username,email, password) VALUES ('ansarbachir','bachir@ansar', 'password1')  RETURNING id;
INSERT INTO users (username,email, password) VALUES ('ansaranes','anes@ansar', 'password2')  RETURNING id;
 
 
WITH admin_user AS (
    SELECT id FROM users WHERE username = 'ansarbachir'
),
normal_user AS (
    SELECT id FROM users WHERE username = 'ansaranes'
),
admin_role AS (
    SELECT id FROM roles WHERE name = 'ROLE_ADMIN'
),
user_role AS (
    SELECT id FROM roles WHERE name = 'ROLE_USER'
)
INSERT INTO users_roles (user_id, role_id)
SELECT id, (SELECT id FROM admin_role) FROM admin_user
UNION ALL
SELECT id, (SELECT id FROM user_role) FROM normal_user
UNION ALL
SELECT id, (SELECT id FROM admin_role) FROM normal_user;
