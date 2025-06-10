
public class MenuVO {

    // 필드
    private String categoryName;
    private int menuId;
    private String menuName;
    private int unitPrice;
    private String menuShow;
    private String menuDelete;
    private int categoryId;

    // 기본 생성자
    public MenuVO() {}

    // 전체 필드 초기화 생성자
    public MenuVO(String categoryName, int menuId, String menuName, int unitPrice,
                  String menuShow, String menuDelete, int categoryId) {
        this.categoryName = categoryName;
        this.menuId = menuId;
        this.menuName = menuName;
        this.unitPrice = unitPrice;
        this.menuShow = menuShow;
        this.menuDelete = menuDelete;
        this.categoryId = categoryId;
    }

    // Getter / Setter
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMenuShow() {
        return menuShow;
    }

    public void setMenuShow(String menuShow) {
        this.menuShow = menuShow;
    }

    public String getMenuDelete() {
        return menuDelete;
    }

    public void setMenuDelete(String menuDelete) {
        this.menuDelete = menuDelete;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "MenuVO [categoryName=" + categoryName + ", menuId=" + menuId +
                ", menuName=" + menuName + ", unitPrice=" + unitPrice +
                ", menuShow=" + menuShow + ", menuDelete=" + menuDelete +
                ", categoryId=" + categoryId + "]";
    }
}
