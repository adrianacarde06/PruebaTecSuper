### Prueba Técnica Supermercado con Java Spring Boot

<p>
Desarrollo de una prueba técnica para mostrar la implementación de Crud para Productos y Ventas que se realizan en un supermercado. Los servicios a encontrar en la api son:
</p>

- Crear, Actualizar, Eliminar, Listar Producto
- Crear, Actualizar, Eliminar, Listar Sucursal
- Crear, Actualizar, Eliminar, Listar Ventas, incluyendo sus detalles de venta

### Algunos servicios



Crear un Producto:

    {
		"nombre":"Coca cola",
		"precio": 3000.0,
		"categoria": "Bebidas",
		"cantidad": 30
	}
    
Respuesta del servicio:

    200 -> Producto creado correctamente.
	500 -> Error interno del servidor.

### Diagrama de secuencia
                    
```seq
Usuario-->Sistema: ProductoDTO
Sistema-->Sistema: CrearProducto
Sistema-->Usuario: Producto creado correctamente.
```

