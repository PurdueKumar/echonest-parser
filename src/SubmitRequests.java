/**
 * Created by Admin on 12/30/2014.
 */

import java.io.*;

public class SubmitRequests implements Runnable {

    private String key;

    private String file;

    public SubmitRequests(String key, String file) {

        this.key = key;

        this.file = file;
    }


    public static void main(String[] args) throws Exception {

        String primary = "MKREFTXUIX9EAB2XB";
        String Secondary = "M7LCVQLA7ZYNS3ZO5";

        SubmitRequests test = new SubmitRequests(primary, null);

        /*String out = test.requestInfo("SOAEHQL13E904FDD68", primary);

        System.out.println(out);*/

        try {
            System.out.println(test.requestInfo("prince", "7", primary));
        }

        catch(Exception e) {

        }

        Thread t1 = new Thread(new SubmitRequests(primary, "top5-1.csv"));
        Thread t2 = new Thread(new SubmitRequests(Secondary, "top5-2.csv"));

        t1.start();
        t2.start();

    }

    public String requestInfo(String artist, String song, String key) throws Exception {

        artist = artist.replace(" ", "%20");
        song = song.replace(" ", "%20");

        String requestId = "http://developer.echonest" +
                ".com/api/v4/song/search?api_key=" + key + "&format=xml&artist=" + artist
                + "&title=" + song;

        String id = SongList.getSongId(requestId);

        String requestProfile = "http://developer.echonest" +
                ".com/api/v4/song/profile?api_key=" + key + "&format=xml&id=" + id + "&bucket" +
                "=audio_summary";

        return SongProfile.getProfile(requestProfile);

    }

    public String requestInfo(String id, String key) throws Exception {

        String requestProfile = "http://developer.echonest" +
                ".com/api/v4/song/profile?api_key=" + key + "&format=xml&id=" + id + "&bucket" +
                "=audio_summary";

        return SongProfile.getProfile(requestProfile);
    }

    public void run() {

        //boolean paused = false;

        FileReader in;
        BufferedReader input = null;
        PrintWriter pw = null;
        String info = null;
        String[] output = null;

        File f = new File(file.substring(0, file.indexOf('.')) + " profile.csv");

        try {
            in = new FileReader(file);
            input = new BufferedReader(in);
            pw = new PrintWriter(f);

            input.readLine();
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        pw.println("artist id,artist name,id,key,energy,liveness,tempo,speechiness,acousticness,instrumentalness," +
                "mode,time signature,duration,loudness,audio md5,valence,danceability,title,");

        do {

            for (int i = 0; i < 10; i++) {

                //paused = false;

                try {

                    info = input.readLine();

                    if (info == null) {
                        break;
                    }

                    output = info.split(",");

                    //if (output[3].equals("") || output[3] == null) {

                    //System.out.println("ID does not exist, searching by artist and song title");

                    if (output[0].startsWith("(")) {

                        output[0] = output[0].substring(output[0].indexOf(')'));
                    }

                    int delInd = output[0].indexOf('(');

                    if (delInd != -1) {
                        output[0] = output[0].substring(0, delInd);
                    }

                    delInd = output[0].indexOf('/');

                    if (delInd != -1) {

                        output[0] = output[0].substring(0, delInd);
                    }


                    //code only applicable to mixed list
                        /*if (i >= 18) {
                            paused = true;
                            i = 0;
                            Thread.sleep(60000);

                        }*/

                    info = requestInfo(output[1], output[0], key);

                    System.out.println(output[1] + " " + output[0]);

                    System.out.println(info);

                    pw.println(info);


                    /*} else {

                        System.out.println("ID exists, getting info from song ID");


                        info = requestInfo(output[3], key);

                        System.out.println(output[3]);

                        System.out.println(info);

                        pw.println(info);
                    }*/

                } catch (Exception e) {

                    pw.println(output[0]+ "," + output[1]+ ", unable to get info");

                    System.out.println("unable to get info from artist: " + output[1] + " and title: " + output[0]);
                    System.out.println(e.getMessage());

                }

            }

            try {

                System.out.println("pausing for 1 min");
                Thread.sleep(60000);


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } while (info != null);

        System.out.println("done acquiring song info");


        try {
            pw.close();
            input.close();
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

}
