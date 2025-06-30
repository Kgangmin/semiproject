create table semi_test_tbl
(
no NUMBER,
name VARCHAR2(10)
);

create table tbl_main_page
(imgno        number  not null
,imgname      Nvarchar2(20) not null
,imgfilename  Nvarchar2(30) not null
,constraint   PK_tbl_main_page_imgno primary key(imgno)
);

create sequence seq_main_image
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

insert into tbl_main_page(imgno, imgname, imgfilename) values(seq_main_image.nextval, '국내여행특가', '국내여행특가.png');      
insert into tbl_main_page(imgno, imgname, imgfilename) values(seq_main_image.nextval, '신한카드', '신한카드.png');
insert into tbl_main_page(imgno, imgname, imgfilename) values(seq_main_image.nextval, '애견펜션', '애견펜션.png');
insert into tbl_main_page(imgno, imgname, imgfilename) values(seq_main_image.nextval, '호캉스특가', '호캉스특가.png');

commit;

select * from tab;

select * from tbl_user_grade;
select * from TBL_STAY_CATEGORY;
select * from TBL_STAY_EXTRAIMG;
select * from TBL_STAY;
select * from TBL_ROOM;
select * from TBL_USER;


insert into tbl_user_grade(grade_no, grade_name, grade_cutoff, pointrate) values(1, 'VVIP', 100000000, 0.08);      
insert into tbl_user_grade(grade_no, grade_name, grade_cutoff, pointrate) values(2, 'VIP', 10000000, 0.05);
insert into tbl_user_grade(grade_no, grade_name, grade_cutoff, pointrate) values(3, 'PLATINUM', 5000000, 0.03);
insert into tbl_user_grade(grade_no, grade_name, grade_cutoff, pointrate) values(4, 'GOLD', 1000000, 0.01);
insert into tbl_user_grade(grade_no, grade_name, grade_cutoff, pointrate) values(5, 'SILVER', 1, 0.005);
insert into tbl_user_grade(grade_no, grade_name, grade_cutoff, pointrate) values(6, 'WHITE', 0, 0.001);

commit;


-- 예약번호 시퀀스
create sequence seq_reservationNo
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

-- 숙소번호 시퀀스
create sequence seq_stayNo
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

-- 객실번호 시퀀스
create sequence seq_roomNo
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


insert into tbl_user(user_id, user_pwd, user_name, email, mobile, birthday, fk_grade_no) values('leess', 'qwer1234$', '이순신', 'leess@gmail.com', '01012345678','1999-01-01', '6');
select * from tbl_user;

commit;
<<<<<<< HEAD
<<<<<<< HEAD

--------------------------------------------------------------------------------

desc tbl_stay;

alter  table tbl_stay add (postcode           varchar2(5));
alter  table tbl_stay add (address            varchar2(200));
alter  table tbl_stay add (detailaddress      varchar2(200));
alter  table tbl_stay add (extraaddress       varchar2(200));

commit;

alter   table tbl_user modify(email     varchar2(200));
alter   table tbl_user modify(mobile     varchar2(200));

commit;

desc tbl_user;

--------------------------------------------------------------------------------

-- =========================
-- 호텔 (카테고리 1, thumbnails 1~10)
-- =========================

-- 1. Lotte Hotel Seoul
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '롯데호텔서울',
   'A',
   '롯데호텔서울.png',
   '서울 중구 을지로 30에 위치한 1,015실 규모의 5성급 호텔',
   '+82-2-771-1000',
   37.564928,
   126.981079,
   '04533',
   '서울특별시 중구 을지로 30', '롯데호텔 서울', '(소공동)');

-- 2. Grand Hyatt Seoul
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '그랜드하얏트서울',
   'A',
   '그랜드하얏트서울.png',
   '용산구 소월로 322에 위치한 616실 규모의 5성급 호텔',
   '+82-2-797-1234',
   37.539773,
   126.997932,
   '04347',
   '서울특별시 용산구 소월로 322', '그랜드하얏트 서울', '(한남동)');

-- 3. The Plaza Seoul
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '더플라자서울',
   'A',
   '더플라자서울.png',
   '서울시청 앞 소공로 119에 위치한 319실 규모의 럭셔리 부티크 호텔',
   '+82-2-771-1000',
   37.564763,
   126.977956,
   '04525',
   '서울특별시 중구 소공로 119', '', '(태평로2가)' );

-- 4. Koreana Hotel
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '코리아나호텔',
   'A',
   '코리아나호텔.png',
   '서울 중구 세종대로 135에 위치한 344실 규모의 4성급 스카이스크래퍼 호텔',
   '+82-2-759-3000',
   37.568497,
   126.976689,
   '04519',
   '서울 중구 세종대로 135', '코리아나호텔', '(태평로1가)' );

-- 5. Conrad Seoul
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '콘래드서울',
   'A',
   '콘래드서울.png',
   '여의도 IFC 내에 위치한 434실 규모의 5성급 럭셔리 호텔',
   '+82-2-6137-7000',
   37.52428,
   126.92557,
   '07326',
   '서울 영등포구 국제금융로 10', '콘래드 서울', '(여의도동)' );

-- 6. 시그니엘서울
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '시그니엘서울',
   'A',
   '시그니엘서울.png',
   '모든 객실은 탁 트인 서울 전망을 자랑하는 5성급 호텔',
   '02-3213-1000',
   37.513271,
   127.103444,
   '05551',
   '서울시 송파구 올림픽로 300', '시그니엘 서울', '(신천동)' );

-- 7. 세인트존스호텔
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '세인트존스호텔',
   'A',
   '세인트존스호텔.png',
   '강원 강릉에 위치한 강릉역 10분거리 4성급 호텔',
   '033-660-9000',
   37.791443,
   128.921015,
   '25467',
   '강원특별자치도 강릉시 창해로 307', '세인트존스호텔', '(강문동)' );

-- 8. Paradise Hotel Busan
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '파라다이스호텔부산',
   'A',
   '파라다이스호텔부산.png',
   '해운대 해변 바로 앞에 위치한 5성급 리조트형 호텔',
   '+82-51-749-2111',
   35.160122,
   129.164043,
   '48099',
   '부산광역시 해운대구 해운대해변로 296', '파라다이스호텔 부산', '(중동)' );

-- 9. Grand InterContinental Seoul Parnas
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '그랜드인터컨티넨탈서울파르나스',
   'A',
   '그랜드인터컨티넨탈서울파르나스.png',
   '강남구 테헤란로 521에 위치한 516실 규모의 5성급 MICE 호텔',
   '+82-2-555-5656',
   37.509226,
   127.060872,
   '06164',
   '서울특별시 강남구 테헤란로 521', '그랜드인터컨티넨탈 서울파르나스', '(삼성동)' );

-- 10. 신라호텔제주
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '신라호텔제주',
   'A',
   '신라호텔제주.png',
   '태평양과 한라산의 멋진 전망을 감상할 수 있는 5성급 호텔',
   '+82-64-735-5114',
   33.247441,
   126.407966,
   '63535',
   '제주특별자치도 서귀포시 중문관광로72번길 75', '신라호텔제주', '(색달)' );
   

-- =========================
-- 모텔 (카테고리 2, thumbnails 11~20)
-- =========================

-- 11. Sunshine Hotel
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '선샤인호텔',
   'B',
   '선샤인호텔.png',
   '강남구 도산대로 205에 위치한 3성급 비즈니스 모텔',
   '+82-2-548-8222',
   37.517235,
   127.047325,
   '06026',
   '서울특별시 강남구 도산대로 205', '선샤인호텔', '(신사동)' );

-- 12. 수유 cafe 72
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '수유CAFE72',
   'B',
   '수유CAFE72.png',
   '국민은행 수유점 뒷골목에 위치한 소형 비즈니스 모텔',
   '+82-2-744-2766',
   37.633413,
   127.022515,
   '01114',
   '서울특별시 강북구 도봉로73길 39', '수유CAFE78', '(수유동)' );

-- 13. 독산 3S 호텔
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '독산3S호텔',
   'B',
   '독산3S호텔.png',
   '독산역 차량 3분거리에 위치한 비즈니스 모텔',
   '+82-2-804-1584',
   37.465347,
   126.897117,
   '08603',
   '서울특별시 금천구 시흥대로 353', '독산3S호텔', '(독산동)');

-- 14. 호텔 월하여관
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '호텔 월하여관',
   'B',
   '호텔월하여관.png',
   '종로3가역 도보5분에 위치한 새로 오픈한 모텔',
   '+82-50-3505-24585',
   37.576912,
   126.990657,
   '03134',
   '서울특별시 종로구 율곡로10길 24', '호텔 월하여관', '(와룡동)' );

-- 15. 신촌 아늑호텔
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '신촌 아늑호텔',
   'B',
   '신촌아늑호텔.png',
   '신촌역 도보 5분 거리의 아기자기한 모텔',
   '+82-50-3505-30772',
   37.556869,
   126.939605,
   '03780',
   '서울특별시 서대문구 연세로2길 49', '신촌아늑호텔', '(창천동)' );

-- 16. 해운대 아크블루
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   'ARK BLUE',
   'B',
   '해운대아크블루.png',
   '해운대해수욕장 바로앞! 바다전망을 가진 모텔',
   '+82-50-3505-28078',
   35.159779,
   129.156491,
   '48093',
   '부산광역시 해운대구 해운대해변로209번가길 25', '아크블루', '(우동)' );

-- 17. 광안리 YAM
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '광안리 YAM',
   'B',
   '광안리YAM.png',
   '광안리 해변에서 도보 5분 거리에 위치한 모던 모텔',
   '+82-50-3505-25533',
   35.155382,
   129.125396,
   '48283',
   '부산광역시 수영구 광안해변로290번길 20', '광안리 YAM', '(민락동)' );

-- 18. Hound Motel
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '호텔하운드',
   'B',
   '호텔하운드.png',
   '서면 중심가에 위치한 심플,모던 스타일 모텔',
   '+82-51-635-1290',
   35.157778,
   129.056111,
   '47353',
   '부산 부산진구 황령대로7번길 10', '하운드 모텔', '(범천동)' );

-- 19. Page 9
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   'Page 9',
   'B',
   'page9.png',
   '신림역 7번출구 도보3분^^',
   '+82-2-882-9015',
   37.485676,
   126.931526,
   '08754',
   '서울 관악구 신림로66길 30 (신림동)', 'Page 9', '(신림동)' );

-- 20. 속초 리츠호텔
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '리츠호텔',
   'B',
   '속초리츠호텔.png',
   '동명항,등대전망대,포장마차촌 도보5분!',
   '+82-50-3505-14683',
   38.212853,
   128.599618,
   '24811',
   '강원 속초시 영금정로 27 (동명동)', '속초 리츠호텔', '(동명동)' );



-- =========================
-- 펜션 (카테고리 3, thumbnails 21~30)
-- =========================

-- 21. 강릉 블루스테이
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '강릉 블루스테이',
   'C',
   '강릉 블루스테이.png',
   '전 객실 오션뷰, 루프탑 테라스 보유 펜션',
   '+82-10-7189-8260',
   37.796046,
   128.917623,
   '25467',
   '강원특별자치도 강릉시 창해로350번길 21', '블루스테이', '(강문동)' );

-- 22. 강릉 풀빌라 케이
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '강릉 풀빌라 케이',
   'C',
   '강릉 풀빌라 케이.png',
   '사천진 해변 앞 복층 풀빌라형 펜션',
   '+82-33-646-1000',
   37.845791,
   128.869860,
   '25435',
   '강원도 강릉시 사천면 진리해변길 183', '풀빌라 케이', '(사천면)' );

