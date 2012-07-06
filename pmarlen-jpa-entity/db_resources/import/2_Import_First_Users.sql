DELETE FROM USUARIO_PERFIL;
DELETE FROM USUARIO;

INSERT INTO USUARIO VALUES ('lleon'		,1,'LUCIANO LEON SANCHEZ'			,'ef6299c9e7fdae6d775819ce1e2620b8'	,'lleon@perfumeriamarlen.com.mx',NULL);
INSERT INTO USUARIO VALUES ('uleon'		,1,'ULISES LEON RESENDIZ'			,'ef6299c9e7fdae6d775819ce1e2620b8'	,'uleon@perfumeriamarlen.com.mx',NULL);
INSERT INTO USUARIO VALUES ('ecastaneda'	,1,'ELIZABETH CASTAÑEDA RODRIGUEZ'	,'ef6299c9e7fdae6d775819ce1e2620b8'	,'ecastaneda@perfumeriamarlen.com.mx',NULL);
INSERT INTO USUARIO VALUES ('dleon'		,1,'DANIEL LEON RESENDIZ'			,'ef6299c9e7fdae6d775819ce1e2620b8'	,'dleon@perfumeriamarlen.com.mx',NULL);
INSERT INTO USUARIO VALUES ('root'		,1,'root'			,'977244cbc826a0f25d07a07f194835b1'	,'dleon@perfumeriamarlen.com.mx',NULL);

INSERT INTO USUARIO_PERFIL VALUES ('root'		,'root');
INSERT INTO USUARIO_PERFIL VALUES ('root'		,'pmarlenuser');

INSERT INTO USUARIO_PERFIL VALUES ('lleon'		,'pmarlenuser');
INSERT INTO USUARIO_PERFIL VALUES ('lleon'		,'admin');

INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'pmarlenuser');
INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'admin');
INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'finances');

INSERT INTO USUARIO_PERFIL VALUES ('ecastaneda','pmarlenuser');
INSERT INTO USUARIO_PERFIL VALUES ('ecastaneda','admin');
INSERT INTO USUARIO_PERFIL VALUES ('ecastaneda','finances');

INSERT INTO USUARIO_PERFIL VALUES ('dleon'		,'pmarlenuser');
INSERT INTO USUARIO_PERFIL VALUES ('dleon'		,'stock');
