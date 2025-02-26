package Opgave8C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class MainC {
    public static void main(String[] args) {
        try {
            System.out.println("Henter navn, id og karakter på alle sturderende, der har taget den givne eksamen i den givne termin ");
            BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Intast Eksamen: ");
            String eksamen = inLine.readLine();
            System.out.println("Intast Termin: ");
            String termin = inLine.readLine();
            Connection minConnection;
            minConnection = DriverManager.getConnection(
                    "jdbc:sqlserver://LocalHost\\SQLExpress;databaseName=obligatoriskDaos;user=sa;password=6640;");
            String sql = "select s.navn, s.studentId, ef.karakter, e.navn, ea.termin\n" +
                    "from Studerende s\n" +
                    "inner join eksamensForsøg ef\n" +
                    "on s.studentId = ef.studentId\n" +
                    "inner join EksamensAfvikling ea\n" +
                    "on ea.eksamensAfviklingsId = ef.eksamensAfviklingsId\n" +
                    "inner join Eksamen e\n" +
                    "on e.eksamensId = ea.eksamensId\n" +
                    "where e.navn = ? and ea.termin = ?";// preparedStatement
            PreparedStatement prestmt = minConnection.prepareStatement(sql);
            prestmt.setString(1, eksamen);
            prestmt.setString(2, termin);
            ResultSet res = prestmt.executeQuery();// Executer preparedStatement og henter et resultset i variablen res
            while (res.next()) { //Itererer gennem res vha next
                System.out.println(res.getString(1) + ", " + res.getString(2) + ", " + res.getString(3));
            }
            System.out.println("Data hentet");
            if (prestmt != null)
                prestmt.close();
            if (minConnection != null)
                minConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }
    }
}
