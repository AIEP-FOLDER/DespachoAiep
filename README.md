# 📦 DespachoAIEP

Aplicación móvil para la gestión de compras y cálculo automático de despacho en una distribuidora de alimentos. Incluye autenticación con Gmail, cálculo de tarifas según reglas de negocio, monitoreo de cadena de frío y compatibilidad con versiones Android Lollipop y Oreo.

---

## 🚀 Características principales

- 🛒 Registro de compras con cálculo automático de despacho
- 📍 Validación de radio de cobertura (20 km)
- 💰 Tarifas dinámicas según monto de compra
- 🔐 Autenticación con cuentas Gmail (Firebase Auth)
- ❄️ Monitoreo de temperatura para productos congelados
- 📱 Compatibilidad con Android Lollipop (API 21) y Oreo (API 26)

---

## 📐 Reglas de negocio implementadas

| Monto de compra | Distancia | Tarifa de despacho |
|-----------------|-----------|--------------------|
| ≥ $50.000       | ≤ 20 km   | Gratuito           |
| $25.000–$49.999 | Cualquier | $150/km            |
| < $25.000       | Cualquier | $300/km            |
| > 20 km         | Cualquier | No disponible      |

---

## 🧪 Casos de uso

- Compra de $55.000 a 18 km → despacho gratuito
- Compra de $30.000 a 10 km → $1.500 de despacho
- Compra de $20.000 a 5 km → $1.500 de despacho
- Compra de $60.000 a 25 km → rechazo por distancia

---

## 🛠️ Tecnologías utilizadas

- **Kotlin + Jetpack Compose**
- **Firebase Authentication**
- **Credential Manager API**
- **Gradle + Modularización por feature**
- **Compatibilidad con Android API 21+**

---

## 📦 Instalación

```bash
git clone https://github.com/tuusuario/DespachoAIEP.git
cd DespachoAIEP
./gradlew build
```
### ✉️ Contacto

> Desarrollado por Matías Ignacio Pérez Nauto
> 📍 Puerto Varas, Chile
> 📧 contacto@tucorreo.co
