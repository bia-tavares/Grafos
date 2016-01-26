package projetografos;

import javafx.util.Pair;

/**
 * Classe para manipulação de grafo.
 * 
 * @param grafo - Grafo a ser manipulado
 * @param tempo - tempo global de busca
 * @param compConexos - quantidade de componentes conexos
 * @param ehConexo - boolean que indica se o grafo é desconexo
 * @param ehBipartido - boolean que indica se o grafo é bipartido
 * 
 * 
 * @author Beatriz Tavares e Raissa Amaral
 */
public class ManipulaGrafo {
    
    private Grafo grafo;
    private int tempo, compConexos;
    private boolean conexo = true, ciclico = false, bipartido = false;
    
    
    /**
     * Construtor da classe.
     * 
     * @param _g - grafo que será manipulado
     */
    public ManipulaGrafo(Grafo _g){
    
        this.grafo = _g;
    }
    
    /**
     * Método que imprime as listas de vértices e arestas
     */
    public void imprimeGrafo(){
        for (int i = 0; i < grafo.getVertices().size(); i++) {
            
            if(i == 0) System.out.print("VERTICES: [ ");
            
            System.out.print("v"+String.valueOf(i+1));
            
            if( ( i >= 0 ) && ( i < ( grafo.getVertices().size() - 1 ) )){
                System.out.print(", ");
            }
            
            if(i == grafo.getVertices().size()-1) System.out.println(" ]");
        }
        
        for (int i = 0; i < grafo.getArestas().size(); i++) {
            
            if(i == 0) System.out.print("ARESTAS: [ ");
            
            Pair<Integer, Integer> aresta = grafo.getArestas().get(i);
            System.out.print("( v"+String.valueOf(aresta.getKey()+1)+ ", v"+String.valueOf(aresta.getValue()+1)+" )");
            
            if( ( i >= 0 ) && ( i < ( grafo.getArestas().size() - 1 ) )){
                System.out.print(", ");
            }
                
            if(i == grafo.getArestas().size()-1) System.out.println(" ]");
        }
    }
    
    
    /**
     * Método que gerencia a busca em profundidade.
     * Esse método inicia o tempo global, 
     * inicia a busca em profundidade com raiz no vértice v0,
     * testa se todos os vértices foram visitados e
     * faz novas buscas, caso seja necessário.
     */
    public void busca(){
        
        tempo = 1;
        
        //número de buscas realizadas
        compConexos = 0;
                
        /* Se o grafo for desconexo, ao sair da primeira busca existirão vértices não explorados. 
            Então devemos percorrer a lista de vértices em busca de um vértice não explorado,
            e fazer uma nova busca usando ele como raiz.
        */
        for(int i = 0; i < grafo.getVertices().size(); i++){
            
            if ( grafo.getVertices().get(i).getPE() == 0 ) {
                compConexos++;
                if((compConexos > 1 ) && (conexo == true)) 
                    conexo = false;
                System.out.println("\n\n BUSCA EM PROFUNDIDADE "+String.valueOf(compConexos)+":");
                buscaProfundidade(i);
            }
        }
 
    }
    
    /**
     * Realiza recursivamente a busca em profundidade a partir de um índice.
     * 
     * @param _indiceRaiz - valor de índice da raiz da recursão.
     */
    private void buscaProfundidade(int _indiceRaiz){
               
        Vertice raiz = grafo.getVertices().get(_indiceRaiz);
        raiz.setPE(tempo);
        
        /* Imprime dados do vértice a ser explorado */
        String str = "Explorando v"+String.valueOf(_indiceRaiz+1)+"."+
                " PE(v"+String.valueOf((_indiceRaiz+1))+"): "+String.valueOf(raiz.getPE())+
                ", PS(v"+String.valueOf((_indiceRaiz+1))+"): "+String.valueOf(raiz.getPS())+
                ", PAI(v"+String.valueOf((_indiceRaiz+1))+"): ";
        if(raiz.getPai() == -1) str+="nenhum";
        else str+=String.valueOf((raiz.getPai()+1));
        System.out.println(str);
        
        /* Testaremos para cada aresta do grafo se é uma aresta que pertence ao vértice que está sendo explorado. */
        for (int i = 0; i < grafo.getArestas().size(); i++) {
            
            /* A aresta relaciona vx com vy da seguinte maneira: (vx, vy) */
            Pair<Integer,Integer> aresta = grafo.getArestas().get(i);
            Vertice vx = grafo.getVertices().get(aresta.getKey());
            Vertice vy = grafo.getVertices().get(aresta.getValue());
            
            /* Se o índice de vx é o mesmo da raíz (vértice que está sendo explorado no momento), 
                então vx é a raiz e a aresta é (raiz, vy)
                Testaremos se a aresta é de profundidade, de retorno, ou de avanço.
            */
            if (vx.getIndice() == raiz.getIndice()){
                
                /* Se o vértice vy tiver PE == 0, significa que ainda não foi visitado.
                    Logo a aresta é de profundidade e iremos explorar vy.
                    Seu pai será a raiz e visitaremos vy no tempo tempo++.
                
                    A cor do vértice Vy é 1 por default. Iremos substituir por 1-cor(pai).
                */
                if (vy.getPE() == 0){
                    
                    vy.setPai(_indiceRaiz);
                    vy.setCor( 1 - raiz.getCor() );
                    
                    tempo++;
                    System.out.println("\n t = "+String.valueOf(tempo)+" : ");
                    System.out.println("Inserindo aresta de profundidade (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((vy.getIndice()+1))+")");
                    buscaProfundidade(aresta.getValue());
                    
                }
                /* Se o vértice vy não tiver PE == 0, significa que já foi visitado.*/
                else{
                    
                    /* Se vy já foi visitado mas tem PS==0, significa que ainda não foi totalmente explorado.
                        Se vy não é pai da raiz, então a aresta é de retorno e iremos avisar.
                        Se existe aresta de retorno, o grafo não é acíclico.
                    */
                    if ( (vy.getPS() == 0) && (raiz.getPai() != vy.getIndice()) ){
                        if (!ciclico) ciclico = true;
                        System.out.println("Inserindo aresta de retorno (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((vy.getIndice()+1))+")");
                    }
                    
                    /* Se vy já foi visitado (PE!=0) e tem PS!=0, significa que já foi totalmente explorado.
                        Se vy não é pai da raiz, então a aresta é de avanço e iremos avisar.
                    */
                    if( (vy.getPS() != 0) && (raiz.getPai() != vy.getIndice()) ){
                        
                        if(vy.getPS() < raiz.getPE()) System.out.println("Inserindo aresta de cruzamento (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((vy.getIndice()+1))+")");
                        else System.out.println("Inserindo aresta de avanço (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((vy.getIndice()+1))+")");
                    }
                }
                
            }
                    
        }
        
        tempo++;
        System.out.println("\n t = "+String.valueOf(tempo)+" : ");
        raiz.setPS(tempo);
        System.out.println("PS(v"+String.valueOf(_indiceRaiz+1)+"): "+String.valueOf(raiz.getPS()) );
    }
    
    /**
     * Método que diz se o grafo é desconexo.
     * Só faz sentido se a busca tiver sido rodada.
     *
     * @return true se for desconexo.
     */
    public boolean ehConexo(){
        return conexo;
    }
    
    /**
     * Método para retorno de número de componentes conexos.
     * 
     * @return quantidade de componentes conexos
     */
    public int numeroCompConexos(){
        return compConexos;
    }
    
    
    /**
     * @return se é acíclico ou não;
     */
    public boolean ehCiclico(){
        return ciclico;
    }
}
