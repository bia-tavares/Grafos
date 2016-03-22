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
    private boolean ciclico = false, bipartido = true, euleriano = false;
    private List<Pair<Integer, Integer>> pontes = new ArrayList<Pair<Integer, Integer>>();
    
    
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
    public void imprimeTabelaGrafo(Grafo _grafo){
        for (int i = 0; i < _grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\n\nVert : ");
            System.out.print(" v"+String.valueOf(i+1));
        }
        for (int i = 0; i < _grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\n  PE : ");
            Vertice v = _grafo.getVertices().get(i);
            System.out.print("  "+(v.getPE()));
        }
        for (int i = 0; i < _grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\n  PS : ");
            Vertice v = _grafo.getVertices().get(i);
            System.out.print("  "+(v.getPS()));
        }
        for (int i = 0; i < _grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\nBack : ");
            Vertice v = _grafo.getVertices().get(i);
            System.out.print("  "+(v.getBack()));
        }
        for (int i = 0; i < _grafo.getVertices().size(); i++) {
            if(i == 0) System.out.print("\nGrau : ");
            Vertice v = _grafo.getVertices().get(i);
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
    public void busca(Grafo _grafo){
        
        // Guarda se a aresta já foi visitada
        boolean[] arestaVisitada = new boolean[_grafo.getArestas().size()];
        
        /* compConexos é o número de grafos desconexos encontrados.
           A raíz é escolhida aleatoriamente na primeira busca, baseado no numero de vertices.
        */
        
        int raiz = (int)(Math.random()*_grafo.getNumeroVertices());
        buscaProfundidade(_grafo, raiz, arestaVisitada);

        /* Se o grafo for desconexo, ao sair da primeira busca existirão vértices não explorados. 
            Então devemos percorrer a lista de vértices em busca de um vértice não explorado,
            e fazer uma nova busca usando ele como v.
        */
        for(int i = 0; i < _grafo.getVertices().size(); i++){
            
            if ( _grafo.getVertices().get(i).getPE() == 0 ) {
                _grafo.addCompConexos();
                if((_grafo.getCompConexos() > 1 ) && (_grafo.getConexo() == true)) 
                    _grafo.setConexo(false);
//                System.out.println("\n\n BUSCA EM PROFUNDIDADE "+String.valueOf(compConexos)+":");
                buscaProfundidade(_grafo, i, arestaVisitada);
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
    private void buscaProfundidade(Grafo _grafo, int _indiceRaiz, boolean[] _arestaVisitada){
               
        Vertice v = _grafo.getVertices().get(_indiceRaiz);
        /* Para cada novo vértice inserido, incrementa o tempo */
        _grafo.addTempo();
        v.setPE(_grafo.getTempo());
        v.setBack(v.getPE());
        
        /* Imprime dados do vértice a ser explorado 
        String str = "Explorando v"+String.valueOf(_indiceRaiz+1)+"."+
                " PE(v"+String.valueOf((_indiceRaiz+1))+"): "+String.valueOf(v.getPE())+
                ", PS(v"+String.valueOf((_indiceRaiz+1))+"): "+String.valueOf(v.getPS())+
                ", PAI(v"+String.valueOf((_indiceRaiz+1))+"): ";
        if(v.getPai() == -1) str+="nenhum";
        else str+=String.valueOf((v.getPai()+1));
        System.out.println(str);
        */
        /* 
           Testaremos para cada aresta do grafo se não foi visitada e 
           se é uma aresta que pertence ao vértice que está sendo explorado. 
        */
        for (int indiceAresta = 0; indiceAresta < _grafo.getArestas().size(); indiceAresta++) {
            
            if(!_arestaVisitada[indiceAresta]){
                
                /* A aresta relaciona a com b da seguinte maneira: (a, b) */
                Pair<Integer,Integer> aresta = _grafo.getArestas().get(indiceAresta);
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
                    if(a == v.getIndice()) w = _grafo.getVertices().get(b);
                    if(b == v.getIndice()) w = _grafo.getVertices().get(a);
                    

                    /* Se o vértice w tiver PE == 0, significa que ainda não foi visitado.
                        Logo a aresta é de profundidade e iremos explorar w.
                        Seu pai será a v e visitaremos w.
                        A cor do vértice w é 1 por default. Iremos substituir por 1-cor(pai).
                    */
                    if (w.getPE() == 0){
                        _arestaVisitada[indiceAresta] = true;
                        w.setPai(_indiceRaiz);
                        w.setCor(1 - v.getCor() );

//                        System.out.println("\n t = "+String.valueOf(tempo)+" : ");
//                        System.out.println("Inserindo aresta de profundidade (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");

                    /* Guardando arestas numa lista generica representando um bloco,
                        * a medida que o algoritmo rodar, os blocos vao sendo definidos, 
                        * e as arestas removidas desta lista inicial */
                        Pair<Integer,Integer> copy_aresta = new Pair(a, b);
                        _grafo.set_bloco(copy_aresta);
                        buscaProfundidade(_grafo, w.getIndice(), _arestaVisitada);
                        /*O back ser maior ou igual à PE representa que o grafo deixou de seguir neste bloco e iniciou outro*/
                        if (w.getBack() >= v.getPE()) {
                            Bloco bl = new Bloco();
                            bl.setID(_grafo.addIndiceBloco());
                            /*Entao varremos na lista todas as ultimas arestas inseridas, até chegar na que atendedeu à condiçao acima*/
                            while(_grafo.get_bloco().ultimaAresta().equals(copy_aresta) == false){
                                if(_grafo.get_bloco() != null) {
                                    Pair<Integer, Integer> aa = _grafo.get_bloco().removeAresta(); 
                                    bl.addAresta(aa);
                                    
                                }
                            };

                            bl.addAresta(_grafo.get_bloco().removeAresta());
                            _grafo.addBloco(bl, _grafo.getIndiceBloco());
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
                            
//                            System.out.println("Inserindo aresta de retorno (v"+String.valueOf((_indiceRaiz+1))+",v"+String.valueOf((w.getIndice()+1))+")");
                            _arestaVisitada[indiceAresta] = true;
                            Pair<Integer,Integer> copy_aresta = new Pair(a, b);
                            _grafo.set_bloco(copy_aresta);

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
        
        _grafo.addTempo();
        v.setPS(_grafo.getTempo());
        
//        System.out.println("\n t = "+String.valueOf(tempo)+" : ");
//        System.out.println("PS(v"+String.valueOf(_indiceRaiz+1)+"): "+String.valueOf(v.getPS()) );
    }
    
    /**
     * Método que imprime as pontes, articulações e blocos do grafo.
     */
    public void buscaPontesArticBlocos(Grafo _grafo){
        
        for (int j = 0; j < _grafo.getBlocos().size(); j++) {
            for (int i = 0; i < grafo.getBlocos().get(j).getArestas().size(); i++) {
                Pair<Integer, Integer> aresta = _grafo.getBlocos().get(j).getArestas().get(i);
                if(i == 0) {
                    //Se o bloco tiver apenas uma aresta, ele é considerado uma ponte
                    if(_grafo.getBlocos().get(j).getArestas().size() == 1){
                        _grafo.addPonte(aresta);
                        System.out.print("Bloco "+_grafo.getBlocos().get(j).getID()+"(PONTE): [ ");
                    }
                    else {
                        System.out.print("Bloco "+_grafo.getBlocos().get(j).getID()+": [ ");
                    }
                }
                     
                System.out.print("( v"+String.valueOf(aresta.getKey()+1)+ ", v"+String.valueOf(aresta.getValue()+1)+" )");

                if( ( i >= 0 ) && ( i < ( _grafo.getBlocos().get(j).getArestas().size() - 1 ) )){
                    System.out.print(", ");
                }

                if(i == _grafo.getBlocos().get(j).getArestas().size()-1) System.out.println(" ]");
                
            }
        }
    }
    
    public Pair<Integer, Integer> busca_aresta(Vertice v, ArrayList<Pair<Integer, Integer>> _arestas) {
        for (int i = 0; i < _arestas.size(); i++) {
            if(_arestas.get(i).getKey() == v.getIndice() || _arestas.get(i).getValue() == v.getIndice()){
                return _arestas.get(i);
            }
        }
        return null;
    }
    public boolean ePonte(Grafo g, Pair<Integer, Integer> aresta){
        for (int i = 0; i < g.getBlocos().size(); i++) {
            //Caso o bloco tenha apenas uma aresa e essa seja a aresta passada, ela é uma ponte
            if(g.getBlocos().get(i).getArestas().size() == 1  && g.getBlocos().get(i).getArestas().get(0) == aresta){
                return true;
            }
        }
        return false;
    }
     public Pair<Integer, Integer> busca_aresta_nao_ponte(Grafo g, Vertice v, ArrayList<Pair<Integer, Integer>> _arestas) {
        for (int i = 0; i < _arestas.size(); i++) {
            if((_arestas.get(i).getKey()) == v.getIndice() || _arestas.get(i).getValue() == v.getIndice()){
                if(ePonte(g, _arestas.get(i)) == false){
                    return _arestas.get(i);
                }
            }
        }
        return null;
    }
         
    public void removeAresta (Grafo g, Pair<Integer, Integer> aresta){
        for (int i = 0; i < g.getArestas().size(); i++) {
            if(g.getArestas().get(i) == aresta){
                g.getArestas().remove(i);
            }
        }
    }
    
    public Vertice busca_vertice (Pair<Integer, Integer> a, Vertice v1, Grafo g) {
        
        int v2 = 0;
        
        if(a.getKey() == v1.getIndice()){
            v2 = a.getValue();
        }else{
            v2 = a.getKey();
        }
        System.out.print(">>>"+String.valueOf(v2));
        return g.getVertices().get(v2);
    }
    
    public  ArrayList<Vertice> Fleury() {
        
        Grafo g = grafo;
        ArrayList<Vertice> vertices = g.getVertices();
        ArrayList<Pair<Integer, Integer>> caminho = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Vertice> c = new ArrayList<Vertice>();
        //pega o primeiro vertice do grafo e coloca em c
        Vertice v = vertices.get(0);
        c.add(v);
                
        while (!g.getArestas().isEmpty()){
            //chama de v1 o ultimo elemento adicionado em c
            Vertice v1 = c.get(c.size()-1);
            Pair<Integer, Integer> a = new Pair(null, null);
            //busca a aresta que contenha v1
            if (v1.getGrau() == 1) {
                a = busca_aresta(v1, g.getArestas()); 
            }else {
                a = busca_aresta_nao_ponte(g, v1, g.getArestas());
                if(a == null) a = busca_aresta(v1, g.getArestas());
            }
            
            
            /**DUVIDA
             * Está entrando em busca_vertice com aresta nula para o exemplo com 5 vértices e 100%. 
             * Está repetindo vértice no caminho. Isso deveria acontecer?
             */
            removeAresta(g, a);
            //CHAMAR BUSCA EM PROFUNDIDADE DE NOVO
            caminho.add(a);
            Vertice v2 = busca_vertice(a, v1, g);
            c.add(v2);
            
        }
        
        
        for (int i = 0; i < c.size(); i++) {
            
            if(i == 0) System.out.print("Circuito Euleriano(por fleury): [ ");
            
            System.out.print("v"+(c.get(i).getIndice()+1));
            
            if( ( i >= 0 ) && ( i < ( grafo.getVertices().size() - 1 ) )){
                System.out.print(", ");
            }
            
            if(i == grafo.getVertices().size()-1) System.out.println(" ]");
        }
        return c;
    }

    /*
    Algoritmo de Fleury para a busca do Ciclo Euleriano
        em um grafo:
        caminho Fleury(grafo G = (V,A)) {
        v0 = v´ertice em V
        C = [v0]
        Enquanto existir aresta em A
        vi = ´ultimo v´ertice inserido em C
        Se vi possui apenas uma aresta incidente;
        ai = a aresta incidente a vi em G
        Sen˜ao
        ai = aresta de vi em G n˜ao ponte
        Apagar ai de A em G
        Inserir ai no final de C
        vj = v´ertice destino de ai
        Inserir vj na posi¸c˜ao final de C
        Retornar C
        }
    */
    
    /**
     * Verifica se o grafo é euleriano e, em um caso positivo, 
     * exibe um circuito eureliano do grafo.
     * Circuitos euleriando são aqueles que percorrem TODAS as arestas dro grafo, sem repeti-las
     * 
     * E o vértice inicial coinicide com o o final
     * 
     * @return se grafo é ou não euleriano.
     */
    public boolean ehEuleriano(Grafo _grafo){
        // Grafos eulerianos, precisam necessariamente ser conexos
        if(this.ehConexo(_grafo) && _grafo.getCompConexos()==1) {
            euleriano = true;
            for (int i = 0; i < _grafo.getVertices().size(); i++) {
                // E todos seus elementos precisam ter grau par                
                if((_grafo.getVertices().get(i).getGrau())%2 == 1) {
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
            
            System.out.print("v"+(visitados.get(i).getIndice()+1));
            
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
    public boolean ehConexo(Grafo _g){
        return _g.getConexo();
    }
    
    /**
     * Método para retorno de número de componentes conexos.
     * 
     * @return quantidade de componentes conexos
     */
//    public int numeroCompConexos(){
//        return _grafo.compConexos;
//    }
    
    
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
