package com.likoil.likoilbonus.mvp.network;

import java.util.Date;
import java.util.List;

/**
 * Created by jambi on 11.12.2016.
 */

public class DiscountData {

    List<DiscountItem> discounts;

    public List<DiscountItem> getDiscounts() {
        return discounts;
    }

    public static class DiscountItem {

        String amount;
        String volume;
        String fuel_name;
        String azs;
        String point;
        Date date;

        public String getAmount() {
            return amount;
        }

        public String getVolume() {
            return volume;
        }

        public String getFuel_name() {
            return fuel_name;
        }

        public String getAzs() {
            return azs;
        }

        public String getPoint() {
            return point;
        }

        public Date getDate() {
            return date;
        }
    }
}
