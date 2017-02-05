package com.likoil.likoilbonus.mvp.network;

import java.util.Date;
import java.util.List;

/**
 * Created by jambi on 05.02.2017.
 */

public class WithdrawalsData {


    List<WithdrawalItem> withdrawals;

    public List<WithdrawalItem> getWithdrawals() {
        return withdrawals;
    }

    public static class WithdrawalItem {

        String type;
        String azs;
        String amount;
        Date use_at;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAzs() {
            return azs;
        }

        public void setAzs(String azs) {
            this.azs = azs;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Date getUse_at() {
            return use_at;
        }

        public void setUse_at(Date use_at) {
            this.use_at = use_at;
        }
    }
}
