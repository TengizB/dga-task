INSERT INTO public.app_user(id, email, first_name, last_name, password, role)
select '1971a5ee-88d6-4e24-8c3c-ced6fe66365b','tbregvadze@mail.com','Tengiz', 'Bregvadze', '$2a$10$fDKfBrdfbUt7PkFU.fKH2usQpNnTcquG4l9YQ1TheOkESCCrB1ilG','ADMIN'
where NOT EXISTS (SELECT id from app_user where id = '1971a5ee-88d6-4e24-8c3c-ced6fe66365b');


INSERT INTO public.app_user(id, email, first_name, last_name, password, role)
SELECT '1a31e54b-3e45-4c13-9b22-4589f19a4a93', 'john.doe@mail.com', 'John', 'Doe', '$2a$10$fDKfBrdfbUt7PkFU.fKH2usQpNnTcquG4l9YQ1TheOkESCCrB1ilG', 'USER'
WHERE NOT EXISTS (SELECT id FROM app_user WHERE id = '1a31e54b-3e45-4c13-9b22-4589f19a4a93');

INSERT INTO public.app_user(id, email, first_name, last_name, password, role)
SELECT'2e42b36d-2f55-4a98-813a-7f9bd0ec07d1', 'jane.smith@mail.com', 'Jane', 'Smith', '$2a$10$fDKfBrdfbUt7PkFU.fKH2usQpNnTcquG4l9YQ1TheOkESCCrB1ilG', 'USER'
WHERE NOT EXISTS (SELECT id FROM app_user WHERE id = '2e42b36d-2f55-4a98-813a-7f9bd0ec07d1');

INSERT INTO public.app_user(id, email, first_name, last_name, password, role)
SELECT '43adaad2-7c88-4237-af57-ba56376ef26c', '1john.doe@mail.com', '1John', '1Doe', '$2a$10$fDKfBrdfbUt7PkFU.fKH2usQpNnTcquG4l9YQ1TheOkESCCrB1ilG', 'USER'
WHERE NOT EXISTS (SELECT id FROM app_user WHERE id = '43adaad2-7c88-4237-af57-ba56376ef26c');

INSERT INTO public.app_user(id, email, first_name, last_name, password, role)
SELECT '57e48090-64b3-4ab7-b39d-c55889266958', '2jane.smith@mail.com', '2Jane', '2Smith', '$2a$10$fDKfBrdfbUt7PkFU.fKH2usQpNnTcquG4l9YQ1TheOkESCCrB1ilG', 'USER'
WHERE NOT EXISTS (SELECT id FROM app_user WHERE id = '57e48090-64b3-4ab7-b39d-c55889266958');

INSERT INTO public.app_user(id, email, first_name, last_name, password, role)
SELECT 'efd31af9-cd0e-4e40-9869-a31b795208e6', '3john.doe@mail.com', '3John', '3Doe', '$2a$10$fDKfBrdfbUt7PkFU.fKH2usQpNnTcquG4l9YQ1TheOkESCCrB1ilG', 'USER'
WHERE NOT EXISTS (SELECT id FROM app_user WHERE id = 'efd31af9-cd0e-4e40-9869-a31b795208e6');

INSERT INTO public.app_user(id, email, first_name, last_name, password, role)
SELECT '76f40ad8-2626-4bea-b5a5-8671b592921d', '4jane.smith@mail.com', '4Jane', '4Smith', '$2a$10$fDKfBrdfbUt7PkFU.fKH2usQpNnTcquG4l9YQ1TheOkESCCrB1ilG', 'USER'
WHERE NOT EXISTS (SELECT id FROM app_user WHERE id = '76f40ad8-2626-4bea-b5a5-8671b592921d');

INSERT INTO public.app_user(id, email, first_name, last_name, password, role)
SELECT '215bc7ef-ac47-4338-897d-e9e2831acc4c', '5john.doe@mail.com', '5John', '5Doe', '$2a$10$fDKfBrdfbUt7PkFU.fKH2usQpNnTcquG4l9YQ1TheOkESCCrB1ilG', 'USER'
WHERE NOT EXISTS (SELECT id FROM app_user WHERE id = '215bc7ef-ac47-4338-897d-e9e2831acc4c');

INSERT INTO public.app_user(id, email, first_name, last_name, password, role)
SELECT '2a49e57b-0047-42d0-92ec-c19bdc64c7a6', '6jane.smith@mail.com', '6Jane', '6Smith', '$2a$10$fDKfBrdfbUt7PkFU.fKH2usQpNnTcquG4l9YQ1TheOkESCCrB1ilG', 'USER'
WHERE NOT EXISTS (SELECT id FROM app_user WHERE id = '2a49e57b-0047-42d0-92ec-c19bdc64c7a6');



