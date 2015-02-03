import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.net.URL;

public class SongList {

    /**
     * This program connects the the echonest website and parses the XML file.
     * It then uses the getProfile method to return the date from the song in the form of tab seperated values
     */


    //this method connects to echonest, and returns the song data.
    //the parameter echoCall is the URL for the method which generates the XML
    public static String getSongId(String echoCall) throws Exception {

        //initializing class with overridden methods to get date
        ListHandler handler = new ListHandler();

        //output String with tab separated data values
        String output;

        //initializing java.net object to connect to echonest website
        URL echo = new URL(echoCall);

        //initializing xml stream
        InputSource in = new InputSource(echo.openStream());


        //initialzing XML parser
        XMLReader myReader = XMLReaderFactory.createXMLReader();
        myReader.setContentHandler(handler);
        myReader.parse(in);

        //SongHandler outputting song data
        output = handler.getRightSong();

        return output;

    }


}

