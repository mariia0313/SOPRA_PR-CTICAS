package org.sopra.rogueguild.controller;

import java.util.ArrayList;
import java.util.Scanner;

import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Armor;
import org.sopra.rogueguild.repository.model.Boots;
import org.sopra.rogueguild.repository.model.City;
import org.sopra.rogueguild.repository.model.Helmet;
import org.sopra.rogueguild.repository.model.Incursion;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.Potion;
import org.sopra.rogueguild.repository.model.Quest;
import org.sopra.rogueguild.repository.model.Quests;
import org.sopra.rogueguild.repository.model.StatQuest;
import org.sopra.rogueguild.repository.model.Weapon;
import org.sopra.rogueguild.repository.model.WorldEvent;
import org.sopra.rogueguild.repository.model.WorldEventGenerator;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.view.components.MessageView;
import org.sopra.rogueguild.view.components.PlayerView;

/**
 * Controlador principal del flujo de la tienda.
 *
 * Coordina la interacción entre el jugador, el repositorio y la vista,
 * gestionando las opciones del menú principal: compra, venta, equipamiento,
 * incursiones y misiones.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class ShopController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final Scanner sc;
    private City startingCity;
    private Quests quests = new Quests();

    public ShopController(Player p, ViewDisplay v, ShopRepository r, City startingCity) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.sc = new Scanner(System.in);
        this.startingCity = startingCity;
        quests.createInitialQuests();
    }
    
    public void start() throws Exception {
        WorldEventGenerator worldEventGenerator = new WorldEventGenerator();
        WorldEvent worldEvent = worldEventGenerator.generateRandomWorldEventer(repository);
        MessageView message = new MessageView(System.out, 10);
        int opt;

        message.showMessage("¡Bienvenido a ROGUE GUILD! ¿Cúal es tu nombre?"); 
        String name = sc.nextLine();
        player.setName(name);
        player.setCurrentCity(startingCity);

        do {
            view.landingPage();
            view.playerStatus(player);
            System.out.print("| Introduzca el número de la acción a realizar: ");
            opt = readNumber();
            switch (opt) {
                case 1:
                    message.showMessage(worldEvent.getEventDesription());
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    message.showMessage(worldEvent.getEventDesription());
                    view.displayStock(repository.getAllStock(), true);
                    System.out.print("| Introduzca el número del arma a comprar: ");
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
                        System.out.print("| Introduzca el número del item a vender: ");
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
                    System.out.print("| Introduzca el número de la incursión a realizar: ");
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
                    System.out.print("| Introduzca el número de la misión a realizar: ");
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
                    System.out.print("| Introduzca el número del item a equipar: ");
                    int option3 = readNumber();
                    if (option3 != -1) {
                        if (option3 < 1 || option3 > quests.getQuests().size()){
                            message.showMessage("Opción inválida");
                        } else {
                        message.showMessage(equipItem(player.getInventory().get(option3-1)));
                        }
                    }
                    
                    break;
                
                case 7:
                    message.showMessage(showEquippedItems());
                    ArrayList<Item> visibleItems = new ArrayList<>();
                    for (Item item : player.getItemEquipped()) {
                        if (item != null && item.getName() != null) {
                            visibleItems.add(item);
                        }
                    }

                    System.out.print("| Introduzca el número del item a desequipar: ");
                    int option4 = readNumber();
                    if (option4 != -1) {
                        if (option4 > 0 && option4 <= visibleItems.size()) {
                            Item itemSeleccionado = visibleItems.get(option4 - 1);
                            message.showMessage(unequipItem(itemSeleccionado));
                        } else {
                            message.showMessage("Opción no válida");
                        }
                    }
                    break;
                case 8:
                    message.showMessage(showEquippedItems());
                    break;

                case 9:
                    travelProcess();
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
        MessageView message = new MessageView(System.out, 10);
        Item item = repository.getItem(id);
        if (item == null) {
            return BuyResponse.notFound(id);
        } else if (player.getGold() < item.getPrice()) {
            return BuyResponse.notEnoughGold(item, player.getGold());
        } else {
            player.buy(item);
            repository.removeItem(id);
            if (item.getItemCategory().equals(ItemCategory.POTION)) {
                message.showMessage(player.healPlayer((Potion)item));
                player.removeItem(item);
            }
            return BuyResponse.success(item);
        }
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
        if (opt >= 1 && opt <= quests.getQuests().size() && quests.getQuests().get(opt-1).getStatus()==false){
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
                    requirements += "\n|| " + ((StatQuest) quest).requirementsLeftStatQuest(player);
                } else {
                    if (!quest.requirementsLeft(player).isEmpty()) {
                    for (ItemCategory category : quest.requirementsLeft(player)) {
                       requirements += "\n|| Necesitas un objeto de categoría " + category.name(); 
                    }
                    }
                }
                message.showMessage(requirements);
            }

        } else {
            message.showMessage("Esta misón ya ha sido completada");
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
                message.showMessage("Ya tienes un item de este tipo equipado. Desea remplazarlo por el de daño más bajo? (Si: 1,  No: 2)\n | Arma 1: " + itemEquipped.get(0).toString() + " | Arma 2: " + itemEquipped.get(1).toString());
                option = readNumber();
                if(option!=-1){
                    if(option == 1){
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
                    } else {
                    result = "El item no se ha equipado";
                    }
                }
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
                result = "El item no se puede equipar, es de tipo " + item.getItemCategory();
                break;
        }
        return result;
    }

    public String unequipItem(Item item) {
                ArrayList<Item> itemEquipped = player.getItemEquipped();
                String result = "";

                switch (item.getItemCategory()) {
                    case WEAPON:
                        if (itemEquipped.get(0) == item) { 
                            player.addItem(itemEquipped.get(0));     
                            itemEquipped.set(0, new Weapon(null, 0, 0));
                            result = "Has desequipado el arma " + item.getName() + " de la primera ranura.";
                        } else if (itemEquipped.get(1) == item) {
                            player.addItem(itemEquipped.get(1)); 
                            itemEquipped.set(1, new Weapon(null, 0, 0));
                            result = "Has desequipado el arma " + item.getName() + " de la segunda ranura.";
                        } else {
                            result = "Esa arma no la tienes equipada.";
                        }
                        break;
                        
                    case BOOTS:
                        player.addItem(itemEquipped.get(3));
                        itemEquipped.set(3, new Boots(null, 0, 0));
                        result = "Has desequipado las botas " + item.getName() + ".";
                        break;
                        
                    case ARMOR:
                        player.addItem(itemEquipped.get(2));
                        itemEquipped.set(2, new Armor(null, 0, 0));
                        result = "Has desequipado la armadura " + item.getName() + ".";
                        break;
                        
                    case HELMET:
                        player.addItem(itemEquipped.get(4));
                        itemEquipped.set(4, new Helmet(null, 0, 0));
                        result = "Has desequipado el casco " + item.getName() + ".";
                        break;
                        
                    default:
                        result = "Opción no válida";
                        break;
                }
                
                return result;
}

    public String showEquippedItems(){
        String itemsEquipped = "";
        int quantityItems = 0;
        for (int i = 0; i < player.getItemEquipped().size(); i++) {
            if (player.getItemEquipped().get(i).getName() != null) {
                itemsEquipped += "\n|| " + (quantityItems+1) + " " + player.getItemEquipped().get(i).toString();
                player.getItemEquipped().get(i).setId(quantityItems);
                quantityItems++;
            }
        }

        if (quantityItems == 0) {
            itemsEquipped = "No tienes items equipados.";
        }

        return itemsEquipped;
    }
    

private void travelProcess() {
        MessageView message = new MessageView(System.out, 10);
        
        System.out.println("\n=================================================");
        System.out.println("   VIAJAR A OTRA CIUDAD");
        System.out.println("   Ciudad actual: " + player.getCurrenCity().getName());
        System.out.println("=================================================");
        System.out.println("| 1. Oakhaven");
        System.out.println("| 2. Sylvanwood");
        System.out.println("| 3. Timberwall");
        System.out.println("| 4. Ironstone");
        System.out.println("| 5. Stormport");
        System.out.println("| 6. Mossdeep");
        System.out.println("| 7. Ravencrest");
        System.out.println("=================================================");
        System.out.print("| Seleccione el número de la ciudad destino: ");
        
        int option = readNumber();
        if (option != -1) {
            City destination = null;
    
            switch (option) {
                case 1: destination = new City("Oakhaven"); break;
                case 2: destination = new City("Sylvanwood"); break;
                case 3: destination = new City("Timberwall"); break;
                case 4: destination = new City("Ironstone"); break;
                case 5: destination = new City("Stormport"); break;
                case 6: destination = new City("Mossdeep"); break;
                case 7: destination = new City("Ravencrest"); break;
                default:
                    message.showMessage("Selección de ciudad inválida.");
            }
    
            if (destination != null) {
                String resultadoViaje = player.travelTo(destination);
                message.showMessage(resultadoViaje);
                
                if (resultadoViaje.contains("¡Has llegado")) {
                    player.setCurrentCity(destination); 
                }
            }

        }
    }
}
