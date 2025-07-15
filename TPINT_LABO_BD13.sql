-- 1. CREAR BASE DE DATOS
CREATE DATABASE TP_INT;
USE TP_INT;

-- 2. TABLAS BÁSICAS SIN DEPENDENCIAS
CREATE TABLE Provincias (
    IdProvincias_Pr VARCHAR(2) NOT NULL,
    descripcion_Pr VARCHAR(50) NOT NULL,
    CONSTRAINT PK_Provincia PRIMARY KEY (IdProvincias_Pr)
);

CREATE TABLE Localidades (
    IdLocalidad_Loc VARCHAR(2) NOT NULL,
    IdProvincia_Loc VARCHAR(2) NOT NULL,
    descripcion_Loc VARCHAR(30) NOT NULL,
    CONSTRAINT PK_Localidad PRIMARY KEY (IdLocalidad_Loc),
    CONSTRAINT FK_LocalidadProvincia FOREIGN KEY (IdProvincia_Loc) REFERENCES Provincias (IdProvincias_Pr)
);

CREATE TABLE TiposCuentas (
    IdTipoCuenta_Tc INT PRIMARY KEY ,
    NombreTipo_Tc VARCHAR(30) NOT NULL
);

CREATE TABLE TiposMovimientos (
    IdTipoMovimiento_Tm INT NOT NULL PRIMARY KEY,
      Descripcion_Tm VARCHAR(50)
);

-- 3. TABLA CLIENTES: Datos del Cliente
CREATE TABLE Clientes (
    DNI_Cl VARCHAR(8) PRIMARY KEY,
    CUIL_Cl VARCHAR(11) UNIQUE NOT NULL,
    Nombre_Cl VARCHAR(30) NOT NULL,
    Apellido_Cl VARCHAR(30) NOT NULL,
    Sexo_Cl VARCHAR(1) NOT NULL,
    Nacionalidad_Cl VARCHAR(30),
    FechaNacimiento_Cl DATE NOT NULL,
    Direccion_Cl VARCHAR(50),
    IdLocalidad_Cl VARCHAR(2),
    IdProvincia_Cl VARCHAR(2),
    CorreoElectronico_Cl VARCHAR(50) UNIQUE NOT NULL,
    Telefonos_Cl VARCHAR(12) NOT NULL,
    Estado_Cl BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT FK_ClienteLocalidad FOREIGN KEY (IdLocalidad_Cl) REFERENCES Localidades(IdLocalidad_Loc),
    CONSTRAINT FK_ClienteProvincia FOREIGN KEY (IdProvincia_Cl) REFERENCES Provincias(IdProvincias_Pr)
);

-- 4. TABLA USUARIOS:
CREATE TABLE Usuarios (
    CodUsuario_Usu INT AUTO_INCREMENT NOT NULL,
    DNI_Usu CHAR(8),
    Nombre_Usu VARCHAR(8),
    Contrasenia_Usu VARCHAR(8),
    Rol_Usu CHAR(1),
    Estado_Usu TINYINT(1) DEFAULT 1,
    CONSTRAINT check_Rol CHECK (Rol_Usu IN ('A', 'C')),
    CONSTRAINT PK_CodUsuario_Usu PRIMARY KEY (CodUsuario_Usu)
    
);
-- 5. TABLA CUENTA depende de prestamo y cuenta
CREATE TABLE Cuentas (
    NumeroCuenta_Cu INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ClienteDNI_Cu VARCHAR(8) NOT NULL,
    FechaCreacion_Cu DATE NOT NULL,
    TipoCuenta_Cu INT NOT NULL,
    CBU_Cu VARCHAR(22) UNIQUE NOT NULL,
    SALDO_Cu DECIMAL(15,2) NOT NULL DEFAULT 0,
    Estado_Cu BOOLEAN  DEFAULT TRUE,
    CONSTRAINT FK_TipoCuenta FOREIGN KEY (TipoCuenta_Cu) REFERENCES TiposCuentas(IdTipoCuenta_Tc),
    CONSTRAINT FK_CuentaCliente FOREIGN KEY (ClienteDNI_Cu) REFERENCES Clientes(DNI_Cl)
);

-- 6. TABLA PRESTAMO
CREATE TABLE Prestamos (
    IdPrestamo_Pr INT AUTO_INCREMENT PRIMARY KEY,
    DNICliente_Pr VARCHAR(8) NOT NULL,
    NumeroCuenta_Pr int UNSIGNED  NOT NULL,
    Fecha_Pr DATE NOT NULL,
    ImporteTotal_Pr DECIMAL(15,2) NOT NULL,
    ImporteSolicitado_Pr DECIMAL(15,2) NOT NULL,
    PlazoPagoMeses_Pr INT NOT NULL,
    MontoMensual_Pr DECIMAL(15,2) NOT NULL,
    Cuotas_Pr VARCHAR(50),
    Estado_Pr CHAR(1) NOT NULL DEFAULT 'P',
    CONSTRAINT chk_EstadoPrestamo CHECK (Estado_Pr IN ('P', 'A', 'R')),
    CONSTRAINT FK_PrestamoCliente FOREIGN KEY (DNICliente_Pr) REFERENCES Clientes(DNI_Cl),
    CONSTRAINT FK_PrestamoCuenta FOREIGN KEY (NumeroCuenta_Pr) REFERENCES Cuentas(NumeroCuenta_Cu)
);

