package Opgave8A;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MainA {
    public static void main(String[] args) {
        try{
            //OPGAVE:
            //Lav et Java-program, der anvendes JDBC. Programmet skal oprette et eksamensforsøg for en
            //given studerende for en given afvikling af en eksamen. Via programmet skal du kunne
            //indtaste data for et eksamensforsøg for én studerende. Brugeren skal selv indtaste
            //identifikation på eksamensafviklingen og på den studerende udover
            //karakteren/administrative bedømmelse. Der forventes rimelige SQL-fejlreaktioner.
            String url = "jdbc:sqlserver://DESKTOP-KQNO6AD\\SQLEXPRESS;databaseName=Aflevering;user=sa;password=CARDONDB;";
            Connection minConnection = DriverManager.getConnection(url);

            Scanner scanner = new Scanner(System.in);

            System.out.println("EksamensForsøg_ID  -  INT");
            int forsøg_id = scanner.nextInt();

            System.out.println("EksamensAfviklings_ID  -  INT");
            int afviklings_id = scanner.nextInt();

            System.out.println("Student_ID  -  INT");
            int student_id = scanner.nextInt();

            System.out.println("AdministrativBedømmelse eller karakter");
            System.out.println("'SY' 'IM' 'IA' eller -3, 0, 2, 4, 7, 10, 12");
            String input = scanner.next();

            //HVIS SIDSTE INPUT KAN CASTET TIL INT, SÅ KØRER --KARAKTER INSERT--
            String sql;

            if (isCastable(input)){
                sql = "INSERT INTO eksamensForsøg (eksamensForsøgID, eksamensAfviklingsId, studentId, karakter) VALUES (?,?,?,?)";
                //INSERT KARAKTER
            }else{
                sql = "INSERT INTO eksamensForsøg (eksamensForsøgID, eksamensAfviklingsId, studentId, administatrivBedømmelse) VALUES (?,?,?,?)";
                //INSERT administrativ_Bedømmelse
            }
            PreparedStatement prestmt = minConnection.prepareStatement(sql);
            prestmt.clearParameters();
            prestmt.setInt(1, forsøg_id);
            prestmt.setInt(2, afviklings_id);
            prestmt.setInt(3, student_id);
            if (isCastable(input)){
                int karakter = Integer.parseInt(input);
                prestmt.setInt(4, karakter);
            }else{
                prestmt.setString(4, input);
            }

            prestmt.executeUpdate();
            System.out.println("Eksamensforsøg oprettet");

            if (prestmt != null) prestmt.close();
            if (minConnection != null) prestmt.close();

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static boolean isCastable(String text) throws SQLException {
        try{
            int karakter = Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}