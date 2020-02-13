package Entity;

import Entity.Collectibles.Collectible;
import Entity.Collectibles.Items;

public class Inventory {
    private Items items[] = new Items[3];

    public boolean addToInventory(Collectible c) {
        // přiřadím do prázdného inventory slotu
        if (items[0] == null) {
            items[0] = c.getDropItem();
            return true;
        } else if (items[1] == null) {
            items[1] = c.getDropItem();
            return true;
        } else if (items[2] == null) {
            items[2] = c.getDropItem();
            return true;
        } else {
            return false; //Nelze sebrat z důvodu plného inventáře
        }
    }

}
