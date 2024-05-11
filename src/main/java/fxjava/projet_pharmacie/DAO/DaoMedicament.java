package fxjava.projet_pharmacie.DAO;

import fxjava.projet_pharmacie.Model.Medicament;
import fxjava.projet_pharmacie.Model.TypeMed;
import fxjava.projet_pharmacie.Utilities.LaConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoMedicament {

    Connection conn;

    public ArrayList<Medicament> getAllMedicaments(){
        conn = LaConnection.seConnecter();
        ArrayList<Medicament> medicaments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM medicament";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                medicaments.add(new Medicament(rs.getInt("code_med"), rs.getString("nom_med"), rs.getFloat("prix_med"), rs.getInt("stock_med"), TypeMed.valueOf(rs.getString("type_med"))));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des médicaments : "+ex.getMessage());
        }
        return medicaments;
    }

    public Medicament getMedicamentByCode(int code_med){
        conn = LaConnection.seConnecter();
        Medicament medicament = null;
        try {
            String sql = "SELECT * FROM medicament WHERE code_med = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, code_med);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                medicament = new Medicament(rs.getInt("code_med"), rs.getString("nom_med"), rs.getFloat("prix_med"), rs.getInt("stock_med"), TypeMed.valueOf(rs.getString("type_med")));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du médicament : "+ex.getMessage());
        }
        return medicament;
    }

    public boolean addMedicament(Medicament medicament){
        conn = LaConnection.seConnecter();
        try {
            String sql = "INSERT INTO medicament(nom_med, prix_med, stock_med, type_med) VALUES(?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, medicament.getNom_med());
            ps.setFloat(2, medicament.getPrix_med());
            ps.setInt(3, medicament.getStock_med());
            ps.setString(4, medicament.getType_med().name());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du médicament : "+ex.getMessage());
            return false;
        }
    }

    public boolean updateMedicament(Medicament medicament){
        conn = LaConnection.seConnecter();
        try {
            String sql = "UPDATE medicament SET nom_med = ?, prix_med = ?, stock_med = ?, type_med = ? WHERE code_med = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, medicament.getNom_med());
            ps.setFloat(2, medicament.getPrix_med());
            ps.setInt(3, medicament.getStock_med());
            ps.setString(4, medicament.getType_med().name());
            ps.setInt(5, medicament.getCode_med());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du médicament : "+ex.getMessage());
            return false;
        }
    }

    public boolean deleteMedicament(int code_med){
        conn = LaConnection.seConnecter();
        try {
            String sql = "DELETE FROM medicament WHERE code_med = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, code_med);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du médicament : "+ex.getMessage());
            return false;
        }
    }
}
