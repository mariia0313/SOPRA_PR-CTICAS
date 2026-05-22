package org.sopra.rogueguild.repository.model;

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
                if(discountOrPriceRise == 1){
                    description = "Las armas tienen un descuento del " + percentage + "%";
                }else{
                    description = "Las armas han aumentado su precio un " + percentage + "%";
                }
                break;

            case "HELMET":
                if(discountOrPriceRise == 1){
                    description = "Los cascos tienen un descuento del " + percentage + "%";
                }else{
                    description = "Los cascos han aumentado su precio un " + percentage + "%";
                }
                break;
            case "POTION": 
                if(discountOrPriceRise == 1){
                    description = "Las armas tienen un descuento del " + percentage + "%";
                }else{
                    description = "Las pociones han aumentado su precio un " + percentage + "%";
                }
                break;
            case "BOOTS":
                if(discountOrPriceRise == 1){
                    description = "Las botas tienen un descuento del " + percentage + "%";
                }else{
                    description = "Las botas han aumentado su precio un " + percentage + "%";
                }
                break;
            case "ARMOR":

                if(discountOrPriceRise == 1){
                    description = "Las armaduras tienen un descuento del " + percentage + "%";
                }else{
                    description = "Las armaduras han aumentado su precio un " + percentage + "%";
                }
                break;
            
                default:
                throw new AssertionError();
        }
        return description;
    }



}