-- 7.tabla cuotas
CREATE TABLE Cuotas (
    IdCuota_Cuo INT AUTO_INCREMENT PRIMARY KEY,
    IDPrestamo_Cuo INT NOT NULL,
    NroCuenta_Cuo INT UNSIGNED NOT NULL,
    NroCuota_Cuo INT  NOT NULL,
    FechaVencimiento_Cuo DATE NOT NULL,
    MontoCuota_Cuo DECIMAL(15,2) NOT NULL,
    Estado_Cuo CHAR(1) NOT NULL DEFAULT 'D',
    CONSTRAINT chk_EstadoCuota CHECK (Estado_Cuo IN ('P', 'D')),
    CONSTRAINT FK_CuotaPrestamo FOREIGN KEY (IDPrestamo_Cuo) REFERENCES Prestamos(IdPrestamo_Pr),
    CONSTRAINT FK_CuotaCuenta FOREIGN KEY (NroCuenta_Cuo) REFERENCES Cuentas(NumeroCuenta_Cu)
);

-- 8. TABLA MOVIMIENTO (depende de CUENTA, TIPOS_MOVIMIENTO y PRESTAMO)
CREATE TABLE Movimientos (
    IDMovimiento_Mv INT AUTO_INCREMENT PRIMARY KEY,
    Fecha_Mv DATE NOT NULL,
    Detalle_Mv VARCHAR(100) NOT NULL,
    Importe_Mv DECIMAL(10,2) NOT NULL,
    NumeroCuenta_Mv INT UNSIGNED NULL,
    IdTipoMovimiento_Mv INT NOT NULL,
     CONSTRAINT FK_MovimientoCuenta FOREIGN KEY (NumeroCuenta_Mv) REFERENCES Cuentas(NumeroCuenta_Cu),
    CONSTRAINT FK_MovimientoTipo FOREIGN KEY (IdTipoMovimiento_Mv) REFERENCES TiposMovimientos(IdTipoMovimiento_Tm)
);

-- 9. TABLA TRANSFERENCIAS
CREATE TABLE Transferencias(
    IdTransferencia_Tra INT AUTO_INCREMENT PRIMARY KEY,
    NroCuentaOrigen_Tra INT UNSIGNED NOT NULL,
    NroCuentaDestino_Tra INT UNSIGNED NOT NULL,
    Fechao_Tra DATE NOT NULL,
    Importe_Tra DECIMAL(15,2) NOT NULL,
    Estado_Tra CHAR(1) NOT NULL DEFAULT '1',
    CONSTRAINT FK_NroCuentaOrigen_Tra FOREIGN KEY (NroCuentaOrigen_Tra) REFERENCES Cuentas(NumeroCuenta_Cu),
    CONSTRAINT FK_NroCuentaDestino_Tra FOREIGN KEY (NroCuentaDestino_Tra) REFERENCES Cuentas(NumeroCuenta_Cu)
);

-- TRIGGERS

-- TRIGGER PARA INSERT EN CUENTAS (Alta de cuenta)
DELIMITER $$
CREATE TRIGGER tr_cuentas_despues_insert
AFTER INSERT ON Cuentas
FOR EACH ROW
BEGIN
  INSERT INTO Movimientos (
    Fecha_Mv,
    Detalle_Mv,
    Importe_Mv,
    NumeroCuenta_Mv,
    IdTipoMovimiento_Mv
  )
  VALUES (
    CURDATE(),
    'Alta de cuenta',
    NEW.Saldo_Cu,
    NEW.NumeroCuenta_Cu,
    1
  );
END$$
DELIMITER ;

-- TRIGGER PARA APROBACION DE PRESTAMO
DELIMITER $$
CREATE TRIGGER trg_AprobacionPrestamo 
AFTER UPDATE ON Prestamos
FOR EACH ROW
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE totalCuotas INT;
  DECLARE fechaBase DATE;
  -- Solo se activa si el estado cambia a 'A' (aprobado)
  IF OLD.Estado_Pr <> 'A' AND NEW.Estado_Pr = 'A' THEN
    -- Insertar movimiento de alta de préstamo
    INSERT INTO Movimientos (
      Fecha_Mv,
      Detalle_Mv,
      Importe_Mv,
      NumeroCuenta_Mv,
      IdTipoMovimiento_Mv
    ) VALUES (
      CURDATE(),
      'Alta de préstamo',
      NEW.ImporteSolicitado_Pr,
      NEW.NumeroCuenta_Pr,
      2
    );
    -- Actualizar saldo en la cuenta
    UPDATE Cuentas
    SET SALDO_Cu = SALDO_Cu + NEW.ImporteSolicitado_Pr
    WHERE NumeroCuenta_Cu = NEW.NumeroCuenta_Pr;
    -- Preparar variables para insertar cuotas
    SET totalCuotas = CAST(NEW.Cuotas_Pr AS UNSIGNED);
    SET fechaBase = NEW.Fecha_Pr;
    -- Insertar cuotas en bucle
    WHILE i <= totalCuotas DO
      INSERT INTO Cuotas (
        IDPrestamo_Cuo,
        NroCuenta_Cuo,
        NroCuota_Cuo,
        FechaVencimiento_Cuo,
        MontoCuota_Cuo,
        Estado_Cuo
      ) VALUES (
        NEW.IdPrestamo_Pr,
        NEW.NumeroCuenta_Pr,
        i,
        DATE_ADD(fechaBase, INTERVAL i MONTH),
        NEW.MontoMensual_Pr,
        'D'
      );
      SET i = i + 1;
    END WHILE;
  END IF;
