create database diasystem;

/*
用户表,  用户名,密码,显示名,邮箱,密码验证问题索引,密码验证问题答案
id 主键
*/
create table `user`(
	`id` int(11) not null comment '主键',
	`username` varchar(25) collate utf8_bin not null comment '用户名',
	`password` varchar(35) collate utf8_bin not null comment '密码',
	`disname` varchar(25) collate utf8_bin not null comment '显示名',
	`email` varchar(55) collate utf8_bin not null comment '邮箱',
	`ansindex` enum('1','2','3','4','5') collate utf8_bin not null comment '密码验证问题索引',
	`answer` text collate utf8_bin not null comment '密码验证问题答案' 
)engine=innodb default charset=utf8 collate=utf8_bin comment='用户表'; 

alter table `user` add primary key(`id`); #设置主键

alter table `user` modify `id` int(11) not null auto_increment comment '主键'; #设置自增

alter table `user` auto_increment=1;   #设置自增从1开始

/*
日记表 天气,心情,标题,内容,日期
num 主键(编号)
*/
create table `diary`(
	`num` int(11) not null comment '编号(主键)',
	`weather` enum('晴','阴天','多云','雨','雾','雪') collate utf8_bin not null comment '天气',
	`mood` enum('高兴','郁闷','兴奋','悲伤','恐惧','欣喜') collate utf8_bin not null comment '心情',
	`title` varchar(15) collate utf8_bin not null comment '标题',
	`content` text collate utf8_bin not null comment '内容',
	`mydate` date collate utf8_bin not null comment '日期'
)engine=innodb default charset=utf8 collate=utf8_bin comment='日记表';

alter table `diary` add primary key(`num`); #设置主键
alter table `diary` modify `num` int(11) not null auto_increment comment '编号(主键)'; # 设置自增
alter table `diary` auto_increment=1000;   #设置自增起点

/*
联系表:用户与日记之间的联系
用户id,日记编号,主键
*/
create table `contact`(
	`key` int(11) not null comment '主键',
	`id` int(11) not null comment '用户id',
	`num` int(11) not null comment '日记编号'
)engine=innodb default charset=utf8 collate=utf8_bin comment='联系表';
alter table `contact` add primary key(`key`);
alter table `contact` modify `key` int(11) not null auto_increment comment '编号(主键)';
alter table `contact` auto_increment=1000;

#设置外键
ALTER TABLE `contact` add constraint fk_id foreign key (`id`) references `user`(`id`);
ALTER TABLE `contact` add constraint fk_num foreign key (`num`) references `diary`(`num`);

    /*----------下面是测试---------*/
insert into `user` (`username`,`password`,`disname`,`email`,`ansindex`,`answer`)
 values ('我是张三','qwert;123','我是显示名','1214141513@qq.com','1','卧槽');  #插入user

insert into `diary` (`weather`,`mood`,`title`,`content`,`mydate`) 
 values ('晴','高兴','我是标题','内容....','1997-12-10');  #插入日记
 
insert into `contact` (`id`,`num`) values (1,1001);       #插入联系到联系表

select * from `user` where `username`='1' and `password`='1';   #查询该用户是否存在
select * from `user` where `username`='1' and `ansindex`='2' and `answer`='1'; #判断找回密码时选择和输入的问题答案是否正确