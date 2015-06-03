package grafos;

import java.util.ArrayList;
import org.javatuples.Pair;

/**
 * @class Representación abstracta de los nodos del grafo
 */
public class Nodo implements Comparable<Nodo> {
    //Dato como un objeo simple, información del nodo
    private Object dato;
    
    //Lista nodos adyacentes
    private ArrayList<Nodo> listaNodos;
    
    //False/True nodo no visitado/visitado
    public boolean visitado = false;
    
    //Peso del nodo
    int peso;

    /**
     * @param dato Infomación del nodo
     * @param peso peso del nodo
     * @method Construtor de un nodo
     */
    public Nodo (Object dato, int peso) {
        //Metodo sobre cargado de Nodo, inicializamos los atributos de la clase
        this.dato = dato;
        listaNodos = new ArrayList<>();
        this.peso = peso;
    }
    
    /**
     * @param nodo Nodo con el que se tiene una relación
     * @method Se agraga un nodo con el que este nodo tiene relación
     */
    public void setNodo (Nodo nodo) {
        this.listaNodos.add(nodo);
    }
    
    /**
     * @return Nombre del nodo
     * @method Getter de dato
     */
    public Object getDato(){
        return this.dato;
    }
    
    /**
     * @return Lista con los nodos abyacentes a este
     * @method Getter de listaNodos
     */
    public ArrayList<Nodo> getHijos(){
        return listaNodos;
    }
    
    /**
     * @return Peso de este nodo
     * @method Getter de peso
     */
    public int getPeso(){
        return peso;
    }
     
    /**
     * @param nodo nodo con el cuál se compara
     * @return identificador que usa el PriorityQueue
     * @method Comparador para el funcionamiento del PriorityQueue
     */
    @Override
    public int compareTo(Nodo nodo) {
        if(peso > nodo.peso) return 1;
        if(peso == nodo.peso) return 0;
        return -1;
    }
}
