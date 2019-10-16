import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public abstract class dbUtil {
    public static ArrayList<String> myTokenizer(String str) {
        ArrayList<String> output = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(str,"|");
        while (tokenizer.hasMoreTokens()) {
            output.add(tokenizer.nextToken());
        }
        return output;
    }
    public  static int covertNumber(String str) {
        String[] dates = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
        int number = -1;
        for (int i=0;i<dates.length;i++) {
            if(str.equals(dates[i])) number = i+1;
        }
        return number;
    }
    public  static  String insertConverter(String str) {
        String[] list = str.split(";");
        String output = "";
        for(int i=0;i<list.length;i++) {
            if(i==0) output = list[i];
            else output = output +","+list[i];
        }
        System.out.println("Data: " + output);
        return  output;
    }
    public  static String[] bulkConverter() {
        Scanner scannBulk = new Scanner(System.in);
        String str = scannBulk.nextLine();
        String[] list = str.split(":");
        for (int i=0;i<list.length;i++) {
            list[i] = list[i].substring(1,list[i].length()-1);
        }
        return list;
    }
    public  static  String inputConverter(String str) {
        int begin = str.lastIndexOf('(');
        int end = str.lastIndexOf(')');
        String output = str.substring(begin+1,end);
        return output;
    }
    public  static String[] inputConverter_Bulk(String str) {
        String list[] = str.split(":");
        for (int i=0;i<list.length;i++) {
            int begin = list[i].lastIndexOf('(');
            int end = list[i].lastIndexOf(')');
            list[i] = list[i].substring(begin+1,end);
        }
        return list;
    }
    public  static void insertUtil(String type,String str) {
        try {
            main.db_CONN.setAutoCommit(false);
            switch (type) {
                case "farmer":
                    insert_Farmer.insert_F(str);
                    break;
                case "market":
                    insert_Market.insert_M(str);
                    break;
                case "product":
                    insert_Product.insert_P(str);
                    break;
                case "buys":
                    insert_Buys.insert_B(str);
                    break;
                case "registers":
                    insert_Registers.insert_R(str);
                    break;
                case "produces":
                    insert_Produces.insert_Pro(str);
                    break;
            }
            main.db_CONN.commit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            try {
                main.db_CONN.rollback();
            }
            catch (SQLException ex2) {
                ex2.printStackTrace();
            }
        }
    }
    public  static  void rsPrinter(ResultSet rs) {
        try {
            int count = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for(int i=1;i<count+1;i++) System.out.print(rs.getMetaData().getColumnName(i) + ": " + rs.getString(i) + " ");
                System.out.println("");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
