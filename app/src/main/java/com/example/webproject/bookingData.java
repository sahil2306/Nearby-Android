package com.example.webproject;

public class bookingData {
    private String res_email;
    private String res_name;
    private String res_date;
    private String res_time;
    private String sr_no;
    private String res_id;

    public bookingData(String res_email, String res_name, String res_date, String res_time, String res_id, String sr_no) {
        this.res_email = res_email;
        this.res_name = res_name;
        this.res_date = res_date;
        this.res_time = res_time;
        this.res_id = res_id;
        this.sr_no = sr_no;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id){
        this.res_id = res_id;
    }

    public String getSr_no() {
        return sr_no;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = sr_no;
    }

    public String getRes_email() {
        return res_email;
    }

    public void setRes_email(String res_email) {
        this.res_email = res_email;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_date() {
        return res_date;
    }

    public void setRes_date(String res_date) {
        this.res_date = res_date;
    }

    public String getRes_time() {
        return res_time;
    }

    public void setRes_time(String res_time) {
        this.res_time = res_time;
    }
}
