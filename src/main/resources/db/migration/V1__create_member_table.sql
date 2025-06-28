CREATE TABLE test.member (
                             mem_idx BIGINT NOT NULL AUTO_INCREMENT,
                             mem_id VARCHAR(50),
                             mem_password VARCHAR(255),
                             mem_name VARCHAR(50),
                             mem_gender VARCHAR(5),
                             mem_created_at VARCHAR(50),
                             mem_updated_at VARCHAR(50),
                             mem_deleted_at VARCHAR(50),
                             mem_access_token VARCHAR(255),
                             mem_refresh_token VARCHAR(255),
                             PRIMARY KEY (mem_idx)
);