-- 23. 강릉 탑 스파 펜션
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '강릉 탑 스파 펜션',
   'C',
   '강릉 탑 스파 펜션.png',
   '개별 스파 욕조 보유, 오션뷰 스파 펜션',
   '+82-33-661-6622',
   37.812723,
   128.894419,
   '25459',
   '강원도 강릉시 해안로 663-50', '탑 스파 펜션', '(안현동)' );

-- 24. 니나랑 스파 펜션
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '니나랑 스파 펜션',
   'C',
   '니나랑 스파 펜션.png',
   '커플 전용 스파 객실 보유 펜션',
   '+82-33-642-7080',
   37.734867,
   128.989576,
   '25626',
   '강원도 강릉시 강동면 안인진길 34-22', '니나랑 스파 펜션', '(강동면)' );

-- 25. 산토리니 펜션
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '산토리니 펜션',
   'C',
   '산토리니 펜션.png',
   '협재해변 인근 그리스풍 화이트 펜션',
   '+82-64-798-1234',
   33.240365,
   126.364737,
   '63533',
   '제주특별자치도 서귀포시 안덕면 창천리 932-2', '산토리니 펜션', '(안덕면)' );

-- 26. 애월 한옥 펜션
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '애월 한옥 펜션',
   'C',
   '애월 한옥 펜션.png',
   '제주 올레길 코스 인근 전통 한옥 스타일 펜션',
   '+82-64-794-0901',
   33.423682,
   126.344108,
   '63035',
   '제주특별자치도 제주시 애월읍 천덕로 399-84', '애월 한옥 펜션', '(애월읍)' );

-- 27. 힐링 포레스트 펜션
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '힐링 포레스트 펜션',
   'C',
   '힐링 포레스트 펜션.png',
   '북한강 뷰, 숲속 글램핑형 펜션',
   '+82-31-773-1234',
   37.541017,
   127.609731,
   '12512',
   '경기도 양평군 용문면 중원산로453번길 72', '힐링 포레스트 펜션', '(용문면)' );

-- 28. 해뜨는 숲속 펜션
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '해뜨는 숲속 펜션',
   'C',
   '해뜨는 숲속 펜션.png',
   '남이섬·아침고요수목원 인근 커플 전용 펜션',
   '+82-31-581-5555',
   37.766968,
   127.397286,
   '12446',
   '경기도 가평군 상면 청우산2길 42-26', '해뜨는 숲속 펜션', '(상면)' );

-- 29. 스테이 디앤디 펜션
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '스테이 디앤디 펜션',
   'C',
   '스테이 디앤디 펜션.png',
   '서귀포시 한라산 뷰 풀빌라형 펜션',
   '+82-64-732-1234',
   33.245788,
   126.206772,
   '63502',
   '제주특별자치도 서귀포시 대정읍 영락리 1551-8', '스테이 디앤디 펜션', '(대정읍)' );

-- 30. 그바다에 머물다 펜션
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '그바다에 머물다 펜션',
   'C',
   '그바다에 머물다 펜션.png',
   '협재 해변 바로 앞 오션뷰 펜션',
   '+82-64-797-1616',
   33.394131,
   126.241212,
   '63011',
   '제주특별자치도 제주시 한림읍 협재4길 8', '그 바다에 머물다 펜션', '(한림읍)' );


-- =========================
-- 게스트하우스 (카테고리 4, thumbnails 31~40)
-- =========================

-- 31. Zzzip 게스트하우스
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   'Zzzip 게스트하우스',
   'D',
   'Zzzip 게스트하우스.png',
   '명동 중심가 24시간 체크인 가능한 배낭여행객 호스텔',
   '+82-2-318-3798',
   37.547707,
   126.914900,
   '04072',
   '서울 마포구 성지1길 32-16', 'Zzzip 게스트하우스', '(합정동)' );

-- 32. 백팩커스 인사이드
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '백팩커스 인사이드',
   'D',
   '백팩커스 인사이드.png',
   '종로3가역 도보 2분, 저렴한 도미토리 객실',
   '+82-2-6386-6073',
   37.584814,
   126.997853,
   '03074',
   '서울 종로구 성균관로4길 5', '백팩커스 인사이드', '(명륜2가)' );

-- 33. 펀웨이브게스트하우스
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '펀웨이브게스트하우스',
   'D',
   '펀웨이브게스트하우스.png',
   '해운대 해수욕장에서 7분 거리, 해운대역에서 300m 거리에 있는 숙',
   '+82-2-749-1234',
   35.163999,
   129.161275,
   '48095',
   '부산광역시 해운대구 중동1로 11-1 3층', '펀웨이브게스트하우스', '(우동)' );

-- 34. 보령 어썸 게스트하우스
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '보령 어썸 게스트하우스',
   'D',
   '보령 어썸 게스트하우스.png',
   '루프탑파티와 클럽파티가 공존하는 국내 최대 게스트하우스',
   '+82-50-3505-00326',
   36.310886,
   126.516139,
   '33488',
   '충청남도 보령시 고잠2길 28', '어썸 게스트하우스', '(신흑동)' );

-- 35. 이태원 서울큐브 게스트하우스
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '이태원 서울큐브 게스트하우스',
   'D',
   '이태원 서울큐브 게스트하우스.png',
   '이태원에 위치한 캡슐호텔 / 4인실 / 커플룸',
   '+82-50-3505-29859',
   37.534086,
   126.996095,
   '04405',
   '서울특별시 용산구 우사단로14길 8', '서울큐브 게스트하우스', '(이태원동)' );

-- 36. 전주 유정 게스트하우스
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '전주 유정 게스트하우스',
   'D',
   '전주 유정 게스트하우스.png',
   '전주 한옥마을 경기전 인근에 위치한 현대식 구조의 게스트하우스',
   '+82-50-3505-16305',
   35.817485,
   127.149585,
   '55039',
   '전북특별자치도 전주시 완산구 어진길 90-9', '유정 게스트하우스', '(경원동2가)' );

-- 37. 제주 게토 게스트하우스파티
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '제주 게토 게스트하우스파티',
   'D',
   '제주 게토 게스트하우스파티.png',
   '한림·협재해변 인근 감성 게스트하우스',
   '050350520544',
   33.299884,
   126.713243,
   '63618',
   '제주특별자치도 서귀포시 남원읍 서의로154번길 69', '게토 게스트하우스파티', '(남원읍)' );

-- 38. 속초 하루 게스트하우스
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '속초 하루 게스트하우스',
   'D',
   '속초 하루 게스트하우스.png',
   '감성적인 라운지, 포토존, 시리얼룸 보유',
   '+82-50-3505-14751',
   38.211681,
   128.591586,
   '24822',
   '강원도 속초시 장안로 19-1 (동명동)', '하루 게스트하우스', '(동명동)' );

-- 39. 제주 반집 게스트하우스
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '제주 반집 게스트하우스',
   'D',
   '제주 반집 게스트하우스.png',
   '조용하고 한적한 제주스러운 마을',
   '+82-50-3505-17264',
   33.501469,
   126.774389,
   '63356',
   '제주특별자치도 제주시 구좌읍 덕평로 394', '반집 게스트하우스', '(구좌읍)' );

-- 40. 서울숲 스테이
INSERT INTO tbl_stay
  (stay_no, stay_name, fk_stay_category_no, stay_thumbnail,
   stay_info, stay_tel, latitude, longitude, postcode, address,
   detailaddress, extraaddress)
VALUES
  (seq_stayNo.nextval,
   '서울숲 스테이',
   'D',
   '서울숲 스테이.png',
   '도심을 내려다 볼수있는 루프탑 테라스가 있는 게스트하우스',
   '+82-50-3505-21343',
   37.547481,
   127.044814,
   '04778',
   '서울특별시 성동구 왕십리로 108-1', '서울숲스테이', '(성수동1가)' );
   
rollback;
--------------------------------------------------------------------------------

desc tbl_room;
select * from tbl_stay order by TO_NUMBER(stay_no);



insert into tbl_room values('1-1', '1', 'twin', '롯데호텔서울_room1.png', 1053004, '기준2인/최대2인 싱글베드2개 조식 아웃도어뷰');
insert into tbl_room values('1-2', '1', 'standard', '롯데호텔서울_room2.png', 2170000, '기준2인/최대2인 더블베드1개 조식 아웃도어뷰');
insert into tbl_room values('1-3', '1', 'deluxe', '롯데호텔서울_room3.png', 4837858, '기준2인/최대3인 킹베드1개 조식 아웃도어뷰 객실 크기 127m²');

insert into tbl_room values('2-1', '2', 'standard', '그랜드하얏트서울_room1.png', 683886, '기준2인 아동1명 무료투숙 가능 킹베드1개 조식 리버뷰');
insert into tbl_room values('2-2', '2', 'sweet', '그랜드하얏트서울_room2.png', 954571, '기준2인 아동2명 무료투숙 가능 킹베드1개 조식 리버뷰');
insert into tbl_room values('2-3', '2', 'deluxe', '그랜드하얏트서울_room3.png', 1028571, '기준2인 아동2명 무료투숙 가능 킹베드1개 조식 스파 리버뷰');

insert into tbl_room values('3-1', '3', 'standard', '더플라자서울_room1.png', 570000, '기준2인/최대2인 킹베드1개 조식 시티뷰');
insert into tbl_room values('3-2', '3', 'twin', '더플라자서울_room2.png', 954571, '기준2인/최대2인 싱글베드2개 조식 시티뷰');
insert into tbl_room values('3-3', '3', 'deluxe', '더플라자서울_room3.png', 1028571, '기준2인/최대3인 킹베드1개 조식 시티뷰 할부 결제가능');

insert into tbl_room values('4-1', '4', 'standard', '코리아나호텔_room1.png', 605000, '기준2인/최대2인 더블베드1개 조식 스트리트뷰');
insert into tbl_room values('4-2', '4', 'twin', '코리아나호텔_room2.png', 799496, '기준2인/최대2인 싱글베드2개 조식 시티뷰 욕조');
insert into tbl_room values('4-3', '4', 'deluxe', '코리아나호텔_room3.png', 1420800, '기준4인/최대4인 퀸베드1개 조식 시티뷰 욕조');

insert into tbl_room values('5-1', '5', 'standard', '콘래드서울_room1.png', 579000, '기준2인/최대2인 킹베드1개 조식 리버뷰');
insert into tbl_room values('5-2', '5', 'deluxe', '콘래드서울_room3.png', 820800, '기준2인/최대3인 퀸베드1개 조식 시티뷰 발코니/테라스 욕조 라운지 이용가능');

insert into tbl_room values('6-1', '6', 'standard', '시그니엘서울_room1.png', 558000, '기준2인/최대2인 킹베드1개 조식 아웃도어뷰 피트니스센터');
insert into tbl_room values('6-2', '6', 'twin', '시그니엘서울_room2.png', 899496, '기준2인/최대2인 싱글베드2개 조식 아웃도어뷰 피트니스센터');
insert into tbl_room values('6-3', '6', 'deluxe', '시그니엘서울_room3.png', 1650000, '기준2인/최대3인 퀸베드1개 조식 아웃도어뷰 발코니/테라스 피트니스센터');

