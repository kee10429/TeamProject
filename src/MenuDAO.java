import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
	private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
        return DriverManager.getConnection(url, "pos", "pos");
    }

    // 메뉴 리스트 가져오기 
    public List<MenuVO> menuList() {
        List<MenuVO> list = new ArrayList<>();

        String sql = "SELECT m.menu_id, m.menu_name, m.unit_price, m.menu_show, m.menu_delete, " +
                     "c.category_id, c.category_name " +
                     "FROM menu m JOIN category c ON m.category_id = c.category_id " +
                     "WHERE m.menu_delete != '삭제됨'";  // 삭제 안 된 메뉴만

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

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
        }

        return list;
    }

    // 메뉴 추가
    public int menuInsert(String menuName, int unitPrice, String menuShow, String menuDelete, int categoryId) {
        int result = 0;
        String sql = "INSERT INTO menu(menu_name, unit_price, menu_show, menu_delete, category_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, menuName);
            pstmt.setInt(2, unitPrice);
            pstmt.setString(3, menuShow);
            pstmt.setString(4, menuDelete);
            pstmt.setInt(5, categoryId);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("menuInsert 오류: " + e);
        }

        return result;
    }

    // 메뉴 삭제 
    public int menuDelete(int menuId) {
        int result = 0;
        String sql = "UPDATE menu SET menu_delete = '삭제됨' WHERE menu_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, menuId);
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("menuDelete 오류: " + e);
        }

        return result;
    }

    // 메뉴 수정 (메뉴명, 단가, 카테고리 수정)
    public int menuUpdate(int menuId, String menuName, int unitPrice, int categoryId) {
        int result = 0;
        String sql = "UPDATE menu SET menu_name = ?, unit_price = ?, category_id = ? WHERE menu_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, menuName);
            pstmt.setInt(2, unitPrice);
            pstmt.setInt(3, categoryId);
            pstmt.setInt(4, menuId);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("menuUpdate 오류: " + e);
        }

        return result;
    }

    // 메뉴 숨기기/보이기 토글 (menu_show 컬럼 변경)
    public int toggleMenuShow(int menuId, String currentShow) {
        int result = 0;
        String newShow = currentShow.equals("보임") ? "숨겨짐" : "보임";

        String sql = "UPDATE menu SET menu_show = ? WHERE menu_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newShow);
            pstmt.setInt(2, menuId);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("toggleMenuShow 오류: " + e);
        }

        return result;
    }

    // category 이름으로 category_id 조회 
    public int getCategoryIdByName(String categoryName) {
        int categoryId = -1;

        String sql = "SELECT category_id FROM category WHERE category_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    categoryId = rs.getInt("category_id");
                }
            }

        } catch (Exception e) {
            System.out.println("getCategoryIdByName 오류: " + e);
        }

        return categoryId;
    }
}