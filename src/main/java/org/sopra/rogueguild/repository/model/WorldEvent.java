package org.sopra.rogueguild.repository.model;

/**
 * Representa un evento de mundo que afecta al precio de los items de la tienda.
 *
 * Puede aplicar un descuento o una subida de precio a una categoría concreta
 * o a todos los items. La descripción del evento se genera automáticamente al construirlo.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */

public class WorldEvent {

    private String description;
    private String targetItems;
    private int discountOrPriceRise;
    private int percentage;

    public WorldEvent(String targetItems, int discountOrPriceRise, int percentage){
        this.targetItems = targetItems;
        this.discountOrPriceRise = discountOrPriceRise;
        this.percentage = percentage;
        this.description = createDescription();
    }

    public String createDescription(){
        String description = "";
        switch (targetItems) {
            case "WEAPON":
                if(discountOrPriceRise == 0){
                    description = "Las armas tienen un descuento del " + percentage + "%";
                }else{
                    description = "Las armas han aumentado su precio un " + percentage + "%";
                }
                break;

            case "HELMET":
                if(discountOrPriceRise == 0){
                    description = "Los cascos tienen un descuento del " + percentage + "%";
                }else{
                    description = "Los cascos han aumentado su precio un " + percentage + "%";
                }
                break;
            case "POTION": 
                if(discountOrPriceRise == 0){
                    description = "Las pociones tienen un descuento del " + percentage + "%";
                }else{
                    description = "Las pociones han aumentado su precio un " + percentage + "%";
                }
                break;
            case "BOOTS":
                if(discountOrPriceRise == 0){
                    description = "Las botas tienen un descuento del " + percentage + "%";
                }else{
                    description = "Las botas han aumentado su precio un " + percentage + "%";
                }
                break;
            case "ARMOR":

                if(discountOrPriceRise == 0){
                    description = "Las armaduras tienen un descuento del " + percentage + "%";
                }else{
                    description = "Las armaduras han aumentado su precio un " + percentage + "%";
                }
                break;

            case "TODOS":
                if(discountOrPriceRise == 0){
                    description = "Una plaga de dragones ha rebajado el precio de toda la mercancía un " + percentage + "%";
                }else{
                    description = "Una plaga de dragones ha disparado el precio de toda la mercancía un " + percentage + "%";
                }
                break;
            
            default:
                throw new AssertionError();
        }
        return description;
    }

    public String getEventDesription(){
        return description;
    }

    public int getPercentage(){
        return percentage;
    }

    public int getDiscountOrPriceRise(){
        return discountOrPriceRise;
    }

    public String getTarget(){
        return targetItems;
    }

}
