INSERT INTO recipe_user (id, name, email) VALUES ('1', 'John Doe', 'john.doe@example.com');
INSERT INTO recipe_user (id, name, email) VALUES ('2', 'Jane Smith', 'jane.smith@example.com');

INSERT INTO recipe (id, name, type, servings, user_id) VALUES ('1', 'Lasagna', 'non-vegetarian', 4, '1');
INSERT INTO recipe (id, name, type, servings, user_id) VALUES ('2', 'Spinach and Ricotta Stuffed Shells', 'vegetarian', 6, '2');

INSERT INTO ingredient (id, ingredients, recipe_id) VALUES ('1', 'Lasagna noodles', '1');
INSERT INTO ingredient (id, ingredients, recipe_id) VALUES ('2', 'Ground beef', '1');
INSERT INTO ingredient (id, ingredients, recipe_id) VALUES ('3', 'Tomato sauce', '1');
INSERT INTO ingredient (id, ingredients, recipe_id) VALUES ('4', 'Jumbo shells', '2');
INSERT INTO ingredient (id, ingredients, recipe_id) VALUES ('5', 'Spinach', '2');
INSERT INTO ingredient (id, ingredients, recipe_id) VALUES ('6', 'Ricotta cheese', '2');

INSERT INTO instruction (id, instructions, recipe_id) VALUES ('1', 'Cook noodles according to package instructions', '1');
INSERT INTO instruction (id, instructions, recipe_id) VALUES ('2', 'Brown ground beef in a skillet', '1');
INSERT INTO instruction (id, instructions, recipe_id) VALUES ('3', 'Layer noodles, meat, and sauce in a baking dish', '1');
INSERT INTO instruction (id, instructions, recipe_id) VALUES ('4', 'Cook jumbo shells according to package instructions', '2');
INSERT INTO instruction (id, instructions, recipe_id) VALUES ('5', 'Mix spinach and ricotta cheese in a bowl', '2');
INSERT INTO instruction (id, instructions, recipe_id) VALUES ('6', 'Stuff cooked shells with spinach and ricotta mixture', '2');