INSERT INTO public.contact_info(id, type, value, user_id)
SELECT 'c3feabb7-35bc-4be2-9a6c-972a2665f71a', 'MOBILE', '5645645645', '1a31e54b-3e45-4c13-9b22-4589f19a4a93'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = 'c3feabb7-35bc-4be2-9a6c-972a2665f71a');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT 'c2d823e0-cd3b-4a97-925c-5be24f716f32', 'MOBILE', '2344234', '2e42b36d-2f55-4a98-813a-7f9bd0ec07d1'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = 'c2d823e0-cd3b-4a97-925c-5be24f716f32');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT 'f5e1954f-9496-4877-a133-e2523e13bcd0', 'MOBILE', '67687675', '43adaad2-7c88-4237-af57-ba56376ef26c'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = 'f5e1954f-9496-4877-a133-e2523e13bcd0');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT 'cff472ec-8f55-4ff7-b0d6-6dab7aa13369', 'MOBILE', '4354567', '57e48090-64b3-4ab7-b39d-c55889266958'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = 'cff472ec-8f55-4ff7-b0d6-6dab7aa13369');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT '1d4173e2-40e3-4ee8-b8c8-4111691ed666', 'MOBILE', '3423557', 'efd31af9-cd0e-4e40-9869-a31b795208e6'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = '1d4173e2-40e3-4ee8-b8c8-4111691ed666');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT '68c0c0c6-23eb-4af5-b281-27301a3f1f59', 'MOBILE', '2343467', '76f40ad8-2626-4bea-b5a5-8671b592921d'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = '68c0c0c6-23eb-4af5-b281-27301a3f1f59');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT '2023a310-e03b-487b-ad26-e8fb51941597', 'MOBILE', '23454657', '215bc7ef-ac47-4338-897d-e9e2831acc4c'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = '2023a310-e03b-487b-ad26-e8fb51941597');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT '06e9d2ba-fd6e-469c-939b-47fd0f19475c', 'MOBILE', '45676584', '2a49e57b-0047-42d0-92ec-c19bdc64c7a6'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = '06e9d2ba-fd6e-469c-939b-47fd0f19475c');





INSERT INTO public.contact_info(id, type, value, user_id)
SELECT 'f00ddb92-9ab2-41ad-91d6-f6576587424d', 'EMAIL', '5645645645@mail.com', '1a31e54b-3e45-4c13-9b22-4589f19a4a93'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = 'f00ddb92-9ab2-41ad-91d6-f6576587424d');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT '21b405da-acd8-4ecf-a8cc-14f8cd09bb58', 'EMAIL', '2344234@mail.com', '2e42b36d-2f55-4a98-813a-7f9bd0ec07d1'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = '21b405da-acd8-4ecf-a8cc-14f8cd09bb58');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT 'dba9d50f-ba35-4ebe-8ad7-68ac20525029', 'EMAIL', '67687675@mail.com', '43adaad2-7c88-4237-af57-ba56376ef26c'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = 'dba9d50f-ba35-4ebe-8ad7-68ac20525029');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT 'd407b310-bec1-49fc-b673-990e6cf3ca7d', 'EMAIL', '4354567@mail.com', '57e48090-64b3-4ab7-b39d-c55889266958'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = 'd407b310-bec1-49fc-b673-990e6cf3ca7d');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT '174b9f6a-0a88-41a2-af7e-4f00d172805a', 'EMAIL', '3423557@mail.com', 'efd31af9-cd0e-4e40-9869-a31b795208e6'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = '174b9f6a-0a88-41a2-af7e-4f00d172805a');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT '0f1e7885-3f2f-4bd6-88a1-5b0c5a10ba73', 'EMAIL', '2343467@mail.com', '76f40ad8-2626-4bea-b5a5-8671b592921d'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = '0f1e7885-3f2f-4bd6-88a1-5b0c5a10ba73');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT '6c081f42-4451-4442-ac9f-d0990a09004f', 'EMAIL', '23454657@mail.com', '215bc7ef-ac47-4338-897d-e9e2831acc4c'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = '6c081f42-4451-4442-ac9f-d0990a09004f');

INSERT INTO public.contact_info(id, type, value, user_id)
SELECT '6c1ec9f6-c8f0-40d4-8725-8736f2f3614f', 'EMAIL', '45676584@mail.com', '2a49e57b-0047-42d0-92ec-c19bdc64c7a6'
WHERE NOT EXISTS (SELECT id FROM contact_info WHERE id = '6c1ec9f6-c8f0-40d4-8725-8736f2f3614f');


