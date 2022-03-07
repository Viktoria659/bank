TRUNCATE client, usr, account, role cascade;

insert INTO client values (nextval('client_id_seq'), null, 'firstname', null, 'surname');
insert INTO usr values (currval('client_id_seq'), true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'username', 1);
insert INTO account values (nextval('account_id_seq'), 100, 0, currval('client_id_seq'));

insert INTO client values (nextval('client_id_seq'), null, 'firstname', null, 'surname');
insert INTO usr values (currval('client_id_seq'), true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'username-2', 1);

insert INTO client values (nextval('client_id_seq'), null, 'ЖКХ', null, 'surname');
insert INTO usr values (currval('client_id_seq'), true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'communal_services', 1);
insert INTO account values (nextval('account_id_seq'), 100, 0, currval('client_id_seq'));

insert INTO client values (nextval('client_id_seq'), null, 'Телефон', null, 'surname');
insert INTO usr values (currval('client_id_seq'), true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'telephone', 1);
insert INTO account values (nextval('account_id_seq'), 100, 0, currval('client_id_seq'));

insert INTO client values (nextval('client_id_seq'), null, 'Налоги', null, 'surname');
insert INTO usr values (currval('client_id_seq'), true, '$2a$10$fTIAlQfT5zV83MSX3we//uhiIX7rHjNO.wDzB6vONWwQLWMEkaMXG', 'tax', 1);
insert INTO account values (nextval('account_id_seq'), 100, 0, currval('client_id_seq'));