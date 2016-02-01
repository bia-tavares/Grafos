package projetografos;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import jdk.nashorn.internal.objects.NativeArray;

/**
 * Classe para manipulação de grafo.
 * 
 * @param grafo - Grafo a ser manipulado
 * @param tempo - tempo global de busca
 * @param compConexos - quantidade de componentes conexos
 * @param conexo - boolean que indica se o grafo é desconexo
 * @param ciclico - boolean que indica se o grafo é ciclico
 * @param bipartido - boolean que indica se o grafo é bipartido
 * 
 * 
 * @author Beatriz Tavares e Raissa Amaral
 */
public class ManipulaGrafo {
    
    private Grafo grafo;
    private int tempo, compConexos;
    private boolean conexo = true, ciclico = false, bipartido = false;
    private List<Pair<Integer, Integer>> pontes = new ArrayList<Pair<Integer, Integer>>();
    private Bloco _bloco = new Bloco(0);
    private int indice_bloco = 0;
    
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
     * Método que imprime as listas de vértices e arestas
     */
    public void imprimeTabelaGrafo(){
        for (int i = 0; i < grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\n\nVert : ");
            System.out.print(" v"+String.valueOf(i+1));
        }
        for (int i = 0; i < grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\n  PE : ");
            Vertice v = grafo.getVertices().get(i);
            System.out.print("  "+(v.getPE()));
        }
        for (int i = 0; i < grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\n  PS : ");
            Vertice v = grafo.getVertices().get(i);
            System.out.print("  "+(v.getPS()));
        }
        for (int i = 0; i < grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\nBack : ");
            Vertice v = grafo.getVertices().get(i);
            System.out.print("  "+(v.getBack()));
        }
        System.out.println("\n");
    }
    
    
    /**
     * Método que gerencia a busca em profundidade.
     * Esse método inicia o tempo global, 
 inicia a busca em profundidade com v no vértice v0,
 testa se todos os vértices foram visitados e
 faz novas buscas, caso seja necessário.
     */
    public void busca(){
        
        tempo = 0;
        
        //número de buscas realizadas
        compConexos = 0;
                
        /* Se o grafo for desconexo, ao sair da primeira busca existirão vértices não explorados. 
            Então devemos percorrer a lista de vértices em busca de um vértice não explorado,
            e fazer uma nova busca usando ele como v.
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
     * @param _indiceRaiz - valor de índice da v da recursão.
     */
    private void buscaProfundidade(int _indiceRaiz){
               
        Vertice v = grafo.getVertices().get(_indiceRaiz);
        tempo++;
        v.setPE(tempo);
        v.setBack(v.getPE());
        
        /* Imprime dados do vértice a ser explorado */
        String str = "Explorando v"+String.valueOf(_indiceRaiz+1)+"."+
                " PE(v"+String.valueOf((_indiceRaiz+1))+"): "+String.valueOf(v.getPE())+
                ", PS(v"+String.valueOf((_indiceRaiz+1))+"): "+String.valueOf(v.getPS())+
                ", PAI(v"+String.valueOf((_indiceRaiz+1))+"): ";
        if(v.getPai() == -1) str+="nenhum";
        else str+=String.valueOf((v.getPai()+1));
        System.out.println(str);
        
        /* Testaremos para cada aresta do grafo se é uma aresta que pertence ao vértice que está sendo explorado. */
        for (int i = 0; i < grafo.getArestas().size(); i++) {
            
            /* A aresta relaciona a com b da seguinte maneira: (a, b) */
            Pair<Integer,Integer> aresta = grafo.getArestas().get(i);
            int a = aresta.getKey();
            int b = aresta.getValue();
            Vertice w = new Vertice();
            
            /* Se o índice de a é o mesmo de v (vértice que está sendo explorado no momento), 
                então a é a v e a aresta é (v, b)
                Testaremos se a aresta é de profundidade, de retorno, ou de avanço.
            */
            if ((a == v.getIndice()) || (b == v.getIndice())){
                
                /* Colocando em w o vértice a qual v está conectado através da aresta analisada*/
                if(a == v.getIndice()) w = grafo.getVertices().get(b);
                if(b == v.getIndice()) w = grafo.getVertices().get(a);
                
                /* Se o vértice w tiver PE == 0, significa que ainda não foi visitado.
                    Logo a aresta é de profundidade e iremos explorar w.
                    Seu pai será a v e visitaremos w no tempo tempo++.
                
                    A cor do vértice w é 1 por default. Iremos substituir por 1-cor(pai).
                */
                if (w.getPE() == 0){
                    
                    w.setPai(_indiceRaiz);
                    w.setCor(1 - v.getCor() );
                    
                    //COMENTEI ESSA PARTE DO TEMPO, POIS ELE NÃO É ACRESCIDO AQUI
                    //Quando a aresta é criada, o tempo deve ser adicionado no inico do procedimento
                    //tempo++;
                    System.out.println("\n t = "+String.valueOf(tempo)+" : ");
                    System.out.println("Inserindo aresta de profundidade (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");
                    /* Guardando arestas numa lista generica representando um bloco,
                    * a medida que o algoritmo rodar, os blocos vao sendo definidos, 
                    * e as arestas removidas desta lista inicial */
                    Pair<Integer,Integer> copy_aresta = new Pair(a, b);
                    _bloco.addAresta(copy_aresta);
                    
                    buscaProfundidade(w.getIndice());
                    /*O back ser maior ou igual à PE representa que o grafo deixou de seguir neste bloco e iniciou outro*/
                    if (w.getBack() >= v.getPE()) {
                        Bloco bl = new Bloco(indice_bloco++);
                        /*Entao varremos na lista todas as ultimas arestas inseridas, até chegar na que atendedeu à condiçao acima*/
                        while(_bloco.ultimaAresta().equals(copy_aresta) == false){
                            if(_bloco != null) {
                                Pair<Integer, Integer> aa = _bloco.removeAresta(); 
                                bl.addAresta(aa);
                            }
                        };

                        bl.addAresta(_bloco.removeAresta());
                        grafo.addBloco(bl, indice_bloco);
                    }
                    v.setBack(Math.min(v.getBack(), w.getBack()));
                }
                /* Se o vértice w não tiver PE == 0, significa que já foi visitado.*/
                else{
                    
                    /* Se w já foi visitado mas tem PS==0, significa que ainda não foi totalmente explorado.
                        Se w não é pai da v, então a aresta é de retorno e iremos avisar.
                        Se existe aresta de retorno, o grafo não é acíclico.
                    */
                    if ( (w.getPS() == 0) && (v.getPai() != w.getIndice()) ){
                        if (!ciclico) ciclico = true;
                        if ((w.getCor() == v.getCor()) && (!bipartido)) bipartido = true;
                        System.out.println("Inserindo aresta de retorno (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");
                        
                        Pair<Integer,Integer> copy_aresta = new Pair(a, b);
                        _bloco.addAresta(copy_aresta);
                    
                        v.setBack(Math.min(v.getBack(), w.getPE()));
                    }
                    
                    /* Se w já foi visitado (PE!=0) e tem PS!=0, significa que já foi totalmente explorado.
                        Se w não é pai da v, então a aresta é de avanço e iremos avisar.
                    */
                    if( (w.getPS() != 0) && (v.getPai() != w.getIndice()) ){
                        
                        if(w.getPS() < v.getPE()) System.out.println("Inserindo aresta de cruzamento (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");
                        else System.out.println("Inserindo aresta de avanço (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");
                    }
                }
                
            }
                        
        }
        
        tempo++;
        System.out.println("\n t = "+String.valueOf(tempo)+" : ");
        v.setPS(tempo);
        System.out.println("PS(v"+String.valueOf(_indiceRaiz+1)+"): "+String.valueOf(v.getPS()) );
    }
    
    /**
     * Método que imprime as pontes, articulações e blocos do grafo.
     */
    public void buscaPontesArticBlocos(){
        
        for (int j = 0; j < grafo.getBlocos().size(); j++) {
            for (int i = 0; i < grafo.getBlocos().get(j).getArestas().size(); i++) {
                if(i == 0) {
                    //Se o bloco tiver apenas uma aresta, ele pode ser considerado uma ponte
                    if(grafo.getBlocos().get(j).getArestas().size() == 1){
                        System.out.print("Bloco "+grafo.getBlocos().get(j).getID()+"(PONTE): [ ");
                    }
                    else {
                        System.out.print("Bloco "+grafo.getBlocos().get(j).getID()+": [ ");
                    }
                }
                     
                Pair<Integer, Integer> aresta = grafo.getBlocos().get(j).getArestas().get(i);
                System.out.print("( v"+String.valueOf(aresta.getKey()+1)+ ", v"+String.valueOf(aresta.getValue()+1)+" )");

                if( ( i >= 0 ) && ( i < ( grafo.getBlocos().get(j).getArestas().size() - 1 ) )){
                    System.out.print(", ");
                }

                if(i == grafo.getBlocos().get(j).getArestas().size()-1) System.out.println(" ]");
            }
        }
    }
    
    /**
     * Verifica se o grafo é eureliano e, em um caso positivo, 
     * exibe um circuito eureliano do grafo.
     */
    public void ehEuleriano(){
        
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
     * @return se é cíclico ou não;
     */
    public boolean ehCiclico(){
        return ciclico;
    }
    
    /**
     * @return se é bipartido ou não;
     */
    public boolean ehBipartido(){
        return bipartido;
    }
}
