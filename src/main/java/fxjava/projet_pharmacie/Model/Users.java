package fxjava.projet_pharmacie.Model;

public class Users {

    private int id;
    private String email;
    private String password;
    private String nom_user;
    private String tel;

    public Users(int id, String email, String password, String nom_user, String tel) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nom_user = nom_user;
        this.tel = tel;
    }

    public Users(String email, String password, String nom_user, String tel) {
        this.email = email;
        this.password = password;
        this.nom_user = nom_user;
        this.tel = tel;
    }

    public Users(String email , String password){
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

@Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nom_user='" + nom_user + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id;
    }

    @Override
    public int hashCode() {
        return id;
    }


}
