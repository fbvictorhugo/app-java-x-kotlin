package net.fbvictorhugo.j.barreirasanitaria.data.model;

import java.util.Date;

public class Questionario {

    private long id;
    private long barreiraId;
    private long pessoaId;
    private boolean viagemExterior;
    private boolean sintomaFebre;
    private boolean sintomaCoriza;
    private boolean sintomaCancaco;
    private boolean sintomaPerdaPaladar;
    private boolean sintomaFaltaAr;
    private boolean sintomaContatoComEnfermos;
    private String observacoes;
    private Date dataResposta;

    public Questionario(long id, long barreiraId, long pessoaId) {
        this.id = id;
        this.barreiraId = barreiraId;
        this.pessoaId = pessoaId;
    }

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

    public boolean isSintomaCancaco() {
        return sintomaCancaco;
    }

    public void setSintomaCancaco(boolean sintomaCancaco) {
        this.sintomaCancaco = sintomaCancaco;
    }

    public boolean isSintomaPerdaPaladar() {
        return sintomaPerdaPaladar;
    }

    public void setSintomaPerdaPaladar(boolean sintomaPerdaPaladar) {
        this.sintomaPerdaPaladar = sintomaPerdaPaladar;
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
}
