package projetografos;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 *
 * @author Beatriz Tavares e Raissa Amaral
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int nVertices = 0, prob = 0;
        Grafo grafo = null;
        ManipulaGrafo manipulador = null;
        
   
        Scanner scan = new Scanner (System.in);  
        System.out.println("Quantos vértices terá no grafo?");
        nVertices = scan.nextInt();
        
        System.out.println("Qual a probabilidade usada para as arestas?");
        prob = scan.nextInt();
        
        System.out.println("");
        System.out.println("");
        
        scan.close();
        
        if ((nVertices != 0)&&(prob != 0)) 
            grafo = new Grafo(prob, nVertices);
        
        if(grafo != null) 
            manipulador = new ManipulaGrafo(grafo);
        
        
        if (manipulador != null){
            
            manipulador.imprimeGrafo();
            System.out.println("\n\n\n");
            
            manipulador.busca();
            
            if(manipulador.ehConexo()) System.out.println("\n\n O GRAFO É CONEXO!!");
            else System.out.println("\n\n O GRAFO NÃO É CONEXO!!");
            
            System.out.println("\n\n O NÚMERO DE COMPONENTES CONEXAS DO GRAFO É: " + String.valueOf(manipulador.numeroCompConexos()));
            
            if(manipulador.ehCiclico()) System.out.println("\n\n O GRAFO É CÍCLICO!!");
            else System.out.println("\n\n O GRAFO NÃO É CÍCLICO!!");
            
            if(manipulador.ehBipartido()) System.out.println("\n\n O GRAFO É BIPARTIDO!!");
            else System.out.println("\n\n O GRAFO NÃO É BIPARTIDO!!");
            
            if(manipulador.ehConexo() && !manipulador.ehCiclico()) System.out.println("\n\n É UMA ÁRVORE!!");
            else System.out.println("\n\n NÃO É UMA ÁRVORE!!");
            
            manipulador.imprimeTabelaGrafo();
            manipulador.imprimeBloco();

            
            // Depois de montar o grafo, buscar as pontes, articulações e blocos.
            //manipulador.buscaPontesArticBlocos();
        
            // Verificar se o grafo é eureliano e, em um caso positivo, exibir um circuito eureliano do grafo.
            //manipulador.ehEuleriano();
 
        }
        
    }
    
}