insert into tbl_room values('7-1', '7', 'standard', '세인트존스호텔_room1.png', 234145, '기준2인/최대2인 퀸베드1개 조식 마운틴뷰');
insert into tbl_room values('7-2', '7', 'sweet', '세인트존스호텔_room2.png', 325000, '기준2인/최대3인 퀸베드1개 조식 마운틴뷰 발코니/테라스');
insert into tbl_room values('7-3', '7', 'deluxe', '세인트존스호텔_room3.png', 775000, '기준2인/최대3인 킹베드1개 조식 마운틴뷰 발코니/테라스 스파');

insert into tbl_room values('8-1', '8', 'standard', '파라다이스호텔부산_room1.png', 505000, '기준2인/최대2인 더블베드1개 조식 스트리트뷰');
insert into tbl_room values('8-2', '8', 'sweet', '파라다이스호텔부산_room2.png', 640000, '기준2인/최대2인 킹베드1개 조식 시티뷰 욕조');
insert into tbl_room values('8-3', '8', 'deluxe', '파라다이스호텔부산_room3.png', 710000, '기준2인/최대3인 퀸베드1개 조식 시티뷰 발코니/테라스');

insert into tbl_room values('9-1', '9', 'standard', '그랜드인터컨티넨탈서울파르나스_room1.png', 713900, '기준2인/최대2인 킹베드1개 체크인15:00~체크아웃11:00');
insert into tbl_room values('9-2', '9', 'sweet', '그랜드인터컨티넨탈서울파르나스_room2.png', 834900, '기준2인/최대3인 킹베드1개 체크인15:00~체크아웃11:00 조식');
insert into tbl_room values('9-3', '9', 'deluxe', '그랜드인터컨티넨탈서울파르나스_room3.png', 1052700, '기준2인/최대3인 킹베드1개 체크인15:00~체크아웃11:00 조식 스파');

insert into tbl_room values('10-1', '10', 'standard', '신라호텔제주_room1.png', 405000, '기준2인/최대2인 더블베드1개 조식 가든뷰');
insert into tbl_room values('10-2', '10', 'sweet', '신라호텔제주_room2.png', 1899496, '기준2인/최대2인 더블베드1개 조식 가든뷰 발코니/테라스');
insert into tbl_room values('10-3', '10', 'deluxe', '신라호텔제주_room3.png', 5420800, '기준4인/최대4인 퀸베드1개 조식 오션뷰 발코니/테라스 전용수영장');

--------------------------------------------------------------------------------

insert into tbl_room values('11-1', '11', 'standard', '선샤인호텔_room1.png', 107717, '기준2인/최대2인 더블베드1개 스트리트뷰');
insert into tbl_room values('11-2', '11', 'sweet', '선샤인호텔_room2.png', 131079, '기준2인/최대2인 더블베드1개 스트리트뷰 욕조');
insert into tbl_room values('11-3', '11', 'twin', '선샤인호텔_room3.png', 151588, '기준2인/최대2인 싱글베드1개 더블베드1개 스트리뷰 욕조');

insert into tbl_room values('12-1', '12', 'standard', '수유CAFE72_room1.png', 55000, '기준2인/최대2인 킹베드1개 스마트TV');

insert into tbl_room values('13-1', '13', 'standard', '독산3S호텔_room1.png', 52000, '기준2인/최대2인 킹베드1개 75인치TV');
insert into tbl_room values('13-2', '13', 'doublePC', '독산3S호텔_room2.png', 77000, '기준2인/최대2인 킹베드1개 스마트TV 2PC 대형욕조');
insert into tbl_room values('13-3', '13', 'deluxe', '독산3S호텔_room3.png', 82000, '기준2인/최대2인 퀸베드1개 스마트TV 안마의자 1PC 대형욕조');

insert into tbl_room values('14-1', '14', 'standard', '호텔월하여관_room1.png', 30000, '기준2인/최대2인 퀸베드1개');
insert into tbl_room values('14-2', '14', 'premium', '호텔월하여관_room2.png', 40000, '기준2인/최대2인 퀸베드1개 스마트TV');
insert into tbl_room values('14-3', '14', 'deluxe', '호텔월하여관_room3.png', 50000, '기준2인/최대2인 퀸베드1개 스마트TV');

insert into tbl_room values('15-1', '15', 'standard', '신촌아늑호텔_room1.png', 70000, '기준2인/최대2인 킹베드1개 4성급 호텔침구');
insert into tbl_room values('15-2', '15', 'multi', '신촌아늑호텔_room2.png', 95000, '기준2인/최대2인 퀸베드1개 4성급 호텔침구 스마트TV');
insert into tbl_room values('15-3', '15', 'deluxe', '신촌아늑호텔_room3.png', 115000, '기준2인/최대2인 퀸베드1개 4성급 호텔침구 스마트TV');

insert into tbl_room values('16-1', '16', 'standard', '해운대아크블루_room1.png', 44000, '기준2인/최대2인 킹베드1개 넷플릭스 욕조 주차불가');
insert into tbl_room values('16-2', '16', 'sweet', '해운대아크블루_room2.png', 53000, '기준2인/최대2인 킹베드1개 넷플릭스 욕조 쇼파 무료주차');
insert into tbl_room values('16-3', '16', 'deluxe', '해운대아크블루_room3.png', 62000, '기준2인/최대3인 퀸베드1개 넷플릭스 무료주차 오션뷰');

insert into tbl_room values('17-1', '17', 'standard', '광안리YAM_room1.png', 44000, '기준2인/최대2인 더블베드1개 넷플릭스');
insert into tbl_room values('17-2', '17', 'sweet', '광안리YAM_room2.png', 65000, '기준2인/최대2인 킹베드1개 넷플릭스');
insert into tbl_room values('17-3', '17', 'deluxe', '광안리YAM_room3.png', 83000, '기준2인/최대2인 퀸베드1개 넷플릭스');

insert into tbl_room values('18-1', '18', 'standard', '호텔하운드_room1.png', 45000, '기준2인/최대2인 더블베드1개 넷플릭스 욕조');
insert into tbl_room values('18-2', '18', 'premium', '호텔하운드_room2.png', 65000, '기준2인/최대3인 킹베드1개 넷플릭스 욕조');
insert into tbl_room values('18-3', '18', 'deluxe', '호텔하운드_room3.png', 75000, '기준2인/최대2인 퀸베드1개 넷플릭스 욕조');

insert into tbl_room values('19-1', '19', 'standard', 'page9_room1.png', 49500, '기준2인/최대2인 킹베드1개 65인치 스마트TV');
insert into tbl_room values('19-2', '19', 'sweet', 'page9_room2.png', 65000, '기준2인/최대3인 킹베드1개 넷플릭스 욕조');
insert into tbl_room values('19-3', '19', 'deluxe', 'page9_room3.png', 75000, '기준2인/최대2인 퀸베드1개 넷플릭스 욕조');

insert into tbl_room values('20-1', '20', 'standard', '속초리츠호텔_room1.png', 45000, '기준2인/최대2인 더블베드1개 조식 시티뷰');
insert into tbl_room values('20-2', '20', 'sweet', '속초리츠호텔_room2.png', 65000, '기준2인/최대3인 킹베드1개 조식 시티뷰 스마트TV');
insert into tbl_room values('20-3', '20', 'deluxe', '속초리츠호텔_room3.png', 80000, '기준2인/최대3인 퀸베드1개 조식 오션뷰 스마트TV');

--------------------------------------------------------------------------------

insert into tbl_room values('21-1', '21', '201', '강릉 블루스테이_room1.png', 105000, '기준2인/최대2인 더블베드1개 오션뷰 발코니/테라스');
insert into tbl_room values('21-2', '21', '202', '강릉 블루스테이_room2.png', 130000, '기준2인/최대3인 킹베드1개 오션뷰 욕조 발코니/테라스');
insert into tbl_room values('21-3', '21', '203', '강릉 블루스테이_room3.png', 160000, '기준3인/최대3인 싱블베드1개 더블베드1개 오션뷰 욕조 발코니/테라스');

insert into tbl_room values('22-1', '22', '301', '강릉 풀빌라 케이_room1.png', 89000, '기준2인/최대2인 퀸베드1개 오션뷰');
insert into tbl_room values('22-2', '22', '302', '강릉 풀빌라 케이_room2.png', 109000, '기준2인/최대3인 퀸베드1개 월풀욕조');
insert into tbl_room values('22-3', '22', '203', '강릉 풀빌라 케이_room3.png', 219000, '기준2인/최대6인 퀸베드2개 통창오션뷰 수영장 사전문의必');

insert into tbl_room values('23-1', '23', '301', '강릉 탑 스파 펜션_room1.png', 56000, '기준2인/최대3인 더블베드1개 오션뷰 취사시설');
insert into tbl_room values('23-2', '23', '303', '강릉 탑 스파 펜션_room2.png', 65500, '기준2인/최대3인 퀸베드1개 오션뷰 바베큐');
insert into tbl_room values('23-3', '23', '307', '강릉 탑 스파 펜션_room3.png', 75000, '기준2인/최대4인 퀸베드1개 오션뷰 월풀욕조');

insert into tbl_room values('24-1', '24', '201', '니나랑 스파 펜션_room1.png', 79000, '기준2인/최대3인 더블베드1개 오션뷰 취사시설 스파');
insert into tbl_room values('24-2', '24', '303', '니나랑 스파 펜션_room2.png', 89000, '기준2인/최대4인 퀸베드1개 오션뷰 스파 복층');
insert into tbl_room values('24-3', '24', '401', '니나랑 스파 펜션_room3.png', 149000, '기준4인/최대6인 퀸베드1개 오션뷰 취사시설 스파 온돌방');

insert into tbl_room values('25-1', '25', '70평객실', '산토리니 펜션_room1.png', 425600, '기준4인/최대8인 퀸베드3개 오션뷰 수영장 바베큐');

insert into tbl_room values('26-1', '26', '13평원룸형', '애월 한옥 펜션_room1.png', 85500, '기준2인/최대4인 더블베드1개 취사시설 엑스트라베드 최대1개');
insert into tbl_room values('26-2', '26', '13평원룸형', '애월 한옥 펜션_room2.png', 85500, '기준2인/최대4인 더블베드1개 취사시설 엑스트라베드 최대1개');

insert into tbl_room values('27-1', '27', '로즈마리', '힐링 포레스트 펜션_room1.png', 170000, '기준4인/최대6인 킹베드1개 바베큐 온돌방 2층테라스');
insert into tbl_room values('27-2', '27', '아이리스', '힐링 포레스트 펜션_room2.png', 170000, '기준4인/최대6인 킹베드1개 바베큐 온돌방 테라스');
insert into tbl_room values('27-3', '27', '힐링숲1호', '힐링 포레스트 펜션_room3.png', 220000, '기준4인/최대10인 킹베드2개 바베큐 온돌방2개 테라스 안마기');

insert into tbl_room values('28-1', '28', '국화', '해뜨는 숲속 펜션_room1.png', 58000, '기준2인/최대6인 더블베드1개 바베큐 복층');
insert into tbl_room values('28-2', '28', '대나무', '해뜨는 숲속 펜션_room2.png', 110000, '기준4인/최대8인 더블베드1개 바베큐 25평원룸형 테라스');
insert into tbl_room values('28-3', '28', '매화', '해뜨는 숲속 펜션_room3.png', 170000, '기준6인/최대12인 더블베드2개 바베큐 30평거실+방 테라스 화장실2개');

