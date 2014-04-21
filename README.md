# The Problem

Interacting with Confluence's JSON-RPC API can be a hassle.

# The Solution

jCAW, JSON-Confluence API Wrapper, makes working with Confluence's JSON API straight forward and easy to implement.

# Install

        go to the dist folder and download the jCAW1.4.jar. Use it as a library in your project.
        

# Example Code / Hello World


```java
        // Creation & Connection Of A Service Object
        ConfluenceService myService = new ConfluenceService();
        myService.setBaseURL("https://confluenceServiceDomain.com");
        myService.setLoginToken("jprather", "qwerty");
        myService.setAPIVersion(1);    //myService.setAPIVersion(9) for jira
        myService.connect();
        
        // Building the parameters for the request
        List<Object> parameters = new LinkedList<Object>();
        parameters.add("xyz");
        
        // Perform the API call and store the JSONArray response
        JSONArray responseData;
        responseData = myService.requestJArray("methodName", parameters);

        // Another way to retrieve data - a List of HashMaps.
        //List<HashMap<String, String>> responseData;
        //responseData = myService.requestList("methodName", parameters);

        // Example / Print The Response
        if(responseData!=null){
             int z=0;
             for(z=0;z<responseData.size();++z) {
                System.out.println(responseData.get(z).toString());
             }
        }

```


# Dependencies

None! Just include the jar or source code in your project.

# License

MIT: http://opensource.org/licenses/MIT

# Other

Status: 

* Lib Complete.
* Added experimental jira support

todo: 

* update authentication method (from os_username/os_password to basic auth)

