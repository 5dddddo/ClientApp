package com.example.clientapp.VO;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MemberVO implements Parcelable {
    private int member_no;
    private String member_id;
    private String member_pw;
    private String member_mname;
    private String member_phonenumber;
    private int car_no;
    private String car_id;
    private String car_type;
    private String car_color;
    private String code;


    public MemberVO() {
        super();
    }


    public MemberVO(int member_no, String member_id, String member_pw, String member_mname,
                    String member_phonenumber, int car_no, String car_type, String car_id, String car_color) {
        super();
        this.member_no = member_no;
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_mname = member_mname;
        this.member_phonenumber = member_phonenumber;
        this.car_no = car_no;
        this.car_type = car_type;
        this.car_id = car_id;
        this.car_color = car_color;
    }

    public static final Creator<MemberVO> CREATOR = new Creator<MemberVO>() {
        @Override
        public MemberVO createFromParcel(Parcel parcel) {
            // 마샬링된 데이터를 언마샬링(복원)할 때 사용되는 method
            return new MemberVO(parcel);
        }

        @Override
        public MemberVO[] newArray(int i) {      // 몇개 복원할거에요 숫자 : i
            return new MemberVO[i];
        }
    };

    public MemberVO(int member_no) {
        this.member_no = member_no;
    }


    protected MemberVO(Parcel parcel) {
        member_no = parcel.readInt();
        member_id = parcel.readString();
        member_pw = parcel.readString();
        member_mname = parcel.readString();
        member_phonenumber = parcel.readString();
        car_no = parcel.readInt();
        car_type = parcel.readString();
        car_id = parcel.readString();
        car_color = parcel.readString();
        code = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeInt(member_no);
            parcel.writeString(member_id);
            parcel.writeString(member_pw);
            parcel.writeString(member_mname);
            parcel.writeString(member_phonenumber);
            parcel.writeInt(car_no);
            parcel.writeString(car_type);
            parcel.writeString(car_id);
            parcel.writeString(car_color);
            parcel.writeString(code);
        } catch (Exception e) {
            Log.i("LOG", e.toString());
        }
    }

    public static Creator<MemberVO> getCREATOR() {
        return CREATOR;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_pw() {
        return member_pw;
    }

    public void setMember_pw(String member_pw) {
        this.member_pw = member_pw;
    }

    public String getMember_mname() {
        return member_mname;
    }

    public void setMember_mname(String member_mname) {
        this.member_mname = member_mname;
    }

    public String getMember_phonenumber() {
        return member_phonenumber;
    }

    public void setMember_phonenumber(String member_phonenumber) {
        this.member_phonenumber = member_phonenumber;
    }

    public int getCar_no() {
        return car_no;
    }

    public void setCar_no(int car_no) {
        this.car_no = car_no;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
