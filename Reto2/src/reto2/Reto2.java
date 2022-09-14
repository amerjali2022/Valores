package reto2;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Andres Marsiglia
 */
public class Reto2 {

    /**
     * Este debe ser el único objeto de tipo Scanner en el código
     */
    private final Scanner scanner = new Scanner(System.in);
    private BaseDatosProductos baseDatosProductos = new BaseDatosProductos();// Constructor vacio cuya función es inicializar la base de datos
    
     /**
     * Este método es usado para solicitar datos al usuario
     * @return  Lectura de la siguiente linea del teclado
     */
    
    public String read(){
        return this.scanner.nextLine();
    }
    
     /**
     * método principal
     */
    
    public void run(){
        /*
        solución propuesta
        */
        String opcion = read(); 
        String info = read();
        String[] arrayInfo = info.split(" ");
        Producto producto = new Producto(
                Integer.parseInt(arrayInfo[0]),
                arrayInfo[1],
                Double.parseDouble(arrayInfo[2]),
                Integer.parseInt(arrayInfo[3])
        );
        switch (opcion){
            case "AGREGAR":
                agregarProducto(producto);
                break;
            case "ACTUALIZAR":
                actualizarProducto(producto);
                break;
            case "BORRAR":
                borrarProducto(producto);
                break;
        }
    }
    
    /**
     * Metodo que permite agregar un producto, y si ya existe imprime "ERROR". Adicional imprime un informe
     * @param producto 
     */
    public void agregarProducto(Producto producto) {
        if(this.baseDatosProductos.validadorProducto(producto)){
            imprimirMensaje("ERROR");
        }else{
            this.baseDatosProductos.agregarProducto(producto);
            imprimirMensaje(this.baseDatosProductos.generarInforme());
        }
    }
    
    /**
     * Metodo que permite actualizar un producto, y si no existe alguno para actualizar, imprime "ERROR". Adicional imprime un informe
     * @param producto 
     */
    public void actualizarProducto(Producto producto) {
        if(this.baseDatosProductos.validadorProducto(producto)){
            this.baseDatosProductos.actualizarProducto(producto);
            imprimirMensaje(this.baseDatosProductos.generarInforme());
        }else{
            imprimirMensaje("ERROR");
        }
    }
    
    /**
     * Metodo que permite borrar un producto, y si no existe alguno para borrar, imprime "ERROR". Adicional imprime un inform
     * @param producto 
     */
    public void borrarProducto(Producto producto) {
        if(this.baseDatosProductos.validadorProducto(producto)){
            this.baseDatosProductos.borrarProducto(producto);
            imprimirMensaje(this.baseDatosProductos.generarInforme());
        }else{
            imprimirMensaje("ERROR");
        }
    }
    
    /**
     * Metodo para imprimir algun mensaje en pantalla
     * @param mensaje 
     */
    public void imprimirMensaje(String mensaje){
        System.out.println(mensaje);
    }
}

/**
 * Define los atributos de la Base de Datos a traves de la clase HashMap
 * @version 17/08/2021/A
 * @author Andres Marsiglia
 */

class BaseDatosProductos {
    
    private HashMap<Integer, Producto> listaProductos;
    
    public BaseDatosProductos() {
        this.listaProductos = new HashMap<>();
        Producto producto = new Producto(1, "Naranjas", 7000.0, 35);
        this.listaProductos.put(producto.getCodigo(), producto);
        producto = new Producto(2, "Limones", 2500.0, 15);
        this.listaProductos.put(producto.getCodigo(), producto);
        producto = new Producto(3, "Peras", 2700.0, 65);
        this.listaProductos.put(producto.getCodigo(), producto);
        producto = new Producto(4, "Arandanos", 9300.0, 34);
        this.listaProductos.put(producto.getCodigo(), producto);
        producto = new Producto(5, "Tomates", 2100.0, 42);
        this.listaProductos.put(producto.getCodigo(), producto);
        producto = new Producto(6, "Fresas", 9100.0, 20);
        this.listaProductos.put(producto.getCodigo(), producto);
        producto = new Producto(7, "Helado", 4500.0, 41);
        this.listaProductos.put(producto.getCodigo(), producto);
        producto = new Producto(8, "Galletas", 500.0, 8);
        this.listaProductos.put(producto.getCodigo(), producto);
        producto = new Producto(9, "Chocolates", 4500.0, 80);
        this.listaProductos.put(producto.getCodigo(), producto);
        producto = new Producto(10, "Jamon", 17000.0, 48);
        this.listaProductos.put(producto.getCodigo(), producto);        
    }
    
