# favourite-recipe-service

ERD Diagram: https://github.com/sbsathish999/favourite-recipe-service/blob/main/Favourite-Recipes-ERD.png

API Documentation Snapshot : https://github.com/sbsathish999/favourite-recipe-service/blob/main/API-documentation.PNG

API Documentation URL: http://localhost:8080/swagger-ui/#/

Test case Report: https://github.com/sbsathish999/favourite-recipe-service/blob/main/TestCaseReport.PNG

Search Criteria information:
Available operations : (String)
1. eq - (equals)
2. cn - (contains)
3. nc - (not contains)

value: (OBject) Possible data types: Integer,String,etc.

filterKey: (String) fields of Recipe table such as (servings, ingredients, instructions, type(values : veg/non-veg) 

Sample APIs (Request & Response):

1. Creating User:
API: /user/save
Method: POST
RequestBody: 
{
  "email": "sathish@gmail.com",
  "name": "Sathish"
}

Response:
{
  "id": "78faaa53-48c4-457c-99be-b1167a9b3da9",
  "name": "Sathish",
  "email": "sathish@gmail.com"
}

2. Fetching user:
API: /user/get?email=sathish@gmail.com
Method: GET
Response:
{
  "id": "78faaa53-48c4-457c-99be-b1167a9b3da9",
  "name": "Sathish",
  "email": "sathish@gmail.com"
}

3. Saving Favourite Recipe:
API: /favourite-recipes/save
Method: POST
RequestBody:
{
  "ingredients": [
    {
      "ingredients": "400gm of fish oil"
    },
   {
      "ingredients": "4 slice of Salmon"
    }
  ],
  "instructions": [
    {
      "instructions": "Apply oil on Salmon"
    },
   {
      "instructions": "Grill the salmon for 5 mins"
    }
  ],
  "name": "Salmon grill",
  "servings": 2,
  "type": "Non-vegetarian",
  "user": {
    "id": "9ab8dc68-520b-408a-a594-ad8b91026ae0"
  }
}
ResponseBody:
{
  "id": "6cc88cea-bca8-4e5c-b22f-3d8b827ddca6",
  "name": "Salmon grill",
  "type": "Non-vegetarian",
  "servings": 2,
  "ingredients": [
    {
      "id": "80946e03-5037-4aaf-934e-f22c8db22de7",
      "ingredients": "400gm of fish oil"
    },
    {
      "id": "1dd3cbfb-fbd0-4b2f-acf0-30db142e3157",
      "ingredients": "4 slice of Salmon"
    }
  ],
  "instructions": [
    {
      "id": "53953bcd-193b-4939-b051-0f66d4052fb2",
      "instructions": "Apply oil on Salmon"
    },
    {
      "id": "4067c68a-fc49-4943-9ac6-c09306371f4c",
      "instructions": "Grill the salmon for 5 mins"
    }
  ],
  "user": {
    "id": "9ab8dc68-520b-408a-a594-ad8b91026ae0",
    "name": null,
    "email": null
  }
}

4. Search Favourite Recipes with search criteria:
API: /favourite-recipes/search/9ab8dc68-520b-408a-a594-ad8b91026ae0
Method: Post
Path vairable: {userId}
RequestBody:
[
  {
    "filterKey": "ingredients",
    "operation": "nc",
    "value": "salmon"
  },
 {
    "filterKey": "instructions",
    "operation": "cn",
    "value": "oven"
  },
{
    "filterKey": "servings",
    "operation": "eq",
    "value": 2
  },
{
    "filterKey": "type",
    "operation": "eq",
    "value": "vegetarian"
  }
]

ResponseBody:
[
  {
    "id": "580409b9-0ef3-47fc-b460-a7dacbe0e35a",
    "name": "Grilled Potato",
    "type": "vegetarian",
    "servings": 2,
    "ingredients": [
      {
        "id": "cf8326b9-5aad-4cb0-8c1f-dce94738cb32",
        "ingredients": "400gm of fish oil"
      },
      {
        "id": "db92776d-fd4d-4662-8d96-2102e595b204",
        "ingredients": "4 slice of Potato"
      }
    ],
    "instructions": [
      {
        "id": "0a18d680-2469-40a3-bb90-0579ec75914e",
        "instructions": "Apply oil on potatoes"
      },
      {
        "id": "ac0d169a-883b-4936-b8e1-b76e55a5779d",
        "instructions": "Grill the salmon for 5 mins with oven"
      }
    ],
    "user": {
      "id": "9ab8dc68-520b-408a-a594-ad8b91026ae0",
      "name": "Sathish",
      "email": "sathish@gmail.com"
    }
  }
]




