
public class MenuVO {
	private int menuId;
    private String menuName;
    private int unitPrice;
    private String menuShow;
    private String menuDelete;
    private int categoryId;          
    private String categoryName;
    

    
    // getter/setter
    

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

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