END$$
DELIMITER ;

-- TRIGGER PARA TRANSFERENCIAS
DELIMITER $$
CREATE TRIGGER tr_transferencias_ai
AFTER INSERT ON Transferencias
FOR EACH ROW
BEGIN
  -- Debitar cuenta origen
  UPDATE Cuentas
  SET Saldo_Cu = Saldo_Cu - NEW.Importe_Tra
  WHERE NumeroCuenta_Cu = NEW.NroCuentaOrigen_Tra;
  -- Acreditar cuenta destino
  UPDATE Cuentas
  SET Saldo_Cu = Saldo_Cu + NEW.Importe_Tra
  WHERE NumeroCuenta_Cu = NEW.NroCuentaDestino_Tra;
  -- Insertar movimiento negativo (origen)
  INSERT INTO Movimientos (
    Fecha_Mv,
    Detalle_Mv,
    Importe_Mv,
    NumeroCuenta_Mv,
    IdTipoMovimiento_Mv
  )
  VALUES (
    NEW.Fechao_Tra,
    CONCAT('Transferencia a cuenta ', NEW.NroCuentaDestino_Tra),
    -NEW.Importe_Tra,
    NEW.NroCuentaOrigen_Tra,
    4
  );
  -- Insertar movimiento positivo (destino)
  INSERT INTO Movimientos (
    Fecha_Mv,
    Detalle_Mv,
    Importe_Mv,
    NumeroCuenta_Mv,
    IdTipoMovimiento_Mv
  )
  VALUES (
    NEW.Fechao_Tra,
    CONCAT('Transferencia recibida de cuenta ', NEW.NroCuentaOrigen_Tra),
    NEW.Importe_Tra,
    NEW.NroCuentaDestino_Tra,
    4
  );
END$$
DELIMITER ;

-- TRIGGER PARA ACTUALIZAR ESTADO DE USUARIO CUANDO SE DESACTIVA UN CLIENTE
DELIMITER $$
CREATE TRIGGER actualizar_estado_usuario
AFTER UPDATE ON Clientes
FOR EACH ROW
BEGIN
  -- Solo cuando el estado cambia de TRUE a FALSE
  IF OLD.Estado_Cl = TRUE AND NEW.Estado_Cl = FALSE THEN
    UPDATE Usuarios
    SET Estado_Usu = 0
    WHERE DNI_Usu = NEW.DNI_Cl;
  END IF;
END$$
DELIMITER ;

-- STORED PROCEDURE PARA: REPORTES, PORCENTAJE DE TIPO DE CUENTAS
DELIMITER $$
CREATE PROCEDURE porcentaje_cuentas()
BEGIN
    DECLARE total INT DEFAULT 0;
    DECLARE total_ahorro INT DEFAULT 0;
    DECLARE total_corriente INT DEFAULT 0;
    DECLARE porcentaje_ahorro DECIMAL(5,2) DEFAULT 0.00;
    DECLARE porcentaje_corriente DECIMAL(5,2) DEFAULT 0.00;
    -- OBTENEMOS LA CANTIDAD TOTAL DE CUENTAS
    SELECT COUNT(*) INTO total FROM Cuentas;
    -- SI HAY, CALCULAMOS
    IF total > 0 THEN
        SELECT COUNT(*) INTO total_ahorro 
        FROM Cuentas 
        WHERE TipoCuenta_Cu = 2;
        SELECT COUNT(*) INTO total_corriente 
        FROM Cuentas 
        WHERE TipoCuenta_Cu = 1;
        SET porcentaje_ahorro = ROUND((total_ahorro / total) * 100, 2);
        SET porcentaje_corriente = ROUND((total_corriente / total) * 100, 2);
    END IF;
    -- DEVOLVEMOS EL RESULTADO
    SELECT 
        porcentaje_ahorro AS porcentajeAhorro,
        porcentaje_corriente AS porcentajeCorriente,
        (porcentaje_ahorro + porcentaje_corriente) AS totalPorcentaje;
END $$
DELIMITER ;

