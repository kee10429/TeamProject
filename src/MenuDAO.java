import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    // Connection 얻기
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
        return DriverManager.getConnection(url, "pos", "pos");
    }

    // 메뉴 리스트 가져오기
    public List<MenuVO> menuList() {
        
    	List<MenuVO> list = new ArrayList<>();
        
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. 드라이버 로딩 및 DB 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            // 2. SQL 준비 / 바인딩 / 실행
            String sql = "SELECT m.menu_id, m.menu_name, m.unit_price, m.menu_show, m.menu_delete, " +
                         "c.category_id, c.category_name " +
                         "FROM menu m JOIN category c ON m.category_id = c.category_id " +
                         "WHERE m.menu_delete != '삭제됨'";

            pstmt = conn.prepareStatement(sql);
            
            rs = pstmt.executeQuery();

            // 3. 결과 처리
            while (rs.next()) {
                MenuVO vo = new MenuVO();
                vo.setMenuId(rs.getInt("menu_id"));
                vo.setMenuName(rs.getString("menu_name"));
                vo.setUnitPrice(rs.getInt("unit_price"));
                vo.setMenuShow(rs.getString("menu_show"));
                vo.setMenuDelete(rs.getString("menu_delete"));
                vo.setCategoryId(rs.getInt("category_id"));
                vo.setCategoryName(rs.getString("category_name"));
                list.add(vo);
            }

        } catch (Exception e) {
            System.out.println("menuList 오류: " + e);
        } finally {
            // 4. 자원 정리
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("menuList 자원정리 오류: " + e);
            }
        }

        return list;
    }

    // 메뉴 추가
    public int menuInsert(String menuName, int unitPrice, String menuShow, String menuDelete, int categoryId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String query = "INSERT INTO menu(menu_name, unit_price, menu_show, menu_delete, category_id) " +
                           "VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, menuName);
            pstmt.setInt(2, unitPrice);
            pstmt.setString(3, menuShow);
            pstmt.setString(4, menuDelete);
            pstmt.setInt(5, categoryId);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("menuInsert 오류: " + e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("menuInsert 자원정리 오류: " + e);
            }
        }

        return result;
    }

    // 메뉴 삭제 (논리삭제)
    public int menuDelete(int menuId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String sql = "UPDATE menu SET menu_delete = '삭제됨' WHERE menu_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, menuId);
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("menuDelete 오류: " + e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("menuDelete 자원정리 오류: " + e);
            }
        }

        return result;
    }

    // 메뉴 수정
    public int menuUpdate(int menuId, String menuName, int unitPrice, int categoryId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String sql = "UPDATE menu SET menu_name = ?, unit_price = ?, category_id = ? WHERE menu_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, menuName);
            pstmt.setInt(2, unitPrice);
            pstmt.setInt(3, categoryId);
            pstmt.setInt(4, menuId);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("menuUpdate 오류: " + e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("menuUpdate 자원정리 오류: " + e);
            }
        }

        return result;
    }

    // 메뉴 숨기기/보이기 toggle
    public int toggleMenuShow(int menuId, String currentShow) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;
        String newShow = currentShow.equals("보임") ? "숨기기" : "보임";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String sql = "UPDATE menu SET menu_show = ? WHERE menu_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newShow);
            pstmt.setInt(2, menuId);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("toggleMenuShow 오류: " + e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("toggleMenuShow 자원정리 오류: " + e);
            }
        }

        return result;
    }

    // 카테고리 이름으로 카테고리 ID 조회
    public int getCategoryIdByName(String categoryName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int categoryId = -1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String sql = "SELECT category_id FROM category WHERE category_name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, categoryName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                categoryId = rs.getInt("category_id");
            }

        } catch (Exception e) {
            System.out.println("getCategoryIdByName 오류: " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("getCategoryIdByName 자원정리 오류: " + e);
            }
        }

        return categoryId;
    }
}