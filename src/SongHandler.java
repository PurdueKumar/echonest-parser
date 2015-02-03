import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

/**
 * This class overrides the Default handler methods to get the value that we need
 */


public class SongHandler extends DefaultHandler {

    private String songData = "";
    private String name = "";
    boolean begin = false;
    boolean stop = false;
    boolean noVal;
    boolean aName;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) {

        if (localName.equals("artist_id")) {

            begin = true;
        }

        if (localName.equals("analysis_url")) {
            stop = true;
        }

        if (localName.equals("energy")) {
            stop = false;
        }

        if (localName.equals("audio_summary")) {

            noVal = false;
        } else {
            noVal = true;
        }

        if (localName.equals("artist_name")) {

            aName = true;
        } else {
            aName = false;
        }


    }

    @Override
    public void characters(char ch[], int start, int length) {

        String add = new String(ch, start, length);

        if (aName) {
            add = add.replace("é", "e");
            name += add;
            noVal = false;
            return;
        }

        noVal = false;

        if (add.startsWith("http") || (add.startsWith("&"))) {
            return;
        } else if (add.contains(",")) {
            add = add.replace(',', '&');
        }

        if (begin && !stop) {

            songData += add + ",";
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (begin && !stop) {

            if (noVal) {

                songData += ",";

            }
        }

        if (localName.equals("artist_name")) {

            songData += name + ",";
        }
    }

    public String getSongData() {

        return songData;
    }
}
