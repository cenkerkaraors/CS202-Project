import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class insert_Buys {
    public static void insert_B (String buys) throws SQLException {
        String buysT = "INSERT INTO Buys_Product (amount,price,creditcard,mid,wid,rid) " + "VALUES (?,?,?,?,300,?)";
        String buysCN = "INSERT INTO Buys_Product_CN (creditcard,mname) " + "VALUES (?,?)";
        String query = "SELECT W.rid,W.price\n" +
                "FROM Farmer F,Website_List W,Product P\n" +
                "WHERE F.fid = W.fid AND W.pid = P.pid AND F.fname = ? AND F.last_name = ? AND P.pname = ?";
        String query2 = "SELECT M.mid\n" +
                "FROM Local_Market M\n" +
                "WHERE M.mname = ? AND M.maddress = ?";
            PreparedStatement psB = main.db_CONN.prepareStatement(buysT,PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement psbCN = main.db_CONN.prepareStatement(buysCN);
            PreparedStatement q = main.db_CONN.prepareStatement(query);
            PreparedStatement q2 = main.db_CONN.prepareStatement(query2);
            String[] list = buys.split(",");
            for(int i=0;i<list.length;i++) {
                switch (i) {
                    case 0:
                        q.setString(1,list[i]);
                        break;
                    case 1:
                        q.setString(2,list[i]);
                        break;
                    case 2:
                        q.setString(3,list[i]);
                        ResultSet rs = q.executeQuery();
                        Double price = 0.0;
                        int rid = 0;
                        while (rs.next()) {
                             price = rs.getDouble("price");
                             rid = rs.getInt("rid");
                        }
                        psB.setInt(5,rid);
                        psB.setDouble(2,price);
                        break;
                    case 3:
                        psbCN.setString(2,list[i]);
                        q2.setString(1,list[i]);
                        break;
                    case 4:
                        q2.setString(2,list[i]);
                        ResultSet rs2 = q2.executeQuery();
                        int mid = 0;
                        while (rs2.next()) {
                            mid = rs2.getInt("mid");
                        }
                        psB.setInt(4,mid);
                        break;
                    case 5:
                        int amount = Integer.parseInt(list[i]);
                        psB.setInt(1,amount);
                        break;
                    case 6:
                        psB.setString(3,list[i]);
                        psbCN.setString(1,list[i]);
                        break;
                }
            }
            psbCN.executeUpdate();
            psB.executeUpdate();

    }
}