insert into tbl_room values('29-1', '29', 'ResortA207', '스테이 디앤디 펜션_room1.png', 85000, '기준2인/최대6인 더블베드1개 오션뷰 바베큐 복층형');
insert into tbl_room values('29-2', '29', 'ResortA205', '스테이 디앤디 펜션_room2.png', 105000, '기준3인/최대4인 더블베드1개 싱글베드1개 오션뷰 바베큐 원룸형 월풀욕조');
insert into tbl_room values('29-3', '29', '4-Villa독채', '스테이 디앤디 펜션_room3.png', 3500000, '기준2인/최대6인 더블베드2개 오션뷰 바베큐 복층형독채 단독수영장 월풀욕조 스파배스');

insert into tbl_room values('30-1', '30', '05가파도', '그바다에 머물다 펜션_room1.png', 130000, '기준4인/최대5인 퀸베드1개 취사시설 복층형');
insert into tbl_room values('30-2', '30', '07비양도', '그바다에 머물다 펜션_room2.png', 210000, '기준6인/최대7인 퀸베드1개 취사시설 복층형 온돌방1개');

--------------------------------------------------------------------------------

insert into tbl_room values('31-1', '31', '201', 'Zzzip 게스트하우스_room1.png', 40000, '4인실 공용욕실');
insert into tbl_room values('31-2', '31', '202', 'Zzzip 게스트하우스_room2.png', 50000, '2인실 개인욕실');

insert into tbl_room values('32-1', '32', '301', '백패커스 인사이드_room1.png', 30000, '4인실');
insert into tbl_room values('32-2', '32', '302', '백패커스 인사이드_room2.png', 45000, '2인실 PC');
insert into tbl_room values('32-3', '32', '304', '백패커스 인사이드_room3.png', 70000, '1인실');

insert into tbl_room values('33-1', '33', '도미토리', '펀웨이브게스트하우스_room1.png', 23000, '6인실');
insert into tbl_room values('33-2', '33', '2인실', '펀웨이브게스트하우스_room2.png', 49000, '2인실');
insert into tbl_room values('33-3', '33', '1인실', '펀웨이브게스트하우스_room3.png', 65000, '1인실');

insert into tbl_room values('34-1', '34', '도미토리', '보령 어썸 게스트하우스_room1.png', 16000, '6인실/최대8인');
insert into tbl_room values('34-2', '34', '커플룸', '보령 어썸 게스트하우스_room2.png', 42000, '2인실 퀸베드1개');

insert into tbl_room values('35-1', '35', '도미토리', '이태원 서울큐브 게스트하우스_room1.png', 35000, '4인실');
insert into tbl_room values('35-2', '35', '더블룸', '이태원 서울큐브 게스트하우스_room2.png', 95000, '2인실');
insert into tbl_room values('35-3', '35', '싱글룸', '이태원 서울큐브 게스트하우스_room3.png', 130000, '1인실');

insert into tbl_room values('36-1', '36', '달콤한하루', '전주 유정 게스트하우스_room1.png', 35000, '기준2인/최대2인 더블베드1개');
insert into tbl_room values('36-2', '36', '꿈같은하루', '전주 유정 게스트하우스_room2.png', 45000, '기준2인/최대2인 복층형');
insert into tbl_room values('36-3', '36', '완벽한하루', '전주 유정 게스트하우스_room3.png', 80000, '기준4인/최대6인 더블베드1개 복층형');

insert into tbl_room values('37-1', '37', '도미토리', '제주 게토 게스트하우스파티_room1.png', 25000, '6인실');
insert into tbl_room values('37-2', '37', '파티션', '제주 게토 게스트하우스파티_room2.png', 30000, '4인실');

insert into tbl_room values('38-1', '38', '4인도미토리', '속초 하루 게스트하우스_room1.png', 20000, '4인실');
insert into tbl_room values('38-2', '38', '2인도미토리', '속초 하루 게스트하우스_room2.png', 25000, '2인실');
insert into tbl_room values('38-3', '38', '특실', '속초 하루 게스트하우스_room3.png', 50000, '기준2인/최대2인 퀸베드1개 테라스객실 희망시 문의');

insert into tbl_room values('39-1', '39', '2인실', '제주 반집 게스트하우스_room1.png', 40000, '2인실 취사시설');
insert into tbl_room values('39-2', '39', '4인실', '제주 반집 게스트하우스_room2.png', 60000, '4인실 취사시설');

insert into tbl_room values('40-1', '40', '5인패밀리', '서울숲 스테이_room1.png', 22000, '5인실 공용욕실');
insert into tbl_room values('40-2', '40', '2인트윈', '서울숲 스테이_room2.png', 29500, '2인실 공용욕실');
insert into tbl_room values('40-3', '40', '더블룸', '서울숲 스테이_room3.png', 65000, '기준2인/최대2인 더블베드1개 개별욕실');

select * from tbl_room order by TO_NUMBER(fk_stay_no);

commit;

--------------------------------------------------------------------------------

select * from tab;

desc tbl_stay_extraimg;

select * from tbl_stay order by to_number(stay_no);

insert into tbl_stay_extraimg values('1_extraimg1', '1', '롯데호텔서울_extraimg1.png');
insert into tbl_stay_extraimg values('1_extraimg2', '1', '롯데호텔서울_extraimg2.png');
insert into tbl_stay_extraimg values('1_extraimg3', '1', '롯데호텔서울_extraimg3.png');

insert into tbl_stay_extraimg values('2_extraimg1', '2', '그랜드하얏트서울_extraimg1.png');
insert into tbl_stay_extraimg values('2_extraimg2', '2', '그랜드하얏트서울_extraimg2.png');
insert into tbl_stay_extraimg values('2_extraimg3', '2', '그랜드하얏트서울_extraimg3.png');

insert into tbl_stay_extraimg values('3_extraimg1', '3', '더플라자서울_extraimg1.png');
insert into tbl_stay_extraimg values('3_extraimg2', '3', '더플라자서울_extraimg2.png');
insert into tbl_stay_extraimg values('3_extraimg3', '3', '더플라자서울_extraimg3.png');

insert into tbl_stay_extraimg values('4_extraimg1', '4', '코리아나호텔_extraimg1.png');
insert into tbl_stay_extraimg values('4_extraimg2', '4', '코리아나호텔_extraimg2.png');
insert into tbl_stay_extraimg values('4_extraimg3', '4', '코리아나호텔_extraimg3.png');

insert into tbl_stay_extraimg values('5_extraimg1', '5', '콘래드서울_extraimg1.png');
insert into tbl_stay_extraimg values('5_extraimg2', '5', '콘래드서울_extraimg2.png');
insert into tbl_stay_extraimg values('5_extraimg3', '5', '콘래드서울_extraimg3.png');

insert into tbl_stay_extraimg values('6_extraimg1', '6', '시그니엘서울_extraimg1.png');
insert into tbl_stay_extraimg values('6_extraimg2', '6', '시그니엘서울_extraimg2.png');
insert into tbl_stay_extraimg values('6_extraimg3', '6', '시그니엘서울_extraimg3.png');

insert into tbl_stay_extraimg values('7_extraimg1', '7', '세인트존스호텔_extraimg1.png');
insert into tbl_stay_extraimg values('7_extraimg2', '7', '세인트존스호텔_extraimg2.png');
insert into tbl_stay_extraimg values('7_extraimg3', '7', '세인트존스호텔_extraimg3.png');

insert into tbl_stay_extraimg values('8_extraimg1', '8', '파라다이스호텔부산_extraimg1.png');
insert into tbl_stay_extraimg values('8_extraimg2', '8', '파라다이스호텔부산_extraimg2.png');
insert into tbl_stay_extraimg values('8_extraimg3', '8', '파라다이스호텔부산_extraimg3.png');

insert into tbl_stay_extraimg values('9_extraimg1', '9', '그랜드인터컨티넨탈서울파르나스_extraimg1.png');
insert into tbl_stay_extraimg values('9_extraimg2', '9', '그랜드인터컨티넨탈서울파르나스_extraimg2.png');
insert into tbl_stay_extraimg values('9_extraimg3', '9', '그랜드인터컨티넨탈서울파르나스_extraimg3.png');

insert into tbl_stay_extraimg values('10_extraimg1', '10', '신라호텔제주_extraimg1.png');
insert into tbl_stay_extraimg values('10_extraimg2', '10', '신라호텔제주_extraimg2.png');
insert into tbl_stay_extraimg values('10_extraimg3', '10', '신라호텔제주_extraimg3.png');

--------------------------------------------------------------------------------

insert into tbl_stay_extraimg values('11_extraimg1', '11', '선샤인호텔_extraimg1.png');
insert into tbl_stay_extraimg values('11_extraimg2', '11', '선샤인호텔_extraimg2.png');
insert into tbl_stay_extraimg values('11_extraimg3', '11', '선샤인호텔_extraimg3.png');

insert into tbl_stay_extraimg values('12_extraimg1', '12', '수유CAFE72_extraimg1.png');
insert into tbl_stay_extraimg values('12_extraimg2', '12', '수유CAFE72_extraimg2.png');
insert into tbl_stay_extraimg values('12_extraimg3', '12', '수유CAFE72_extraimg3.png');

insert into tbl_stay_extraimg values('13_extraimg1', '13', '독산3S호텔_extraimg1.png');
insert into tbl_stay_extraimg values('13_extraimg2', '13', '독산3S호텔_extraimg2.png');

insert into tbl_stay_extraimg values('14_extraimg1', '14', '호텔월하여관_extraimg1.png');
insert into tbl_stay_extraimg values('14_extraimg2', '14', '호텔월하여관_extraimg2.png');
insert into tbl_stay_extraimg values('14_extraimg3', '14', '호텔월하여관_extraimg3.png');

insert into tbl_stay_extraimg values('15_extraimg1', '15', '신촌아늑호텔_extraimg1.png');
insert into tbl_stay_extraimg values('15_extraimg2', '15', '신촌아늑호텔_extraimg2.png');
insert into tbl_stay_extraimg values('15_extraimg3', '15', '신촌아늑호텔_extraimg3.png');

insert into tbl_stay_extraimg values('16_extraimg1', '16', '해운대아크블루_extraimg1.png');
insert into tbl_stay_extraimg values('16_extraimg2', '16', '해운대아크블루_extraimg2.png');
insert into tbl_stay_extraimg values('16_extraimg3', '16', '해운대아크블루_extraimg3.png');

insert into tbl_stay_extraimg values('17_extraimg1', '17', '광안리YAM_extraimg1.png');
insert into tbl_stay_extraimg values('17_extraimg2', '17', '광안리YAM_extraimg2.png');

insert into tbl_stay_extraimg values('18_extraimg1', '18', '호텔하운드_extraimg1.png');
insert into tbl_stay_extraimg values('18_extraimg2', '18', '호텔하운드_extraimg2.png');
insert into tbl_stay_extraimg values('18_extraimg3', '18', '호텔하운드_extraimg3.png');

insert into tbl_stay_extraimg values('19_extraimg1', '19', 'page9_extraimg1.png');
insert into tbl_stay_extraimg values('19_extraimg2', '19', 'page9_extraimg2.png');
insert into tbl_stay_extraimg values('19_extraimg3', '19', 'page9_extraimg3.png');

insert into tbl_stay_extraimg values('20_extraimg1', '20', '속초리츠호텔_extraimg1.png');
insert into tbl_stay_extraimg values('20_extraimg2', '20', '속초리츠호텔_extraimg2.png');
insert into tbl_stay_extraimg values('20_extraimg3', '20', '속초리츠호텔_extraimg3.png');

--------------------------------------------------------------------------------

insert into tbl_stay_extraimg values('21_extraimg1', '21', '강릉 블루스테이_extraimg1.png');
insert into tbl_stay_extraimg values('21_extraimg2', '21', '강릉 블루스테이_extraimg2.png');

