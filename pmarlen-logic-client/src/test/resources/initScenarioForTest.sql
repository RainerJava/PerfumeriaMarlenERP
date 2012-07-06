DELETE FROM MOVIMIENTO_HISTORICO_PRODUCTO;
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
DELETE FROM USUARIO_PERFIL;
DELETE FROM USUARIO;
DELETE FROM PERFIL;
DELETE FROM FORMA_DE_PAGO;
DELETE FROM ESTADO;

-- =================================================================================

INSERT INTO PERFIL VALUES ('root'        ,'Super Usuario');
INSERT INTO PERFIL VALUES ('pmarlenuser' ,'Usuario Comun');

INSERT INTO USUARIO VALUES ('root' ,1,'root'							,'977244cbc826a0f25d07a07f194835b1'	,'root@perfumeriamarlen.com.mx',NULL);
INSERT INTO USUARIO VALUES ('test1',1,'test 1','ef6299c9e7fdae6d775819ce1e2620b8'	,'mail1@perfumeriamarlen.com.mx',NULL);
INSERT INTO USUARIO VALUES ('test2',1,'test 2','ef6299c9e7fdae6d775819ce1e2620b8'	,'mail2@perfumeriamarlen.com.mx',NULL);

INSERT INTO USUARIO_PERFIL VALUES  ('test1','pmarlenuser');
INSERT INTO USUARIO_PERFIL VALUES  ('test2','pmarlenuser');
INSERT INTO USUARIO_PERFIL VALUES ('root'		,'root');
INSERT INTO USUARIO_PERFIL VALUES ('root'		,'pmarlenuser');

INSERT INTO FORMA_DE_PAGO (ID,DESCRIPCION)  VALUES (1,'FORMA_DE_PAGO_1');
INSERT INTO FORMA_DE_PAGO (ID,DESCRIPCION)  VALUES (2,'FORMA_DE_PAGO_2');

INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (1 ,'ESTADO_CAPTURADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (2 ,'ESTADO_SINCRONIZADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (3 ,'ESTADO_VERIFICADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (4 ,'ESTADO_SURTIDO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (5 ,'ESTADO_ENVIADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (6 ,'ESTADO_ENTREGADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (7 ,'ESTADO_DEVUELTO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (8 ,'ESTADO_CANCELADO');

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

INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1, 1,'PROVEEDOR 1','PROV010101XXX','(0155)00000001','(0155)00000001','(0155)00000001','CALLE','0','1',100,'xxx1@mail.com','http://127.0.0.1');
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1, 2,'PROVEEDOR 2','PROV010101XXX','(0155)00000001','(0155)00000001','(0155)00000001','CALLE','0','2',200,'xxx2@mail.com','http://127.0.0.1');
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1, 3,'PROVEEDOR 3','PROV010101XXX','(0155)00000001','(0155)00000001','(0155)00000001','CALLE','0','3',300,'xxx3@mail.com','http://127.0.0.1');

INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(1,'CONTACTO 1','(04455)00000001','contacto@mail.com'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(1,1);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(2,'CONTACTO 2','(04455)00000001','contacto@mail.com'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(2,2);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(3,'CONTACTO 3','(04455)00000001','contacto@mail.com'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(3,3);

INSERT INTO ALMACEN (ID,ES_FISCAL,NOMBRE,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS) VALUES(1,1,'ALMACEN FISCAL'   ,'C.ORQUIDEAS','MZ5','LT6 S/N',100,'(55)59362597');
INSERT INTO ALMACEN (ID,ES_FISCAL,NOMBRE,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS) VALUES(2,0,'ALMACEN NO FISCAL','C.ORQUIDEAS','MZ5','LT6 S/N',100,'(55)59362597');

INSERT INTO EMPRESA(ID,NOMBRE,NOMBRE_FISCAL) VALUES (1,'EMPRESA 1','EMPRESA 1 S.A. DE C.V.');
INSERT INTO EMPRESA(ID,NOMBRE,NOMBRE_FISCAL) VALUES (2,'EMPRESA 2','EMPRESA 2 S.A. DE C.V.');

INSERT INTO LINEA(ID,NOMBRE) VALUES (1,'LINEA 1');
INSERT INTO LINEA(ID,NOMBRE) VALUES (2,'LINEA 2');
INSERT INTO LINEA(ID,NOMBRE) VALUES (3,'LINEA 3');

INSERT INTO MARCA(ID,LINEA_ID,EMPRESA_ID,NOMBRE) VALUES (1,1,1,'MARCA 1');
INSERT INTO MARCA(ID,LINEA_ID,EMPRESA_ID,NOMBRE) VALUES (2,1,2,'MARCA 2');
INSERT INTO MARCA(ID,LINEA_ID,EMPRESA_ID,NOMBRE) VALUES (3,2,1,'MARCA 3');
INSERT INTO MARCA(ID,LINEA_ID,EMPRESA_ID,NOMBRE) VALUES (4,2,2,'MARCA 4');
INSERT INTO MARCA(ID,LINEA_ID,EMPRESA_ID,NOMBRE) VALUES (5,3,1,'MARCA 5');
INSERT INTO MARCA(ID,LINEA_ID,EMPRESA_ID,NOMBRE) VALUES (6,3,2,'MARCA 6');

INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,FACTOR_MAX_DESC,COSTO,FACTOR_PRECIO,PRECIO_BASE,UNIDAD_MEDIDA) VALUES(1,1,'PRODUCTO 1','P1',95,72,0.05, 5.50 ,1.30,7.15,'G');
INSERT INTO CODIGO_DE_BARRAS(CODIGO,PRODUCTO_ID) VALUES ('7501039700952',1);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,1  ,now(),31,10,5.50 ,7.15,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (1,1,10,0);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(2,1  ,now(),33,12,5.50 ,7.15,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (2,1,12,0);

INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,FACTOR_MAX_DESC,COSTO,FACTOR_PRECIO,PRECIO_BASE,UNIDAD_MEDIDA) VALUES(2,2,'PRODUCTO 2','P2',80,72,0.05, 4.00 ,1.30,5.20,'L');
INSERT INTO CODIGO_DE_BARRAS(CODIGO,PRODUCTO_ID) VALUES ('7501039700953',2);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,2  ,now(),31,15,4.00 ,5.20,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (1,2,15,0);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(2,2  ,now(),33,2 ,4.00 ,5.20,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (2,2,2,0);

INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,FACTOR_MAX_DESC,COSTO,FACTOR_PRECIO,PRECIO_BASE,UNIDAD_MEDIDA) VALUES(3,3,'PRODUCTO 3','P3',85,72,0.05, 4.60 ,1.30,5.98,'L');
INSERT INTO CODIGO_DE_BARRAS(CODIGO,PRODUCTO_ID) VALUES ('7501039700954',3);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,3  ,now(),31, 8,4.60 ,5.98,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (1,3,8,0);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(2,3  ,now(),33, 9,4.60 ,5.98,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (2,3,9,0);

INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,FACTOR_MAX_DESC,COSTO,FACTOR_PRECIO,PRECIO_BASE,UNIDAD_MEDIDA) VALUES(4,4,'PRODUCTO 44','P1',95,72,0.05, 5.50 ,1.30,7.15,'G');
INSERT INTO CODIGO_DE_BARRAS(CODIGO,PRODUCTO_ID) VALUES ('7501039700952',4);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,4  ,now(),31,14,5.50 ,7.15,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (1,4,14,0);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(2,4  ,now(),33, 5,5.50 ,7.15,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (2,4,5,0);

INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,FACTOR_MAX_DESC,COSTO,FACTOR_PRECIO,PRECIO_BASE,UNIDAD_MEDIDA) VALUES(5,5,'PRODUCTO 50','P55',80,72,0.05, 4.00 ,1.30,5.20,'L');
INSERT INTO CODIGO_DE_BARRAS(CODIGO,PRODUCTO_ID) VALUES ('7501039700947',5);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,5  ,now(),31,42,4.00 ,5.20,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (1,5,42,0);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(2,5  ,now(),33, 3,4.00 ,5.20,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (2,5,3,0);

INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,FACTOR_MAX_DESC,COSTO,FACTOR_PRECIO,PRECIO_BASE,UNIDAD_MEDIDA) VALUES(6,6,'PRODUCTO 606','P60',85,72,0.05, 4.60 ,1.30,5.98,'L');
INSERT INTO CODIGO_DE_BARRAS(CODIGO,PRODUCTO_ID) VALUES ('3501039700945',6);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,6  ,now(),31,10,4.60 ,5.98,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (1,6,10,0);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(2,6  ,now(),33,19,4.60 ,5.98,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA) VALUES (2,6,19,0);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA,FACTOR_DESC_1) VALUES(1,1, 7.97 ,0.01);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA,FACTOR_DESC_1) VALUES(2,2, 7.97 ,0.01);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA,FACTOR_DESC_1) VALUES(3,3, 18.34,0.01);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA,FACTOR_DESC_1) VALUES(1,4, 7.97 ,0.01);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA,FACTOR_DESC_1) VALUES(2,5, 7.97 ,0.01);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA,FACTOR_DESC_1) VALUES(3,6, 18.34,0.01);

INSERT INTO CLIENTE VALUES 	(1,1,'XXXX010101ABC',now(),'CLIENTE 1',NULL,'CALLE','Ext','Int',100,'58370253,58371093',NULL,NULL,'xxx1@hotmail.com',NULL,NULL,'Precio Real $255.96','X');	
INSERT INTO CLIENTE VALUES 	(2,1,'CCXX010102DEF',now(),'CLIENTE 2',NULL,'CALLE','Ext','Int',100,'58370255,58371092',NULL,NULL,'xxx2@hotmail.com',NULL,NULL,'xx','X');	
INSERT INTO CLIENTE VALUES 	(3,1,'AAXX010103GHI',now(),'CLIENTE 3',NULL,'CALLE','Ext','Int',100,'58370258,58371091',NULL,NULL,'xxx3@hotmail.com',NULL,NULL,'yy','X');	

INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(4,'CONTACTO 4','(04455)00000001','contacto@mail.com'); 
INSERT INTO CLIENTE_CONTACTO VALUES 	(1,4);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(5,'CONTACTO 5','(04455)00000001','contacto@mail.com'); 
INSERT INTO CLIENTE_CONTACTO VALUES 	(2,5);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(6,'CONTACTO 6','(04455)00000001','contacto@mail.com'); 
INSERT INTO CLIENTE_CONTACTO VALUES 	(3,6);