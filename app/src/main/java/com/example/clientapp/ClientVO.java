package com.example.clientapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ClientVO implements Parcelable {
    private String CLIENT_NUM;
    private String CLIENT_NAME;
    private String CLIENT_ID;
    private String PASSWORD;
    private String CAR_ID;
    private String CAR_TYPE;
    private String TEL;

    public static final Creator<ClientVO> CREATOR = new Creator<ClientVO>() {
        @Override
        public ClientVO createFromParcel(Parcel parcel) {
            // 마샬링된 데이터를 언마샬링(복원)할 때 사용되는 method
            return new ClientVO(parcel);
        }

        @Override
        public ClientVO[] newArray(int i) {      // 몇개 복원할거에요 숫자 : i
            return new ClientVO[i];
        }
    };

    public ClientVO() {

    }

    public ClientVO(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public ClientVO(String CLIENT_NUM, String CLIENT_NAME, String CLIENT_ID, String PASSWORD, String CAR_ID, String CAR_TYPE, String TEL) {
        this.CLIENT_NUM = CLIENT_NUM;
        this.CLIENT_NAME = CLIENT_NAME;
        this.CLIENT_ID = CLIENT_ID;
        this.PASSWORD = PASSWORD;
        this.CAR_ID = CAR_ID;
        this.CAR_TYPE = CAR_TYPE;
        this.TEL = TEL;
    }

    protected ClientVO(Parcel parcel) {
        CLIENT_ID = parcel.readString();
        CLIENT_NUM = parcel.readString();
        CLIENT_NAME = parcel.readString();
        CAR_TYPE = parcel.readString();
        CAR_ID = parcel.readString();
        TEL = parcel.readString();
        PASSWORD = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try {

            parcel.writeString(CLIENT_ID);
            parcel.writeString(CLIENT_NUM);
            parcel.writeString(CLIENT_NAME);
            parcel.writeString(CAR_TYPE);
            parcel.writeString(CAR_ID);
            parcel.writeString(TEL);
            parcel.writeString(PASSWORD);
        } catch (Exception e) {
            Log.i("LOG", e.toString());
        }
    }

    public String getCLIENT_NUM() {
        return CLIENT_NUM;
    }

    public void setCLIENT_NUM(String CLIENT_NUM) {
        this.CLIENT_NUM = CLIENT_NUM;
    }

    public String getCLIENT_NAME() {
        return CLIENT_NAME;
    }

    public void setCLIENT_NAME(String CLIENT_NAME) {
        this.CLIENT_NAME = CLIENT_NAME;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getCAR_ID() {
        return CAR_ID;
    }

    public void setCAR_ID(String CAR_ID) {
        this.CAR_ID = CAR_ID;
    }

    public String getCAR_TYPE() {
        return CAR_TYPE;
    }

    public void setCAR_TYPE(String CAR_TYPE) {
        this.CAR_TYPE = CAR_TYPE;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }
}
