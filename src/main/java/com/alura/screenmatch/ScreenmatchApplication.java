package com.alura.screenmatch;

import com.alura.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.model.DatosSerie;
import com.alura.screenmatch.model.DatosTemporada;
import com.alura.screenmatch.principal.EjemploStreams;
import com.alura.screenmatch.principal.Principal;
import com.alura.screenmatch.repository.SerieRepository;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    @Autowired
    private SerieRepository respository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(respository);
		principal.mostrarMenu();

		//El ejemplo de las lineas siguientes solo es para ver como funcionan los streams
        //EjemploStreams ejemploStreams = new EjemploStreams();
        //ejemploStreams.muestreEjemplo();
	}
}
