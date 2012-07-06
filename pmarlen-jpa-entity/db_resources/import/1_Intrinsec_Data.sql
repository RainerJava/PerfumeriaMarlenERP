DELETE FROM TIPO_MOVIMIENTO;
DELETE FROM PEDIDO_VENTA_DETALLE; 
DELETE FROM PEDIDO_VENTA_ESTADO; 
DELETE FROM PEDIDO_VENTA;
DELETE FROM CLIENTE_CONTACTO;
DELETE FROM PROVEEDOR_CONTACTO;
DELETE FROM CONTACTO;
DELETE FROM CLIENTE;
DELETE FROM PROVEEDOR_PRODUCTO;
DELETE FROM PRODUCTO_MULTIMEDIO;
DELETE FROM PROVEEDOR;
DELETE FROM MULTIMEDIO;
DELETE FROM CODIGO_DE_BARRAS;
DELETE FROM ALMACEN_PRODUCTO;
DELETE FROM PRODUCTO;
DELETE FROM ALMACEN;
DELETE FROM MARCA;
DELETE FROM LINEA;
DELETE FROM EMPRESA;  
-- DELETE FROM POBLACION;
-- DELETE FROM MUNICIPIO_O_DELEGACION;
-- DELETE FROM ENTIDAD_FEDERATIVA;
DELETE FROM USUARIO_PERFIL;
DELETE FROM USUARIO;
DELETE FROM PERFIL;
DELETE FROM FORMA_DE_PAGO;
DELETE FROM ESTADO;

-- =================================================================================

INSERT INTO PERFIL VALUES ('root'        ,'Super Usuario');
INSERT INTO PERFIL VALUES ('pmarlenuser' ,'Usuario Comun');
INSERT INTO PERFIL VALUES ('admin' 	   ,'Administrador');
INSERT INTO PERFIL VALUES ('finances' 	   ,'Finanzas');
INSERT INTO PERFIL VALUES ('stock' 	   ,'Almacen');
INSERT INTO PERFIL VALUES ('sales' 	   ,'Vendedor');

INSERT INTO USUARIO VALUES ('root'		,1,'root'							,'977244cbc826a0f25d07a07f194835b1'	,'root@perfumeriamarlen.com.mx',NULL);

INSERT INTO USUARIO_PERFIL VALUES ('root'		,'root');
INSERT INTO USUARIO_PERFIL VALUES ('root'		,'pmarlenuser');

INSERT INTO FORMA_DE_PAGO (ID,DESCRIPCION)  VALUES (1,'FORMA_DE_PAGO_1');
INSERT INTO FORMA_DE_PAGO (ID,DESCRIPCION)  VALUES (2,'FORMA_DE_PAGO_2');

INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (1 ,'ESTADO_CAPTURADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (2 ,'ESTADO_SINCRONIZADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (3 ,'ESTADO_VERIFICADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (4 ,'ESTADO_SURTIDO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (5 ,'ESTADO_FACTURADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (6 ,'ESTADO_ENVIADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (7 ,'ESTADO_ENTREGADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (8 ,'ESTADO_DEVUELTO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (9 ,'ESTADO_CANCELADO');

INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (10,'CREACION');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (21,'EXTRACCION_A_FIS');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (22,'EXTRACCION_A_FIS_BAL');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (23,'EXTRACCION_A_NOF');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (24,'EXTRACCION_A_NOF_BAL');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (31,'AGREGACION_A_FIS');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (32,'AGREGACION_A_FIS_BAL');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (33,'AGREGACION_A_NOF');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (34,'AGREGACION_A_NOF_BAL');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (40,'TIPO_MOV_MERMA');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (50,'TIPO_MOV_MODIFICACION_COSTO_O_PRECIO');