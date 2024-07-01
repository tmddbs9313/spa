package com.ohgiraffers.mapping.section02.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/* 필기. embedded 가 될 수 있는 타입을 지정할 때 사용한다. */
@Embeddable
public class Price {

    @Column(name = "reqular_price")
    private int regularPrice;

    @Column(name = "discount_rate")
    private double discountRate;

    @Column(name = "sell_price")
    private int sellPrice;

    protected Price() {}


    public Price(int regularPrice, double discountRate) {
        validateNagativePrice(regularPrice);
        validateNagativeDiscountRate(discountRate);
        this.regularPrice = regularPrice;
        this.discountRate = discountRate;
        this.sellPrice = calcSellPrice(regularPrice, discountRate);
    }

    private int calcSellPrice(int regularPrice, double discountRate) {

        return (int) (regularPrice - (regularPrice * discountRate));

    }

    private void validateNagativeDiscountRate(double discountRate) {

        if(regularPrice < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }

    }

    private void validateNagativePrice(int regularPrice) {

        if(discountRate < 0) {
            throw new IllegalArgumentException("할인율은 음수일 수 없습니다.");
        }

    }
}
