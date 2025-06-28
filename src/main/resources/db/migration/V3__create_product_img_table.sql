CREATE TABLE test.product_img (
                                  prdi_idx INT NOT NULL AUTO_INCREMENT,
                                  prdi_prd_idx INT,
                                  prdi_origin_name VARCHAR(255),
                                  prdi_name VARCHAR(255),
                                  prdi_dir VARCHAR(255),
                                  prdi_src VARCHAR(255),
                                  prdi_size DOUBLE,
                                  prdi_content_type VARCHAR(255),
                                  prdi_order INT,
                                  prdi_uuid VARCHAR(255),
                                  prdi_created_at DATETIME,
                                  prdi_updated_at DATETIME,
                                  prdi_deletd_at DATETIME,
                                  PRIMARY KEY (prdi_idx)
);
