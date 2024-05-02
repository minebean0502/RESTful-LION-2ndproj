# 호텔 넣는 부분
INSERT INTO hotel (title, address, area, first_image, tel, mapX, mapY, description, avg_score, review_count, like_count, created_at, updated_at)
VALUES ('호텔 캘리포니아1', '캘리포니아 어딘가', '캘리포니아', 'image_url_here', '123-456-7890', '123.456', '987.654', '최고의 호텔입니다.', 4.5, 100, 50, NOW(), NOW());

# room 넣는 부분
INSERT INTO room (name, price, content, hotel_id, created_at, updated_at)
VALUES ('10번방', '20000', '10번방이에용', 1, NOW(), NOW());

# member에 대해서는 비밀번호가 encode가 들어가기 때문에 직접 생성 X
# But manager가 생성되었다면, 그 사람들에게 호텔 적절히 매칭시켜줘야함
# UPDATE member SET business_number = '1234' WHERE id = 12; 이건 매 줄 각각 해줘야함

# 이건 한번에 처리하는법
UPDATE member
set business_number = CASE id
    WHEN 12 THEN '1234'
    WHEN 13 THEN '2345'
    WHEN 14 THEN '3456'
    WHEN 15 THEN '4567'
    WHEN 16 THEN '5678'
END
WHERE id IN (12, 13, 14, 15, 16);

