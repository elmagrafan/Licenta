package com.company;

import com.company.models.ListaResurseModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import static java.lang.Integer.parseInt;


public class Aplicatie extends JFrame implements ActionListener {

    /*  ArrayList<Locatie> listaLocatii; */
    MyTranzitionList listaTranzitii = new MyTranzitionList();
    MyLocationList listaLocatii = new MyLocationList();
    Menu simulareMeniu, stop, fileMeniu, productMenu, submenu, vizualizationMenu;
    int[][] registru = new int[100][1000];
    int registruSecund = 0;
    MenuItem retetar, comenzi, resurse;
    ActionEvent e;
    Graphics g;
    Timer myTimer;
    PasSimulare pasSimulare;
    int numarPasiSimulare = 0;
    final JFileChooser fileOpener;
    Document doc;

    public Aplicatie() {
        super("Model fabricatie");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        fileOpener = new JFileChooser();


        MenuBar mb = new MenuBar();		        // begin with creating menu bar
        setMenuBar(mb);				// add menu bar to frame

        simulareMeniu = new Menu("Simulare");		// create menus

        fileMeniu = new Menu("File");

        productMenu = new Menu("Editare");

        vizualizationMenu = new Menu("Vizualizare");


        mb.add(fileMeniu);
        mb.add(simulareMeniu);  // add menus to menu bar
        mb.add(productMenu);
        mb.add(vizualizationMenu);


        simulareMeniu.addActionListener(this);		// link with ActionListener for event handling
        fileMeniu.addActionListener(this);
        vizualizationMenu.addActionListener(this);
        productMenu.addActionListener(this);

        submenu = new Menu("A submenu");


        retetar = new MenuItem("retetar");
        submenu.add(retetar);

        simulareMeniu.add(new MenuItem("Start fast"));
        simulareMeniu.add(new MenuItem("Start slow"));
        simulareMeniu.add(new MenuItem("Stop"));
        simulareMeniu.add(new MenuItem("Configurare"));
        vizualizationMenu.add(new MenuItem("Vizualizare tabel"));
        vizualizationMenu.add(new MenuItem("Vizualizare grafic"));


        fileMeniu.add(new MenuItem("Default"));
        fileMeniu.add(new MenuItem("Retete"));
        fileMeniu.add(new MenuItem("Resurse"));
        fileMeniu.add(new MenuItem("Comenzi"));
        fileMeniu.add(new MenuItem("Aprovizionare materiale"));

        productMenu.add(new MenuItem("Creati o lista de comenzi"));
        productMenu.add(new MenuItem("Creati o lista de resurse"));
        productMenu.add(new MenuItem("Creati o lista de retete"));

        setTitle("Simulare Petri Net");		// frame creation methods
        setVisible(true);


    }

//timer


    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();// know the menu item selected by the user
        System.out.println("You selected " + str);

