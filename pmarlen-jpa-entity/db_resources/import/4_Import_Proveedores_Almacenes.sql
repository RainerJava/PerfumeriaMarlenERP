DELETE FROM PROVEEDOR_CONTACTO;
DELETE FROM CLIENTE_CONTACTO;
DELETE FROM CONTACTO;
DELETE FROM PROVEEDOR;
DELETE FROM CLIENTE;
DELETE FROM ALMACEN;

INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1, 1,'NATTURA LABORATORIOS SA DE CV','NLA820723FL4','(0155)52431670','(04455)13603749','(0155)52431668','Balsas','14',NULL,100,'natturasmendoza@prodigy.net.mx','www.punkyjunky.net');
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1, 2,'4E DE MEXICO SA DE CV','CME0412063GA','(0155) 52206979',NULL,'(0155)52206980','AVEINIDA UNO NORTE','15','1F,1G',200,'a_cordova@4edemexico.com','www.4edemexico.com');
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1, 3,'GRUPO Y DISTRIBUCIONES DE ORIENTE SA DE CV','GDO0309182W1','(0155)26089571','19916988','(0155)26089571','Calzada de Tlalpan','2310','2',300,NULL,'www.grupoydistribucionesdeoriente.com.mx');
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2, 4,'DISTRIBUIDORA SERMA','SEDJ430218119','(01777)1723771','(01777)2211509','(01777)3192220','26 NORTE','2',NULL,400,NULL,NULL);
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2, 5,'IMAGEN COMERCIAL','DICE545401IC3',NULL,'(04455) 54942833',NULL,'H. CONGRESO DE LA UNION','364','LOCAL 24',100,NULL,NULL);
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2, 6,'IMPORTADOR Y EXPORTADOR D SONY','HAMS490717C75','(0155)55125686','(0155)55102216','(0155)55185104','AV. MORELOS ','50',NULL,200,NULL,NULL);
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2, 7,'DISTRIBUIDORA SALGADO','GARA730324NT4','(0155)35364439','17944894','(0155)35364438','AV. TORRE EL CTRICA','166',NULL,300,NULL,NULL);
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2, 8,'IMPORTADOR Y EXPORTADOR','HAVE8105217Y8','(0155)55102175',NULL,'(0155)55185104','AV. MORELOS','50',NULL,400,NULL,NULL);
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2, 9,'JOHNSON  JOHNSON DE MEXICO SA DE CV','HEDV590129LV0',NULL,NULL,NULL,'JAVIER BARROS SIERRA','555',NULL,100,NULL,NULL);
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1,10,'COSDELVA SA DE CV','COS931203PR3','(0155)53916964',NULL,'(0155)53899221','CALLE 24','91',NULL,200,NULL,'www.cosdelva.com.mx');
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1,11,'SAFLOSA INDUSTRIA REAL SA DE CV','SIRV69860711','(01648)4621617','(04455)54020718','(01648)4621417','POZA RICA ','9',NULL,300,NULL,'www.saflosa.com.mx');
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1,12,'TALCO MAX SA DE CV','TAL030729LG7','(01614)4159894',NULL,'(01614)4155981','1 DE MAYO','22',NULL,400,NULL,NULL);
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1,13,'DALUX SA DE CV','DAL960529DA0','(0155)55673603',NULL,'(0155)55677051','PONIENTE 140','681',NULL,100,NULL,NULL);
INSERT INTO PROVEEDOR(CLASIFICACION_FISCAL,ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1,14,'WALDOS DÃ“LAR MART DE MEXICO S DE RL DE CV','WDM990126350','(01627)62777',NULL,NULL,'AV CENTRAL','47',NULL,200,NULL,NULL);

INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(  1,'OMAR HERRERA CONTRERAS','(04455)13603749','natturasmendoza@prodigy.net.mx'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(1,1);

INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(  2,'ANGELINA OLVERA','','a_cordova@4edemexico.com'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(2,2);

INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(  3,'ANGEL BADILLO RODRIGUEZ','19916988',''); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(3,3);

INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(  4,'JUAN JOSE SERAFIN DIAZ','(01777)2211509',''); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(4,4);

INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(  5,'ERNESTO DIAZ CERVANTES','(04455) 54942833',''); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(5,5);

INSERT INTO ALMACEN (ID,ES_FISCAL,NOMBRE,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS) VALUES(1,1,'ALMACEN FISCAL'   ,'C.ORQUIDEAS','MZ5','LT6 S/N',100,'(55)59362597');
INSERT INTO ALMACEN (ID,ES_FISCAL,NOMBRE,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS) VALUES(2,0,'ALMACEN NO FISCAL','C.ORQUIDEAS','MZ5','LT6 S/N',100,'(55)59362597');
