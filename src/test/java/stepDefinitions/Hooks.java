package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		StepDefinition stepd = new StepDefinition();
		if(StepDefinition.place_id==null)
		{
		stepd.add_place_pay_load_with("Vishal", "Haryanvi", "Delhi");
		stepd.user_calls_with_http_request("AddPlaceAPI", "POST");
		stepd.verify_place_id_created_maps_to_using("Vishal", "getPlaceAPI");
		}
	}

}
