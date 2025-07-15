Se trata de un sistema web para la gestión de un banco, con funcionalidades diferenciadas para dos tipos de usuarios: administrador y cliente.

El administrador tiene la capacidad de realizar operaciones de ABML (Alta, Baja, Modificación y Listado) sobre clientes y cuentas, autorizar o rechazar solicitudes de préstamos, y visualizar reportes estadísticos. Cada cliente puede tener hasta tres cuentas, con un saldo inicial asignado automáticamente. Además, puede gestionar los préstamos aprobados, incluyendo el control de cuotas.

El cliente puede acceder a sus datos personales, visualizar el historial de movimientos de sus cuentas, realizar transferencias (entre sus propias cuentas o a cuentas de otros clientes mediante CBU), solicitar préstamos y pagar sus cuotas desde su cuenta asociada. El sistema registra cada pago de cuota con su fecha correspondiente.

El diseño contempla distintas reglas de negocio como el límite de cuentas por cliente, imposibilidad de dejar cuentas con saldo negativo, y validaciones sobre disponibilidad de saldo para transferencias.
