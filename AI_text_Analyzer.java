import java.awt.*;
import javax.swing.*;

public class AI_text_Analyzer{
    static JTextField userinput;
    static JPanel mainpanel;
    static JScrollPane scroller;
    static String API_KEY = "API_KEY";
    public static void main(String[] args) {
        //setup screen
        JFrame frame = new JFrame("text_Analyzer");
        frame.setSize(400,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //create main pannel
        mainpanel = new JPanel();
        mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));

        //scroller pannel
        scroller = new JScrollPane(mainpanel);
        frame.add(scroller,BorderLayout.CENTER);

        //create bottom pannel
        JPanel bottom = new JPanel(new BorderLayout());
        JButton send = new JButton("SEND");
        userinput = new JTextField();

        bottom.add(userinput,BorderLayout.CENTER);
        bottom.add(send,BorderLayout.EAST);

        frame.add(bottom,BorderLayout.SOUTH);

        send.addActionListener( e -> sendtext());

        frame.setVisible(true);


        
        
    }

    static void sendtext(){
        //add text to main pannel
        String gettext = userinput.getText().trim();
        if (gettext.isEmpty()) return;

        addtext(gettext, FlowLayout.RIGHT);
        userinput.setText("");

        //add ai response to main pannel
        String aireply = AnalyzewithAI(gettext);
        addtext(aireply, FlowLayout.LEFT);



    }

    static void addtext(String genarated,int align ){
        JPanel panel = new JPanel(new FlowLayout(align));
        JLabel label = new JLabel("<html>" + genarated + "</html>");
        panel.add(label);

        mainpanel.add(panel);
        mainpanel.revalidate();

        // auto scroll
        JScrollBar vertical = scroller.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());

    }

    static String AnalyzewithAI(String text){

        try {

            String prompt =
                    "Analyze the following text.\n\n" +
                    "Include:\n" +
                    "- Improved version\n" +
                    "- Word count\n" +
                    "- Sentiment\n" +
                    "- 5 keywords\n" +
                    "- AI or Human detection\n\n" +
                    "Text:\n" + text;

            // Escape JSON special characters
            prompt = prompt
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n");

            // JSON body
            String json = """
            {
                "model": "gpt-4o-mini",
                "messages": [
                    {
                        "role": "user",
                        "content": "%s"
                    }
                ]
            }
            """.formatted(prompt);

            // API call
            String response = API.post(
                    "https://api.openai.com/v1/chat/completions",
                    json
            );

            return (response);

        }

        catch(Exception e){

            return "Error: " + e.getMessage();

        }
    }
}