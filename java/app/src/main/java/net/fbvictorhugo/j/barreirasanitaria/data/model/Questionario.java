package net.fbvictorhugo.j.barreirasanitaria.data.model;

import java.util.Date;

public class Questionario {

    private long id;
    private long barreiraId;
    private long pessoaId;
    private boolean viagemExterior;
    private boolean sintomaFebre;
    private boolean sintomaCoriza;
    private boolean sintomaTosse;
    private boolean sintomaCancaco;
    private boolean sintomaDorGarganta;
    private boolean sintomaFaltaAr;
    private boolean sintomaContatoComEnfermos;
    private String observacoes;
    private Date dataResposta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBarreiraId() {
        return barreiraId;
    }

    public void setBarreiraId(long barreiraId) {
        this.barreiraId = barreiraId;
    }

    public long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public boolean isViagemExterior() {
        return viagemExterior;
    }

    public void setViagemExterior(boolean viagemExterior) {
        this.viagemExterior = viagemExterior;
    }

    public boolean isSintomaFebre() {
        return sintomaFebre;
    }

    public void setSintomaFebre(boolean sintomaFebre) {
        this.sintomaFebre = sintomaFebre;
    }

    public boolean isSintomaCoriza() {
        return sintomaCoriza;
    }

    public void setSintomaCoriza(boolean sintomaCoriza) {
        this.sintomaCoriza = sintomaCoriza;
    }

    public boolean isSintomaTosse() {
        return sintomaTosse;
    }

    public void setSintomaTosse(boolean sintomaTosse) {
        this.sintomaTosse = sintomaTosse;
    }

    public boolean isSintomaCancaco() {
        return sintomaCancaco;
    }

    public void setSintomaCancaco(boolean sintomaCancaco) {
        this.sintomaCancaco = sintomaCancaco;
    }

    public boolean isSintomaDorGarganta() {
        return sintomaDorGarganta;
    }

    public void setSintomaDorGarganta(boolean sintomaDorGarganta) {
        this.sintomaDorGarganta = sintomaDorGarganta;
    }

    public boolean isSintomaFaltaAr() {
        return sintomaFaltaAr;
    }

    public void setSintomaFaltaAr(boolean sintomaFaltaAr) {
        this.sintomaFaltaAr = sintomaFaltaAr;
    }

    public boolean isSintomaContatoComEnfermos() {
        return sintomaContatoComEnfermos;
    }

    public void setSintomaContatoComEnfermos(boolean sintomaContatoComEnfermos) {
        this.sintomaContatoComEnfermos = sintomaContatoComEnfermos;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(Date dataResposta) {
        this.dataResposta = dataResposta;
    }
}
