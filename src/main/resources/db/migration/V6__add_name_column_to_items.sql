ALTER TABLE ITEMS ADD COLUMN NAME VARCHAR(100) NOT NULL DEFAULT 'Empty name';
ALTER TABLE ITEMS ALTER COLUMN DESCRIPTION VARCHAR(100) NULL;