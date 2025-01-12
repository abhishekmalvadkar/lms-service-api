-- USERS TABLE
CREATE TABLE users (
    id VARCHAR(40) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    last_login_time DATETIME NULL,
    active BIT(1) NOT NULL DEFAULT 1,
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NULL,
    created_by VARCHAR(40) NULL,
    updated_on DATETIME NULL,
    updated_by VARCHAR(40) NULL,
    role_id VARCHAR(40) NOT NULL
);

-- ROLES TABLE
CREATE TABLE roles (
    id VARCHAR(40) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NULL,
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NOT NULL,
    created_by VARCHAR(40) NULL,
    updated_on DATETIME NOT NULL,
    updated_by VARCHAR(40) NULL,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

ALTER TABLE users
    ADD CONSTRAINT users_created_by_fk FOREIGN KEY (created_by) REFERENCES users(id);

ALTER TABLE users
    ADD CONSTRAINT users_updated_by_fk FOREIGN KEY (updated_by) REFERENCES users(id);

ALTER TABLE users
    ADD CONSTRAINT users_role_id_fk FOREIGN KEY (role_id) REFERENCES roles(id);



-- TAGS TABLE
CREATE TABLE tags (
    id VARCHAR(40) PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NOT NULL,
    created_by VARCHAR(40) NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by VARCHAR(40) NOT NULL,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- LINKS TABLE
CREATE TABLE links (
    id VARCHAR(40) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    url TEXT NOT NULL,
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NOT NULL,
    created_by VARCHAR(40) NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by VARCHAR(40) NOT NULL,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- LINK_TAGS TABLE
CREATE TABLE link_tags (
    id VARCHAR(40) PRIMARY KEY,
    link_id VARCHAR(40) NOT NULL,
    tag_id VARCHAR(40) NOT NULL,
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NOT NULL,
    created_by VARCHAR(40) NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by VARCHAR(40) NOT NULL,
    FOREIGN KEY (link_id) REFERENCES links(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- HEADER_CONFIG TABLE
CREATE TABLE header_config (
    id VARCHAR(40) PRIMARY KEY,
    header_name VARCHAR(100) NULL,
    header_type VARCHAR(100) NULL,
    mapping_table VARCHAR(100) NULL,
    mapping_column VARCHAR(100) NULL,
    category VARCHAR(20) NULL,
    display_order DECIMAL(5,2) NULL,
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NOT NULL,
    created_by VARCHAR(40) NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by VARCHAR(40) NOT NULL,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- INSERT INITIAL DATA FOR ROLES
INSERT INTO roles (id, name, description, delete_flag, created_on, created_by, updated_on, updated_by)
VALUES
    ('01JHCWEFS3D4YMWYGRAMX8FZT1', 'SYSTEM', 'System role with full access', 0, UTC_TIMESTAMP(), null, UTC_TIMESTAMP(), null),
    ('01JHCWEFS3E83G0E16WFXD592C', 'ADMIN', 'Admin role with elevated privileges', 0, UTC_TIMESTAMP(), null, UTC_TIMESTAMP(), null),
    ('01JHCWEFS33BSER6C0MR7YS5W4', 'CUSTOMER', 'Customer role with limited access', 0, UTC_TIMESTAMP(), null, UTC_TIMESTAMP(), null);


-- INSERT SYSTEM USER
INSERT INTO users (id, name, email, password, last_login_time, active, delete_flag, created_on, created_by, updated_on, updated_by, role_id)
VALUES
    ('01JHCWNZ8TJT54N2XW130WDS8K', 'System', 'system@example.com', 'securepasswordhash', NULL, 1, 0, UTC_TIMESTAMP(), NULL, UTC_TIMESTAMP(), NULL, '01JHCWEFS3D4YMWYGRAMX8FZT1');


-- INSERT HEADER CONFIG DATA
INSERT INTO header_config (id, header_name, header_type, mapping_table, mapping_column, category, display_order, delete_flag, created_on, created_by, updated_on, updated_by)
VALUES
    ('01JHCX6M68QF420CWFCCR4KTNZ', 'Name', 'text', 'tags', 'name', 'TAG', 1.00, 0, UTC_TIMESTAMP(), '01JHCWNZ8TJT54N2XW130WDS8K', UTC_TIMESTAMP(), '01JHCWNZ8TJT54N2XW130WDS8K'),
    ('01JHCXA2Z90WRZQ83DW2C94N4D', 'Title', 'text', 'links', 'title', 'LINK', 1.00, 0, UTC_TIMESTAMP(), '01JHCWNZ8TJT54N2XW130WDS8K', UTC_TIMESTAMP(), '01JHCWNZ8TJT54N2XW130WDS8K'),
    ('01JHCXCER8V8GH99QQR1QEYCPC', 'URL', 'text', 'links', 'url', 'LINK', 2.00, 0, UTC_TIMESTAMP(), '01JHCWNZ8TJT54N2XW130WDS8K', UTC_TIMESTAMP(), '01JHCWNZ8TJT54N2XW130WDS8K'),
    ('01JHCXEC9Q6QW0HM1XYBQYXWRC', 'Tags', 'dropdown-mul', 'link_tag', 'tag_id', 'LINK', 3.00, 0, UTC_TIMESTAMP(), '01JHCWNZ8TJT54N2XW130WDS8K', UTC_TIMESTAMP(), '01JHCWNZ8TJT54N2XW130WDS8K');