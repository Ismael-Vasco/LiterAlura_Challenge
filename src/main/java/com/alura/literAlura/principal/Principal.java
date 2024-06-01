package com.alura.literAlura.principal;

import com.alura.literAlura.model.*;
import com.alura.literAlura.repository.AutorRepository;
import com.alura.literAlura.repository.LibroRepository;
import com.alura.literAlura.service.ConsumoApi;
import com.alura.literAlura.service.Conversor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private Conversor conversor = new Conversor();
    List<Libro> libroBuscado;
    private Scanner teclado = new Scanner(System.in);
    private int opcion=-1;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    private DatosLibro getDatosLibro(String busqueda) {
        String json = consumoApi.obtenerLibros(URL + "?search=" + busqueda.replace(" ", "+"));
        List<DatosLibro> libros = conversor.obtenerDatos(json, Datos.class).resultados();
        Optional<DatosLibro> libro = libros.stream()
                .filter(l -> l.titulo().toLowerCase().contains(busqueda.toLowerCase()))
                .findFirst();
        if (libro.isPresent()) {
            return libro.get();
        }
        System.out.println("\n***  LIBRO NO ENCONTRADO  ***\n");
        return null;
    }

    private void viewLibro(Libro libro){
        System.out.println("\n***************** LIBRO *****************");
        System.out.println("TITULO: "+ libro.getTitulo());
        System.out.println("AUTOR: "+libro.getAutor().getNombre());
        System.out.println("IDIOMA: "+libro.getIdioma());
        System.out.println("NUMERO DE DESCARCAS: "+libro.getNumeroDeDescargas());
        System.out.println("*****************************************");
    }

    private void viewAutor(Autor autor){
        System.out.println("\n***************** AUTOR *****************");
        System.out.println("AUTOR: "+ autor.getNombre());
        System.out.println("FECHA DE NACIMIENTO: "+autor.getFechaDeNacimiento());
        System.out.println("FECHA DE FALLECIMIENTO: "+autor.getFechaDeFallecimiento());
        List<String> libros = autor.getLibros().stream()
                .map(l -> l.getTitulo())
                .collect(Collectors.toList());
        System.out.println("---------------- LIBROS -----------------");
        System.out.println(libros);
        System.out.println("*****************************************");
    }

    public void mostrarMenu(){
        while (opcion!= 0){
            var menu = """
                    \n ***  ELIGE UNA OPCION  ***\n
                    N   |           OPCION
                    --------------------------------------------------
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    \n""";

            System.out.println(menu);
            teclado.nextLine();
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoreRegistrados();
                    break;
                case 4:
                    autoresVivosPorAño();
                    break;
                case 5:
                    libroPorIdioma();
                    break;
                case 0:
                    System.out.println("CERRANDO LA APLICACIÓN...!");
                    break;
                default:
                    System.out.println("    ¡¡¡ OPCIÓN INVALIDA !!!     ");
            }
        }
    }

    // opción 1
    public void buscarLibroPorTitulo(){
        System.out.println("\nINGRESE EL NOMBRE DEL LIBRO QUE DESEA VER:");
        String nombreLibro = teclado.next();
        libroBuscado = libroRepository.findByTituloContainsIgnoreCase(nombreLibro);

        if (libroBuscado.isEmpty()){
            Libro libro = new Libro(getDatosLibro(nombreLibro));
            viewLibro(libro);
            libroRepository.save(libro);
        }else {
            libroBuscado.stream().forEach(this::viewLibro);
        }

    }
    // opción 2
    private void librosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if(libros.isEmpty()){
            System.out.println("""
                    \n
                   ***********************************************
                                NO HAY LIBROS REGISTRADOS
                       INTENTA REGISTRAR ALGUNO EN LA OPCION 1
                   ***********************************************
                    """);
        }else {
            libros.stream().forEach(this::viewLibro);
        }

    }
    // opción 3
    private void autoreRegistrados() {
        List<Autor> autores = autorRepository.findAll();
                autores.stream()
                        .forEach(this::viewAutor);
    }
    // opción 4
    private void autoresVivosPorAño() {
        System.out.println("\nINGRESA EL AÑO EN QUE DESEAS BUSCAR AUTORES VIVOS");
                Integer fechaDeFallecimiento = teclado.nextInt();
                List<Autor> autores = autorRepository.findByFechaDeFallecimientoGreaterThan(fechaDeFallecimiento);

                if (autores.isEmpty()){
                    System.out.println("""
                                       \n
                                       ***********************************************
                                            PARA ENTONCES NO HABIAN AUTORES VIVIO
                                                DE LOS QUE HAY REGISTRADOS
                                       ***********************************************
                                         """);
                }else {
                    autores.stream()
                            .forEach(this::viewAutor);
                }

    }
    // opción 5
    private void libroPorIdioma() {
        System.out.println("\n INGRESA EL IDIOMA EN QUE QUIERES BUSCAR LOS LIBROS ");
                System.out.println("es - ESPAÑOL");
                System.out.println("en - INGLES");
                System.out.println("fr - FRANCES");
                System.out.println("pt - PORTUGUES\n");
                String idioma = teclado.next();
                List<Libro> libros = libroRepository.findByIdioma(idioma.toLowerCase());

                if (libros.isEmpty()){
                    System.out.println("""
                            \n
                                       ***********************************************
                                            NO HAY LIBROS REGISTRADOS EN ESE IDIOMA
                                       ***********************************************
                            """);
                }
                libros.stream()
                        .forEach(this::viewLibro);
    }
}
