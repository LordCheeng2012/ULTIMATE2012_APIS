@EmpleadosV1
Feature: Validar funcionamineto del endpoint de empleados v1
Yo como un usuario de la API Kardex quiero validar la creacion  de un empleado de kardex v1
  Background:
    * call read('../../../../Utils/Config.feature@PathEmployes')
    * header Content-Type = 'application/json'
    * def responses = read('classpath:com/UL2012/API/KardexTest/Resp/EmployesV1/Response.json')
    * def data = read('classpath:com/UL2012/API/KardexTest/Req/EmployesV1/post_request.json')

    @test_9 @EmpleadosV1 @regresion @HappyPath @componente
    Scenario: Validar creacion de un empleado con datos correctos y que status code retorne 201
      Given url UrlBase + '/Save'
      And request data
      When method post
      Then status 201
      And match response == responses.Created

      @test_10 @EmpleadosV1 @regresion @UnHappyPath @componente
        Scenario Outline: Validar creacion de un empleado con datos incorrectos y que status code retorne 400
          Given url UrlBase + '/Save'
            And request <data>
            When method post
            Then status 400
            And match response == responses.BadRequest
            Examples:
            | data |
            | { "id": "AHB723", "name": "John Doe", "email":"jsj" }|
            |{}|
            |{ "id": "AHB723", "name": "John Doe", "email":"jsj", "phone":"1234567890" }|