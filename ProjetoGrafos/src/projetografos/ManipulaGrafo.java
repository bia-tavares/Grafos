package projetografos;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

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
    private boolean conexo = true, ciclico = false, bipartido = true, euleriano = false;
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
        for (int i = 0; i < grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\nGrau : ");
            Vertice v = grafo.getVertices().get(i);
            System.out.print("  "+(v.getGrau()));
        }
        System.out.println("\n");
    }
    
    
    /**
     * Método que gerencia a busca em profundidade.
     * Esse método inicia o tempo global, 
        inicia a busca em profundidade com raiz em um vértice aleatório,
        testa se todos os vértices foram visitados e
        faz novas buscas, caso seja necessário.
     */
    public void busca(){
        
        tempo = 0;
        
        // Guarda se a aresta já foi visitada
        boolean[] arestaVisitada = new boolean[grafo.getArestas().size()];
        
        /* compConexos é o número de grafos desconexos encontrados.
           A raíz é escolhida aleatoriamente na primeira busca, baseado no numero de vertices.
        */
        compConexos = 1;
        int raiz = (int)(Math.random()*grafo.getNumeroVertices());
        buscaProfundidade(raiz, arestaVisitada);

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
                buscaProfundidade(i, arestaVisitada);
            }
        }
        
       
    }
    
    /**
     * Realiza recursivamente a busca em profundidade a partir de um índice.
     * 
     * @param _indiceRaiz - valor de índice da v da recursão.
     * @param _arestaVisitada - Lista booleana que indica se determinada aresta já foi visitada.
     * 
     */
    private void buscaProfundidade(int _indiceRaiz, boolean[] _arestaVisitada){
               
        Vertice v = grafo.getVertices().get(_indiceRaiz);
        /* Para cada novo vértice inserido, incrementa o tempo */
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
        
        /* 
           Testaremos para cada aresta do grafo se não foi visitada e 
           se é uma aresta que pertence ao vértice que está sendo explorado. 
        */
        for (int indiceAresta = 0; indiceAresta < grafo.getArestas().size(); indiceAresta++) {
            
            if(!_arestaVisitada[indiceAresta]){
                
                /* A aresta relaciona a com b da seguinte maneira: (a, b) */
                Pair<Integer,Integer> aresta = grafo.getArestas().get(indiceAresta);
                int a = aresta.getKey();
                int b = aresta.getValue();
                Vertice w = new Vertice();

                /* 
                   Se o índice de a é o mesmo de v (vértice que está sendo explorado no momento), 
                   então a==v e a aresta é (v, b). 
                   Se o índice de b é o mesmo de v, então b==v e a aresta é (a, v).
                   Testaremos se a aresta é de profundidade, de retorno, ou de avanço.
                */
                if ((a == v.getIndice()) || (b == v.getIndice())){

                    /* Colocando em w o vértice a qual v está conectado através da aresta analisada: (v,w) ou (w,v)*/
                    if(a == v.getIndice()) w = grafo.getVertices().get(b);
                    if(b == v.getIndice()) w = grafo.getVertices().get(a);
                    

                    /* Se o vértice w tiver PE == 0, significa que ainda não foi visitado.
                        Logo a aresta é de profundidade e iremos explorar w.
                        Seu pai será a v e visitaremos w.
                        A cor do vértice w é 1 por default. Iremos substituir por 1-cor(pai).
                    */
                    if (w.getPE() == 0){
                        _arestaVisitada[indiceAresta] = true;
                        w.setPai(_indiceRaiz);
                        w.setCor(1 - v.getCor() );

                        System.out.println("\n t = "+String.valueOf(tempo)+" : ");
                        System.out.println("Inserindo aresta de profundidade (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");
                        /* Guardando arestas numa lista generica representando um bloco,
                        * a medida que o algoritmo rodar, os blocos vao sendo definidos, 
                        * e as arestas removidas desta lista inicial */
                        Pair<Integer,Integer> copy_aresta = new Pair(a, b);
                        _bloco.addAresta(copy_aresta);

                        buscaProfundidade(w.getIndice(), _arestaVisitada);
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

                        /* 
                           Se w já foi visitado mas tem PS==0, significa que ainda não foi totalmente explorado.
                           Então a aresta é de retorno e iremos avisar.
                           Se existe aresta de retorno, o grafo não é acíclico.
                        */
                        
                        if (w.getPS() == 0){
                            
                            if (!ciclico) ciclico = true;
                            /* Se os 2 vértices ligados por uma aresta de retorno tiverem a mesma cor, o grafo não é bipartido */
                            if ((w.getCor() == v.getCor()) && (bipartido)) bipartido = false;
                            
                            System.out.println("Inserindo aresta de retorno (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");
                            _arestaVisitada[indiceAresta] = true;
                            Pair<Integer,Integer> copy_aresta = new Pair(a, b);
                            _bloco.addAresta(copy_aresta);

                            v.setBack(Math.min(v.getBack(), w.getPE()));
                        }

                        
                        /*
                           Grafos não direcionados não possuem arestas de avanço ou cruzamento.
                           Os filhos são totalmente explorados antes de voltar para o pai.
                        */
                        /*
                        if( (w.getPS() != 0) && (v.getPai() != w.getIndice()) ){
                            
                            // Se os 2 vértices ligados por uma aresta de retorno tiverem a mesma cor, o grafo não é bipartido 
                            if ((w.getCor() == v.getCor()) && (bipartido)) bipartido = false;
                            
                            if(w.getPS() < v.getPE()) System.out.println("Inserindo aresta de cruzamento (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");
                            else System.out.println("Inserindo aresta de avanço (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");
                            _arestaVisitada[indiceAresta] = true;
                        }
                        */
                    }

                }
            }           
        }
        
        tempo++;
        v.setPS(tempo);
        
        System.out.println("\n t = "+String.valueOf(tempo)+" : ");
        System.out.println("PS(v"+String.valueOf(_indiceRaiz+1)+"): "+String.valueOf(v.getPS()) );
    }
    
    /**
     * Método que imprime as pontes, articulações e blocos do grafo.
     */
    public void buscaPontesArticBlocos(){
        
        for (int j = 0; j < grafo.getBlocos().size(); j++) {
            for (int i = 0; i < grafo.getBlocos().get(j).getArestas().size(); i++) {
                Pair<Integer, Integer> aresta = grafo.getBlocos().get(j).getArestas().get(i);
                if(i == 0) {
                    //Se o bloco tiver apenas uma aresta, ele é considerado uma ponte
                    if(grafo.getBlocos().get(j).getArestas().size() == 1){
                        grafo.addPonte(aresta);
                        System.out.print("Bloco "+grafo.getBlocos().get(j).getID()+"(PONTE): [ ");
                    }
                    else {
                        System.out.print("Bloco "+grafo.getBlocos().get(j).getID()+": [ ");
                    }
                }
                     
                System.out.print("( v"+String.valueOf(aresta.getKey()+1)+ ", v"+String.valueOf(aresta.getValue()+1)+" )");

                if( ( i >= 0 ) && ( i < ( grafo.getBlocos().get(j).getArestas().size() - 1 ) )){
                    System.out.print(", ");
                }

                if(i == grafo.getBlocos().get(j).getArestas().size()-1) System.out.println(" ]");
            }
        }
    }
    
    /**
     * Verifica se o grafo é euleriano e, em um caso positivo, 
     * exibe um circuito eureliano do grafo.
     * Circuitos euleriando são aqueles que percorrem TODAS as arestas dro grafo, sem repeti-las
     * 
     * E o vértice inicial coinicide com o o final
     * 
     * @return se grafo é ou não euleriano.
     */
    public boolean ehEuleriano(){
        // Grafos eulerianos, precisam necessariamente ser conexos
        if(this.ehConexo() && this.compConexos==1) {
            euleriano = true;
            for (int i = 0; i < grafo.getVertices().size(); i++) {
                // E todos seus elementos precisam ter grau par                
                if((grafo.getVertices().get(i).getGrau())%2 == 1) {
                    euleriano = false;
                }
            }
        }
        return euleriano;
    }
    
    

    /**
     *  Monta um circuito euleriano partindo de um vértice aleatório.
     */
    public void montaGrafoEuleriano(){
        
        ArrayList<Vertice> visitados = new ArrayList<Vertice>();
            
        int indice = (int)(Math.random()*grafo.getNumeroVertices());
                
        buscaEuleriano(indice, visitados);
            
        for (int i = 0; i < visitados.size(); i++) {
            
            if(i == 0) System.out.print("Circuito Euleriano: [ ");
            
            System.out.print("v"+visitados.get(i).getIndice());
            
            if( ( i >= 0 ) && ( i < ( grafo.getVertices().size() - 1 ) )){
                System.out.print(", ");
            }
            
            if(i == grafo.getVertices().size()-1) System.out.println(" ]");
        }
        

   }
    
    private void buscaEuleriano(int _indice, ArrayList<Vertice> _visitado){

        Vertice v = grafo.getVertices().get(_indice);
        _visitado.add(v);
        
        
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
                
                if(!_visitado.contains(w))
                    buscaEuleriano(w.getIndice(), _visitado);
            }
        }
        
        
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
