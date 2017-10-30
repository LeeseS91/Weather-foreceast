/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherforecastsummary;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;

/**
 *
 * @author LeeseS
 */
public class WeatherForecastSummary extends JPanel implements ActionListener{
    //String URLname="http://www.bbc.com/weather/2638867";
    //int locIndex=0;
    JLabel picture;
    //JComboBox locList;
    //JComboBox dayList;
    public WeatherForecastSummary(){
        super(new BorderLayout());
        
        String[] locations = {"St Albans", "Bakewell", "Sheffield", "London"};
        String[] days = {"1", "2", "3", "4", "5"};
        
       
        JComboBox locList = new JComboBox(locations);
        JComboBox dayList = new JComboBox(days);
        JTextArea txtArea= new JTextArea();
        //JComboBox locList = new JComboBox();
        //JComboBox dayList = new JComboBox(days);
        //JButton Enter = new JButton();
        JButton Enter = new JButton(new AbstractAction("print"){
            public void actionPerformed(ActionEvent e){
                String selectedItem= (String)locList.getSelectedItem();
                int locIndex = (int)locList.getSelectedIndex();
                String[] locationNo = {"2638867", "2656617", "2638077", "2643743"};
                /*if (selectedItem != null)
                {
                    String URLname = "http://www.bbc.com/weather/"+locationNo[locIndex];
                    //String selectedItemStr =selectedItem.toString();
                    
                }*/
                //String name=locList.getSource();
                String URLname = "http://www.bbc.com/weather/"+locationNo[locIndex];
                int x = (int)dayList.getSelectedIndex();
                int x2 = x+1;
                if (x2>1){
                   System.out.println("__________________________________________");
                   System.out.println("The weather in "+selectedItem+" for the next " +x2+ " days will be:");     
                }
                else{
                    System.out.println("__________________________________________");
                    System.out.println("The weather in "+selectedItem+" today is:");
                }                
                        try {
                    //String URLname = null;
                    
            openWebpage(URLname, x);
            
            } catch (IOException ex) {
            Logger.getLogger(WeatherForecastSummary.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        });
        Enter.setSize(400,400);
        Enter.setVisible(true);
        Enter.setText("Enter");
        //Enter.addActionListener(this);
        //Enter.addActionListener(new doMyAction());
        locList.setSelectedIndex(0);
        dayList.setSelectedIndex(0);
        //locList.addActionListener(this);
        //dayList.addActionListener(this);
        
             
        /*picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setHorizontalAlignment(JLabel.CENTER);
        //updateLabel(locations[locList.getSelectedIndex()]);
        picture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        picture.setPreferredSize(new Dimension(177, 122+10));
        */
        add(locList, BorderLayout.PAGE_START);
        add(dayList, BorderLayout.CENTER);
        add(Enter, BorderLayout.LINE_END);
        //add(picture, BorderLayout.PAGE_END);
        
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }
    /** Listens to the combo box       */
    /*
    public void actionPerformed(ActionEvent e){
        String[] locationNo = {"2638867", "2656617", "2638077", "2643743"};
        JComboBox cb = (JComboBox)e.getSource();
        //JButton cb = (JButton)e.getSource();
        String locName = (String)cb.getSelectedItem();
        int locIndex = cb.getSelectedIndex();
        String URLname = "http://www.bbc.com/weather/"+locationNo[locIndex];
        //System.out.println(locIndex);
        System.out.println(locName);
        System.out.println("http://www.bbc.com/weather/"+locationNo[locIndex]);
        //return locIndex;
        //return URLname;
            //updateLabel(locName);
            //picture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
            //return URL;
            
        try {
            openWebpage(URLname, locIndex);
            
        } catch (IOException ex) {
            Logger.getLogger(WeatherForecastSummary.class.getName()).log(Level.SEVERE, null, ex);
        } 
            
    }*/
    
    public void openWebpage(String URLname, int x) throws IOException{
        
        URL url = new URL(URLname);

        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();

        // Once you have the Input Stream, it's just plain old Java IO stuff.

        // For this case, since you are interested in getting plain-text web page
        // I'll use a reader and output the text content to System.out.

        // For binary content, it's better to directly read the bytes from stream and write
        // to the target file.

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        String prevLine =" ";
        int indDay=0;
        int indWeather=0;
        int indMax=0;
        int indMin=0;
        int indWind=0;
        int indSpeed=0;
        int cap=x;
        
        /*|| prevLine.contains("<td class=\"weather\">") || prevLine.contains("<td class=\"max-temp\">") || 
                    prevLine.contains("<td class=\"min-temp\">") || prevLine.contains("<td class=\"wind\">") || prevLine.contains(">Wind Speed</span>"))
        */
        while ((line = br.readLine()) != null) {
            
            if(prevLine.contains("<td class=\"dayname\">") && indDay<=cap) {
                System.out.println("__________________________________________");
                System.out.println("Day: " + line.substring(12,22));
                indDay++;
            }
            else if(prevLine.contains("<td class=\"weather\">")&& indWeather<=cap){
                String lineEdit=line.substring(23);
                String lineEdit2=lineEdit.replaceAll("</p>","");
               System.out.println("Overall weather: " + lineEdit2);
                indWeather++;
            }
            else if(prevLine.contains("<td class=\"max-temp\">")&& indMax<=cap){
                
                String Str2 = line.replaceAll("[^0-9,-]","");
               
                System.out.println("The maximum temperature will range between " + Str2.substring(1) + "\u00b0"+"C");
                
                indMax++;
            }
            else if(prevLine.contains("<td class=\"min-temp\">")&& indMin<=cap){
                String Str2 = line.replaceAll("[^0-9,-]","");
                System.out.println("The minimum temperature will range between " + Str2.substring(1) + "\u00b0"+"C");
                indMin++;
            }
            else if(prevLine.contains("<td class=\"wind\">")&& indWind<=cap){
                String lineEdit=line.substring(49);
                String lineEdit2=lineEdit.replaceAll("</span>","");
                System.out.println("The wind direction will be: " + lineEdit2);
                indWind++;
            }
            else if(prevLine.contains(">Wind Speed</span>")&& indSpeed<=cap){
                String newLine = line.replaceAll("[^0-9]","");
                String newLineRev = newLine.substring(0,2) + "kph or " +newLine.substring(2)+"mph";
                System.out.println("The wind speed is expected to be: " + newLineRev);
                indSpeed++;
            }
            prevLine=line;
        }
    }
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("WeatherForecastSummary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        //Create and set up the content pane.
        JComponent newContentPane = new WeatherForecastSummary();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        //frame.add(Enter, BorderLayout.LINE_END);
    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        
        //add(locList, BorderLayout.PAGE_START);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
/*
class doMyAction implements ActionListener {
    String URLname;
    int locIndex;
    public doMyAction(String URL, int t) {
        String URLname=URL;
        int locIndex=t;
    }
    
         public void actionPerformed(ActionEvent e) {
        try {
            openWebpage(URLname, locIndex);
            
        } catch (IOException ex) {
            Logger.getLogger(WeatherForecastSummary.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
*/