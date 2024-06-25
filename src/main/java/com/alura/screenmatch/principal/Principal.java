package com.alura.screenmatch.principal;

import com.alura.screenmatch.model.*;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;
import org.springframework.cglib.core.Local;

import java.net.URL;
import java.sql.ClientInfoStatus;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=555af605";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();



    public void mostrarMenu(){

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Menu
                    1 - Buscar series
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    
                    0 - Salir
                    """;

            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion)
            {
                case 1:
                    buscarSerieWeb();
                    break;

                case 2:
                    buscarEpisodioPorSerie();
                    break;

                case 3:
                    mostrarSeriesBuscadas();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación ...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }



        /*
        System.out.println("Escriba el nombre de la serie que deseas buscar: ");

        //Busca los datos generales de la serie
        var nombreSerie = sc.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + APIKEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);

        System.out.println(datos);
        //Busca los datos de todas las temporadas
        List<DatosTemporada> temporadas = new ArrayList<>();
         */


        /*
        for(int i = 1; i <= datos.totalTemporadas(); i++){
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+")+ "&Season="
                    + i + APIKEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporadas);
        }
        temporadas.forEach(System.out::println);

         */

        /*
        //Mostrar solo el titulo de los episodios para las temporadas
        for (int i = 0; i < datos.totalTemporadas(); i++) {
            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            System.out.println("\nTemporada " + (i + 1));
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println((j + 1) + ".- " + episodiosTemporada.get(j).titulo());
            }

        }

         */

        //lamda
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


        /*
        //Convertir todas las informaciones a una lista de tipo DatosEpisodio

        System.out.println("Top 5 mejores episodios: ");
        List<DatosEpisodio> datosEpisodios = temporadas.stream() //Convertir a stream
                .flatMap(t -> t.episodios().stream()) //"Aplana" la lista de listas de episodios, es decir, da como resultado una sola lista con todos los episodios
                .collect(Collectors.toList()); //Convierte en una lista mutable
        //toList -> listas inmutables
        //collet(Collector.toList() -> Listas mutables

         */

        /*

        //Sacar el top 5 mejores episodios
        datosEpisodios.stream() //Convertimos en stream
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primer filtro (N/A)" + e))//filtramos y dejamos solo los que no son "N/A" (sin importar si son mayusculas o minusculas)
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .peek(e -> System.out.println("Segundo filtro (M<m)" + e))//Para comparar la evaluación de cada episodio y ordenarlo
                .limit(5).peek(e -> System.out.println("tercer filtro" + e))
                .forEach(ep -> System.out.println(ep.titulo() + " - Calificación: " + ep.evaluacion()));

        //Convirtiendo los datos de tipo episodio
         */

        /*
        List<Episodio> episodiosList = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.temporada(), d)))
                .collect(Collectors.toList());

        episodiosList.forEach(System.out::println);

         */



        /*
        //Episodios a partir de x año

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Indica el año a partir del cual deseas filtrar los episodios");
        var fecha = sc.nextInt();
        sc.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(fecha, 1,1); //Representa (año, mes, día)

        episodiosList.stream()
                .filter(e -> e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() + ", Titulo: " + e.getTitulo() + ", Fecha: " + e.getFechaLanzamiento().format(dtf)
                ));


         */

        /*
        //Busca episodios por pedazos del titulo
        System.out.println("Introduce el titulo a buscar: ");
        var pedazo = sc.nextLine();

        Optional<Episodio> episodioBucar = episodiosList.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazo.toUpperCase()))
                .findFirst();

        if (episodioBucar.isPresent()){
            System.out.println("Episodio encontrado con exito!");
            System.out.println("Datos:" + episodioBucar.get());
        }else{
            System.out.println("Episodio no encontrado");
        }

         */

        /*
        Map<Integer, Double> evaluacionesTemporada = episodiosList.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));

        System.out.println(evaluacionesTemporada);

        DoubleSummaryStatistics est = episodiosList.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));

        System.out.println("Media: " + est.getAverage());
        System.out.println("Episodio mejor evaluado: " + est.getMax());
        System.out.println("Episodio peor evaluado: " + est.getMin());

         */

    }


    private DatosSerie getDatosSerie(){
        System.out.println("Escriba el nombre de la serie que desee buscar: ");
        var nombreSerie = sc.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + APIKEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarSerieWeb(){
        DatosSerie datos = getDatosSerie();
        datosSeries.add(datos);
        System.out.println(datos);
    }

    private void buscarEpisodioPorSerie(){
        DatosSerie datosSerie = getDatosSerie();
        List<DatosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= datosSerie.totalTemporadas(); i++)
        {
            var json = consumoAPI.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&Season="
                    + i + APIKEY);
            DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void mostrarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
        series = datosSeries.stream()
                .map(e -> new Serie(e))
                .collect(Collectors.toList());

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

}
