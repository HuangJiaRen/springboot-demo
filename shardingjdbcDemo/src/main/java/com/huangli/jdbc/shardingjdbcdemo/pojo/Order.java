package com.huangli.jdbc.shardingjdbcdemo.pojo;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 */

@Entity
@Table(name="t_order")
public class Order {
    @Id
    private Long orderId;

    private Long userId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
