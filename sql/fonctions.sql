CREATE FUNCTION dbo.listeJeux (@filtreTitre NVARCHAR(50))
RETURNS TABLE
AS
RETURN
(
select ex.no_jeu as noJeu, j.titre as titre, reference, age_min as ageMin, description,  duree, tarif_jour as tarifJour, COUNT(ex.no_jeu) as nbExemplairesDisponibles
 from jeux j left join exemplaires ex on j.no_jeu = ex.no_jeu
 where ex.louable = 1
  and ('TOUS'=@filtreTitre OR titre like '%' + @filtreTitre + '%')
group by ex.no_jeu, j.titre, j.reference, j.description, j.tarif_jour, j.duree, j.age_min
);