-- categoryテーブル
INSERT IGNORE INTO categorys (id, name) VALUES (1, 'ラーメン');
INSERT IGNORE INTO categorys (id, name) VALUES (2, '和食');
INSERT IGNORE INTO categorys (id, name) VALUES (3, 'おでん');
INSERT IGNORE INTO categorys (id, name) VALUES (4, '揚げ物');
INSERT IGNORE INTO categorys (id, name) VALUES (5, 'イタリアン');
INSERT IGNORE INTO categorys (id, name) VALUES (6, 'ピザ');
INSERT IGNORE INTO categorys (id, name) VALUES (7, 'お好み焼き');
INSERT IGNORE INTO categorys (id, name) VALUES (8, 'カレー');
INSERT IGNORE INTO categorys (id, name) VALUES (9, 'フランス料理');
INSERT IGNORE INTO categorys (id, name) VALUES (10, 'パン');
INSERT IGNORE INTO categorys (id, name) VALUES (11, '鉄板焼き');

-- shopテーブル
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) 
VALUES (1, 1,'NAGOYAラーメン', 'shop01.jpg', '名古屋味噌を使ったラーメンが絶品です', '1000' , '2000' ,'10:00','19:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-678' ,'月曜日');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (2, 2,'NAGOYA飯', 'shop01.jpg', 'ごはんがおいしいです', '1000' , '3000' ,'10:00','19:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-500','月、火');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (3, 3,'NAGOYAおでん', 'shop01.jpg', '名古屋味噌を使った特製のおでんです', '1000' , '1500' ,'10:00','19:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-400','月、水');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (4, 4,'NAGOYAの唐揚げ', 'shop01.jpg', '50年続く老舗の店舗です', '1000' , '3000' ,'10:00','17:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-300','月、火');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (5, 5,'NAGOYAパスタ', 'shop01.jpg', '素材にこだわった店舗です', '1000' , '3000' ,'10:00','19:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-200','月、火');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (6, 6,'NAGOYAピザ', 'shop01.jpg', 'かまどが設置されており、本格的なピザを楽しめます', '1000' , '3000' ,'19:00','24:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-100','月、水');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (7, 7,'関西風お好みやき', 'shop01.jpg', '関西風のお好み焼きが楽しめます', '1000' , '3000' ,'18:00','23:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-120','月、水');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (8, 8,'本格カレー', 'shop01.jpg', 'カレーがおいしいです', '1000' , '3000' ,'10:00','18:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-130','月、水');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (9, 9,'THE フランス', 'shop01.jpg', 'フランスパンが絶品です', '1000' , '3000' ,'10:00','19:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-140','月、水');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (10,10,'近所のパン屋さん', 'shop01.jpg', '水曜日になると割引があります', '1000' , '3000' ,'10:00','19:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-150','月、水');
INSERT IGNORE INTO shops (id, category_id,name, image_name, description, price_lower_limit, price_upper_limit,opening_times, closed_times,postal_code, address, phone_number,closed_day) VALUES (11, 11,'鉄板焼きやまちゃん', 'shop01.jpg', '店主がこだわった鉄板料理です。', '1000' , '3000' ,'10:00','19:00', '481-0046', '愛知県北名古屋市石橋X-XX-XX', '012-345-160','月、水');

-- rolesテーブル
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_PREMIUM');

