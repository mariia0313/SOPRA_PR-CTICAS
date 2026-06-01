package org.sopra.rogueguild;

import org.sopra.rogueguild.controller.ShopController;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.City;
import static org.sopra.rogueguild.repository.model.City.createWorldMap;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;

public class App {
    public static void main(String[] args) throws Exception{
        ShopRepository repository = new ShopRepository();
        ViewDisplay view = new ViewDisplay();

        City startingCity = createWorldMap();
        Player player = new Player( "Iñigo Montolla", 500, startingCity);

        ShopController controller = new ShopController(player, view, repository);
        controller.start();
    }
}