insert into tbl_stay_extraimg values('22_extraimg1', '22', '강릉 풀빌라 케이_extraimg1.png');
insert into tbl_stay_extraimg values('22_extraimg2', '22', '강릉 풀빌라 케이_extraimg2.png');

insert into tbl_stay_extraimg values('23_extraimg1', '23', '강릉 탑 스파 펜션_extraimg1.png');

insert into tbl_stay_extraimg values('24_extraimg1', '24', '니나랑 스파 펜션_extraimg1.png');

insert into tbl_stay_extraimg values('25_extraimg1', '25', '산토리니 펜션_extraimg1.png');
insert into tbl_stay_extraimg values('25_extraimg2', '25', '산토리니 펜션_extraimg2.png');

insert into tbl_stay_extraimg values('26_extraimg1', '26', '애월 한옥 펜션_extraimg1.png');
insert into tbl_stay_extraimg values('26_extraimg2', '26', '애월 한옥 펜션_extraimg2.png');

insert into tbl_stay_extraimg values('27_extraimg1', '27', '힐링 포레스트 펜션_extraimg1.png');
insert into tbl_stay_extraimg values('27_extraimg2', '27', '힐링 포레스트 펜션_extraimg2.png');
insert into tbl_stay_extraimg values('27_extraimg3', '27', '힐링 포레스트 펜션_extraimg3.png');

insert into tbl_stay_extraimg values('28_extraimg1', '28', '해뜨는 숲속 펜션_extraimg1.png');
insert into tbl_stay_extraimg values('28_extraimg2', '28', '해뜨는 숲속 펜션_extraimg2.png');
insert into tbl_stay_extraimg values('28_extraimg3', '28', '해뜨는 숲속 펜션_extraimg3.png');

insert into tbl_stay_extraimg values('29_extraimg1', '29', '스테이 디앤디 펜션_extraimg1.png');
insert into tbl_stay_extraimg values('29_extraimg2', '29', '스테이 디앤디 펜션_extraimg2.png');
insert into tbl_stay_extraimg values('29_extraimg3', '29', '스테이 디앤디 펜션_extraimg3.png');

insert into tbl_stay_extraimg values('30_extraimg1', '30', '그바다에 머물다 펜션_extraimg1.png');

--------------------------------------------------------------------------------

insert into tbl_stay_extraimg values('31_extraimg1', '31', 'Zzzip 게스트하우스_extraimg1.png');
insert into tbl_stay_extraimg values('31_extraimg2', '31', 'Zzzip 게스트하우스_extraimg2.png');

insert into tbl_stay_extraimg values('32_extraimg1', '32', '백팩커스 인사이드_extraimg1.png');
insert into tbl_stay_extraimg values('32_extraimg2', '32', '백팩커스 인사이드_extraimg2.png');

insert into tbl_stay_extraimg values('33_extraimg1', '33', '펀웨이브게스트하우스_extraimg1.png');
insert into tbl_stay_extraimg values('33_extraimg2', '33', '펀웨이브게스트하우스_extraimg2.png');

insert into tbl_stay_extraimg values('34_extraimg1', '34', '보령 어썸 게스트하우스_extraimg1.png');
insert into tbl_stay_extraimg values('34_extraimg2', '34', '보령 어썸 게스트하우스_extraimg2.png');

insert into tbl_stay_extraimg values('35_extraimg1', '35', '이태원 서울큐브 게스트하우스_extraimg1.png');
insert into tbl_stay_extraimg values('35_extraimg2', '35', '이태원 서울큐브 게스트하우스_extraimg2.png');
insert into tbl_stay_extraimg values('35_extraimg3', '35', '이태원 서울큐브 게스트하우스_extraimg3.png');

insert into tbl_stay_extraimg values('36_extraimg1', '36', '전주 유정 게스트하우스_extraimg1.png');
insert into tbl_stay_extraimg values('36_extraimg2', '36', '전주 유정 게스트하우스_extraimg2.png');
insert into tbl_stay_extraimg values('36_extraimg3', '36', '전주 유정 게스트하우스_extraimg3.png');
insert into tbl_stay_extraimg values('36_extraimg4', '36', '전주 유정 게스트하우스_extraimg4.png');
insert into tbl_stay_extraimg values('36_extraimg5', '36', '전주 유정 게스트하우스_extraimg5.png');

insert into tbl_stay_extraimg values('37_extraimg1', '37', '제주 게토 게스트하우스파티_extraimg1.png');
insert into tbl_stay_extraimg values('37_extraimg2', '37', '제주 게토 게스트하우스파티_extraimg2.png');
insert into tbl_stay_extraimg values('37_extraimg3', '37', '제주 게토 게스트하우스파티_extraimg3.png');
insert into tbl_stay_extraimg values('37_extraimg4', '37', '제주 게토 게스트하우스파티_extraimg4.png');
insert into tbl_stay_extraimg values('37_extraimg5', '37', '제주 게토 게스트하우스파티_extraimg5.png');
insert into tbl_stay_extraimg values('37_extraimg6', '37', '제주 게토 게스트하우스파티_extraimg6.png');

insert into tbl_stay_extraimg values('38_extraimg1', '38', '속초 하루 게스트하우스_extraimg1.png');
insert into tbl_stay_extraimg values('38_extraimg2', '38', '속초 하루 게스트하우스_extraimg2.png');
insert into tbl_stay_extraimg values('38_extraimg3', '38', '속초 하루 게스트하우스_extraimg3.png');
insert into tbl_stay_extraimg values('38_extraimg4', '38', '속초 하루 게스트하우스_extraimg4.png');
insert into tbl_stay_extraimg values('38_extraimg5', '38', '속초 하루 게스트하우스_extraimg5.png');
insert into tbl_stay_extraimg values('38_extraimg6', '38', '속초 하루 게스트하우스_extraimg6.png');

insert into tbl_stay_extraimg values('39_extraimg1', '39', '제주 반집 게스트하우스_extraimg1.png');
insert into tbl_stay_extraimg values('39_extraimg2', '39', '제주 반집 게스트하우스_extraimg2.png');
insert into tbl_stay_extraimg values('39_extraimg3', '39', '제주 반집 게스트하우스_extraimg3.png');
insert into tbl_stay_extraimg values('39_extraimg4', '39', '제주 반집 게스트하우스_extraimg4.png');

insert into tbl_stay_extraimg values('40_extraimg1', '40', '서울숲 스테이_extraimg1.png');
insert into tbl_stay_extraimg values('40_extraimg2', '40', '서울숲 스테이_extraimg2.png');
insert into tbl_stay_extraimg values('40_extraimg3', '40', '서울숲 스테이_extraimg3.png');
insert into tbl_stay_extraimg values('40_extraimg4', '40', '서울숲 스테이_extraimg4.png');
insert into tbl_stay_extraimg values('40_extraimg5', '40', '서울숲 스테이_extraimg5.png');
insert into tbl_stay_extraimg values('40_extraimg6', '40', '서울숲 스테이_extraimg6.png');

select * from tbl_stay_extraimg order by to_number(fk_stay_no);
commit;
--------------------------------------------------------------------------------

select * from tbl_room order by to_number(fk_stay_no);
select * from tbl_room_extraimg order by fk_room_no;

desc tbl_room_extraimg;

insert into tbl_room_extraimg values('1-1_extraimg1', '1-1', '롯데호텔서울_room1_extraimg1.png');
insert into tbl_room_extraimg values('1-1_extraimg2', '1-1', '롯데호텔서울_room1_extraimg2.png');

insert into tbl_room_extraimg values('1-2_extraimg1', '1-2', '롯데호텔서울_room2_extraimg1.png');
insert into tbl_room_extraimg values('1-2_extraimg2', '1-2', '롯데호텔서울_room2_extraimg2.png');

insert into tbl_room_extraimg values('1-3_extraimg1', '1-3', '롯데호텔서울_room3_extraimg1.png');
insert into tbl_room_extraimg values('1-3_extraimg2', '1-3', '롯데호텔서울_room3_extraimg2.png');

insert into tbl_room_extraimg values('2-1_extraimg1', '2-1', '그랜드하얏트서울_room1_extraimg1.png');
insert into tbl_room_extraimg values('2-1_extraimg2', '2-1', '그랜드하얏트서울_room1_extraimg2.png');
insert into tbl_room_extraimg values('2-1_extraimg3', '2-1', '그랜드하얏트서울_room1_extraimg3.png');

insert into tbl_room_extraimg values('2-2_extraimg1', '2-2', '그랜드하얏트서울_room2_extraimg1.png');
insert into tbl_room_extraimg values('2-2_extraimg2', '2-2', '그랜드하얏트서울_room2_extraimg2.png');
insert into tbl_room_extraimg values('2-2_extraimg3', '2-2', '그랜드하얏트서울_room2_extraimg3.png');

insert into tbl_room_extraimg values('2-3_extraimg1', '2-3', '그랜드하얏트서울_room3_extraimg1.png');
insert into tbl_room_extraimg values('2-3_extraimg2', '2-3', '그랜드하얏트서울_room3_extraimg2.png');
insert into tbl_room_extraimg values('2-3_extraimg3', '2-3', '그랜드하얏트서울_room3_extraimg3.png');

insert into tbl_room_extraimg values('3-1_extraimg1', '3-1', '더플라자서울_room1_extraimg1.png');
insert into tbl_room_extraimg values('3-1_extraimg2', '3-1', '더플라자서울_room1_extraimg2.png');
insert into tbl_room_extraimg values('3-1_extraimg3', '3-1', '더플라자서울_room1_extraimg3.png');

insert into tbl_room_extraimg values('3-2_extraimg1', '3-2', '더플라자서울_room2_extraimg1.png');
insert into tbl_room_extraimg values('3-2_extraimg2', '3-2', '더플라자서울_room2_extraimg2.png');
insert into tbl_room_extraimg values('3-2_extraimg3', '3-2', '더플라자서울_room2_extraimg3.png');

insert into tbl_room_extraimg values('3-3_extraimg1', '3-3', '더플라자서울_room3_extraimg1.png');
insert into tbl_room_extraimg values('3-3_extraimg2', '3-3', '더플라자서울_room3_extraimg2.png');

insert into tbl_room_extraimg values('4-1_extraimg1', '4-1', '코리아나호텔_room1_extraimg1.png');
insert into tbl_room_extraimg values('4-1_extraimg2', '4-1', '코리아나호텔_room1_extraimg2.png');

insert into tbl_room_extraimg values('4-2_extraimg1', '4-2', '코리아나호텔_room2_extraimg1.png');
insert into tbl_room_extraimg values('4-2_extraimg2', '4-2', '코리아나호텔_room2_extraimg2.png');

insert into tbl_room_extraimg values('4-3_extraimg1', '4-3', '코리아나호텔_room3_extraimg1.png');
insert into tbl_room_extraimg values('4-3_extraimg2', '4-3', '코리아나호텔_room3_extraimg2.png');

insert into tbl_room_extraimg values('5-1_extraimg1', '5-1', '콘래드서울_room1_extraimg1.png');
insert into tbl_room_extraimg values('5-1_extraimg2', '5-1', '콘래드서울_room1_extraimg2.png');

insert into tbl_room_extraimg values('5-2_extraimg1', '5-2', '콘래드서울_room2_extraimg1.png');
insert into tbl_room_extraimg values('5-2_extraimg2', '5-2', '콘래드서울_room2_extraimg2.png');

