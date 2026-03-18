-- PRODUCTS
INSERT INTO PRODUCTS (ACCOUNT_ID, NAME, DESCRIPTION, PRICE) VALUES
                                                                (1,'Laptop Pro','High performance laptop',1500),
                                                                (1,'Wireless Mouse','Ergonomic mouse',40),
                                                                (1,'Mechanical Keyboard','Gaming keyboard',120),
                                                                (1,'Gaming Monitor','144Hz monitor',300),
                                                                (1,'USB-C Hub','Multiport hub',60),
                                                                (2,'Smartphone X','Flagship smartphone',900),
                                                                (2,'Wireless Earbuds','Noise cancelling earbuds',200),
                                                                (2,'Smartwatch','Fitness smartwatch',250),
                                                                (2,'Tablet Plus','10 inch tablet',500),
                                                                (2,'Bluetooth Speaker','Portable speaker',120);

-- CAMPAIGNS
INSERT INTO CAMPAIGNS (ACCOUNT_ID, BID_AMOUNT, CAMPAIGN_FUND, NAME, PRODUCT_ID, RADIUS_IN_KM, STATUS, TOWN) VALUES
                                                                                                                (1,3.5,300,'Premium Laptop Campaign',1,20,'ON','Warsaw'),
                                                                                                                (1,2.2,180,'Business Laptop Ads',1,15,'ON','Krakow'),
                                                                                                                (1,1.8,150,'Student Laptop Deals',1,25,'OFF','Wroclaw'),
                                                                                                                (1,2.7,220,'Gaming Laptop Promotion',1,30,'ON','Gdansk'),
                                                                                                                (1,1.5,120,'Budget Laptop Campaign',1,10,'ON','Poznan'),

                                                                                                                (1,0.8,90,'Wireless Mouse Promo',2,20,'ON','Warsaw'),
                                                                                                                (1,0.6,70,'Office Mouse Campaign',2,15,'ON','Krakow'),
                                                                                                                (1,1.0,110,'Gaming Mouse Ads',2,25,'ON','Wroclaw'),
                                                                                                                (1,0.4,60,'Cheap Mouse Campaign',2,10,'OFF','Lodz'),
                                                                                                                (1,0.9,95,'Ergonomic Mouse Promo',2,18,'ON','Gdansk');

-- CAMPAIGN_KEYWORDS
INSERT INTO CAMPAIGN_KEYWORDS (CAMPAIGN_ID, KEYWORDS) VALUES
                                                          (1,'laptop'),(1,'gaming laptop'),(1,'high performance laptop'),
                                                          (2,'business laptop'),(2,'office laptop'),
                                                          (3,'student laptop'),(3,'cheap laptop'),
                                                          (4,'gaming laptop'),(4,'fast laptop'),
                                                          (5,'budget laptop'),(5,'cheap laptop'),

                                                          (6,'wireless mouse'),(6,'computer mouse'),
                                                          (7,'office mouse'),(7,'ergonomic mouse'),
                                                          (8,'gaming mouse'),(8,'rgb mouse'),
                                                          (9,'cheap mouse'),
                                                          (10,'ergonomic mouse');