package com.eduinteractive.web.controller;

public interface ViewPaths {

	public static final String TEST = "/html/estudiante/test.jsp";
	public static final String INICIO = "/html/index.jsp";
	public static final String PRE_INICIO = "/inicio";
	public static final String PRE_HOME_ESTUDIANTE = "/home";
	public static final String HOME_ESTUDIANTE = "/html/estudiante/home-page.jsp";
	public static final String SEARCH_TEACHER = "/html/estudiante/search-page.jsp";
	public static final String DETAILS_ESTUDIANTE = "/html/estudiante/details-estudiante-page.jsp";
	public static final String DETAILS_PROFESOR = "/html/estudiante/details-profesor-page.jsp";
	public static final String BUSQUEDA_HORARIOS = "/html/estudiante/search-horarios-page.jsp";
	public static final String VIDEO_CALL_ESTUDIANTE = "/html/estudiante/sesion-page.jsp";
	public static final String PROPERTIES_ESTUDIANTE = "/html/estudiante/properties-page.jsp";
	public static final String RECOVERY_ACCOUNT = "/html/estudiante/recoveryUser/email-page.jsp";
	public static final String RECOVERY_CODE = "/html/estudiante/recoveryUser/recovery-code-page.jsp";
	public static final String SET_PASSWORD = "/html/estudiante/recoveryUser/recover-password-page.jsp";

	//profesor

	public static final String PRE_HOME_PROFESOR = "/homeTeacher";
	public static final String HOME_PROFESOR = "/html/profesor/home-page.jsp";
	public static final String SETTINGS_PROFESOR = "/html/profesor/properties-page.jsp";
	public static final String DETAILS_PROFESOR_AS_PROFESOR = "/html/profesor/details-profesor-page.jsp";
	public static final String TEACHER_NOT_ACTIVATE = "/html/notActive/notActiveTeacher.jsp";
	public static final String VIDEO_CALL_PROFESOR = "/html/profesor/sesion-page.jsp";

}