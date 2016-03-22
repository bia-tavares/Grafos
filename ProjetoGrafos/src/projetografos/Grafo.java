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
    private int tempo, compConexos;
    private boolean conexo;
    private Bloco _bloco;
    private int indice_bloco;

    /**
     * Construtor da classe.
     * 
     * @param _prob - probabilidade de cada possível aresta ser inserida.
     * @param _nVertices - número de vértices do grafo.
     */
    public Grafo(){
        this.tempo = 0;
        this.compConexos = 1;
        this.conexo = true;
        this.indice_bloco = 0;
        Bloco b = new Bloco();
        this.blocos.add(0,b);
        this._bloco = b;
    } 
    
    /**
     * Constrói grafo com parâmetros passados.
     */
    public void constroiGrafo(double probabilidade, int nVertices ){
        
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
    
    public int getCompConexos(){
        return this.compConexos;
    }
    public void addCompConexos(){
        this.compConexos++;
    }
    
    public int getTempo(){
        return this.tempo;
    }
    public void addTempo(){
        this.tempo++;
    }
    public void setConexo(boolean r){
        this.conexo = r;
    }
    public boolean getConexo(){
        return this.conexo;
    }
    public Pair<Integer, Integer> set_bloco(Pair<Integer, Integer> aresta){
        this._bloco.addAresta(aresta);
        return aresta;
    }
    public int addIndiceBloco() {
        return this.indice_bloco++;
    }
    public int getIndiceBloco() {
        return this.indice_bloco;
    }
    public Bloco get_bloco(){
        return this._bloco;
    }
}
