package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/20/2015.
 */
public class Categories {
    private String name;
    private int icon;

    public Categories(String name, int icon){
        this.name = name;
        this.icon = icon;
    }


    public int getIcon() {
        return icon;
    }
    public String getName() {
        return name;
    }
}
