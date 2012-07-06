package com.pmarlen.web.operation;

import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.Producto;

/**
 * PedidoVentaDetalleWrapper
 */
public class PedidoVentaDetalleWrapper {

    private PedidoVentaDetalle detalleVentaPedido;

    private int cantMax;
    
    public PedidoVentaDetalleWrapper(PedidoVentaDetalle detalleVentaPedido){
        this.detalleVentaPedido = detalleVentaPedido;
    }

    public int getCantidad() {
        return this.detalleVentaPedido.getCantidad();
    }

    public void setCantidad(int cantidad) {
        this.detalleVentaPedido.setCantidad(cantidad);
    }

    public double getPrecioBase() {
        return this.detalleVentaPedido.getPrecioBase();
    }

    public void setPrecioBase(double precioBase) {
        this.detalleVentaPedido.setPrecioBase(precioBase);
    }

    public double getDescuentoAplicado() {
        return this.detalleVentaPedido.getDescuentoAplicado();
    }

    public void setDescuentoAplicado(double descuentoAplicado) {
        this.detalleVentaPedido.setDescuentoAplicado(descuentoAplicado);
    }

    public Producto getProducto() {
        return this.detalleVentaPedido.getProducto();
    }

    public void setProducto(Producto producto) {
        this.detalleVentaPedido.setProducto(producto);
    }

    public PedidoVentaDetalle getPedidoVentaDetalle() {
        return detalleVentaPedido;
    }

    public void setPedidoVentaDetalle(PedidoVentaDetalle detalleVentaPedido) {
        this.detalleVentaPedido = detalleVentaPedido;
    }

    public int getCantMax() {
        return cantMax;
    }

    public void setCantMax(int cantMax) {
        this.cantMax = cantMax;
    }

}