insert into tbl_room_extraimg values('6-1_extraimg1', '6-1', '시그니엘서울_room1_extraimg1.png');
insert into tbl_room_extraimg values('6-1_extraimg2', '6-1', '시그니엘서울_room1_extraimg2.png');

insert into tbl_room_extraimg values('6-2_extraimg1', '6-2', '시그니엘서울_room2_extraimg1.png');
insert into tbl_room_extraimg values('6-2_extraimg2', '6-2', '시그니엘서울_room2_extraimg2.png');

insert into tbl_room_extraimg values('6-3_extraimg1', '6-3', '시그니엘서울_room3_extraimg1.png');
insert into tbl_room_extraimg values('6-3_extraimg2', '6-3', '시그니엘서울_room3_extraimg2.png');

insert into tbl_room_extraimg values('7-1_extraimg1', '7-1', '세인트존스호텔_room1_extraimg1.png');
insert into tbl_room_extraimg values('7-1_extraimg2', '7-1', '세인트존스호텔_room1_extraimg2.png');
insert into tbl_room_extraimg values('7-1_extraimg3', '7-1', '세인트존스호텔_room1_extraimg3.png');

insert into tbl_room_extraimg values('7-2_extraimg1', '7-2', '세인트존스호텔_room2_extraimg1.png');
insert into tbl_room_extraimg values('7-2_extraimg2', '7-2', '세인트존스호텔_room2_extraimg2.png');
insert into tbl_room_extraimg values('7-2_extraimg3', '7-2', '세인트존스호텔_room2_extraimg3.png');

insert into tbl_room_extraimg values('7-3_extraimg1', '7-3', '세인트존스호텔_room3_extraimg1.png');
insert into tbl_room_extraimg values('7-3_extraimg2', '7-3', '세인트존스호텔_room3_extraimg2.png');
insert into tbl_room_extraimg values('7-3_extraimg3', '7-3', '세인트존스호텔_room3_extraimg3.png');

insert into tbl_room_extraimg values('9-1_extraimg1', '9-1', '그랜드인터컨티넨탈서울파르나스_room1_extraimg1.png');
insert into tbl_room_extraimg values('9-1_extraimg2', '9-1', '그랜드인터컨티넨탈서울파르나스_room1_extraimg2.png');
insert into tbl_room_extraimg values('9-1_extraimg3', '9-1', '그랜드인터컨티넨탈서울파르나스_room1_extraimg3.png');

insert into tbl_room_extraimg values('9-2_extraimg1', '9-2', '그랜드인터컨티넨탈서울파르나스_room2_extraimg1.png');
insert into tbl_room_extraimg values('9-2_extraimg2', '9-2', '그랜드인터컨티넨탈서울파르나스_room2_extraimg2.png');
insert into tbl_room_extraimg values('9-2_extraimg3', '9-2', '그랜드인터컨티넨탈서울파르나스_room2_extraimg3.png');

insert into tbl_room_extraimg values('9-3_extraimg1', '9-3', '그랜드인터컨티넨탈서울파르나스_room3_extraimg1.png');
insert into tbl_room_extraimg values('9-3_extraimg2', '9-3', '그랜드인터컨티넨탈서울파르나스_room3_extraimg2.png');
insert into tbl_room_extraimg values('9-3_extraimg3', '9-3', '그랜드인터컨티넨탈서울파르나스_room3_extraimg3.png');

insert into tbl_room_extraimg values('10-1_extraimg1', '10-1', '신라호텔제주_room1_extraimg1.png');

insert into tbl_room_extraimg values('10-2_extraimg1', '10-2', '신라호텔제주_room2_extraimg1.png');
insert into tbl_room_extraimg values('10-2_extraimg2', '10-2', '신라호텔제주_room2_extraimg2.png');

insert into tbl_room_extraimg values('10-3_extraimg1', '10-3', '신라호텔제주_room3_extraimg1.png');
insert into tbl_room_extraimg values('10-3_extraimg2', '10-3', '신라호텔제주_room3_extraimg2.png');

--------------------------------------------------------------------------------

insert into tbl_room_extraimg values('11-1_extraimg1', '11-1', '선샤인호텔_room1_extraimg1.png');
insert into tbl_room_extraimg values('11-1_extraimg2', '11-1', '선샤인호텔_room1_extraimg2.png');

insert into tbl_room_extraimg values('11-2_extraimg1', '11-2', '선샤인호텔_room2_extraimg1.png');
insert into tbl_room_extraimg values('11-2_extraimg2', '11-2', '선샤인호텔_room2_extraimg2.png');

insert into tbl_room_extraimg values('11-3_extraimg1', '11-3', '선샤인호텔_room3_extraimg1.png');
insert into tbl_room_extraimg values('11-3_extraimg2', '11-3', '선샤인호텔_room3_extraimg2.png');

insert into tbl_room_extraimg values('12-1_extraimg1', '12-1', '수유CAFE72_room1_extraimg1.png');
insert into tbl_room_extraimg values('12-1_extraimg2', '12-1', '수유CAFE72_room1_extraimg2.png');

insert into tbl_room_extraimg values('13-1_extraimg1', '13-1', '독산3S호텔_room1_extraimg1.png');
insert into tbl_room_extraimg values('13-1_extraimg2', '13-1', '독산3S호텔_room1_extraimg2.png');
insert into tbl_room_extraimg values('13-1_extraimg3', '13-1', '독산3S호텔_room1_extraimg3.png');

insert into tbl_room_extraimg values('13-2_extraimg1', '13-2', '독산3S호텔_room2_extraimg1.png');
insert into tbl_room_extraimg values('13-2_extraimg2', '13-2', '독산3S호텔_room2_extraimg2.png');
insert into tbl_room_extraimg values('13-2_extraimg3', '13-2', '독산3S호텔_room2_extraimg3.png');

insert into tbl_room_extraimg values('13-3_extraimg1', '13-3', '독산3S호텔_room3_extraimg1.png');
insert into tbl_room_extraimg values('13-3_extraimg2', '13-3', '독산3S호텔_room3_extraimg2.png');
insert into tbl_room_extraimg values('13-3_extraimg3', '13-3', '독산3S호텔_room3_extraimg3.png');

insert into tbl_room_extraimg values('14-1_extraimg1', '14-1', '호텔월하여관_room1_extraimg1.png');
insert into tbl_room_extraimg values('14-1_extraimg2', '14-1', '호텔월하여관_room1_extraimg2.png');
insert into tbl_room_extraimg values('14-1_extraimg3', '14-1', '호텔월하여관_room1_extraimg3.png');

insert into tbl_room_extraimg values('14-2_extraimg1', '14-2', '호텔월하여관_room2_extraimg1.png');
insert into tbl_room_extraimg values('14-2_extraimg2', '14-2', '호텔월하여관_room2_extraimg2.png');
insert into tbl_room_extraimg values('14-2_extraimg3', '14-2', '호텔월하여관_room2_extraimg3.png');

insert into tbl_room_extraimg values('14-3_extraimg1', '14-3', '호텔월하여관_room3_extraimg1.png');
insert into tbl_room_extraimg values('14-3_extraimg2', '14-3', '호텔월하여관_room3_extraimg2.png');
insert into tbl_room_extraimg values('14-3_extraimg3', '14-3', '호텔월하여관_room3_extraimg3.png');

insert into tbl_room_extraimg values('15-1_extraimg1', '15-1', '신촌아늑호텔_room1_extraimg1.png');

insert into tbl_room_extraimg values('15-2_extraimg1', '15-2', '신촌아늑호텔_room2_extraimg1.png');
insert into tbl_room_extraimg values('15-2_extraimg2', '15-2', '신촌아늑호텔_room2_extraimg2.png');
insert into tbl_room_extraimg values('15-2_extraimg3', '15-2', '신촌아늑호텔_room2_extraimg3.png');

insert into tbl_room_extraimg values('15-3_extraimg1', '15-3', '신촌아늑호텔_room3_extraimg1.png');
insert into tbl_room_extraimg values('15-3_extraimg2', '15-3', '신촌아늑호텔_room3_extraimg2.png');

insert into tbl_room_extraimg values('16-1_extraimg1', '16-1', '해운대아크블루_room1_extraimg1.png');
insert into tbl_room_extraimg values('16-1_extraimg2', '16-1', '해운대아크블루_room1_extraimg2.png');

insert into tbl_room_extraimg values('16-2_extraimg1', '16-2', '해운대아크블루_room2_extraimg1.png');
insert into tbl_room_extraimg values('16-2_extraimg2', '16-2', '해운대아크블루_room2_extraimg2.png');

insert into tbl_room_extraimg values('16-3_extraimg1', '16-3', '해운대아크블루_room3_extraimg1.png');
insert into tbl_room_extraimg values('16-3_extraimg2', '16-3', '해운대아크블루_room3_extraimg2.png');

insert into tbl_room_extraimg values('17-1_extraimg1', '17-1', '광안리YAM_room1_extraimg1.png');
insert into tbl_room_extraimg values('17-1_extraimg2', '17-1', '광안리YAM_room1_extraimg2.png');

insert into tbl_room_extraimg values('17-2_extraimg1', '17-2', '광안리YAM_room2_extraimg1.png');
insert into tbl_room_extraimg values('17-2_extraimg2', '17-2', '광안리YAM_room2_extraimg2.png');

insert into tbl_room_extraimg values('17-3_extraimg1', '17-3', '광안리YAM_room3_extraimg1.png');
insert into tbl_room_extraimg values('17-3_extraimg2', '17-3', '광안리YAM_room3_extraimg2.png');

insert into tbl_room_extraimg values('18-1_extraimg1', '18-1', '호텔하운드_room1_extraimg1.png');
insert into tbl_room_extraimg values('18-1_extraimg2', '18-1', '호텔하운드_room1_extraimg2.png');

insert into tbl_room_extraimg values('18-2_extraimg1', '18-2', '호텔하운드_room2_extraimg1.png');
insert into tbl_room_extraimg values('18-2_extraimg2', '18-2', '호텔하운드_room2_extraimg2.png');

insert into tbl_room_extraimg values('18-3_extraimg1', '18-3', '호텔하운드_room3_extraimg1.png');
insert into tbl_room_extraimg values('18-3_extraimg2', '18-3', '호텔하운드_room3_extraimg2.png');

insert into tbl_room_extraimg values('19-1_extraimg1', '19-1', 'page9_room1_extraimg1.png');
insert into tbl_room_extraimg values('19-1_extraimg2', '19-1', 'page9_room1_extraimg2.png');

insert into tbl_room_extraimg values('19-2_extraimg1', '19-2', 'page9_room2_extraimg1.png');
insert into tbl_room_extraimg values('19-2_extraimg2', '19-2', 'page9_room2_extraimg2.png');
insert into tbl_room_extraimg values('19-2_extraimg3', '19-2', 'page9_room2_extraimg3.png');

insert into tbl_room_extraimg values('19-3_extraimg1', '19-3', 'page9_room3_extraimg1.png');
insert into tbl_room_extraimg values('19-3_extraimg2', '19-3', 'page9_room3_extraimg2.png');
insert into tbl_room_extraimg values('19-3_extraimg3', '19-3', 'page9_room3_extraimg3.png');

insert into tbl_room_extraimg values('20-1_extraimg1', '20-1', '속초리츠호텔_room1_extraimg1.png');
insert into tbl_room_extraimg values('20-1_extraimg2', '20-1', '속초리츠호텔_room1_extraimg2.png');
insert into tbl_room_extraimg values('20-1_extraimg3', '20-1', '속초리츠호텔_room1_extraimg3.png');

