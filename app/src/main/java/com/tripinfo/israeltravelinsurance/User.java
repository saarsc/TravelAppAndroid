package com.tripinfo.israeltravelinsurance;

public class User {
    private String mail;
    private String date;

    public User(String mail, String date) {
        this.mail = mail;
        this.date = date;
    }

    public String getMail() {
        return mail;
    }

    public User() {
    }

    public void setMail(String mail) {
        this.mail = mail;

    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object  != null && object  instanceof User)
        {
            sameSame = this.mail.equals (((User)object).mail) && this.date .equals (((User)object).date);
        }

        return sameSame;
    }
}
