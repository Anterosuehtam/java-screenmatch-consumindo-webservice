package br.com.alura.screenmatch.modelos;

import com.google.gson.annotations.SerializedName;

public record TituloOmdb(String title, String year, String runtime, @SerializedName("imdbRating") String imdbRating) {
}
