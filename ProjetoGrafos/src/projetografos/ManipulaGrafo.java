package projetografos;

import javafx.util.Pair;

/**
 * Classe para manipulação de grafo.
 * 
 * @param m_pGrafo - Grafo a ser manipulado
 * @param t - tempo global de busca
 * @param m_pDesconexo - boolean que indica se o grafo é desconexo
 * 
 * 
 * @author Beatriz Tavares e Raissa Amaral
 */
public class ManipulaGrafo {
    
    private Grafo m_pGrafo;
    private int t = 0;
    private boolean m_pDesconexo = false;
    
    
    /**
     * Construtor da classe.
     * 
     * @param _g - grafo que será manipulado
     */
    public ManipulaGrafo(Grafo _g){
    
        this.m_pGrafo = _g;
    }
    
    /**
     * Método que imprime as listas de vértices e arestas
     */
    public void imprimeGrafo(){
        for (int i = 0; i < m_pGrafo.getVertices().size(); i++) {
            
            if(i == 0) System.out.print("VERTICES: [ ");
            
            System.out.print("v"+String.valueOf(i+1));
            
            if( ( i >= 0 ) && ( i < ( m_pGrafo.getVertices().size() - 1 ) )){
                System.out.print(", ");
            }
            
            if(i == m_pGrafo.getVertices().size()-1) System.out.println(" ]");
        }
        
        for (int i = 0; i < m_pGrafo.getArestas().size(); i++) {
            
            if(i == 0) System.out.print("ARESTAS: [ ");
            
            Pair<Integer, Integer> aresta = m_pGrafo.getArestas().get(i);
            System.out.print("( v"+String.valueOf(aresta.getKey()+1)+ ", v"+String.valueOf(aresta.getValue()+1)+" )");
            
            if( ( i >= 0 ) && ( i < ( m_pGrafo.getArestas().size() - 1 ) )){
                System.out.print(", ");
            }
                
            if(i == m_pGrafo.getArestas().size()-1) System.out.println(" ]");
        }
    }
    
    
    /**
     * Método que gerencia a busca em profundidade.
     * Esse método inicia o tempo global, 
     * inicia a busca em profundidade com raiz no vértice v0,
     * testa se todos os vértices foram visitados e
     * faz novas buscas, caso seja necessário.
     */
    public void buscaProfundidade(){
        
        t = 1;
        
        //número de buscas realizadas
        int nBuscas = 1;
        
        System.out.println("BUSCA EM PROFUNDIDADE "+String.valueOf(nBuscas)+":");
        System.out.println("\n t = "+String.valueOf(t)+" : ");
        buscaProfundidade(0);
        
        /* Se o grafo for desconexo, ao sair da primeira busca existirão vértices não explorados. 
            Então devemos percorrer a lista de vértices em busca de um vértice não explorado,
            e fazer uma nova busca usando ele como raiz.
        */
        for(int i = 0; i < m_pGrafo.getVertices().size(); i++){
            
            if ( m_pGrafo.getVertices().get(i).getPE() == 0 ) {
                m_pDesconexo = true;
                nBuscas++;
                System.out.println("\n\n BUSCA EM PROFUNDIDADE "+String.valueOf(nBuscas)+":");
                buscaProfundidade(i);
            }
        }
 
    }
    
    /**
     * Realiza recursivamente a busca em profundidade a partir de um índice.
     * 
     * @param _pIndiceRaiz - valor de índice da raiz da recursão.
     */
    private void buscaProfundidade(int _pIndiceRaiz){
               
        Vertice raiz = m_pGrafo.getVertices().get(_pIndiceRaiz);
        raiz.setPE(t);
        
        /* Imprime dados do vértice a ser explorado */
        String str = "Explorando v"+String.valueOf(_pIndiceRaiz+1)+"."+
                " PE(v"+String.valueOf((_pIndiceRaiz+1))+"): "+String.valueOf(raiz.getPE())+
                ", PS(v"+String.valueOf((_pIndiceRaiz+1))+"): "+String.valueOf(raiz.getPS())+
                ", PAI(v"+String.valueOf((_pIndiceRaiz+1))+"): ";
        if(raiz.getPai() == -1) str+="nenhum";
        else str+=String.valueOf((raiz.getPai()+1));
        System.out.println(str);
        
        /* Testaremos para cada aresta do grafo se é uma aresta que pertence ao vértice que está sendo explorado. */
        for (int i = 0; i < m_pGrafo.getArestas().size(); i++) {
            
            /* A aresta relaciona vx com vy da seguinte maneira: (vx, vy) */
            Pair<Integer,Integer> aresta = m_pGrafo.getArestas().get(i);
            Vertice vx = m_pGrafo.getVertices().get(aresta.getKey());
            Vertice vy = m_pGrafo.getVertices().get(aresta.getValue());
            
            /* Se o índice de vx é o mesmo da raíz (vértice que está sendo explorado no momento), 
                então vx é a raiz e a aresta é (raiz, vy)
                Testaremos se a aresta é de profundidade, de retorno, ou de avanço.
            */
            if (vx.getIndice() == raiz.getIndice()){
                
                /* Se o vértice vy tiver PE == 0, significa que ainda não foi visitado.
                    Logo a aresta é de profundidade e iremos explorar vy.
                    Seu pai será a raiz e visitaremos vy no tempo t++.
                */
                if (vy.getPE() == 0){
                    
                    vy.setPai(_pIndiceRaiz);
                    
                    t++;
                    System.out.println("\n t = "+String.valueOf(t)+" : ");
                    System.out.println("Inserindo aresta de profundidade (v"+String.valueOf((_pIndiceRaiz+1))+",v"+String.valueOf((vy.getIndice()+1))+")");
                    buscaProfundidade(aresta.getValue());
                    
                }
                /* Se o vértice vy não tiver PE == 0, significa que já foi visitado.*/
                else{
                    
                    /* Se vy já foi visitado mas tem PS==0, significa que ainda não foi totalmente explorado.
                        Se vy não é pai da raiz, então a aresta é de retorno e iremos avisar.
                    */
                    if ( (vy.getPS() == 0) && (raiz.getPai() != vy.getIndice()) ){
                        t++;
                        System.out.println("\n t = "+String.valueOf(t)+" : ");
                        System.out.println("Inserindo aresta de retorno (v"+String.valueOf((_pIndiceRaiz+1))+",v"+String.valueOf((vy.getIndice()+1))+")");
                    }
                    
                    /* Se vy já foi visitado (PE!=0) e tem PS!=0, significa que já foi totalmente explorado.
                        Se vy não é pai da raiz, então a aresta é de avanço e iremos avisar.
                    */
                    if( (vy.getPS() != 0) && (raiz.getPai() != vy.getIndice()) ){
                        t++;
                        System.out.println("\n t = "+String.valueOf(t)+" : ");
                        System.out.println("Inserindo aresta de avanço (v"+String.valueOf((_pIndiceRaiz+1))+",v"+String.valueOf((vy.getIndice()+1))+")");
                    }
                }
                
            }
                    
        }
        
        t++;
        System.out.println("\n t = "+String.valueOf(t)+" : ");
        raiz.setPS(t);
        System.out.println( "PS(v"+String.valueOf(_pIndiceRaiz+1)+"): "+String.valueOf(raiz.getPS()) );
    }
    
    /**
     * Método que diz se o grafo é desconexo.
     * Só faz sentido se a busca tiver sido rodada.
     *
     * @return true se for desconexo.
     */
    public boolean ehDesconexo(){
        return m_pDesconexo;
    }
}
