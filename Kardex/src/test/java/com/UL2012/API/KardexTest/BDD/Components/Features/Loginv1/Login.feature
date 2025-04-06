Feature: Componente Login Ingresar al sistema
  //definimos el endpoint y los datos
  Background:
    * def UrlBase = 'http://localhost:5020/UL2012/API/Kardex/V1/Admins'
    * def Endpoint = '/login'

  @LoginCorrecto @HappyPath
  Scenario: Validar Login con credenciales Correctas y que status code  retorne 202 Accepted
    Given url UrlBase+Endpoint
    And request { "username": "MoisesSolis@UL.com.pe", "password": "901263455"
    When method post
    Then status 202
    And  match $..type contains 'Success'
    And  match $..title contains 'ACCESS AUTHORISED'
    And  match $..message contains 'Credenciales Validas'
    And  match $..data contains 'MSA458'
    And  match $..cod_Msg contains 'SUC02'

  @LoginNoCorrecto @UnHappyPath
  Scenario: Validar Login con credenciales Incorrectas y que status code  retorne 401 Unauthorized
    Given url UrlBase+Endpoint
    And request { "username": "MoisesSolis@UL.XX.XX", "password": "90126XX55" }
    When method post
    Then status 401
    And  match $..type contains 'Error'
    And  match $..title contains 'NO AUTHORIZED '
    And  match $..message contains 'CREDENCIALES INEXISTENTES'
    And  match $..data contains 'No Data'
    And  match $..cod_Msg contains 'ERR22'

  @LoginNoCorrecto @UnHappyPath
  Scenario: Validar Login con credenciales vacias y que status code  retorne 400 Bad Request
    Given url UrlBase+Endpoint
    And request { "username": "", "password": "" }
    When method post
    Then status 400
    And  match $..type contains 'Warning'
    And  match $..title contains 'Campos vacios'
    And  match $..message contains 'Campos vacios'
    And  match $..data contains 'No data'
    And  match $..cod_Msg contains 'ERR01'

  @LoginNoCorrecto @UnHappyPath
  Scenario: Validar Login con credenciales nulas y que status code  retorne 401 Unauthorized
    Given url UrlBase+Endpoint
    And request { "username": null, "password": null }
    When method post
    Then status 401
    And  match $..type contains 'Warning'
    And  match $..title contains 'Campos vacios'
    And  match $..message contains 'Campos vacios'
    And  match $..data contains 'No data'
    And  match $..cod_Msg contains 'ERR01'

    ######################################################################################################################
