package findfood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @project FindFood; Aplicación buscadora de restaurantes cercanos
 *          El projecto hace uso de grafos, dijkstra y bases de datos
 * @version 2.0.0 "Final" (02/06/2015)
 * @author Luis Miguel Mejía Suárez (201410033010)
 * @author Johan Sebastían Yepes Rios (201410049010)
 * @profesor Alberto Antonio Restrepo; Estructuras de Datos y Algoritmos II
 * 
 * @class Clae principal, el prpgrama inicia aquí
 *        Interfz inicial donde el usario esoge los parametros de busqueda
 */

public class MainInterfaz extends JFrame implements ActionListener {
    //Lista de tipos de comida en la base de datos
    private final String[] tipos = {"Almuerzo", "Asados", "Café/Bar",
                                    "Desayunos", "Fast Food", "Internacional", 
                                    "Mar", "Resposteria", "Típica", 
                                    "Vegetariana"};
    
    //Lista del rango de precios que maneja la base de datos
    private final String[] precios = {"10.000 - 20.000", "20.000 - 30.000", 
                                      "30.000 - 35.000", "35.000 - 45.000",
                                      "45.000 o más"};
    
    //Lista de las ubicaciones actuales en la base de datos
    private final String[] ublicaciones = {"Copacabana", "Bello", "Norte", 
                                           "Centro", "Sur", "Centro", "Oriente",
                                           "Occidente", "Envigado", "Itagui", 
                                           "Sabaneta"};
    
    //Elementos de la interfaz grafica
    private JComboBox tipo;
    private JComboBox precio;
    private JComboBox ubicacion;
    private JButton OK;
    private static ComponentOrientation CO;
    
    /**
     * @method Constructor of the intrefaz
     */
    private MainInterfaz () {
        //Configurar la interfaz con un GribBagLayout --------------------------
        this.setTitle("FINDFOOD");
        this.setSize(400, 150);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        CO = ComponentOrientation.LEFT_TO_RIGHT;
        c.fill = GridBagConstraints.HORIZONTAL;
        //----------------------------------------------------------------------
        
        //Agregamos los elementos a la interfaz --------------------------------
        JLabel Title = new JLabel("OPCIONES");
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        this.add(Title, c);
        
        JLabel LTipos = new JLabel("Tipo");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(LTipos, c);
        
        JLabel LPrecios = new JLabel("Precio");
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(LPrecios, c);
        
        JLabel LUbicación = new JLabel("Ubicación");
        c.gridx = 4;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(LUbicación , c);
        
        tipo = new JComboBox(tipos);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(tipo, c);
        
        precio = new JComboBox(precios);
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(precio, c);
        
        ubicacion = new JComboBox(ublicaciones);
        c.gridx = 4;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(ubicacion, c);
        
        OK = new JButton("OK");
        OK.addActionListener(this);
        c.gridx = 5;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(OK, c);
        //----------------------------------------------------------------------
    }
    
    /**
     * @method Aquí inicia la busqueda pedida por el usuario
     * @param evt evento que se activa cuando se presiona el boton 'OK'
     */
    @Override
    public void actionPerformed(java.awt.event.ActionEvent evt) {                                         
        //Obtenemos las selecciones del usuario
        String userTipo = "'" + tipo.getSelectedItem().toString() + "'";
        String userCategoria = String.valueOf(precio.getSelectedIndex()+1);
        String userUbicacion = ubicacion.getSelectedItem().toString();
        
        //Realizamos todo el procedimiento del programa
        FindFood searcher = new FindFood();
        searcher.getResults(userTipo, userCategoria, userUbicacion);
        Result results = new Result(searcher.getInformation());
        this.setVisible(false);
        results.setVisible(true);
    }                                        
    
    /**
     * @method main method all the code logic runs here
     * @param args 
     */
    public static void main (String[] args) {
        //Cambiar la interfaz grafica al estilo Nimbus -------------------------
        try {
            for (UIManager.LookAndFeelInfo info : 
                 UIManager.getInstalledLookAndFeels()) {
                
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, e);
        }
        //----------------------------------------------------------------------
        
        MainInterfaz program = new MainInterfaz();
        program.setVisible(true);
    }
}
