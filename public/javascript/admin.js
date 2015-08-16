$('#add').on("click", function(){
  addInsertForm();
});

$('#search').on("click", function(){
  alert("yolo");
});

function addInsertForm(){
  $("#form").empty();
  $("#form").append("<br>");

  $("#form").append("<form  id='newvideoform'>")

  var form = "<br><label for='week_number'>Week Number</label><select name='week_number' form='newvideoform'>"

  var form = form +  getWeekNumbers();
  var form = form + "</select>"
  $("#form").append("<br>");

  $("#form").append(form);
  $("#form").append("<br><label for='semester_number'>Semester Number</label><select name='semester_number' form='newvideoform'> \
                    <option value='1'>1</option></select>");
  $("#form").append("<br><label for='url'>URL</label> \
      <input type='text' name='url' id='url' value='url' form='newvideoform'><br>")

  $("#form").append("<br><label for='title'>Title</label> \
      <input type='text' name='title' id='title' value='title' form='newvideoform'><br>")

  $("#form").append("<br><label for='is_beginner'>Beginner</label><select name='is_beginner' form='newvideoform'> \
                    <option value='false'>false</option><option value='true'>true</option></select>");

  $("#form").append("<br><label for='is_advanced'>Advanced</label><select name='is_advanced' form='newvideoform'> \
                    <option value='false'>false</option><option value='true'>true</option></select>");



  $("#form").append("<br><input id='my-submit' type='submit'> \
      </form>")




      // this is the id of the form
  $("#my-submit").click(function() {
      var url = "/videos/"; // the script where you handle the form input.
      var fun =  $("#newvideoform").serialize();
      console.log(fun);
      $.ajax({
             type: "POST",
             url: url,
             data: $("#newvideoform").serialize(), // serializes the form's elements.
             success: function(data)
             {
                 $('#form').empty();
                 alert("new video added")
                 //alert(data); // show response from the php script.
             }

           });

      //return false; // avoid to execute the actual submit of the form.
  });
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
