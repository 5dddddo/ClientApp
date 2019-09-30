package com.example.clientapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


// 해당 클래스의 객체가 마샬링이 가능하도록 parcelable interface를 구현
// 마샬링이 데이터 변환하는거인듯 ? 언마샬링은 반대! (마샬링된 데이터 받는쪽에서하는거)

public class CarVO implements Parcelable {

    private String RESERVE_ID;
    private String CLIENT_ID;
    private String KEY;
    private String RESERVE_TIME;
    private String KEY_EXPIRE_TIME;
    private String REPAIR_TIME;
    private String BODYSHOP_ID;

    public static final Creator<CarVO> CREATOR = new Creator<CarVO>() {
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

    public CarVO(String RESERVE_ID) {
        this.RESERVE_ID = RESERVE_ID;
    }

    public CarVO(String RESERVE_ID, String CLIENT_ID, String KEY, String RESERVE_TIME, String KEY_EXPIRE_TIME, String REPAIR_TIME, String BODYSHOP_ID) {
        this.RESERVE_ID = RESERVE_ID;
        this.CLIENT_ID = CLIENT_ID;
        this.KEY = KEY;
        this.RESERVE_TIME = RESERVE_TIME;
        this.KEY_EXPIRE_TIME = KEY_EXPIRE_TIME;
        this.REPAIR_TIME = REPAIR_TIME;
        this.BODYSHOP_ID = BODYSHOP_ID;
    }

    protected CarVO(Parcel parcel){
        RESERVE_ID = parcel.readString();
        CLIENT_ID = parcel.readString();
        KEY = parcel.readString();
        RESERVE_TIME = parcel.readString();
        KEY_EXPIRE_TIME = parcel.readString();
        REPAIR_TIME = parcel.readString();
        BODYSHOP_ID = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeString(RESERVE_ID);
            parcel.writeString(CLIENT_ID);
            parcel.writeString(KEY);
            parcel.writeString(RESERVE_TIME);
            parcel.writeString(KEY_EXPIRE_TIME);
            parcel.writeString(REPAIR_TIME);
            parcel.writeString(BODYSHOP_ID);
        } catch (Exception e){
            Log.i("LOG",e.toString());
        }
    }

    public String getRESERVE_ID() {
        return RESERVE_ID;
    }

    public void setRESERVE_ID(String RESERVE_ID) {
        this.RESERVE_ID = RESERVE_ID;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getRESERVE_TIME() {
        return RESERVE_TIME;
    }

    public void setRESERVE_TIME(String RESERVE_TIME) {
        this.RESERVE_TIME = RESERVE_TIME;
    }

    public String getKEY_EXPIRE_TIME() {
        return KEY_EXPIRE_TIME;
    }

    public void setKEY_EXPIRE_TIME(String KEY_EXPIRE_TIME) {
        this.KEY_EXPIRE_TIME = KEY_EXPIRE_TIME;
    }

    public String getREPAIR_TIME() {
        return REPAIR_TIME;
    }

    public void setREPAIR_TIME(String REPAIR_TIME) {
        this.REPAIR_TIME = REPAIR_TIME;
    }

    public String getBODYSHOP_ID() {
        return BODYSHOP_ID;
    }

    public void setBODYSHOP_ID(String BODYSHOP_ID) {
        this.BODYSHOP_ID = BODYSHOP_ID;
    }

    public static Creator<CarVO> getCREATOR() {
        return CREATOR;
    }
}