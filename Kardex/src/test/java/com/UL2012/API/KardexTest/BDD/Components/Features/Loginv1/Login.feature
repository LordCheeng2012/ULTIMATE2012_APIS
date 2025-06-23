@featureLoginV1
Feature: Yo como Administrador quiero validar el login de la aplicacion Kardex_System
para poder acceder a la aplicacion
  //definimos el endpoint y los datos
  Background:
    * call read('../../../../Utils/Config.feature@PathAdmins')
    * def Endpoint = '/login'
    * def req = read('../../../../Req/Loginv1/Login.json')
    * def res = read('../../../../Resp/Response.json')
    * header Content-Type = 'application/json'

  @test_1 @LoginCorrecto @HappyPath @componente
  Scenario: Validar Login con credenciales Correctas y que status code  retorne 202 Accepted
    Given url UrlBase + Endpoint
    * print UrlBase + Endpoint
    * req.username = 'MoisesSolis@UL.com.pe'
    * req.password = '901263455'
    And request req
    When method post
    Then status 202
    * def ID_Asis = response[0].data
    * print ID_Asis
    And match  response == res
    And  match $..type contains 'Success'
    And  match $..title contains 'ACCESS AUTHORISED'
    And  match $..message contains 'Credenciales Validas'
    And  match $..cod_Msg contains 'SUC02'

  @test_2 @LoginNoCorrecto @UnHappyPath @componente
  Scenario Outline: Validar Login con credenciales vacias o valores invalidos y que status code  retorne 400 Bad Request
    Given url UrlBase+Endpoint
    * req.username = '<user>'
    * req.password = '<pass>'
    And request req
    When method post
    * print response
    Then status 400
    And match  response == res
    And  match $..type contains 'Error'
    And  match $..title contains '<title>'
    And  match $..message contains '<msg>'
    And match $..codeStatus contains <codeStatus>
    And  match $..data contains '<data>'
    And  match $..cod_Msg contains 'ERR01'
    Examples:
      |user |       pass  |        title      |            msg                  |  data  |codeStatus|
      |     |             |Error de validacion|Los campos no pueden estar vacios| |null|
      |     |901263455  |Error de validacion|Los campos no pueden estar vacios| |null|
      |#oterformat|901263455  |Error de validacion|El formato del usuario no es correcto|usuario no es el esperado|null|

 @test_3 @LoginNoCorrecto @UnHappyPath @componente
  Scenario: Validar Login con credenciales nulas y que status code  retorne 500 por error no controlado
    Given url UrlBase+Endpoint
    And request req
    * req.username = null
    * req.password = 'null'
    When method post
    * print response
    Then status 400
    And match  response == res
    And  match $..type contains 'Error'
    And  match $..title contains 'Error de validacion'
    And  match $..message contains 'Los campos no pueden estar vacios'
    And match $..codeStatus contains null
    And  match $..data contains ''
    And  match $..cod_Msg contains 'ERR01'

    ######################################################################################################################
