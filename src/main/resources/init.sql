create table t_device(deviceId varchar(64) not null primary key,
    phone varchar(11),
    phoneType varchar(16) not null,
    phoneVersion varchar(16) not null,
    appName varchar(16) not null,
    appVersion varchar(16) not null,
    userId varchar(64),
    userName varchar(64),
    createTime TIME );