package com.aurealab.api.util;

public class constants {
    public static class messages {
        public static final String error = "Ha ocurrido un error: ";
        public static final String noData = "No encontró ningún dato";
        public static final String consultGood = "Consultado correctamente";
        public static final String responseSaveUserGood = "Datos creado correctamente";
        public static final String responseUpdateGood = "Datos actualizados correctamente";
        public static final String dontFoundByID = "No encontro ningun dato con ese ID";
        public static final String switchDTOToEntityError = "Error al convertir DTO a Entity: el objeto es nulo.";
    }
    public static class success{
        public static final String savedSuccess = "Creado exitosamente ";
        public static final String updatedSuccess = "Actualizado exitosamente";
        public static final String overedSuccess = "Proceso terminado satisfactoriamente";
    }
    public static class errors{
        public static final String saveError = "Error al intentar crear";
        public static final String updateError = "Error al intentar actualizar";
        public static final String findError = "Error al intentar buscar";
        public static final String routeNotFound = "Error, ruta no encontrada";
        public static final String handlerException = "Ocurrió un error inesperado.";
        public static final String errorCreatingToken = "Ocurrió un error al crear el token";
        public static final String internalServerError = "INTERNAL_SERVER_ERROR";
        public static final String tokenCreationError = "TOKEN_CREATION_ERROR";
        public static final String tokenValidationError = "Token invalido, No autorizado.";
        public static final String unespectedError = "Error inesperado ";
        public static final String invalidUserOrPass = "Usuario o contraseña invalido";
        public static final String dataPersistenceError = "Datos duplicados";
    }
    public static class roleDescriptions {
        public static final String superUser = "Este rol tiene permisos globales sobre toda la aplicación";
        public static final String admin = "Este rol tiene privilegios elevados";
        public static final String supervisor = "Este rol esta mas enfocado en reportes y metricas";
        public static final String operativeUser = "Usuario operativo con capacidad de gestionar turnos";
        public static final String digiter = "Este rol solo ingresará datos ";
    }

    public static class uitlLogs{
        public static final String separator = " ----------------------------------------------------------------- ";
    }
}
