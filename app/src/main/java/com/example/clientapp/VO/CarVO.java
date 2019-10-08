package com.example.clientapp.VO;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CarVO implements Parcelable{
    private int car_no;
    private String car_type;
    private String tire_change_distance;
    private String wiper_change_distance;
    private String engine_oil_viscosity;
    private String distance;
    private String car_id;
    private String car_color;
    private String cooler_left;
    private int member_no;


    public CarVO() {
    }

    public CarVO(int car_no, String car_type, String tire_change_distance, String wiper_change_distance, String engine_oil_viscosity, String distance, String car_id, String car_color, String cooler_left, int member_no) {
        this.car_no = car_no;
        this.car_type = car_type;
        this.tire_change_distance = tire_change_distance;
        this.wiper_change_distance = wiper_change_distance;
        this.engine_oil_viscosity = engine_oil_viscosity;
        this.distance = distance;
        this.car_id = car_id;
        this.car_color = car_color;
        this.cooler_left = cooler_left;
        this.member_no = member_no;
    }


    public static final Parcelable.Creator<CarVO> CREATOR = new Parcelable.Creator<CarVO>() {
        @Override
        public CarVO createFromParcel(Parcel parcel) {
            // 마샬링된 데이터를 언마샬링(복원)할 때 사용되는 method
            return new CarVO(parcel);
        }

        @Override
        public CarVO[] newArray(int i) {      // 몇개 복원할거에요 숫자 : i
            return new CarVO[i];
        }
    };

    public CarVO(int member_no) {
        this.member_no = member_no;
    }


    protected CarVO(Parcel parcel) {
        car_no = parcel.readInt();
        car_type = parcel.readString();
        tire_change_distance = parcel.readString();
        wiper_change_distance = parcel.readString();
        engine_oil_viscosity = parcel.readString();
        distance = parcel.readString();
        car_id = parcel.readString();
        car_color = parcel.readString();
        cooler_left = parcel.readString();
        member_no = parcel.readInt();

    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeInt(car_no);
            parcel.writeString(car_type);
            parcel.writeString(tire_change_distance);
            parcel.writeString(wiper_change_distance);
            parcel.writeString(engine_oil_viscosity);
            parcel.writeString(distance);
            parcel.writeString(car_id);
            parcel.writeString(car_color);
            parcel.writeString(cooler_left);
            parcel.writeInt(member_no);
        } catch (Exception e) {
            Log.i("LOG", e.toString());
        }
    }


    public int getCar_no() {
        return car_no;
    }

    public void setCar_no(int car_no) {
        this.car_no = car_no;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getTire_change_distance() {
        return tire_change_distance;
    }

    public void setTire_change_distance(String tire_change_distance) {
        this.tire_change_distance = tire_change_distance;
    }

    public String getWiper_change_distance() {
        return wiper_change_distance;
    }

    public void setWiper_change_distance(String wiper_change_distance) {
        this.wiper_change_distance = wiper_change_distance;
    }

    public String getEngine_oil_viscosity() {
        return engine_oil_viscosity;
    }

    public void setEngine_oil_viscosity(String engine_oil_viscosity) {
        this.engine_oil_viscosity = engine_oil_viscosity;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getCooler_left() {
        return cooler_left;
    }

    public void setCooler_left(String cooler_left) {
        this.cooler_left = cooler_left;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }
}
