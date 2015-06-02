package findfood;

import grafos.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 * @class Clase lógica donde se ejcutan todas las computaciones del programa
 */

public final class FindFood {
    
    //Representación abstracta de la ciudad como un grafo
    private Grafo City;
    //Distancía entre 
    private Map<String, Integer> Proximity;
    private final ArrayList<String[]> Result; //Result of the user search
    private String[][] SortedResult; //Restaurants sorted by proximity
    private DefaultTableModel Information; //All the information in one string
    private Connection con;

    public FindFood() {
        this.Result = new ArrayList<>();
        String cityName = "/Cities/Medellin.gph";
        createGraph(cityName);
    }
    
    private void createGraph (final String fileName) {
        //here we create the 'City' graph of cityName
        Grafo temporal = new Grafo();
        
        try {
            //Open The File
            InputStream Data = FindFood.class.getResourceAsStream(fileName);
            BufferedReader File = new BufferedReader(new InputStreamReader(Data));
            StringTokenizer st = new StringTokenizer(File.readLine());
            
            //Read the first line of the file (Nodes)
            while (st.hasMoreTokens()) {
                temporal.agregarALista(new Nodo(st.nextToken(), 0));
            }
            
            //Read the rest of the file (Edges)
            String Line;
            String[] Parts;
            while ((Line = File.readLine()) != null) {
                Parts = Line.split(" ");
                temporal.crearEnlace(Parts[0], Parts[1], 
                        Integer.parseInt(Parts[2]));
                temporal.crearEnlace(Parts[1], Parts[0], 
                        Integer.parseInt(Parts[2]));
            }            
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error: " + ex.getMessage());
            System.exit(1);
        }
        
        this.City = temporal;
    }
    
    private void getRestaurants(String Tipo, String Categoria) {
        //Here We serach in the DB for the restaurants matching
        getConnection();
        
        try {
            //Get Data
            Statement estado = con.createStatement();
            ResultSet resultado = estado.executeQuery("select Nombre, "
                    + "Ubicacion from restaurantes where Tipo="
                    + Tipo +" and Categoria=" + Categoria);
            
            while (resultado.next()) {
                String[] restaurant = new String[2];
                restaurant[0] = resultado.getString(1);
                restaurant[1] = resultado.getString(2);
                Result.add(restaurant);
            }
            
            //Close the connection
            resultado.close();
            estado.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("Unexpected error ocurred");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
    
    private void getConnection() {
        //Here we create a connection with the DB
        
        try {          
            //Open Data Base
            System.out.println("Conectando con la Base de Datos");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            String url = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql578021";
                this.con = DriverManager.getConnection(url, "sql578021", "rL8*gG6!");
            System.out.println("Conexión Exitosa");           
        } catch (SQLException | ClassNotFoundException | InstantiationException 
                | IllegalAccessException ex ) {
            System.err.println("Unexpected error ocurred");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
  
    /**
     *
     * @return
     */
    private void sortResults() {
        //Here we order the restaurants in order of proximity (Insert Sort)
        SortedResult = new String[Result.size()][2];
        SortedResult = Result.toArray(SortedResult);
        
        String Ubicacion, Restaurante;
        int J, Compare;

        for (int I = 1; I < SortedResult.length; I++) {
            Restaurante = SortedResult[I][0];
            Ubicacion = SortedResult[I][1];
            Compare = Proximity.get(Ubicacion);
            J = I-1;
            
            while (J >= 0 &&  Proximity.get(SortedResult[J][1]) > Compare) {
                SortedResult[J+1][0] = SortedResult[J][0];
                SortedResult[J+1][1] = SortedResult[J][1];
                J--;
            }
            
            SortedResult[J+1][0] = Restaurante;
            SortedResult[J+1][1] = Ubicacion;
        }
    }

    public void getResults (String Tipo, String Categoria, String Ubicación) {
        //Here are all the appliaction logic
        this.Proximity = City.dijkstra(Ubicación);
        getRestaurants(Tipo, Categoria);
        sortResults();
    }

    public DefaultTableModel getInformation() {
        this.Information = new DefaultTableModel();
        Information.addColumn("NAME");
        Information.addColumn("UBICACIÓN");            

        for (String[] datos : SortedResult) {
            Information.addRow(datos);
        }        
        
        return Information;
    }
}
