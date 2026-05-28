package org.sopra.rogueguild.repository.model;

public class StatQuest extends Quest {
    private int requiredArmor;
    private int requiredDamage;

    public StatQuest(String description, int goldReward, int requiredArmor, int requiredDamage){
        super(description, goldReward, null);
        this.requiredArmor = requiredArmor;
        this.requiredDamage = requiredDamage;
    }

    public boolean checkRequirement(Player player) {
        boolean meetsRequirements = false;
        int totalArmor = 0;
        int totalAttack = 0;

        for (Item item : player.getItemEquipped()) {
            if (item != null && item.getName() != null) {
                
                if (item instanceof Weapon) {
                    totalAttack += ((Weapon) item).getDamage();
                }
                
                if (item instanceof Armor) {
                    totalArmor += ((Armor) item).getShield(); 
                } else if (item instanceof Helmet) {
                    totalArmor += ((Helmet) item).getDefense();
                }
            }
        }
        
        if (requiredDamage != 0 && totalAttack >= requiredDamage) {
            meetsRequirements = true;
        }

        if (requiredArmor != 0 && totalArmor >= requiredArmor) {
            meetsRequirements = true;
        }

        return meetsRequirements;
    }

    public String requirementsLeftStatQuest(Player player){
        String result = "";
        int totalArmor = 0;
        int totalAttack = 0;

        for (Item item : player.getItemEquipped()) {
            if (item != null && item.getName() != null) {
                
                if (item instanceof Weapon) {
                    totalAttack += ((Weapon) item).getDamage();
                }
                
                if (item instanceof Armor) {
                    totalArmor += ((Armor) item).getShield(); 
                } else if (item instanceof Helmet) {
                    totalArmor += ((Helmet) item).getDefense();
                }
            }
        }

        if (requiredDamage != 0 && totalAttack < requiredDamage) {
            result = "Te faltan " + (requiredDamage - totalAttack) + " de ataque para cumplir con los requisitos";
        }

        if (requiredArmor != 0 && totalArmor >= requiredArmor) {
            result = "Te faltan " + (requiredArmor - totalArmor) + " de armadura para cumplir con los requisitos";
        }

        return result;
    }
}
