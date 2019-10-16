import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class insert_Market {
    public static void insert_M (String market) throws SQLException {
        String marketZC = "INSERT INTO Local_Market_ZC (zipcode,city) " + "VALUES (?,?)";
        String marketAZ = "INSERT INTO Local_Market_AZ (maddress,zipcode) " + "VALUES (?,?)";
        String marketT = "INSERT INTO LocaL_Market (mname,maddress,phone_number,budget) " + "VALUES (?,?,?,?)";
            PreparedStatement psmZC = main.db_CONN.prepareStatement(marketZC);
            PreparedStatement psmAZ = main.db_CONN.prepareStatement(marketAZ);
            PreparedStatement psM = main.db_CONN.prepareStatement(marketT);
            String[] list = market.split(",");
            for (int i=0;i<list.length;i++) {
                switch (i) {
                    case 0:
                        psM.setString(1, list[i]);
                        break;
                    case 1:
                        psM.setString(2, list[i]);
                        psmAZ.setString(1,list[i]);
                        break;
                    case 2:
                        String number = list[i];
                        int zipCode = Integer.parseInt(number);
                        psmAZ.setInt(2,zipCode);
                        psmZC.setInt(1,zipCode);
                        break;
                    case 3:
                        psmZC.setString(2,list[i]);
                        break;
                    case 4:
                        psM.setString(3, list[i]);
                        break;
                    case 5:
                        String num = list[i];
                        int budget = Integer.parseInt(num);
                        psM.setInt(4,budget);
                        psmZC.executeUpdate();
                        psmAZ.executeUpdate();
                        psM.executeUpdate();
                        break;

                }
            }
    }
}
