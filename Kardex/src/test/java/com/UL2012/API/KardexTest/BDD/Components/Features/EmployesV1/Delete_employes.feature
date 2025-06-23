@Component_Delete_Employes
Feature: Validacion de Empleado mediente su identificador
  Yo como Qa quiero validar la funcionalidad de DeleteEmployes , Mediante el codigo de empleado

  Background:
    * call read('../../../../Utils/Config.feature@PathEmployes')
    * header Content-Type = 'application/json'
    * def responses = read('classpath:com/UL2012/API/KardexTest/Resp/EmployesV1/Response.json')
    * url UrlBase
    * call read('classpath:com/UL2012/API/KardexTest/BDD/Components/Features/EmployesV1/post_employesv1.feature@test_9')

  @componente @test_11 @regresion @smoke_test
  Scenario:Validar status 200 al Eliminar un empleado mediante su codigo de empleado
  Given path '/Delete/BLE174'
   When method Delete
  Then status 200

  @componente @test_12 @regresion @smoke_test
  Scenario Outline:Validar Error 404 al eliminar un empleado con un codigo invalido por <razon>
Given path '/Delete/'+ <valor>
  When method Delete
   Then status 404
    And match response.Details =='El formato del codigo personal no es correcto'
    And match response.Message == 'Formato Incorrecto'
    And match response.Cod == 'ERR01'
    Examples:
      |Razon|valor|
      |Formato invalido|'$%FWE%4wet'|
      |Vacio|' '|
