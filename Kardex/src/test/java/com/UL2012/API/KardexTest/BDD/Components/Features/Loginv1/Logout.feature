@featureLogoutV1
Feature: Yo como Administrador quiero cerrar mi session en la api Kardex-System
Background:
  * call read('../../../../Utils/Config.feature@PathAdmins')
  * def End = '/logout'
  * def req = read('../../../../Req/Loginv1/Login.json')
  * def res = read('../../../../Resp/Response.json')
  * header Content-Type = 'application/json'
  @test_4 @logoutCorrecto @componente @HappyPath
Scenario: Validar que endpoint de logout cierre mi sessio correctamente - 200
  * call read('Login.feature@LoginCorrecto')
  Given url UrlBase + End
    * print UrlBase + End
  And param id_asistency = ID_Asis
  When method GET
  Then status 200
  And match response == res
    And match response[0].type == 'STATUS'
    And match response[0].title == 'Cierre de session'
    And match response[0].message == 'Se cerro la session,Correctamente'
    And match response[0].codeStatus == null
    And match response[0].data == ''
    And match response[0].cod_Msg == 'STD01'

@test_5 @logoutNocorrecto @componente @UnHappyPath
 Scenario Outline: Validar que endpoint de logout falle por id session inexistentes o expirados - 400
   Given url UrlBase + End
   * print UrlBase + End
   And param id_asistency = '<id>'
   When method GET
   Then status 400
   And match response == res
  And match response[0].type == 'STATUS'
  And match response[0].title == 'Cierre de session'
  And match response[0].message == 'La session no se cerro porque no existe o tienes otra sesión abierta'
  And match response[0].codeStatus == null
  And match response[0].data == ''
  And match response[0].cod_Msg == 'STD01'
  Examples:
    | id |
    | MSA4581746160988 |
    | DOH6701746160990 |