-- STORED PROCEDURE PARA: REPORTES, PORCENTAJE DE PRESTAMOS APROBADOS, RECHAZADOS Y PENDIENTES
DELIMITER $$
CREATE PROCEDURE reporte_estado_prestamos()
BEGIN
    DECLARE total_prestamos INT DEFAULT 0;
    DECLARE total_pendientes INT DEFAULT 0;
    DECLARE total_aprobados INT DEFAULT 0;
    DECLARE total_rechazados INT DEFAULT 0;
    DECLARE porcentaje_aprobados DECIMAL(5,2) DEFAULT 0.00;
    DECLARE porcentaje_rechazados DECIMAL(5,2) DEFAULT 0.00;
    DECLARE porcentaje_pendientes DECIMAL(5,2) DEFAULT 0.00;
    -- OBTENEMOS EL TOTAL DE PRESTAMOS
    SELECT COUNT(*) INTO total_prestamos FROM Prestamos;
    -- SI HAY PRESTAMOS, CALCULAMOS TODO
    IF total_prestamos > 0 THEN
        -- CONTAMOS POR ESTADO
        SELECT COUNT(*) INTO total_pendientes 
        FROM Prestamos 
        WHERE Estado_Pr = 'P';
        SELECT COUNT(*) INTO total_aprobados 
        FROM Prestamos 
        WHERE Estado_Pr = 'A';
        SELECT COUNT(*) INTO total_rechazados 
        FROM Prestamos 
        WHERE Estado_Pr = 'R';
        -- CALCULAMOS PORCENTAJES
        SET porcentaje_pendientes = ROUND((total_pendientes / total_prestamos) * 100, 2);
        SET porcentaje_aprobados = ROUND((total_aprobados / total_prestamos) * 100, 2);
        SET porcentaje_rechazados = ROUND((total_rechazados / total_prestamos) * 100, 2);
    END IF;
    -- DEVOLVEMOS EL RESULTADO COMPLETO
    SELECT 
        total_prestamos AS TotalPrestamosSolicitados,
        total_pendientes AS PrestamosPendientes,
        total_aprobados AS PrestamosAprobados,
        total_rechazados AS PrestamosRechazados,
        porcentaje_pendientes AS PorcentajePendientes,
        porcentaje_aprobados AS PorcentajeAprobados,
        porcentaje_rechazados AS PorcentajeRechazados,
        (porcentaje_pendientes + porcentaje_aprobados + porcentaje_rechazados) AS TotalPorcentaje;
END $$

DELIMITER ;
-- EJECUTAR EL PROCEDIMIENTO
CALL reporte_estado_prestamos();
-- PROCEDIMIENTO PARA EREPORTE CLIENTE CON PRESTAMOS POR FECHA
DELIMITER //
CREATE PROCEDURE reporte_clientes_con_prestamos(
    IN fecha_inicio DATE,
    IN fecha_fin DATE
)
BEGIN
    SELECT COUNT(DISTINCT DNICliente_Pr) AS CantidadClientes
    FROM Prestamos
    WHERE Fecha_Pr BETWEEN fecha_inicio AND fecha_fin;
END //
DELIMITER ;
CALL reporte_clientes_con_prestamos('2025-01-01', '2025-12-31');

-- VALIDACIONES
-- VALIDACIONES TABLA CUENTAS
ALTER TABLE CUENTAS
ADD constraint chk_saldo_valido
check (SALDO_Cu >0);

-- VALIDACIONES TABLA CUOTAS
alter table Cuotas
add constraint chk_IDPrestamo_valido
check(IDPrestamo_Cuo>0);
alter table Cuotas
add constraint chk_MontoCuota_valido
check(MontoCuota_Cuo>0);
alter table Cuotas
add constraint chk_CuentaPago_valido
check(CuentaPago_Cuo>0);

-- VALIDACIONES TABLA PRESTAMOS
alter table  Prestamos
add constraint chk_importeTotal_valido
check(ImporteTotal_Pr >0);
alter table  Prestamos
add constraint chk_importeSolicitado_valido
check(ImporteSolicitado_Pr >0);
alter table Prestamos 
add constraint chk_PlazoPagoMeses_valido
check(PlazoPagoMeses_Pr>0);
alter table Prestamos 
add constraint chk_MontoMensual_valido
check(MontoMensual_Pr>0);
alter table Prestamos 
add constraint chk_Cuotas_valido
check(Cuotas_Pr>0);

-- *** INSERTS *** --
-- INSERT PROVINCIAS
INSERT INTO Provincias (IdProvincias_Pr, descripcion_Pr) VALUES
('01', 'Buenos Aires'),
('02', 'Misiones'),
('03', 'Santa Fe'),
('04', 'Mendoza'),
('05', 'Tucuman'),
('06', 'Salta'),
('07', 'Cordoba');

