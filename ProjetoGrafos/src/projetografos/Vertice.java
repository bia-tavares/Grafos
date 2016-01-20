package projetografos;

/**
 *
 * Classe que mantém os dados de um vértice.
 * 
 * @author Bia Barroso
 */
public class Vertice {

    private int pE, pS;
    
    
    public Vertice(){
        pE = pS = 0;
    }
    
    public int getPE(){
        return this.pE;
    }
    
    public int getPS(){
        return this.pS;
    }
    
    public void setPE(int _pE){
        this.pE = _pE;
    }
    
    public void setPS(int _pS){
        this.pS = _pS;
    }
}
