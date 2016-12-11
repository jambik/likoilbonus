package com.likoil.likoilbonus.mvp.network;

/**
 * Created by jambi on 11.12.2016.
 */

public class StatusData {
    String bonus;
    StatusDataInfo info;

    public String getBonus() {
        return bonus;
    }

    public StatusDataInfo getInfo() {
        return info;
    }

    public class StatusDataInfo {
        String last_name;
        String patronymic;
        String name;

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
