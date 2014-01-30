'use strict';
var util = require('util'),
    path = require('path'),
    yeoman = require('yeoman-generator'),
    _ = require('lodash'),
    _s = require('underscore.string'),
    pluralize = require('pluralize'),
    asciify = require('asciify');

var AngularScalatraGenerator = module.exports = function AngularScalatraGenerator(args, options, config) {
  yeoman.generators.Base.apply(this, arguments);

  this.on('end', function () {
    this.installDependencies({ skipInstall: options['skip-install'] });
  });

  this.pkg = JSON.parse(this.readFileAsString(path.join(__dirname, '../package.json')));
};

util.inherits(AngularScalatraGenerator, yeoman.generators.Base);

AngularScalatraGenerator.prototype.askFor = function askFor() {

  var cb = this.async();

  console.log('\n' +
    '+-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+\n' +
    '|a|n|g|u|l|a|r| |s|c|a|l|a|t|r|a| |g|e|n|e|r|a|t|o|r|\n' +
    '+-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+\n' +
    '\n');

  var prompts = [{
    type: 'input',
    name: 'baseName',
    message: 'What is the name of your application?',
    default: 'myapp'
  },
  {
    type: 'input',
    name: 'packageName',
    message: 'What is your default package name?',
    default: 'com.mycompany.myapp'
  }];

  this.prompt(prompts, function (props) {
    this.baseName = props.baseName;
    this.packageName = props.packageName;

    cb();
  }.bind(this));
};

AngularScalatraGenerator.prototype.app = function app() {

  this.entities = [];
  this.resources = [];
  this.generatorConfig = {
    "baseName": this.baseName,
    "packageName": this.packageName,
    "entities": this.entities,
    "resources": this.resources
  };
  this.generatorConfigStr = JSON.stringify(this.generatorConfig, null, '\t');

  this.template('_generator.json', 'generator.json');
  this.template('_package.json', 'package.json');
  this.template('_bower.json', 'bower.json');
  this.template('bowerrc', '.bowerrc');
  this.template('Gruntfile.js', 'Gruntfile.js');
  this.copy('gitignore', '.gitignore');

  var packageFolder = this.packageName.replace(/\./g, '/');

  var projectDir = 'project/';
  var resourcesDir = 'src/main/resources/';
  var scalaDir = 'src/main/scala/';
  var appDir = scalaDir + packageFolder + '/';
  var dataDir = appDir + 'data/';
  var modelsDir = appDir + 'models/';
  var webappDir = 'src/main/webapp/';
  var webinfDir = webappDir + 'WEB-INF/';
  var testDir = 'src/test/scala/' + packageFolder + '/';
  this.mkdir(projectDir);
  this.mkdir(resourcesDir);
  this.mkdir(appDir);
  this.mkdir(dataDir);
  this.mkdir(modelsDir);
  this.mkdir(webappDir);
  this.mkdir(webinfDir);
  this.mkdir(testDir);

  this.copy('project/build.properties', projectDir + 'build.properties');
  this.template('project/_build.scala', projectDir + 'build.scala');
  this.copy('project/plugins.sbt', projectDir + 'plugins.sbt');
  this.copy('sbt', 'sbt');
  this.copy('src/main/resources/logback.xml', resourcesDir + 'logback.xml');
  this.template('src/main/scala/package/data/_DatabaseInit.scala', dataDir + 'DatabaseInit.scala');
  this.template('src/main/scala/package/data/_DatabaseSessionSupport.scala', dataDir + 'DatabaseSessionSupport.scala');
  this.template('src/main/scala/_ScalatraBootstrap.scala', scalaDir + 'ScalatraBootstrap.scala');
  this.template('src/main/scala/package/models/_ScalatraRecord.scala', modelsDir + 'ScalatraRecord.scala');
  this.copy('src/main/webapp/WEB-INF/web.xml', webinfDir + 'web.xml');

  var publicCssDir = webappDir + 'css/';
  var publicJsDir = webappDir + 'js/';
  var publicViewDir = webappDir + 'views/';
  this.mkdir(publicCssDir);
  this.mkdir(publicJsDir);
  this.mkdir(publicViewDir);
  this.template('public/_index.html', webappDir + 'index.html');
  this.copy('public/css/app.css', publicCssDir + 'app.css');
  this.template('public/js/_app.js', publicJsDir + 'app.js');
  this.template('public/js/home/_home-controller.js', publicJsDir + 'home/home-controller.js');
  this.template('public/views/home/_home.html', publicViewDir + 'home/home.html');
};

AngularScalatraGenerator.prototype.projectfiles = function projectfiles() {
  this.copy('editorconfig', '.editorconfig');
  this.copy('jshintrc', '.jshintrc');
};
