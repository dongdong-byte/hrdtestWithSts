show databases ;

create database hirdTestFinal;

use hirdTestFinal;

show  tables;

create table employees(
                          EmpNo BIGINT primary key auto_increment comment '사번',
                          EmpName varchar(30) not null comment '사원명',
                          Dept varchar(20) not null comment '부서명',
                          HireDate date not null comment '입사일',
                          Salary int , check ( Salary >=2000000)
);

select  *from employees;

alter table employees
add  unique (EmpNo),
    modify column Salary int not null  check ( Salary>=2000000 );

alter table employees
add constraint  uk_emp_name unique (EmpName);

insert into employees(EmpName, Dept, HireDate, Salary) VALUES
('','','','');

select * from employees where EmpNo='';

select EmpNo,EmpName,Salary from employees where Salary >='';

update employees
set EmpNo =''
  ,EmpName =''
  ,Dept =''
  ,Salary=''
Where EmpNo ='';

delete from employees where EmpNo='';