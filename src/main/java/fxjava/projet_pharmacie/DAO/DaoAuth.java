package fxjava.projet_pharmacie.DAO;

import fxjava.projet_pharmacie.Model.Users;
import fxjava.projet_pharmacie.Utilities.LaConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoAuth {

  Connection conn;

  public Users getUserByEmail(String email){
      conn = LaConnection.seConnecter();
        Users user = null;
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new Users(rs.getInt("id"), rs.getString("email"), rs.getString("password"), rs.getString("nom_user"), rs.getString("tel"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : "+ex.getMessage());
        }
        return user;
  }

  public boolean addUser(Users user){
      conn = LaConnection.seConnecter();
        try {
            String sql = "INSERT INTO users(email, password, nom_user, tel) VALUES(?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNom_user());
            ps.setString(4, user.getTel());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : "+ex.getMessage());
            return false;
        }
  }

    public boolean updateUser(Users user){
        conn = LaConnection.seConnecter();
            try {
                String sql = "UPDATE users SET email = ?, password = ?, nom_user = ?, tel = ? WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getNom_user());
                ps.setString(4, user.getTel());
                ps.setInt(5, user.getId());
                return ps.executeUpdate() > 0;

            } catch (SQLException ex) {
                System.out.println("Erreur lors de la mise à jour de l'utilisateur : "+ex.getMessage());
                return false;
            }
    }

    public boolean deleteUser(int id){
        conn = LaConnection.seConnecter();
            try {
                String sql = "DELETE FROM users WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;

            } catch (SQLException ex) {
                System.out.println("Erreur lors de la suppression de l'utilisateur : "+ex.getMessage());
                return false;
            }
    }




}