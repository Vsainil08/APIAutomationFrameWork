Feature: Validating Place API's
@AddPlace @Regression
Scenario Outline: Veridy if place is being Succesfully added using AddPlaceAPI
	Given Add Place PayLoad with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "Post" HTTP request
	Then the API call is success with statusCode is 200
	And "status" in response body is "OK" 
	And "scope" in response body is "APP"
	And verify place_Id created maps to "<name>" using "getPlaceAPI"
	

Examples:
	| name     | language| address            |
  | My house | Hindi   | prabhu ke charno me|
#  | Our House| Prachin | Vaikunth          |

@DeletePlace @Regression
 Scenario: Verify if Delete Place funtionality is working
	Given DeletePlace Payload 
	When user calls "deletePlaceAPI" with "Post" HTTP request
	Then the API call is success with statusCode is 200
	And "status" in response body is "OK"