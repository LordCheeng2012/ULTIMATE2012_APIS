Feature: Configuracion Inicial para el Api
//definimos el endpoint y los datos
    Background:
        * def Url = 'http://localhost:5020/UL2012/API/Kardex/V1'
        * def Admins = '/Admins'

@PathAdmins
Scenario: Validar el endpoint de la API
    * def UrlBase = Url + Admins
    * print 'Url Base de prueba para Admins es  : '+ UrlBase
