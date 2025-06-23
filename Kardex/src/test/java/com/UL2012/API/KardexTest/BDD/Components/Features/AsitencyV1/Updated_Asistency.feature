@Updated_Asistency_component
  Feature:Validar funcionamiento de Marcado de asistencia mediante su id
  Background:
    * call read('../../../../Utils/Config.feature@PathAsistency')
    * def Endpoint = '/login'
    * header Content-Type = 'application/json'
    * url UrlBase
    * def responses = read('classpath:com/UL2012/API/KardexTest/Resp/AsistencyV1/Responses.json')

    @componente @test_16 @regresion @smoke_Test
  Scenario Outline:Validar status 200 marcado de <Peticion>
  Given path '/UpdateAsistency/'
  * header code = "DOH67015062025"
  * header peticion = '<Valor>'
      When method GET
      Then status 200
  And match response == responses.Updated.Ok
  Examples:
    |Valor|Peticion|
    |Break|Marcado de salida de almuerzo|
    |RetBreak|Retorno de merienda|
    |Exit|Marcar Hora de Salida|

    @componente @test_17 @regresion @smoke_Test
    Scenario Outline:Validar status 400 al marcar un id invalido para la peticion <razon>
      Given path '/UpdateAsistency/'
      * header code = "DOH67015062025"
      * header Peticion = <Valor>
      When method GET
      Then status 400
      And match response == responses.Updated.BadRequest
      And match response.message == '<response>'
    Examples:
    |Valor|razon|response|
    |'br?lñ'|Formato invalido|Fallo en el registro , no se detecto la petición|
    |'BreakkkkKKKKK'|Numero de caracteres excedido|Limite de caracteres exedido para codigo o peticion|
    |''|Vacio|Fallo en el registro , no se detecto la petición|

  @componente @regresion @smoke_Test @test_18
    Scenario:Validar aucencia de headers requeridos como : Peticion
      Given path '/UpdateAsistency/'
      * header code = "DOH67015062025"
      When method GET
      Then status 400
      And match response == responses.Updated.BadRequest
      And match response.data == "Header : Peticion no esta presente"

@componente @regresion @smoke_Test @test_18
  Scenario:Validar aucencia de headers requeridos como : CODE
    Given path '/UpdateAsistency/'
  * header peticion = 'PET'
    When method GET
    Then status 400
    And match response == responses.Updated.BadRequest
    And match response.data == "Header : CODE no esta presente"


