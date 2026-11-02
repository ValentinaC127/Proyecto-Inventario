# Proyecto de Inventario - API CRUD

## Funcionalidad Implementada
Gestión de Medicamentos.

## Endpoints Disponibles y Métodos HTTP

* GET /api/medicamentos
* GET /api/medicamentos/{id}
* POST /api/medicamentos
* PUT /api/medicamentos/{id}
* DELETE /api/medicamentos/{id}

## Ejemplos de Solicitudes y Respuestas JSON

### POST /api/medicamentos
**Solicitud:**
```json
{
  "nombre": "Ibuprofeno",
  "laboratorio": "bayer",
  "precio": 1500.0,
  "stock": 30,
  "fechaVencimiento": "2027-12-31"
}
```


**Respuesta:**
```json
{
  "id": 14,
  "nombre": "Ibuprofeno",
  "laboratorio": {
    "id": 1,
    "nombre": "bayer"
  },
  "precio": 1500.0,
  "stock": 30,
  "fechaVencimiento": "2027-12-31"
}