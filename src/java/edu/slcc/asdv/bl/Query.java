package edu.slcc.asdv.bl;

import edu.slcc.asdv.utilities.connect;
import edu.slcc.asdv.beans.InitializationBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.Collection;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class Query {

    public static ProductsForSale<String, Keyable> pfs = InitializationBean.getProducts();

    public static ProductsForSale<String, Keyable> getAllInfo2() throws SQLException {
        Connection con = connect.connection();
        if (con == null) {
            System.out.println("Cannot connect to DB");
            return null;
        }

        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM inventory");
            SortedMap[] m = ResultSupport.toResult(result).getRows();

            for (SortedMap m1 : m) {
                Item t = new Item();
                t.setCategory(m1.get("category").toString());
                t.setDescription(m1.get("description").toString());
                t.setItem_no(m1.get("item_no").toString());
                t.setPicture_ref(m1.get("picture_ref").toString());
                t.setPrice(m1.get("price").toString());
                t.setQty(m1.get("qty").toString());
                t.setTitle(m1.get("title").toString());
                pfs.create(t);
            }
            return pfs;
        } finally {
            con.close();
        }

    }

    public static ProductsForSale<String, Keyable> getPfs() {
        return pfs;
    }

    public static Collection<Item> findSomeInfo(String query) throws SQLException {
        System.out.println("FIND SOME INFO CALLED");
        Collection<Item> result = new ArrayList<>();
        String nQuery = query.toLowerCase();
        for (Item m1 : pfs.findAll2()) {
            if (m1.getTitle().toLowerCase().contains(nQuery)) {
                result.add(m1);
            }
        }
        if (result.isEmpty() == true)//>end loop if we returned an item
        {
            nQuery = nQuery.substring(0, nQuery.length() - 1);//>subtract
            findSomeInfo(nQuery);
        }

        return result;

    }

    public static Item search(String item_no) throws SQLException {
        return pfs.findAll2Arg(item_no);
    }

    
    
    public static void updateDBPurchase() throws SQLException {
        Connection con = connect.connection();
        if (con == null) {
            System.out.println("Cannot connect to DB");
        }

        try {
            for (Item it : pfs.findAll2()) {
                Statement stmt = con.createStatement();
                int result = stmt.executeUpdate(
                        "UPDATE inventory SET qty = " + it.getQty()
                        + " WHERE item_no = " + Integer.valueOf(it.getItem_no()));
            }
        } finally {
            con.close();
        }
    }

    public static void updateDBSingle(Item it) throws SQLException {
        Connection con = connect.connection();
        if (con == null) {
            System.out.println("Cannot connect to DB");
        }

        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(
                    "UPDATE inventory SET title = '" + it.getTitle() + "', "
                    + "price = " + Double.valueOf(it.getPrice()) + ", "
                    + "category = '" + it.getCategory() + "', "
                    + "picture_ref = '" + it.getPicture_ref() + "', "
                    + "qty = " + Integer.valueOf(it.getQty()) + ", "
                    + "description = '" + fixDesc(it.getDescription()) + "' "
                    + "WHERE item_no = " + Integer.valueOf(it.getItem_no()));

        } finally {
            con.close();
        }
    }

    public static void updateDBAdd(Item it) throws SQLException {
        Connection con = connect.connection();
        if (con == null) {
            System.out.println("Cannot connect to DB");
        }

        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(
                    "INSERT INTO inventory (title, price, category, picture_ref,"
                    + " qty, item_no, description) VALUES ('" + it.getTitle()
                    + "', '" + Double.valueOf(it.getPrice()) + "', '"
                    + it.getCategory() + "', '" + it.getPicture_ref()
                    + "', '" + Integer.valueOf(it.getQty()) + "', '"
                    + Integer.valueOf(it.getItem_no()) + "', '"
                    + fixDesc(it.getDescription()) + "')");

        } finally {
            con.close();
        }
    }

    public static void updateDBDelete(Item it) throws SQLException {
        Connection con = connect.connection();
        if (con == null) {
            System.out.println("Cannot connect to DB");
        }

        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(
                    "DELETE FROM inventory WHERE item_no = "
                    + Integer.valueOf(it.getItem_no()));

        } finally {
            con.close();
        }
    }

    //UNNECESSARY
//        public static void updateDBFull() throws SQLException {
//        Connection con = connect.connection();
//        if (con == null) {
//            System.out.println("Cannot connect to DB");
//        }
//        
//        try {
//            for(Item it : pfs.findAll2())
//            {
//                Statement stmt = con.createStatement();
//            int result = stmt.executeUpdate(
//                    "UPDATE inventory SET title = '"+ it.getTitle() + "', "+
//                           "price = " + Double.valueOf(it.getPrice()) + ", "+
//                            "category = '" + it.getCategory() + "', "+
//                            "picture_ref = '" + it.getPicture_ref() + "', "+
//                            "qty = " + Integer.valueOf(it.getQty()) + ", "+
//                            "description = '" + fixDesc(it.getDescription()) + "' "+
//                            "WHERE item_no = " + Integer.valueOf(it.getItem_no()));
//            } 
//        } finally {
//            con.close();
//        } 
//    }
    public static String fixDesc(String desc) {
        if (desc.contains("'")) {
            desc = desc.replaceAll("'", "`");
        }
        //doesn't fix quotes (") only apostrophes
        return desc;
    }

    public static boolean daySale(String day, String option) throws SQLException {
        Connection con = connect.connection();
        if (con == null) {
            System.out.println("Cannot connect to DB");
            return false;
        }

        try { //'20200324' day format
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select "
                    + option + " from sales WHERE day = " + day);

            while (rs.next()) {
                //pull & convert from DB
                InputStream in = rs.getBinaryStream(1);
                //download locally
                OutputStream f = new FileOutputStream(new File("D:/School/NetBeans/2020-SPRING/Web_App_III/Mp03/web/resources/files/" + day + ".pdf"));
                int c = 0;
                while ((c = in.read()) > -1) {
                    f.write(c);
                }
                f.close();
                in.close();
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static boolean uploadPNG(String title, String filename, String location) throws SQLException {
        Connection con = connect.connection();
                try {
            Statement st = con.createStatement();
            File imgfile = new File("D:/School/NetBeans/2020-SPRING/Web_App_III/Mp03/web/resources/files/"+filename);

            FileInputStream fin = new FileInputStream(imgfile);

            PreparedStatement pre
                    = con.prepareStatement("insert into pngHolder values(?,?,?)");

            pre.setString(1, title);
            pre.setBinaryStream(2, (InputStream) fin, (int) imgfile.length());
            pre.setString(3, location);
            pre.executeUpdate();
            System.out.println("Successfully inserted the file into the database!");

            pre.close();
            fin.close();

        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }


        return false;
    }

}
