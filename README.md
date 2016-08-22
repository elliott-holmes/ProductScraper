#ProductScraper developed by Elliott Holmes

##Build process
This application has been kept very simple and avoided using any external JARs for the processing of the records. 
However it does include JUnit for its Unit tests so you will need to include that on the class path during the build.

#Running the JAR
To Run the application you can either run it through the IDE, by modifying the run configuration and adding a single parameter to the application. This parameter should be the URL that you wish to scrape the products from. 

Alternatively you can run this through the command line using the following :-

<code>
java -cp <path/to/jar> holmes.elliott.sainsburys.ProductScraper http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html
</code>

This will return the JSON on the screen. The JSON output has been tested as valid using an external validator (http://json.parser.online.fr/).

###Additional Information
This project was done without using any external libraries that could be used. This was to allow me to fully control the handling of the html and the output format. 

I could have used Jsoup to do the HTML parsing, and GSON to do the JSON conversion, but felt these would make a very small application very big, and would restrict the amount of coding you would see. 

Enjoy the code, and if you have any problems getting it to run, I have included a working version of the jar file in this repository so you can give that a run instead.
