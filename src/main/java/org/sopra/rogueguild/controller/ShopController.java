package org.sopra.rogueguild.controller;

import java.util.ArrayList;
import java.util.Scanner;

import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Armor;
import org.sopra.rogueguild.repository.model.Boots;
import org.sopra.rogueguild.repository.model.Helmet;
import org.sopra.rogueguild.repository.model.Incursion;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.Quest;
import org.sopra.rogueguild.repository.model.Quests;
import org.sopra.rogueguild.repository.model.StatQuest;
import org.sopra.rogueguild.repository.model.Weapon;
import org.sopra.rogueguild.repository.model.WorldEvent;
import org.sopra.rogueguild.repository.model.WorldEventGenerator;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.view.components.MessageView;
import org.sopra.rogueguild.view.components.PlayerView;

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
    
    public void start() throws Exception {
        Quests quests = new Quests();
        quests.createInitialQuests();
        WorldEventGenerator worldEventGenerator = new WorldEventGenerator();
        WorldEvent worldEvent = worldEventGenerator.generateRandomWorldEventer(repository);
        MessageView message = new MessageView(System.out, 10);
        int opt;
        do {
            view.landingPage();
            view.playerStatus(player);
            opt = readNumber();
            switch (opt) {
                case 1:
                    message.showMessage(worldEvent.getEventDesription());
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    message.showMessage(worldEvent.getEventDesription());
                    view.displayStock(repository.getAllStock(), true);
                    int itemId = readNumber();
                    if (itemId != -1) {
                        BuyResponse buyResponse = buyProcess(itemId);
                        view.buyResult(buyResponse);
                    }
                    break;
                case 3:
                    if (player.getInventory().isEmpty()) {
                        message.showMessage("No tienes items en el inventario.");
                    } else {
                        PlayerView playerView = new PlayerView(System.out);
                        playerView.playerInventoryToSell(player);
                        int itemIdToSell = readNumber();
                        if (itemIdToSell != -1) {
                        int realItemPosition = itemIdToSell-1;
                        if (realItemPosition < 0 || realItemPosition >= player.getInventory().size()){
                            message.showMessage("Error. Id introducido inválido");
                        } else {
                            sellProcess(player.getInventory().get(realItemPosition));
                        }
                        }
                        

                    }

                    break;
                case 4:
                    message.showMessage("Qué tipo de incursión quieres realizar?\n 1. Incursión de conquista.\n 2. Incursión de saqueo.\n 3. Incursión menor");
                    int option = readNumber();
                    if (option != -1) {
                        if (option > 3 || option < 1){
                            message.showMessage("Opción inválida");
                        } else {
                            doIncursion(option);
                        }
                    }

                    break;
                case 5: 
                    quests.showAvailableQuests();
                    int option2 = readNumber();
                    if (option2 != -1) {
                        if (option2 < 1 || option2 > quests.getQuests().size()){
                            message.showMessage("Opción inválida");
                        } else {
                            doQuest(option2, quests);
                        }
                    }
                    break;
                case 6:
                    message.showMessage(player.showInventory());
                    int option3 = readNumber();
                    if (option3 != -1) {
                        message.showMessage(equipItem(player.getInventory().get(option3-1)));
                    }
                    
                    break;
                
                case 7:
                    message.showMessage(showEquippedItems());
                        int option4 = readNumber();
                        if (option4 != -1){
                            if (option4 >= 0 && option4 > player.getItemEquipped().size()) {
                                Item item = player.getItemEquipped().get(option4-1);
                                message.showMessage(unequipItem(item));
                            } else {
                                message.showMessage("Opción no válida");
                            }
                        }
                    break;
                case 8:
                    message.showMessage(showEquippedItems());
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

    private void sellProcess(Item item) throws Exception {
        MessageView message = new MessageView(System.out, 10);
        player.removeItem(item);

        int nextShopId = repository.getAllStock().keySet().stream()
                            .max(Integer::compare)
                            .orElse(0) + 1;
        repository.getAllStock().put(nextShopId, item);

        double rawSellPrice = item.getBasePrice() * 0.80;
        int goldRecieved = (int) (Math.round(rawSellPrice / 5.0) * 5);

        try {
            player.addGold(goldRecieved);
        } catch (Exception e){
            message.showMessage(e.getMessage());
        }

        message.showMessage("Has vendido " + item.getName() + " por " + goldRecieved + " monedas.");
    }

    private void doIncursion(int opt) throws Exception{
        Incursion incursion = new Incursion(opt, player);
        MessageView message = new MessageView(System.out, 10);
        message.showMessage(incursion.getDescription());

        repository.loadInitialStock();
    }

    private void doQuest(int opt, Quests quests) throws Exception{
        MessageView message = new MessageView(System.out, 10);
        Quest quest = quests.getQuests().get(opt - 1);
        if (quest.checkRequirement(player) == true) {
            int oldGold = player.getGold(); // Guardamos el oro actual ANTES de añadir el nuevo
            int goldReward = quest.getGoldReward();
            try {
                player.addGold(goldReward);
                quest.completeQuest();
                message.showMessage("La misión ha sido completada con éxito. Has ganado " + goldReward + " de oro.");
            } catch (Exception e){
                int realReceivedGold = 500 - oldGold;
                quest.completeQuest();
                message.showMessage("La misión ha sido completada. Solo pudiste reclamar " + realReceivedGold + " de oro debido al límite.");
            } 
            } else {
                String requirements = "No se cumplen con los requisitos requeridos para completar la misión";
                if (!quest.hasRequiredItems()) {
                    requirements += "\n" + ((StatQuest) quest).requirementsLeftStatQuest(player);
                } else {
                    if (quest.requirementsLeft(player).isEmpty()) {
                    for (ItemCategory category : quest.requirementsLeft(player)) {
                       requirements += "\nNecesitas un objeto de categoría " + category.name(); 
                    }
                    }
                }

            message.showMessage(requirements);
        }
        }

    private int readNumber() {
        int number = 0;
        try {
            number = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            MessageView message = new MessageView(System.out, 10);
            message.showMessage("Error: ¡Debes introducir un número válido, no caracteres o letras!");
            number = -1;
        }

        return number;
    }


    public String equipItem(Item item){
        MessageView message = new MessageView(System.out, 10);
        int option = 0;
        ArrayList<Item> itemEquipped = player.getItemEquipped();
        String result = "";
        switch (item.getItemCategory()) {
            case WEAPON:
                if (itemEquipped.get(0).getName() == null) {
                itemEquipped.set(0, item);
                player.removeItem(item);
                result = "Arma equipada en la primera ranura.";
            } else if (itemEquipped.get(1).getName() == null) {
                itemEquipped.set(1, item);
                player.removeItem(item);
                result = "Arma equipada en la segunda ranura.";
            } else {

                int damage0 = ((Weapon) itemEquipped.get(0)).getDamage();
                int damage1 = ((Weapon) itemEquipped.get(1)).getDamage();

                int slotToReplace = 0;
                if (damage1 < damage0) {
                    slotToReplace = 1;
                }

                Item oldWeapon = itemEquipped.get(slotToReplace);
                
                
                itemEquipped.set(slotToReplace, item);
                player.removeItem(item);
                player.addItem(oldWeapon);
                
                result = "Ranuras llenas. Se reemplazó el arma " + oldWeapon.getName() + " (Menor daño/Primera) y volvió a tu inventario.";
            }
                break;
            case BOOTS:
                if(itemEquipped.get(3).getName() == null){
                    itemEquipped.set(3, item);
                    player.removeItem(item);
                    result = "El item se ha equipado correctamente";
                }else{
                    message.showMessage("Ya tienes un item de este tipo equipado. Desea remplazarlo? (Si: 1,  No: 2)  \nItem actual: " + itemEquipped.get(3).toString());
                    option = readNumber();
                    if(option!=-1){
                        if(option == 1){
                            player.addItem(player.getItemEquipped().get(3));
                            itemEquipped.set(3, item);
                            player.removeItem(item);
                            result = "El item se ha equipado correctamente";
                        }else{
                            result = "El item no se ha equipado";
                        }
                    }
                }
                break;
            case ARMOR:
                if(itemEquipped.get(2).getName() == null){
                    itemEquipped.set(2, item);
                    player.removeItem(item);
                    result = "El item se ha equipado correctamente";
                }else{
                    message.showMessage("Ya tienes un item de este tipo equipado. Desea remplazarlo? (Si: 1,  No: 2)  \nItem actual: " + itemEquipped.get(2).toString());
                    option = readNumber();
                    if(option!=-1){
                        if(option == 1){
                            player.addItem(player.getItemEquipped().get(2));
                            itemEquipped.set(2, item);
                            player.removeItem(item);
                            result = "El item se ha equipado correctamente";
                        }else{
                            result = "El item no se ha equipado";
                        }
                    }
                }
                break;
            case HELMET:
                if(itemEquipped.get(4).getName() == null){
                    itemEquipped.set(4, item);
                    player.removeItem(item);
                    result = "El item se ha equipado correctamente";
                }else{
                    message.showMessage("Ya tienes un item de este tipo equipado. Desea remplazarlo? (Si: 1,  No: 2)  \nItem actual: " + itemEquipped.get(4).toString());
                    option = readNumber();
                    if(option!=-1){
                        if(option == 1){
                            player.addItem(player.getItemEquipped().get(4));
                            itemEquipped.set(4, item);
                            player.removeItem(item);
                            result = "El item se ha equipado correctamente";
                        }else{
                            result = "El item no se ha equipado";
                        }
                    }
                }
                break;
            default:
                throw new AssertionError();
        }
        return result;
    }

    public String unequipItem(Item item) {
                MessageView message = new MessageView(System.out, 10);
                int option = 0;
                ArrayList<Item> itemEquipped = player.getItemEquipped();
                String result = "";
                switch (item.getItemCategory()) {
                case WEAPON:
                    if (itemEquipped.get(0).getName().equals(item.getName())) { 
                    player.addItem(itemEquipped.get(0));     
                    itemEquipped.set(0, new Weapon(null, 0, 0));
                    result = "Has desequipado el arma " + item.getName() + " de la primera ranura.";
                    
                } else if (itemEquipped.get(1).getName().equals(item.getName())) {
                    player.addItem(itemEquipped.get(1)); 
                    itemEquipped.set(1, new Weapon(null, 0, 0));
                    player.addItem(item);
                    result = "Has desequipado el arma " + item.getName() + " de la segunda ranura.";
                    
                } else {
                    result = "Esa arma no la tienes equipada.";
                }
                    break;
                case BOOTS:
                    player.addItem(player.getItemEquipped().get(3));
                    itemEquipped.set(3, new Boots(null, 0, 0));
                    break;
                case ARMOR:
                    player.addItem(player.getItemEquipped().get(2));
                    itemEquipped.set(2, new Armor(null, 0, 0));
                    break;
                case HELMET:
                    player.addItem(player.getItemEquipped().get(4));
                    itemEquipped.set(4, new Helmet(null, 0, 0));
                    break;
                default:
                    throw new AssertionError();
            }
        return result;
}

public String showEquippedItems(){
    String itemsEquipped = "";
    int quantityItems = 0;
    for (int i = 0; i < player.getItemEquipped().size(); i++) {
        if (player.getItemEquipped().get(i).getName() != null) {
            itemsEquipped += "| " + (i+1) + " " + player.getItemEquipped().get(i).toString() + "\n";
            quantityItems++;
        }
    }

    if (quantityItems == 0) {
        itemsEquipped = "No tienes items equipados.";
    }

    return itemsEquipped;
}
    
}
