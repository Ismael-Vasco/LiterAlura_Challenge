package com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Autor autor;
    private String idioma;
    private Integer numeroDeDescargas;

    // Constructor
    public Libro(DatosLibro libro) {
        this.titulo = libro.titulo();
        Optional<DatosAutor> autor = libro.autores().stream()
                .findFirst();
        if (autor.isPresent()) {
            this.autor = new Autor(autor.get());
        } else {
            System.out.println("\n***  NO HAY AUTOR ***\n");
        }
        this.idioma = libro.idiomas().get(0);
        this.numeroDeDescargas = libro.numeroDeDescargas();
    }
    // constructor predeterminado
    public Libro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", idioma=" + idioma +
                ", numeroDeDescargas=" + numeroDeDescargas;
    }
}