-- INSERT LOCALIDADES
INSERT INTO Localidades (IdLocalidad_Loc, IdProvincia_Loc, descripcion_Loc) VALUES
-- BUENOS AIRES
('01', '01', 'La Plata'),
('02', '01', 'Mar del Plata'),
('03', '01', 'Bahía Blanca'),
('04', '01', 'Tigre'),
-- MISIONES
('05', '02', 'Posadas'),
('06', '02', 'Oberá'),
('07', '02', 'Eldorado'),
('08', '02', 'Puerto Iguazú'),
-- SANTA FE
('09', '03', 'Rosario'),
('10', '03', 'Santa Fe Capital'),
('11', '03', 'Rafaela'),
-- MENDOZA
('12', '04', 'Mendoza Capital'),
('13', '04', 'San Rafael'),
-- TUCUMAN
('14', '05', 'San Miguel de Tucumán'),
('15', '05', 'Tafí Viejo'),
-- SALTA
('16', '06', 'Salta Capital'),
('17', '06', 'Orán'),
-- CORDOBA
('18', '07', 'Córdoba Capital'),
('19', '07', 'Villa María'),
('20', '07', 'Río Cuarto');

-- INSERT TIPO DE CUENTAS
INSERT INTO TiposCuentas ( IdTipoCuenta_Tc, NombreTipo_Tc) VALUES
(1, 'Cuenta Corriente'),
(2, 'Caja de Ahorro');

-- INSERT TIPO DE MOVIMIENTOS
INSERT INTO TiposMovimientos (IdTipoMovimiento_Tm, Descripcion_Tm) VALUES 
(1, 'Alta de cuenta'),
(2, 'Alta de préstamo'),
(3, 'Pago de préstamo'),
(4, 'Transferencia');

-- INSERT CLIENTES
 INSERT INTO Clientes (DNI_Cl, CUIL_Cl, Nombre_Cl, Apellido_Cl, Sexo_Cl,
    Nacionalidad_Cl, FechaNacimiento_Cl, Direccion_Cl,
    IdLocalidad_Cl, IdProvincia_Cl, CorreoElectronico_Cl, Telefonos_Cl) VALUES
('12345678', '20123456780', 'MARIA', 'PEREZ', 'F', 'Argentina', '1990-05-10', 'Calle Falsa 123', '01', '01', 'maria.perez@email.com', '1134567890'),
('23456789', '20234567891', 'JUAN', 'GOMEZ', 'M', 'Argentina', '1985-03-20', 'Av. Siempre Viva 742', '03', '01', 'juan.gomez@email.com', '1145678901'),
('34567890', '20345678902', 'LAURA', 'SUAREZ', 'F', 'Argentina', '1992-07-15', 'San Martín 1234', '03', '02', 'laura.suarez@email.com', '1156789012'),
('45678901', '20456789013', 'CARLOS', 'RAMIREZ', 'M', 'Argentina', '1988-08-22', 'Belgrano 456', '04', '02', 'carlos.ramirez@email.com', '1167890123'),
('56789012', '20567890124', 'SOFIA', 'LOPEZ', 'F', 'Argentina', '1995-12-01', 'Mitre 789', '05', '03', 'sofia.lopez@email.com', '1178901234'),
('67890123', '20678901235', 'PEDRO', 'FERNANDEZ', 'M', 'Argentina', '1983-06-18', 'Rivadavia 321', '06', '03', 'pedro.fernandez@email.com', '1189012345'),
('78901234', '20789012346', 'MARTINA', 'GARCIA', 'F', 'Argentina', '1991-09-05', 'Sarmiento 654', '07', '04', 'martina.garcia@email.com', '1190123456'),
('89012345', '20890123457', 'LUCAS', 'MARTINEZ', 'M', 'Argentina', '1989-11-25', 'Alsina 147', '08', '04', 'lucas.martinez@email.com', '1201234567'),
('90123456', '20901234568', 'AGUSTINA', 'RODRIGUEZ', 'F', 'Argentina', '1993-03-30', 'Urquiza 852', '09', '05', 'agustina.rodriguez@email.com', '1212345678'),
('11223344', '20112233445', 'NICOLAS', 'SOSA', 'M', 'Argentina', '1996-02-14', 'Perón 111', '10', '05', 'nicolas.sosa@email.com', '1223456789'),
('22334455', '20223344556', 'JULIETA', 'TORRES', 'F', 'Argentina', '1990-10-10', 'Laprida 222', '11', '06', 'julieta.torres@email.com', '1234567890'),
('33445566', '20334455667', 'DIEGO', 'MORALES', 'M', 'Argentina', '1984-04-17', '9 de Julio 333', '12', '06', 'diego.morales@email.com', '1245678901'),
('44556677', '20445566778', 'VALENTINA', 'AGUIRRE', 'F', 'Argentina', '1997-01-08', 'Italia 444', '13', '01', 'valentina.aguirre@email.com', '1256789012'),
('55667788', '20556677889', 'TOMAS', 'CASTRO', 'M', 'Argentina', '1992-07-19', 'Francia 555', '14', '02', 'tomas.castro@email.com', '1267890123'),
('66778899', '20667788990', 'MICAELA', 'ORTEGA', 'F', 'Argentina', '1994-05-23', 'España 666', '15', '03', 'micaela.ortega@email.com', '1278901234');

