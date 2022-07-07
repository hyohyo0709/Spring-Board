##############################################
게시판 만들기
##############################################

select*from board
order by num;


1. 테이블생성 
create table board(
   	num number,
   	writer varchar2(20),
 	email varchar2(30),
	subject varchar2(50),
	reg_date date,
	readcount number default 0, 
	ref number, 
	re_step number, 
	re_level number, 
	content varchar2(100),
	ip varchar2(20),
    upload varchar2(300)
);

create table members_hj(
num number,
mem_id varchar2(20),
mem_pass varchar2(30),
mem_email varchar2(50),
mem_name varchar(100),
mem_citizen_id_1 number,
mem_citizen_id_2 number,
mem_hobby varchar2(50),
mem_introduce varchar2(100)
);

create sequence members_hj_num_seq
start with 1
increment by 1
nocache
nocycle;

select*from members_hj;


create sequence board_num_seq
start with 1 
increment by 1
nocache
nocycle;

insert into board 
values(board_num_seq.nextval, '홍길동','young@aaaa.com','제목1',sysdate,0,board_num_seq.nextval,
0,0,'내용 테스트.......','127.0.0.1','sample.txt');



select num, readcount 
from board
where num=2;

alter table board modify (writer varchar2(100)) ;



select num, subject, ref, re_step, re_level
from board
order by ref desc, re_step asc

1 ~  5
6 ~ 10

select b.* 
from (select rownum as rm, a.*
	  from (select *
	 	    from board
            order by ref desc, re_step asc) a)b
where b.rm>=? and b.rm<=?           



delete from board
where num=21

drop table board;


select b.* from 
(select rownum as rm, a.* from(
  select num, ref, re_step,re_level from board
  order by ref desc, re_step asc) a)b
where b.rm >=1  and b.rm<=5

delete from board where num=23;

WebServlet("/board/*")


webContent             /boardview/list.jsp
boardview
  list.jsp

select count(*) from board where subject like '%w%'
select count(*) from board where writer like '%3%'




select num, ref, re_step,re_level 
from board 
order by num

from -> where -> group by ->having ->select -> order by



select b.*
 from(select a.*, rownum as rm
 from(select * from board order by ref desc ,re_step asc)a)b
 where b.rm>=5 and b.rm<=10;





























