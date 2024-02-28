package test;

import models.Equipe;
import services.EquipeService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        EquipeService equipeService = new EquipeService();
        // idjoueur a changer !!!!
        List<Equipe> equipeList = equipeService.getEquipesParMembre(7);

        for (Equipe equipe :equipeList){
            System.out.println(equipe.toString());
        }

        System.out.println("Hello world!");


    }
}