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

select * from tbl_stay_category;

select * from tbl_stay;