package com.alura.screenmatch.principal;

import com.alura.screenmatch.service.ConsumoAPI;

import java.util.Scanner;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=555af605";


    public void mostrarMenu(){
        System.out.println("Escriba el nombre de la serie que deseas buscar: ");
        var nombreSerie = sc.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE);
        //game+of+thrones

    }
}
