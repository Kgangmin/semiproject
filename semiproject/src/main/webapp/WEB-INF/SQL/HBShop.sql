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

insert into tbl_room values('11-1', '11', 'standard', '신라호텔제주_room1.png', 405000, '기준2인/최대2인 더블베드1개 조식 가든뷰');
insert into tbl_room values('11-2', '11', 'sweet', '신라호텔제주_room2.png', 1899496, '기준2인/최대2인 더블베드1개 조식 가든뷰 발코니/테라스');
insert into tbl_room values('11-3', '11', 'deluxe', '신라호텔제주_room3.png', 5420800, '기준4인/최대4인 퀸베드1개 조식 오션뷰 발코니/테라스 전용수영장');

insert into tbl_room values('20-1', '20', 'standard', '속초리츠호텔_room1.png', 45000, '기준2인/최대2인 더블베드1개 조식 시티뷰');
insert into tbl_room values('20-2', '20', 'sweet', '속초리츠호텔_room2.png', 65000, '기준2인/최대3인 킹베드1개 조식 시티뷰 스마트TV');
insert into tbl_room values('20-3', '20', 'deluxe', '속초리츠호텔_room3.png', 80000, '기준2인/최대3인 퀸베드1개 조식 오션뷰 스마트TV');




