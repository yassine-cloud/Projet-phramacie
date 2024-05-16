package fxjava.projet_pharmacie.Model;

public class Medicament {

    private int code_med;
    private String nom_med;
    private Float prix_med;
    private int stock_med;
    private TypeMed type_med;

    public Medicament(String nom_med, Float prix_med, int stock_med, TypeMed type_med) {
        this.nom_med = nom_med;
        this.prix_med = prix_med;
        this.stock_med = stock_med;
        this.type_med = type_med;
    }

    public Medicament(int code_med, String nom_med, Float prix_med, int stock_med, TypeMed type_med) {
        this(nom_med, prix_med, stock_med, type_med);
        this.code_med = code_med;
    }


    public int getCode_med() {
        return code_med;
    }

    public void setCode_med(int code_med) {
        this.code_med = code_med;
    }

    public String getNom_med() {
        return nom_med;
    }

    public void setNom_med(String nom_med) {
        this.nom_med = nom_med;
    }

    public Float getPrix_med() {
        return prix_med;
    }

    public void setPrix_med(Float prix_med) {
        this.prix_med = prix_med;
    }

    public int getStock_med() {
        return stock_med;
    }

    public void setStock_med(int stock_med) {
        this.stock_med = stock_med;
    }

    public TypeMed getType_med() {
        return type_med;
    }

    public void setType_med(TypeMed type_med) {
        this.type_med = type_med;
    }

    @Override
    public String toString() {
//        return "Medicament{" +
//                "code_med=" + code_med +
//                ", nom_med='" + nom_med + '\'' +
//                ", prix_med=" + prix_med +
//                ", stock_med=" + stock_med +
//                ", type_med=" + type_med +
//                '}';
        return nom_med;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicament)) return false;
        Medicament medicament = (Medicament) o;
        return code_med == medicament.code_med;
    }

    @Override
    public int hashCode() {
        return code_med;
    }

    public int compareTo(Medicament o) {
        return nom_med.compareTo(o.nom_med);
    }
}
