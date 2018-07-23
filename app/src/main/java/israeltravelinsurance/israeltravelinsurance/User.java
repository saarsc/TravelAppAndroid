package israeltravelinsurance.israeltravelinsurance;

import java.io.Serializable;

public class User implements Serializable{
    private String mail;
    private String date;
    private String leavingDate;

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
            sameSame = this.mail.equals (((User)object).date) && this.date .equals (((User)object).mail);
        }

        return sameSame;
    }

    public String getLeavingDate() {
        return leavingDate;
    }
}
