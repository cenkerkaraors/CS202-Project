import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class loadCSV {
    public static ArrayList<String> readCSV(String csvFile) {
        ArrayList<String> list = new ArrayList<String>();
        Path pathTOFile = Paths.get(csvFile);
        try (BufferedReader br = Files.newBufferedReader(pathTOFile, StandardCharsets.UTF_8)){
            String line = br.readLine();
            while (line != null) {
                String[] rows = line.split(";");
                String temp = "";
                for(int i=0;i<rows.length;i++) {

                    if( i==0) temp = rows[i];
                    else temp = temp + "," + rows[i];

                }
                list.add(temp);
                line = br.readLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void insertCSV(String csvFile) {
        try {
        main.db_CONN.setAutoCommit(false);
        ArrayList<String> list = loadCSV.readCSV(csvFile);
        switch (csvFile) {
            case "farmers.csv":
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0) insert_Farmer.insert_F(list.get(i));
                }
                break;
            case "markets.csv":
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0) insert_Market.insert_M(list.get(i));
                }
                break;
            case "products.csv":
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0) insert_Product.insert_P(list.get(i));
                }
                break;
            case "produces.csv":
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0) insert_Produces.insert_Pro(list.get(i));
                }
                break;
            case "registers.csv":
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0) insert_Registers.insert_R(list.get(i));
                }
                break;
            case "buys.csv":
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0) insert_Buys.insert_B(list.get(i));
                }
                break;
        }
        main.db_CONN.commit();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            try {
                main.db_CONN.rollback();
                System.out.println("Rolll back");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
