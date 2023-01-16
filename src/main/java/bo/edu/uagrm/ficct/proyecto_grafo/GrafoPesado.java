/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.uagrm.ficct.proyecto_grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author hp
 */
public class GrafoPesado {
      protected List<List<AdyacentesConPeso>>listaDeAdyacencia;
    
    
    public GrafoPesado(){
        listaDeAdyacencia=new ArrayList<>();
    }
    
    public GrafoPesado(int nroVerticeInicial)throws ExceptionesNroVerticeInvalidos{
        
            if(nroVerticeInicial<0){
                throw new ExceptionesNroVerticeInvalidos();
            }
            this.listaDeAdyacencia=new ArrayList<>();
            
            for(int i=0;i<nroVerticeInicial;i++){
                this.insertarVertice();
            }    
    }
   
    public void insertarVertice(){
       List<AdyacentesConPeso>adyacentes=new ArrayList<>();
       listaDeAdyacencia.add(adyacentes);
    
    }
    public int cantidadDeVertices(){
        return listaDeAdyacencia.size();
    }
    
    public int gradoDelVertice(int posicionDelVertice){
        this.validarVertice(posicionDelVertice);
        List<AdyacentesConPeso>listaDeAdyacentes=listaDeAdyacencia.get(posicionDelVertice);
        return listaDeAdyacentes.size();
    }
    public void validarVertice(int posicion){
        if(posicion<0 || posicion>this.cantidadDeVertices()){
            throw new IllegalArgumentException("La posicion del vertice es invalida. El vertic no existe");
        }
    }
    
    public void insertarArista(int posicionOrigen,int posicionDestino,double peso)throws ExceptionAristaYaExiste{
            validarVertice(posicionOrigen);
            validarVertice(posicionDestino);
            if(existeAdyacencia(posicionOrigen,posicionDestino)){
                throw new ExceptionAristaYaExiste();
            }
            List<AdyacentesConPeso>listaOrigen=listaDeAdyacencia.get(posicionOrigen);
            AdyacentesConPeso nodo=new AdyacentesConPeso(posicionDestino,peso);
            
            listaOrigen.add(nodo);
            Collections.sort(listaOrigen);
              if(posicionOrigen != posicionDestino){
                   List<AdyacentesConPeso>listaDestino=listaDeAdyacencia.get(posicionDestino);
                   AdyacentesConPeso nodo1=new AdyacentesConPeso(posicionOrigen,peso);
                   listaDestino.add(nodo1); 
                   Collections.sort(listaDestino);
             }
        
    
    }
    public boolean existeAdyacencia(int posicionOrigen,int posicionDestino){
        this.validarVertice(posicionOrigen);
        this.validarVertice(posicionDestino);
        List<AdyacentesConPeso>listaDeAycentes=listaDeAdyacencia.get(posicionOrigen);
        AdyacentesConPeso nuevo=new AdyacentesConPeso(posicionDestino);
        return listaDeAycentes.contains(nuevo);
    
    }
    