        if(str.equals("Start fast")) {
            myTimer = new Timer();

            String s = (String) JOptionPane.showInputDialog(
                    this,
                    "Please insert the number of steps to take",
                    "Number of steps",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "0");

            if ((s != null) && (s.length() > 0)) {
                numarPasiSimulare = parseInt(s);
                pasSimulare = new PasSimulare(this, g, numarPasiSimulare, myTimer);
                myTimer.scheduleAtFixedRate(pasSimulare, 1000, 100);
                return;
            }

                }else if(str.equals("Start slow")) {
                myTimer = new Timer();

                String s1 = (String) JOptionPane.showInputDialog(
                        this,
                        "Please insert the number of steps to take",
                        "Number of steps",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "0");

                if ((s1 != null) && (s1.length() > 0)) {
                    numarPasiSimulare = parseInt(s1);
                    pasSimulare = new PasSimulare(this, g, numarPasiSimulare, myTimer);
                    myTimer.scheduleAtFixedRate(pasSimulare, 1000, 1000);
                    return;

                }
           // System.exit(0);
            }else if(str.equals("Retete")){
            //open a file
            //Create a file chooser


            //In response to a button click:
            int returnVal = fileOpener.showOpenDialog(this);
            StringBuilder log;
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileOpener.getSelectedFile();
                //This is where a real application would open the file.
                parsareFisierProduse(file);
            }
            setVisible(true);
            }else if(str.equals("Resurse")){
            //open a file
            //Create a file chooser


            //In response to a button click:
            int returnVal = fileOpener.showOpenDialog(this);
            StringBuilder log;
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileOpener.getSelectedFile();
                //This is where a real application would open the file.
                parsareFisierResurse(file);
            }
            setVisible(true);
            }else if(str.equals("Comenzi")) {
            //open a file
            //Create a file chooser


            //In response to a button click:
            int returnVal = fileOpener.showOpenDialog(this);
            StringBuilder log;
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileOpener.getSelectedFile();
                //This is where a real application would open the file.
                parsareFisierComenzi(file);
            }
            setVisible(true);

        }else if(str.equals("Aprovizionare materiale")) {
            //open a file
            //Create a file chooser
            //In response to a button click:
            int returnVal = fileOpener.showOpenDialog(this);
            StringBuilder log;
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileOpener.getSelectedFile();
                //This is where a real application would open the file.
                parsareFisierAprovizionareMateriale(file);
            }
            setVisible(true);

        }else if(str.equals("Configurare")){
            JFrame frame = new JFrame("Please insert some data:");
            String configurationOption = JOptionPane.showInputDialog(frame, "Please insert the number of tokens that are to be inserted");
            String tokenConfiguration = JOptionPane.showInputDialog(frame, "Please insert the number of tokens that are to be inserted at location echipament");
            String token1Configuration = JOptionPane.showInputDialog(frame, "Please insert the number of tokens that are to be inserted at location material");
            String token2Configuration = JOptionPane.showInputDialog(frame, "Please insert the number of tokens that are to be inserted at location personal");

            int configurationOptionNumber = parseInt(configurationOption);
            int tokenConfigurationNumber = parseInt(tokenConfiguration);
            int token1ConfigurationNumber = parseInt(token1Configuration);
            int token2ConfigurationNumber = parseInt(token2Configuration);


            parsareFisierComenzi(configurationOptionNumber);
            parsareFisierResurse(tokenConfigurationNumber, token1ConfigurationNumber, token2ConfigurationNumber);
            setVisible(true);

        }else if(str.equals("Vizualizare tabel"))
        {
            int i, j, i1;
            String string = "";
            String title = "value of tokens in tabular form";
            for (i = 1; i < this.listaLocatii.listaLocatii.size(); i++) {
                string += this.listaLocatii.listaLocatii.get(i).nume.toString() + "\t \t ";
                for (j = 1; j < numarPasiSimulare; j++) {
                    Integer x = registru[i][j];
                    string += " \t" + x.toString();
                }
                string += "\n";
            }
            JOptionPane.showMessageDialog(this,string);
        }else if(str.equals("Vizualizare grafic"))
        {
            JFrame frame = new JFrame("Please insert some data:");
            String numeLocatie = JOptionPane.showInputDialog(frame, "Please insert the name of location to be displayed");
            for (int i = 1; i < this.listaLocatii.listaLocatii.size(); i++){
                if(numeLocatie.equals(this.listaLocatii.listaLocatii.get(i).nume)){
                    LineChartEx ex = new LineChartEx(numeLocatie, registru, this);
                    ex.setVisible(true);
                }
                }
        }

        else if(str.equals("Default"))
        {
            parsareFisierProduse(new File("E:\\Faculta\\Calin Muresan\\PetriNETPrototype\\Retete5_0.xml"));
            parsareFisierComenzi(new File("E:\\Faculta\\Calin Muresan\\PetriNETPrototype\\Comenzi.xml"));
            parsareFisierAprovizionareMateriale(new File("E:\\Faculta\\Calin Muresan\\PetriNETPrototype\\Schema_aprovizionare_materiale.xml"));
            parsareFisierResurse(new File("E:\\Faculta\\Calin Muresan\\PetriNETPrototype\\Resurse.xml"));
            setVisible(true);
        }

        else if(str.equals("Creati o lista de comenzi")) {
            int result = 1, i;
            ArrayList<String> numeNecesarComenzi = new ArrayList<String>();
            ArrayList<String> numeNecesarNumarJetoane = new ArrayList<String>();

            while (result != JOptionPane.CANCEL_OPTION) {
                //popupFormPanel("Adaugati numele produsului necesar:", "Adaugati numarul unitatilor:");

                JTextField Field1 = new JTextField();
                JTextField Field2 = new JTextField();
                JPanel myPanel = new JPanel();
                myPanel.setLayout(new GridLayout(2, 2));
                myPanel.add(new JLabel("Adaugati numele produsului necesar:"));
                myPanel.add(Field1);
                myPanel.add(new JLabel("Adaugati numarul unitatilor:"));
                myPanel.add(Field2);
                result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Va rog inserati valorile", JOptionPane.OK_CANCEL_OPTION);

                String numeNecesarProduse = Field1.getText();
                String numarJetoane = Field2.getText();
                numeNecesarComenzi.add(numeNecesarProduse);
                numeNecesarNumarJetoane.add(numarJetoane);
            }

            try {

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                // root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("Aplicatie");
                doc.appendChild(rootElement);



                // staff elements

                for(i = 0; i < numeNecesarComenzi.size() || i < numeNecesarNumarJetoane.size(); i++) {

                    Element locatie = doc.createElement("Locatie");
                    rootElement.appendChild(locatie);

                    Element locationName = doc.createElement("locationName");
                    locationName.appendChild(doc.createTextNode(numeNecesarComenzi.get(i)));
                    locatie.appendChild(locationName);

                    Element numarNecesarJetoane = doc.createElement("numarJetoane");
                    numarNecesarJetoane.appendChild(doc.createTextNode(numeNecesarNumarJetoane.get(i)));
                    locatie.appendChild(numarNecesarJetoane);
                }


                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult resultXml = new StreamResult(new File("E:\\Faculta\\Calin Muresan\\PetriNETPrototype\\Comenzi1_0.xml"));

                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);

                transformer.transform(source, resultXml);

                System.out.println("File saved!");

            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            }
        } else if(str.equals("Creati o lista de resurse")){
            int result = 1, i;
            ListaResurseModel item;
            ArrayList<ListaResurseModel> listaResurseModelArray = new ArrayList<>();
            while (result != JOptionPane.CANCEL_OPTION) {
                JTextField Field1 = new JTextField();
                JTextField Field2 = new JTextField();
                JPanel myPanel = new JPanel();

                myPanel.setLayout(new GridLayout(2, 2));
                myPanel.add(new JLabel("Adaugati numele resursei:"));
                myPanel.add(Field1);
                myPanel.add(new JLabel("Adaugati numarul de unitati/bucati:"));
                myPanel.add(Field2);
                result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Va rog inserati valorile", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    // save data
                    int numarJetoane = Integer.parseInt(Field2.getText());
                    String numeLocatie = Field1.getText();
                    item = new ListaResurseModel(numarJetoane, numeLocatie);
                    listaResurseModelArray.add(item);
                    // serialize to XML file
                }
            }

            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


                // root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("Aplicatie");
                doc.appendChild(rootElement);

                // staff elements

                    for (i = 0; i < listaResurseModelArray.size(); i++){
                    String itemModelValue = null;
                    Element locatie = doc.createElement("Locatie");
                    rootElement.appendChild(locatie);

                    Element locationName = doc.createElement("locationName");
                    locationName.appendChild(doc.createTextNode(listaResurseModelArray.get(i).getLocationName()));
                    locatie.appendChild(locationName);

                    Element numarNecesarJetoane = doc.createElement("numarJetoane");
                    itemModelValue = String.valueOf(listaResurseModelArray.get(i).getValue());
                    numarNecesarJetoane.appendChild(doc.createTextNode(itemModelValue));
                    locatie.appendChild(numarNecesarJetoane);
                    }


                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult resultXml = new StreamResult(new File("E:\\Faculta\\Calin Muresan\\PetriNETPrototype\\Resurse1_0.xml"));
                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);

                transformer.transform(source, resultXml);

                System.out.println("File saved!");

            }
            catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            }catch (TransformerException tfe) {
                tfe.printStackTrace();
            }

        }else if(str.equals("Creati o lista de retete")) {

            final JFrame frame = new JFrame("Adaugati una dintre optiuni");
            frame.setVisible(true);
            frame.setSize(500, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            final JPanel panel = new JPanel();
            frame.add(panel);
            JButton button = new JButton("Resurse");
            panel.add(button);
            final ArrayList<String> arrayNume = new ArrayList<>();
            final ArrayList<String> arrayCoordonataX = new ArrayList<>();
            final ArrayList<String> arrayCoordonataY = new ArrayList<>();


            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Completati campurile");
                    frame.setSize(500, 200);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    JPanel panel = new JPanel();
                    frame.add(panel);
                    final JTextField textField = new JTextField("Inserati nume", 10);
                    final JTextField textField1 = new JTextField("Inserati coordonata x", 10);
                    final JTextField textField2 = new JTextField("Inserati coordonata y", 10);
                    JButton button1 = new JButton("Adauga resurse");
                    JLabel jLabel = new JLabel("Nume", 10);
                    JLabel jLabel1 = new JLabel("Coordonata x:", 10);
                    JLabel jLabel2 = new JLabel("Coordonata y:", 10);
                    panel.add(jLabel);
                    panel.add(textField);
                    panel.add(jLabel1);
                    panel.add(textField1);
                    panel.add(jLabel2);
                    panel.add(textField2);
                    panel.add(button1);
                    textField.setVisible(true);
                    textField1.setVisible(true);
                    textField2.setVisible(true);
                    jLabel.setVisible(true);
                    jLabel1.setVisible(true);
                    jLabel2.setVisible(true);

                    button1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            arrayNume.add(textField.getText());
                            arrayCoordonataX.add(textField1.getText());
                            arrayCoordonataY.add(textField2.getText());
                        }
                    });
                    panel.setVisible(true);
                    frame.setVisible(true);
                }
            });

            JButton button2 = new JButton("Faze");
            final ArrayList<String> arrayNumeLocatieIntrare = new ArrayList<>();
            final ArrayList<String> arrayNumeLocatieIesire = new ArrayList<>();
            panel.add(button2);

            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final JFrame frame = new JFrame("Completati campurile");
                    frame.setSize(500, 200);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    final JPanel panel = new JPanel();

                    Component add = frame.add(panel);
                    JTextField textField = new JTextField("Inserati numele aici", 10);
                    JTextField textField1 = new JTextField("Inserati coordonata x aici", 10);
                    JTextField textField2 = new JTextField("Inserati coordonata y aici", 10);
                    JButton button1 = new JButton("Adauga faze");

                    JLabel jLabel = new JLabel("Nume", SwingConstants.LEFT);
                    JLabel jLabel1 = new JLabel("Coordonata x:", SwingConstants.LEADING);
                    JLabel jLabel2 = new JLabel("Coordonata y:", SwingConstants.TRAILING);
                    panel.add(jLabel);
                    panel.add(textField);
                    panel.add(jLabel1);
                    panel.add(textField1);
                    panel.add(jLabel2);
                    panel.add(textField2);
                    panel.add(button1);
                    jLabel.setVisible(true);
                    jLabel1.setVisible(true);
                    jLabel2.setVisible(true);
                    button1.setVisible(true);
                    textField.setVisible(true);
                    textField1.setVisible(true);
                    textField2.setVisible(true);
                    panel.setVisible(true);
                    add.setVisible(true);
                    frame.setVisible(true);

                    button1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // JPanel panel = new JPanel();
                            final JFrame frame = new JFrame("Completati campurile");
                            frame.setSize(500, 200);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            JPanel panel = new JPanel();
                            frame.add(panel);
                            String[] choices = {"Locatii intrare", "Locatii iesire"};
                            JComboBox<String> comboBox = new JComboBox<>(choices);
                            comboBox.setVisible(true);
                            JLabel jLabel3 = new JLabel("Locatii:", SwingConstants.LEFT);
                            final JTextField jTextField = new JTextField("Inserati locatia:", 10);
                            JButton button3 = new JButton("Adauga");
                            JButton button4 = new JButton("Cancel");
                            jLabel3.setVisible(true);
                            jTextField.setVisible(true);
                            button3.setVisible(true);
                            button4.setVisible(true);
                            button4.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    frame.setVisible(false);
                                    frame.dispose();
                                }
                            });
                            if (comboBox.equals("Locatii intrare")) {
                                button3.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        arrayNumeLocatieIntrare.add(jTextField.getText());
                                    }
                                });
                            } else if (comboBox.equals("Locatii iesire")) {
                                button3.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        arrayNumeLocatieIesire.add(jTextField.getText());
                                    }
                                });
                            }
                            panel.add(comboBox);
                            panel.add(jLabel3);
                            panel.add(jTextField);
                            panel.add(button3);
                            panel.add(button4);
                            frame.setVisible(true);
                            panel.setVisible(true);
                        }
                    });

                }

            });
        }

    }



     /*       int result = 1, i, activareButonLocatii = 1, j = 0;
            ListaReteteModel retete;
            ArrayList<ListaReteteModel> listaReteteModelArray = new ArrayList<>();
            ArrayList<String> arrayNumeLocatieIntrare = new ArrayList<>();
            ArrayList<String> arrayNumeLocatieIesire = new ArrayList<>();
            ArrayList<Integer> arrayCoordLocatieIntrareX = new ArrayList<>();
            ArrayList<Integer> arrayCoordLocatieIntrareY = new ArrayList<>();
            ArrayList<Integer> arrayCoordLocatieIesireX = new ArrayList<>();
            ArrayList<Integer> arrayCoordLocatieIesireY = new ArrayList<>();

            String coordXTranzitie = null;
            String coordYTranzitie = null;
            String durataTranzitie = null;
            String coordXLocatie = null;
            String coordYLocatie = null;

            while (result != JOptionPane.CANCEL_OPTION) {
                JTextField Field1 = new JTextField();
                JTextField Field2 = new JTextField();
                JTextField Field3 = new JTextField();
                JTextField Field4 = new JTextField();
                JTextField Field5 = new JTextField();
                JTextField Field6 = new JTextField();
                JTextField Field7 = new JTextField();
                JTextField Field8 = new JTextField();
                JTextField Field9 = new JTextField();
                JTextField Field10 = new JTextField();
                JButton ButonAdaugareLocatii = new JButton("Adauga locatie");



                JPanel myPanel = new JPanel();

                myPanel.setLayout(new GridLayout(2, 2));
                myPanel.add(new JLabel("Adaugati numele tranzitiei:"));
                myPanel.add(Field1);
                myPanel.add(new JLabel("Adaugati durata:"));
                myPanel.add(Field2);
                myPanel.add(new JLabel("Adaugati coordonata x:"));
                myPanel.add(Field3);
                myPanel.add(new JLabel("Adaugati coordonata y:"));
                myPanel.add(Field4);
                myPanel.add(new JLabel("Adaugati numele locatiei de intrare:"));
                myPanel.add(Field5);
                myPanel.add(new JLabel("Adaugati numele locatiei de iesire:"));
                myPanel.add(Field6);
                myPanel.add(new JLabel("Adaugati coordonata x a locatiei de intrare:"));
                myPanel.add(Field7);
                myPanel.add(new JLabel("Adaugati coordonata y a locatiei de intrare:"));
                myPanel.add(Field8);
                myPanel.add(new JLabel("Adaugati coordonata x a locatiei de iesire:"));
                myPanel.add(Field9);
                myPanel.add(new JLabel("Adaugati coordonata y a locatiei de iesire:"));
                myPanel.add(Field10);
                myPanel.add(ButonAdaugareLocatii);

                result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Va rog inserati valorile", JOptionPane.OK_CANCEL_OPTION);
                activareButonLocatii = JOptionPane.showConfirmDialog(null, myPanel,
                        "Va rog adaugati locatii", JOptionPane.OK_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    // save data

                    int durata = Integer.parseInt(Field2.getText());
                    String numeTranzitie = Field1.getText();
                    int coordonataX = Integer.parseInt(Field3.getText());
                    int coordonataY = Integer.parseInt(Field4.getText());

                    String numeLocatieIntrare = Field5.getText();
                    if(activareButonLocatii == JOptionPane.OK_OPTION);
                    arrayNumeLocatieIntrare.add(numeLocatieIntrare);

                    String numeLocatieIesire = Field6.getText();
                    if(activareButonLocatii == JOptionPane.OK_OPTION);
                    arrayNumeLocatieIesire.add(numeLocatieIesire);

                    int coordonataXLocatieIntrare = Integer.parseInt(Field7.getText());
                    if(activareButonLocatii == JOptionPane.OK_OPTION);
                    arrayCoordLocatieIntrareX.add(coordonataXLocatieIntrare);

                    int coordonataYLocatieIntrare = Integer.parseInt(Field8.getText());
                    if(activareButonLocatii == JOptionPane.OK_OPTION);
                    arrayCoordLocatieIntrareY.add(coordonataYLocatieIntrare);

                    int coordonataXLocatieIesire = Integer.parseInt(Field9.getText());
                    if(activareButonLocatii == JOptionPane.OK_OPTION);
                    arrayCoordLocatieIesireX.add(coordonataXLocatieIesire);

                    int coordonataYLocatieIesire = Integer.parseInt(Field10.getText());
                    if(activareButonLocatii == JOptionPane.OK_OPTION);
                    arrayCoordLocatieIesireY.add(coordonataYLocatieIesire);

                    j++;

                    retete = new ListaReteteModel(coordonataX, coordonataY, arrayCoordLocatieIntrareX.get(j), arrayCoordLocatieIntrareY.get(j), arrayCoordLocatieIesireX.get(j), arrayCoordLocatieIesireY.get(j), durata, numeTranzitie, arrayNumeLocatieIntrare.get(j), arrayNumeLocatieIesire.get(j));
                    listaReteteModelArray.add(retete);
                }
            }

            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


                // root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("Aplicatie");
                doc.appendChild(rootElement);

                // staff elements
                for (int l = 0; l < listaReteteModelArray.size(); l++) {
                    Element locatie = doc.createElement("Locatie");
                    rootElement.appendChild(locatie);

                    Element numeLocatie = doc.createElement("locationName");
                    numeLocatie.appendChild(doc.createTextNode(listaReteteModelArray.get(l).getLocatieIntrare()));
                    locatie.appendChild(numeLocatie);

                    Element coordX = doc.createElement("x");
                    coordXLocatie = String.valueOf(listaReteteModelArray.get(l).getCoordXLocatieIntrare());
                    coordX.appendChild(doc.createTextNode(coordXLocatie));
                    locatie.appendChild(coordX);

                    Element coordY = doc.createElement("y");
                    coordYLocatie = String.valueOf(listaReteteModelArray.get(l).getCoordYLocatieIntrare());
                    coordY.appendChild(doc.createTextNode(coordYLocatie));
                    locatie.appendChild(coordY);

                    Element numarJetoane = doc.createElement("numarJetoane");
                    numarJetoane.appendChild(doc.createTextNode("0"));
                    locatie.appendChild(numarJetoane);

                }

                for (int m = 0; m < listaReteteModelArray.size(); m++) {
                    Element locatie = doc.createElement("Locatie");
                    rootElement.appendChild(locatie);

                    Element numeLocatie = doc.createElement("locationName");
                    numeLocatie.appendChild(doc.createTextNode(listaReteteModelArray.get(m).getLocatieIesire()));
                    locatie.appendChild(numeLocatie);

                    Element coordX = doc.createElement("x");
                    coordXLocatie = String.valueOf(listaReteteModelArray.get(m).getCoordXLocatieIesire());
                    coordX.appendChild(doc.createTextNode(coordXLocatie));
                    locatie.appendChild(coordX);

                    Element coordY = doc.createElement("y");
                    coordYLocatie = String.valueOf(listaReteteModelArray.get(m).getCoordYLocatieIesire());
                    coordY.appendChild(doc.createTextNode(coordYLocatie));
                    locatie.appendChild(coordY);

                    Element numarJetoane = doc.createElement("numarJetoane");
                    numarJetoane.appendChild(doc.createTextNode("0"));
                    locatie.appendChild(numarJetoane);

                }

                for (i = 0; i < listaReteteModelArray.size(); i++){

                    Element tranzitie = doc.createElement("Tranzitie");
                    rootElement.appendChild(tranzitie);

                    Element tranzitionName = doc.createElement("tranzitionName");
                    tranzitionName.appendChild(doc.createTextNode(listaReteteModelArray.get(i).getTransitionName()));
                    tranzitie.appendChild(tranzitionName);

                    Element tranzitieCoordX = doc.createElement("x");
                    coordXTranzitie = String.valueOf(listaReteteModelArray.get(i).getCoordXTranzitie());
                    tranzitieCoordX.appendChild(doc.createTextNode(coordXTranzitie));
                    tranzitie.appendChild(tranzitieCoordX);

                    Element tranzitieCoordY = doc.createElement("y");
                    coordYTranzitie = String.valueOf(listaReteteModelArray.get(i).getCoordYTranzitie());
                    tranzitieCoordY.appendChild(doc.createTextNode(coordYTranzitie));
                    tranzitie.appendChild(tranzitieCoordY);

                    Element durata = doc.createElement("durata");
                    durataTranzitie = String.valueOf(listaReteteModelArray.get(i).getDurata());
                    durata.appendChild(doc.createTextNode(durataTranzitie));
                    tranzitie.appendChild(durata);

                    for (int k = 0; k < listaReteteModelArray.size(); k++) {
                        Element locatieIntrare = doc.createElement("locatieIntrare");
                        locatieIntrare.appendChild(doc.createTextNode(listaReteteModelArray.get(k).getLocatieIntrare()));
                        tranzitie.appendChild(locatieIntrare);

                        Element locatieIesire = doc.createElement("locatieIesire");
                        locatieIesire.appendChild(doc.createTextNode(listaReteteModelArray.get(i).getLocatieIesire()));
                        tranzitie.appendChild(locatieIesire);

                    }

                }


                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult resultXml = new StreamResult(new File("E:\\Faculta\\Calin Muresan\\PetriNETPrototype\\Retete2_0.xml"));
                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);

                transformer.transform(source, resultXml);

                System.out.println("File saved!");

            }
            catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            }catch (TransformerException tfe) {
                tfe.printStackTrace();
            } */

    public void drawPane(){
        // a jframe here isn't strictly necessary, but it makes the example a little more real
        JFrame frame = new JFrame("InputDialog Example #1");

        // prompt the user to enter their name
        String name = JOptionPane.showInputDialog(frame, "What's your name?");

        // get the user's input. note that if they press Cancel, 'name' will be null
        System.out.printf("The user's name is '%s'.\n", name);
        System.exit(0);
    }

    public void paint(Graphics g) {
        super.paint(g);
        // desenareModel(g);
        this.listaTranzitii.desenareTranzitie(g);
        this.listaLocatii.desenareLocatie(g);
        this.g = g;
        // desenareModel(g);
    }


    public void desenareModel(Graphics g) {
        Graphics2D grafica = (Graphics2D) g;

        grafica.drawLine(900, 990, 900, 900);
        grafica.drawOval(100, 100, 25, 10);
        grafica.drawRect(100, 200, 25, 10);
        grafica.drawString("Tranzitie", 100 + 27, 200);
        grafica.draw(new Line2D.Double(0, 0, 400, 400));

    }

    public void parsareFisierComenzi(int numarComenziJetoane)
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        doc = null;
        try {
            doc = dBuilder.parse("C:\\Users\\Ovidiu\\Documents\\Comenzi.xml");
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        NodeList nList3 = doc.getElementsByTagName("Locatie");
        Element mainTopLevelElement = doc.getDocumentElement();
        NodeList allChildNodes = mainTopLevelElement.getChildNodes();
        ArrayList locationModelList = new ArrayList<Locatie>();
        for (int temp3 = 0; temp3 < nList3.getLength(); temp3++) {
            Node nNode3 = nList3.item(temp3);
            if (nNode3.getNodeType() == Node.ELEMENT_NODE ) {

                Element eElement3 = (Element) nNode3;

                String nume1 = eElement3.getElementsByTagName("locationName").item(0).getTextContent();
                Locatie l = this.listaLocatii.cautareLocatie(nume1);
                if (l != null){
                    l.numar = numarComenziJetoane;
                }
            }
        }

    }

    public void parsareFisierResurse(int tokenEchipamentConfiguration, int tokenMaterialConfiguration, int tokenPersonalConfiguration)
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        doc = null;
        try {
            doc = dBuilder.parse("C:\\Users\\Ovidiu\\Documents\\Resurse.xml");
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        NodeList nList3 = doc.getElementsByTagName("Locatie");
        Element mainTopLevelElement = doc.getDocumentElement();
        NodeList allChildNodes = mainTopLevelElement.getChildNodes();
        ArrayList locationModelList = new ArrayList<Locatie>();
        for (int temp3 = 0; temp3 < nList3.getLength(); temp3++) {
            Node nNode3 = nList3.item(temp3);
            if (nNode3.getNodeType() == Node.ELEMENT_NODE ) {

                Element eElement3 = (Element) nNode3;

                String nume1 = eElement3.getElementsByTagName("locationName").item(0).getTextContent();

                Locatie l = this.listaLocatii.cautareLocatie(nume1);
                if (l != null && l.nume.equals("Echipament")){
                    l.numar = tokenEchipamentConfiguration;
                }else if (l != null && l.nume.equals("Material"))
                    l.numar = tokenMaterialConfiguration;
                else if (l != null && l.nume.equals("Personal"))
                    l.numar = tokenPersonalConfiguration;
            }
        }
    }

    public void parsareFisierComenzi(File file)
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        doc = null;
        try {
            doc = dBuilder.parse(file);
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        NodeList nList3 = doc.getElementsByTagName("Locatie");
        Element mainTopLevelElement = doc.getDocumentElement();
        NodeList allChildNodes = mainTopLevelElement.getChildNodes();
        ArrayList locationModelList = new ArrayList<Locatie>();
        for (int temp3 = 0; temp3 < nList3.getLength(); temp3++) {
            Node nNode3 = nList3.item(temp3);
            if (nNode3.getNodeType() == Node.ELEMENT_NODE ) {

                Element eElement3 = (Element) nNode3;

                String nume1 = eElement3.getElementsByTagName("locationName").item(0).getTextContent();
                int numarJetoane = parseInt(eElement3.getElementsByTagName("numarJetoane").item(0).getTextContent());

                Locatie l = this.listaLocatii.cautareLocatie(nume1);
                if (l != null){
                    l.numar = numarJetoane;
                }
            }
        }

    }


    public void parsareFisierAprovizionareMateriale(File file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        doc = null;
        try {
            doc = dBuilder.parse(file);
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        NodeList nList1 = doc.getElementsByTagName("Tranzitie");
        NodeList nList3 = doc.getElementsByTagName("Locatie");
        Element mainTopLevelElement = doc.getDocumentElement();
        NodeList allChildNodes = mainTopLevelElement.getChildNodes();
        ArrayList locationModelList = new ArrayList<Locatie>();
        for (int temp3 = 0; temp3 < nList3.getLength(); temp3++) {
            Node nNode3 = nList3.item(temp3);
            if (nNode3.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement3 = (Element) nNode3;


                int x1 = parseInt(eElement3.getElementsByTagName("x").item(0).getTextContent());
                int y1 = parseInt(eElement3.getElementsByTagName("y").item(0).getTextContent());
                String nume1 = eElement3.getElementsByTagName("locationName").item(0).getTextContent();
                int numarJetoane = parseInt(eElement3.getElementsByTagName("numarJetoane").item(0).getTextContent());

                Locatie l = this.listaLocatii.cautareLocatie(nume1);
                if (l == null) {
                    Locatie locatie1 = new Locatie(x1, y1, nume1, numarJetoane);
                    this.listaLocatii.listaLocatii.add(locatie1);
                }
            }
        }

        for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
            Node nNode1 = nList1.item(temp1);

            if (nNode1.getNodeType() == Node.ELEMENT_NODE ) {
                Element eElement1 = (Element) nNode1;

                int x = parseInt(eElement1.getElementsByTagName("x").item(0).getTextContent());
                int y = parseInt(eElement1.getElementsByTagName("y").item(0).getTextContent());
                String nume = eElement1.getElementsByTagName("transitionName").item(0).getTextContent();
                int durata = parseInt(eElement1.getElementsByTagName("durata").item(0).getTextContent());

                //   String nume20 = eElement1.getElementsByTagName("locatieIntrare").item(0).getTextContent();
                Tranzitie t1 = new Tranzitie(x, y, nume, durata);

                NodeList nList10 = eElement1.getElementsByTagName("locatieIntrare");
                int i = nList10.getLength();
                for (int temp10 = 0; temp10 < nList10.getLength(); temp10++) {
                    Node nNode10 = nList10.item(temp10);
                    if (nNode10.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement10 = (Element) nNode10;
                        String nume10 = eElement10.getFirstChild().getTextContent();
                        //cauta locatie cu numele nume10

                        Locatie l = this.listaLocatii.cautareLocatie(nume10);
                        if (l != null){
                            t1.listaLocatiiIntrare.add(l);
                        }
                        // Locatie l = new Locatie(50*temp10,50+temp10*50,nume10,5);
                    }
                }

                NodeList nList11 = eElement1.getElementsByTagName("locatieIesire");
                int i1 = nList11.getLength();
                for (int temp11 = 0; temp11 < nList11.getLength(); temp11++) {
                    Node nNode11 = nList11.item(temp11);
                    if (nNode11.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement11 = (Element) nNode11;
                        String nume11 = eElement11.getFirstChild().getTextContent();
                        //cauta locatie cu numele nume10

                        Locatie l = this.listaLocatii.cautareLocatie(nume11);
                        if (l != null){
                            t1.listaLocatiiIesire.add(l);
                        }
                        // Locatie l = new Locatie(50*temp10,50+temp10*50,nume10,5);
                    }
                }

                this.listaTranzitii.listaTranzitii.add(t1);

            }
//.........
        }

        this.setVisible(true);
        this.pack();
        this.setSize(1000,1000);

    }

    public void parsareFisierResurse(File file)
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        doc = null;
        try {
            doc = dBuilder.parse(file);
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        NodeList nList3 = doc.getElementsByTagName("Locatie");
        Element mainTopLevelElement = doc.getDocumentElement();
        NodeList allChildNodes = mainTopLevelElement.getChildNodes();
        ArrayList locationModelList = new ArrayList<Locatie>();
        for (int temp3 = 0; temp3 < nList3.getLength(); temp3++) {
            Node nNode3 = nList3.item(temp3);
            if (nNode3.getNodeType() == Node.ELEMENT_NODE ) {

                Element eElement3 = (Element) nNode3;

                String nume1 = eElement3.getElementsByTagName("locationName").item(0).getTextContent();
                int numarJetoane = parseInt(eElement3.getElementsByTagName("numarJetoane").item(0).getTextContent());

                Locatie l = this.listaLocatii.cautareLocatie(nume1);
                if (l != null){
                    l.numar = numarJetoane;
                }



            }
        }

    }

    public void parsareFisierProduse(File file)
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        doc = null;
        try {
            doc = dBuilder.parse(file);
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        NodeList nList1 = doc.getElementsByTagName("Tranzitie");
        NodeList nList3 = doc.getElementsByTagName("Locatie");
        Element mainTopLevelElement = doc.getDocumentElement();
        NodeList allChildNodes = mainTopLevelElement.getChildNodes();
        ArrayList locationModelList = new ArrayList<Locatie>();
        for (int temp3 = 0; temp3 < nList3.getLength(); temp3++) {
            Node nNode3 = nList3.item(temp3);
            if (nNode3.getNodeType() == Node.ELEMENT_NODE ) {

                Element eElement3 = (Element) nNode3;



                int x1 = parseInt(eElement3.getElementsByTagName("x").item(0).getTextContent());
                int y1 = parseInt(eElement3.getElementsByTagName("y").item(0).getTextContent());
                String nume1 = eElement3.getElementsByTagName("locationName").item(0).getTextContent();
                int numarJetoane = parseInt(eElement3.getElementsByTagName("numarJetoane").item(0).getTextContent());

                Locatie l = this.listaLocatii.cautareLocatie(nume1);
                if (l == null){
                    Locatie locatie1 = new Locatie(x1, y1, nume1, numarJetoane);
                    this.listaLocatii.listaLocatii.add(locatie1);
                }



            }
        }



        for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
            Node nNode1 = nList1.item(temp1);

            if (nNode1.getNodeType() == Node.ELEMENT_NODE ) {
                Element eElement1 = (Element) nNode1;

                int x = parseInt(eElement1.getElementsByTagName("x").item(0).getTextContent());
                int y = parseInt(eElement1.getElementsByTagName("y").item(0).getTextContent());
                String nume = eElement1.getElementsByTagName("transitionName").item(0).getTextContent();
                int durata = parseInt(eElement1.getElementsByTagName("durata").item(0).getTextContent());

                //   String nume20 = eElement1.getElementsByTagName("locatieIntrare").item(0).getTextContent();
                Tranzitie t1 = new Tranzitie(x, y, nume, durata);

                NodeList nList10 = eElement1.getElementsByTagName("locatieIntrare");
                int i = nList10.getLength();
                for (int temp10 = 0; temp10 < nList10.getLength(); temp10++) {
                    Node nNode10 = nList10.item(temp10);
                    if (nNode10.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement10 = (Element) nNode10;
                        String nume10 = eElement10.getFirstChild().getTextContent();
                        //cauta locatie cu numele nume10

                        Locatie l = this.listaLocatii.cautareLocatie(nume10);
                        if (l != null){
                            t1.listaLocatiiIntrare.add(l);
                        }
                        // Locatie l = new Locatie(50*temp10,50+temp10*50,nume10,5);



                    }
                }

                NodeList nList11 = eElement1.getElementsByTagName("locatieIesire");
                int i1 = nList11.getLength();
                for (int temp11 = 0; temp11 < nList11.getLength(); temp11++) {
                    Node nNode11 = nList11.item(temp11);
                    if (nNode11.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement11 = (Element) nNode11;
                        String nume11 = eElement11.getFirstChild().getTextContent();
                        //cauta locatie cu numele nume10

                        Locatie l = this.listaLocatii.cautareLocatie(nume11);
                        if (l != null){
                            t1.listaLocatiiIesire.add(l);
                        }
                        // Locatie l = new Locatie(50*temp10,50+temp10*50,nume10,5);



                    }
                }


                this.listaTranzitii.listaTranzitii.add(t1);


            }
//.........
        }

            this.setVisible(true);
            this.pack();
            this.setSize(1000,1000);
    }

    public static void main(String[] args) {
        // write your code here

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                String s;


                Aplicatie a = new Aplicatie();
               // String test1 = JOptionPane.showInputDialog("Da-mi un numar");
                // Tranzitie t1 = new Tranzitie(200, 100, "Macinare", "15");


                try {

                   /* File fXmlFile = new File("C:\\Users\\Ovidiu\\Documents\\Tranzitie2.xml");
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(fXmlFile); */

                    //optional, but recommended
                    //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                  //  doc.getDocumentElement().normalize();

                    // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                    // NodeList nList = doc.getElementsByTagName("Aplicatie");

                   // NodeList nList1 = doc.getElementsByTagName("Tranzitie");
                   // NodeList nList2 = doc.getElementsByTagName("ListaLocatiiIntrare");
                   // NodeList nList3 = doc.getElementsByTagName("Locatie");
                   // NodeList nList4 = doc.getElementsByTagName("ListaLocatiiIesire");

                    //  System.out.println("----------------------------");

                  /*  Element mainTopLevelElement = doc.getDocumentElement();
                    NodeList allChildNodes = mainTopLevelElement.getChildNodes(); */

                    ArrayList locationModelList = new ArrayList<Locatie>();
              /*      for (int currIndex = 0; currIndex < nList3.getLength(); currIndex++)
             S       {
                        Node currentNode = nList3.item(currIndex);
                        NodeList currentNodeChildren = currentNode.getChildNodes();

                        int xVal = 0;
                        int yVal = 0;
                        String locationNameVal = "";
                        int numarJetoaneVal = 0;

                        for (int currentChildIndex = 0; currentChildIndex <= currentNodeChildren.getLength(); currentChildIndex++){

                            Node currentChild = currentNodeChildren.item(currentChildIndex);

                            if(currentChild.getNodeName().equals("x")){
                                xVal = Integer.parseInt(currentChild.getNodeValue());
                            }
                            if(currentChild.getNodeName().equals("y")){
                                yVal = Integer.parseInt(currentChild.getNodeValue());
                            }
                            if(currentChild.getNodeName().equals("locationName")){
                                locationNameVal = currentChild.getNodeValue();
                            }
                            if(currentChild.getNodeName().equals("numarJetoane")){
                                numarJetoaneVal = Integer.parseInt(currentChild.getNodeValue());
                            }
                        }

                        Locatie locatie = new Locatie(xVal, yVal, locationNameVal, numarJetoaneVal);

                        for(int i = 0;  i <= locationModelList.size(); i++)
                        {
                            locationModelList.add(locatie);
                            a.listaLocatii.listaLocatii.add(locatie);

                        }


                    } */






                       /*         for (int temp3 = 0; temp3 < nList3.getLength(); temp3++) {
                                    Node nNode3 = nList3.item(temp3);
                                    if (nNode3.getNodeType() == Node.ELEMENT_NODE ) {

                                        Element eElement3 = (Element) nNode3;



                                        int x1 = Integer.parseInt(eElement3.getElementsByTagName("x").item(0).getTextContent());
                                        int y1 = Integer.parseInt(eElement3.getElementsByTagName("y").item(0).getTextContent());
                                        String nume1 = eElement3.getElementsByTagName("locationName").item(0).getTextContent();
                                        int numarJetoane = Integer.parseInt(eElement3.getElementsByTagName("numarJetoane").item(0).getTextContent());

                                        Locatie locatie1 = new Locatie(x1, y1, nume1, numarJetoane);
                                        a.listaLocatii.listaLocatii.add(locatie1);


                                    }
                                }



                            for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
                                Node nNode1 = nList1.item(temp1);

                                if (nNode1.getNodeType() == Node.ELEMENT_NODE ) {
                                    Element eElement1 = (Element) nNode1;

                                    int x = Integer.parseInt(eElement1.getElementsByTagName("x").item(0).getTextContent());
                                    int y = Integer.parseInt(eElement1.getElementsByTagName("y").item(0).getTextContent());
                                    String nume = eElement1.getElementsByTagName("transitionName").item(0).getTextContent();
                                    int durata = Integer.parseInt(eElement1.getElementsByTagName("durata").item(0).getTextContent());

                                 //   String nume20 = eElement1.getElementsByTagName("locatieIntrare").item(0).getTextContent();
                                    Tranzitie t1 = new Tranzitie(x, y, nume, durata);

                                    NodeList nList10 = eElement1.getElementsByTagName("locatieIntrare");
                                   int i = nList10.getLength();
                                    for (int temp10 = 0; temp10 < nList10.getLength(); temp10++) {
                                        Node nNode10 = nList10.item(temp10);
                                        if (nNode10.getNodeType() == Node.ELEMENT_NODE) {
                                            Element eElement10 = (Element) nNode10;
                                            String nume10 = eElement10.getFirstChild().getTextContent();
                                            //cauta locatie cu numele nume10

                                            Locatie l = a.listaLocatii.cautareLocatie(nume10);
                                            if (l != null){
                                                t1.listaLocatiiIntrare.add(l);
                                            }
                                           // Locatie l = new Locatie(50*temp10,50+temp10*50,nume10,5);



                                        }
                                        }

                                    NodeList nList11 = eElement1.getElementsByTagName("locatieIesire");
                                    int i1 = nList11.getLength();
                                    for (int temp11 = 0; temp11 < nList11.getLength(); temp11++) {
                                        Node nNode11 = nList11.item(temp11);
                                        if (nNode11.getNodeType() == Node.ELEMENT_NODE) {
                                            Element eElement11 = (Element) nNode11;
                                            String nume11 = eElement11.getFirstChild().getTextContent();
                                            //cauta locatie cu numele nume10

                                            Locatie l = a.listaLocatii.cautareLocatie(nume11);
                                            if (l != null){
                                                t1.listaLocatiiIesire.add(l);
                                            }
                                            // Locatie l = new Locatie(50*temp10,50+temp10*50,nume10,5);



                                        }
                                    }


                                    a.listaTranzitii.listaTranzitii.add(t1);


                                    }
//.........


                                    ;

                                }
*/
                           // a.setVisible(true);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            };




        });
    }


}
