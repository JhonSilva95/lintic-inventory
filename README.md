# Linktic Inventory
Este componente actúa como el núcleo para el control de existencias en la tabla inventory y el registro histórico de transacciones. Su función principal es garantizar la integridad del stock ante cada operación comercial.

#### Funcionalidades Principales
- Actualización de Inventario: Sincroniza las cantidades disponibles en la base de datos tras cada movimiento.
- Historial de Ventas: Registra de forma persistente cada transacción para auditoría y reportes.

#### Endpoints Disponibles
purchaseProduct (Proceso de Compra): 
- Registra la venta en el historial.
- Realiza la disminución automática de la cantidad del producto en la tabla de inventario.
- Asegura que el stock se actualice en tiempo real.

getStockStatus (Consulta de Disponibilidad):
- Obtiene el stock actual de un producto.
- Determina e informa si el artículo se encuentra disponible o agotado.

## Tecnologías Principales
Las tecnologías principales usadas en este proyecto son las siguiente:
 - Java 21
 - Springboot 4.0.1
 - Lombok
 - RestClient

 ## Ejecución

Para poder ejecutar este proyecto de manera local puede hacerlo de la siguiente manera:
```bash
mvn clean package
```
```bash
mvn spring-boot:run
```

Ahora bien si lo deseas ejecutar en un contendor docker realizalo de la siguiente mandera:
#### Para construir el contenedor.
recuarda estar al mismo nivel del Dockerfile.
```bash
docker build -t inventory-service:1.0.0 .
```
#### Para correr el contenedor.
recuarda estar al mismo nivel del Docker file.
```bash
docker run -d -p 8080:8080 --name inventory-app -e JAVA_OPTS="-Xms256m -Xmx512m" product-service:1.0.0
```
### Ejecutar todo con base de datos incluido - docker-compose:
```bash
docker-compose up -d
```
ver logs:
```bash
docker-compose logs -f product-service
```
apagar todo
```bash
docker-compose down
```
## Uso
A continuación las curl y su respuesta para tener en cuenta como debería funcionar el componente:
#### Realizar compra
```curl
curl --location 'http://localhost:8081/linktic/purchase' \
--header 'Content-Type: application/json' \
--data '{
    "productId": 1,
    "quantity": 1
}'
```
```json
{
    "code": 0,
    "description": "OK",
    "content": {
        "productId": 1,
        "quantityPurchased": 1,
        "date": "2026-01-23T00:40:07.8521244",
        "newStock": 2
    }
}
```

#### Optener Stock
```curl
curl --location 'http://localhost:8081/inventory/product/1'
```
```json
{
    "code": 0,
    "description": "OK",
    "content": {
        "productId": 1,
        "productName": "Arroz",
        "quantity": 2,
        "status": "DISPONIBLE"
    }
}
```
## Nota
Para este componente se utilizo la ayuda de la IA Gemini para ayuda mas que todo en los test ya que los genera de manera rapido y fácil de entender.

## Autor
Jhon Alexander Silva - Desarrollador de software.