    public Iterable<Integer>adyacentesDelVertice(int posicionDelVertice){
        this.validarVertice(posicionDelVertice);
        List<AdyacentesConPeso>listaDeAdyacentes=listaDeAdyacencia.get(posicionDelVertice);
        List<Integer>soloVertices=new ArrayList<>();
        for(AdyacentesConPeso vertice:listaDeAdyacentes){
            soloVertices.add(vertice.getIndiceVertice());
        }
        
        Iterable<Integer>iteradorDeAdyacentes=soloVertices;
        return iteradorDeAdyacentes;
    
    }
    
    
    public Iterable<AdyacentesConPeso>adyacentesDelVerticeConPeso(int posicionDelVertice){
        this.validarVertice(posicionDelVertice);
        List<AdyacentesConPeso>lista=this.listaDeAdyacencia.get(posicionDelVertice);
        return lista;
    }
    
    
    public int cantidadDeAristas(){
        int lazos=0;
        int aristas=0;
            for(int i=0;i<this.listaDeAdyacencia.size();i++){
                List<AdyacentesConPeso>adyacentes=listaDeAdyacencia.get(i);
                    for(AdyacentesConPeso elemento:adyacentes){
                        if(i==elemento.getIndiceVertice()){
                         lazos++;
                        }else{
                         aristas++;
                        }
                    }//fin for each
            }//fin for
            return lazos+(aristas/2);
    }
    public void elimnarArista(int posicionOrigen,int posicionDestino)throws ExceptionAristaNoExiste{
            this.validarVertice(posicionOrigen);
            this.validarVertice(posicionDestino);
            if(!this.existeAdyacencia(posicionOrigen, posicionDestino)){
                throw new ExceptionAristaNoExiste();
            }
            List<AdyacentesConPeso>listaOrigen=listaDeAdyacencia.get(posicionOrigen);
            AdyacentesConPeso aBorrar=new AdyacentesConPeso(posicionDestino,0);
            
            int posEliminar=listaOrigen.indexOf(aBorrar);
            listaOrigen.remove(posEliminar);
                if(posicionOrigen!=posicionDestino){
                      List<AdyacentesConPeso>listaDestino=listaDeAdyacencia.get(posicionDestino);
                      AdyacentesConPeso aBorrar1=new AdyacentesConPeso(posicionOrigen,0);
                      int posAEliminar=listaDestino.indexOf(aBorrar1);
                      listaDestino.remove(posAEliminar);
                }
            
    }
    
    
    public void eliminarVertice(int posVerticeAEliminar){
        this.validarVertice(posVerticeAEliminar);
        this.listaDeAdyacencia.remove(posVerticeAEliminar);
        AdyacentesConPeso eliminar=new AdyacentesConPeso(posVerticeAEliminar,0);
        for(List<AdyacentesConPeso>listaAdya:this.listaDeAdyacencia){
            int posicionAEliminarDeAdyacencia=listaAdya.indexOf(eliminar);
            if(posicionAEliminarDeAdyacencia>=0){
                listaAdya.remove(posicionAEliminarDeAdyacencia);
               
            }
            for(int i=0;i<listaAdya.size();i++){    //por que el for este si el remove hace todo el trabajo
                int posicionAdyacente=listaAdya.get(i).getIndiceVertice();
                    if(posicionAdyacente>posVerticeAEliminar){
                        AdyacentesConPeso poner=listaAdya.get(i);
                        poner.setIndiceVertice(posicionAdyacente-1);
                        listaAdya.set(i,poner);
                    }
            }
        }
    
    }
    
    public double peso(int verticeOrigen,int verticeDestino) throws ExceptionAristaNoExiste{
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
            if(!existeAdyacencia(verticeOrigen, verticeDestino)){
                throw new ExceptionAristaNoExiste();
            }
        List<AdyacentesConPeso>lista=this.listaDeAdyacencia.get(verticeOrigen);
        AdyacentesConPeso nodo=new AdyacentesConPeso(verticeDestino);
        int posicion=lista.indexOf(nodo);
        return lista.get(posicion).getPeso();
        
    
    
    }
    
    public String mostraElGrafo(){
       String s="|  ";
       
        Double [][]matriz=new Double[this.cantidadDeVertices()][this.cantidadDeVertices()];
        for(int i=0;i<this.cantidadDeVertices();i++){
            s=s+i+" | ";
        }
        s=s+"\n";
       for(int i=0;i<this.cantidadDeVertices();i++){
           for(int j=0;j<this.cantidadDeVertices();j++){
               matriz[i][j]=(double)0;
           }
       }
       
        for(int i=0;i<this.listaDeAdyacencia.size();i++){
            List<AdyacentesConPeso>adyacentes=listaDeAdyacencia.get(i);
                for(AdyacentesConPeso elemento : adyacentes){
                    matriz[i][elemento.getIndiceVertice()]=elemento.getPeso();
                }
        }
        for(int i=0;i<this.cantidadDeVertices();i++){
            s=s+i+"|";
           for(int j=0;j<this.cantidadDeVertices();j++){
               s=s+matriz[i][j]+" ";
           }
           s=s+"\n";
       }
       return s;
    }
    
}
