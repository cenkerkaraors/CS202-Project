import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class insert_Produces {
    public static  void insert_Pro (String produces) throws SQLException {
        String producesT = "INSERT INTO Produces (year_pro,quantity,fid,pid) " + "VALUES (?,?,?,?)";
        String query = "SELECT F.fid\n" +
                "FROM Farmer F\n" +
                "JOIN Farmer_AZ ON Farmer_AZ.faddress = F.faddress\n" +
                "JOIN Farmer_ZC ON Farmer_ZC.zipcode = Farmer_AZ.zipcode\n" +
                "WHERE F.fname = ? AND F.last_name = ?";
        String query2 = "SELECT P.pid\n" +
                "FROM Product P\n" +
                "WHERE P.pname = ?";
            PreparedStatement psPro = main.db_CONN.prepareStatement(producesT,PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement q = main.db_CONN.prepareStatement(query);
            PreparedStatement q2 = main.db_CONN.prepareStatement(query2);
            String[] list = produces.split(",");
            int id = 0;
            for(int i=0;i<list.length;i++) {
                switch (i) {
                    case 0:
                        q.setString(1,list[i]);
                        break;
                    case 1:
                        q.setString(2,list[i]);
                        ResultSet rs = q.executeQuery();
                        int fid = 0;
                        while (rs.next()) {
                            fid = rs.getInt("fid");
                        }
                        psPro.setInt(3,fid);
                        break;
                    case 2:
                        q2.setString(1,list[i]);
                        int pid = 0;
                        ResultSet rs2 = q2.executeQuery();
                        while (rs2.next()) {
                            pid = rs2.getInt("pid");
                        }
                        psPro.setInt(4,pid);
                        break;
                    case 3:
                        int amount = Integer.parseInt(list[i]);
                        psPro.setInt(2,amount);
                        break;
                    case 4:
                        int year = Integer.parseInt(list[i]);
                        psPro.setInt(1,year);
                        break;
                }
            }
            psPro.executeUpdate();

    }
}
