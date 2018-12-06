create table author (
  id            serial primary key not null,
  first_name    text,
  last_name     text,
  date_of_burth timestamp
);

create table person (
  id            serial primary key not null,
  first_name    text,
  last_name     text,
  date_of_burth timestamp
);

create table book (
  id        serial primary key  not null,
  title     text,
  author_id int references author (id)
);
