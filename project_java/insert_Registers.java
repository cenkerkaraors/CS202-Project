import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class insert_Registers {
    public static void insert_R (String registers) throws SQLException {
        String register = "INSERT INTO Registers (IBAN,fid,wid) " + "VALUES (?,?,300)";
        String websiteList = "INSERT INTO Website_List (origin,price,quantity,fid,pid,wid,rid) " + "VALUES (?,?,?,?,?,300,?)";
        String query = "SELECT F.fid,Farmer_ZC.city\n" +
                "FROM Farmer F\n" +
                "JOIN Farmer_AZ ON Farmer_AZ.faddress = F.faddress\n" +
                "JOIN Farmer_ZC ON Farmer_ZC.zipcode = Farmer_AZ.zipcode\n" +
                "WHERE F.fname = ? AND F.last_name = ?";
        String query2 = "SELECT P.pid\n" +
                "FROM Product P\n" +
                "WHERE P.pname = ?";
            PreparedStatement psR = main.db_CONN.prepareStatement(register,PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement psWL = main.db_CONN.prepareStatement(websiteList);
            PreparedStatement q = main.db_CONN.prepareStatement(query);
            PreparedStatement q2 = main.db_CONN.prepareStatement(query2);
            String[] list = registers.split(",");
            int id = 0;
            for(int i=0;i<list.length;i++) {
                switch (i) {
                    case 0:
                        q.setString(1,list[i]);
                        break;
                    case 1:
                        q.setString(2,list[i]);
                        ResultSet rs = q.executeQuery();
                        String origin = "";
                        int fid = 0;
                        while (rs.next()) {
                            origin = rs.getString("city");
                            fid = rs.getInt("fid");
                        }
                        psR.setInt(2,fid);
                        psWL.setString(1,origin);
                        psWL.setInt(4,fid);
                        break;
                    case 2:
                        q2.setString(1,list[i]);
                        int pid = 0;
                        ResultSet rs2 = q2.executeQuery();
                        while (rs2.next()) {
                            pid = rs2.getInt("pid");
                        }
                        psWL.setInt(5,pid);
                        break;
                    case 3:
                        int amount = Integer.parseInt(list[i]);
                        psWL.setInt(3,amount);
                        break;
                    case 4:
                        String pStr = list[i] + "." + list[i+1];
                        Double price = Double.parseDouble(pStr);
                        psWL.setDouble(2,price);
                        break;
                    case 6:
                        psR.setString(1,list[i]);
                        break;
                }
            }
            psR.executeUpdate();
            ResultSet res = psR.getGeneratedKeys();
            res.next();
            id = res.getInt(1);
            psWL.setInt(6,id);
            psWL.executeUpdate();


    }
}