insert into tbl_room_extraimg values('20-2_extraimg1', '20-2', '속초리츠호텔_room2_extraimg1.png');
insert into tbl_room_extraimg values('20-2_extraimg2', '20-2', '속초리츠호텔_room2_extraimg2.png');
insert into tbl_room_extraimg values('20-2_extraimg3', '20-2', '속초리츠호텔_room2_extraimg3.png');

insert into tbl_room_extraimg values('20-3_extraimg1', '20-3', '속초리츠호텔_room3_extraimg1.png');
insert into tbl_room_extraimg values('20-3_extraimg2', '20-3', '속초리츠호텔_room3_extraimg2.png');
insert into tbl_room_extraimg values('20-3_extraimg3', '20-3', '속초리츠호텔_room3_extraimg3.png');

--------------------------------------------------------------------------------

insert into tbl_room_extraimg values('21-1_extraimg1', '21-1', '강릉 블루스테이_room1_extraimg1.png');
insert into tbl_room_extraimg values('21-1_extraimg2', '21-1', '강릉 블루스테이_room1_extraimg2.png');
insert into tbl_room_extraimg values('21-1_extraimg3', '21-1', '강릉 블루스테이_room1_extraimg3.png');

insert into tbl_room_extraimg values('21-2_extraimg1', '21-2', '강릉 블루스테이_room2_extraimg1.png');
insert into tbl_room_extraimg values('21-2_extraimg2', '21-2', '강릉 블루스테이_room2_extraimg2.png');
insert into tbl_room_extraimg values('21-2_extraimg3', '21-2', '강릉 블루스테이_room2_extraimg3.png');

insert into tbl_room_extraimg values('21-3_extraimg1', '21-3', '강릉 블루스테이_room3_extraimg1.png');
insert into tbl_room_extraimg values('21-3_extraimg2', '21-3', '강릉 블루스테이_room3_extraimg2.png');
insert into tbl_room_extraimg values('21-3_extraimg3', '21-3', '강릉 블루스테이_room3_extraimg3.png');

insert into tbl_room_extraimg values('22-1_extraimg1', '22-1', '강릉 풀빌라 케이_room1_extraimg1.png');
insert into tbl_room_extraimg values('22-1_extraimg2', '22-1', '강릉 풀빌라 케이_room1_extraimg2.png');
insert into tbl_room_extraimg values('22-1_extraimg3', '22-1', '강릉 풀빌라 케이_room1_extraimg3.png');

insert into tbl_room_extraimg values('22-2_extraimg1', '22-2', '강릉 풀빌라 케이_room2_extraimg1.png');
insert into tbl_room_extraimg values('22-2_extraimg2', '22-2', '강릉 풀빌라 케이_room2_extraimg2.png');
insert into tbl_room_extraimg values('22-2_extraimg3', '22-2', '강릉 풀빌라 케이_room2_extraimg3.png');

insert into tbl_room_extraimg values('22-3_extraimg1', '22-3', '강릉 풀빌라 케이_room3_extraimg1.png');
insert into tbl_room_extraimg values('22-3_extraimg2', '22-3', '강릉 풀빌라 케이_room3_extraimg2.png');
insert into tbl_room_extraimg values('22-3_extraimg3', '22-3', '강릉 풀빌라 케이_room3_extraimg3.png');

insert into tbl_room_extraimg values('23-1_extraimg1', '23-1', '강릉 탑 스파 펜션_room1_extraimg1.png');
insert into tbl_room_extraimg values('23-1_extraimg2', '23-1', '강릉 탑 스파 펜션_room1_extraimg2.png');
insert into tbl_room_extraimg values('23-1_extraimg3', '23-1', '강릉 탑 스파 펜션_room1_extraimg3.png');

insert into tbl_room_extraimg values('23-2_extraimg1', '23-2', '강릉 탑 스파 펜션_room2_extraimg1.png');
insert into tbl_room_extraimg values('23-2_extraimg2', '23-2', '강릉 탑 스파 펜션_room2_extraimg2.png');
insert into tbl_room_extraimg values('23-2_extraimg3', '23-2', '강릉 탑 스파 펜션_room2_extraimg3.png');

insert into tbl_room_extraimg values('23-3_extraimg1', '23-3', '강릉 탑 스파 펜션_room3_extraimg1.png');
insert into tbl_room_extraimg values('23-3_extraimg2', '23-3', '강릉 탑 스파 펜션_room3_extraimg2.png');
insert into tbl_room_extraimg values('23-3_extraimg3', '23-3', '강릉 탑 스파 펜션_room3_extraimg3.png');

insert into tbl_room_extraimg values('24-1_extraimg1', '24-1', '니나랑 스파 펜션_room1_extraimg1.png');
insert into tbl_room_extraimg values('24-1_extraimg2', '24-1', '니나랑 스파 펜션_room1_extraimg2.png');
insert into tbl_room_extraimg values('24-1_extraimg3', '24-1', '니나랑 스파 펜션_room1_extraimg3.png');

insert into tbl_room_extraimg values('24-2_extraimg1', '24-2', '니나랑 스파 펜션_room2_extraimg1.png');
insert into tbl_room_extraimg values('24-2_extraimg2', '24-2', '니나랑 스파 펜션_room2_extraimg2.png');
insert into tbl_room_extraimg values('24-2_extraimg3', '24-2', '니나랑 스파 펜션_room2_extraimg3.png');

insert into tbl_room_extraimg values('24-3_extraimg1', '24-3', '니나랑 스파 펜션_room3_extraimg1.png');
insert into tbl_room_extraimg values('24-3_extraimg2', '24-3', '니나랑 스파 펜션_room3_extraimg2.png');
insert into tbl_room_extraimg values('24-3_extraimg3', '24-3', '니나랑 스파 펜션_room3_extraimg3.png');
insert into tbl_room_extraimg values('24-3_extraimg4', '24-3', '니나랑 스파 펜션_room3_extraimg4.png');

insert into tbl_room_extraimg values('25-1_extraimg1', '25-1', '산토리니 펜션_room1_extraimg1.png');
insert into tbl_room_extraimg values('25-1_extraimg2', '25-1', '산토리니 펜션_room1_extraimg2.png');
insert into tbl_room_extraimg values('25-1_extraimg3', '25-1', '산토리니 펜션_room1_extraimg3.png');
insert into tbl_room_extraimg values('25-1_extraimg4', '25-1', '산토리니 펜션_room1_extraimg4.png');
insert into tbl_room_extraimg values('25-1_extraimg5', '25-1', '산토리니 펜션_room1_extraimg5.png');

insert into tbl_room_extraimg values('26-1_extraimg1', '26-1', '애월 한옥 펜션_room1_extraimg1.png');
insert into tbl_room_extraimg values('26-1_extraimg2', '26-1', '애월 한옥 펜션_room1_extraimg2.png');
insert into tbl_room_extraimg values('26-1_extraimg3', '26-1', '애월 한옥 펜션_room1_extraimg3.png');
insert into tbl_room_extraimg values('26-1_extraimg4', '26-1', '애월 한옥 펜션_room1_extraimg4.png');

insert into tbl_room_extraimg values('26-2_extraimg1', '26-2', '애월 한옥 펜션_room2_extraimg1.png');
insert into tbl_room_extraimg values('26-2_extraimg2', '26-2', '애월 한옥 펜션_room2_extraimg2.png');
insert into tbl_room_extraimg values('26-2_extraimg3', '26-2', '애월 한옥 펜션_room2_extraimg3.png');
insert into tbl_room_extraimg values('26-2_extraimg4', '26-2', '애월 한옥 펜션_room2_extraimg4.png');

insert into tbl_room_extraimg values('27-1_extraimg1', '27-1', '힐링 포레스트 펜션_room1_extraimg1.png');
insert into tbl_room_extraimg values('27-1_extraimg2', '27-1', '힐링 포레스트 펜션_room1_extraimg2.png');
insert into tbl_room_extraimg values('27-1_extraimg3', '27-1', '힐링 포레스트 펜션_room1_extraimg3.png');
insert into tbl_room_extraimg values('27-1_extraimg4', '27-1', '힐링 포레스트 펜션_room1_extraimg4.png');

insert into tbl_room_extraimg values('27-2_extraimg1', '27-2', '힐링 포레스트 펜션_room2_extraimg1.png');
insert into tbl_room_extraimg values('27-2_extraimg2', '27-2', '힐링 포레스트 펜션_room2_extraimg2.png');
insert into tbl_room_extraimg values('27-2_extraimg3', '27-2', '힐링 포레스트 펜션_room2_extraimg3.png');

insert into tbl_room_extraimg values('27-3_extraimg1', '27-3', '힐링 포레스트 펜션_room3_extraimg1.png');
insert into tbl_room_extraimg values('27-3_extraimg2', '27-3', '힐링 포레스트 펜션_room3_extraimg2.png');
insert into tbl_room_extraimg values('27-3_extraimg3', '27-3', '힐링 포레스트 펜션_room3_extraimg3.png');

insert into tbl_room_extraimg values('28-1_extraimg1', '28-1', '해뜨는 숲속 펜션_room1_extraimg1.png');
insert into tbl_room_extraimg values('28-1_extraimg2', '28-1', '해뜨는 숲속 펜션_room1_extraimg2.png');
insert into tbl_room_extraimg values('28-1_extraimg3', '28-1', '해뜨는 숲속 펜션_room1_extraimg3.png');

insert into tbl_room_extraimg values('28-2_extraimg1', '28-2', '해뜨는 숲속 펜션_room2_extraimg1.png');
insert into tbl_room_extraimg values('28-2_extraimg2', '28-2', '해뜨는 숲속 펜션_room2_extraimg2.png');
insert into tbl_room_extraimg values('28-2_extraimg3', '28-2', '해뜨는 숲속 펜션_room2_extraimg3.png');

insert into tbl_room_extraimg values('28-3_extraimg1', '28-3', '해뜨는 숲속 펜션_room3_extraimg1.png');
insert into tbl_room_extraimg values('28-3_extraimg2', '28-3', '해뜨는 숲속 펜션_room3_extraimg2.png');
insert into tbl_room_extraimg values('28-3_extraimg3', '28-3', '해뜨는 숲속 펜션_room3_extraimg3.png');

insert into tbl_room_extraimg values('29-1_extraimg1', '29-1', '스테이 디앤디 펜션_room1_extraimg1.png');
insert into tbl_room_extraimg values('29-1_extraimg2', '29-1', '스테이 디앤디 펜션_room1_extraimg2.png');
insert into tbl_room_extraimg values('29-1_extraimg3', '29-1', '스테이 디앤디 펜션_room1_extraimg3.png');

insert into tbl_room_extraimg values('29-2_extraimg1', '29-2', '스테이 디앤디 펜션_room2_extraimg1.png');
insert into tbl_room_extraimg values('29-2_extraimg2', '29-2', '스테이 디앤디 펜션_room2_extraimg2.png');
insert into tbl_room_extraimg values('29-2_extraimg3', '29-2', '스테이 디앤디 펜션_room2_extraimg3.png');

insert into tbl_room_extraimg values('29-3_extraimg1', '29-3', '스테이 디앤디 펜션_room3_extraimg1.png');
insert into tbl_room_extraimg values('29-3_extraimg2', '29-3', '스테이 디앤디 펜션_room3_extraimg2.png');
insert into tbl_room_extraimg values('29-3_extraimg3', '29-3', '스테이 디앤디 펜션_room3_extraimg3.png');
insert into tbl_room_extraimg values('29-3_extraimg4', '29-3', '스테이 디앤디 펜션_room3_extraimg4.png');

