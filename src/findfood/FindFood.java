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
    
    //Distancía minima entre la ubicación actual y el resto de la ciudad
    private Map<String, Integer> Proximity;
    
    //Los restaurantes en la base de datos que cumplen la busqueda del usuario
    private final ArrayList<String[]> Result;
    
    //Los restaurantes encontrados ordenados por cercania
    private String[][] SortedResult;
    
    //Modelo de la tabla con la información obtenida
    private DefaultTableModel Information;
    
    //Conexión con la base de datos
    private Connection con;

    /**
     * @method Constructor de la clase
     */
    public FindFood() {
        //Inicializamos el grafo de la ciudad y la lista de restaurantes
        this.Result = new ArrayList<>();
        String cityName = "/Cities/Medellin.gph";
        createGraph(cityName);
    }
    
    /**
     * @param fileName Nombre del archivo con el grafo a crear
     * @method Creamos un nuevo grafo de la ciudad deseada
     */
    private void createGraph (final String fileName) {
        Grafo temporal = new Grafo(); //Nuevo grafo vacio
        
        try {
            //Abrimos el archivo
            InputStream Data = FindFood.class.getResourceAsStream(fileName);
            BufferedReader File = new BufferedReader(
                                                new InputStreamReader(Data));
            StringTokenizer st = new StringTokenizer(File.readLine());
            
            //Leemos la primera linea, el nombre de todos los nodos del grafo
            while (st.hasMoreTokens()) {
                //Agregamos al grafo cada nodo
                temporal.agregarALista(new Nodo(st.nextToken(), 0));
            }
            
            //El resto del archivo contiene una rista por linea
            String Line;
            String[] Parts;
            while ((Line = File.readLine()) != null) {
                //Creamos las aristas bidireccionales
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
    
    /**
     * @param Tipo Tipo de comida buscada por el usuario
     * @param Categoria Rango de precios que el usuario desea
     * @method Aquí se busca en la base de datos los restaurantes
     *         que cumplan con las restriciones del usuario
     */
    private void getRestaurants(String Tipo, String Categoria) {
        getConnection(); //Establecempos la conexión con la base de datos
        
        try {
            //Obetenmos la información de la base de datos
            Statement estado = con.createStatement();
            ResultSet resultado = estado.executeQuery("select Nombre, "
                    + "Ubicacion from restaurantes where Tipo="
                    + Tipo +" and Categoria=" + Categoria);
            
            //Guardamos la información
            //Mientras hayan más resutlados hacer...
            while (resultado.next()) {                
                //Creamos un nuevo restaurante  y lo agragamos a la lista
                String[] restaurant = new String[2];
                restaurant[0] = resultado.getString(1); //Nombre
                restaurant[1] = resultado.getString(2); //Ubicación
                Result.add(restaurant);
            }
            
            //Cerramos la conexión 
            resultado.close();
            estado.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("Unexpected error ocurred");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * @method Establecer la conexión con la base de datos
     */
    private void getConnection() {      
        try {          
            //Abrimos la base de datos
            System.out.println("Conectando con la Base de Datos");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            String url = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql578021";
            this.con = DriverManager.getConnection(url, "sql578021","rL8*gG6!");
            System.out.println("Conexión Exitosa");           
        } catch (SQLException | ClassNotFoundException | InstantiationException 
                | IllegalAccessException ex ) {
            System.err.println("Unexpected error ocurred");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
  
    /**
     * @method Ordenamos los restaurantes por orden de cercania (Insert sort)
     */
    private void sortResults() {
        //Convertimos la lista de restaurantes en una matriz de Strings de 2*n  
        SortedResult = new String[Result.size()][2];
        SortedResult = Result.toArray(SortedResult);
        
        //Variables del Insert sort
        String Ubicacion, Restaurante;
        int J, Compare;

        //Analizamos el arreglo desde la segunda posición hatsa el final
        for (int I = 1; I < SortedResult.length; I++) {
            Restaurante = SortedResult[I][0];
            Ubicacion = SortedResult[I][1];
            Compare = Proximity.get(Ubicacion);
            J = I-1;
            
            //Mientras mi anterior sea mayor que el actual hacer...
            while (J >= 0 &&  Proximity.get(SortedResult[J][1]) > Compare) {
                //Cambiamos de posición el anteior con el actual
                SortedResult[J+1][0] = SortedResult[J][0];
                SortedResult[J+1][1] = SortedResult[J][1];
                J--;
            }
            
            SortedResult[J+1][0] = Restaurante;
            SortedResult[J+1][1] = Ubicacion;
        }
    }
    
    /**
     * @param Tipo Tipo de restaurante - comida solicitado por el usuario
     * @param Categoria Margen de precios que el usuario escogio
     * @param Ubicación Punto donde se encuentra el usuario
     * @method Es metodo gestiona todo el funcionamiejnto del pgograma
     */
    public void getResults (String Tipo, String Categoria, String Ubicación) {
        //Relizamos dijkstra desde el punto donde esta el usuario
        this.Proximity = City.dijkstra(Ubicación);
        //Relizamos la busqueda de los restaurantes
        getRestaurants(Tipo, Categoria);
        //Ordenamos los resultados
        sortResults();
    }

    /***
     * @return Modelo de una JTable con los resultados del programa
     * @method Aquí se crea la tabla con la información del programa
     */
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
