package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoPatient;
import fxjava.projet_pharmacie.DAO.DaoPatientMed;
import fxjava.projet_pharmacie.Model.PatientMed;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Dashboard implements Initializable {

    private final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @FXML
    private LineChart<?, ?> chart;

    @FXML
    private Label refLastMonth;

    @FXML
    private Label refMoy;

    @FXML
    private Button buttonDashboard;

    @FXML
    private Button buttonLogout;

    @FXML
    private Button buttonMedicament;

    @FXML
    private Button buttonPatient;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createChart();
//        buttonDashboard.setOnAction(event -> handleDashboard());
        buttonLogout.setOnAction(event -> handleLogout());
        buttonMedicament.setOnAction(event -> handleMedicament());
        buttonPatient.setOnAction(event -> handlePatient());


    }

    public void createChart(){
        XYChart.Series series = new XYChart.Series();
        series.setName("Revenue");

        ArrayList<PatientMed> patientMeds = DaoPatientMed.getAllPatientMeds();
        List<PatientMed> sortedPatientMeds = patientMeds.stream()
                .sorted(Comparator.comparing(PatientMed::getDate).reversed())
                .collect(Collectors.toList());

        // Get the first element which is the latest date
                PatientMed latestPatientMed = sortedPatientMeds.get(0);

        // Get the month from the date
                Calendar cal = Calendar.getInstance();
                cal.setTime(latestPatientMed.getDate());
                int lastMonth = cal.get(Calendar.MONTH) + 1;
                int lastYear = cal.get(Calendar.YEAR);

                cal.add(Calendar.MONTH, -11);
                int firstMonth = cal.get(Calendar.MONTH) + 1;
                int firstYear = cal.get(Calendar.YEAR);
//                System.out.println("begin "+firstMonth +"/"+firstYear + " end "+lastMonth +"/"+lastYear);

                Calendar calTest = Calendar.getInstance();

                float moyenn = 0f;
                float revenue11 = 0f;
                float revenue12 = 0f;
                for(int i= 1; i<=12; i++){
                    float revenue = 0f;
                    int monthRev = cal.get(Calendar.MONTH) + 1;
                    int yearRev = cal.get(Calendar.YEAR);
                    for(PatientMed PatientMed : patientMeds){
                        calTest.setTime(PatientMed.getDate());
                        int month = calTest.get(Calendar.MONTH) + 1;
                        int year = calTest.get(Calendar.YEAR);
                        if(month == monthRev && year == yearRev){
                            revenue += PatientMed.getPayer();
                        }
                    }
                    series.getData().add(new XYChart.Data((months[monthRev-1]+"/"+yearRev), revenue));
                    if(i == 11){
                        revenue11 = revenue;
                    }
                    if(i == 12){
                        revenue12 = revenue;
                    }
                    moyenn += revenue;
//                    System.out.println("Month = " + months[monthRev-1] + " Revenue = " + revenue);
//                    if (cal.get(Calendar.MONTH) == (lastMonth-1) && cal.get(Calendar.YEAR) == lastYear)
//                        break;
                    cal.add(Calendar.MONTH, 1);
                }
                moyenn = moyenn/12;
                if(revenue12 > revenue11){
                    refLastMonth.setStyle("-fx-text-fill: green");
                    float difference = revenue12 - revenue11;
                    String formattedDifference = String.format("%.3f", difference);
                    refLastMonth.setText("+" + formattedDifference + " Dinar");
                }else {
                    refLastMonth.setStyle("-fx-text-fill: red");
                    float difference = revenue11 - revenue12;
                    String formattedDifference = String.format("%.3f", difference);
                    refLastMonth.setText("-" + formattedDifference + " Dinar");
                }
                if(revenue12 > moyenn){
                    refMoy.setStyle("-fx-text-fill: green");
                    float difference = revenue12-moyenn;
                    String formattedDifference = String.format("%.3f", difference);
                    refMoy.setText("+" + formattedDifference + " Dinar");
                }else {
                    refMoy.setStyle("-fx-text-fill: red");
                    float difference = moyenn-revenue12 ;
                    String formattedDifference = String.format("%.3f", difference);
                    refMoy.setText("+" + formattedDifference + " Dinar");
                }





        chart.getData().add(series);
    }


    private void handleDashboard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Dashboard.fxml"));
        try {
            // Set the new scene
            Stage stage = (Stage) buttonDashboard.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogout() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Login.fxml"));
        try {
            // Set the new scene
            Stage stage = (Stage) buttonLogout.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMedicament() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Medicament.fxml"));
        try {
            // Set the new scene
            Stage stage = (Stage) buttonMedicament.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePatient() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Patient.fxml"));
        try {
            // Set the new scene
            Stage stage = (Stage) buttonPatient.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
