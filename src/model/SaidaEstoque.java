package model;

import java.time.LocalDate;

public class SaidaEstoque {
    private int saidaId;
    private int codigoItem;
    private int quantidade;
    private LocalDate dataSaida;
    private int origemLocalizacao;
    private String destino;

    public SaidaEstoque(int codigoItem, int quantidade, LocalDate dataSaida, int origemLocalizacao, String destino) {
        this.codigoItem = codigoItem;
        this.quantidade = quantidade;
        this.dataSaida = dataSaida;
        this.origemLocalizacao = origemLocalizacao;
        this.destino = destino;
    }

    public int getSaidaId() {
        return saidaId;
    }

    public int getCodigoItem() {
        return codigoItem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public int getOrigemLocalizacao() {
        return origemLocalizacao;
    }

    public String getDestino() {
        return destino;
    }
}