insert into tbl_room_extraimg values('30-1_extraimg1', '30-1', '그바다에 머물다 펜션_room1_extraimg1.png');
insert into tbl_room_extraimg values('30-1_extraimg2', '30-1', '그바다에 머물다 펜션_room1_extraimg2.png');
insert into tbl_room_extraimg values('30-1_extraimg3', '30-1', '그바다에 머물다 펜션_room1_extraimg3.png');

insert into tbl_room_extraimg values('30-2_extraimg1', '30-2', '그바다에 머물다 펜션_room2_extraimg1.png');
insert into tbl_room_extraimg values('30-2_extraimg2', '30-2', '그바다에 머물다 펜션_room2_extraimg2.png');
insert into tbl_room_extraimg values('30-2_extraimg3', '30-2', '그바다에 머물다 펜션_room2_extraimg3.png');

--------------------------------------------------------------------------------

insert into tbl_room_extraimg values('31-1_extraimg1', '31-1', 'Zzzip 게스트하우스_room1_extraimg1.png');
insert into tbl_room_extraimg values('31-1_extraimg2', '31-1', 'Zzzip 게스트하우스_room1_extraimg2.png');
insert into tbl_room_extraimg values('31-1_extraimg3', '31-1', 'Zzzip 게스트하우스_room1_extraimg3.png');

insert into tbl_room_extraimg values('31-2_extraimg1', '31-2', 'Zzzip 게스트하우스_room2_extraimg1.png');

insert into tbl_room_extraimg values('33-1_extraimg1', '33-1', '펀웨이브게스트하우스_room1_extraimg1.png');

insert into tbl_room_extraimg values('33-2_extraimg1', '33-2', '펀웨이브게스트하우스_room2_extraimg1.png');

insert into tbl_room_extraimg values('33-3_extraimg1', '33-3', '펀웨이브게스트하우스_room3_extraimg1.png');
insert into tbl_room_extraimg values('33-3_extraimg2', '33-3', '펀웨이브게스트하우스_room3_extraimg2.png');

insert into tbl_room_extraimg values('34-1_extraimg1', '34-1', '보령 어썸 게스트하우스_room1_extraimg1.png');

insert into tbl_room_extraimg values('34-2_extraimg1', '34-2', '보령 어썸 게스트하우스_room2_extraimg1.png');

insert into tbl_room_extraimg values('35-1_extraimg1', '35-1', '이태원 서울큐브 게스트하우스_room1_extraimg1.png');
insert into tbl_room_extraimg values('35-1_extraimg2', '35-1', '이태원 서울큐브 게스트하우스_room1_extraimg2.png');

insert into tbl_room_extraimg values('35-2_extraimg1', '35-2', '이태원 서울큐브 게스트하우스_room2_extraimg1.png');
insert into tbl_room_extraimg values('35-2_extraimg2', '35-2', '이태원 서울큐브 게스트하우스_room2_extraimg2.png');

insert into tbl_room_extraimg values('35-3_extraimg1', '35-3', '이태원 서울큐브 게스트하우스_room3_extraimg1.png');

insert into tbl_room_extraimg values('36-1_extraimg1', '36-1', '전주 유정 게스트하우스_room1_extraimg1.png');
insert into tbl_room_extraimg values('36-1_extraimg2', '36-1', '전주 유정 게스트하우스_room1_extraimg2.png');
insert into tbl_room_extraimg values('36-1_extraimg3', '36-1', '전주 유정 게스트하우스_room1_extraimg3.png');

insert into tbl_room_extraimg values('36-2_extraimg1', '36-2', '전주 유정 게스트하우스_room2_extraimg1.png');
insert into tbl_room_extraimg values('36-2_extraimg2', '36-2', '전주 유정 게스트하우스_room2_extraimg2.png');
insert into tbl_room_extraimg values('36-2_extraimg3', '36-2', '전주 유정 게스트하우스_room2_extraimg3.png');
insert into tbl_room_extraimg values('36-2_extraimg4', '36-2', '전주 유정 게스트하우스_room2_extraimg4.png');

insert into tbl_room_extraimg values('36-3_extraimg1', '36-3', '전주 유정 게스트하우스_room3_extraimg1.png');
insert into tbl_room_extraimg values('36-3_extraimg2', '36-3', '전주 유정 게스트하우스_room3_extraimg2.png');
insert into tbl_room_extraimg values('36-3_extraimg3', '36-3', '전주 유정 게스트하우스_room3_extraimg3.png');
insert into tbl_room_extraimg values('36-3_extraimg4', '36-3', '전주 유정 게스트하우스_room3_extraimg4.png');

insert into tbl_room_extraimg values('37-1_extraimg1', '37-1', '제주 게토 게스트하우스파티_room1_extraimg1.png');
insert into tbl_room_extraimg values('37-1_extraimg2', '37-1', '제주 게토 게스트하우스파티_room1_extraimg2.png');
insert into tbl_room_extraimg values('37-1_extraimg3', '37-1', '제주 게토 게스트하우스파티_room1_extraimg3.png');

insert into tbl_room_extraimg values('37-2_extraimg1', '37-2', '제주 게토 게스트하우스파티_room2_extraimg1.png');
insert into tbl_room_extraimg values('37-2_extraimg2', '37-2', '제주 게토 게스트하우스파티_room2_extraimg2.png');
insert into tbl_room_extraimg values('37-2_extraimg3', '37-2', '제주 게토 게스트하우스파티_room2_extraimg3.png');
insert into tbl_room_extraimg values('37-2_extraimg4', '37-2', '제주 게토 게스트하우스파티_room2_extraimg4.png');

insert into tbl_room_extraimg values('38-1_extraimg1', '38-1', '속초 하루 게스트하우스_room1_extraimg1.png');
insert into tbl_room_extraimg values('38-1_extraimg2', '38-1', '속초 하루 게스트하우스_room1_extraimg2.png');
insert into tbl_room_extraimg values('38-1_extraimg3', '38-1', '속초 하루 게스트하우스_room1_extraimg3.png');
insert into tbl_room_extraimg values('38-1_extraimg4', '38-1', '속초 하루 게스트하우스_room1_extraimg4.png');
insert into tbl_room_extraimg values('38-1_extraimg5', '38-1', '속초 하루 게스트하우스_room1_extraimg5.png');

insert into tbl_room_extraimg values('38-2_extraimg1', '38-2', '속초 하루 게스트하우스_room2_extraimg1.png');
insert into tbl_room_extraimg values('38-2_extraimg2', '38-2', '속초 하루 게스트하우스_room2_extraimg2.png');
insert into tbl_room_extraimg values('38-2_extraimg3', '38-2', '속초 하루 게스트하우스_room2_extraimg3.png');
insert into tbl_room_extraimg values('38-2_extraimg4', '38-2', '속초 하루 게스트하우스_room2_extraimg4.png');

insert into tbl_room_extraimg values('38-3_extraimg1', '38-3', '속초 하루 게스트하우스_room3_extraimg1.png');
insert into tbl_room_extraimg values('38-3_extraimg2', '38-3', '속초 하루 게스트하우스_room3_extraimg2.png');
insert into tbl_room_extraimg values('38-3_extraimg3', '38-3', '속초 하루 게스트하우스_room3_extraimg3.png');
insert into tbl_room_extraimg values('38-3_extraimg4', '38-3', '속초 하루 게스트하우스_room3_extraimg4.png');
insert into tbl_room_extraimg values('38-3_extraimg5', '38-3', '속초 하루 게스트하우스_room3_extraimg5.png');

insert into tbl_room_extraimg values('39-1_extraimg1', '39-1', '제주 반집 게스트하우스_room1_extraimg1.png');
insert into tbl_room_extraimg values('39-1_extraimg2', '39-1', '제주 반집 게스트하우스_room1_extraimg2.png');
insert into tbl_room_extraimg values('39-1_extraimg3', '39-1', '제주 반집 게스트하우스_room1_extraimg3.png');
insert into tbl_room_extraimg values('39-1_extraimg4', '39-1', '제주 반집 게스트하우스_room1_extraimg4.png');

insert into tbl_room_extraimg values('39-2_extraimg1', '39-2', '제주 반집 게스트하우스_room2_extraimg1.png');
insert into tbl_room_extraimg values('39-2_extraimg2', '39-2', '제주 반집 게스트하우스_room2_extraimg2.png');
insert into tbl_room_extraimg values('39-2_extraimg3', '39-2', '제주 반집 게스트하우스_room2_extraimg3.png');

insert into tbl_room_extraimg values('40-1_extraimg1', '40-1', '서울숲 스테이_room1_extraimg1.png');
insert into tbl_room_extraimg values('40-1_extraimg2', '40-1', '서울숲 스테이_room1_extraimg2.png');
insert into tbl_room_extraimg values('40-1_extraimg3', '40-1', '서울숲 스테이_room1_extraimg3.png');

insert into tbl_room_extraimg values('40-2_extraimg1', '40-2', '서울숲 스테이_room2_extraimg1.png');
insert into tbl_room_extraimg values('40-2_extraimg2', '40-2', '서울숲 스테이_room2_extraimg2.png');
insert into tbl_room_extraimg values('40-2_extraimg3', '40-2', '서울숲 스테이_room2_extraimg3.png');

insert into tbl_room_extraimg values('40-3_extraimg1', '40-3', '서울숲 스테이_room3_extraimg1.png');
insert into tbl_room_extraimg values('40-3_extraimg2', '40-3', '서울숲 스테이_room3_extraimg2.png');

--------------------------------------------------------------------------------

select * from tbl_stay;

select * from tab;
desc tbl_stay_category;

select      stay_category_no, stay_category_name
from		tbl_stay_category
order by	stay_category_no asc;

SELECT * FROM
(
    SELECT ROWNUM AS rn, s.* FROM
    ( 
        SELECT * FROM tbl_stay ORDER BY stay_no
    ) s  WHERE ROWNUM <= 6 and fk_stay_category_no='B'
) WHERE rn >= 1 ;

SELECT      room_no, fk_stay_no, room_grade, room_thumbnail, price_per_night, room_info
FROM        tbl_room
WHERE       fk_stay_no = 37
ORDER BY    TO_NUMBER(REGEXP_SUBSTR(room_no, '^[^-]+')),
            TO_NUMBER(REGEXP_SUBSTR(room_no, '[^-]+$', 1, 1));


select user_id, access_level from tbl_user;

commit;
update tbl_user
set access_level = 1
where user_id = 'admin';

desc tbl_room_extraimg;
desc tbl_stay_extraimg;

select * from tbl_room_extraimg;

SELECT *
FROM tbl_room_extraimg
ORDER BY
  TO_NUMBER(REGEXP_SUBSTR(room_extraimg_no, '^\d+')),                             -- 앞번호
  TO_NUMBER(REGEXP_SUBSTR(room_extraimg_no, '-(\d+)', 1, 1, NULL, 1)),           -- 중간번호
  TO_NUMBER(REGEXP_SUBSTR(room_extraimg_no, 'extraimg(\d+)', 1, 1, NULL, 1));    -- 끝번호
  
  
desc tbl_room;

desc tbl_review;

select  fk_stay_no, room_no, review_no, reserv_score, review_contents, review_writedate, fk_reserv_no
from
(
    select  *
    from    
    (   select  *
        from    tbl_reservation A
        join    tbl_review  B
        on      A.reserv_no = B.fk_reserv_no
        where   review_no = 1
    )
)   C
join    tbl_room D
on      C.fk_room_no = D.room_no;
