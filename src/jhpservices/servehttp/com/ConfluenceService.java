package jhpservices.servehttp.com;
/*
Copyright [2012] [Jacob Prather]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import jhpservices.servehttp.com.parser.JSONParser;
import jhpservices.servehttp.com.parser.ParseException;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

public class ConfluenceService {
    public ConfluenceService() {
        _gen = new Random();
    }

    public ConfluenceService(String bURL, int v, String usr, String pwd) {
        this();

        if (!setBaseURL(bURL) || !setLoginToken(usr,pwd) || !setAPIVersion(v)) {
            System.out.println("Error With ConfluenceService Parameters. No Para's Were Set.");
        }
    }

    //Connect
    public boolean connect() {
        if (!checkAllCSData()) { return false; }

        String configuredURL = getBuiltCSLocation();

        URL serverURL = null;
        try {
            serverURL = new URL(configuredURL);
        }
        catch (MalformedURLException e) {
            System.out.println("Invalid URL or Issue With Auth. [C]");
            return false;
        }

        _csSession = new JSONRPC2Session(serverURL);
        return true;
    }

    //Request that returns a List of HashMaps
    //Less Stable Than requestJArray
    public List<Map<String, Object>> requestList(String method, List<Object> data) {
        if (data == null) {
            data = new ArrayList<Object>();
        }

        if (method == null || _csSession == null) {
            System.out.print("Method null, or session not initialized");
            return null;
        }

        int requestID = _gen.nextInt(1000);

        JSONRPC2Request request = new JSONRPC2Request(method, requestID);
        request.setPositionalParams(data);

        JSONRPC2Response response = null;
        try {
            response = _csSession.send(request);
        }
        catch (JSONRPC2SessionException e) {
            System.err.println(e.getMessage() + "[R]");
            return null;
        }

        if (response.indicatesSuccess()) {
            try {
                JSONParser prsr = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

                // For array responses, iterate over each and make it into a map,
                // Then add that map to the return list
                if (response.getResult().toString().startsWith("[")) {
                    JSONArray jArr = (JSONArray) prsr.parse(response.getResult().toString());

                    for (Object obj : jArr) {
                        JSONObject jObj = (JSONObject) obj;

                        Map<String, Object>propMap = new HashMap<String, Object>();
                        for (Entry<String, Object> property : jObj.entrySet()) {
                            propMap.put(property.getKey(), property.getValue());
                        }

                        list.add(propMap);
                    }
                }
                // For a single object response, make it into a map, wrap it in a list, and return
                else {
                    JSONObject jObj = (JSONObject) prsr.parse(response.getResult().toString());

                    Map<String, Object>propMap = new HashMap<String, Object>();
                    for (Entry<String, Object> property : jObj.entrySet()) {
                        propMap.put(property.getKey(), property.getValue());
                    }

                    list.add(propMap);
                }

                return list;
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println(response.getError().getMessage()+ "[R]");
        }

        return null;
    }

    //Request that returns a JSONArray
    public JSONArray requestJArray(String method, List<Object> data) {
        if (data == null) {
            data = new ArrayList<Object>();
        }

        if (method == null || _csSession == null) {
            System.out.print("Method null, or session not initialized");
            return null;
        }

        int requestID = _gen.nextInt(1000);

        JSONRPC2Request request = new JSONRPC2Request(method, requestID);
        request.setPositionalParams(data);
        JSONRPC2Response response = null;

        try {
            response = _csSession.send(request);
        }
        catch (JSONRPC2SessionException e) {
            System.err.println(e.getMessage() + "[R]");
            return null;
        }

        JSONArray jArr = null;
        if (response.indicatesSuccess()) {
            try {
                Object retObj = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(response.getResult().toString());

                if (retObj instanceof JSONArray) {
                    jArr = (JSONArray) retObj;
                }
                else {
                    jArr = new JSONArray();
                    jArr.add(retObj);
                }
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println(response.getError().getMessage()+ "[R]");
        }

        return jArr;
    }

    //SETTERS
    public boolean setBaseURL(String baseUrl) {
        if (checkInput(baseUrl)) {
            _csLocation = baseUrl;
            return true;
        }

        System.out.println("Improper Base URL. [SBU]");
        return false;
    }

    public boolean setLoginToken(String username, String password) {
        if (checkInput(username) && checkInput(password)) {
            _username = username;
            _password = password;
            return true;
        }

        System.out.println("Improper Username or Password. [SLT]");
        return false;
    }

    public boolean setAPIVersion(int version) {
         switch (version) {
            case 1:
            case 2:
                _version = version;
                return true;
            default:
                System.out.println("Improper Version. [SV]");
                return false;
        }
    }

    //HELPER+GET FUNCTIONS
    private String getBuiltCSLocation() {
        if (checkAllCSData()) {
            String versionString = null;
            switch (_version) {
                case 1 : versionString = VERSION1; break;
                case 2 : versionString = VERSION2; break;
                default :
                    System.out.println("No Version Set. [GBCSL]");
                    return null;
            }

            return String.format("%s%s%s", _csLocation, versionString, getBasicAuthString());
        }

        System.out.println("Invalid Base URL, Username, or Password. [GBCSL]");
        return null;
    }

    private String getBasicAuthString() {
        if (checkInput(_username) && checkInput(_password)) {
            return String.format("?os_username=%s&os_password=%s", _username, _password);
        }

        System.out.println("Both Username And Password must be specified. [GBAS]");
        return null;
    }

    private boolean checkInput(String x) {
        return x != null && x.length() > 0;
    }

    private boolean checkAllCSData() {
        return checkInput(_csLocation) && checkInput(_username) && checkInput(_password) && (_version == 1 || _version == 2);
    }

    //ConfluenceService data
    private String _csLocation;
    private String _username;
    private String _password;
    private int _version;
    private Random _gen;
    private JSONRPC2Session _csSession;

    // Constants
    private final String VERSION1 = "/rpc/json-rpc/confluenceservice-v1";
    private final String VERSION2 = "/rpc/json-rpc/confluenceservice-v2";
}
