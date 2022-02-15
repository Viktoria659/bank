TRUNCATE client, usr, account, role cascade;

insert INTO role values (1, 'ROLE_USER');
insert INTO role values (2, 'ROLE_ADMIN');

insert INTO client values (nextval('client_id_seq'), null, 'firstname', null, 'surname');
insert INTO usr values (currval('client_id_seq'), true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'username', 1);
insert INTO account values (nextval('account_id_seq'), 100, 0, currval('client_id_seq'));

insert INTO client values (nextval('client_id_seq'), null, 'firstname', null, 'surname');
insert INTO usr values (currval('client_id_seq'), true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'username-2', 1);

insert INTO client values (10, null, 'ЖКХ', null, 'surname');
insert INTO usr values (10, true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'communal_services', 1);
insert INTO account values (2, 100, 0, 10);

insert INTO client values (11, null, 'Телефон', null, 'surname');
insert INTO usr values (11, true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'telephone', 1);
insert INTO account values (3, 100, 0, 11);

insert INTO client values (12, null, 'Налоги', null, 'surname');
insert INTO usr values (12, true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'tax', 1);
insert INTO account values (4, 100, 0, 12);