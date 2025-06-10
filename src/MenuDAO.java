import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public MenuDAO() {}

    // 메뉴 등록
    public int menuInsert(String menu_name, int unit_price, String menu_show, String menu_delete, int category_id) {
        int count = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String query = "INSERT INTO menu (menu_name, unit_price, menu_show, menu_delete, category_id) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, menu_name);
            pstmt.setInt(2, unit_price);
            pstmt.setString(3, menu_show);
            pstmt.setString(4, menu_delete);
            pstmt.setInt(5, category_id);

            count = pstmt.executeUpdate();
            System.out.println(count + "건 등록");

        } catch (Exception e) {
            System.out.println("menuInsert 오류: " + e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("자원 정리 오류: " + e);
            }
        }
        return count;
    }

    // 메뉴 수정
    public int menuUpdate(int menu_id, String menu_name, int unit_price, String menu_show, String menu_delete, int category_id) {
        int count = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String query = "UPDATE menu SET menu_name = ?, unit_price = ?, menu_show = ?, menu_delete = ?, category_id = ? WHERE menu_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, menu_name);
            pstmt.setInt(2, unit_price);
            pstmt.setString(3, menu_show);
            pstmt.setString(4, menu_delete);
            pstmt.setInt(5, category_id);
            pstmt.setInt(6, menu_id);

            count = pstmt.executeUpdate();
            System.out.println(count + "건이 수정되었습니다.");

        } catch (Exception e) {
            System.out.println("menuUpdate 오류: " + e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("자원 정리 오류: " + e);
            }
        }

        return count;
    }

    // 메뉴 삭제 (물리 삭제 방식)
    public int menuDelete(int menu_id) {
        int count = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String query = "DELETE FROM menu WHERE menu_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, menu_id);

            count = pstmt.executeUpdate();
            System.out.println(count + "건이 삭제되었습니다.");

        } catch (Exception e) {
            System.out.println("menuDelete 오류: " + e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("자원 정리 오류: " + e);
            }
        }

        return count;
    }

    // 메뉴 리스트 (삭제된 메뉴 제외)
    public List<MenuVO> menuList() {
        List<MenuVO> menuList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String query = "SELECT m.menu_id, m.menu_name, m.unit_price, m.menu_show, m.menu_delete, m.category_id, c.category_name " +
                           "FROM menu m JOIN category c ON m.category_id = c.category_id " +
                           "WHERE m.menu_delete != '삭제됨' OR m.menu_delete IS NULL";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int menuId = rs.getInt("menu_id");
                String menuName = rs.getString("menu_name");
                int unitPrice = rs.getInt("unit_price");
                String menuShow = rs.getString("menu_show");
                String menuDelete = rs.getString("menu_delete");
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");

                MenuVO menu = new MenuVO(categoryName, menuId, menuName, unitPrice, menuShow, menuDelete, categoryId);
                menuList.add(menu);
            }

        } catch (Exception e) {
            System.out.println("menuList 오류: " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("자원 정리 오류: " + e);
            }
        }

        return menuList;
    }
}
