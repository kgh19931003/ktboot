CREATE TABLE test.product_img (
                                  prdi_idx INT NOT NULL AUTO_INCREMENT,
                                  prdi_prd_idx INT NOT NULL,
                                  prdi_origin_name VARCHAR(255),
                                  prdi_name VARCHAR(255),
                                  prdi_dir VARCHAR(255),
                                  prdi_src VARCHAR(255),
                                  prdi_size BIGINT,
                                  prdi_content_type VARCHAR(255),
                                  prdi_order INT DEFAULT 0,
                                  prdi_uuid VARCHAR(255),
                                  prdi_created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  prdi_updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  prdi_deleted_at DATETIME,
                                  PRIMARY KEY (prdi_idx)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
