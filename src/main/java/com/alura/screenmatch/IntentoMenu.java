package com.alura.screenmatch;

import com.alura.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.model.DatosSerie;
import com.alura.screenmatch.model.DatosTemporada;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class IntentoMenu implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        var consumoApi = new ConsumoAPI();
        var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&apikey=555af605");
        System.out.println(json);

        ConvierteDatos conversor = new ConvierteDatos();

        var datos = conversor.obtenerDatos(json, DatosSerie.class);

        System.out.println(datos);

        json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=555af605");
        DatosEpisodio episodio = conversor.obtenerDatos(json, DatosEpisodio.class);
        System.out.println(episodio);

        List<DatosTemporada> temporadas = new ArrayList<>();

        for(int i = 1; i <= datos.totalTemporadas(); i++){
            json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season="
                    + i + "&apikey=555af605");
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporadas);
        }

        temporadas.forEach(System.out::println);
         */

        //Mi intento para ponerle un menu
        System.out.println("Bienvenido a screenMatch");

        while(true){
            System.out.println("Introduce ");



        }

    }

}
