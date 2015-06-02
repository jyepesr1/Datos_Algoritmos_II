package grafos;

import java.util.*;

/**
 * Representación de grafos en Java
 * @Version 1.1.0 (09/03/2015)
 * @Author Johan Sebatían Yepes Rios (201410049010)
 * @Author Luis Miguel Mejía Suárez (201410033010)
 *
 * @class Representación abstracta del grafo
 *        Todos los metodos del manejo del grafo estan aquí
 */

public class Grafo {
    //Declaracion de un listado que contendra a los nodos
    static private final ArrayList<Nodo> listaNodo = new ArrayList<>();
    
    //Máximo numero de vértices
    static final int MAX = 10000;
    
    //Definimos un valor grande que represente la distancia infinita inicial
    static final int INF = Integer.MAX_VALUE;
    
    //distancia[ u ] distancia de vértice inicial a vértice con ID = u
    static int distancia[ ] = new int[ MAX ];
    
    //Arreglo ´para los vértices visitados
    static boolean visitado[ ] = new boolean[ MAX ];
    
    //Cola para Dijkstra
    static PriorityQueue<Nodo> Q = new PriorityQueue<>();
    
    //Para la impresion de caminos
    static int previo[] = new int[MAX];
    
    //Representación abstracta del grafo
    static private Map<String, Integer> map;
    
    //Para el indice de cada nodo
    static int counter = 0;
    
    /**
     * @method Constructor del grafo
     * Creamos un nuevo mapa de String -> Integer
     */
    public Grafo() {
        Grafo.map = map = new HashMap<>();
    }
    
    /**
     * @method Agregamos un nodo al grafo
     * Ingreso del nodo a la lista
     * Ingreso del nodo al mapa
     * Aumentamos el indice para el proxímo nodo7
     * @param nodo
     */
    public void agregarALista (Nodo nodo) {
        listaNodo.add(nodo);
        map.put((String)nodo.getDato(), counter);
        counter++;
    }
    
    /**
     * @method Creamos una arista entre dos nodos Origen -> Destino
     * @param NodoPadre Origen
     * @param NodoHijo Destino
     * @param peso Peso de la arista
     */
    public void crearEnlace (String NodoPadre, String NodoHijo, int peso) {
        Nodo padre = buscarNodo(NodoPadre); //De donde va
        Nodo hijo = buscarNodo(NodoHijo);//A donde va
        
        if (padre != null && hijo != null) {
            //Si los dos nodos existen, creamos el enlace
            padre.setNodo(new Nodo(NodoHijo, peso));
        }
    }
    
    /**
     * @method buscamos si el nodo pertenece al grafo
     * @param nombreNodo nombre del nodo a buscar
     * @return El nodo buscado
     */
    public Nodo buscarNodo (Object nombreNodo) {
        Nodo result = null;//Nodo temporal nulo, por si no se encuentra el nodo
        
        for (Nodo nodo : listaNodo) {
            //recorre el listado de nodos
            if (((String)nombreNodo).equals(nodo.getDato().toString())) {
                //Si el nombre del nodo esta en la lista, regresa el nodo
                result = nodo;
                break;
            }
        }
        
        return result;
    }
    
    /**
     * @param primero
     * @return 
     * @method buscamos si el nodo pertenece al grafo
     */
    public Map<String, Integer> dijkstra (String primero) {
        //Preliminares de la función -------------------------------------------
        //mapa de String -> Integer, donde se guardaran los resultados
        Map<String, Integer> result = new HashMap<>();
        //Indice del nodo desde el que se inicia Dijkstra
        int inicial = map.get(primero);
        //Inicializamos nuestros arreglos
        init();
        //Insertamos el vértice inicial en la Cola de Prioridad
        Q.add(listaNodo.get(inicial));
        //Inicializamos la distancia del inicial como 0
        distancia[inicial] = 0;
        //Variables a usar en la función
        int adyacente , peso, actual;
        //----------------------------------------------------------------------
        
        while (!Q.isEmpty()) {
            //Mientras haya nodos en la Cola...
            
            //Obtenemos de la cola el nodo con menor peso
            actual = map.get((String)Q.element().getDato());
            //Sacamos el elemento de la cola
            Q.remove();
            //Si el vértice actual ya fue visitado entonces continuamos
            if(visitado[actual]) continue;
            //Marcamos como visitado el vértice actual
            visitado[actual] = true;
            
            String nodoAdyacente;
            for (int i = 0; i < listaNodo.get(actual).getHijos().size(); i++ ) {
                //Revisamos los adyacentes del vertice actual
                
                //Obtenemos el nodo abyacente
                Object temp = listaNodo.get(actual).getHijos().get(i).getDato();
                nodoAdyacente = temp.toString();
                
                //Id del vertice adayacente
                adyacente = map.get(nodoAdyacente);
                
                //Peso de la arista que une actual con adyacente
                peso = (int)listaNodo.get(actual).getHijos().get(i).getPeso();
                if (!visitado[adyacente]) {
                    //Si el vertice adyacente no fue visitado
                    //realizamos el paso de comprobacion
                    comprobacion(actual, adyacente, peso, nodoAdyacente);
                }
            }
        }
        
        for (int i = 0; i < listaNodo.size(); i++ ) {
            //Agregamos al mapa las distancía minima de cada nodo con el inicio
            result.put(listaNodo.get(i).getDato().toString(), distancia[i]);
        }
        
        return result;
    }
    
    /**
     * @method función de inicialización para Dijkstra
     */
    static void init() {
        for (int i = 0; i <= listaNodo.size(); i++) {
            //Inicializamos todas las distancias con valor infinito
            distancia[i] = INF;
            //Inicializamos todos los vértices como no visitados
            visitado[i] = false;
            //Inicializamos el previo del vertice i con -1
            previo[i] = -1;
        }
    }
    
    /**
     * @method Paso de relajacion
     */
    static void comprobacion (int actual, int adyacente, 
                              int peso, String nodoAdyacente) {
        //Si la distancia del origen al vertice actual + peso de su arista 
        //es menor a la distancia del origen al vertice adyacente
        
        if (distancia[actual] + peso < distancia[adyacente]) {
            //relajamos el vertice actualizando la distancia
            distancia[adyacente] = distancia[actual] + peso;
            //a su vez actualizamos el vertice previo
            previo[adyacente] = actual;
            //agregamos adyacente a la cola de prioridad
            Q.add(new Nodo(nodoAdyacente, peso));
        }
    }
    
    /**
     *@method Impresion del camino mas corto desde el vertice inicial y final
     */
    static void print (int destino) {
        if(previo[destino] != -1 ){   //si aun poseo un vertice previo
            print(previo[destino]); //recursivamente sigo explorando
        }
        
        for (String key : map.keySet()) {
            //terminada la recursion imprimo los vertices recorridos
            if(destino == map.get(key)) System.out.print(key + " ");
        }
    }
    
    /**
     * @method imprimir el grafo en forma de lista de abyacencía
     * @param flag
     */
    public void imprimirLista (boolean flag) {
        for (Nodo nodo : listaNodo) {
            if(!"".equals((String)nodo.getDato())){
                System.out.print((String)nodo.getDato()+"-> ");
                if(flag){
                    nodo.imprimirNodos();
                }else{
                    nodo.imprimirNodos2();
                }
            }
        }
    }
}
