package org.sopra.rogueguild.controller;

import java.util.Scanner;

import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Incursion;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.WorldEvent;
import org.sopra.rogueguild.repository.model.WorldEventGenerator;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.view.components.MessageView;
import org.sopra.rogueguild.view.components.PlayerView;
import org.sopra.rogueguild.controller.dto.BuyResponse;

public class ShopController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final Scanner sc;

    public ShopController(Player p, ViewDisplay v, ShopRepository r) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        WorldEventGenerator worldEventGenerator = new WorldEventGenerator();
        WorldEvent worldEvent = worldEventGenerator.generateRandomWorldEventer(repository);
        MessageView message = new MessageView(System.out, 10);
        int opt;
        do {
            view.landingPage();
            view.playerStatus(player);
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    message.showMessage(worldEvent.getEventDesription());
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    message.showMessage(worldEvent.getEventDesription());
                    view.displayStock(repository.getAllStock(), true);
                    int itemId = Integer.parseInt(sc.nextLine());
                    BuyResponse buyResponse = buyProcess(itemId);
                    view.buyResult(buyResponse);
                    break;
                case 3:
                    if (player.getInventory().isEmpty()) {
                        message.showMessage("No tienes items en el inventario.");
                    } else {
                        PlayerView playerView = new PlayerView(System.out);
                        playerView.playerInventoryToSell(player);
                        int itemIdToSell = Integer.parseInt(sc.nextLine());
                        int realItemPosition = itemIdToSell-1;
                        if (realItemPosition < 0 || realItemPosition >= player.getInventory().size()){
                            message.showMessage("Error. Id introducido inválido");
                        } else {
                            sellProcess(player.getInventory().get(realItemPosition));
                        }

                    }

                    break;
                case 4:
                    message.showMessage("Qué tipo de incursión quieres realizar?\n 1. Incursión de conquista.\n 2. Incursión de saqueo.\n 3. Incursión menor");
                    int option = Integer.parseInt(sc.nextLine());
                    if (option > 3 || option < 1){
                        message.showMessage("Opción inválida");
                    } else {
                        doIncursion(option);
                    }

                    break;
                case 0:
                    view.quitMessage();
                    break;
                }
                view.pressKeyMessage();
                sc.nextLine();
        } while (opt != 0);
    }

    private BuyResponse buyProcess(int id) {
        Item item = repository.getItem(id);
        if (item == null) {
            return BuyResponse.notFound(id);
        }
        if (player.getGold() < item.getPrice()) {
            return BuyResponse.notEnoughGold(item, player.getGold());
        }
        player.buy(item);
        repository.removeItem(id);
        return BuyResponse.success(item);
    }

    private void sellProcess(Item item) {
        player.removeItem(item);

        int nextShopId = repository.getAllStock().keySet().stream()
                            .max(Integer::compare)
                            .orElse(0) + 1;
        repository.getAllStock().put(nextShopId, item);

        double rawSellPrice = item.getBasePrice() * 0.80;
        int goldRecieved = (int) (Math.round(rawSellPrice / 5.0) * 5);

        player.addGold(goldRecieved);

        System.out.println("Has vendido " + item.getName() + " por " + goldRecieved + " monedas.");
    }

    private void doIncursion(int opt){
        Incursion incursion = new Incursion(opt, player);
        MessageView message = new MessageView(System.out, 10);
        message.showMessage(incursion.getDescription());

        repository.loadInitialStock();
    }
}
