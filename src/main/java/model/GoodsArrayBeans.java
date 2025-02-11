package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodsArrayBeans implements Serializable {
    private List<GoodsBeans> goodsArray;

    public GoodsArrayBeans() {
        goodsArray = new ArrayList<>();
    }

    // 新しいコンストラクタを追加
    public GoodsArrayBeans(List<GoodsBeans> goodsList) {
        this.goodsArray = goodsList;
    }

    // 既存のメソッド
    public void addGoods(GoodsBeans goods) {
        goodsArray.add(goods);
    }

    public List<GoodsBeans> getGoodsArray() {
        return goodsArray;
    }

    public void setGoodsArray(List<GoodsBeans> goodsArray) {
        this.goodsArray = goodsArray;
    }
}