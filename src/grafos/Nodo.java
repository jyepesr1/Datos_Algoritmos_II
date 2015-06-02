package grafos;

import java.util.ArrayList;
import org.javatuples.Pair;

/**
 * @class Rspresentación abstracta de los nodos
 */

public class Nodo implements Comparable<Nodo>{
    private Object dato;//Dato como un objeo simple, información del nodo
    private ArrayList<Nodo> listaNodos; //Lista nodos adyacentes
    public boolean visitado = false;//False/True nodo -> no visitado / visitado
    Pair<String ,Integer> pair; //Tupla 
    int peso;

    public Nodo(Object dato, int peso){//metodo sobre cargado de Nodo
        this.dato = dato;//define un puntero para dato
        listaNodos = new ArrayList<>();
        this.peso = peso;
    }
    public void setDato(Object dato){//los metodos getDato y setDato sirven para poder marcar al nodo con un dato (el booleano)
        this.dato = dato;
    }
    public Object getDato(){
        return this.dato;
    }
    
    public void setNodo(Nodo nodo){//puntero para el nodo al agragarlo
        this.listaNodos.add(nodo);
    }
    
    public ArrayList<Nodo> getHijos(){//para identificar en el arreglo quien es nodo hijo y devolverlo
        return listaNodos;
    }
    
    public void imprimirNodos2(){
        for (Nodo nodo : listaNodos){
            if(!"".equals(nodo.getDato())){
                System.out.print(nodo.getDato()+" ");
            }
        } 
        System.out.println(); 
    }
    
    public int getPeso(){
        return peso;
    }

    public void imprimirNodos(){
        for (Nodo nodo : listaNodos){
            if(!"".equals(nodo.getDato())){
                System.out.print(nodo.getDato());
                System.out.print("("+nodo.getPeso()+")"+" ");
            }
        } 
        System.out.println(); 
    } 
     
    @Override
    public int compareTo(Nodo nodo) { //es necesario definir un comparador para el correcto funcionamiento del PriorityQueue
       if(peso > nodo.peso) return 1;
       if(peso == nodo.peso) return 0;
       return -1;
    }
}