    /**
     * Metodo para agregar un producto a la base de datos
     * @param producto 
     */
    public void agregarProducto(Producto producto){
        this.listaProductos.put(producto.getCodigo(), producto);
    }
    
    /**
     * Metodo para borrar un producto de la base de datos
     * @param producto 
     */
    public void borrarProducto(Producto producto){
        this.listaProductos.remove(producto.getCodigo());
    }
    
    /**
     * Metodo para actualizar un producto de la base de datos
     * @param producto 
     */
    public void actualizarProducto(Producto producto){
        this.listaProductos.replace(producto.getCodigo(), producto);
    }
    
    /**
     * Metodo que permite validar si un producto ya existe en la base de datos
     * @param producto
     * @return la clave del producto si este existe
     */
    public boolean validadorProducto(Producto producto){
        return this.listaProductos.containsKey(producto.getCodigo());
    }
    
    /**
     * Metodo que permite generar un Informe con el nombre de los productos de mayor y menor precio, el promedio de precios y el valor total del inventario
     * @return Dos elementos tipo string y dos valores numericos
     */
    public String generarInforme(){
        String totalInventario = totalInventario();
        String mayorPrecio = mayorPrecio();
        String menorPrecio = menorPrecio();
        String promedio = promedio();
        return mayorPrecio + " " + menorPrecio + " " + promedio + " " + totalInventario;
    }
    
    /**
     * Metodo que permite calcular el valor total del Inventario
     * @return Un valor numerico
     */
    private String totalInventario(){
        double suma = 0;
        for (Producto producto : this.listaProductos.values()) {
            suma += (producto.getPrecio() * producto.getInventario());
        }
        DecimalFormat df = new DecimalFormat("#.0");
        return "" + df.format(suma);
    }
    /**
     * Metodo que permite verificar el nombre del producto de mayor precio
     * @return Un elemento tipo string
     */
    public String mayorPrecio() {
        Producto p = new Producto();
        for (Producto productoP : this.listaProductos.values()){
            if (productoP.getPrecio() >  p.getPrecio()) {
                p = productoP;
            }
        }
        return p.getNombre();
    }
    /**
     * Metodo que permite verificar el nombre del producto de menor precio
     * @return Un elemento tipo string
     */
    public String menorPrecio() {
        Producto p = new ArrayList<>(this.listaProductos.values()).get(0);
        for (Producto productoP : this.listaProductos.values()){
            if (productoP.getPrecio() <  p.getPrecio()) {
                p = productoP;
            }
        }
        return p.getNombre();
    }
    
    /**
     * Metodo que permite calcular el promedio de precios de los productos
     * @return Un valor numerico
     */
    public String promedio(){
        double total = 0;
        int contador = 0;
        double promedio;
        for (Producto producto : this.listaProductos.values()){
            total+=(producto.getPrecio());
            contador++;
        }
        promedio = total/contador;
        DecimalFormat df = new DecimalFormat("#.0");
        
        return "" + df.format(promedio);
    }

}
  
/**
 * Define las atributos del objeto Producto 
 * @version 17/08/2021/A
 * @author Andres Marsiglia
 */

class Producto {
    
    private int codigo;
    private String nombre;
    private double precio;
    private int inventario;
    
    /**
     * 
     */

    public Producto() {
    }
    
    /**
     * 
     * @param codigo
     * @param nombre
     * @param precio
     * @param inventario 
     */
    
    public Producto(int codigo, String nombre, double precio, int inventario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.inventario = inventario;
    }

    /**
     * Permite obtener el valor del codigo.
     * @return codigo numerico
     */
    public int getCodigo() {
        return codigo;
    }
    
    /**
     * Permite asignarle un valor por parametro a la variable codigo.
     * @param codigo: parametro a asignar de tipo numerico
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Permite obtener el nombre del objeto.
     * @return cadena de caracteres
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Permite asignarle un nombre por parametro a la variable nombre. 
     * @param nombre: parametro a asignar de tipo cadena de caracteres
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Permite obtener el precio del objeto.
     * @return valor numerico decimal
     */
    public double getPrecio() {
        return precio;
    }
    
    /**
     * Permite asignarle un precio por parametro a la variable precio.
     * @param precio: parametro a asignar de tipo numerico decimal
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    /**
     * Permite obtener la cantidad existente del objeto.
     * @return valor numerico 
     */
    public int getInventario() {
        return inventario;
    }
    
    /**
     * Permite asignarle un numero de existencias por parametro a la variable inventario
     * @param inventario: parametro a asignar de tipo numerico
     */
    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

}