-- INSERT USUARIOS
INSERT INTO Usuarios (DNI_Usu, Nombre_Usu, Contrasenia_Usu, Rol_Usu) VALUES
-- CLIENTES
('12345678', 'MARIA', 'clave123', 'C'),
('23456789', 'JUAN', 'segura99', 'C'),
('34567890', 'LAURA', 'pass4567', 'C'),
('45678901', 'CARLOS', 'carlos1', 'C'),
('56789012', 'SOFIA', 'sofia2', 'C'),
('67890123', 'PEDRO', 'pedro3', 'C'),
('78901234', 'MARTINA', 'marti4', 'C'),
('89012345', 'LUCAS', 'lucas5', 'C'),
('90123456', 'AGUSTINA', 'agus6', 'C'),
('11223344', 'NICOLAS', 'nicolas7', 'C'),
('22334455', 'JULIETA', 'juli8', 'C'),
('33445566', 'DIEGO', 'diego9', 'C'),
('44556677', 'VALEN', 'valen10', 'C'),
('55667788', 'TOMAS', 'tomas11', 'C'),
('66778899', 'MICAELA', 'mica12', 'C'),
-- ADMINISTRADORES
('34955063', 'JUDITH', 'clave456', 'A'),
('42623741', 'PABLO', 'Pablo123', 'A'),
('40000001', 'CARO', 'Caro123', 'A'),
('40000002', 'CELI', 'Celi123', 'A'),
('40000003', 'RODRI', 'Rodri123', 'A');

-- INSERT CUENTAS
INSERT INTO Cuentas (ClienteDNI_Cu, FechaCreacion_Cu, TipoCuenta_Cu, CBU_Cu, SALDO_Cu) VALUES
-- CUENTAS CORRIENTES
('12345678', CURDATE(), 1, '00000000000000000001', 10000.00),
('23456789', CURDATE(), 1, '00000000000000000002', 10000.00),
('34567890', CURDATE(), 1, '00000000000000000003', 10000.00),
('45678901', CURDATE(), 1, '00000000000000000004', 10000.00),
('56789012', CURDATE(), 1, '00000000000000000005', 10000.00),
('67890123', CURDATE(), 1, '00000000000000000006', 10000.00),
('78901234', CURDATE(), 1, '00000000000000000007', 10000.00),
('89012345', CURDATE(), 1, '00000000000000000008', 10000.00),
('90123456', CURDATE(), 1, '00000000000000000009', 10000.00),
('11223344', CURDATE(), 1, '00000000000000000010', 10000.00),
('22334455', CURDATE(), 1, '00000000000000000011', 10000.00),
('33445566', CURDATE(), 1, '00000000000000000012', 10000.00),
('44556677', CURDATE(), 1, '00000000000000000013', 10000.00),
('55667788', CURDATE(), 1, '00000000000000000014', 10000.00),
('66778899', CURDATE(), 1, '00000000000000000015', 10000.00),
-- CAJA DE AHORRO
('12345678', CURDATE(), 2, '00000000000000000016', 10000.00),
('23456789', CURDATE(), 2, '00000000000000000017', 10000.00),
('34567890', CURDATE(), 2, '00000000000000000018', 10000.00),
('45678901', CURDATE(), 2, '00000000000000000019', 10000.00),
('56789012', CURDATE(), 2, '00000000000000000020', 10000.00),
('67890123', CURDATE(), 2, '00000000000000000021', 10000.00),
('78901234', CURDATE(), 2, '00000000000000000022', 10000.00),
('89012345', CURDATE(), 2, '00000000000000000023', 10000.00),
('90123456', CURDATE(), 2, '00000000000000000024', 10000.00),
('11223344', CURDATE(), 2, '00000000000000000025', 10000.00),
('22334455', CURDATE(), 2, '00000000000000000026', 10000.00),
('33445566', CURDATE(), 2, '00000000000000000027', 10000.00),
('44556677', CURDATE(), 2, '00000000000000000028', 10000.00),
('55667788', CURDATE(), 2, '00000000000000000029', 10000.00),
('66778899', CURDATE(), 2, '00000000000000000030', 10000.00);

