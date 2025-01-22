package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StoreArrayBeans {
    private List<StoreBeans> stores;

    public StoreArrayBeans() {
        this.stores = new ArrayList<>();
    }

    public void addStore(StoreBeans store) {
        this.stores.add(store);
    }

    public List<StoreBeans> getStores() {
        return stores;
    }

    public void setStores(List<StoreBeans> stores) {
        this.stores = stores;
    }

    public void sortByNameAsc() {
        stores.sort(Comparator.comparing(StoreBeans::getStoreName));
    }

    public void sortByNameDesc() {
        stores.sort(Comparator.comparing(StoreBeans::getStoreName).reversed());
    }

    public void sortByDistance() {
        stores.sort(Comparator.comparing(StoreBeans::getDistance));
    }

    public void sortByOpeningTimeAsc() {
        stores.sort(Comparator.comparing(StoreBeans::getOpeningTime));
    }

    public void sortByClosingTimeDesc() {
        stores.sort(Comparator.comparing(StoreBeans::getClosingTime).reversed());
    }
}

