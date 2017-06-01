# WeatherRest
Simple example on how to use Retrofit

In essence:
Instrument the project (permissions, dependencies)
- Find how to access your needed REST API
- Use a browser to get a sample JSON response, use jsonschema2pojo to create POJOs for it, and copy to your project.
- The “Example.java” is the response root, rename it (eg. WeatherResponse.java)
- Define a Retrofit service interface (@GET(..))
- Use: Create Retrofit client, instantiate call, and execute.
