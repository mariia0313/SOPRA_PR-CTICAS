package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.repository.model.ItemCategory;

/**
 * Componente de vista que muestra el resultado de un intento de compra.
 *
 * Interpreta el estado del {@link org.sopra.rogueguild.controller.dto.BuyResponse}
 * y delega el mensaje correspondiente a {@link MessageView}.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class BuyResultView {
    private final MessageView messages;

    public BuyResultView(MessageView messages) {
        this.messages = messages;
    }

    public void show(BuyResponse r) {
        switch (r.getStatus()) {
        case SUCCESS:
            if (!r.getItem().getItemCategory().equals(ItemCategory.POTION)) {
                messages.showMessage("[+] " + r.getItem().getName() + " ya está en tu equipo!");
            }
            break;
        case NOT_FOUND:
                messages.showMessage("[!] Ese objeto (" + r.getRequestedId() + ") no existe en nuestra tienda.");
                break;
        case NOT_ENOUGH_GOLD:
                messages.showMessage("[!] No tienes suficiente oro. Te faltan " + r.getMissingGold() + " monedas.");
                break;
        }
    }
}
