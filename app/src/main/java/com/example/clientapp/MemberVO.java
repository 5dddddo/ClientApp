package com.example.clientapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MemberVO implements Parcelable {
    private int member_no;
    private String member_id;
    private String member_pw;
    private String member_mname;
    private String member_phonenumber;


    public MemberVO() {
        super();
    }


    public MemberVO(int member_no, String member_id, String member_pw, String member_mname,
                    String member_phonenumber, int car_no) {
        super();
        this.member_no = member_no;
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_mname = member_mname;
        this.member_phonenumber = member_phonenumber;
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


}
