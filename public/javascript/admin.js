$('#add').on("click", function(){
  alert("yolo");
  addInsertForm();
});

$('#search').on("click", function(){
  alert("yolo");
});

function addInsertForm(){
  $("#form").empty();
  var form = "<select name='week_number'>"

  var form = form +  getWeekNumbers();
  var form = form + "</select>"
  $("#form").append("<br>");
  $("#form").append(form);
}

function getWeekNumbers(){
  var result = ""

  for(i = 1; i < 14; i++){
    result = result + "<option value='"+i +"'>"
    result = result + i
    result = result + "</option>"

  }

  return result;
}
