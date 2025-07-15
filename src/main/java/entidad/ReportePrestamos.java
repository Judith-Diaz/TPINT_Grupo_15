package entidad;

public class ReportePrestamos {
    private int totalPrestamosSolicitados;
    private int prestamosPendientes;
    private int prestamosAprobados;
    private int prestamosRechazados;
    private double porcentajePendientes;
    private double porcentajeAprobados;
    private double porcentajeRechazados;
    private double totalPorcentaje;

    
    public ReportePrestamos(int totalPrestamosSolicitados, int prestamosPendientes, 
                                  int prestamosAprobados, int prestamosRechazados, 
                                  double porcentajePendientes, double porcentajeAprobados, 
                                  double porcentajeRechazados, double totalPorcentaje) {
        this.totalPrestamosSolicitados = totalPrestamosSolicitados;
        this.prestamosPendientes = prestamosPendientes;
        this.prestamosAprobados = prestamosAprobados;
        this.prestamosRechazados = prestamosRechazados;
        this.porcentajePendientes = porcentajePendientes;
        this.porcentajeAprobados = porcentajeAprobados;
        this.porcentajeRechazados = porcentajeRechazados;
        this.totalPorcentaje = totalPorcentaje;
    }

    // Constructor vacío
    public ReportePrestamos() {
        this.totalPrestamosSolicitados = 0;
        this.prestamosPendientes = 0;
        this.prestamosAprobados = 0;
        this.prestamosRechazados = 0;
        this.porcentajePendientes = 0.0;
        this.porcentajeAprobados = 0.0;
        this.porcentajeRechazados = 0.0;
        this.totalPorcentaje = 0.0;
    }

    // Getters y Setters
    public int getTotalPrestamosSolicitados() {
        return totalPrestamosSolicitados;
    }

    public void setTotalPrestamosSolicitados(int totalPrestamosSolicitados) {
        this.totalPrestamosSolicitados = totalPrestamosSolicitados;
    }

    public int getPrestamosPendientes() {
        return prestamosPendientes;
    }

    public void setPrestamosPendientes(int prestamosPendientes) {
        this.prestamosPendientes = prestamosPendientes;
    }

    public int getPrestamosAprobados() {
        return prestamosAprobados;
    }

    public void setPrestamosAprobados(int prestamosAprobados) {
        this.prestamosAprobados = prestamosAprobados;
    }

    public int getPrestamosRechazados() {
        return prestamosRechazados;
    }

    public void setPrestamosRechazados(int prestamosRechazados) {
        this.prestamosRechazados = prestamosRechazados;
    }

    public double getPorcentajePendientes() {
        return porcentajePendientes;
    }

    public void setPorcentajePendientes(double porcentajePendientes) {
        this.porcentajePendientes = porcentajePendientes;
    }

    public double getPorcentajeAprobados() {
        return porcentajeAprobados;
    }

    public void setPorcentajeAprobados(double porcentajeAprobados) {
        this.porcentajeAprobados = porcentajeAprobados;
    }

    public double getPorcentajeRechazados() {
        return porcentajeRechazados;
    }

    public void setPorcentajeRechazados(double porcentajeRechazados) {
        this.porcentajeRechazados = porcentajeRechazados;
    }

    public double getTotalPorcentaje() {
        return totalPorcentaje;
    }

    public void setTotalPorcentaje(double totalPorcentaje) {
        this.totalPorcentaje = totalPorcentaje;
    }

    @Override
    public String toString() {
        return "Total: " + totalPrestamosSolicitados + ", Aprobados: " + prestamosAprobados + 
               ", Rechazados: " + prestamosRechazados + ", Pendientes: " + prestamosPendientes +
               ", %Aprob: " + porcentajeAprobados + ", %Rech: " + porcentajeRechazados + 
               ", %Pend: " + porcentajePendientes;
    }
}

