var express = require('express');
var app = express();
app.use(express.static(__dirname + '/public'));
var port = 9001;
app.listen(port);
console.log('Server running on: ', port);