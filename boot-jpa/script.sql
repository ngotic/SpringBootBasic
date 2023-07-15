create table Item (
    name varchar2(30) primary key,
    price number not null,
    color varchar2(30) not null,
    owner varchar2(30) null,
    orderdate date default sysdate not null
);

insert into Item (name, price, color, owner, orderdate) values ('키보드', 100000, 'black', '홍길동', sysdate - 1);
insert into Item (name, price, color, owner, orderdate) values ('무접점 고급 키보드', 120000, 'white', null, sysdate - 1.5);
insert into Item (name, price, color, owner, orderdate) values ('기계식 키보드', 150000, 'black', '홍길동', sysdate - 2);
insert into Item (name, price, color, owner, orderdate) values ('멤브레인 키보드', 80000, 'white', '홍길동', sysdate - 2.5);
insert into Item (name, price, color, owner, orderdate) values ('마우스', 50000, 'silver', '홍길동', sysdate - 3);
insert into Item (name, price, color, owner, orderdate) values ('버티컬 마우스', 90000, 'silver', '아무개', sysdate - 3.5);
insert into Item (name, price, color, owner, orderdate) values ('게이밍 마우스', 120000, 'black', '아무개', sysdate - 4);
insert into Item (name, price, color, owner, orderdate) values ('고급 볼 마우스', 95000, 'yellow', '아무개', sysdate - 3);
insert into Item (name, price, color, owner, orderdate) values ('노트북', 1100000, 'white', '아무개', sysdate - 4.5);
insert into Item (name, price, color, owner, orderdate) values ('노트북 가방', 120000, 'blue', null, sysdate - 5);
insert into Item (name, price, color, owner, orderdate) values ('노트북 받침대', 95000, 'yellow', '하하하', sysdate - 5.5);
insert into Item (name, price, color, owner, orderdate) values ('노트북 파우치', 95000, 'yellow', '하하하', sysdate - 5);


select * from Item;

commit;