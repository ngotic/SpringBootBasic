create table tblBoard (
   seq     number      primary key,
   subject varchar2(1000) not null,
   content varchar2(2000) not null,
   regdate           date not null,
   id      varchar2(30) not null
);

create sequence seq;

desc tblBoard;

insert into tblBoard values(seq.nextVal, '제목입니다.', '내용입니다.', sysdate, '멍멍이');
insert into tblBoard values(seq.nextVal, '제목입니다.2', '내용입니다.2', sysdate, '야옹이');
insert into tblBoard values(seq.nextVal, '제목입니다.3', '내용입니다.3', sysdate, '어흥이');

select * from tblBoard;