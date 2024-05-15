package fxjava.projet_pharmacie.Model;

import java.util.Date;
import java.util.Objects;

public class PatientMed {

    private int id;
    private int codePatient;
    private int code_med;
    private int qte;
    private Date date;
    private float payer;

    public PatientMed(){}

    public PatientMed(int codePatient, int code_med, int qte, Date date, float payer) {
        this.codePatient = codePatient;
        this.code_med = code_med;
        this.qte = qte;
        this.date = date;
        this.payer = payer;
    }

    public PatientMed(int id, int codePatient, int code_med, int qte, Date date, float payer) {
        this(codePatient, code_med, qte, date, payer);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodePatient() {
        return codePatient;
    }

    public void setCodePatient(int codePatient) {
        this.codePatient = codePatient;
    }

    public int getCode_med() {
        return code_med;
    }

    public void setCode_med(int code_med) {
        this.code_med = code_med;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPayer() {
        return payer;
    }

    public void setPayer(float payer) {
        this.payer = payer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientMed that = (PatientMed) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "PatientMed{" +
                "id=" + id +
                ", codePatient=" + codePatient +
                ", code_med=" + code_med +
                ", qte=" + qte +
                ", date=" + date +
                ", payer=" + payer +
                '}';
    }
}