-- INSERT PRESTAMOS
INSERT INTO Prestamos (DNICliente_Pr, NumeroCuenta_Pr, Fecha_Pr, ImporteTotal_Pr, ImporteSolicitado_Pr, PlazoPagoMeses_Pr, MontoMensual_Pr, Cuotas_Pr, Estado_Pr)
VALUES
-- AUTORIZADOS
('12345678', 1, '2025-01-15', 210000.00, 120000.00, 12, 17500.00, 12, 'A'),
('23456789', 3, '2025-02-10', 90000.00, 60000.00, 6, 15000.00, 6, 'A'),
('34567890', 5, '2025-03-20', 112500.00, 90000.00, 9, 12500.00, 9, 'A'),
('45678901', 7, '2025-04-05', 37500.00, 30000.00, 3, 12500.00, 3, 'A'),
('56789012', 9, '2025-05-10', 262500.00, 150000.00, 12, 21875.00, 12, 'A'),
('67890123', 11, '2025-06-01', 62500.00, 50000.00, 3, 20833.33, 3, 'A'),
('78901234', 13, '2025-06-20', 420000.00, 240000.00, 12, 35000.00, 12, 'A'),
-- PENDIENTES
('22334455', 21, '2025-06-15', 210000.00, 120000.00, 12, 17500.00, 12, 'P'),
('33445566', 23, '2025-06-18', 90000.00, 60000.00, 6, 15000.00, 6, 'P'),
('44556677', 25, '2025-06-20', 112500.00, 90000.00, 9, 12500.00, 9, 'P'),
('55667788', 27, '2025-06-22', 37500.00, 30000.00, 3, 12500.00, 3, 'P'),
('66778899', 29, '2025-06-25', 300000.00, 150000.00, 24, 12500.00, 24, 'P'),
('12345678', 2, '2025-06-28', 62500.00, 50000.00, 6, 12500.00, 6, 'P'),
('23456789', 4, '2025-07-01', 480000.00, 240000.00, 24, 20000.00, 24, 'P'),
-- RECHAZADOS
('67890123', 12, '2025-07-09', 125000.00, 100000.00, 10, 12500.00, 10, 'R'),
('78901234', 14, '2025-07-09', 210000.00, 120000.00, 12, 17500.00, 12, 'R'),
('89012345', 16, '2025-07-09', 90000.00, 60000.00, 6, 15000.00, 6, 'R'),
('90123456', 18, '2025-07-09', 112500.00, 90000.00, 9, 12500.00, 9, 'R'),
('11223344', 20, '2025-07-09', 37500.00, 30000.00, 3, 12500.00, 3, 'R'),
('22334455', 22, '2025-07-09', 262500.00, 150000.00, 12, 21875.00, 12, 'R'),
('33445566', 24, '2025-07-09', 100000.00, 50000.00, 24, 4166.67, 24, 'R');

-- INSERT CUOTAS
INSERT INTO Cuotas (IDPrestamo_Cuo, NroCuenta_Cuo, NroCuota_Cuo, FechaVencimiento_Cuo, MontoCuota_Cuo, Estado_Cuo) VALUES
(1, 1, 1, '2025-02-15', 17500.00, 'D'),
(1, 1, 2, '2025-03-15', 17500.00, 'D'),
(1, 1, 3, '2025-04-15', 17500.00, 'D'),
(1, 1, 4, '2025-05-15', 17500.00, 'D'),
(1, 1, 5, '2025-06-15', 17500.00, 'D'),
(1, 1, 6, '2025-07-15', 17500.00, 'D'),
(1, 1, 7, '2025-08-15', 17500.00, 'D'),
(1, 1, 8, '2025-09-15', 17500.00, 'D'),
(1, 1, 9, '2025-10-15', 17500.00, 'D'),
(1, 1, 10, '2025-11-15', 17500.00, 'D'),
(1, 1, 11, '2025-12-15', 17500.00, 'D'),
(1, 1, 12, '2026-01-15', 17500.00, 'D'),
(2, 3, 1, '2025-03-10', 15000.00, 'D'),
(2, 3, 2, '2025-04-10', 15000.00, 'D'),
(2, 3, 3, '2025-05-10', 15000.00, 'D'),
(2, 3, 4, '2025-06-10', 15000.00, 'D'),
(2, 3, 5, '2025-07-10', 15000.00, 'D'),
(2, 3, 6, '2025-08-10', 15000.00, 'D'),
(3, 5, 1, '2025-04-20', 12500.00, 'D'),
(3, 5, 2, '2025-05-20', 12500.00, 'D'),
(3, 5, 3, '2025-06-20', 12500.00, 'D'),
(3, 5, 4, '2025-07-20', 12500.00, 'D'),
(3, 5, 5, '2025-08-20', 12500.00, 'D'),
(3, 5, 6, '2025-09-20', 12500.00, 'D'),
(3, 5, 7, '2025-10-20', 12500.00, 'D'),
(3, 5, 8, '2025-11-20', 12500.00, 'D'),
(3, 5, 9, '2025-12-20', 12500.00, 'D'),
(4, 7, 1, '2025-05-05', 12500.00, 'D'),
(4, 7, 2, '2025-06-05', 12500.00, 'D'),
(4, 7, 3, '2025-07-05', 12500.00, 'D'),
(5, 9, 1, '2025-06-10', 21875.00, 'D'),
(5, 9, 2, '2025-07-10', 21875.00, 'D'),
(5, 9, 3, '2025-08-10', 21875.00, 'D'),
(5, 9, 4, '2025-09-10', 21875.00, 'D'),
(5, 9, 5, '2025-10-10', 21875.00, 'D'),
(5, 9, 6, '2025-11-10', 21875.00, 'D'),
(5, 9, 7, '2025-12-10', 21875.00, 'D'),
(5, 9, 8, '2026-01-10', 21875.00, 'D'),
(5, 9, 9, '2026-02-10', 21875.00, 'D'),
(5, 9, 10, '2026-03-10', 21875.00, 'D'),
(5, 9, 11, '2026-04-10', 21875.00, 'D'),
(5, 9, 12, '2026-05-10', 21875.00, 'D'),
(6, 11, 1, '2025-07-01', 20833.33, 'D'),
(6, 11, 2, '2025-08-01', 20833.33, 'D'),
(6, 11, 3, '2025-09-01', 20833.33, 'D'),
(7, 13, 1, '2025-08-07', 35000.00, 'D'),
(7, 13, 2, '2025-09-07', 35000.00, 'D'),
(7, 13, 3, '2025-10-07', 35000.00, 'D'),
(7, 13, 4, '2025-11-07', 35000.00, 'D'),
(7, 13, 5, '2025-12-07', 35000.00, 'D'),
(7, 13, 6, '2026-01-07', 35000.00, 'D'),
(7, 13, 7, '2026-02-07', 35000.00, 'D'),
(7, 13, 8, '2026-03-07', 35000.00, 'D'),
(7, 13, 9, '2026-04-07', 35000.00, 'D'),
(7, 13, 10, '2026-05-07', 35000.00, 'D'),
(7, 13, 11, '2026-06-07', 35000.00, 'D'),
(7, 13, 12, '2026-07-07', 35000.00, 'D');

