package p2.wordsuggestor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

public final class ParseFBMessages {
    private ParseFBMessages() {
        /* should not be instantiated */
    }

    public static void main(String[] args) throws IOException {
        String name = "<Your FB Name>"; // e.g. "Ruth Anderson"
        String archive = "<Your FB Archive>"; // e.g. "/Users/rea/workspace/332/facebook-rea/messages"

        Stack<String> corpus = new Stack<>();
        File[] listOfFiles = (new File(archive + File.separator + "inbox")).listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            File conversation = new File(listOfFiles[i], "message_1.json");
            if (conversation.isFile()) {
                try {
                    JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(conversation));
                    JSONArray messages = (JSONArray) obj.get("messages");
                    for (Object m : messages) {
                        JSONObject msg = (JSONObject) m;
                        String sender = (String) msg.get("sender_name");
                        String type = (String) msg.get("type");
                        if (sender != null && sender.equals(name) && (type == null || type.equals("Generic"))) {
                            corpus.push((String) msg.get("content"));
                        }
                    }
                } catch (ParseException e) {
                    System.err.println("Could not parse: " + conversation.toString());
                }
            }
        }

        PrintWriter out = new PrintWriter("me.txt", StandardCharsets.UTF_8);

        while (!corpus.isEmpty()) {
            out.println(corpus.pop());
        }

        out.close();
    }
}
