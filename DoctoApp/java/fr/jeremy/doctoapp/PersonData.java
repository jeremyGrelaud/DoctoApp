package fr.jeremy.doctoapp;

public class PersonData {
    private int phone;
    private String name;
    private String password;
    private String email;

    public PersonData( String name,int phone,  String email, String password){
        this.setPhone(phone);
        this.setName(name);
        this.setPassword(password);
        this.setEmail(email);
    }


    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PersonData{" +
                "phone=" + phone +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
