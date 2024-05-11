package fxjava.projet_pharmacie.DAO;

import fxjava.projet_pharmacie.Model.Patient;
import fxjava.projet_pharmacie.Utilities.LaConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoPatient {

    Connection conn;

    public ArrayList<Patient> getAllPatients(){
        conn = LaConnection.seConnecter();
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            String sql = "SELECT * FROM patient";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                patients.add(new Patient(rs.getInt("codePatient"), rs.getString("nomPatient"), rs.getString("telPatient"), rs.getString("emailPatient"), rs.getDate("date_nais")));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des patients : "+ex.getMessage());
        }
        return patients;
    }

    public Patient getPatientByCode(int codePatient){
        conn = LaConnection.seConnecter();
        Patient patient = null;
        try {
            String sql = "SELECT * FROM patient WHERE codePatient = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, codePatient);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                patient = new Patient(rs.getInt("codePatient"), rs.getString("nomPatient"), rs.getString("telPatient"), rs.getString("emailPatient"), rs.getDate("date_nais"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du patient : "+ex.getMessage());
        }
        return patient;
    }

    public boolean addPatient(Patient patient){
        conn = LaConnection.seConnecter();
        try {
            String sql = "INSERT INTO patient(nomPatient, telPatient, emailPatient, date_nais) VALUES(?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, patient.getNomPatient());
            ps.setString(2, patient.getTelPatient());
            ps.setString(3, patient.getEmailPatient());
            ps.setDate(4, new java.sql.Date(patient.getDate_nais().getTime()));
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du patient : "+ex.getMessage());
            return false;
        }
    }

    public boolean updatePatient(Patient patient){
        conn = LaConnection.seConnecter();
        try {
            String sql = "UPDATE patient SET nomPatient = ?, telPatient = ?, emailPatient = ?, date_nais = ? WHERE codePatient = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, patient.getNomPatient());
            ps.setString(2, patient.getTelPatient());
            ps.setString(3, patient.getEmailPatient());
            ps.setDate(4, new java.sql.Date(patient.getDate_nais().getTime()));
            ps.setInt(5, patient.getCodePatient());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du patient : "+ex.getMessage());
            return false;
        }
    }

    public boolean deletePatient(int codePatient){
        conn = LaConnection.seConnecter();
        try {
            String sql = "DELETE FROM patient WHERE codePatient = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, codePatient);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du patient : "+ex.getMessage());
            return false;
        }
    }


}
