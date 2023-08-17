public class GestorPrecios {
    private Producto producto;
    
    public GestorPrecios(Producto producto) {
        this.producto = producto;}
    
    public void agregarPrecio(double valor) throws ExceptionPrecio {
        Precio nuevoPrecio = new Precio(valor);
        actualizarPrecioExistente();
        producto.getMisPrecios().add(nuevoPrecio);}
    
    public double getPrecioActual() {
        return producto.getMisPrecios()
                      .stream()
                      .filter(p -> p.getFechaHasta() == null)
                      .findFirst()
                      .map(Precio::getValor)
                      .orElse(0.0);}
    
    public ArrayList<Precio> getPreciosPorFecha(Calendar fechaExacta) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return producto.getMisPrecios()
                       .stream()
                       .filter(p -> p.getFechaHastaConFormato().equals(sdf.format(fechaExacta.getTime())))
                       .collect(Collectors.toCollection(ArrayList<Precio>::new));}
    
    private void actualizarPrecioExistente() {
        producto.getMisPrecios()
                .stream()
                .filter(precio -> precio.getFechaHasta() == null)
                .findFirst()
                .ifPresent(precio -> {
                    Calendar fechaActual = GregorianCalendar.getInstance();
                    precio.setFechaHasta(fechaActual);
                });}
}
