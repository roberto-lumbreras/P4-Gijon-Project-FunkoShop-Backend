# Funkoshop Backend  
### *Aplicación de e-commerce para venta de Funko Pops*  

Esta aplicación está diseñada para gestionar la venta de Funko Pops en una tienda online. Proporciona una API RESTful preparada para integrarse con un frontend y cubre todas las funcionalidades requeridas.  

---

# Stack  

🌱 **Lenguaje**: Java 21 con Spring Boot  

🧩 **Testing**: JUnit y Mockito  

🐬 **Base de Datos**: MySQL con Workbench 8.0  

🔑 **Seguridad**: Spring Security, JWT para autenticación y contraseñas cifradas con BCrypt   

---

# Funcionalidades  

## ✨ **General**  
- **Seguridad y autenticación**:  
  - Inicio de sesión para usuarios y administradores utilizando email y contraseña.  
  - Contraseñas encriptadas y generación de tokens JWT para acceso seguro.
    

## ✨ **Clientes**  
- **Registro y gestión de usuarios**:  
  - Registro de usuarios con email y contraseña.  
  - Actualización de datos personales: nombre, apellidos, teléfono y direcciones.
    

## ✨ **Productos**  
- CRUD completo para productos (añadir, editar, desactivar y eliminar).  
- Gestión de stock con actualización de cantidad disponible.  
- Posibilidad de asignar descuentos a productos.  
- Listado de productos paginados.  
- Filtros por categoría (Animación, Anime & Manga, Marvel, DC Comics).  
- Búsqueda avanzada por texto (título y descripción).  

- **Listado de productos destacados**:  
  - Novedades.  
  - Ofertas.  

- **Detalles de producto**:  
  - Título, precio, categoría, descripción y estado (disponible/agotado).
    

## ✨ **Pedidos**  
- Endpoint para crear un pedido con los productos seleccionados en el carrito.  
- Consultar el estado de los pedidos realizados.  
- Cambiar el estado de un pedido: En proceso, Enviado, Entregado.
  

## ✨ **Tracking**  
- Endpoint para consultar el estado actual del pedido.
  

## ✨ **Facturación**  
- Mostrar historial de ventas a través de un PDF.  
- Mostrar la factura de un pedido unitario en un PDF.
   

## ✨ **Gestión**  
- **Estadísticas**:  
  - Productos más vendidos.  

- **Gestión de categorías**:  
  - CRUD completo para las categorías de productos.  

---

# Instalaciones  

✨ *Para probar este proyecto, necesitarás:*  
- JDK 21  
- Apache Maven  
- MySQL Workbench (configurado con username = root, password = 1234)  
- Un IDE (como IntelliJ IDEA o VSCode con extensiones de Java)  
- Git (para clonar el repositorio)  
- Postman (opcional, para probar endpoints)  

---

## Diagramas UML  

![UML](https://github.com/roberto-lumbreras/P4-Gijon-Project-FunkoShop-Backend/blob/dev/src/main/resources/static/images/UML_FUNKO.png) 

---

# Colaboradores 😊

## Departamento 1:
* **Scrum Master & Developer**: Maria García [Github](https://github.com/strawmery)
* **Product Owner & Developer**: Pilar Pato [Github](https://github.com/Pilar-Pato)
* **Developer**: Guadalupe G. Figueroa [Github](https://github.com/GuadalupeGFigueroa)
* **Developer**: Ana Belén Hernández [Github](https://github.com/AnaBHernandez)
* **Developer**: Naudelyn Lucena [Github](https://github.com/NaudelynLucena)
* **Developer**: Andrés Vázquez [Github](https://github.com/andresvaz89)
* **Developer**: Acacia Sánchez Pastur [Github](https://github.com/Acacia-Sanchez)

## Departamento 2:
* **Scrum Master & Developer**: Lara Gutiérrez [Github](https://github.com/lara-gs)
* **Product Owner & Developer**: Óscar Menéndez [Github](https://github.com/Morty1904)
* **Developer**: Susana Artime [Github](https://github.com/Susana-Artime)
* **Developer**: Andrea Martinez [Github](https://github.com/andreamsgi27)
* **Developer**: Mercy Chancayauri [Github](https://github.com/mercyluz)
* **Developer**: Roberto Lumbreras [Github](https://github.com/roberto-lumbreras)
* **Developer**: Juan Camilo [Github](https://github.com/Juanito2005)

## Departamento 3:
* **Scrum Master & Developer**: Adrian Caiñas [Github](https://github.com/acr00)
* **Product Owner & Developer**: Alejandra Sierra [Github](https://github.com/alejandra-sierra)
* **Developer**: Estefany Ochoa [Github](https://github.com/EstefanyOchoaRomero)
* **Developer**: Kevin Boy [Github](https://github.com/sealkboy)
* **Developer**: Rubén Blanco [Github](https://github.com/Ruben-BV)
* **Developer**: Abel Prieto [Github](https://github.com/abelpriem)
