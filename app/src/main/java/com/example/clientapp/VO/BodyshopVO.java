package com.example.clientapp.VO;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class BodyshopVO implements Parcelable {

    private int bodyshop_no;
    private String bodyshop_id;
    private String bodyshop_pw;
    private String bodyshop_name;
    private String bodyshop_address;
    private String bodyshop_lat;
    private String bodyshop_long;




    public static Creator<BodyshopVO> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<BodyshopVO> CREATOR = new Creator<BodyshopVO>() {
        @Override
        public BodyshopVO createFromParcel(Parcel parcel) {
            // 마샬링된 데이터를 언마샬링(복원)할 때 사용되는 method
            return new BodyshopVO(parcel);
        }

        @Override
        public BodyshopVO[] newArray(int i) {      // 몇개 복원할거에요 숫자 : i
            return new BodyshopVO[i];
        }
    };

    protected BodyshopVO(Parcel parcel) {
        bodyshop_no = parcel.readInt();
        bodyshop_id = parcel.readString();
        bodyshop_pw = parcel.readString();
        bodyshop_name = parcel.readString();
        bodyshop_address = parcel.readString();
        bodyshop_lat = parcel.readString();
        bodyshop_long = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeInt(bodyshop_no);
            parcel.writeString(bodyshop_id);
            parcel.writeString(bodyshop_pw);
            parcel.writeString(bodyshop_name);
            parcel.writeString(bodyshop_address);
            parcel.writeString(bodyshop_lat);
            parcel.writeString(bodyshop_long);
        } catch (Exception e) {
            Log.i("LOG", e.toString());
        }
    }

    public BodyshopVO(){

    }


    public BodyshopVO(int bodyshop_no, String bodyshop_id, String bodyshop_pw, String bodyshop_name, String bodyshop_address, String bodyshop_lat, String bodyshop_long) {
        this.bodyshop_no = bodyshop_no;
        this.bodyshop_id = bodyshop_id;
        this.bodyshop_pw = bodyshop_pw;
        this.bodyshop_name = bodyshop_name;
        this.bodyshop_address = bodyshop_address;
        this.bodyshop_lat = bodyshop_lat;
        this.bodyshop_long = bodyshop_long;
    }

    public int getBodyshop_no() {
        return bodyshop_no;
    }

    public void setBodyshop_no(int bodyshop_no) {
        this.bodyshop_no = bodyshop_no;
    }

    public String getBodyshop_id() {
        return bodyshop_id;
    }

    public void setBodyshop_id(String bodyshop_id) {
        this.bodyshop_id = bodyshop_id;
    }

    public String getBodyshop_pw() {
        return bodyshop_pw;
    }

    public void setBodyshop_pw(String bodyshop_pw) {
        this.bodyshop_pw = bodyshop_pw;
    }

    public String getBodyshop_name() {
        return bodyshop_name;
    }

    public void setBodyshop_name(String bodyshop_name) {
        this.bodyshop_name = bodyshop_name;
    }

    public String getBodyshop_address() {
        return bodyshop_address;
    }

    public void setBodyshop_address(String bodyshop_address) {
        this.bodyshop_address = bodyshop_address;
    }

    public String getBodyshop_lat() {
        return bodyshop_lat;
    }

    public void setBodyshop_lat(String bodyshop_lat) {
        this.bodyshop_lat = bodyshop_lat;
    }

    public String getBodyshop_long() {
        return bodyshop_long;
    }

    public void setBodyshop_long(String bodyshop_long) {
        this.bodyshop_long = bodyshop_long;
    }
}
