-- 1. Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS Gestor_Medico;
USE Gestor_Medico;

-- 2. Crear las tablas de Seguridad y Auditoría
CREATE TABLE usuario (
    Id_Usuario INT AUTO_INCREMENT,
    Username VARCHAR(20) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL, 
    Rol ENUM('Administrador', 'Medico', 'Asistente') NOT NULL,
    CONSTRAINT USR_ID_PK PRIMARY KEY (Id_Usuario),
    CONSTRAINT USR_UN_NN CHECK (Username <> ''),
    CONSTRAINT USR_PWD_NN CHECK (Password <> '')
);

CREATE TABLE bitacora_log (
    ID_LOG INT AUTO_INCREMENT,
    Id_Usuario INT NOT NULL,
    Accion VARCHAR(100) NOT NULL,
    Tabla_Afectada VARCHAR(30) NOT NULL,
    Fecha_Hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT BL_ID_PK PRIMARY KEY (ID_LOG),
    CONSTRAINT BL_USR_FK FOREIGN KEY (Id_Usuario) REFERENCES usuario(Id_Usuario) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT BL_ACC_CK CHECK (Accion <> ''),
    CONSTRAINT BL_TAB_CK CHECK (Tabla_Afectada <> '')
);

-- 3. Crear las tablas del Sistema Médico (POO Original)
CREATE TABLE Persona (
    Id INT PRIMARY KEY,
    Nombre VARCHAR(20),
    PrimerApellido VARCHAR(20),
    SegundoApellido VARCHAR(20),
    Sexo ENUM('M','F'),
    Desnutricion BOOLEAN DEFAULT FALSE,
    Sobrepeso BOOLEAN DEFAULT FALSE,
    Alergias BOOLEAN DEFAULT FALSE,
    Obesidad BOOLEAN DEFAULT FALSE,
    Diabetes BOOLEAN DEFAULT FALSE,
    OtrasEnfermedades VARCHAR(50),
    Tipo_Persona ENUM('P','A')
);

CREATE TABLE Alumno (
    Id INT PRIMARY KEY,
    viveCon ENUM('Papá','Mamá','Ambos'),
    Carrera ENUM('LISW','LIP','LSC','LIPI','LIM','LIC'),
    CONSTRAINT FK_Alumno_Persona FOREIGN KEY (Id) REFERENCES Persona(Id) ON DELETE CASCADE
);

CREATE TABLE Personal (
    Id INT PRIMARY KEY,
    Estatus ENUM('Base','Temporal'),
    CONSTRAINT FK_Personal_Persona FOREIGN KEY (Id) REFERENCES Persona(Id) ON DELETE CASCADE
);

CREATE TABLE Historial_Clinico (
    Id_Consulta INT AUTO_INCREMENT PRIMARY KEY,
    Id INT,
    padecimiento_actual VARCHAR(50),
    Antecedentes VARCHAR(50),
    Medicamento VARCHAR(20),
    PlanDeTratamiento VARCHAR(50),
    Fecha DATE,
    CONSTRAINT FK_Id_Persona FOREIGN KEY (Id) REFERENCES Persona(Id) ON DELETE CASCADE
);

-- 4. Inserción del Usuario Administrador por defecto
INSERT INTO usuario (Username, Password, Rol) 
VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador');