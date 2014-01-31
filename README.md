# The Angular-Scalatra generator 

A [Yeoman](http://yeoman.io) generator for [AngularJS](http://angularjs.org) and [Scalatra](http://scalatra.org/).

Scalatra is a Scala-based micro-framework.  For AngularJS integration with other micro-frameworks, see https://github.com/rayokota/MicroFrameworkRosettaStone.

## Installation

Install [Git](http://git-scm.com) and [node.js](http://nodejs.org).
Install Yeoman:

    npm install -g yo

Install the Angular-Scalatra generator:

    npm install -g generator-angular-scalatra

## Creating a Scalatra service

In a new directory, generate the service:

    yo angular-scalatra

Run the service:

    $ ./sbt
    > container:start

Your service will run at [http://localhost:8080](http://localhost:8080).


## Creating a persistent entity

Generate the entity:

    yo angular-scalatra:entity [myentity]

You will be asked to specify attributes for the entity, where each attribute has the following:

- a name
- a type (String, Integer, Float, Boolean, Date, Enum)
- for a String attribute, an optional minimum and maximum length
- for a numeric attribute, an optional minimum and maximum value
- for a Date attribute, an optional constraint to either past values or future values
- for an Enum attribute, a list of enumerated values
- whether the attribute is required

Files that are regenerated will appear as conflicts.  Allow the generator to overwrite these files as long as no custom changes have been made.

Run the service:

    $ ./sbt
    > container:start
    
A client-side AngularJS application will now be available by running

	grunt server
	
The Grunt server will run at [http://localhost:9000](http://localhost:9000).  It will proxy REST requests to the Scalatra service running at [http://localhost:8080](http://localhost:8080).

At this point you should be able to navigate to a page to manage your persistent entities.  

The Grunt server supports hot reloading of client-side HTML/CSS/Javascript file changes.

