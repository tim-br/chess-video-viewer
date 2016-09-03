$('#add').on("click", function(){
  $("#form").empty();
  $("#delete-div").empty();
  displayInsertForm();
});

$('#delete').on("click", function(){
  $("#form").empty();
  $("#delete-div").empty();
  dumpAllVideos();
});

function dumpAllVideos(){
  $("#delete-div").empty();
  $.ajax({
         type: "GET",
         url: "/videos",
         success: function(data)
         {
             for(i = 0; i < data.length; i++){
               $("#delete-div").append("<div>")
               $("#delete-div").append("<p><b> Title: "+data[i].title+"</b></p>");
               $("#delete-div").append("<p> URL: "+data[i].url+"</p>");
               $("#delete-div").append("<p> is beginner: "+data[i].is_beginner+"</p>");
               $("#delete-div").append("<p> is advanced: "+data[i].is_advanced+"</p>");
               $("#delete-div").append("<p> Week Number: "+data[i].week_number+"</p>");
               $("#delete-div").append("<p> Semester Number: "+data[i].semester_number+"</p>");

               $("#delete-div").append("<button class='delete-vid' id="+data[i].id+"> Delete This Video</button>");

               $("#delete-div").append("</div>");
               $("#delete-div").append("<hr>");
             }

             $(".delete-vid").on("click", function(){
               $id = $(this).attr("id");
               this_url = "/videos/"+$id;
               $.ajax({
                 url: this_url,
                type: 'DELETE',
                headers: {
                  "X-CSRF-Token": csrf_token
                },
                success: function(result) {
                  alert("video was deleted successfully (please refresh page)");
                }
                });
             })
             //alert(data); // show response from the php script.
         }

    });
}

function displayInsertForm(){
  $("#form").empty();
  $("#form").append("<br>");

  $("#form").append("<form  id='newvideoform'>")

  var form = "<br><label for='week_number'>Week Number</label><select name='week_number' form='newvideoform'>"

  var form = form +  getWeekNumbers();
  var form = form + "</select>"
  $("#form").append("<br>");

  $("#form").append(form);
  $("#form").append("<br><label for='semester_number'>Semester Number</label><select name='semester_number' form='newvideoform'> \
                    <option value='1'>1</option><option value='2'>2</option><option value='3'>3</option></select>");
  $("#form").append("<br><label for='url'>URL</label> \
      <input type='text' name='url' id='url' value='url' form='newvideoform'><br>")

  $("#form").append("<br><label for='title'>Title</label> \
      <input type='text' name='title' id='title' value='title' form='newvideoform'><br>")

  $("#form").append("<br><label for='is_beginner'>Beginner</label><select name='is_beginner' form='newvideoform'> \
                    <option value='false'>false</option><option value='true'>true</option></select>");

  $("#form").append("<br><label for='is_advanced'>Advanced</label><select name='is_advanced' form='newvideoform'> \
                    <option value='false'>false</option><option value='true'>true</option></select>");

  $("#form").append("<br><label for='is_intermediate'>Intermediate</label><select name='is_intermediate' form='newvideoform'> \
                    <option value='false'>false</option><option value='true'>true</option></select>");

  $("#form").append("<br><input id='my-submit' type='submit'> \
      </form>")


    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i = 0; i <ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length,c.length);
            }
        }
        return "";
    }

      // this is the id of the form
  $("#my-submit").click(function() {
      var url = "/videos/"; // the script where you handle the form input.
      var fun =  $("#newvideoform").serialize();
      $.ajax({
             type: "POST",
             headers: {
               "X-CSRF-Token": csrf_token
             },
             url: url,
             data: $("#newvideoform").serialize(), // serializes the form's elements.
             success: function(data)
             {
                 $('#form').empty();
                 alert("new video added")
                 //alert(data); // show response from the php script.
             }

           });

      return false; // avoid to execute the actual submit of the form.
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