-- usersテーブル
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (1, '笠原 太郎', 'カサハラ タロウ', '101-0022', '東京都千代田区神田練塀町300番地', '090-1234-5678', 'taro.kasahara@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (2, '笠原 花子', 'カサハラ ハナコ', '101-0022', '東京都千代田区神田練塀町300番地', '090-1234-5678', 'hanako.kasahara@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 2, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (3, '笠原 義勝', 'カサハラ ヨシカツ', '638-0644', '奈良県五條市西吉野町湯川X-XX-XX', '090-1234-5678', 'yoshikatsu.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (4, '笠原 幸美', 'カサハラ サチミ', '342-0006', '埼玉県吉川市南広島X-XX-XX', '090-1234-5678', 'sachimi.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (5, '笠原 雅', 'カサハラ ミヤビ', '527-0209', '滋賀県東近江市佐目町X-XX-XX', '090-1234-5678', 'miyabi.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (6, '笠原 正保', 'カサハラ マサヤス', '989-1203', '宮城県柴田郡大河原町旭町X-XX-XX', '090-1234-5678', 'masayasu.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (7, '笠原 真由美', 'カサハラ マユミ', '951-8015', '新潟県新潟市松岡町X-XX-XX', '090-1234-5678', 'mayumi.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (8, '笠原 安民', 'カサハラ ヤスタミ', '241-0033', '神奈川県横浜市旭区今川町X-XX-XX', '090-1234-5678', 'yasutami.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (9, '笠原 章緒', 'カサハラ アキオ', '739-2103', '広島県東広島市高屋町宮領X-XX-XX', '090-1234-5678', 'akio.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (10, '笠原 祐子', 'カサハラ ユウコ', '601-0761', '京都府南丹市美山町高野X-XX-XX', '090-1234-5678', 'yuko.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (11, '笠原 秋美', 'カサハラ アキミ', '606-8235', '京都府京都市左京区田中西春菜町X-XX-XX', '090-1234-5678', 'akimi.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (12, '笠原 信平', 'カサハラ シンペイ', '673-1324', '兵庫県加東市新定X-XX-XX', '090-1234-5678', 'shinpei.kasahara@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (15, '笠原 強士', 'カサハラ ツヨシ', '999-9999', '兵庫県加東市新定X-XX-XX', '090-1234-9999', 'tuyosi.kasahara@example.com', 'password', 3, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (16, '笠原 弱士', 'カサハラ ヨワシ', '999-9999', '兵庫県加東市新定X-XX-XX', '090-1234-9999', 'yowasi.kasahara@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 3, true);
-- reviewテーブル
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(1,1,1,4,'とてもよかったです');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(2,1,2,5,'完璧でした');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(3,1,3,4,'とてもよかったです');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(4,1,4,3,'やすくてよかったです');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(5,1,5,2,'少し雰囲気がよくなかったです');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(6,1,6,1,'ちょっと使いづらかったです');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(7,1,7,4,'とてもよかったです');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(8,1,8,4,'とてもよかったです');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(9,1,9,4,'とてもよかったです');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(10,1,10,4,'とてもよかったです');
INSERT IGNORE INTO reviews (id, shop_id, user_id, score, content) VALUES(11,1,11,4,'とてもよかったです');

-- companyテーブル
INSERT IGNORE INTO companys (id, name, address, representative, established, capital ,description ,number_of_employees) VALUES(1,'NAGOYAMESHI株式会社','〒460-0002 愛知県名古屋市中区丸の内1-9-16	丸の内Oneビルディング3F','笠原虎次郎','2017年2月28日','一億円','飲食店等の情報提供サービス','80名');



-- termsテーブル
INSERT IGNORE INTO terms (id, description) VALUES(1,'﻿# NAGOYAMESHI 要件定義書\n## NAGOYAMESHI 要件定義書');


--reservationsテーブル
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(1,1,1,'2024-04-30','17:00',1);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(2,2,1,'2024-04-30','17:00',2);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(3,3,1,'2024-04-30','17:00',2);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(4,4,1,'2024-04-30','17:00',2);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(5,5,1,'2024-04-30','17:00',2);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(6,6,1,'2024-04-30','17:00',2);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(7,7,1,'2024-04-30','17:00',2);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(8,8,1,'2024-04-30','17:00',2);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(9,9,1,'2024-04-30','17:00',2);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(10,10,1,'2024-04-30','17:00',2);
INSERT IGNORE INTO reservations (id,shop_id, user_id ,checkin_date,time_date,number_of_people) VALUES(11,11,1,'2024-04-30','17:00',2);