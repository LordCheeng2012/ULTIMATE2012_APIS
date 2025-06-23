@EmpleadosV1
Feature: Validar funcionamineto del endpoint de empleados v1
Yo como un usuario de la API Kardex quiero validar el listado de empleados de kardex v1
  Background:
    * call read('../../../../Utils/Config.feature@PathEmployes')
    * def Endpoint = '/login'
    * header Content-Type = 'application/json'
    * def responses = read('classpath:com/UL2012/API/KardexTest/Resp/EmployesV1/Response.json')

  @test_6 @EmpleadosV1 @HappyPath @componente
    Scenario: Validar listado de empleados con credenciales correctas y
    que status code retorne 200
    Given url UrlBase
    When method get
    Then status 200
    And match each response == responses.Ok

  @test_7 @EmpleadosV1 @UnHappyPath @componente
    Scenario: Validar listado de empleado especifco con su id y que status code retorne 200
    Given url UrlBase + '/AHB723'
    When method get
    Then status 200
    When method get
    And match response == responses.Ok

  @test_8 @EmpleadosV1 @UnHappyPath @componente
    Scenario Outline: Validar listado de empleado especifco con su id sin formato esperado y
    que status code retorne 404
    Given url UrlBase + '/'+ <valor>
    When method get
    Then status 404
    And match response == responses.NotFound
    Examples:
      | valor |
      | 'AHBcd' |
      | 'Ad4234few34' |
      | '12324235' |
      | null |


