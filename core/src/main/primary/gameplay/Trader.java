package main.primary.gameplay;

import main.primary.Global;

public class Trader {

    private final Item item;
    private int pricePerItem;
    private final int itemAmount;
    private boolean hasNegotiated = false;

    public Trader() {
        item = new Item();
        itemAmount = Global.app.rand.nextInt(5) + 1;
        pricePerItem = item.getPrice();
    }

    public Item getItem() {
        return item;
    }

    public int getPricePerItem() {
        return pricePerItem;
    }

    public int getTotalPrice() {
        return pricePerItem * itemAmount;
    }

    public int getItemCount() {
        return itemAmount;
    }

    public boolean canNegotiate() {
        return !hasNegotiated;
    }

    public void negotiate(boolean merchantCheck) {
        if (hasNegotiated) {
            return;
        }
        hasNegotiated = true;

        if (merchantCheck) {
            pricePerItem = pricePerItem / 10;
        } else {
            pricePerItem = pricePerItem * 2;
        }
    }
}
