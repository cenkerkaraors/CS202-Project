import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class insert_Farmer {
    public static void insert_F (String farmer) throws SQLException {
            String farmerZC = "INSERT INTO Farmer_ZC (zipcode,city) " + "VALUES (?,?)";
            String farmerAZ = "INSERT INTO Farmer_AZ (faddress,zipcode) " + "VALUES (?,?)";
            String farmerT = "INSERT INTO Farmer (fname,last_name,faddress) " + "VALUES (?,?,?)";
                PreparedStatement psfZC = main.db_CONN.prepareStatement(farmerZC);
                PreparedStatement psfAZ = main.db_CONN.prepareStatement(farmerAZ);
                PreparedStatement psF = main.db_CONN.prepareStatement(farmerT,PreparedStatement.RETURN_GENERATED_KEYS);
                String[] list = farmer.split(",");
                int id = 0;
                for(int i=0;i<list.length;i++) {
                    switch (i) {
                        case 0:
                            psF.setString(1, list[i]);
                            break;
                        case 1:
                            psF.setString(2, list[i]);
                            break;
                        case 2:
                            psF.setString(3, list[i]);
                            psfAZ.setString(1, list[i]);
                            break;
                        case 3:
                            String number = list[i];
                            int zipCode = Integer.parseInt(number);
                            psfAZ.setInt(2, zipCode);
                            psfZC.setInt(1, zipCode);
                            break;
                        case 4:
                            psfZC.setString(2, list[i]);
                            psfZC.executeUpdate();
                            psfAZ.executeUpdate();
                            psF.executeUpdate();
                            ResultSet rs = psF.getGeneratedKeys();
                            rs.next();
                            id = rs.getInt(1);

                            break;
                        case 5:
                            ArrayList<String> phones = dbUtil.myTokenizer(list[i]);
                            for (int k = 0; k < phones.size(); k++) {
                                String phone = "INSERT INTO Farmer_phone (fid,phone_number) " + "VALUES (?,?)";
                                PreparedStatement psPhone = main.db_CONN.prepareStatement(phone);
                                psPhone.setInt(1, id);
                                psPhone.setString(2, phones.get(k));
                                psPhone.executeUpdate();

                            }
                            break;
                        case 6:
                            ArrayList<String> emails = dbUtil.myTokenizer(list[i]);
                            for (int k = 0; k < emails.size(); k++) {
                                String email = "INSERT INTO Farmer_email (fid,email) " + "VALUES (?,?)";
                                PreparedStatement psEmail = main.db_CONN.prepareStatement(email);
                                psEmail.setString(2, emails.get(k));
                                psEmail.setInt(1, id);
                                psEmail.executeUpdate();
                            }
                            break;

                    }
                }

    }
 }
