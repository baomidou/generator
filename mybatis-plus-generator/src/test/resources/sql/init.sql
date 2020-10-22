create table `t_simple`
(
    id          int IDENTITY primary key comment 'id',
    name        varchar(50),
    age         int,
    flag        int,
    version     bigint,
    create_time datetime,
    update_time datetime,
    primary key (id)
);
