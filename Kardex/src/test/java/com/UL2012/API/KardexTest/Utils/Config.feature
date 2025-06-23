Feature: Configuracion Inicial para el Api
//definimos el endpoint y los datos
    Background:
        * def Url = 'http://localhost:5020/UL2012/API/Kardex/V1'
        * def Admins = '/Admins'

 @PathAdmins
Scenario: Validar el endpoint de la API
    * def UrlBase = Url + Admins
    * print 'Url Base de prueba para Admins es  : '+ UrlBase

@PathEmployes
 Scenario: Validar el endpointde la API
    * def UrlBase = Url + '/Empleados'
    * print 'Url Base de prueba para Empleados es  : '+ UrlBase

  @PathAsistency
  Scenario:validar el endpoint de la Api
* def UrlBase = Url + '/Asistency'
    * print 'Url Base de prueba para Asistency :'+UrlBase


