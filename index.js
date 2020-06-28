var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

//Connect to MYSQL
var con = mysql.createConnection({
  host : 'localhost',
  user : 'root',
  password : '290sug',//Default of XAMPP
  database : 'edmstsearch' //이름 변경하기
});

var app = express();
app.use(bodyParser.json());
// app.use(bodyParser.urlencoded({extended:true}));


//Get All PERSON from database
app.get("/person",(req,res,next)=>{
  console.log("/person 진입");
  //관계형 데이터 쓴 것
  var query = "SELECT * FROM edmstsearch.record LEFT JOIN person ON edmstsearch.record.user_name = edmstsearch.person.name";
  //con.query('SELECT * FROM alarm_list',function(error,result,fields){ //이건 관계형 데이터 없이 전부 가져오기
  con.query(query,function(error,result,fields){
    con.on('error',function(err){
      console.log('[MYSQL]ERROR',err);
    });
    // if(result && result.length){
    //   res.end(JSON.stringify(result));
    // }else{
    //   res.end(JSON.stringify('NO person here'));
    // }
   res.end(JSON.stringify(result));

  });
});

app.post('/post', (req, res) => {
   console.log('who get in here post /users');
   var inputData;

   req.on('data', (data) => {
     inputData = JSON.parse(data);
   });

   req.on('end', () => {
     console.log("user_id : "+inputData.alarm_user_code);
    var query = "SELECT alarm_name,day FROM alarm_list_name WHERE alarm_user_code LIKE '%"+inputData.alarm_user_code+"%'";
    console.log("query :"+query);
     con.query(query,function(error,result,fields){
       con.on('error',function(err){
         console.log('[MYSQL]ERROR',err);
       });
      // if(result && result.length){
         res.end(JSON.stringify(result));

         console.log("RESULT : "+ result);
    //    }else{
    //      res.end(JSON.stringify('NO person here'));
      //  }
     });
   });
});

/* 튜토리얼 post
app.post("/search",(req,res,next)=>{

  var post_data = req.body; //GET POST body
  var name_search = post_data.search;//GET field 'search' from post data
  console.log(name_search);
  var query = "SELECT * FROM person WHERE name LIKE '%"+name_search+"%'";

  con.query(query,function(error,result,fields){
    con.on('error',function(err){
      console.log('[MYSQL]ERROR',err);
    });
    if(result && result.length){
      res.end(JSON.stringify(result));
    }else{
      res.end(JSON.stringify('NO person here'));
    }
  });
});
*/
app.post('/insert',(req,res)=>{
  console.log("Here in the /insert");
  req.once('data', (data) => {
    inputData = JSON.parse(data);
  });
  req.once('end', () => {
    console.log("name : "+inputData.alarm_user_code + " , time : "+inputData.time+ " , day : "+inputData.day);
    //var insertParams = [inputData.name,inputData.address,inputData.email,inputData.phone];
    // var query = 'INSERT INTO person (name,address,email,phone) VALUES (?,?,?,?)';
    var query = 'INSERT INTO alarm_list_name SET ? ';
    con.query(query,inputData,function(err,fields){
      if(err){
        console.log(err);
      }else{
        res.end("INSERTION OK !!! THERE IS NO DELAY");
        console.log("INSERT OK");
      //  console.log(rows.insertId);
      }
    });
  });

});

app.post('/update',(req,res)=>{
  console.log("Here is in the /update");

  req.on('data', (data) => {
    inputData = JSON.parse(data);
  });
  req.on('end', () => {
  var updateParams = [inputData.email,inputData.name];
  var query = "UPDATE person SET email = ? WHERE name = ?";
    con.query(query,updateParams,function(err,rows,fields){
      console.log(query);
      if(err){
        console.log(err);
      }else{
        res.end("UPDATE OK !!! THERE IS NO DELAY");
        console.log("update OK");
      }
    });
  });

});


app.listen(3306, () => {
  console.log('Example app listening on port 3306!!');
});
