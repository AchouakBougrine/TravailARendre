package devoir1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSetMetaData;

public class ExoJDBC {

    Statement stmt;
    MySQLConnection con;

    public ExoJDBC(MySQLConnection con) {
        this.con = con;
        stmt = con.getStmt();
    }

    // Méthode pour afficher le développeurqui a réalisé le maximum de scripts par jour
    public void afficherMAXnbscripts() {
        try {
            // Exécution de la requête SQL pour savoir le développeur quia réalisé le maximum de scripts par jour
            ResultSet result = stmt.executeQuery("SELECT MAX(NbScripts) AS MaxScripts, Jour, Developpeurs "
                    + "FROM DevData\n"
                    + "GROUP BY NbScripts, Developpeurs");
            System.out.println("***********************************************************");
            System.out.println(" Le développeur qui a réalisé le nombre maximum de scripts en une journée :");
            System.out.println("***********************************************************");
            // Parcourir les résultats et afficher les informations souhaitées
            while (result.next()) {
                System.out.println("Le developpeur est :" + result.getString("Developpeurs"));
                System.out.println("Le jour du nombre de script est :" + result.getString("Jour"));
                System.out.println("Le nombre de scripts est :" + result.getInt("MaxScripts"));
                System.out.println("***********************************************************");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void afficherListeDesDeveloppeursParNbScripts() {
        try {
            // Exécution de la requête SQL pour avoir la liste des développeurs triée par NbScripts décroissant
            ResultSet result = stmt.executeQuery("SELECT SUM(NbScripts) AS C, Developpeurs, Jour "
                    + "FROM devdata\n"
                    + "GROUP BY NbScripts");
            System.out.println("***********************************************************");
            System.out.println("        Liste des développeurs triés par nombre de scripts        ");
            System.out.println("***********************************************************");
            // Parcourir les résultats et afficher les informations souhaitées
            while (result.next()) {
                System.out.println("Le nom du Developpeur est :" + result.getString("Developpeurs"));
                System.out.println("Le jour du nombre de script est :" + result.getString("Jour"));
                System.out.println("Le nombre de scripts est :" + result.getInt("C"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void afficherTotalScriptsSemaine() {
        try {
            // Exécution de la requête SQL pour calculer le total des scripts réalisés en une semaine
            ResultSet result = stmt.executeQuery("SELECT SUM(NbScripts) AS TotalScripts, WEEK(Jour) AS WeekNumber \n"
                    + "FROM DevData \n"
                    + "GROUP BY WEEK(Jour);");
            System.out.println("***********************************************************");
            System.out.println(" le total des scripts réalisés en une semaine :");
            System.out.println("***********************************************************");
            //Parcourir les résultats et afficher les informations souhaitées
            while (result.next()) {
                int totalScripts = result.getInt("TotalScripts");
                System.out.println("le total des scripts réalisés en une semaine est:" + totalScripts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher le nombre total de scripts réalisés par un programmeur
    public void afficherTotalScriptsParProgrammeur(String programmeur) {
        try {
            // Exécution de la requête SQL pour calculer le total des scripts que un programmeur a realisé

            ResultSet result = stmt.executeQuery("SELECT SUM(NbScripts) AS TotalScripts "
                    + "FROM DevData "
                    + "WHERE Developpeurs = '" + programmeur + "' ;");
            if (result.next()) {
                int totalScripts = result.getInt("TotalScripts");
                System.out.println("***********************************************************");
                System.out.println("Total des scripts réalisés par " + programmeur + " est " + totalScripts);
                System.out.println("***********************************************************");
            } else {
                System.out.println("Aucun script trouvé pour: " + programmeur + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Exécution d'une requête libre et afficher les méta-informations
    public void requeteLibre() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Veillez entrez votre requête SQL svp : ");
            String sql = scanner.nextLine();
            // Vérifier si la requête est une requête SELECT
            if (sql.trim().toUpperCase().startsWith("SELECT")) {
                ResultSet result = stmt.executeQuery(sql);
                ResultSetMetaData metaData = result.getMetaData();
                // Afficher le nombre de colonnes
                int columnCount = metaData.getColumnCount();
                System.out.println("Nombre de colonnes : " + columnCount);
                // Afficher les noms et types de données des colonnes
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnType = metaData.getColumnTypeName(i);
                    System.out.println("Colonne " + i + ": " + columnName + " (Type: " + columnType + ")");
                }
                // Afficher le contenu de la table
                while (result.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(result.getString(i) + " ");
                    }
                    System.out.println();
                }
            } else {
                // Pour les requêtes qui ne sont pas des SELECT (INSERT, UPDATE, DELETE)
                int rowsAffected = stmt.executeUpdate(sql);
                System.out.println("Nombre de lignes modifiées : " + rowsAffected);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
