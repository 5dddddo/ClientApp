package com.example.clientapp.VO;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class NotificationVO implements Parcelable {

    private int notification_no;
    private int member_no;
    private String notification_time;
    private String title;
    private String contents;

    public NotificationVO() {
    }

    public NotificationVO(int notification_no, int member_no, String notification_time, String title, String contents) {
        super();
        this.notification_no = notification_no;
        this.member_no = member_no;
        this.notification_time = notification_time;
        this.title = title;
        this.contents = contents;
    }

    public static Creator<NotificationVO> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<NotificationVO> CREATOR = new Creator<NotificationVO>() {
        @Override
        public NotificationVO createFromParcel(Parcel parcel) {
            // 마샬링된 데이터를 언마샬링(복원)할 때 사용되는 method
            return new NotificationVO(parcel);
        }

        @Override
        public NotificationVO[] newArray(int i) {      // 몇개 복원할거에요 숫자 : i
            return new NotificationVO[i];
        }
    };

    protected NotificationVO(Parcel parcel) {
        notification_no = parcel.readInt();
        member_no = parcel.readInt();
        notification_time = parcel.readString();
        title = parcel.readString();
        contents = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeInt(notification_no);
            parcel.writeInt(member_no);
            parcel.writeString(notification_time);
            parcel.writeString(title);
            parcel.writeString(contents);
        } catch (Exception e) {
            Log.i("LOG", e.toString());
        }
    }

    public int getNotification_no() {
        return notification_no;
    }

    public void setNotification_no(int notification_no) {
        this.notification_no = notification_no;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}
