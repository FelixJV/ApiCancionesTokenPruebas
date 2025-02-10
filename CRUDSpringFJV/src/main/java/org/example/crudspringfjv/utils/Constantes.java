package org.example.crudspringfjv.utils;

public final class Constantes {
    public static final String THYMELEAFPREFIX = "/WEB-INF/templates/";
    public static final String THYMELEAFSUFIX = ".html";
    public static final String TEMPLATE_ENGINE_ATTR = "thymeleaf.TemplateEngineInstance";
    public static final String MESSAGE_ATR = "message";
    public static final String PL_ATR = "pl";
    // Strings para LoginServlet
    public static final String LOGIN_VIEW = "login";
    public static final String LOGIN_ERROR = "Usuario o contraseña incorrectos";
    public static final String USER = "user";
    public static final String LOGIN_REDIRECT_PLAYLIST = "playlist";
    public static final String ERROR = "error";
    public static final String REGISTER = "register";

    // Strings para PlaylistServlet
    public static final String PLAYLIST_VIEW = "playlist";
    public static final String ACTION_ADD = "add";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_UPDATE = "update";
    public static final String PARAM_ID = "id";
    public static final String PARAM_TITULO = "titulo";
    public static final String PARAM_ARTISTA = "artista";
    public static final String LOGIN = "login";
    public static final Object CUENTA_VERIFICADA ="Cuenta verificada";
    public static final Object ERROR_EN_REGISTRO = "Error al registrar";

    public static final Object NOT_FOUND_CANCION = "Canción no encontrada";
    public static final Object INVALID_TOKEN = "Token inválido o expirado";
}
