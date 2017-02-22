$ ->
  $.get "/users", (data) ->
    $.each data, (scoreScreen, score) ->
      $("#users").append $("<li>").text score.username
      $("#times").append $("<li>").text score.time 
      