package psbd.models;

import psbd.utils.UserEnum;

public class User {

    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private String phoneNumber;
    private String pesel;
    private String company;
    private UserEnum type;

    public User(UserEnum type, String login, String password, String name, String surname, String email, String pesel, String phoneNumber, String company)

    {
        this.type = type;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.company = company;
    }

    public UserEnum getType() {
        return type;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getPesel() {
        return pesel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
