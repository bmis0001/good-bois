# Good Bois
This is a sample project showcasing Android architecture using MVVM. 
This app displays images and information related to various dog breeds using [The Dog API](https://www.thedogapi.com/).



### Features
This project consists of: 
- Lifecycle aware component to perform actions in response to a change in the lifecycle
- MVVM architectural pattern
- Dependency injection using Hilt
- Retrofit library for networking
- Picasso library to fetch load images
- Navigation UI to navigate between different fragments
- Unit tests to validate a few strings and also also test if retrofit is fetching and parsing data
- A simple unit test to display a user journey


### Project structure
This project has two main parts, the __main__ directory which constits of app functionality, __test__ directory which consists of unit test classes and __androidTest__ directory which consists of single user journey test. The packages and their respective files for the __main__ directory are:
- __adapter__: RecyclerView adapter and DiffUtil class
  - __SearchAdapter__
  - __SearchAdapterDiffUtil__
- __di__: Dependency providing classes using hilt
  - __NetworkModule__: Defined retrofit objects with application level scope (Singleton)
- __model__: Contains data classes
  - __ApiResponse__: Contains data classes related to search image api response
- __repository__: Consists of different data repositories, which are fetched from database/server
  - __ImageRepository__: Repository for geting data from the server
- __retrofit__: Consists of classes dealing with different endpoints
  - __SearchApi__: Consists of search API endpoint
- __ui__: It consists of classes dealing with UI like custom views, activities and fragments
  - __MainActivity__: App's launcher activity. It also acts as a base where first fragment will be placed
  - __DoggieSearchFragment__: It displays all the breeds of dogs. User can input a breed via editText which will fetch data from the API and present it to user in 2 column scrollable view
  - __DoggieDetailFragment__: It receives the data from DoggieSearchFragment and displays the image of the dog breed and its description
- __util__: Consists of all the helper classes and functions used throughout the project
  - __Constants__: Contains constants used within the app
  - __InternetConnectivity__: Used to check if the device is conneced to the internet to optimize network access
  - __Validator__: Validates if the string inputs and urls are valid
- __viewmodel__: Contains all the viewModel classes used in project
  - __BaseViewModel__: Generic viewModel class used within the project
- __res__: Resource directory containing layouts, themes, drawable, navigation graph, etc.

The files int the __test__ directory are:
- __SearchRequestTest__: Test class for testing if the retrofit is fetching and parsing the data
- __SearchTextValidatorTest__: Test class for testing if the Validator class is checking for empty input properly
- __UrlValidatorTest__: Test class for testing if the Validator class is validating the url properly

The files int the __androidtTest__ directory are:
- __HomeScreenTest__: Test class showcasing a simple user journey


Additional comments are provided within the code for better understanding.


### Potential improvements
- Internet connection validator is implemented but a better approach would be internet observer for improved intuitiveness (Loads images when the network is available)
- Instrumentation tests could be improved
