package com.blps_lab1.demo.DTO;

import com.blps_lab1.demo.beans.PaymentType;

import java.util.Date;
import java.util.List;

public class PaymentDTO {
    private List<Long> products_ids;
    private Date date;
    private PaymentType paymentType;
    private Long price;

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public List<Long> getProducts_ids() {
        return products_ids;
    }


    public Date getDate() {
        return date;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setProducts_ids(List<Long> products_ids) {
        this.products_ids = products_ids;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
