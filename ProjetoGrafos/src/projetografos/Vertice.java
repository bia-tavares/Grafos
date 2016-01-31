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

    private int pE, pS, indice, pai, cor=0, back;
    
    
    public Vertice(){
        pE = pS = 0;
        pai = -1;
    }
    
    public int getPE(){
        return this.pE;
    }
    
    public int getPS(){
        return this.pS;
    }
    
    public int getIndice(){
        return this.indice;
    }
    
    public int getPai(){
        return this.pai;
    }
    
    public int getCor(){
        return cor;
    }
    
    public int getBack()
    {
        return back;
    }
    
    public void setPE(int _pE){
        this.pE = _pE;
    }
    
    public void setPS(int _pS){
        this.pS = _pS;
    }
    
    public void setIndice(int _indice){
        this.indice = _indice;
    }
    
    public void setPai(int _pai){
        this.pai = _pai;
    }
    
    public void setCor(int _cor){
        this.cor = _cor;
    }
    
    public void setBack(int _back){
        this.back = _back;
    }
}