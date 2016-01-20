package projetografos;

/**
 *
 * Classe que mantém os dados de um vértice.
 * 
 * @author Bia Barroso
 */
public class Vertice {

    private int m_pPE, m_pPS, m_pIndice;
    
    
    public Vertice(){
        m_pPE = m_pPS = 0;
    }
    
    public int getPE(){
        return this.m_pPE;
    }
    
    public int getPS(){
        return this.m_pPS;
    }
    
    public int getIndice(){
        return this.m_pIndice;
    }
    
    public void setPE(int _pE){
        this.m_pPE = _pE;
    }
    
    public void setPS(int _pS){
        this.m_pPS = _pS;
    }
    
    public void setIndice(int _pIndice){
        this.m_pIndice = _pIndice;
    }
}
