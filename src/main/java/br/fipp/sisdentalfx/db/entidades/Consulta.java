package br.fipp.sisdentalfx.db.entidades;

import java.time.LocalDate;

public class Consulta {
    private int id;
    private LocalDate data;
    private int horario;
    private Dentista dentista;
    private Paciente paciente;
    private String relato;
    private boolean efetivada;

    public Consulta(int id, LocalDate data, int horario, Dentista dentista, Paciente paciente, String relato, boolean efetivada) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.dentista = dentista;
        this.paciente = paciente;
        this.relato = relato;
        this.efetivada = efetivada;
    }

    public Consulta(LocalDate data, int horario, Dentista dentista, Paciente paciente, String relato) {
        this(0,data,horario,dentista,paciente,relato,false);
    }

    public Consulta() {
        this(0,LocalDate.now(),8,null,null,"",false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getRelato() {
        return relato;
    }

    public void setRelato(String relato) {
        this.relato = relato;
    }

    public boolean isEfetivada() {
        return efetivada;
    }

    public void setEfetivada(boolean efetivada) {
        this.efetivada = efetivada;
    }
}
