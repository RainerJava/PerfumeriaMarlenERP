use mysql;

DELETE FROM user WHERE user='PMARLEN_TEST';
GRANT SELECT,INSERT,UPDATE,DELETE ON PMARLEN_DB_TEST.* TO 'PMARLEN_TEST'@'localhost' IDENTIFIED BY 'PMARLEN_TEST_PASSWORD';
