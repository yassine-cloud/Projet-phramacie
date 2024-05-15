package fxjava.projet_pharmacie.DAO;

import fxjava.projet_pharmacie.Model.PatientMed;
import fxjava.projet_pharmacie.Utilities.LaConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoPatientMed {

    static Connection conn;

    public static ArrayList<PatientMed> getAllPatientMeds(){
        conn = LaConnection.seConnecter();
        ArrayList<PatientMed> patientMeds = new ArrayList<>();
        try {
            String sql = "SELECT * FROM patientmed";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                patientMeds.add(new PatientMed(rs.getInt("id"), rs.getInt("codePatient"), rs.getInt("code_med"), rs.getInt("qte"), rs.getDate("date"), rs.getFloat("payer")));
            }

            return patientMeds;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des patients : "+ex.getMessage());
        }
        return patientMeds;
    }

    public static ArrayList<PatientMed> getPatientMedByCodePatient(int codePatient){
        conn = LaConnection.seConnecter();
        ArrayList<PatientMed> patientMeds = new ArrayList<>();
        try {
            String sql = "SELECT * FROM patientmed WHERE codePatient = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, codePatient);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                patientMeds.add(new PatientMed(rs.getInt("id"), rs.getInt("codePatient"), rs.getInt("code_med"), rs.getInt("qte"), rs.getDate("date"), rs.getFloat("payer")));
            }
            return patientMeds;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des patients : "+ex.getMessage());
        }
        return patientMeds;
    }

    public static PatientMed getPatientMedByCode(int id){
        conn = LaConnection.seConnecter();
        PatientMed patientMed = null;
        try {
            String sql = "SELECT * FROM patientmed WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                patientMed = new PatientMed(rs.getInt("id"), rs.getInt("codePatient"), rs.getInt("code_med"), rs.getInt("qte"), rs.getDate("date"), rs.getFloat("payer"));
            }
            return patientMed;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du patient : "+ex.getMessage());
        }
        return patientMed;
    }
    public static boolean addPatientMed(PatientMed patientMed){
        conn = LaConnection.seConnecter();
        try {
            String sql = "INSERT INTO patientmed(codepatient , code_med , qte, date, payer) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, patientMed.getCodePatient());
            ps.setInt(2, patientMed.getCode_med());
            ps.setInt(3, patientMed.getQte());
            ps.setDate(4, new java.sql.Date(patientMed.getDate().getTime()));
            ps.setFloat(5, patientMed.getPayer());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du patient : "+ex.getMessage());
        }
        return false;
    }

    public static boolean updatePatientMed(PatientMed patientMed){
        conn = LaConnection.seConnecter();
        try {
            String sql = "UPDATE patientmed SET codepatient = ? , code_med = ? , qte = ?, date = ?, payer = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, patientMed.getCodePatient());
            ps.setInt(2, patientMed.getCode_med());
            ps.setInt(3, patientMed.getQte());
            ps.setDate(4, new java.sql.Date(patientMed.getDate().getTime()));
            ps.setFloat(5, patientMed.getPayer());
            ps.setInt(6, patientMed.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour du patient : "+ex.getMessage());
        }
        return false;
    }

    public static boolean deletePatientMed(int id){
        conn = LaConnection.seConnecter();
        try {
            String sql = "DELETE FROM patientmed WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du patient : "+ex.getMessage());
        }
        return false;
    }





}
