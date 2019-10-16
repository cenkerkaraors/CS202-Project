import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class insert_Product {
    public static void insert_P(String product) throws SQLException {
        String productAM = "INSERT INTO Product_AM (altitude_level,min_temp) " + "VALUES (?,?)";
        String productPH = "INSERT INTO Product_PH (plant_date,harvest_date) " + "VALUES (?,?)";
        String productT = "INSERT INTO Product(pname,plant_date,hardness_level,altitude_level) " + "Values(?,?,?,?)";
            PreparedStatement pspAM = main.db_CONN.prepareStatement(productAM);
            PreparedStatement pspPH = main.db_CONN.prepareStatement(productPH);
            PreparedStatement psP = main.db_CONN.prepareStatement(productT,PreparedStatement.RETURN_GENERATED_KEYS);
            String[] list = product.split(",");
            int id = 0;
            for(int i=0;i<list.length;i++) {
                switch (i) {
                    case 0:
                        psP.setString(1,list[0]);
                        break;
                    case 1:
                        int numberP = dbUtil.covertNumber(list[1]);
                        psP.setInt(2,numberP);
                        pspPH.setInt(1,numberP);
                        break;
                    case 2:
                        int numberH = dbUtil.covertNumber(list[2]);
                        pspPH.setInt(2,numberH);
                        break;
                    case  3:
                        int numberA = Integer.parseInt(list[3]);
                        psP.setInt(4,numberA);
                        pspAM.setInt(1,numberA);
                        break;
                    case 4:
                        int numberT = Integer.parseInt(list[4]);
                        pspAM.setInt(2,numberT);
                        break;
                    case 5:
                        int numberHard = Integer.parseInt(list[5]);
                        psP.setInt(3,numberHard);
                        break;
                }
            }
            pspAM.executeUpdate();
            pspPH.executeUpdate();
            psP.executeUpdate();
    }
}
