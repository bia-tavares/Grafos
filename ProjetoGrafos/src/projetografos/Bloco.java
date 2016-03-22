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
 * @author Beatriz Tavares e Raissa Amaral
 */
public class Bloco {
    
    private int id;
    private ArrayList<Pair<Integer, Integer>> arestas;
     
    public Bloco() {
        this.id = 0;
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
        this.arestas.add(_aresta);
    }
    
    public Pair<Integer, Integer> ultimaAresta() {
        return this.arestas.get(arestas.size()-1);
    }
    
    public  Pair<Integer, Integer> removeAresta() {
        return this.arestas.remove(arestas.size()-1);
    }

}

