package com.example.demo.study.decoration;

/**
 * @author huangli
 * @version 1.0
 * @description 蒸-装饰者 蒸食物
 * @date 2020-12-02 13:50
 */
public class SteamedFood extends FoodDecoration {
    private Food food;

    public SteamedFood(Food food) {
        this.food = food;
    }

    @Override
    public String getDesc() {
        return getDecoration() + food.getDesc();
    }

    private String getDecoration(){
        return "蒸";
    }
}
