# backend-tech-assessment

Skeleton project for Backend Technical Assessment.

Includes
--------
- Maven - [pom.xml](pom.xml)
- Application properties - [application.yml](src/main/resources/application.yml)
- Runnable Spring Boot Application - [BackendTechAssessmentApplication](src/main/java/com/intuit/cg/backendtechassessment/BackendTechAssessmentApplication.java)
- REST endpoints - [RequestMappings.java](src/main/java/com/intuit/cg/backendtechassessment/controller/requestmappings/RequestMappings.java)

Requirements
------------
See Backend Technical Assessment document for detailed requirements.

####Running Tests ###########
To add a Project, we first need to create a Seller in the system.

If running on localhost, use endpoint http://localhost:8080/sellers/

Sample Input json:
{   
    "firstName": "Sam",
    "lastName": "Smith"
}

Before adding a bid, we need to create a buyer with endpoint http://localhost:8080/buyers/

Sample Input json:
{
	"firstName": "Adam",
	"lastName" : "Presley"
}

Sample json for creating a project with endpoint http://localhost:8080/projects/
{    
    "projectDescription":"Bathroom Remodel",
    "maxBudget": 8000,
    "deadlineForBids" : "2019-04-23T12:51:00.625-0700",
    "seller": {
     	"sellerId": 1
     }
}

Sample json for adding a bid with endpoint http://localhost:8080/bids/
{
	"bidAmount" : 3500,
	"project":{
		"projectId":3
	},
	"buyer": {
		"buyerId": 1
	}
}



