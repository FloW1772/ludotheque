create procedure init_jeu_essai
as
BEGIN
	declare @no_jeu INT;
	declare @no_client INT;
	declare @no_exemplaire INT;
	delete from clients;
	delete from adresses;
	delete from jeux_genres;
	delete from exemplaires;
	delete from jeux;
	delete from genres;
insert into genres (no_genre, libelle) values (1, 'Jeu de plateau');
insert into genres (no_genre, libelle) values (2, 'Jeu de cartes');
insert into genres (no_genre, libelle) values (3, 'Jeu de stratégie');
insert into genres (no_genre, libelle) values (4, 'Coopératif');
insert into genres (no_genre, libelle) values (5, 'Jeu de dé');
insert into genres (no_genre, libelle) values (6, 'Jeu d''enquete');
insert into adresses(rue, code_postal, ville) values ('rue des Cormorans', '79000', 'Niort');
INSERT INTO CLIENTS (nom, prenom, email, no_telephone, no_adresse) VALUES ('Curie', 'Marie', 'marie.curie@example.com', '123456789', scope_identity());
insert into adresses(rue, code_postal, ville) values ('rue des marguerites', '79500', 'Melle');
INSERT INTO CLIENTS (nom, prenom, email, no_telephone, no_adresse) VALUES ('Einstein', 'Albert', 'albert.einstein@example.com', '0123456789', scope_identity());
insert into jeux (titre, description,reference, duree, age_min, tarif_jour) values ('Pandemic', 'Descr pandemic', 'refPandemic', 30,10, 12.5 );
select @no_jeu = no_jeu from jeux where reference='refPandemic';
insert into jeux_genres(no_jeu, no_genre) values (@no_jeu, 3);
insert into jeux_genres(no_jeu, no_genre) values (@no_jeu, 1);
insert into exemplaires (no_jeu, codebarre, louable) values (@no_jeu, '1111111111111', 1);
insert into exemplaires (no_jeu, codebarre, louable) values (@no_jeu, '2222222222222', 0);
insert into exemplaires (no_jeu, codebarre, louable) values (@no_jeu, '3333333333333', 1);
insert into jeux (titre, description,reference, duree, age_min, tarif_jour) values ('Welcome', 'Descr welcome', 'refWelcome', 30,10, 9.3 );
select @no_jeu = no_jeu from jeux where reference='refWelcome';
insert into jeux_genres(no_jeu, no_genre) values (@no_jeu, 2);
insert into jeux_genres(no_jeu, no_genre) values (@no_jeu, 3);
insert into exemplaires (no_jeu, codebarre, louable) values (@no_jeu, '4444444444444', 1);
insert into exemplaires (no_jeu, codebarre, louable) values (@no_jeu, '5555555555555', 0);
insert into exemplaires (no_jeu, codebarre, louable) values (@no_jeu, '6666666666666', 1);
END;