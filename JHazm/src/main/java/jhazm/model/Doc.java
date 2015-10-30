package jhazm.model;

/**
 * Created by Mojtaba on 30/10/2015.
 */
public class Doc {
    public int ID;
    public String Title;
    public String Text;
    public String Date;
    public String Time;
    public String Category;
    public String Category2;

    public Doc(int ID, String Title, String Text, String Date, String Time, String Category, String Category2) {
        this.ID = ID;
        this.Title = Title;
        this.Text = Text;
        this.Date = Date;
        this.Time = Time;
        this.Category = Category;
        this.Category2 = Category2;
    }
}
