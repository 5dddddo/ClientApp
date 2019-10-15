package com.example.clientapp.VO;

public class RepairedListVO {

    private int reservation_no;
    private int member_no;
    private int bodyshop_no;
    private String bodyshop_name;
    private String key;
    private String key_expire_time;
    private String reservation_time;
    private String repaired_time;
    private String repaired_person;
    private String tire;
    private String cooler;
    private String engine_oil;
    private String wiper;


    public RepairedListVO() {
    }

    public RepairedListVO(int reservation_no, int member_no, int bodyshop_no, String bodyshop_name, String key, String key_expire_time, String reservation_time, String repaired_time, String repaired_person, String tire, String cooler, String engine_oil, String wiper) {
        this.reservation_no = reservation_no;
        this.member_no = member_no;
        this.bodyshop_name = bodyshop_name;
        this.key = key;
        this.key_expire_time = key_expire_time;
        this.reservation_time = reservation_time;
        this.repaired_time = repaired_time;
        this.repaired_person = repaired_person;
        this.tire = tire;
        this.cooler = cooler;
        this.engine_oil = engine_oil;
        this.wiper = wiper;
    }

    public int getReservation_no() {
        return reservation_no;
    }

    public void setReservation_no(int reservation_no) {
        this.reservation_no = reservation_no;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public int getBodyshop_no() {
        return bodyshop_no;
    }

    public void setBodyshop_no(int bodyshop_no) {
        this.bodyshop_no = bodyshop_no;
    }

    public String getBodyshop_name() {
        return bodyshop_name;
    }

    public void setBodyshop_name(String bodyshop_name) {
        this.bodyshop_name = bodyshop_name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey_expire_time() {
        return key_expire_time;
    }

    public void setKey_expire_time(String key_expire_time) {
        this.key_expire_time = key_expire_time;
    }

    public String getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(String reservation_time) {
        this.reservation_time = reservation_time;
    }

    public String getRepaired_time() {
        return repaired_time;
    }

    public void setRepaired_time(String repaired_time) {
        this.repaired_time = repaired_time;
    }

    public String getRepaired_person() {
        return repaired_person;
    }

    public void setRepaired_person(String repaired_person) {
        this.repaired_person = repaired_person;
    }

    public String getTire() {
        return tire;
    }

    public void setTire(String tire) {
        this.tire = tire;
    }

    public String getCooler() {
        return cooler;
    }

    public void setCooler(String cooler) {
        this.cooler = cooler;
    }

    public String getEngine_oil() {
        return engine_oil;
    }

    public void setEngine_oil(String engine_oil) {
        this.engine_oil = engine_oil;
    }

    public String getWiper() {
        return wiper;
    }

    public void setWiper(String wiper) {
        this.wiper = wiper;
    }
}

