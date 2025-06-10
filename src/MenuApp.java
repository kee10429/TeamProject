import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class MenuApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MenuDAO menuDAO = new MenuDAO();

        while (true) {
            showMenuList(menuDAO);  // 메뉴 출력

            System.out.print("입력>_ ");
            String number = sc.nextLine();

            if (number.equals("1")) {
                System.out.println("[2. 메뉴관리 > 메뉴 추가하기]");

                while (true) {
                    System.out.print("카테고리> ");
                    String categoryName = sc.nextLine();
                    if (categoryName.equals("0")) break;

                    System.out.print("메뉴명> ");
                    String menuName = sc.nextLine();
                    if (menuName.equals("0")) break;

                    System.out.print("가격(단가)> ");
                    String unitPriceInput = sc.nextLine();
                    if (unitPriceInput.equals("0")) break;

                    int unitPrice = 0;
                    try {
                        unitPrice = Integer.parseInt(unitPriceInput);
                    } catch (NumberFormatException e) {
                        System.out.println("❗ 숫자를 입력해주세요.");
                        continue;
                    }

                    // 카테고리 이름으로 ID 찾기
                    int categoryId = getCategoryIdByName(categoryName);
                    if (categoryId == -1) {
                        System.out.println("❗ 존재하지 않는 카테고리입니다.");
                        continue;
                    }

                    // 메뉴 등록
                    int result = menuDAO.menuInsert(menuName, unitPrice, "보임", "사용중", categoryId);
                    if (result > 0) {
                        System.out.println("✅ 메뉴가 추가되었습니다.");
                    } else {
                        System.out.println("❌ 메뉴 추가 실패.");
                    }

                    break;  // 메뉴 하나 추가 후 메인 메뉴로 돌아감
                }

            } else if (number.equals("0")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("❗ 올바른 번호를 입력해주세요.");
            }
        }

        sc.close();
    }

    // 메뉴 목록을 출력하는 메서드
    public static void showMenuList(MenuDAO menuDAO) {
        List<MenuVO> menuList = menuDAO.menuList();

        System.out.println("\n[2. 메뉴관리]");
        System.out.println("번호\t카테고리\t메뉴명\t단가");

        for (int i = 0; i < menuList.size(); i++) {
            MenuVO menu = menuList.get(i);
            System.out.println(
                    (i + 1) + "\t" +
                    menu.getCategoryName() + "\t" +
                    menu.getMenuName() + "\t" +
                    menu.getUnitPrice()
            );
        }

        System.out.println("=============================================================================");
        System.out.println("1.메뉴 추가하기    2.메뉴 삭제하기    3.메뉴 수정하기    4.메뉴숨기기/보이기    0.되돌아가기");
        System.out.println("=============================================================================");
    }

    // 카테고리 이름으로 category_id 조회
    public static int getCategoryIdByName(String categoryName) {
        int categoryId = -1;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.99:3306/pos_db";
            conn = DriverManager.getConnection(url, "pos", "pos");

            String query = "SELECT category_id FROM category WHERE category_name = ?";
            pstmt = conn.prepareStatement(query);
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
                System.out.println("자원 정리 오류: " + e);
            }
        }

        return categoryId;
    }
}
