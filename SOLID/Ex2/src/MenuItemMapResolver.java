import java.util.Map;

public class MenuItemMapResolver implements MenuItemResolver {
    private final Map<String, MenuItem> menu;

    public MenuItemMapResolver(Map<String, MenuItem> menu) {
        this.menu = menu;
    }

    @Override
    public MenuItem resolve(String itemId) {
        return menu.get(itemId);
    }
}
