-- 관리자 계정 생성 (비밀번호: admin123)
-- BCrypt로 해시된 비밀번호: $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa
INSERT INTO users (email, password, name, role) 
VALUES ('admin@cyberboard.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', '관리자', 'ADMIN')
ON DUPLICATE KEY UPDATE role = 'ADMIN';
