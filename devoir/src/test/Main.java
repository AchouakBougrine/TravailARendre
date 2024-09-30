/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import devoir1.MySQLConnection;
import devoir1.ExoJDBC;

public class Main {

    public static void main(String[] args) {
        // La connexion à la base de données Scriptsdev et afficher le maximum de scripts
        try (MySQLConnection mysqlconnexion = new MySQLConnection("jdbc:mysql://localhost:3306/scriptsdev", "root", "")) {

            ExoJDBC exo = new ExoJDBC(mysqlconnexion);

            exo.afficherMAXnbscripts();
            exo.afficherListeDesDeveloppeursParNbScripts();
            exo.afficherTotalScriptsSemaine();
            exo.afficherTotalScriptsParProgrammeur("WAFI");
             exo.requeteLibre();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
