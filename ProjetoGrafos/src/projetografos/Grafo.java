package projetografos;

import java.awt.List;
import java.util.ArrayList;
import javafx.util.Pair;



/**
 * 
 * Classe que mantém os dados do grafo. 
 * Um grafo deve conhecer seus vértices e arestas.
 *
 * 
 * @author Beatriz Tavares e Raissa Amaral
 */
public class Grafo{
    
    private ArrayList<Vertice> m_pVertices = new ArrayList<Vertice>();
    private ArrayList<Pair<Integer, Integer>> m_pArestas = new ArrayList<Pair<Integer, Integer>>();
    private int m_pNVertices;
    private double m_pProb;
    
    /**
     * Construtor da classe.
     * 
     * @param _prob - probabilidade de cada possível aresta ser inserida.
     * @param _nVertices - número de vértices do grafo.
     */
    public Grafo( double _prob, int _nVertices ){
        
        this.m_pProb = _prob;
        this.m_pNVertices = _nVertices;        
       
        constroiGrafo();
    }
    
    /**
     * Constrói grafo com parâmetros passados.
     */
    private void constroiGrafo(){
        
        for (int i = 0; i < m_pNVertices; i++) {
           Vertice v = new Vertice();
           v.setIndice(i);
           m_pVertices.add(v);
        }
        
        for ( int i = 0; i < ( m_pVertices.size() - 1 ); i++ ) {
            
            for ( int j = i; j < m_pVertices.size(); j++ ) {
                
                double chance = Math.random()*100;
                
                if (chance <= m_pProb){
                    Pair<Integer, Integer> aresta = new Pair( i, j);
                    m_pArestas.add(aresta);
                }
                
            }
            
        }

    }
    
    public ArrayList<Vertice> getVertices(){
        return m_pVertices;
    }
    
    public ArrayList<Pair<Integer, Integer>> getArestas(){
        return m_pArestas;
    }
    
    public int getNumeroVertices(){
        return m_pNVertices;
    }
    
    public double getProbabilidade(){
        return m_pProb;
    }
    
    
    
    
}