-- TRANSFERENCIAS
INSERT INTO Transferencias (NroCuentaOrigen_Tra, NroCuentaDestino_Tra, Fechao_Tra, Importe_Tra) VALUES
-- ENTRE CUENTAS DEL MISMO CLIENTE
(1, 16, '2025-07-15', 2000.00),
(3, 18, '2025-07-16', 1500.00),
-- A OTRAS CUENTAS
(2, 9,  '2025-07-17', 3000.00),
(4, 20, '2025-07-18', 2500.00),
(5, 17, '2025-07-19', 5000.00),
(6, 21, '2025-07-20', 3500.00),
(7, 22, '2025-07-23', 8000.00),
(9, 24, '2025-07-24', 10000.00);

INSERT INTO Movimientos (Fecha_Mv, Detalle_Mv, Importe_Mv, NumeroCuenta_Mv, IdTipoMovimiento_Mv) VALUES
-- ALTA DE PRESTAMO
(CURDATE(), 'Alta de préstamo', 120000.00, 1, 2),
(CURDATE(), 'Alta de préstamo', 60000.00, 3, 2),
(CURDATE(), 'Alta de préstamo', 90000.00, 5, 2),
(CURDATE(), 'Alta de préstamo', 30000.00, 7, 2),
(CURDATE(), 'Alta de préstamo', 150000.00, 9, 2),
(CURDATE(), 'Alta de préstamo', 50000.00, 11, 2),
(CURDATE(), 'Alta de préstamo', 240000.00, 13, 2),
-- TRANSFERENCIAS
('2025-07-15', 'Transferencia a cuenta 16', -2000.00, 1, 4),
('2025-07-15', 'Transferencia recibida de cuenta 1', 2000.00, 16, 4),
('2025-07-16', 'Transferencia a cuenta 18', -1500.00, 3, 4),
('2025-07-16', 'Transferencia recibida de cuenta 3', 1500.00, 18, 4),
('2025-07-17', 'Transferencia a cuenta 9', -3000.00, 2, 4),
('2025-07-17', 'Transferencia recibida de cuenta 2', 3000.00, 9, 4),
('2025-07-18', 'Transferencia a cuenta 20', -2500.00, 4, 4),
('2025-07-18', 'Transferencia recibida de cuenta 4', 2500.00, 20, 4),
('2025-07-19', 'Transferencia a cuenta 17', -5000.00, 5, 4),
('2025-07-19', 'Transferencia recibida de cuenta 5', 5000.00, 17, 4),
('2025-07-20', 'Transferencia a cuenta 21', -3500.00, 6, 4),
('2025-07-20', 'Transferencia recibida de cuenta 6', 3500.00, 21, 4),
('2025-07-23', 'Transferencia a cuenta 22', -8000.00, 7, 4),
('2025-07-23', 'Transferencia recibida de cuenta 7', 8000.00, 22, 4),
('2025-07-24', 'Transferencia a cuenta 24', -10000.00, 9, 4),
('2025-07-24', 'Transferencia recibida de cuenta 9', 10000.00, 24, 4);

select * from  Prestamos;
select* from movimientos;
select * from cuotas;
select * from cuentas;
select * from localidades;
select* from provincias;
select * from Usuarios;
select * from clientes;
SELECT
    m.IDMovimiento_Mv AS IDMovimiento,
    m.Fecha_Mv AS Fecha,
    m.Detalle_Mv AS Detalle,
    m.Importe_Mv AS Importe,
    tm.Descripcion_Tm AS TipoMovimiento,

    cu.NumeroCuenta_Cu AS NumeroCuenta,
    cl.DNI_Cl AS DNICliente,
    cl.Nombre_Cl AS NombreCliente,
    cl.Apellido_Cl AS ApellidoCliente

FROM Movimientos m
INNER JOIN TiposMovimientos tm ON m.IdTipoMovimiento_Mv = tm.IdTipoMovimiento_Tm
INNER JOIN Cuentas cu ON m.NumeroCuenta_Mv = cu.NumeroCuenta_Cu
INNER JOIN Clientes cl ON cu.ClienteDNI_Cu = cl.DNI_Cl

ORDER BY m.Fecha_Mv DESC;


