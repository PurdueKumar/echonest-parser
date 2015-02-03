import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Admin on 12/30/2014.
 */
public class ListHandler extends DefaultHandler {

    private String RightSong = "";
    boolean found = false;
    boolean checking = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) {


        if (localName.equals("id")) {

            checking = true;
        }

        if (localName.equals("artist_name")) {
            checking = false;
        }

        if (localName.equals("title")) {
            checking = true;
        }

        if (localName.equals("artist_id")) {
            checking = false;
        }

    }

    @Override
    public void characters(char ch[], int start, int length) {

        if (found) {
            return;
        }

        if (checking) {


            String check = new String(ch, start, length);

            if (check.startsWith("SO")) {
                RightSong = check;
            } else if (!check.endsWith(")")) {

                found = true;
            }
        }

    }

    public String getRightSong() {

        return RightSong;
    }
}

