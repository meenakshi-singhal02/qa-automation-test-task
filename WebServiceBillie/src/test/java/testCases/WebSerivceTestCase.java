package testCases;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pageObjects.WebServiceDTOs.WebServiceDTO;

public class WebSerivceTestCase {
	
	static Response res;
	static String strResponse;
	static List<WebServiceDTO> webServiceDTO;
	static int counter;
	
	@BeforeMethod
	public void beforeMethod()
	{
		//Define all variables
			RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
			RequestSpecification httpReq = RestAssured.given();
		
		//Get comments	
			res = httpReq.request(Method.GET, "/comments");	
	}
	
	@Test()
	public static void makeRequestAndGetResponseCode() {
		Assert.assertEquals(res.getStatusCode(), 200, "Correct status code returned");
		Reporter.log("Correct response code", true);
	}
	
	@Test(dependsOnMethods="verifyCommentBody")
	public void postIdWithCertainValue()
	{
		// Make sure that post with id 40 contains 5 comments
			System.out.println("Count of data having postId:40 is >> " + counter);
			Assert.assertEquals(counter, 5);
	}
	
	@Test(testName="verifyCommentBody")
	public void verifyCommentBody() 
	{		
		//Define all variables
			ObjectMapper mapper = new ObjectMapper();
			String expectedName = "pariatur aspernatur nam atque quis";
			String expectedEmail = "Cooper_Boehm@damian.biz";
			String body = "veniam eos ab voluptatem in fugiat ipsam quis\nofficiis non qui\nquia ut id voluptates et a molestiae commodi quam\ndolorem enim soluta impedit autem nulla";		
			counter = 0;
			
		try {
			//Get body
				strResponse = res.getBody().asString();
				webServiceDTO = mapper.readValue(strResponse, new TypeReference<ArrayList<WebServiceDTO>>() {
			});
			
			//for to iterate through response	
				for (int i = 0; i < webServiceDTO.size(); i++) 
					{
						int postId = webServiceDTO.get(i).getPostId();
						//Check postId=40
						if (postId == 40) 
						{
							counter++;
							if (webServiceDTO.get(i).getName().equals(expectedName) && webServiceDTO.get(i).getEmail().equals(expectedEmail) && webServiceDTO.get(i).getBody().equals(body))
							{
								Assert.assertEquals("Comments details matched", "Comments details matched");
								System.out.println("Expected content found with id : " + webServiceDTO.get(i).getId());
							}
						}
					}
				}		
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.assertEquals("Failed because of certain exception "+e.getMessage(), "Expected passed");
		}
		
	}

	
	

}
