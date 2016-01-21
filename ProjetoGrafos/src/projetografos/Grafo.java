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
 * @author Bia Barroso
 */
public class Grafo {
    
    private ArrayList<Vertice> m_pVertices = new ArrayList<Vertice>();
    private ArrayList<Pair<Vertice, Vertice>> m_pArestas = new ArrayList<Pair<Vertice, Vertice>>();
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
        
        for (int i = 0; i < m_pNVertices; i++) {
           Vertice v = new Vertice();
           v.setIndice(i);
           m_pVertices.add(v);
        }
        
        for ( int i = 0; i < ( m_pVertices.size() - 1 ); i++ ) {
            
            for ( int j = i; j < m_pVertices.size(); j++ ) {
                
                double chance = Math.random()*100;
                
                if (chance <= m_pProb){
                    Pair<Vertice, Vertice> aresta = new Pair( m_pVertices.get(i), m_pVertices.get(j));
                    m_pArestas.add(aresta);
                }
                
            }
            
        }
        
       
    }
    
    /**
     * Método que imprime as listas de vértices e arestas
     */
    public void imprime(){
        for (int i = 0; i < m_pVertices.size(); i++) {
            
            if(i == 0) System.out.print("VERTICES: [ ");
            
            System.out.print("v"+String.valueOf(i+1)+ " ");
            
            if(i == m_pNVertices-1) System.out.println(" ]");
        }
        
        for (int i = 0; i < m_pArestas.size(); i++) {
            
            if(i == 0) System.out.print("ARESTAS: [ ");
            
            Pair<Vertice, Vertice> aresta = m_pArestas.get(i);
            System.out.print("( v"+String.valueOf(aresta.getKey().getIndice()+1)+ ", v"+String.valueOf(aresta.getValue().getIndice()+1)+" ), ");
            
            if(i == m_pArestas.size()-1) System.out.print(" ]");
        }
    }
    
    
}
