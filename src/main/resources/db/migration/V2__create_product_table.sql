CREATE TABLE test.product (
                              prd_idx INT NOT NULL AUTO_INCREMENT,
                              prd_name VARCHAR(255) NOT NULL,
                              prd_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                              prd_thumbnail VARCHAR(255),
                              prd_created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                              prd_updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              prd_deleted_at DATETIME NULL,
                              PRIMARY KEY (prd_idx)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;