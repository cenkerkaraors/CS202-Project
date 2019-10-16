import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class main {
    public static Connection db_CONN = null;
    public static DatabaseMetaData dm = null;
    public static void main(String[] args) {
        System.out.println("Hello");
        try {
            // URL to connect
            String dbURL = "jdbc:sqlserver://localhost:<Enter Yours>;databaseName=hw2DB;integratedSecurity=true";
            // Connecting Data Base
            db_CONN = DriverManager.getConnection(dbURL);
            System.out.println(db_CONN);
            if(db_CONN != null) {
                dm = (DatabaseMetaData) db_CONN.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                System.out.println("Connected");
                System.out.println("--------");


                String website = "INSERT INTO Website (wid) " + "VALUES (300)";
                PreparedStatement web = db_CONN.prepareStatement(website);
                web.executeUpdate();
                main_loop(db_CONN);

            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();

        }
        finally {
            try {
                if (db_CONN != null && !db_CONN.isClosed()) {
                    db_CONN.close();
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Terminated");
        }
        //show table
        public static ResultSet showTables(DatabaseMetaData dm) {
            try {
                ResultSet tables = dm.getTables(null,"dbo","%",null);
                System.out.println("Tables: ");
                while(tables.next()) {
                    System.out.println(tables.getString(3) + ":");
                    String query = "Select *\n" +
                            "From " + tables.getString(3);
                    PreparedStatement stmt = db_CONN.prepareStatement(query);
                    ResultSet rSet = stmt.executeQuery();
                    int count = rSet.getMetaData().getColumnCount();
                    int[] displaySize = new int [count];
                    for(int i=0;i<count;i++) {
                        if(i == 0) System.out.print("|");
                        int size = rSet.getMetaData().getColumnDisplaySize(i+1);
                        displaySize[i] = size;
                        String output1 = rSet.getMetaData().getColumnName(i+1);
                        int maxSize = displaySize[i] - output1.length();
                        for (int k=0;k<maxSize;k++) output1 += " ";
                        System.out.print(output1 + "|");

                    }
                    while (rSet.next()) {
                        System.out.println("");
                        System.out.print("|");
                        for (int i=1;i<count+1;i++){
                            String output = rSet.getString(i);
                            int length = displaySize[i-1] - output.length();
                            for(int j=0;j<length;j++) output += " ";
                            System.out.print(output + "|");
                        }
                    }
                    System.out.println("");
                    for (int i=0;i<count;i++) {
                        for (int k=0;k<displaySize[i];k++) System.out.print("-");
                    }
                    System.out.println("");
                }
                return tables;
            }
            catch (SQLException ex) {
                ex.printStackTrace();;
                return null;
            }
        }
    public static String get_data_from_commands(String second_command) {
        if (second_command==null) {
            System.err.println("Wrong command!");
            usage();
            return null;
        }
        int start = second_command.lastIndexOf('(');
        int end = second_command.lastIndexOf(')');

        if (start == -1 || end == -1) {
            System.err.println("Wrong command!");
            usage();
            return null;
        }

        return second_command.substring(start+1, end);
    }
    public static void parse_commands(String buffer) {
        try {
        if (buffer == null) {
            System.err.println("There is nothing to parse! Something is wrong in the commands.");
            return;
        }

        String[] subCommands = buffer.split(" ");

            if (subCommands.length < 2) {
            System.err.println("Wrong command!");
            usage();
            return;
        }

        String first_part = subCommands[0];
        String second_part = subCommands.length != 2 ? subCommands[1] + subCommands[2] :
                subCommands[1];

        if (first_part.equalsIgnoreCase("show")) {
            if (second_part.equalsIgnoreCase("tables")) {
                ResultSet tables = showTables(dm);
            } else {
                System.err.println("Wrong command!");
                usage();
            }

        } else if (first_part.equalsIgnoreCase("load")) {
            if (second_part.equalsIgnoreCase("data")) {
                loadCSV.insertCSV("farmers.csv");
                loadCSV.insertCSV("markets.csv");
                loadCSV.insertCSV("products.csv");
                loadCSV.insertCSV("produces.csv");
                loadCSV.insertCSV("registers.csv");
                loadCSV.insertCSV("buys.csv");
                System.out.println("Done");
            } else {
                System.err.println("Wrong command!");
                usage();
            }

        } else if (first_part.equalsIgnoreCase("query")) {
            int query_number = Integer.parseInt(second_part);
            switch (query_number) {
                case 1:
                    System.out.println("Query1");
                    String query1 = "SELECT Product.pname,Farmer.fname,Farmer.last_name\n" +
                            "FROM\n" +
                            "(SELECT p.pid, MAX(P.quantity) as maximum\n" +
                            "FROM Produces P\n" +
                            "GROUP BY P.pid)  as table1, Produces table2\n" +
                            "JOIN Farmer ON table2.fid = Farmer.fid\n" +
                            "JOIN Product ON table2.pid = Product.pid\n" +
                            "WHERE table1.pid = table2.pid AND table1.maximum = table2.quantity";
                    PreparedStatement q1 = db_CONN.prepareStatement(query1);
                    ResultSet rs1 = q1.executeQuery();
                    dbUtil.rsPrinter(rs1);
                    break;
                case 2:
                    System.out.println("Query2");
                    String query2 = "SELECT P.pname, F.fname, F.last_name\n" +
                            "FROM\n" +
                            "(SELECT table1.pid, Max(table1.sumP) as maxP\n" +
                            "FROM\n" +
                            "(SELECT W.pid, W.fid, SUM(W.quantity) as sumP\n" +
                            "FROM Website_List W\n" +
                            "GROUP BY W.pid, W.fid) as table1\n" +
                            "GROUP BY table1.pid) as maxTable,\n" +
                            "(SELECT W.pid, W.fid, SUM(W.quantity) as sumP\n" +
                            "FROM Website_List W\n" +
                            "GROUP BY W.pid, W.fid) as sumTable\n" +
                            "JOIN Farmer F ON sumTable.fid = F.fid\n" +
                            "JOIN Product P ON sumTable.pid = P.pid\n" +
                            "WHERE sumTable.sumP = maxTable.maxP AND sumTable.pid = maxTable.pid";
                    PreparedStatement q2 = db_CONN.prepareStatement(query2);
                    ResultSet rs2 = q2.executeQuery();
                    dbUtil.rsPrinter(rs2);
                    break;
                case 3:
                    System.out.println("Query3");
                    String query3 = "SELECT F2.fname, F2.last_name\n" +
                            "FROM\n" +
                            "(SELECT TOP 1 W.fid, SUM(B.amount*B.price) as sumM\n" +
                            "FROM Buys_Product B, Website_List W\n" +
                            "WHERE B.rid = W.rid\n" +
                            "GROUP BY W.fid\n" +
                            "ORDER BY SUM(B.amount*B.price) DESC) as table1,\n" +
                            "Farmer F2\n" +
                            "WHERE F2.fid = table1.fid";
                    PreparedStatement q3 = db_CONN.prepareStatement(query3);
                    ResultSet rs3 = q3.executeQuery();
                    dbUtil.rsPrinter(rs3);
                    break;
                case 4:
                    System.out.println("Query4");
                    String query4 = "SELECT table1.city,M2.mname\n" +
                            "FROM\n" +
                            "(SELECT L2.city, MAX(M.budget) as maxBudget\n" +
                            "FROM Local_Market M\n" +
                            "JOIN Local_Market_AZ L1 on M.maddress = L1.maddress\n" +
                            "JOIN Local_Market_ZC L2 on L1.zipcode = L2.zipcode\n" +
                            "GROUP BY L2.city) table1, Local_Market M2\n" +
                            "JOIN Local_Market_AZ L3 on M2.maddress = L3.maddress\n" +
                            "JOIN Local_Market_ZC L4 on L3.zipcode = L4.zipcode\n" +
                            "WHERE table1.maxBudget = M2.budget AND table1.city = L4.city";
                    PreparedStatement q4 = db_CONN.prepareStatement(query4);
                    ResultSet rs4 = q4.executeQuery();
                    dbUtil.rsPrinter(rs4);
                    break;
                case 5:
                    System.out.println("Query5");
                    String query5 = "SELECT (COUNT(DISTINCT W.fid) + COUNT(DISTINCT B.mid)) as totalUsers\n" +
                            "FROM Website_List W,Buys_Product B";
                    String query5_2 = "SELECT (COUNT(DISTINCT F.fid) + COUNT(DISTINCT M.mid)) as totalUsers\n" +"FROM Local_Market M, Farmer F";
                    PreparedStatement q5 = db_CONN.prepareStatement(query5_2);
                    ResultSet rs5 = q5.executeQuery();
                    dbUtil.rsPrinter(rs5);
                    break;
            }

        } else if (first_part.equalsIgnoreCase("add")) {
            if (second_part.startsWith("FARMERS") || second_part.startsWith("farmers")) {
                String[] data = dbUtil.inputConverter_Bulk(buffer);
                insert_Bulk.insert_bulk("farmer",data);
            } else if (second_part.startsWith("FARMER") || second_part.startsWith("farmer")) {
                String data = dbUtil.inputConverter(buffer);
                String userFarmer = dbUtil.insertConverter(data);
                dbUtil.insertUtil("farmer",userFarmer);
                System.out.println("Done");
            } else if (second_part.startsWith("BUYS") || second_part.startsWith("buys")) {
                String[] data = dbUtil.inputConverter_Bulk(buffer);
                insert_Bulk.insert_bulk("buys",data);
                System.out.println("Done");
            } else if (second_part.startsWith("BUY") || second_part.startsWith("buy")) {
                String data = dbUtil.inputConverter(buffer);
                String userBuys = dbUtil.insertConverter(data);
                dbUtil.insertUtil("buys",userBuys);
                System.out.println("Done");
            } else if (second_part.startsWith("PRODUCTS") || second_part.startsWith("products")) {
                String[] data = dbUtil.inputConverter_Bulk(buffer);
                insert_Bulk.insert_bulk("product",data);
            } else if (second_part.startsWith("PRODUCT") || second_part.startsWith("product")) {
                String data = dbUtil.inputConverter(buffer);
                String userProduct = dbUtil.insertConverter(data);
                dbUtil.insertUtil("product",userProduct);
                System.out.println("Done");
            } else if (second_part.startsWith("MARKETS") || second_part.startsWith("markets") || second_part.startsWith("MARKETs") ) {
                String[] data = dbUtil.inputConverter_Bulk(buffer);
                insert_Bulk.insert_bulk("market",data);
            } else if (second_part.startsWith("MARKET") || second_part.startsWith("market")) {
                String data = dbUtil.inputConverter(buffer);
                String userMarket = dbUtil.insertConverter(data);
                dbUtil.insertUtil("market",userMarket);
                System.out.println("Done");
            } else if (second_part.startsWith("PRODUCESs") || second_part.startsWith("producess")) {
                String[] data = dbUtil.inputConverter_Bulk(buffer);
                insert_Bulk.insert_bulk("produces",data);
            } else if (second_part.startsWith("PRODUCES") || second_part.startsWith("produces")) {
                String data = dbUtil.inputConverter(buffer);
                String userProduces = dbUtil.insertConverter(data);
                dbUtil.insertUtil("produces",userProduces);
                System.out.println("Done");
            } else {
                System.err.println("Wrong command!");
                usage();
            }

        } else if (first_part.equalsIgnoreCase("register")) {
            if (second_part.startsWith("PRODUCTs") || second_part.startsWith("products")) {
                String[] data = dbUtil.inputConverter_Bulk(buffer);
                insert_Bulk.insert_bulk("registers",data);
            } else if (second_part.startsWith("PRODUCT") || second_part.startsWith("product")) {
                String data = dbUtil.inputConverter(buffer);
                String userRegister = dbUtil.insertConverter(data);
                dbUtil.insertUtil("registers",userRegister);
                System.out.println("Done");
            } else {
                System.err.println("Wrong command!");
                usage();
            }
        } else {
            System.err.println("Wrong command!");
            usage();
        }
        usage();
    }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void usage() {
        System.out.println("Supported Commands: SHOW TABLES | LOAD DATA | QUERY # | ADD FARMER(...) | ADD BUY(...) | ADD BUYs(...):(...)"
                + " ADD PRODUCT(...) | ADD MARKET() | REGISTER PRODUCT(...) | ADD FARMERs(...) |"
                + " ADD PRODUCTs(...):(...) | ADD MARKETs(...):(...) | ADD PRODUCES(...) | ADD PRODUCESs(...):(...) | REGISTER PRODUCTs(...):(...)");
    }
    public static void main_loop(Connection database_connection) {

        System.out.println("Command line interface is initiated");
        usage();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String buffer = null;
        try {
            while(true) {
                buffer = reader.readLine();
                if(buffer.equalsIgnoreCase("exit") || buffer.equalsIgnoreCase("quit")){
                    System.out.println("Command line interface is closed.");
                    return;
                } else {
                    parse_commands(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    }

