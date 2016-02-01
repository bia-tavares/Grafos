/*
 * Classe que mantem dados de um bloco
 * Cada grafo possuí um número x de blocos identificados pelo indice i
 * E cada um desses blocos, possuí n arestas
 */
package projetografos;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author rpamaral
 */
public class Bloco {
    
    private int id;
    private ArrayList<Pair<Integer, Integer>> arestas;
     
    public Bloco(int _id) {
        this.id = _id;
        arestas = new ArrayList<Pair<Integer, Integer>>();
    }
    public void setID(int _i) {
        this.id = _i;
    }
    
    public int getID(){
        return this.id;
    }
    
    public ArrayList<Pair<Integer, Integer>> getArestas() {
        return this.arestas;
    }
    
    public void addAresta(Pair<Integer, Integer> _aresta) {
        arestas.add(_aresta);
    }
    
    public Pair<Integer, Integer> ultimaAresta() {
        return this.arestas.get(arestas.size()-1);
    }
    
    public  Pair<Integer, Integer> removeAresta() {
        return arestas.remove(arestas.size()-1);
    }
}
