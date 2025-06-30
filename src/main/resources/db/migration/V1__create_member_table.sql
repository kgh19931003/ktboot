CREATE TABLE test.member (
                             mem_idx BIGINT NOT NULL AUTO_INCREMENT,
                             mem_id VARCHAR(50) NOT NULL UNIQUE,
                             mem_password VARCHAR(255),
                             mem_name VARCHAR(50),
                             mem_gender ENUM('M', 'F') DEFAULT 'M',
                             mem_created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                             mem_updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             mem_deleted_at DATETIME NULL,
                             mem_access_token VARCHAR(255),
                             mem_refresh_token VARCHAR(255),
                             PRIMARY KEY (mem_idx)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;