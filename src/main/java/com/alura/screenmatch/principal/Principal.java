package com.alura.screenmatch.principal;

import com.alura.screenmatch.model.*;
import com.alura.screenmatch.repository.SerieRepository;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;
import org.springframework.cglib.core.Local;

import javax.swing.text.html.Option;
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
    //private List<DatosSerie> datosSeries = new ArrayList<>();
    private List<Serie> series;
    private Optional<Serie> serieBuscada;

    private SerieRepository repositorio;

    public Principal(SerieRepository respository) {
        this.repositorio = respository;
    }


    public void mostrarMenu(){

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Menu
                    1 - Buscar series
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo
                    5 - Top 5 Series
                    6 - Buscar series por categoría
                    7 - Buscar por número máximo de temporadas y evaluación
                    8 - Buscar episodios por titulo
                    9 - Buscar top 5 episodios
                    
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

                case 4:
                    buscarSeriesPorTitulo();
                    break;

                case 5:
                    buscarTop5();
                    break;

                case 6:
                    buscarSerieCategoria();
                    break;

                case 7:
                    buscarTempAndEval();
                    break;

                case 8:
                    buscarEpisodioPorNombre();
                    break;

                case 9:
                    buscarTop5Episodios();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación ...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }

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
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }

    private void buscarEpisodioPorSerie(){
        //DatosSerie datosSerie = getDatosSerie();
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie para ver sus episodios: ");
        var nombreSerie = sc.nextLine();

        //Necesitamos una busqueda que puede o no tener resultado
        Optional<Serie> serie = series.stream().
                filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent())
        {
            var serieEncontrada = serie.get();
            List<DatosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++)
            {
                var json = consumoAPI.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&Season="
                        + i + APIKEY);
                DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.temporada(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
    }

    private void mostrarSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo() {
        System.out.println("Escribe el nombre de la serie: ");
        var nombreSerie = sc.nextLine();

        serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if (serieBuscada.isPresent()){
            System.out.println("La serie encontrada es: " + serieBuscada.get());

        }else{
            System.out.println("Serie no encontrada");
        }
    }

    private void buscarTop5(){
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s -> System.out.println("Titulo: " + s.getTitulo() + ", Evaluación: " + s.getEvaluacion()));
    }

    private void buscarSerieCategoria(){
        System.out.println("Ingrese la categoría que desee buscar: ");
        var categoriaBuscar = sc.nextLine();

        var categoria = Categoria.fromEspanol(categoriaBuscar);

        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);

        System.out.println("Las series de la categoria con el genero " + categoria.name());
        seriesPorCategoria.forEach(s -> System.out.println("Titulo: " + s.getTitulo()));
    }

    private void buscarTempAndEval(){
        System.out.println("Ingrese el maximo de temporadas: ");
        var maxTemporadas = sc.nextInt();
        System.out.println("Ingrese la calificación minima: ");
        var minCalificacion = sc.nextDouble();

        List<Serie> busquedaSeries = repositorio.buscarPorTemporadaYEvaluacion(maxTemporadas, minCalificacion);
        if (!busquedaSeries.isEmpty()){
        busquedaSeries.forEach(s -> System.out.println("Titulo: " + s.getTitulo() +
                ", Temporadas: " + s.getTotalTemporadas() + ", Calificación: " + s.getEvaluacion()));
        }else{
            System.out.println("No se encontró ninguna serie que corresponda a su busqueda");
        }

    }

    private void buscarEpisodioPorNombre(){
        System.out.println("Escribe el nombre del episodio a buscar: ");
        var nombreEpisodio = sc.nextLine();

        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);

        if (!episodiosEncontrados.isEmpty()){
        episodiosEncontrados.forEach(e -> System.out.println("Titulo: " + e.getTitulo() + ", Serie: " + e.getSerie().getTitulo()));
        }else{
            System.out.println("No se encontró ningún episodio con el nombre '" + nombreEpisodio + "'" );
        }

    }

    private void buscarTop5Episodios(){
        System.out.println("===== Series =====");
        buscarSeriesPorTitulo();
        if (serieBuscada.isPresent())
        {
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5Episodios(serie);
            topEpisodios.forEach(e -> System.out.println("Titulo: " + e.getTitulo() + ", Serie: " + e.getSerie().getTitulo()));
        }
    }


}
