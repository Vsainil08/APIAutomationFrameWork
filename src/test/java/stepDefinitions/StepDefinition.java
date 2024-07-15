package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;
import resources.APIResouces;
import resources.TestDataBuild;
import resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;

public class StepDefinition extends Utils {
	ResponseSpecification  resSpec;
	 RequestSpecification response;
	 Response res;
	 TestDataBuild data= new TestDataBuild();
	 static String place_id;
	 
	 @Given("Add Place PayLoad with {string} {string} {string}")
	 public void add_place_pay_load_with(String name, String language, String address) throws IOException { 
		
		//RestAssured.baseURI="https://rahulshettyacademy.com";
		  
		  response = given().spec(requestSpecification())
				  .body(data.AddPlacePayLoad(name, language, address));
	}
	 @When("user calls {string} with {string} HTTP request")
	 public void user_calls_with_http_request(String resource, String method) {
		
		APIResouces resourceAPI = APIResouces.valueOf(resource);
		resourceAPI.getResource();
		System.out.println(resourceAPI.getResource());
		resSpec =new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();		 
		
		if(method.equalsIgnoreCase("POST"))
			res = response.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			res = response.when().get(resourceAPI.getResource());		
	}
	@Then("the API call is success with statusCode is {int}")
	public void the_api_call_is_success_with_status_code_is(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(res.getStatusCode(),200);  
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String KeyValue, String Expectedvalue) {
	    // Write code here that turns the phrase above into concrete actions
		
	    assertEquals(getJsonPath(res, KeyValue), Expectedvalue);   
	    
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String getPLaceAPI) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	  
		place_id=getJsonPath(res, "place_id");
		response = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(getPLaceAPI, "GET");
		String actualName=getJsonPath(res, "name");
		assertEquals(expectedName, actualName);
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		
		response =given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}







}
