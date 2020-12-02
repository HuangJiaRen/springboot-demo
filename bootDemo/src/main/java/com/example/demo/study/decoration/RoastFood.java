package com.example.demo.study.decoration;

/**
 * @author huangli
 * @version 1.0
 * @description 烤-装饰者  烤食物
 * @date 2020-12-02 13:53
 */
public class RoastFood extends FoodDecoration {
    private Food food;

    public RoastFood(Food food) {
        this.food = food;
    }

    @Override
    public String getDesc() {
        return getDecoration() + food.getDesc();
    }

    private String getDecoration(){
        return "烤";
    }
}
