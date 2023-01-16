/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.uagrm.ficct.proyecto_grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author hp
 */
public class Dijkstra {
     private double []costos;
    private int []predecesores;
    private boolean []marcados;
    private DigrafoPesado miDigrafo;
    private static final double INFINITO=Double.MAX_VALUE;
    private double costo;
    
    
    
    
    public Dijkstra(DigrafoPesado unGrafo){
        this.miDigrafo=unGrafo;
        int n=this.miDigrafo.cantidadDeVertices();
        costos=new double[n];
        marcados=new boolean[n];
        predecesores=new int[n];
        costo=0;
        for(int i=0;i<n;i++){
            costos[i]=INFINITO;
            marcados[i]=false;
            predecesores[i]=-1;
            
        }
        
    }
    public double obtenerCosto(){
        return costo;
    }
    public List<Integer> caminosMasCortos(int verticeInicio,int verticeDestino) throws ExceptionAristaNoExiste{
        costos[verticeInicio]=0;
        marcados[verticeInicio]=true;
        int enTurno=verticeInicio;
            
            while((!marcados[verticeDestino]) && (costos[enTurno]!=INFINITO)){
                 Iterable<Integer>lista=this.miDigrafo.adyacentesDelVertice(enTurno);
                    for(Integer elemento:lista){
                        if(!this.marcados[elemento]){
                            double distancia=this.miDigrafo.peso(enTurno, elemento);
                                if((distancia+(costos[enTurno]))<(costos[elemento])){
                                    costos[elemento]=distancia+(costos[enTurno]);
                                    predecesores[elemento]=enTurno;           
                                }
                        }
                    }
               marcados[enTurno]=true;
               enTurno=noMarcadoDeMenorCosto();
            }
            costo=costos[verticeDestino];
           Stack<Integer>pilaDecamino=new Stack<>();
           pilaDecamino.push(verticeDestino);
                if(costos[verticeDestino]!=INFINITO){
                       int elemento=predecesores[verticeDestino];
                            while(elemento!=-1){
                                pilaDecamino.push(elemento);
                                elemento=predecesores[elemento];
                            }
                }
                
         
        List<Integer>salida=new ArrayList<>();
            while (!pilaDecamino.isEmpty()){
                salida.add(pilaDecamino.pop());
            }
        return salida;
    }
    
    private int noMarcadoDeMenorCosto(){
        int vertice=0;
        double max=INFINITO;
            for(int i=0;i<marcados.length;i++){
               if((!marcados[i]) && (costos[i]<=max)){
                   max=costos[i];
                   vertice=i;
               }  
            }
        return vertice;
    } 
}
