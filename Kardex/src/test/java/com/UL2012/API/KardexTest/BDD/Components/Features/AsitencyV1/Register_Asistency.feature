@RegisterAsistency_component
Feature:Validar registro de  asistencia mediante su codigo de indentificacion

  Background:
    * call read('../../../../Utils/Config.feature@PathAsistency')
    * def Endpoint = '/login'
    * header Content-Type = 'application/json'
    * url UrlBase
    * def responses = read('classpath:com/UL2012/API/KardexTest/Resp/AsistencyV1/Responses.json')

    @componente @test_12 @regresion @smoke_test
    Scenario:Validar registro de asistencia mediante  su codigo identificador con status 200
      Given path '/Register/AHB723'
      When method GET
      Then status 201
      And match response == responses.Register.Created

  @componente @test_13 @regresion @smoke_test
  Scenario:Validar registro de asistencia duplicada o despues de haber registrado con el mismo codigo
  Given path 'Register/DOH670'
 When method GET
Then status 409
 And match response == responses.Register.Conflict

@componente @test_14 @regresion @smoke_test
Scenario Outline:Validar registro de asistencia con formatos invalidos
  Given path 'Register/'+ <valor>
  When method GET
  Then status 400
  And match response == responses.Register.BadRequest
  Examples:
    |valor|
    |'invalid@format'|
    |'ABC1234567891011'|
    |'AB'|
    |'123'|
    |' '|
    |null|

    @componente @regresion @smoke_test @test_15
    Scenario:Validar registro de Asistencia con codigo de empleado inexistente
    Given path 'Register/AOB000'
      When method GET
      Then status 404
      And match response == responses.Register.Not_Found