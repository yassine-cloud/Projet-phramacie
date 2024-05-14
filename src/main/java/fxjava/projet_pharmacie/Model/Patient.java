package fxjava.projet_pharmacie.Model;

import java.util.Date;

public class Patient {

    private int codePatient;
    private String nomPatient;
    private String telPatient;
    private String emailPatient;
    private Date date_nais;


    public Patient() {
    }
    public Patient(String nomPatient, String telPatient, String emailPatient, Date date_nais) {
        this.nomPatient = nomPatient;
        this.telPatient = telPatient;
        this.emailPatient = emailPatient;
        this.date_nais = date_nais;
    }

    public Patient(int codePatient, String nomPatient, String telPatient, String emailPatient, Date date_nais) {
        this(nomPatient, telPatient, emailPatient, date_nais);
        this.codePatient = codePatient;
    }

    public int getCodePatient() {
        return codePatient;
    }

    public void setCodePatient(int codePatient) {
        this.codePatient = codePatient;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getTelPatient() {
        return telPatient;
    }

    public void setTelPatient(String telPatient) {
        this.telPatient = telPatient;
    }

    public String getEmailPatient() {
        return emailPatient;
    }

    public void setEmailPatient(String emailPatient) {
        this.emailPatient = emailPatient;
    }

    public Date getDate_nais() {
        return date_nais;
    }

    public void setDate_nais(Date date_nais) {
        this.date_nais = date_nais;
    }

    @Override
    public String toString() {
        return "Patient{" + "codePatient=" + codePatient + ", nomPatient=" + nomPatient + ", telPatient=" + telPatient + ", emailPatient=" + emailPatient + ", date_nais=" + date_nais + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Patient)
            return this.codePatient == ((Patient)obj).codePatient;
        return false;
    }

    @Override
    public int hashCode() {
        return this.codePatient;
    }

    public int compareTo(Patient p) {
        return this.nomPatient.compareTo(p.nomPatient);
    }
}
