package model;

import java.io.Serializable;
import java.util.ArrayList;

public class GoodsArrayBeans implements Serializable {
    private ArrayList<GoodsBeans> goodsArray;

    public GoodsArrayBeans() {
        goodsArray = new ArrayList<>();
    }

    public void addGoods(GoodsBeans goods) {
        goodsArray.add(goods);
    }

    public int getArraySize() {
        return goodsArray.size();
    }

    public ArrayList<GoodsBeans> getGoodsArray() {
        return goodsArray;
    }

    public void setGoodsArray(ArrayList<GoodsBeans> goodsArray) {
        this.goodsArray = goodsArray;
    }
}


