package findfood;

import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 * @class Interfaz para mostrar los resultados al usuario
 *        Es un simple JTable con los restaurantes ordenados por proximidad
 */

public class Result extends javax.swing.JFrame {
    
    /**
     * @method Constructor de la clase
     * @param model Modelo que contiene la información de la tabla
     */    
    public Result (DefaultTableModel model) {        
        setTitle("RESULTS");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(350, 120);
        this.add(new JTable(model),BorderLayout.CENTER);
    }
}
