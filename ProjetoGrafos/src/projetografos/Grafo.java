package projetografos;

import java.awt.List;
import java.util.ArrayList;
import javafx.util.Pair;



/**
 * 
 * Classe que mantém os dados do grafo. 
 * Um grafo deve conhecer seus vértices, arestas, 
 * o número de vértices existentes e 
 * a probabilidade de uma aresta existir
 * 
 * @author Beatriz Tavares e Raissa Amaral
 */
public class Grafo{
    
    private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    private ArrayList<Pair<Integer, Integer>> arestas = new ArrayList<Pair<Integer, Integer>>();
    private int nVertices;
    private double probabilidade;
    private ArrayList<Bloco> blocos = new ArrayList<Bloco>();
    private ArrayList<Pair<Integer, Integer>> pontes = new ArrayList<Pair<Integer, Integer>>();

    /**
     * Construtor da classe.
     * 
     * @param _prob - probabilidade de cada possível aresta ser inserida.
     * @param _nVertices - número de vértices do grafo.
     */
    public Grafo( double _prob, int _nVertices ){
        
        this.probabilidade = _prob;
        this.nVertices = _nVertices;    
        constroiGrafo();
    }
    
    /**
     * Constrói grafo com parâmetros passados.
     */
    private void constroiGrafo(){
        
        for (int i = 0; i < nVertices; i++) {
           Vertice v = new Vertice();
           v.setIndice(i);
           vertices.add(v);
        }
        
        for ( int i = 0; i < ( vertices.size() - 1 ); i++ ) {
            
            for ( int j = i+1; j < vertices.size(); j++ ) {
                
                double chance = Math.random()*100;
                
                /* Testando se i é diferente de j para não ter aresta de v para v (v,v)*/
                if ((chance <= probabilidade)){
                    Pair<Integer, Integer> aresta = new Pair( i, j);
                    arestas.add(aresta);
                    vertices.get(i).addGrau();
                    vertices.get(j).addGrau();
                }
                
            }
        }
        Bloco b = new Bloco(0);
        blocos.add(0,b); 
    }
    
    public ArrayList<Vertice> getVertices(){
        return vertices;
    }
    
    public ArrayList<Pair<Integer, Integer>> getArestas(){
        return arestas;
    }
    
    public int getNumeroVertices(){
        return nVertices;
    }
    
    public double getProbabilidade(){
        return probabilidade;
    }
    
    public ArrayList<Bloco> getBlocos(){
        return blocos;
    }
    
    public Bloco addBloco(Bloco _b, int i) {
        _b.setID(i);
        blocos.add(_b);
        return _b;
    }
    
    public void addPonte(Pair<Integer, Integer> _aresta) {
        this.pontes.add(_aresta);
    }
    
    public ArrayList<Pair<Integer, Integer>> getPontes(){
        return pontes;
    }
    
}
