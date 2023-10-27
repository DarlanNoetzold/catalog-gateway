-- Inserções de dados para a tabela public.categorymodel
INSERT INTO public.categorymodel (categoryid, name)
VALUES
    ('f05baf54-ae7b-4922-9a49-2c0a5252b77c', 'Electronics'),
    ('a7e25a1f-7b23-45e6-8e7c-15a62ec2a893', 'Clothing');

-- Inserções de dados para a tabela public.productmodel
INSERT INTO public.productmodel (allowautomaticskumarketplacecreation, calculatedprice, hasvariations, warrantytime, categoryid, productid, brand, code, definitionpricescope, description, gender, message, model, title, videourl, warrantytext)
VALUES
    (true, true, true, 12, 'f05baf54-ae7b-4922-9a49-2c0a5252b77c', '8a9e9d58-4613-43e4-a090-70f98b157c86', 'Sony', 'SONY123', 'Electronics', '4K Smart TV', 'Unisex', 'High-quality display', 'XBR-55X900H', '55" 4K Smart LED TV', 'https://www.example.com/sony-tv', '1 year warranty'),
    (false, false, false, 24, 'a7e25a1f-7b23-45e6-8e7c-15a62ec2a893', 'c5bdaa1d-88c7-47a5-9a63-d8c4e8ad17d8', 'Nike', 'NIKE456', 'Clothing', 'Running Shoes', 'Men', 'Comfortable running shoes', 'Air Zoom Pegasus 38', 'Mens Running Shoes', 'https://www.example.com/nike-shoes', '2 years warranty');

-- Inserções de dados para a tabela public.skumodel
INSERT INTO public.skumodel (enabled, height, length, price, saleprice, weight, width, sellerid, stocklevel, productid, skuid, displayname, ean, lwhuom, partnerid, weightuom)
VALUES
    (true, 32.5, 56.2, 699.99, 649.99, 4.7, 12.0, 123456, 50, '8a9e9d58-4613-43e4-a090-70f98b157c86', 'd7b9f47d-9a1a-4a3e-b4d8-9e3271632f13', 'Sony XBR-55X900H 55" 4K Smart LED TV', '1234567890123', 'cm', 'ElectronicsPartner123', 'kg'),
    (true, 28.0, 30.5, 129.99, 119.99, 0.7, 10.2, 987654, 100, '8a9e9d58-4613-43e4-a090-70f98b157c86', 'f9cebd5b-45f3-43c6-8e4a-72d3b9ea3c7c', 'Nike Air Zoom Pegasus 38 Mens Running Shoes', '9876543210987', 'cm', 'ClothingPartner456', 'kg');

-- Inserções de dados para a tabela public.attributemodel
INSERT INTO public.attributemodel (attributeid, skuid, description, hexcode, imageurl, internalname, name, priority, type)
VALUES
    ('d6e35213-7757-4eb4-9a5a-755e2cde9011', 'd7b9f47d-9a1a-4a3e-b4d8-9e3271632f13', 'Smart TV with 4K resolution', '#000000', 'https://www.example.com/tv-attribute.jpg', 'smart_tv', 'Smart TV', '1', 'Text'),
    ('e5d8f39c-70a5-4e1e-9aa2-56a7c9ae2c31', 'f9cebd5b-45f3-43c6-8e4a-72d3b9ea3c7c', 'Nike Air Zoom technology', '#0000FF', 'https://www.example.com/shoes-attribute.jpg', 'air_zoom', 'Air Zoom', '1', 'Text');

-- Inserções de dados para a tabela public.keywordmodel
INSERT INTO public.keywordmodel (keywordid, skuid, keyword)
VALUES
    ('c5e80b92-8d5f-4cb5-9181-42d418eb5b6a', 'd7b9f47d-9a1a-4a3e-b4d8-9e3271632f13', '4K TV'),
    ('f8d7a61e-cc6a-496d-8d8a-d7f86a1b4e3d', 'f9cebd5b-45f3-43c6-8e4a-72d3b9ea3c7c', 'Running Shoes');

-- Inserções de dados para a tabela public.mediamodel
INSERT INTO public.mediamodel (mediaid, skuid, largeimageurl, mediumimageurl, smallimageurl, thumbnailimageurl, zoomimageurl)
VALUES
    ('6bafc0a1-7b98-4e3c-90ac-d786cb17d5f8', 'd7b9f47d-9a1a-4a3e-b4d8-9e3271632f13', 'https://www.example.com/large-tv-image.jpg', 'https://www.example.com/medium-tv-image.jpg', 'https://www.example.com/small-tv-image.jpg', 'https://www.example.com/thumbnail-tv-image.jpg', 'https://www.example.com/zoom-tv-image.jpg'),
    ('26e89b76-183c-4a4b-9ac9-1dc9f3c0f5e7', 'f9cebd5b-45f3-43c6-8e4a-72d3b9ea3c7c', 'https://www.example.com/large-shoes-image.jpg', 'https://www.example.com/medium-shoes-image.jpg', 'https://www.example.com/small-shoes-image.jpg', 'https://www.example.com/thumbnail-shoes-image.jpg', 'https://www.example.com/zoom-shoes-image.jpg');
