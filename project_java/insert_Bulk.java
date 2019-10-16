import java.sql.SQLException;

public abstract class insert_Bulk {
    public static void insert_bulk(String str,String[] list) {
        try {
            main.db_CONN.setAutoCommit(false);
            switch (str) {
                case "farmer":
                    for(int i=0;i<list.length;i++) {
                        System.out.println(list[i]);
                        insert_Farmer.insert_F(list[i]);
                    }
                    break;
                case "market":
                    for(int i=0;i<list.length;i++) {
                        insert_Market.insert_M(list[i]);
                    }
                    break;
                case "product":
                    for(int i=0;i<list.length;i++) {
                        insert_Product.insert_P(list[i]);
                    }
                    break;
                case "buys":
                    for(int i=0;i<list.length;i++) {
                        insert_Buys.insert_B(list[i]);
                    }
                    break;
                case "registers":
                    for(int i=0;i<list.length;i++) {
                        insert_Registers.insert_R(list[i]);
                    }
                    break;
                case "produces":
                    for(int i=0;i<list.length;i++) {
                        insert_Produces.insert_Pro(list[i]);
                    }
                    break;
            }
            main.db_CONN.commit();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            try{
                main.db_CONN.rollback();
                System.out.println("Roll back");
            }
            catch (SQLException ex2) {
                ex.printStackTrace();
            }
        }
    }
}
