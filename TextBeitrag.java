import java.util.Date;

public class TextBeitrag implements java.io.Serializable {
    public String msg;
    public String absender;
    public Date timestamp;

    public TextBeitrag(String m, String absender) {
        this.msg = m;
        this.absender = absender;
        this.timestamp = new Date();
    }

    public String getMsg() {
        return this.msg;
    }

    public String getTime() {
        return timestamp.getHours() + ":" + timestamp.getMinutes() + ":" + timestamp.getSeconds();
    }
}
