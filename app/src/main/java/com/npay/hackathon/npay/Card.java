package com.npay.hackathon.npay;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016-09-08.
 */

public class Card {

    private int cardImage;
    private String cardName;
    private String cardNumber;
    private String cardValid;

    public Card(int cardImage, String cardName, String cardNumber, String cardValid) {
        this.cardImage = cardImage;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardValid = cardValid;
    }

    public int getCardImage() {
        return cardImage;
    }

    public void setCardImage(int cardImage) {
        this.cardImage = cardImage;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardValid() {
        return cardValid;
    }

    public void setCardValid(String cardValid) {
        this.cardValid = cardValid;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardImage=" + cardImage +
                ", cardName='" + cardName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardValid='" + cardValid + '\'' +
                '}';
    }
}
