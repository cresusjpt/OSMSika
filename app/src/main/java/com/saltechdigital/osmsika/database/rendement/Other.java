package com.saltechdigital.osmsika.database.rendement;

public class Other {
    private String dateFin;
    private String dateDebut;
    private double qteMetreCarre;

    public Other() {
    }

    public Other(String dateFin, String dateDebut, double qteMetreCarre) {
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
        this.qteMetreCarre = qteMetreCarre;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public double getQteMetreCarre() {
        return qteMetreCarre;
    }

    public void setQteMetreCarre(double qteMetreCarre) {
        this.qteMetreCarre = qteMetreCarre;
    }
}
