package projetografos;

/**
 *
 * Classe que mantém os dados de um vértice.
 * Um vértice conhece sua profundidade de entrada e de saída, 
 * seu índice e o índice de seu pai.
 * 
 * @author Beatriz Tavares e Raissa Amaral
 */
public class Vertice {

    private int m_pPE, m_pPS, m_pIndice, m_pPai;
    
    
    public Vertice(){
        m_pPE = m_pPS = 0;
        m_pPai = -1;
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
    
    public int getPai(){
        return this.m_pPai;
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
    
    public void setPai(int _pPai){
        this.m_pPai = _pPai;
    }
}
