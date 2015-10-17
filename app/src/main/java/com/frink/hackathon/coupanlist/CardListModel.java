package com.frink.hackathon.coupanlist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shashwatsinha on 16/10/15.
 */
public class CardListModel implements Serializable {

    ArrayList<CardModel> coupons;

    public ArrayList<CardModel> getCoupans() {
        return coupons;
    }


    public static class CardModel {
        String title;
        String expiry;
        String term_cond;
        String coupon_company;


        public CardModel(String title, String expiry, String term_cond, String coupan_company) {
            this.title = title;
            this.expiry = expiry;
            this.term_cond = term_cond;
            this.coupon_company = coupan_company;
        }

        @Override
        public String toString() {
            String string = title + expiry + term_cond + coupon_company;

            return string;
        }

        String getTitle() {
            return title;
        }

        String getExpiry() {
            return expiry;
        }

        String getTerm_cond() {
            return term_cond;
        }

        String getCoupon_company() {
            return coupon_company;
        }
    }
}
